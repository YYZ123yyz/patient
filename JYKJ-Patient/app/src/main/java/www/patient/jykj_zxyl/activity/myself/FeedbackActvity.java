package www.patient.jykj_zxyl.activity.myself;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.allin.commlibrary.keyboard.KeyboardUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;

import entity.mySelf.usercenter.FeedBackInfo;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.INetAddress;
import www.patient.jykj_zxyl.util.StrUtils;

public class FeedbackActvity extends AppCompatActivity {
    TextView sub_feed;
    EditText ed_feed_telephone;
    TextView tv_question_time;
    EditText tv_suggestion;
    JYKJApplication mApp;
    Context mContext;
    private TimePickerView startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (JYKJApplication) getApplication();
        mContext = FeedbackActvity.this;
        setContentView(R.layout.activity_myself_setting_feedback);
        ActivityUtil.setStatusBarMain(this);
        sub_feed = findViewById(R.id.sub_feed);
        tv_suggestion = findViewById(R.id.tv_suggestion);
        tv_question_time = findViewById(R.id.tv_question_time);
        ed_feed_telephone = findViewById(R.id.ed_feed_telephone);
        tv_question_time.setOnClickListener(new ButtonClick());
        findViewById(R.id.back).setOnClickListener(new ButtonClick());
        sub_feed.setOnClickListener(new ButtonClick());
        tv_question_time.setOnClickListener(new ButtonClick());
    }

    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sub_feed:
                    subData();

                    break;
                case R.id.tv_question_time:
                    KeyboardUtils.hideSoftInput(FeedbackActvity.this);
                    startTime = new TimePickerBuilder(mContext, (date, v1) -> tv_question_time.setText(DateUtils.fomrDateSeflFormat(date, "yyyy-MM-dd"))).build();
                    startTime.show();
                    break;
                case R.id.back:
                    finish();
                    break;
            }
        }
    }

    void subData() {
        String suggestion = StrUtils.defaultStr(tv_suggestion.getText());
        String falttime = StrUtils.defaultStr(tv_question_time.getText());
        String link = StrUtils.defaultStr(ed_feed_telephone.getText());
        if (suggestion.length() == 0) {
            Toast.makeText(mContext, "请填写您的建议", Toast.LENGTH_SHORT).show();
            return;
        }
        if (falttime.length() == 0) {
            Toast.makeText(mContext, "请选择故障时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (link.length() == 0) {
            Toast.makeText(mContext, "请填写您的联系方式", Toast.LENGTH_SHORT).show();
            return;
        }
        FeedBackInfo subbean = new FeedBackInfo();
        subbean.setContactMode(link);
        subbean.setFaultDate(falttime);
        subbean.setFeedbackContent(suggestion);
        subbean.setLoginPatientPosition(mApp.loginDoctorPosition);
        subbean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        subbean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        subbean.setRequestClientType("1");
        SubDataTask subDataTask = new SubDataTask(subbean);
        subDataTask.execute();
    }

    class SubDataTask extends AsyncTask<Void, Void, Boolean> {
        FeedBackInfo subbean;
        String retmsg = "";

        SubDataTask(FeedBackInfo subbean) {
            this.subbean = subbean;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                String subjson = new Gson().toJson(subbean);
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo=" + subjson, Constant.SERVICEURL + INetAddress.SUB_COMMEND_URL);
                NetRetEntity retEntity = JSON.parseObject(retstr, NetRetEntity.class);
                if (1 == retEntity.getResCode()) {
                    retmsg = retEntity.getResMsg();
                    return true;
                } else {
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
            if (aBoolean) {

                Toast.makeText(mContext, retmsg, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(mContext, retmsg, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
