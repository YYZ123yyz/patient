/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyphenate.easeui.hyhd.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.TimeUtils;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.entity.DoctorPatientUserInfo;
import com.hyphenate.easeui.entity.ProvideDoctorPatientUserInfo;
import com.hyphenate.easeui.hyhd.DemoHelper;
import com.hyphenate.easeui.hyhd.VideoCallActivity;
import com.hyphenate.easeui.hyhd.VoiceCallActivity;
import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;
import com.hyphenate.easeui.utils.MediaSoundUtil;
import com.hyphenate.util.EMLog;

import java.util.List;


public class CallReceiver extends BroadcastReceiver{


    private                 String                      mNetRetStr;                 //返回字符串
    private                 Context                     mContext;
    private                 String                      mFrom;
    private                 String                      mType;
    private                 Long                        mFirstTime;                 //第一次时间
    private                 Handler                     mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    String stringUserName = "";
                    if (mNetRetStr != null && !mNetRetStr.equals("")) {
                        NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            List<ProvideDoctorPatientUserInfo> mProvideDoctorPatientUserInfos =  new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideDoctorPatientUserInfo>>(){}.getType());

                            if (mProvideDoctorPatientUserInfos != null && mProvideDoctorPatientUserInfos.size() > 0)
                            {
                                stringUserName = mProvideDoctorPatientUserInfos.get(0).getUserName();
                            }
                        }
                    }
                    mFirstTime = System.currentTimeMillis();
                    if("video".equals(mType)){ //video call

                        mContext.startActivity(new Intent(mContext, VideoCallActivity.class).
                                putExtra("username", mFrom).putExtra("isComingCall", true).putExtra("nickName", stringUserName).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("vedioNum",1000000));
                    }else{ //voice call
                        mContext.startActivity(new Intent(mContext, VoiceCallActivity.class).
                                putExtra("username", mFrom).putExtra("isComingCall", true).putExtra("nickName", stringUserName).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("voiceNum",1000000));
                        //            MediaSoundUtil mediaSoundUtil = new MediaSoundUtil(context);
                        //            mediaSoundUtil.playRingSound();
                    }
                    EMLog.d("CallReceiver", "app received a incoming call");
                    break;
            }
        }
    };;

    public static long getDistanceTime(long  time1,long time2 ) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        diff = time2 - time1;
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        return sec;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!DemoHelper.getInstance().isLoggedIn())
            return;
        if (mFirstTime != null)
        {
            long timeDistance = getDistanceTime(mFirstTime, System.currentTimeMillis());
            if (timeDistance < 3)
                return;
        }
        //username
        String from = intent.getStringExtra("from");
        //call type
        String type = intent.getStringExtra("type");
        mContext = context;
        mFrom = from;
        mType = type;
        //通过from获取用户信息
        getUserInfo(from);

    }

    private void getUserInfo(final String userCode){
        //连接网络，登录

        new Thread(){
            public void run(){

                ProvideDoctorPatientUserInfo provideDoctorPatientUserInfo = new ProvideDoctorPatientUserInfo();
                provideDoctorPatientUserInfo.setUserCodeList(userCode);
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideDoctorPatientUserInfo),Constant.SERVICEURL+"patientDoctorCommonDataController/getUserInfoList");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

}
