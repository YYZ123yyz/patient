package www.patient.jykj_zxyl.activity.hyhd;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.hyhd.DemoHelper;
import com.hyphenate.easeui.utils.ExtEaseUtils;
import com.tencent.rtmp.*;
import com.tencent.rtmp.ui.TXCloudVideoView;
import entity.liveroom.OpenLiveCond;
import entity.liveroom.RoomDetailInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.HeadImageViewRecycleAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.CircleImageView;
import www.patient.jykj_zxyl.util.StrUtils;
import www.patient.jykj_zxyl.util.TCConstants;
import ztextviewlib.MarqueeTextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NewLivePlayerActivity extends ChatPopDialogActivity implements ITXLivePlayListener, View.OnClickListener {
    Activity mActivity;
    Context mContext;
    public ProgressDialog mDialogProgress = null;
    RoomDetailInfo mRoomDetailInfo = null;
    private String mychatid = "";
    private TXCloudVideoView mPlayerView;
    private TXLivePlayConfig mPlayConfig;
    private PhoneStateListener mPhoneListener = null;
    private TXLivePlayer     mLivePlayer = null;
    protected int            mActivityType;
    public static final int ACTIVITY_TYPE_LIVE_PLAY    = 2;
    public static final int ACTIVITY_TYPE_VOD_PLAY     = 3;
    public static final int ACTIVITY_TYPE_LINK_MIC     = 4;
    private ImageView mLoadingView;
    private boolean          mVideoPlay;
    private boolean          mVideoPause = false;
    private String playUrl = "";
    private int              mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
    private static final int  CACHE_STRATEGY_FAST  = 1;  //极速
    private static final int  CACHE_STRATEGY_SMOOTH = 2;  //流畅
    private static final int  CACHE_STRATEGY_AUTO = 3;  //自动

    private static final float  CACHE_TIME_FAST = 1.0f;
    private static final float  CACHE_TIME_SMOOTH = 5.0f;

    private static final float  CACHE_TIME_AUTO_MIN = 5.0f;
    private static final float  CACHE_TIME_AUTO_MAX = 10.0f;
    private int              mCurrentRenderMode;
    private int              mCurrentRenderRotation;

    private long             mTrackingTouchTS = 0;
    private boolean          mHWDecode   = false;
    private boolean mEnableCache;
    private long             mStartPlayTS = 0;
    private int              mCacheStrategy = 0;
    private Button btnMessage;
    private Button btnShare;
    private Button btnShut;
    private Button mBtnOrientation;
    CircleImageView iv_live_user_head;
    RecyclerView chat_head_imgs;
    TextView tv_chat_num;
    MarqueeTextView mv_chat_content;
    LinearLayoutManager mLayoutManager;
    HeadImageViewRecycleAdapter mImageViewRecycleAdapter;
    List<String> headpics = new ArrayList();
    TextView tv_head_tit;
    String mdetailCode = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mychatid = getIntent().getStringExtra("chatId");
        playUrl = getIntent().getStringExtra("pullUrl");
        mdetailCode = getIntent().getStringExtra("detailCode");
        mApp = (JYKJApplication)getApplication();
        mActivity = NewLivePlayerActivity.this;
        mContext = NewLivePlayerActivity.this;
        setContentView(R.layout.activity_new_player);
        mActivityType = getIntent().getIntExtra("PLAY_TYPE", ACTIVITY_TYPE_LIVE_PLAY);
        loadLive();
        mPlayConfig = new TXLivePlayConfig();
        checkPublishPermission();
        initview();
        iv_live_user_head = findViewById(R.id.iv_live_user_head);
        chat_head_imgs = findViewById(R.id.chat_head_imgs);
        tv_chat_num = findViewById(R.id.tv_chat_num);
        mv_chat_content = findViewById(R.id.mv_chat_content);
        tv_head_tit = findViewById(R.id.tv_head_tit);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        chat_head_imgs.setLayoutManager(mLayoutManager);
        chat_head_imgs.setHasFixedSize(true);
        mImageViewRecycleAdapter = new HeadImageViewRecycleAdapter(headpics,mApp);
        chat_head_imgs.setAdapter(mImageViewRecycleAdapter);
        if(null!=mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl() && mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl().length()>0){
            headpics.add(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
            mImageViewRecycleAdapter.setDate(headpics);
            mImageViewRecycleAdapter.notifyDataSetChanged();
        }
    }

    void initview(){
        if (mLivePlayer == null){
            mLivePlayer = new TXLivePlayer(this);
        }
        mPhoneListener = new TXPhoneStateListener(mLivePlayer);
        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
        mPlayerView.disableLog(true);
        mLoadingView = (ImageView) findViewById(R.id.loadingImageView);
        mVideoPlay = startPlayRtmp();
        btnMessage = findViewById(R.id.btnMessage);
        btnShare = findViewById(R.id.btnShare);
        btnShut = findViewById(R.id.btnShut);
        mBtnOrientation = findViewById(R.id.btnOriention);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goChat();
            }
        });

    }

    boolean isopenchat = false;
    @Override
    public void createChat() {
        Bundle parbund = new Bundle();
        parbund.putString(EaseConstant.EXTRA_CHAT_TYPE,"");
        parbund.putInt(EaseConstant.CHAT_TYPE, EaseConstant.CHATTYPE_CHATROOM);
        parbund.putString(EaseConstant.EXTRA_USER_ID, mychatid);
        parbund.putString(EaseConstant.EXTRA_USER_NAME, mychatid);
        initChat(parbund);
        initChatView();
        joinChatroom();
        setUpView();
        isopenchat = true;
    }

    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

    void loadLive(){
        getProgressBar("打开直播间","正在打开直播间，请稍后...");
        OpenLiveCond opencond = new OpenLiveCond();
        opencond.setDetailsCode(mdetailCode);
        opencond.setLoginUserPosition(mApp.loginDoctorPosition);
        opencond.setOperUserCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        opencond.setOperUserName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        opencond.setRequestClientType("1");
        LoadLiveTask loadtask = new LoadLiveTask(opencond);
        loadtask.execute();
    }

    class LoadLiveTask extends AsyncTask<Void, Void, Boolean>{
        OpenLiveCond queCond;
        RoomDetailInfo queroominfo = null;
        LoadLiveTask(OpenLiveCond queCond){
            this.queCond =queCond;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                String repjson = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queCond),"https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/getLiveRoomDetailsByDetailsCode");
                NetRetEntity retent = JSON.parseObject(repjson,NetRetEntity.class);
                if(retent.getResCode()==1){
                    String subrepjson = retent.getResJsonData();
                    RoomDetailInfo retliveresp = JSON.parseObject(subrepjson,RoomDetailInfo.class);
                    mychatid = retliveresp.getChatRoomCode();
                    playUrl = retliveresp.getPullUrl();
                    queroominfo = retliveresp;
                    return true;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                if(null!=queroominfo){
                    Glide.with(mContext).load(queroominfo.getBroadcastUserLogoUrl())
                            .apply(RequestOptions.placeholderOf(com.hyphenate.easeui.R.mipmap.docter_heard)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                            .into(iv_live_user_head);
                    String parnickname = queroominfo.getBroadcastUserName();
                    tv_head_tit.setText(parnickname);
                }
            }
            cacerProgress();
        }
    }

    StringBuffer msgnamesb = new StringBuffer();

    static final int SHOW_MESSAGE_FLAG = 101;


    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOW_MESSAGE_FLAG:
                    try {
                        EMMessage paramMessage = (EMMessage) msg.obj;
                        String parname = StrUtils.defaultStr(paramMessage.getStringAttribute("nickName"));
                        String parhead = StrUtils.defaultStr(paramMessage.getStringAttribute("imageUrl"));
                        if (null != parname && "" != parname) {
                            if (!msgmap.containsKey(parname)) {
                                msgmap.put(parname, "1");
                                if (null != msgmap.keySet() && msgmap.keySet().size() > 0) {
                                    tv_chat_num.setText(String.valueOf(msgmap.keySet().size()) + "人");
                                }
                                if (parhead.length() > 0) {
                                    headpics.add(parhead);
                                    mImageViewRecycleAdapter.setDate(headpics);
                                    mImageViewRecycleAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        mv_chat_content.setText(msgnamesb);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void showMessages(EMMessage paramMessage) {
        try {
            String parname = StrUtils.defaultStr(paramMessage.getStringAttribute("nickName"));
            if(parname.length()>0){
                msgnamesb.append("  ");
                EMTextMessageBody txtBody = (EMTextMessageBody) paramMessage.getBody();
                //Spannable span = EaseSmileUtils.(mContext, txtBody.getMessage());
                msgnamesb.append(txtBody.getMessage());
                // 设置内容
            }
            Message parmsg = new Message();
            parmsg.what = SHOW_MESSAGE_FLAG;
            parmsg.obj = paramMessage;
            myHandler.sendMessage(parmsg);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        stopPlayRtmp();
        super.onBackPressed();
    }

    private  void stopPlayRtmp() {
        stopLoadingAnimation();
        if (mLivePlayer != null) {
            mLivePlayer.stopRecord();
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
        mVideoPause = false;
        mVideoPlay = false;
    }

    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        100);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (mPlayerView != null){
            mPlayerView.onDestroy();
            mPlayerView = null;
        }
        mPlayConfig = null;
        Log.d(TAG,"vrender onDestroy");
        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_NONE);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();

        if (mPlayType == TXLivePlayer.PLAY_TYPE_VOD_FLV || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_HLS || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_MP4 || mPlayType == TXLivePlayer.PLAY_TYPE_LOCAL_VIDEO) {
            if (mLivePlayer != null) {
                mLivePlayer.pause();
            }
        } else {
            if (mPlayerView != null){
                mPlayerView.onPause();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mVideoPlay && !mVideoPause) {
            if (mPlayType == TXLivePlayer.PLAY_TYPE_VOD_FLV || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_HLS || mPlayType == TXLivePlayer.PLAY_TYPE_VOD_MP4 || mPlayType == TXLivePlayer.PLAY_TYPE_LOCAL_VIDEO) {
                if (mLivePlayer != null) {
                    mLivePlayer.resume();
                }
            }
            else {
                //startPlayRtmp();
                if (mPlayerView != null){
                    mPlayerView.onResume();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 获取内置SD卡路径
     * @return
     */
    public String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }


    private boolean startPlayRtmp() {

//        //由于iOS AppStore要求新上架的app必须使用https,所以后续腾讯云的视频连接会支持https,但https会有一定的性能损耗,所以android将统一替换会http
//        if (playUrl.startsWith("https://")) {
//            playUrl = "http://" + playUrl.substring(8);
//        }

        if (!checkPlayUrl(playUrl)) {
            return false;
        }


        mLivePlayer.setPlayerView(mPlayerView);

        mLivePlayer.setPlayListener(this);
//        mLivePlayer.setRate(1.5f);
        // 硬件加速在1080p解码场景下效果显著，但细节之处并不如想象的那么美好：
        // (1) 只有 4.3 以上android系统才支持
        // (2) 兼容性我们目前还仅过了小米华为等常见机型，故这里的返回值您先不要太当真
        mLivePlayer.enableHardwareDecode(mHWDecode);
        mLivePlayer.setRenderRotation(mCurrentRenderRotation);
        mLivePlayer.setRenderMode(mCurrentRenderMode);
        //设置播放器缓存策略
        //这里将播放器的策略设置为自动调整，调整的范围设定为1到4s，您也可以通过setCacheTime将播放器策略设置为采用
        //固定缓存时间。如果您什么都不调用，播放器将采用默认的策略（默认策略为自动调整，调整范围为1到4s）
        //mLivePlayer.setCacheTime(5);

        if (mEnableCache) {
            mPlayConfig.setCacheFolderPath(getInnerSDCardPath()+"/txcache");
            mPlayConfig.setMaxCacheItems(2);
        } else {
            mPlayConfig.setCacheFolderPath(null);
        }

        mLivePlayer.setConfig(mPlayConfig);
        mLivePlayer.setConfig(mPlayConfig);
        mLivePlayer.setAutoPlay(true);

        int result = mLivePlayer.startPlay(playUrl,mPlayType); // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;

        startLoadingAnimation();

        mStartPlayTS = System.currentTimeMillis();

        if (mActivityType == LivePlayerActivity.ACTIVITY_TYPE_VOD_PLAY) {
            findViewById(R.id.playerHeaderView).setVisibility(View.VISIBLE);
        }
        return true;
    }

    @Override
    public void onPlayEvent(int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
            Log.d("AutoMonitor", "PlayFirstRender,cost=" +(System.currentTimeMillis()-mStartPlayTS));
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS ) {
            int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
            int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION);
            int playable = param.getInt(TCConstants.NET_STATUS_PLAYABLE_DURATION);
            long curTS = System.currentTimeMillis();

            // 避免滑动进度条松开的瞬间可能出现滑动条瞬间跳到上一个位置
            if (Math.abs(curTS - mTrackingTouchTS) < 500) {
                return;
            }
            mTrackingTouchTS = curTS;
            return;
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT || event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            stopPlayRtmp();
            mVideoPlay = false;
            mVideoPause = false;
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING){
            startLoadingAnimation();
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
            stopLoadingAnimation();
            if (mActivityType == LivePlayerActivity.ACTIVITY_TYPE_VOD_PLAY) {
                findViewById(R.id.playerHeaderView).setVisibility(View.GONE);
            }
        } else if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
            //streamRecord(false);
        }

        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
//        if(mLivePlayer != null){
//            mLivePlayer.onLogRecord("[event:"+event+"]"+msg+"\n");
//        }
        if (event < 0) {
            Toast.makeText(getApplicationContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }

        else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
        }
    }

    //公用打印辅助函数
    protected String getNetStatusString(Bundle status) {
        String str = String.format("%-14s %-14s %-12s\n%-14s %-14s %-12s\n%-14s %-14s %-12s\n%-14s %-12s %-12s",
                "CPU:"+status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE),
                "RES:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH)+"*"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT),
                "SPD:"+status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED)+"Kbps",
                "JIT:"+status.getInt(TXLiveConstants.NET_STATUS_NET_JITTER),
                "FPS:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS),
                "ARA:"+status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE)+"Kbps",
                "QUE:"+status.getInt(TCConstants.NET_STATUS_CODEC_CACHE)+"|"+status.getInt(TCConstants.NET_STATUS_CACHE_SIZE),
                "DRP:"+status.getInt(TCConstants.NET_STATUS_CODEC_DROP_CNT)+"|"+status.getInt(TCConstants.NET_STATUS_DROP_SIZE),
                "VRA:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE)+"Kbps",
                "SVR:"+status.getString(TXLiveConstants.NET_STATUS_SERVER_IP),
                "AVRA:"+status.getInt(TCConstants.NET_STATUS_SET_VIDEO_BITRATE),
                "PLA:"+status.getInt(TCConstants.NET_STATUS_PLAYABLE_DURATION));
        return str;
    }

    @Override
    public void onNetStatus(Bundle status) {
        String str = getNetStatusString(status);
        Log.d(TAG, "Current status, CPU:"+status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE)+
                ", RES:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH)+"*"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT)+
                ", SPD:"+status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED)+"Kbps"+
                ", FPS:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS)+
                ", ARA:"+status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE)+"Kbps"+
                ", VRA:"+status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE)+"Kbps");
        //Log.d(TAG, "Current status: " + status.toString());
//        if (mLivePlayer != null){
//            mLivePlayer.onLogRecord("[net state]:\n"+str+"\n");
//        }
    }

    public void setCacheStrategy(int nCacheStrategy) {
        if (mCacheStrategy == nCacheStrategy)   return;
        mCacheStrategy = nCacheStrategy;

        switch (nCacheStrategy) {
            case CACHE_STRATEGY_FAST:
                mPlayConfig.setAutoAdjustCacheTime(true);
                mPlayConfig.setMaxAutoAdjustCacheTime(CACHE_TIME_FAST);
                mPlayConfig.setMinAutoAdjustCacheTime(CACHE_TIME_FAST);
                mLivePlayer.setConfig(mPlayConfig);
                break;

            case CACHE_STRATEGY_SMOOTH:
                mPlayConfig.setAutoAdjustCacheTime(false);
                mPlayConfig.setCacheTime(CACHE_TIME_SMOOTH);
                mLivePlayer.setConfig(mPlayConfig);
                break;

            case CACHE_STRATEGY_AUTO:
                mPlayConfig.setAutoAdjustCacheTime(true);
                mPlayConfig.setMaxAutoAdjustCacheTime(CACHE_TIME_SMOOTH);
                mPlayConfig.setMinAutoAdjustCacheTime(CACHE_TIME_FAST);
                mLivePlayer.setConfig(mPlayConfig);
                break;

            default:
                break;
        }
    }

    private boolean checkPlayUrl(final String playUrl) {
        if (TextUtils.isEmpty(playUrl) || (!playUrl.startsWith("http://") && !playUrl.startsWith("https://") && !playUrl.startsWith("rtmp://")  && !playUrl.startsWith("/"))) {
            if (mActivityType == ACTIVITY_TYPE_LIVE_PLAY) {
                Toast.makeText(getApplicationContext(), "播放地址不合法，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "播放地址不合法，目前仅支持flv,hls,mp4播放方式和本地播放方式（绝对路径，如\"/sdcard/test.mp4\"）!", Toast.LENGTH_SHORT).show();
            }

            return false;
        }

        switch (mActivityType) {
            case ACTIVITY_TYPE_LIVE_PLAY:
            {
                if (playUrl.startsWith("rtmp://")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://"))&& playUrl.contains(".flv")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                } else {
                    Toast.makeText(getApplicationContext(), "播放地址不合法，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            break;
            case ACTIVITY_TYPE_VOD_PLAY:
            {
                if (playUrl.startsWith("http://") || playUrl.startsWith("https://")) {
                    if (playUrl.contains(".flv")) {
                        mPlayType = TXLivePlayer.PLAY_TYPE_VOD_FLV;
                    } else if (playUrl.contains(".m3u8")) {
                        mPlayType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                    } else if (playUrl.toLowerCase().contains(".mp4")) {
                        mPlayType = TXLivePlayer.PLAY_TYPE_VOD_MP4;
                    } else {
                        Toast.makeText(getApplicationContext(), "播放地址不合法，点播目前仅支持flv,hls,mp4播放方式!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else if (playUrl.startsWith("/")) {
                    if (playUrl.contains(".mp4") || playUrl.contains(".flv")) {
                        mPlayType = TXLivePlayer.PLAY_TYPE_LOCAL_VIDEO;
                    } else {
                        Toast.makeText(getApplicationContext(), "播放地址不合法，目前本地播放器仅支持播放mp4，flv格式文件", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "播放地址不合法，点播目前仅支持flv,hls,mp4播放方式!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            break;
            default:
                Toast.makeText(getApplicationContext(), "播放地址不合法，目前仅支持rtmp,flv,hls,mp4播放方式!", Toast.LENGTH_SHORT).show();
                return false;
        }
        return true;
    }



    private void startLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
            ((AnimationDrawable)mLoadingView.getDrawable()).start();
        }
    }

    private void stopLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
            ((AnimationDrawable)mLoadingView.getDrawable()).stop();
        }
    }

    void goChat(){
        if(StrUtils.defaultStr(ExtEaseUtils.getInstance().getUserId()).length()==0){
            mApp.saveUserInfo();
            EMClient.getInstance().login(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),mApp.mProvideViewSysUserPatientInfoAndRegion.getQrCode(),new EMCallBack() {
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();

                    // update current user's display name for APNs
                    boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(ExtEaseUtils.getInstance().getNickName());
                    DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                    if(isopenchat){
                        if(chatViewLayout.getVisibility()== View.VISIBLE) {
                            chatViewLayout.setVisibility(View.GONE);
                        }else{
                            chatViewLayout.setVisibility(View.VISIBLE);
                        }
                    }else{
                        createChat();
                        chatViewLayout.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }else {
            if (isopenchat) {
                if (chatViewLayout.getVisibility() == View.VISIBLE) {
                    chatViewLayout.setVisibility(View.GONE);
                } else {
                    chatViewLayout.setVisibility(View.VISIBLE);
                }
            } else {
                createChat();
                chatViewLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    static class TXPhoneStateListener extends PhoneStateListener {
        WeakReference<TXLivePlayer> mPlayer;
        public TXPhoneStateListener(TXLivePlayer player) {
            mPlayer = new WeakReference<TXLivePlayer>(player);
        }
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            TXLivePlayer player = mPlayer.get();
            switch(state){
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    if (player != null) player.setMute(true);
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (player != null) player.setMute(true);
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (player != null) player.setMute(false);
                    break;
            }
        }
    };
}
