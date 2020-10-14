package www.patient.jykj_zxyl.activity.myself;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import entity.mySelf.usercenter.FeedBackInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.INetAddress;
import www.patient.jykj_zxyl.util.StrUtils;

import java.util.Date;
import java.util.HashMap;

public class FeedbackActvity extends AppCompatActivity {
    TextView sub_feed;
    EditText ed_feed_telephone;
    TextView tv_question_time;
    EditText tv_suggestion;
    JYKJApplication mApp;
    Context mContext;
    private TimePickerView startTime;
    private String mNetLoginRetStr;
    private NetRetEntity netRetEntity;
    private Handler mHandler;
    private String suggestion;
    private String falttime;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (JYKJApplication)getApplication();
        mContext = FeedbackActvity.this;
       setContentView(R.layout.activity_myself_setting_feedback);
        sub_feed = findViewById(R.id.sub_feed);
        tv_suggestion = findViewById(R.id.tv_suggestion);
        tv_question_time = findViewById(R.id.tv_question_time);
        ed_feed_telephone = findViewById(R.id.ed_feed_telephone);
        initView();

        initHandler();
    }

    private void initView() {
        tv_question_time.setOnClickListener(new ButtonClick());
        findViewById(R.id.back).setOnClickListener(new ButtonClick());
        sub_feed.setOnClickListener(new ButtonClick());
        tv_question_time.setOnClickListener(new ButtonClick());
    }

    class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sub_feed:
                    subData();
                break;
                case R.id.tv_question_time:
                    startTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            tv_question_time.setText(DateUtils.fomrDateSeflFormat(date,"yyyy-MM-dd"));
                        }
                    }).build();
                    startTime.show();
                    break;
                case R.id.back:
                    finish();
                    break;
            }
        }
    }

    /**
     * 提交
     */
    private void subData(){
        suggestion = tv_suggestion.getText().toString();
        falttime = tv_question_time.getText().toString();
        link = ed_feed_telephone.getText().toString();
        if(TextUtils.isEmpty(suggestion)){
            Toast.makeText(mContext,"请填写您的建议",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(falttime)){
            Toast.makeText(mContext,"请选择故障时间",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(link)){
            Toast.makeText(mContext,"请填写您的联系方式",Toast.LENGTH_SHORT).show();
            return;
        }
       /* FeedBackInfo subbean = new FeedBackInfo();
        subbean.setContactMode(link);
        subbean.setFaultDate(falttime);
        subbean.setFeedbackContent(suggestion);
        subbean.setLoginPatientPosition(mApp.loginDoctorPosition);
        subbean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        subbean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        subbean.setRequestClientType("1");
        SubDataTask subDataTask = new SubDataTask(subbean);
        subDataTask.execute();*/
        HashMap<String, String> map = new HashMap<>();
        map.put("loginPatientPosition", "108.93425^34.23053");
        map.put("requestClientType", "1");
        map.put("operPatientCode", mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        map.put("operPatientName",mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        map.put("feedbackContent",suggestion);
        map.put("faultDate",falttime);
        map.put("contactMode",link);

        new Thread() {
            public void run() {
                try {
                    mNetLoginRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), Constant.SERVICEURL + "/patientPersonalSetControlle/operSubmitBasicsSystemFeedback");
                    Log.e("tag", "j建议 "+mNetLoginRetStr );
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetLoginRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }
                netRetEntity = JSON.parseObject(mNetLoginRetStr, NetRetEntity.class);

                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        if(netRetEntity.getResCode()==1){
                            Toast.makeText(FeedbackActvity.this,"提交成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Toast.makeText(FeedbackActvity.this,"提交失败",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        };
    }

    class SubDataTask extends AsyncTask<Void,Void,Boolean>{
        FeedBackInfo subbean;
        String retmsg = "";
        SubDataTask(FeedBackInfo subbean){
            this.subbean = subbean;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String subjson = new Gson().toJson(subbean);
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+subjson, Constant.SERVICEURL+ INetAddress.SUB_COMMEND_URL);
                Log.e("TAG", "意见反馈: "+retstr );
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode()){
                    retmsg = retEntity.getResMsg();
                    return true;
                }else{
                    retmsg = retEntity.getResMsg();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Toast.makeText(mContext,retmsg,Toast.LENGTH_SHORT);
                finish();
            }else{
                Toast.makeText(mContext,retmsg,Toast.LENGTH_SHORT);
            }
        }
    }
}
