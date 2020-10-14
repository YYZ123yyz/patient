package com.hyphenate.easeui.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.entity.ProvideViewSysUserPatientInfoAndRegion;
import com.hyphenate.easeui.hyhd.model.Constant;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import com.hyphenate.easeui.order.ProvideInteractPatientInterrogation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import www.patient.jykj_zxyl.base.base_utils.SharedPreferences_DataSave;


public class WZXXActivity extends AppCompatActivity implements View.OnClickListener {
   // private JYKJApplication mApp;
   private String mNetRetStr;                 //返回字符串
    private String mGetImgNetRetStr;                 //获取图片返回字符串
    private Handler mHandler;
    private ImageView iv_sex_man, iv_sex_woman;
    private LinearLayout mBack;
    private TextView mCommit;
    private EditText mYSXM;
    private EditText tv_activityHZZL_userSFXJ;
    private EditText tv_age;
    private TextView tv_time;
    private TextView tv_history_value;
    private TextView tv_activityHZZL_userName;
    private TextView tv_activityHZZL_clyq;
    private TextView tv_activityHZZL_clfs;
    private Context mContext;
    private ProvideInteractPatientInterrogationParment mProvideInteractPatientInterrogationParment = new ProvideInteractPatientInterrogationParment();
    private ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;
    private NetRetEntity netRetEntity;
    private List<ProvideInteractPatientInterrogation> mProvideInteractPatientInterrogations;

    private ProvideInteractPatientInterrogation mProvideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();               //提交的问诊资料
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        SharedPreferences_DataSave jykjdocter = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        String userInfoSuLogin = jykjdocter.getString("viewSysUserDoctorInfoAndHospital", "");
        try {
            mProvideViewSysUserPatientInfoAndRegion = new Gson().fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
        } catch (Exception e) {

        }
        mContext = this;
        initView();
        initHandler();
    }

    /**
     *  请求
     */

    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                      /*  netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            mProvideInteractPatientInterrogation = JSON.parseObject(netRetEntity.getResJsonData(), ProvideInteractPatientInterrogation.class);
                            mProvideInteractPatientInterrogationParment.setInterrogationId(mProvideInteractPatientInterrogation.getInterrogationId());
                            mProvideInteractPatientInterrogationParment.setOrderCode(mProvideInteractPatientInterrogation.getOrderCode());
                            mProvideInteractPatientInterrogationParment.setTreatmentType(mProvideInteractPatientInterrogation.getTreatmentType());
                            mProvideInteractPatientInterrogationParment.setImgCode(mProvideInteractPatientInterrogation.getImgCode());
                            mProvideInteractPatientInterrogationParment.setDoctorCode(mProvideInteractPatientInterrogation.getDoctorCode());
                            mProvideInteractPatientInterrogationParment.setDoctorName(mProvideInteractPatientInterrogation.getDoctorName());
                            mProvideInteractPatientInterrogationParment.setPatientCode(mProvideInteractPatientInterrogation.getPatientCode());
                            mProvideInteractPatientInterrogationParment.setPatientName(mProvideInteractPatientInterrogation.getPatientName());
                            mProvideInteractPatientInterrogationParment.setPatientLinkPhone(mProvideInteractPatientInterrogation.getPatientLinkPhone());
                            mProvideInteractPatientInterrogationParment.setGender(mProvideInteractPatientInterrogation.getGender());
                            mProvideInteractPatientInterrogationParment.setBirthday(mProvideInteractPatientInterrogation.getBirthday());
                            Date date = mProvideInteractPatientInterrogation.getBloodPressureAbnormalDate();
                            if (mProvideInteractPatientInterrogation.getBloodPressureAbnormalDate() != null)
                                mProvideInteractPatientInterrogationParment.setBloodPressureAbnormalDate(Util.dateToStrDate(mProvideInteractPatientInterrogation.getBloodPressureAbnormalDate()));
                            mProvideInteractPatientInterrogationParment.setHtnHistory(mProvideInteractPatientInterrogation.getHtnHistory());
                            mProvideInteractPatientInterrogationParment.setFlagFamilyHtn(mProvideInteractPatientInterrogation.getFlagFamilyHtn());
                            mProvideInteractPatientInterrogationParment.setHighPressureNum(mProvideInteractPatientInterrogation.getHighPressureNum());
                            mProvideInteractPatientInterrogationParment.setLowPressureNum(mProvideInteractPatientInterrogation.getLowPressureNum());
                            mProvideInteractPatientInterrogationParment.setHeartRateNum(mProvideInteractPatientInterrogation.getHeartRateNum());
                            mProvideInteractPatientInterrogationParment.setMeasureInstrument(mProvideInteractPatientInterrogation.getMeasureInstrument());
                            mProvideInteractPatientInterrogationParment.setMeasureInstrumentName(mProvideInteractPatientInterrogation.getMeasureInstrumentName());
                            mProvideInteractPatientInterrogationParment.setStateOfIllness(mProvideInteractPatientInterrogation.getStateOfIllness());
                            mProvideInteractPatientInterrogationParment.setFlagUseState(mProvideInteractPatientInterrogation.getFlagUseState());
                            mProvideInteractPatientInterrogationParment.setFlagHtnHistory(mProvideInteractPatientInterrogation.getFlagHtnHistory());
                            mProvideInteractPatientInterrogationParment.setTreatmentTypeName(mProvideInteractPatientInterrogation.getTreatmentTypeName());
                            mProvideInteractPatientInterrogationParment.setMeasureMode(mProvideInteractPatientInterrogation.getMeasureMode());
                            mProvideInteractPatientInterrogationParment.setMeasureModeName(mProvideInteractPatientInterrogation.getMeasureModeName());
                            //设置数据
                            setLayoutDate();

                        } else {

                        }*/
                        break;
                }
            }
        };
    }

    /**
     * 设置数据
     */
    private void setLayoutDate() {

    }

    private void initView() {
        mBack = findViewById(R.id.iv_back_left);
        mCommit = findViewById(R.id.tv_commit);
        iv_sex_man = findViewById(R.id.iv_sex_man);
        iv_sex_woman = findViewById(R.id.iv_sex_woman);
        //姓名
        mYSXM = findViewById(R.id.tv_activityHZZL_userTZ);
        mYSXM.setCursorVisible(false);
        //手机号
        tv_activityHZZL_userSFXJ = findViewById(R.id.tv_activityHZZL_userSFXJ);
        tv_activityHZZL_userSFXJ.setCursorVisible(false);
        //年龄
        tv_age = findViewById(R.id.tv_age);
        //最早日期
        tv_time = findViewById(R.id.tv_time);
        //高血压病史
        tv_history_value = findViewById(R.id.tv_history_value);
        //是否有家族史
        tv_activityHZZL_userName = findViewById(R.id.tv_activityHZZL_userName);
        //测量仪器
        tv_activityHZZL_clyq = findViewById(R.id.tv_activityHZZL_clyq);
        //测量方式
        tv_activityHZZL_clfs = findViewById(R.id.tv_activityHZZL_clfs);
        addClick();
    }



    /**
     * 下一步
     */
    private void commit() {

    }

    private void addClick() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });
        iv_sex_man.setOnClickListener(this);
        iv_sex_woman.setOnClickListener(this);
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBirthDayChoiceDialog();
            }
        });
        tv_history_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDoctorTitleDialog();
            }
        });
        tv_activityHZZL_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHistoryTitleDialog();
            }
        });
        tv_activityHZZL_clyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCLYQDialog();
            }
        });
        tv_activityHZZL_clfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCLFSDialog();
            }
        });
    }

    /**
     * 选择测量方式
     */
    private void showCLFSDialog() {
     /*   String[] items = new String[mApp.gBasicDate.get(10007).size()];
        for (int i = 0; i < mApp.gBasicDate.get(10007).size(); i++) {
            items[i] = mApp.gBasicDate.get(10007).get(i).getAttrName();
        }
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                tv_activityHZZL_clfs.setText(mApp.gBasicDate.get(10007).get(which).getAttrName());
                mProvideInteractPatientInterrogationParment.setMeasureMode(mApp.gBasicDate.get(10007).get(which).getBaseCode());
                mProvideInteractPatientInterrogationParment.setMeasureModeName(mApp.gBasicDate.get(10007).get(which).getAttrName());
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示*/
    }

    /**
     * 选择测量仪器
     */
    private void showCLYQDialog() {
       /* String[] items = new String[mApp.gBasicDate.get(10006).size()];
        for (int i = 0; i < mApp.gBasicDate.get(10006).size(); i++) {
            items[i] = mApp.gBasicDate.get(10006).get(i).getAttrName();
        }
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                tv_activityHZZL_clyq.setText(mApp.gBasicDate.get(10006).get(which).getAttrName());
                mProvideInteractPatientInterrogationParment.setMeasureInstrument(mApp.gBasicDate.get(10006).get(which).getBaseCode());
                mProvideInteractPatientInterrogationParment.setMeasureInstrumentName(mApp.gBasicDate.get(10006).get(which).getAttrName());
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示*/
    }


    /**
     * 选择生日
     */
    private void showBirthDayChoiceDialog() {
        Calendar nowdate = Calendar.getInstance();
        int mYear = nowdate.get(Calendar.YEAR);
        int mMonth = nowdate.get(Calendar.MONTH);
        int mDay = nowdate.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(mContext, onDateSetListener, mYear, mMonth, mDay).show();
    }

    /**
     * 日期
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear + 1;
            int mDay = dayOfMonth;
            String month = "";
            String day = "";
//            TextView date_textview = (TextView) findViewById(R.id.changebirth_textview);
            String days;
            if (mMonth < 10)
                month = "0" + mMonth;
            else
                month = mMonth + "";
            if (mDay < 10)
                day = "0" + mDay;
            else
                day = mDay + "";
            tv_time.setText(mYear + "-" + month + "-" + day);
            mProvideInteractPatientInterrogationParment.setBloodPressureAbnormalDate(mYear + "-" + month + "-" + day);
            System.out.println();
        }
    };

    /**
     * 最早血压异常时间
     */
    private void showDoctorTitleDialog() {
        String[] doctorTitleName = new String[]{"否", "是"};// 家族内是否有高血压病史
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(doctorTitleName, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                tv_history_value.setText(doctorTitleName[which]);
                mProvideInteractPatientInterrogationParment.setFlagFamilyHtn(which);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    /**
     * 家族史
     */
    private void showHistoryTitleDialog() {
        String[] doctorTitleName = new String[]{"否", "是"};// 家族内是否有高血压病史
        android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(doctorTitleName, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                tv_activityHZZL_userName.setText(doctorTitleName[which]);
                mProvideInteractPatientInterrogationParment.setFlagFamilyHtn(which);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_sex_man) {
            //    mProvideViewSysUserPatientInfoAndRegion.setGender(1);
            iv_sex_man.setBackgroundResource(R.mipmap.sex_choiceno);
            iv_sex_woman.setBackgroundResource(R.mipmap.sex_nochoice);
        } else if (id == R.id.iv_sex_woman) {
            //     mProvideViewSysUserPatientInfoAndRegion.setGender(2);
            iv_sex_man.setBackgroundResource(R.mipmap.sex_nochoice);
            iv_sex_woman.setBackgroundResource(R.mipmap.sex_choiceno);
        }
    }

    /**
     * 获取问诊资料
     */
    private void getInteractPatientInterrogationResNewest() {
        ProvideInteractPatientInterrogation provideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();
        provideInteractPatientInterrogation.setLoginPatientPosition(mProvideViewSysUserPatientInfoAndRegion.getLoginPatientPosition());
        provideInteractPatientInterrogation.setOperPatientCode(mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractPatientInterrogation.setOperPatientName(mProvideViewSysUserPatientInfoAndRegion.getUserName());
//        provideBasicsImg.setOrderCode(getInteractOrderCodeGenerate.getOrderCode());
       // provideInteractPatientInterrogation.setRequestClientType(mOperaType);

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideInteractPatientInterrogation);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "patientInteractDataControlle/getInteractPatientInterrogationResNewest");
                    String string01 = Constant.SERVICEURL + "msgDataControlle/searchMsgPushReminderAllCount";
                    System.out.println(string + string01);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mGetImgNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

}