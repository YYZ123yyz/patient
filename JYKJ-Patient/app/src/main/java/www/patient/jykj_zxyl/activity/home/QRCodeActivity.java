package www.patient.jykj_zxyl.activity.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import entity.shouye.OperScanQrCodeInside;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.hyhd.BindDoctorFriend;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.patient.jykj_zxyl.util.SaveImg;
import zxing.android.CaptureActivity;
import zxing.common.Constant;
import zxing.encode.CodeCreator;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        context = this;
        mApp = (JYKJApplication) getApplication();
        initView();
        initHandler();
        initListener();
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
                }
            }
        };
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
        Bitmap bitmap = CodeCreator.createQRCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getQrCode(), 200, 200, null);
        ivEwCode.setImageBitmap(bitmap);
        tv_wrz = (TextView)this.findViewById(R.id.tv_wrz);
        tv_xm = (TextView)this.findViewById(R.id.tv_xm);
        tv_dq = (TextView)this.findViewById(R.id.tv_dq);
        tv_save_img = (TextView) this.findViewById(R.id.tv_save_img);
        if (mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName() != null && !"".equals(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName()))
            tv_xm.setText(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        String dq = "";
        if (mApp.mProvideViewSysUserPatientInfoAndRegion.getProvinceName() != null && !"".equals(mApp.mProvideViewSysUserPatientInfoAndRegion.getProvinceName()))
            dq += mApp.mProvideViewSysUserPatientInfoAndRegion.getProvinceName();
        if (mApp.mProvideViewSysUserPatientInfoAndRegion.getCityName() != null && !"".equals(mApp.mProvideViewSysUserPatientInfoAndRegion.getCityName()))
            dq += mApp.mProvideViewSysUserPatientInfoAndRegion.getCityName();
        if (mApp.mProvideViewSysUserPatientInfoAndRegion.getAreaName() != null && !"".equals(mApp.mProvideViewSysUserPatientInfoAndRegion.getAreaName()))
            dq += mApp.mProvideViewSysUserPatientInfoAndRegion.getAreaName();
        if (dq != null && !"".equals(dq))
            tv_dq.setText(dq);
        if (mApp.mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus() != null && 0 == mApp.mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus())
            tv_wrz.setText("未认证");
        else if (mApp.mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus() != null && 1 == mApp.mProvideViewSysUserPatientInfoAndRegion.getFlagPatientStatus())
            tv_wrz.setText("已认证");
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

        tv_save_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasSaved = SaveImg.saveImg(bitmap, "心血管健康二维码.jpeg",context);
                if(hasSaved){
                    Toast.makeText(context,"已保存至相册^.^",Toast.LENGTH_SHORT).show();
                }

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
}
