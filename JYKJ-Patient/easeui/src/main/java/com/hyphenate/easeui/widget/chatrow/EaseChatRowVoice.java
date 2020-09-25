package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.util.EMLog;

import www.patient.jykj_zxyl.base.base_utils.LogUtils;

public class EaseChatRowVoice extends EaseChatRowFile {
    private static final String TAG = "EaseChatRowVoice";

    private ImageView voiceImageView;
    private TextView voiceLengthView;
    private ImageView readStatusView;

    private AnimationDrawable voiceAnimation;
    private TextView allNum;
    private TextView remainNum;
    private int test = 0;
    private LinearLayout linNum;
    private EMMessage myMessage;
    public EaseChatRowVoice(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
        this.myMessage =message;
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_voice : R.layout.ease_row_sent_voice, this);
    }

    @Override
    protected void onFindViewById() {
        voiceImageView = ((ImageView) findViewById(R.id.iv_voice));
        voiceLengthView = (TextView) findViewById(R.id.tv_length);
        readStatusView = (ImageView) findViewById(R.id.iv_unread_voice);

        allNum = (TextView) findViewById(R.id.all_num);
        remainNum = (TextView) findViewById(R.id.remain_num);
        linNum = (LinearLayout) findViewById(R.id.lin_num);
    }

    @Override
    protected void onSetUpView() {
        EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) message.getBody();
        int len = voiceBody.getLength();
        if (len > 0) {
            voiceLengthView.setText(voiceBody.getLength() + "\"");
            voiceLengthView.setVisibility(View.VISIBLE);
        } else {
            voiceLengthView.setVisibility(View.INVISIBLE);
        }
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            voiceImageView.setImageResource(R.drawable.ease_chatfrom_voice_playing);
        } else {
            voiceImageView.setImageResource(R.drawable.ease_chatto_voice_playing);
        }

        if (message.direct() == EMMessage.Direct.RECEIVE) {
            if (message.isListened()) {
                // hide the unread icon
                readStatusView.setVisibility(View.INVISIBLE);
            } else {
                readStatusView.setVisibility(View.VISIBLE);
            }
            EMLog.d(TAG, "it is receive msg");
            if (voiceBody.downloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                    voiceBody.downloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
                if (EMClient.getInstance().getOptions().getAutodownloadThumbnail()) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }

            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        } else {

        }

        // To avoid the item is recycled by listview and slide to this item again but the animation is stopped.
        EaseChatRowVoicePlayer voicePlayer = EaseChatRowVoicePlayer.getInstance(getContext());
        if (voicePlayer.isPlaying() && message.getMsgId().equals(voicePlayer.getCurrentPlayingId())) {
            startVoicePlayAnimation();
        }
    }


    public void setLinVi(int i){
        linNum.setVisibility(i % 2 == 0? VISIBLE :GONE);
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        super.onViewUpdate(msg);

        // Only the received message has the attachment download status.
        if (message.direct() == EMMessage.Direct.SEND) {
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
            return;
        }

        EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) msg.getBody();
        if (voiceBody.downloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING ||
                voiceBody.downloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void startVoicePlayAnimation() {
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            voiceImageView.setImageResource(R.anim.voice_from_icon);
        } else {
            voiceImageView.setImageResource(R.anim.voice_to_icon);
        }
        voiceAnimation = (AnimationDrawable) voiceImageView.getDrawable();
        voiceAnimation.start();

        // Hide the voice item not listened status view.
        if (message.direct() == EMMessage.Direct.RECEIVE) {
            readStatusView.setVisibility(View.INVISIBLE);
        }
    }

    public void stopVoicePlayAnimation() {
        if (voiceAnimation != null) {
            voiceAnimation.stop();
        }

        if (message.direct() == EMMessage.Direct.RECEIVE) {
            voiceImageView.setImageResource(R.drawable.ease_chatfrom_voice_playing);
        } else {
            voiceImageView.setImageResource(R.drawable.ease_chatto_voice_playing);
        }
    }
}
