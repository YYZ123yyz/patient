package zxing.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.zxing.Result;


import java.io.IOException;

import entity.SYSParmentEntity;
import entity.shouye.OperScanQrCodeInside;
import entity.shouye.ProvidePatientConditionTakingRecord;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.QRCodeActivity;
import www.patient.jykj_zxyl.activity.home.patient.ZJXQ_ZJBDActivity;
import www.patient.jykj_zxyl.activity.hyhd.BindDoctorFriend;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import zxing.bean.ZxingConfig;
import zxing.camera.CameraManager;
import zxing.common.Constant;
import zxing.decode.DecodeImgCallback;
import zxing.decode.DecodeImgThread;
import zxing.decode.ImageUtil;
import zxing.view.ViewfinderView;


/**
 * 扫一扫
 */

public class CaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    public ZxingConfig config;
    private SurfaceView previewView;
    private ViewfinderView viewfinderView;
    private AppCompatImageView flashLightIv;
    private TextView flashLightTv;
    private AppCompatImageView backIv;
//    private LinearLayoutCompat flashLightLayout;
//    private LinearLayoutCompat albumLayout;
//    private LinearLayoutCompat bottomLayout;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
//    private BeepManager beepManager;
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private SurfaceHolder surfaceHolder;
    private TextView tvAlbum;
    private ImageView ivAdd;
    private MoreFeaturesPopupWindow mPopupWindow;
    private TextView tvMyCode;
    private JYKJApplication mApp;
    public ProgressDialog mDialogProgress = null;
    private             String                              mNetRetStr;                 //返回字符串
    private             Handler                             mHandler;
    private             String                              mDoctorQRCode;              //医生二维码
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }

        /*先获取配置信息*/
        try {
            config = (ZxingConfig) getIntent().getExtras().get(Constant.INTENT_ZXING_CONFIG);
        } catch (Exception e) {

            Log.i("config", e.toString());
        }

        if (config == null) {
            config = new ZxingConfig();
        }


        setContentView(R.layout.activity_capture);


        initView();
        initHandler();
        hasSurface = false;
        mApp = (JYKJApplication) getApplication();
        inactivityTimer = new InactivityTimer(this);
//        getSerciceName("JY0100YS200111180041689EXB");
//        mDoctorQRCode = "JY0100YS200111180041689EXB";
//        beepManager = new BeepManager(this);
//        beepManager.setPlayBeep(config.isPlayBeep());
//        beepManager.setVibrate(config.isShake());


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
                            Toast.makeText(CaptureActivity.this,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        else
                        {
                            String string = netRetEntity.getResMsg();
                            System.out.println(string);
                            startActivity(new Intent(CaptureActivity.this,ZJXQ_ZJBDActivity.class)
                                    .putExtra("doctorQRCode",mDoctorQRCode)
                            .putExtra("url",string));
                            CaptureActivity.this.finish();
                        }

                        break;
                    case 1:

                        break;
                }
            }
        };
    }
    private void initView() {
        previewView = (SurfaceView) findViewById(R.id.preview_view);
        previewView.setOnClickListener(this);

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setZxingConfig(config);


        backIv = (AppCompatImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(this);

        flashLightIv = (AppCompatImageView) findViewById(R.id.flashLightIv);
        flashLightTv = (TextView) findViewById(R.id.flashLightTv);

        tvAlbum = (TextView) findViewById(R.id.tv_album);
        ivAdd = (ImageView) findViewById(R.id.iv_add);
        tvMyCode = (TextView) findViewById(R.id.tv_my_code);
        tvAlbum.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        tvMyCode.setOnClickListener(this);

//        flashLightLayout = findViewById(R.id.flashLightLayout);
//        flashLightLayout.setOnClickListener(this);
//        albumLayout = findViewById(R.id.albumLayout);
//        albumLayout.setOnClickListener(this);
//        bottomLayout = findViewById(R.id.bottomLayout);


//        switchVisibility(bottomLayout, config.isShowbottomLayout());
//        switchVisibility(flashLightLayout, config.isShowFlashLight());
//        switchVisibility(albumLayout, config.isShowAlbum());


        /*有闪光灯就显示手电筒按钮  否则不显示*/
//        if (isSupportCameraLedFlash(getPackageManager())) {
//            flashLightLayout.setVisibility(View.VISIBLE);
//        } else {
//            flashLightLayout.setVisibility(View.GONE);
//        }

    }


    /**
     * @param pm
     * @return 是否有闪光灯
     */
    public static boolean isSupportCameraLedFlash(PackageManager pm) {
        if (pm != null) {
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            if (features != null) {
                for (FeatureInfo f : features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param flashState 切换闪光灯图片
     */
    public void switchFlashImg(int flashState) {

        if (flashState == Constant.FLASH_OPEN) {
            flashLightIv.setImageResource(R.drawable.ic_open);
            flashLightTv.setText(R.string.close_flash);
        } else {
            flashLightIv.setImageResource(R.drawable.ic_close);
            flashLightTv.setText(R.string.open_flash);
        }

    }

    /**
     * @param rawResult 返回的扫描结果
     */
    public void handleDecode(Result rawResult) {

        inactivityTimer.onActivity();

//        beepManager.playBeepSoundAndVibrate();

//        Intent intent = getIntent();
//        intent.putExtra(Constant.CODED_CONTENT, rawResult.getText());
//        setResult(RESULT_OK, intent);
//        ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend = new ProvideViewDoctorExpertRecommend();
//        provideViewDoctorExpertRecommend.setDoctorCode(rawResult.getText());
//        provideViewDoctorExpertRecommend.setUserName(rawResult.getText());
//        startActivity(new Intent(this,ZJXQ_ZJBDActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommend));
//        this.finish();
        mDoctorQRCode = rawResult.getText();
        //调用接口获取服务名称
        getSerciceName(rawResult.getText());

    }

    /**
     * 调接口获取返回服务接口名称
     */
    private void getSerciceName(String qrCode) {
        getProgressBar("请稍候","正在获取数据。。。");
        SYSParmentEntity sYSParmentEntity = new SYSParmentEntity();
        sYSParmentEntity.setLoginUserPosition(mApp.loginDoctorPosition);
        sYSParmentEntity.setRequestClientType("1");
        sYSParmentEntity.setUserUseType("6");
        sYSParmentEntity.setOperUserCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        sYSParmentEntity.setOperUserName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        sYSParmentEntity.setScanQrCode(qrCode);
        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(sYSParmentEntity);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"patientDataControlle/operScanQrCodeInside");
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


    private void switchVisibility(View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication(), config);

        viewfinderView.setCameraManager(cameraManager);
        handler = null;

        surfaceHolder = previewView.getHolder();
        if (hasSurface) {

            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

//        beepManager.updatePrefs();
        inactivityTimer.onResume();

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("扫一扫");
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    protected void onPause() {

        Log.i("CaptureActivity","onPause");
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
//        beepManager.close();
        cameraManager.closeDriver();

        if (!hasSurface) {

            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        viewfinderView.stopAnimator();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
//        if (id == R.id.flashLightLayout) {
//            /*切换闪光灯*/
//            cameraManager.switchFlashLight(handler);
//        } else if (id == R.id.albumLayout) {
//            /*打开相册*/
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_PICK);
//            intent.setType("image/*");
//            startActivityForResult(intent, Constant.REQUEST_IMAGE);
//        } else
        if (id == R.id.backIv) {
            finish();
        }else if(id == R.id.tv_album){
            /*打开相册*/
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, Constant.REQUEST_IMAGE);
        }else if(id == R.id.iv_add){
            mPopupWindow = new MoreFeaturesPopupWindow(CaptureActivity.this);
            if(mPopupWindow!=null&&!mPopupWindow.isShowing()){
                mPopupWindow.showAsDropDown(ivAdd,0,0);
            }
        }else if(id == R.id.tv_my_code){
            startActivity(new Intent(this,QRCodeActivity.class));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_IMAGE && resultCode == RESULT_OK) {
            String path = ImageUtil.getImageAbsolutePath(this, data.getData());
            new DecodeImgThread(path, new DecodeImgCallback() {
                @Override
                public void onImageDecodeSuccess(Result result) {
                    handleDecode(result);
                }

                @Override
                public void onImageDecodeFailed() {
                    Toast.makeText(CaptureActivity.this, R.string.scan_failed_tip, Toast.LENGTH_SHORT).show();
                }
            }).run();

        }
    }

    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(CaptureActivity.this);
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


}
