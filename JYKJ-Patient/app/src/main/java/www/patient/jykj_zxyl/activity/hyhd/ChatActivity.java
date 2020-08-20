package www.patient.jykj_zxyl.activity.hyhd;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseTitleBar;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.ActivityUtil;

/**
 * 聊天界面
 */
public class ChatActivity extends AppCompatActivity {

    private TextView mPhoneLogin;                //手机号登录
    private TextView mUseRegist;                 //用户注册
    private Button mLogin;                     //登录
    private Context mContext;
    private ChatActivity mActivity;
    private EaseTitleBar titleBar;
    private EaseChatMessageList messageList;
    private EaseChatInputMenu inputMenu;
    private JYKJApplication mApp;

    private String doctorUrl;
    private String patientUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBar(mActivity);
        String chatType = getIntent().getStringExtra("chatType");
//        initLayout();
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        String userCode = getIntent().getStringExtra("userCode");
        String userName = getIntent().getStringExtra("userName");
        String loginDoctorPosition = getIntent().getStringExtra("loginDoctorPosition");
        String operDoctorCode = getIntent().getStringExtra("operDoctorCode");
        String operDoctorName = getIntent().getStringExtra("operDoctorName");
        String orderCode = getIntent().getStringExtra("orderCode");

        //头像
        doctorUrl = getIntent().getStringExtra("doctorUrl");
        patientUrl = mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl();


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

        args.putString("doctorUrl", doctorUrl);
        args.putString("userUrl", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
//        args.putString("userName", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        args.putInt(EaseConstant.EXTRA_MESSAGE_NUM, getIntent().getIntExtra(EaseConstant.EXTRA_MESSAGE_NUM, 0));
        args.putLong(EaseConstant.EXTRA_VOICE_NUM, getIntent().getIntExtra(EaseConstant.EXTRA_VOICE_NUM, 0));
        args.putLong(EaseConstant.EXTRA_VEDIO_NUM, getIntent().getIntExtra(EaseConstant.EXTRA_VEDIO_NUM, 0));
        args.putString("chatType", chatType);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        titleBar = (EaseTitleBar) this.findViewById(R.id.title_bar);
        titleBar.setTitle(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
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


}
