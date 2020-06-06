package www.patient.jykj_zxyl.activity.hzgl;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import entity.home.newsMessage.ProvideMsgPushReminder;
import entity.patientInfo.MsgPushModel;
import entity.patientInfo.PatientMsgInfo;
import entity.patientInfo.ProvideViewPatientLablePunchClockState;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.ChooseMsgAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.ChooseMsgAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.DateUtils;


/**
 * 患者管理（提醒患者）
 */
public class HZGLTXHZActivity extends AppCompatActivity {

    private Context mContext;
    private HZGLTXHZActivity mActivity;
    private ProvideViewPatientLablePunchClockState mData;
    private TextView tvName;
    private TextView tvContent;
    private Button btnSend;
    private TextView tvTime;
    private EditText tvTemplate;
    private TextView tvChoseTemplate;
    private JYKJApplication mApp;
    private String mNetRetStr;
    private Handler mHandler;
    private ProvideMsgPushReminder provideMsgPushReminder;//提醒患者最近提醒消息
    private List<MsgPushModel> msgPushModelList = new ArrayList<>();//消息模板
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragmenthzgl_txhz);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBar(mActivity);
        initLayout();
        initHandler();

    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        findViewById(R.id.ll_back).setOnClickListener(new ButtonClick());
        tvName = findViewById(R.id.tv_name);
        tvContent = findViewById(R.id.tv_content);
        btnSend = findViewById(R.id.btn_send);
        tvTime = findViewById(R.id.tv_time);
        tvTemplate = findViewById(R.id.tv_template);
        tvChoseTemplate = findViewById(R.id.tv_choose_template);
        btnSend.setOnClickListener(new ButtonClick());
        tvChoseTemplate.setOnClickListener(new ButtonClick());

        if (getIntent() != null) {
            mData = (ProvideViewPatientLablePunchClockState) getIntent().getSerializableExtra("patientLable");
        }

        String sex;
        if (mData.getGender() == 1) {
            sex = "男";
        } else {
            sex = "女";
        }
        String age = DateUtils.getAgeFromBirthDate(mData.getBirthday()) + "岁";
        tvName.setText(mData.getUserName() + " " + sex + " " + age);
        getMsgPushData();
        getChooseMsgModel();
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;
                case R.id.btn_send:
                    sendRemindMsg();
                    break;
                case R.id.tv_choose_template:
                    if (popupWindow == null) {
                        showChooseMsgPop();

                    }
                    break;

            }
        }
    }


    /**
     * 设置数据
     */
    private void getChooseMsgModel() {
        new Thread() {
            public void run() {
                try {
                    PatientMsgInfo patientMsgInfo = new PatientMsgInfo();
                    patientMsgInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    patientMsgInfo.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    patientMsgInfo.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    String str = new Gson().toJson(patientMsgInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "msgDataControlle/searchRemindPatientMsgResMsgPushModelList");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg(netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    List<MsgPushModel> list = JSON.parseArray(netRetEntity.getResJsonData(), MsgPushModel.class);
                    msgPushModelList.addAll(list);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(0);
                    return;
                }
                mHandler.sendEmptyMessage(3);
            }
        }.start();


    }

    private void getMsgPushData() {
        new Thread() {
            public void run() {
                try {
                    PatientMsgInfo patientMsgInfo = new PatientMsgInfo();
                    patientMsgInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    patientMsgInfo.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    patientMsgInfo.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    patientMsgInfo.setSearchPatientCode(mData.getPatientCode());
                    patientMsgInfo.setSearchPatientName(mData.getUserName());
                    String str = new Gson().toJson(patientMsgInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "msgDataControlle/searchRemindPatientMsgResLatelyMsgPush");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    provideMsgPushReminder = new Gson().fromJson(netRetEntity.getResJsonData(), ProvideMsgPushReminder.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg(netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(0);
                    return;
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();

    }

    private void sendRemindMsg() {
        new Thread() {
            public void run() {
                try {
                    PatientMsgInfo patientMsgInfo = new PatientMsgInfo();
                    patientMsgInfo.setLoginDoctorPosition(mApp.loginDoctorPosition);
                    patientMsgInfo.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    patientMsgInfo.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    patientMsgInfo.setReceivePatientCode(mData.getPatientCode());
                    patientMsgInfo.setReceivePatientName(mData.getUserName());
                    patientMsgInfo.setMsgRemindContent(tvTemplate.getText().toString());
                    String str = new Gson().toJson(patientMsgInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "msgDataControlle/operUpdRemindPatientMsgByMsgContent");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);

                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("提醒失败：" + netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }

                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(0);
                    return;
                }
                mHandler.sendEmptyMessage(2);
            }
        }.start();

    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:

                        break;
                    case 1:
                        tvTime.setText("最近提醒日期：" + provideMsgPushReminder.getMsgReadDate());
                        tvContent.setText(provideMsgPushReminder.getMsgContent());
                        break;
                    case 2:
                        NetRetEntity retEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (retEntity.getResCode() == 0) {
                            Toast.makeText(mContext, retEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(mContext, retEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 3:


                        break;
                }
            }
        };
    }

    ListView listMsg;
    ChooseMsgAdapter adapter;

    private void showChooseMsgPop() {
        popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_chose_msg, null);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        listMsg = view.findViewById(R.id.lv_msg);
        adapter = new ChooseMsgAdapter(HZGLTXHZActivity.this, msgPushModelList);
        listMsg.setAdapter(adapter);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        listMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvTemplate.setText(msgPushModelList.get(position).getMsgContent());
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow = null;
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });


    }
}
