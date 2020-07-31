package www.patient.jykj_zxyl.activity.liveroom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import entity.liveroom.QueryRoomDetailCond;
import entity.liveroom.RoomDetailInfo;
import netService.HttpNetService;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.hyhd.NewLivePlayerActivity;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.StrUtils;

public class LiveroomDetailActivity extends AppCompatActivity {
    JYKJApplication mApp;
    Activity mActivity;
    Context mContext;
    ImageView liveroom_det_head_pic;
    TextView doctor_head_tit;
    TextView live_doctor_name;
    TextView live_doctor_education;
    TextView live_doctor_dep;
    RelativeLayout liveroom_share_holder;
    TextView det_live_tit;
    TextView det_room_key;
    TextView det_room_type;
    TextView det_room_watchnum;
    TextView det_live_time;
    Button go_liveroom_btn;
    private String detailCode;
    public ProgressDialog mDialogProgress = null;
    RoomDetailInfo mRoomDetailInfo = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailCode = StrUtils.defaultStr(getIntent().getStringExtra("detailCode"));
        mApp = (JYKJApplication)getApplication();
        mActivity = LiveroomDetailActivity.this;
        mContext = LiveroomDetailActivity.this;
        setContentView(R.layout.activity_liveroom_detail);
        initview();
    }

    void initview(){
        liveroom_det_head_pic = findViewById(R.id.liveroom_det_head_pic);
        doctor_head_tit = findViewById(R.id.doctor_head_tit);
        live_doctor_name = findViewById(R.id.live_doctor_name);
        live_doctor_education = findViewById(R.id.live_doctor_education);
        live_doctor_dep = findViewById(R.id.live_doctor_dep);
        liveroom_share_holder = findViewById(R.id.liveroom_share_holder);
        det_live_tit = findViewById(R.id.det_live_tit);
        det_room_key = findViewById(R.id.det_room_key);
        det_room_type = findViewById(R.id.det_room_type);
        det_room_watchnum = findViewById(R.id.det_room_watchnum);
        det_live_time = findViewById(R.id.det_live_time);
        go_liveroom_btn = findViewById(R.id.go_liveroom_btn);
    }

    class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(null!=mRoomDetailInfo){
                Intent parint = new Intent(mActivity, NewLivePlayerActivity.class);
                parint.putExtra("detailCode",mRoomDetailInfo.getDetailsCode());
            }
        }
    }

    void loadData(){
        getProgressBar("加载数据","加载数据中心，请稍后...");
        QueryRoomDetailCond queryCond = new QueryRoomDetailCond();
        queryCond.setDetailsCode(detailCode);
        queryCond.setLoginUserPosition(mApp.loginDoctorPosition);
        queryCond.setOperUserCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        queryCond.setOperUserName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        queryCond.setRequestClientType("1");
        LoadDataTask loadDataTask = new LoadDataTask(queryCond);
        loadDataTask.execute();
    }

    class LoadDataTask extends AsyncTask<Void, Void, RoomDetailInfo> {
        QueryRoomDetailCond queryCond;
        LoadDataTask(QueryRoomDetailCond queryCond){
            this.queryCond = queryCond;
        }

        @Override
        protected RoomDetailInfo doInBackground(Void... voids) {
            RoomDetailInfo retinfo = null;
            try{
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryCond),"https://www.jiuyihtn.com:41041/broadcastLiveDataControlle/getLiveRoomDetailsByDetailsCode");
                NetRetEntity retEntity = JSON.parseObject(retstr, NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retinfo = JSON.parseObject(retEntity.getResJsonData(), RoomDetailInfo.class);
                }
            }catch (Exception ex){

            }
            return retinfo;
        }

        @Override
        protected void onPostExecute(RoomDetailInfo roomDetailInfo) {
            if(null!=roomDetailInfo){
                mRoomDetailInfo = roomDetailInfo;
                if(StrUtils.defaultStr(roomDetailInfo.getBroadcastUserLogoUrl()).length()>0){
                    Glide.with(mContext).load(roomDetailInfo.getBroadcastUserLogoUrl()).into(liveroom_det_head_pic);
                }
                doctor_head_tit.setText(StrUtils.defaultStr(roomDetailInfo.getBroadcastUserTitleName()));
                live_doctor_name.setText(StrUtils.defaultStr(roomDetailInfo.getBroadcastUserName()));
                live_doctor_education.setText(StrUtils.defaultStr(roomDetailInfo.getTitleMainShow()));
                live_doctor_dep.setText(StrUtils.defaultStr(roomDetailInfo.getTitleDetailShow()));
                det_live_tit.setText(StrUtils.defaultStr(roomDetailInfo.getBroadcastTitle()));
                det_room_key.setText(StrUtils.defaultStr(roomDetailInfo.getAttrName()));
                det_room_type.setText(StrUtils.defaultStr(roomDetailInfo.getClassName()));
                det_live_time.setText("");
            }
        }
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
}
