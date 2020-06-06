package www.patient.jykj_zxyl.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.hyhd.model.CallReceiver;
import com.hyphenate.exceptions.HyphenateException;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.basicDate.ProvideBasicsDomain;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideDoctorPatientUserInfo;
import entity.basicDate.ProvideViewSysUserPatientInfoAndRegion;
import entity.unionInfo.ProvideUnionDoctorOrg;
import entity.user.UserInfo;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;


public class JYKJApplication extends Application {

    public                     boolean                          gRefreshDate = false;                   //客户管理中是否需要刷新行政区划数据
    public                     Map<Integer,List<ProvideBasicsDomain>> gBasicDate = new HashMap<>();    //基础数据
    public                      List<Activity>                  gActivityList = new ArrayList();
    public                      Context                         gContext;
    public                      ImageLoader                         imageLoader = ImageLoader.getInstance();
    public                      DisplayImageOptions                 mImageOptions;		                                        // DisplayImageOptions是用于设置图片显示的类
    public                      CallReceiver                    mCallReceiver;
    public                      SharedPreferences_DataSave      m_persist;					                            //数据存储

    public                      UserInfo                        mLoginUserInfo;                         //登录需要的用户信息
    public                      ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;       //登录成功后服务端返回的真实用户信息

    public                     List<ProvideBasicsRegion>       gRegionList = new ArrayList<>();                    //所有区域数据
    public                     List<ProvideBasicsRegion>       gRegionProvideList = new ArrayList<>();            //省级区域数据
    public                     List<ProvideBasicsRegion>       gRegionCityList = new ArrayList<>();                //市级区域数据
    public                     List<ProvideBasicsRegion>       gRegionDistList = new ArrayList<>();                //区县级区域数据

    public                     String                          loginDoctorPosition = "20.154455^23.41548512";       //用户所处的经纬度

    public                      boolean                         isUpdateReviewUnion = false;                        //加入联盟审核信息是否更新，默认否
    public MainActivity gMainActivity;
    public                      boolean                         gNetConnectionHX;
    public                      boolean                         gNetWorkTextView = false;                       //是否显示网络状态
    public                      ProvideUnionDoctorOrg           unionSettionChoiceOrg;                          //医生联盟，设置联盟成员联盟层级
    public                      Integer                         mMsgTimeInterval = 5;                               //轮询新消息时间，初始设置5分钟
    public                      String                          gBitMapString;                                  //身份证拍照bitmap

    private                     String                              mNetRetStr;                 //返回字符串


    private                     List<ProvideDoctorPatientUserInfo> mProvideDoctorPatientUserInfo = new ArrayList<>();

    public                      int                         gNewMessageNum = 0;                                  //新消息数量
    public                      int                         gMessageNum = 10;                //可发送的消息数量
    public                      long                        gVoiceTime = 20;                 //可拨打语音消息时长（单位：秒）
    public                      long                        gVedioTime = 30;                 //可拨打视频消息时长（单位：秒）


    public                  List<ProvideBasicsDomain>                 gProvideBasicsDomainJGBJ = new ArrayList<>();             //机构背景
    public                  List<ProvideBasicsDomain>                 gProvideBasicsDomainYSZC = new ArrayList<>();                                 //医生职称


    public                      Handler                         gHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    if (mProvideViewSysUserPatientInfoAndRegion.getPatientCode() == null)
                        mProvideViewSysUserPatientInfoAndRegion.setPatientCode("");
                    if (mProvideViewSysUserPatientInfoAndRegion.getUserPwd() == null)
                        mProvideViewSysUserPatientInfoAndRegion.setUserPwd("");
                    //登录
                    String userAccountHuanxin = mProvideViewSysUserPatientInfoAndRegion.getPatientCode();
                    EMClient.getInstance().login(userAccountHuanxin, mProvideViewSysUserPatientInfoAndRegion.getQrCode(), new EMCallBack() {
                        @Override
                        public void onSuccess() {

                            System.out.println("登录成功");
                            setNewsMessage();
//                            Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(int i, String s) {

                            System.out.println("登录失败");
                        }

                        @Override
                        public void onProgress(int i, String s) {
                            System.out.println("正在登录");
                        }
                    });
                    break;
            }
        }
    };



    public void loginIM(){
        new Thread(){
            public void run(){
                //注册
                try {
                    EMClient.getInstance().createAccount(mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),mProvideViewSysUserPatientInfoAndRegion.getQrCode());
                    gHandler.sendEmptyMessage(1);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    gHandler.sendEmptyMessage(1);
                    System.out.println("~~~~~~~注册失败~~~~~~~~"+e.getDescription());
                }
            }
        }.start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化IM聊天界面
        gContext = getApplicationContext();
        //获取本地缓存
        getPersistence();
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(gContext, options);

        //注册
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if(mCallReceiver == null){
            mCallReceiver = new CallReceiver();
        }
        getApplicationContext().registerReceiver(mCallReceiver, callFilter);
        //初始化图片方法
        /**
         * 配置并初始化ImageLoader
         */
        if (Constant.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }
        initImageLoader(getApplicationContext());

        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        mImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.hztx)			        // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.hztx)	         // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.hztx)		        // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0))	// 设置成圆角图片
                .build();									// 创建配置过得DisplayImageOption对象
        saveIMNumInfo();
        getIMNumInfo();

    }

    /**
     * 保存用户信息
     */
    public void saveUserInfo() {
        //数据存储,以json字符串的格式保存
        m_persist 	= new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.putString("loginUserInfo",new Gson().toJson(mLoginUserInfo));
        m_persist.putString("viewSysUserDoctorInfoAndHospital",new Gson().toJson(mProvideViewSysUserPatientInfoAndRegion));
        m_persist.commit();
    }

    /**
     * 保存图文音视频通话限制信息
     */
    public void saveIMNumInfo() {
        //数据存储,以json字符串的格式保存
        m_persist 	= new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.putInt("messageNum",gMessageNum);
        m_persist.putLong("voiceTime",gVoiceTime);
        m_persist.putLong("vedioTime",gVedioTime);
        m_persist.commit();
    }
    //获取图文音视频通话限制信息
    public void getIMNumInfo() {
        m_persist 	= new SharedPreferences_DataSave(this, "JYKJDOCTER");
        gMessageNum 			    = m_persist.getInt("messageNum",0);
        gVoiceTime 			    = m_persist.getLong("voiceTime",0);
        gVedioTime 			    = m_persist.getLong("vedioTime",0);
        System.out.println(gMessageNum);
        System.out.println(gVoiceTime);
        System.out.println(gVedioTime);
        System.out.println(gVedioTime);
    }



    //清空数据
    public void cleanPersistence() {
        m_persist 	= new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.remove("loginUserInfo");
        m_persist.remove("viewSysUserDoctorInfoAndHospital");
        mLoginUserInfo.setUserPhone("");
        mLoginUserInfo.setUserPwd("");
        m_persist.commit();
    }

    //获取缓存数据
    public void getPersistence() {
        m_persist 	= new SharedPreferences_DataSave(this, "JYKJDOCTER");
        String userInfoLogin 			    = m_persist.getString("loginUserInfo","");
        String userInfoSuLogin 			    = m_persist.getString("viewSysUserDoctorInfoAndHospital","");
        mLoginUserInfo = new Gson().fromJson(userInfoLogin,UserInfo.class);
        try {
            mProvideViewSysUserPatientInfoAndRegion = new Gson().fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
        }catch (Exception e)
        {

        }
        System.out.println(mProvideViewSysUserPatientInfoAndRegion);
    }


    /**
     * 初始化ImageLoader
     * @param context
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * 设置新消息
     */
    public void setNewsMessage() {
        Map<String, EMConversation> conversationMap = EMClient.getInstance().chatManager().getAllConversations();
        List<String> userCodeList = new ArrayList<>();
        //遍历map中的键,获取用户Code列表
        for (String key : conversationMap.keySet()) {
            userCodeList.add(key);
        }

        for (int i = 0; i < userCodeList.size(); i++)
        {
            //获取未读消息数
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userCodeList.get(i));
            if (conversation != null)
                gNewMessageNum = conversation.getUnreadMsgCount();
            if (gNewMessageNum > 0)
                return;
        }

    }


    public static boolean isApplicationBroughtToBackground(final Context context) {
        //创建activity管理对象
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        //取出RunningTask栈
        List<ActivityManager.RunningTaskInfo> list =activityManager.getRunningTasks(1);
        //判断是否为空
        if (!list.isEmpty()){
            //取出运行在栈顶的任务进程
            ComponentName componentName = list.get(0).topActivity;
            //判断任务进程的包名是否和想要判断的程序包名相同
            if (componentName.getPackageName().equals(context.getPackageName())){
                //相同则说明你该程序运行在前台，则返回ture
                return true;
            }
        }
        //否则返回false
        return false;
    }

    /**
     * 设置环信网络状态
     */
    public void setNetConnectionStateHX() {
        if (gMainActivity != null)
            gMainActivity.setHXNetWorkState();
    }
}
