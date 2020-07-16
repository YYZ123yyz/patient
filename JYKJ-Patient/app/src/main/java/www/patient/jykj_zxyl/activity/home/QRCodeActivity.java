package www.patient.jykj_zxyl.activity.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import entity.DoctorInfo.InteractDoctorDetailInfo;
import entity.basicDate.ProvideViewSysUserPatientInfoAndRegion;
import entity.shouye.OperScanQrCodeInside;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.FXActivity;
import www.patient.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.hyhd.BindDoctorFriend;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.patient.jykj_zxyl.util.SaveImg;
import www.patient.jykj_zxyl.util.WechatShareManager;
import zxing.android.CaptureActivity;
import zxing.common.Constant;
import zxing.encode.CodeCreator;

import static www.patient.jykj_zxyl.activity.LoginActivity.isWeixinAvilible;

/**
 * 识别码
 */
public class QRCodeActivity extends AppCompatActivity {
    private ImageView ivEwCode;
    private LinearLayout mBack;
    private ImageView ivAdd;
    private MoreFeaturesPopupWindow mPopupWindow;

    private TextView    userName;
    private TextView    userYY;
    private TextView    userPhone;
    private TextView    userZC;
    private TextView    userSCLY;
    private TextView    userGRJJ;

    private TextView    sys;
    private JYKJApplication mApp;
    private Context     context;

    public static final int REQUEST_CODE_SCAN = 0x123;
    private             String                              qrCode;                         //需要绑定的二维码
    private             String                              mNetRetStr;                 //返回字符串
    private Handler mHandler;
    private             Context                             mContext;
    public ProgressDialog mDialogProgress = null;

    private             TextView            tv_wrz;
    private             TextView            tv_xm;
    private             TextView            tv_dq;
    private             TextView            tv_save_img;

    private             TextView            tv_fx;
    private             Bitmap              logoBitmap;                         //用户头像bitmap
    private             Bitmap              ewmBitmap;                          //二维码Bitmap

    private             WechatShareManager mShareManager;

    private             ProvideViewSysUserPatientInfoAndRegion  mProvideViewSysUserPatientInfoAndRegion;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        context = this;
        mApp = (JYKJApplication) getApplication();
        mShareManager = WechatShareManager.getInstance(context);
        ProvideViewSysUserPatientInfoAndRegion provideViewSysUserPatientInfoAndRegion=  mApp.mProvideViewSysUserPatientInfoAndRegion;
        initView();
        initHandler();
        initListener();
        //网络url转bitmao
        getLogoBitMap();
        getDate();
    }

    /**
     * 获取数据
     */
    private void getDate() {
        getProgressBar("请稍候。。。","正在获取数据");
        ProvideViewSysUserPatientInfoAndRegion interactDoctorDetailInfo = new ProvideViewSysUserPatientInfoAndRegion();
        interactDoctorDetailInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        interactDoctorDetailInfo.setRequestClientType("1");
        interactDoctorDetailInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        interactDoctorDetailInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(interactDoctorDetailInfo),www.patient.jykj_zxyl.application.Constant.SERVICEURL+"patientDataControlle/searchPatientIdentificationCode");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(3);
            }
        }.start();
    }


    private void getLogoBitMap() {
        new Thread(){
            public void run(){
                logoBitmap = getBitmap(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
                mHandler.sendEmptyMessage(10);
            }
        }.start();
    }

    private void initHandler() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(context,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        else
                        {
                            if ("1".equals(netRetEntity.getResData()))
                            {
                                //医生扫医生二维码，绑定医生好友
                                final EditText et = new EditText(context);
                                new AlertDialog.Builder(context).setTitle("请输入申请描述")
                                        .setView(et)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //按下确定键后的事件
                                                bindDoctorFriend(netRetEntity.getResMsg(),et.getText().toString(),qrCode);
                                            }
                                        }).setNegativeButton("取消",null).show();
//
                            }
                            if ("2".equals(netRetEntity.getResMsg()))
                            {
                                //医生扫医生联盟二维码
                            }
                            if ("3".equals(netRetEntity.getResMsg()))
                            {
                                //医生扫患者二维码
                            }

                        }
                        break;
                    case 1:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        Toast.makeText(context,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        ewmBitmap = CodeCreator.createQRCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getQrCode(), 200, 200, logoBitmap);
                        ivEwCode.setImageBitmap(ewmBitmap);
                        break;

                    case 3:
                        cacerProgress();
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(context,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        else
                        {
                           mProvideViewSysUserPatientInfoAndRegion = JSON.parseObject(netRetEntity.getResJsonData(),ProvideViewSysUserPatientInfoAndRegion.class);
                            showDate();
                        }
                        break;
                }
            }
        };
    }

    /**
     * 展示数据
     */
    private void showDate() {
        if (mProvideViewSysUserPatientInfoAndRegion.getUserName() != null && !"".equals(mProvideViewSysUserPatientInfoAndRegion.getUserName()))
            tv_xm.setText(mProvideViewSysUserPatientInfoAndRegion.getUserName());
            tv_dq.setText(mProvideViewSysUserPatientInfoAndRegion.getShowRegionInfo());
        if (mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus() != null && 0 == mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus())
            tv_wrz.setText("未认证");
        else if (mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus() != null && 1 == mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus())
            tv_wrz.setText("已认证");
    }

    /**
     * 医生好友绑定
     */
    private void bindDoctorFriend(String url,String reason,String qrCode) {
        getProgressBar("请稍候","正在处理");
        BindDoctorFriend bindDoctorFriend = new BindDoctorFriend();
        bindDoctorFriend.setLoginDoctorPosition(mApp.loginDoctorPosition);
        bindDoctorFriend.setBindingDoctorQrCode(qrCode);
        bindDoctorFriend.setOperDoctorCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        bindDoctorFriend.setOperDoctorName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        bindDoctorFriend.setApplyReason(reason);

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(bindDoctorFriend);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"/"+url);

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

    private void operScanQrCodeInside(String content) {
        getProgressBar("请稍候","正在处理");
        OperScanQrCodeInside operScanQrCodeInside = new OperScanQrCodeInside();
        operScanQrCodeInside.setLoginDoctorPosition(mApp.loginDoctorPosition);
        operScanQrCodeInside.setUserUseType("5");
        operScanQrCodeInside.setOperUserCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operScanQrCodeInside.setOperUserName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        operScanQrCodeInside.setScanQrCode(content);

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(operScanQrCodeInside);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"doctorDataControlle/operScanQrCodeInside");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(context);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                qrCode = content;
                operScanQrCodeInside(content);
            }
        }
    }
    private void initView(){
        ivEwCode = (ImageView) findViewById(R.id.iv_ew_code);
        mBack = (LinearLayout) findViewById(R.id.ll_back);
        ivAdd = (ImageView) findViewById(R.id.iv_add);

        tv_wrz = (TextView)this.findViewById(R.id.tv_wrz);
        tv_xm = (TextView)this.findViewById(R.id.tv_xm);
        tv_dq = (TextView)this.findViewById(R.id.tv_dq);
        tv_fx = (TextView)this.findViewById(R.id.tv_fx);
        tv_save_img = (TextView) this.findViewById(R.id.tv_save_img);

            //        userName = (TextView)this.findViewById(R.id.tv_userName);
//        userYY = (TextView)this.findViewById(R.id.tv_userYY);
//        userPhone = (TextView)this.findViewById(R.id.tv_phoneNum);
//        userZC = (TextView)this.findViewById(R.id.tv_useZC);
//        userSCLY = (TextView)this.findViewById(R.id.tv_userSCLY);
//        userGRJJ = (TextView)this.findViewById(R.id.tv_userGRJJ);
        sys = (TextView)this.findViewById(R.id.sys);
        sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });

        //分享患者
        tv_save_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wechatShare(1);

            }
        });
        //分享医生
        tv_fx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wechatShare(1);
            }
        });
//        userName.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
//        userYY.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getProvinceName());
//        userPhone.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());
//        userZC.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.get()+"|"+mApp.mProvideViewSysUserPatientInfoAndRegion.getDepartmentName());
//        userSCLY.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getGoodAtRealm());
//        userGRJJ.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getSynopsis());

    }

    private void initListener(){
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow = new MoreFeaturesPopupWindow(QRCodeActivity.this);
                if(mPopupWindow!=null&&!mPopupWindow.isShowing()){
                    mPopupWindow.showAsDropDown(ivAdd,0,0);
                }
            }
        });

    }

    /**
     * 图片网络URL转bitmap
     * @param url
     * @return
     */
    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    //医生分享
    private void wechatShare(final int type) {

        final Dialog dialog = new Dialog(context, R.style.BottomDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.bottom_dialog_dynamic, null);
        dialog.setContentView(view);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        TextView tv3 = view.findViewById(R.id.tv3);
        TextView tv4 = view.findViewById(R.id.tv4);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //分享好友
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //分享患者
                if (type == 1)
                    ToShare(SendMessageToWX.Req.WXSceneSession, mProvideViewSysUserPatientInfoAndRegion.getPatientShareTitle(),
                            mProvideViewSysUserPatientInfoAndRegion.getPatientShareContent(),
                            mProvideViewSysUserPatientInfoAndRegion.getPatientShareUrl(), R.mipmap.logo);
                //分享医生
                if (type == 2)
                    ToShare(SendMessageToWX.Req.WXSceneSession, mProvideViewSysUserPatientInfoAndRegion.getDoctorShareTitle(),
                            mProvideViewSysUserPatientInfoAndRegion.getDoctorShareContent(),
                            mProvideViewSysUserPatientInfoAndRegion.getDoctorShareUrl(), R.mipmap.logo);
                dialog.dismiss();
            }
        });
        //分享朋友圈
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1)
                    ToShare(SendMessageToWX.Req.WXSceneTimeline, mProvideViewSysUserPatientInfoAndRegion.getPatientShareTitle(),
                            mProvideViewSysUserPatientInfoAndRegion.getPatientShareContent(),
                            mProvideViewSysUserPatientInfoAndRegion.getPatientShareUrl(), R.mipmap.logo);
                if (type == 2)
                    ToShare(SendMessageToWX.Req.WXSceneTimeline, mProvideViewSysUserPatientInfoAndRegion.getDoctorShareTitle(),
                            mProvideViewSysUserPatientInfoAndRegion.getDoctorShareContent(),
                            mProvideViewSysUserPatientInfoAndRegion.getDoctorShareUrl(), R.mipmap.logo);
                dialog.dismiss();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();


    }

    /**
     * 分享到微信
     *
     * @param type            SendMessageToWX.Req.WXSceneSession  //会话    SendMessageToWX.Req.WXSceneTimeline //朋友圈
     * @param title           标题
     * @param content         内容
     * @param url             地址
     * @param pictureResource 图标
     */
    public void ToShare(int type, String title, String content, String url, int pictureResource) {
        //i   0是会话  1是朋友圈
        if (isWeixinAvilible(context)) {
            WechatShareManager.ShareContentWebpage mShareContent =
                    (WechatShareManager.ShareContentWebpage) mShareManager.getShareContentWebpag(title, content, url, pictureResource);

            mShareManager.shareByWebchat(mShareContent, type);
        } else {
            Toast.makeText(context, "您还没有安装微信，请先安装微信客户端", Toast.LENGTH_SHORT).show();
        }
    }
}
