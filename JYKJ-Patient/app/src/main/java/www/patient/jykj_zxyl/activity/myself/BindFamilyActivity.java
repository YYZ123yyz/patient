package www.patient.jykj_zxyl.activity.myself;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.basicDate.ProvideBasicsDomain;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalDepartment;
import entity.basicDate.ProvideHospitalInfo;
import entity.basicDate.ProvideViewSysUserPatientInfoAndRegion;
import entity.mySelf.ProvidePatientUrgentContacts;
import entity.mySelf.conditions.QueryContactCond;
import entity.mySelf.conditions.UpContactBean;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import org.w3c.dom.Text;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.*;

/**
 * 个人中心 ==>绑定亲友
 */
public class BindFamilyActivity extends AppCompatActivity {

    private                 Context                     mContext;
    private BindFamilyActivity mActivity;
    public                  ProgressDialog              mDialogProgress =null;

    private                 JYKJApplication             mApp;

    private                 String                      mNetRetStr;                 //返回字符串



    private                 Handler                     mHandler;

    private RelativeLayout          ri_back;
    private EditText tv_activityUserCenter_userNameText;
    private EditText tv_activityUserCenter_userNikeNameText;
private QueryFamilyTask queryFamilyTask;
private MaintainFamilyTask maintainFamilyTask;
private String opetype = "0";


//    private                 ProvideViewSysUserDoctorInfoAndHospital  mProvideViewSysUserDoctorInfoAndHospital = new ProvideViewSysUserDoctorInfoAndHospital();  //医生信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myself_bindfamily);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(mActivity);
        initLayout();
        initHandler();
        loadContacts();
    }

    void loadContacts(){
        QueryContactCond querycond = new QueryContactCond();
        querycond.setLoginPatientPosition(mApp.loginDoctorPosition);
        querycond.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        querycond.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        querycond.setRequestClientType("3");
        queryFamilyTask = new QueryFamilyTask(querycond);
        queryFamilyTask.execute();
    }


    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        break;
                    case 1:
                        cacerProgress();
                        setLayoutDate();
                        break;
                    case 3:

                    case 4:
                        cacerProgress();
                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
                        break;
                }
            }
        };
    }



    /**
     * 设置布局显示
     */
    private void setLayoutDate() {

    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        ri_back = (RelativeLayout)this.findViewById(R.id.ri_back);
        tv_activityUserCenter_userNikeNameText = findViewById(R.id.tv_activityUserCenter_userNikeNameText);
        tv_activityUserCenter_userNameText = findViewById(R.id.tv_activityUserCenter_userNameText);
        ri_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv_activityUserCenter_commit = findViewById(R.id.tv_activityUserCenter_commit);
        ButtonClick thclk = new ButtonClick();
        tv_activityUserCenter_commit.setOnClickListener(thclk);
    }


    /**
     * 点击事件
     */
    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.tv_activityUserCenter_commit:
                    commit();
                    break;
            }
        }
    }


    void commit(){
        boolean hasinputonephone = false;
        String onephone = String.valueOf(tv_activityUserCenter_userNameText.getText());
        String twopgone = String.valueOf(tv_activityUserCenter_userNikeNameText.getText());
        if(!TextUtils.isEmpty(onephone)) {
            if (RegrexUtil.isMobilePhone(onephone) || RegrexUtil.isLinePhone(onephone)) {
                hasinputonephone = true;
            }else{
                Toast.makeText(mContext,"请输入正确的电话号码",Toast.LENGTH_SHORT);
                return;
            }
        }
        if(!TextUtils.isEmpty(twopgone)) {
            if (RegrexUtil.isMobilePhone(twopgone) || RegrexUtil.isLinePhone(twopgone)) {
                hasinputonephone = true;
            }else{
                Toast.makeText(mContext,"请输入正确的电话号码",Toast.LENGTH_SHORT);
                return;
            }

        }
        if(hasinputonephone){
            UpContactBean upbean = new UpContactBean();
            upbean.setContactsId(opetype);
            upbean.setContactsPhone1(onephone);
            upbean.setContactsPhone2(twopgone);
            upbean.setLoginPatientPosition(mApp.loginDoctorPosition);
            upbean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
            upbean.setRequestClientType("1");
            maintainFamilyTask = new MaintainFamilyTask(upbean);
            maintainFamilyTask.execute();
        }else{
            Toast.makeText(mContext,"请输入亲友电话",Toast.LENGTH_SHORT);
            return;
        }
    }

    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(mContext);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }


    class QueryFamilyTask extends AsyncTask<Void,Void, ProvidePatientUrgentContacts>{
        QueryContactCond querycond;
        QueryFamilyTask(QueryContactCond querycond){
            this.querycond = querycond;
        }
        @Override
        protected ProvidePatientUrgentContacts doInBackground(Void... voids) {
            ProvidePatientUrgentContacts retbean = null;
            try {
                String queFaimlys = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(querycond),Constant.SERVICEURL+ INetAddress.QUERY_CONTACT_URL);
                if(!TextUtils.isEmpty(queFaimlys)){
                    NetRetEntity retnetbean = JSON.parseObject(queFaimlys,NetRetEntity.class);
                    if(1==retnetbean.getResCode() && StrUtils.defaultStr(retnetbean.getResJsonData()).length()>3){
                        retbean = JSON.parseObject(retnetbean.getResJsonData(),ProvidePatientUrgentContacts.class);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return retbean;
        }

        @Override
        protected void onPostExecute(ProvidePatientUrgentContacts providePatientUrgentContacts) {
            if(null!=providePatientUrgentContacts){
                opetype = "1";
                tv_activityUserCenter_userNameText.setText(providePatientUrgentContacts.getContactsPhone1());
                tv_activityUserCenter_userNikeNameText.setText(providePatientUrgentContacts.getContactsPhone2());

            }
        }
    }

    class MaintainFamilyTask extends AsyncTask<Void,Void,Boolean>{
        UpContactBean upcondbean;
        String repmsg = "";
        MaintainFamilyTask(UpContactBean upcondbean){
            this.upcondbean = upcondbean;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean retbool = false;
            try {
                String transtr = new Gson().toJson(upcondbean);
                String retmaintaincontstr = HttpNetService.urlConnectionService("jsonDataInfo="+transtr,Constant.SERVICEURL+INetAddress.MATAIN_CONTACT_URL);
                NetRetEntity retnetbean = JSON.parseObject(retmaintaincontstr,NetRetEntity.class);
                if(null!=retnetbean && 1==retnetbean.getResCode()){
                    repmsg = "保存成功";
                    retbool = true;
                }else{
                    repmsg = retnetbean.getResMsg();
                    retbool = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                repmsg = "保存失败";
                retbool = false;
            }

            return retbool;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Toast.makeText(mContext,repmsg,Toast.LENGTH_SHORT);
                finish();
            }else {
                Toast.makeText(mContext,repmsg,Toast.LENGTH_SHORT);
            }
        }
    }

}
