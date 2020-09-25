package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.DateUtills;
import com.hyphenate.easeui.utils.EaseSmileUtils;

import java.util.List;

import www.patient.jykj_zxyl.base.base_utils.LogUtils;

public class EaseChatRowText extends EaseChatRow {

    private TextView contentView;
    private LinearLayout linNum;
    private TextView allNum;
    private TextView remainNum;

    public EaseChatRowText(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
    }

    @Override
    protected void onFindViewById() {
        contentView = (TextView) findViewById(R.id.tv_chatcontent);
        linNum = (LinearLayout) findViewById(R.id.lin_num);
        allNum = (TextView) findViewById(R.id.all_num);
        remainNum = (TextView) findViewById(R.id.remain_num);
    }

    @Override
    public void onSetUpView() {
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
        // 设置内容
        contentView.setText(span, BufferType.SPANNABLE);
        boolean isReserveing = message.getBooleanAttribute("isReserveing", false);
        if (message.direct() == EMMessage.Direct.SEND && isReserveing) {
            long msgTime = message.getMsgTime();
            long reserveConfigStart = message.getLongAttribute("reserveConfigStart", 0);
            long reserveConfigEnd = message.getLongAttribute("reserveConfigEnd", 0);
            LogUtils.e("消息时间  "+msgTime);
            LogUtils.e("预约开始  "+reserveConfigStart);
            LogUtils.e("预约结束  "+reserveConfigEnd);

            linNum.setVisibility((msgTime > reserveConfigStart && reserveConfigEnd > msgTime) ? VISIBLE : GONE);


        }else {
            linNum.setVisibility(GONE);
        }
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress();
                break;
        }
//        int num = msg.getIntAttribute("num", 0);
        boolean isReserveing = message.getBooleanAttribute("isReserveing", false);
        if (message.direct() == EMMessage.Direct.SEND && isReserveing) {
            long msgTime = message.getMsgTime();
            long reserveConfigStart = message.getLongAttribute("reserveConfigStart", 0);
            long reserveConfigEnd = message.getLongAttribute("reserveConfigEnd", 0);
            int sumDuration = message.getIntAttribute("sumDuration", 0);
            LogUtils.e("消息时间  "+msgTime);
            LogUtils.e("预约开始  "+reserveConfigStart);
            LogUtils.e("预约结束  "+reserveConfigEnd);
            LogUtils.e("剩余次数  "+sumDuration);
            linNum.setVisibility((msgTime > reserveConfigStart && reserveConfigEnd > msgTime) ? VISIBLE : GONE);
            allNum.setText("您一共有5次机会");
            remainNum.setText(String.format("您还有%d次机会", sumDuration));
        }else {
            linNum.setVisibility(GONE);
        }
    }

    public void onAckUserUpdate(final int count) {
        if (ackedView != null) {
            ackedView.post(new Runnable() {
                @Override
                public void run() {
                    ackedView.setVisibility(VISIBLE);
                    ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
                }
            });
        }
    }

    private void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private void onMessageSuccess() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.GONE);

        // Show "1 Read" if this msg is a ding-type msg.
        if (EaseDingMessageHelper.get().isDingMessage(message) && ackedView != null) {
            ackedView.setVisibility(VISIBLE);
            int count = message.groupAckCount();
            ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), count));
        }

        // Set ack-user list change listener.
        EaseDingMessageHelper.get().setUserUpdateListener(message, userUpdateListener);
    }

    private void onMessageError() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
    }

    private void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener =
            new EaseDingMessageHelper.IAckUserUpdateListener() {
                @Override
                public void onUpdate(List<String> list) {
                    onAckUserUpdate(list.size());
                }
            };
}
