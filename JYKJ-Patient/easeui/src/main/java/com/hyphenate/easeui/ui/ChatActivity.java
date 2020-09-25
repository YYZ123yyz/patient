package com.hyphenate.easeui.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.allin.commlibrary.preferences.SavePreferences;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.contract.CheckContract;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.entity.ProvideViewSysUserPatientInfoAndRegion;
import com.hyphenate.easeui.presenter.CheckPresenter;
import com.hyphenate.easeui.utils.ActivityUtil;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseTitleBar;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.CheckNumBean;
import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_db.DbManager;
import www.patient.jykj_zxyl.base.base_db.entity.CheckDoctorNumEntity;
import www.patient.jykj_zxyl.base.base_db.entity.CheckNumEntity;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;
import www.patient.jykj_zxyl.base.base_utils.SharedPreferences_DataSave;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;


/**
 * 聊天界面
 */
public class ChatActivity extends AbstractMvpBaseActivity<CheckContract.View, CheckPresenter>
        implements CheckContract.View {

    private TextView mPhoneLogin;                //手机号登录
    private TextView mUseRegist;                 //用户注册
    private Button mLogin;                     //登录
    private Context mContext;
    private ChatActivity mActivity;
    private EaseTitleBar titleBar;
    private EaseChatMessageList messageList;
    private EaseChatInputMenu inputMenu;
    private ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;
    private String doctorUrl;
    private String patientUrl;
    private String doctorType;//医生类型
    private OrderMessage orderMessage;
    private EaseChatFragment chatFragment;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getCheckNum(RetrofitUtil.encodeParam(getParamsHashMap(0)));

    }

    @Override
    protected void initView() {
        super.initView();

    }

    @NotNull
    private HashMap<String, Object> getParamsHashMap(int type) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginPatientPosition", "123123^123123213");
        paramMap.put("mainPatientName", "11111111");
        if (type ==0){
            paramMap.put("mainDoctorName", "11111");
            paramMap.put("mainPatientCode", "54f4ee7c30124539b29879aae61dc786");
            paramMap.put("mainDoctorCode", "250dda9c6ad544a4b6d16db7a2f1bcb6");
        }else {
            paramMap.put("orderCode", "123123123123123123123123");
            paramMap.put("useDuration",1);
            paramMap.put("mainPatientCode","54f4ee7c30124539b29879aae61dc786");
        }

        return paramMap;
    }

    @Override
    protected void onPause() {
        super.onPause();

        mPresenter.submitData(RetrofitUtil.encodeParam(getParamsHashMap(1)));
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        titleBar = (EaseTitleBar) this.findViewById(R.id.title_bar);
        titleBar.setTitle(mProvideViewSysUserPatientInfoAndRegion.getUserName());
        titleBar.setRightImageResource(R.drawable.ease_mm_title_remove);

        messageList = (EaseChatMessageList) this.findViewById(R.id.message_list);
        //初始化messagelist
//        messageList.init(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(), 1, null,doctorUrl,patientUrl);
//        //设置item里的控件的点击事件
//        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {
//
//            @Override
//            public void onUserAvatarClick(String username) {
//                //头像点击事件
//            }
//
//            @Override
//            public void onUserAvatarLongClick(String username) {
//
//            }
//
//            @Override
//            public void onMessageInProgress(EMMessage message) {
//
//            }
//
//
//            @Override
//            public void onBubbleLongClick(EMMessage message) {
//                //气泡框长按事件
//            }
//
//            @Override
//            public boolean onBubbleClick(EMMessage message) {
//                //气泡框点击事件，EaseUI有默认实现这个事件，如果需要覆盖，return值要返回true
//                return false;
//            }
//
//            @Override
//            public boolean onResendClick(EMMessage message) {
//                return false;
//            }
//        });
        //messageList.init(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(), 1, null,doctorUrl,patientUrl);
        //messageList.init();
        //设置item里的控件的点击事件
        messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                //头像点击事件
            }

            @Override
            public void onUserAvatarLongClick(String username) {

            }

            @Override
            public void onMessageInProgress(EMMessage message) {

            }


            @Override
            public void onBubbleLongClick(EMMessage message) {
                //气泡框长按事件
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                //气泡框点击事件，EaseUI有默认实现这个事件，如果需要覆盖，return值要返回true
                return false;
            }

            @Override
            public boolean onResendClick(EMMessage message) {
                return false;
            }
        });
//        //获取下拉刷新控件
//        swipeRefreshLayout = messageList.getSwipeRefreshLayout();
//        //刷新消息列表
//        messageList.refresh();
//        messageList.refreshSeekTo(position);
//        messageList.refreshSelectLast();

        inputMenu = (EaseChatInputMenu) this.findViewById(R.id.input_menu);
//注册底部菜单扩展栏item
//传入item对应的文字，图片及点击事件监听，extendMenuItemClickListener实现EaseChatExtendMenuItemClickListener
//        inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
//        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);

//初始化，此操作需放在registerExtendMenuItem后
        inputMenu.init();
//设置相关事件监听
        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {

            @Override
            public void onTyping(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onSendMessage(String content) {
                // 发送文本消息
//                sendTextMessage(content);
            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {

            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                ////把touch事件传入到EaseVoiceRecorderView 里进行录音
//                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {
//                    @Override
//                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
//                        // 发送语音消息
//                        sendVoiceMessage(voiceFilePath, voiceTimeLength);
//                    }
//                });
                return false;
            }
        });


    }

    @Override
    public void showLoading(int code) {
        showLoading("", null);
    }

    @Override
    public void hideLoading() {
        dismissLoading();
    }

    @Override
    public void getCheckNumSucess(CheckNumBean bean) {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        ActivityUtil.setStatusBarMain(this);
        ActivityStackManager.getInstance().add(this);
        mContext = this;
        mActivity = this;
        // ActivityUtil.setStatusBar(mActivity);
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            orderMessage = (OrderMessage) extras.getSerializable("orderMsg");
        }
        String chatType = getIntent().getStringExtra("chatType");
        doctorType = getIntent().getStringExtra("doctorType");
//        initLayout();
        //new出EaseChatFragment或其子类的实例
        chatFragment = new EaseChatFragment();
        String userCode = getIntent().getStringExtra("userCode");
        String userName = getIntent().getStringExtra("userName");
        String loginDoctorPosition = getIntent().getStringExtra("loginDoctorPosition");
        String operDoctorCode = getIntent().getStringExtra("operDoctorCode");
        String operDoctorName = getIntent().getStringExtra("operDoctorName");
        String orderCode = getIntent().getStringExtra("orderCode");
        //头像
        doctorUrl = getIntent().getStringExtra("doctorUrl");
        SharedPreferences_DataSave jykjdocter = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        String userInfoSuLogin = jykjdocter.getString("viewSysUserDoctorInfoAndHospital", "");
        try {
            mProvideViewSysUserPatientInfoAndRegion = new Gson().fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
        } catch (Exception e) {

        }

        patientUrl = mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl();


        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, userCode);
        args.putString(EaseConstant.EXTRA_USER_NAME, userName);
        args.putString("date", getIntent().getStringExtra("date"));
        args.putString("loginDoctorPosition", loginDoctorPosition);
        args.putString("operDoctorCode", operDoctorCode);
        args.putString("operDoctorName", operDoctorName);
        args.putString("orderCode", orderCode);
        args.putSerializable("orderMessage", orderMessage);

        args.putString("doctorUrl", doctorUrl);
        args.putString("userUrl", mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
//        args.putString("userName", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        args.putInt(EaseConstant.EXTRA_MESSAGE_NUM, getIntent().getIntExtra(EaseConstant.EXTRA_MESSAGE_NUM, 0));
        args.putLong(EaseConstant.EXTRA_VOICE_NUM, getIntent().getIntExtra(EaseConstant.EXTRA_VOICE_NUM, 0));
        args.putLong(EaseConstant.EXTRA_VEDIO_NUM, getIntent().getIntExtra(EaseConstant.EXTRA_VEDIO_NUM, 0));
        args.putString("chatType", chatType);
        args.putString("doctorType", doctorType);
        //检测图文次数
        args.putLong("reserveConfigStart",bean.getReserveConfigStart());//Long.valueOf("1601000647879")
        args.putLong("reserveConfigEnd",bean.getReserveConfigEnd());//Long.valueOf("1601019524000")
        args.putInt("allNum",bean.getSumDuration());
        args.putInt("sumDuration",bean.getSumDuration()-bean.getUseDuration());
        SPUtils.getInstance().put("checksum_duration",bean.getSumDuration());
        args.putInt("isReserveing",bean.getIsReserveing());

//        CheckNumEntity checkNumEntity1 = new CheckNumEntity();
//        checkNumEntity1.setUsedNum(6);
//        checkNumEntity1.setId(Long.valueOf(1002));
//        checkNumEntity1.setUserId("1003");
//        DbManager.getInstance().getCheckNumEntityService().inSeartData(checkNumEntity1);
        CheckDoctorNumEntity checkDoctorNumEntity = new CheckDoctorNumEntity();
        checkDoctorNumEntity.setDocId(operDoctorName);
        checkDoctorNumEntity.setUsedNum(bean.getSumDuration()-bean.getUseDuration());
        DbManager.getInstance().getCheckDocNumEntityService().inSeartData(checkDoctorNumEntity);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        SavePreferences.setData("isNewMsg", false);
    }

    @Override
    public void getDataFiled(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void submitDataSucess(String msg) {
//        ToastUtils.showShort(msg);
    }


    /**
     * 点击事件
     */
    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
