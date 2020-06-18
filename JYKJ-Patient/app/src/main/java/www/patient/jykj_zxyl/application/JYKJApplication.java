package www.patient.jykj_zxyl.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.hyhd.model.CallReceiver;
import com.hyphenate.exceptions.HyphenateException;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.basicDate.ProvideBasicsDomain;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideDoctorPatientUserInfo;
import entity.basicDate.ProvideViewSysUserPatientInfoAndRegion;
import entity.unionInfo.ProvideUnionDoctorOrg;
import entity.user.UserInfo;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.fragment.FragmentShouYe;
import www.patient.jykj_zxyl.util.HttpUtils;


public class JYKJApplication extends Application {

    public                     boolean                          gRefreshDate = false;                   //客户管理中是否需要刷新行政区划数据
    public                     Map<Integer,List<ProvideBasicsDomain>> gBasicDate = new HashMap<>();    //基础数据
    public                      List<Activity>                  gActivityList = new ArrayList();
    public                      Context                         gContext;
    public                      ImageLoader                         imageLoader = ImageLoader.getInstance();
    public                      DisplayImageOptions                 mImageOptions;		                                        // DisplayImageOptions是用于设置图片显示的类

    public                      SharedPreferences_DataSave      m_persist;					                            //数据存储

    public                      UserInfo                        mLoginUserInfo;                         //登录需要的用户信息
    public                      ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;       //登录成功后服务端返回的真实用户信息

    public                     List<ProvideBasicsRegion>       gRegionList = new ArrayList<>();                    //所有区域数据
    public                     List<ProvideBasicsRegion>       gRegionProvideList = new ArrayList<>();            //省级区域数据
    public                     List<ProvideBasicsRegion>       gRegionCityList = new ArrayList<>();                //市级区域数据
    public                     List<ProvideBasicsRegion>       gRegionDistList = new ArrayList<>();                //区县级区域数据

    public                     String                          loginDoctorPosition = "20.154455^23.41548512";       //用户所处的经纬度

    public                      boolean                         isUpdateReviewUnion = false;                        //加入联盟审核信息是否更新，默认否
    public MainActivity gMainActivity;
    public                      boolean                         gNetConnectionHX;
    public                      boolean                         gNetWorkTextView = false;                       //是否显示网络状态
    public                      ProvideUnionDoctorOrg           unionSettionChoiceOrg;                          //医生联盟，设置联盟成员联盟层级
    public                      Integer                         mMsgTimeInterval = 5;                               //轮询新消息时间，初始设置5分钟
    public                      String                          gBitMapString;                                  //身份证拍照bitmap
    public                      String                          gPayOrderCode;
    public                      List<Activity>                  gPayCloseActivity = new ArrayList<>();          //支付成功之后需要关闭的页面

    private                     String                              mNetRetStr;                 //返回字符串


    private                     List<ProvideDoctorPatientUserInfo> mProvideDoctorPatientUserInfo = new ArrayList<>();

    public                      int                         gNewMessageNum = 0;                                  //新消息数量
    public                      int                         gMessageNum = 10;                //可发送的消息数量
    public                      long                        gVoiceTime = 20;                 //可拨打语音消息时长（单位：秒）
    public                      long                        gVedioTime = 30;                 //可拨打视频消息时长（单位：秒）


    public                  List<ProvideBasicsDomain>                 gProvideBasicsDomainJGBJ = new ArrayList<>();             //机构背景
    public                  List<ProvideBasicsDomain>                 gProvideBasicsDomainYSZC = new ArrayList<>();                                 //医生职称

//    public BDLocationListener myListener = new MyLocationListener();
    public LocationClient mLocationClient = null;

    private                     MainActivity               mainActivity;

    /**
     * 异地登录环信
     */
    public void user_login_another_device() {
        gMainActivity.user_login_another_device();
    }


//    private class MyLocationListener implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
////            Toast.makeText(getApplicationContext(),"监听到了",Toast.LENGTH_LONG).show();
//            StringBuffer sb = new StringBuffer(256);
//            sb.append("time : ");
//            sb.append(location.getTime());
//            sb.append("\nerror code : ");
//            sb.append(location.getLocType());
//            sb.append("\nlatitude : ");
//            sb.append(location.getLatitude());
//            sb.append("\nlontitude : ");
//            sb.append(location.getLongitude());
//            sb.append("\nradius : ");
//            sb.append(location.getRadius());
//            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                sb.append("\nspeed : ");
//                sb.append(location.getSpeed());// 单位：公里每小时
//                sb.append("\nsatellite : ");
//                sb.append(location.getSatelliteNumber());
//                sb.append("\nheight : ");
//                sb.append(location.getAltitude());// 单位：米
//                sb.append("\ndirection : ");
//                sb.append(location.getDirection());// 单位度
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());
//                sb.append("\ndescribe : ");
//                sb.append("gps定位成功");
//
//            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());
//                //运营商信息
//                sb.append("\noperationers : ");
//                sb.append(location.getOperators());
//                sb.append("\ndescribe : ");
//                sb.append("网络定位成功");
//            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
//                sb.append("\ndescribe : ");
//                sb.append("离线定位成功。离线定位结果也是有效的");
//            } else if (location.getLocType() == BDLocation.TypeServerError) {
//                sb.append("\ndescribe : ");
//                sb.append("服务端网络定位失败。能够反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
//            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                sb.append("\ndescribe : ");
//                sb.append("网络不同导致定位失败，请检查网络是否通畅");
//            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                sb.append("\ndescribe : ");
//                sb.append("无法获取有效定位根据导致定位失败。通常是因为手机的原因。处于飞行模式下通常会造成这样的结果，能够试着重新启动手机");
//            }
//            sb.append("\nlocationdescribe : ");
//            sb.append(location.getLocationDescribe());// 位置语义化信息
//            List<Poi> list = location.getPoiList();// POI数据
//            if (list != null) {
//                sb.append("\npoilist size = : ");
//                sb.append(list.size());
//                for (Poi p : list) {
//                    sb.append("\npoi= : ");
//                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
//                }
//            }
//            Log.e("描写叙述：", sb.toString());
//        }
//    }

    public                      Handler                         gHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    if (mProvideViewSysUserPatientInfoAndRegion.getPatientCode() == null)
                        mProvideViewSysUserPatientInfoAndRegion.setPatientCode("");
                    if (mProvideViewSysUserPatientInfoAndRegion.getUserPwd() == null)
                        mProvideViewSysUserPatientInfoAndRegion.setUserPwd("");
                    String userAccountHuanxin = mProvideViewSysUserPatientInfoAndRegion.getPatientCode();
                    System.out.println("患者编码"+mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    System.out.println("患者密码"+mProvideViewSysUserPatientInfoAndRegion.getQrCode());



                    EMClient.getInstance().login(userAccountHuanxin, mProvideViewSysUserPatientInfoAndRegion.getQrCode(), new EMCallBack() {
                        @Override
                        public void onSuccess() {

                            System.out.println("登录成功");
                            setNewsMessage();
//                            Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                            System.out.println("登录成功");
                            gMainActivity.registHX();

                        }

                        @Override
                        public void onError(int i, String s) {

                            System.out.println("登录失败");

                        }

                        @Override
                        public void onProgress(int i, String s) {
                            System.out.println("正在登录");
                        }
                    });
//                    editText.setText("code:"+mProvideViewSysUserPatientInfoAndRegion.getPatientCode()+"密码："+mProvideViewSysUserPatientInfoAndRegion.getQrCode());
//                    AlertDialog.Builder inputDialog =
//                            new AlertDialog.Builder(getApplicationContext());
//                    inputDialog.setTitle("参数").setView(editText);
//                    inputDialog.setPositiveButton("确定",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            }).show();
                    //登录
                    break;
            }
        }
    };



    public void loginIM(){
        new Thread(){
            public void run(){
                //注册
                try {
                    EMClient.getInstance().createAccount(mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),mProvideViewSysUserPatientInfoAndRegion.getQrCode());
                    gHandler.sendEmptyMessage(1);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    gHandler.sendEmptyMessage(1);
                    System.out.println("~~~~~~~注册失败~~~~~~~~"+e.getDescription());
                }
            }
        }.start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化IM聊天界面
        gContext = getApplicationContext();
        //获取本地缓存
        getPersistence();
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(gContext, options);

        //初始化图片方法
        /**
         * 配置并初始化ImageLoader
         */
        if (Constant.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }
        initImageLoader(getApplicationContext());

        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        mImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.hztx)			        // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.hztx)	         // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.hztx)		        // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0))	// 设置成圆角图片
                .build();									// 创建配置过得DisplayImageOption对象
        saveIMNumInfo();
        getIMNumInfo();

//        startLocate();

    }

    /**
     * 保存用户信息
     */
    public void saveUserInfo() {
        //数据存储,以json字符串的格式保存
        m_persist 	= new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.putString("loginUserInfo",new Gson().toJson(mLoginUserInfo));
        m_persist.putString("viewSysUserDoctorInfoAndHospital",new Gson().toJson(mProvideViewSysUserPatientInfoAndRegion));
        m_persist.commit();
    }

    /**
     * 保存图文音视频通话限制信息
     */
    public void saveIMNumInfo() {
        //数据存储,以json字符串的格式保存
        m_persist 	= new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.putInt("messageNum",gMessageNum);
        m_persist.putLong("voiceTime",gVoiceTime);
        m_persist.putLong("vedioTime",gVedioTime);
        m_persist.commit();
    }
    //获取图文音视频通话限制信息
    public void getIMNumInfo() {
        m_persist 	= new SharedPreferences_DataSave(this, "JYKJDOCTER");
        gMessageNum 			    = m_persist.getInt("messageNum",0);
        gVoiceTime 			    = m_persist.getLong("voiceTime",0);
        gVedioTime 			    = m_persist.getLong("vedioTime",0);
        System.out.println(gMessageNum);
        System.out.println(gVoiceTime);
        System.out.println(gVedioTime);
        System.out.println(gVedioTime);
    }



    //清空数据
    public void cleanPersistence() {
        m_persist 	= new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.remove("loginUserInfo");
        m_persist.remove("viewSysUserDoctorInfoAndHospital");
        mLoginUserInfo.setUserPhone("");
        mLoginUserInfo.setUserPwd("");
        m_persist.commit();
    }

    //获取缓存数据
    public void getPersistence() {
        m_persist 	= new SharedPreferences_DataSave(this, "JYKJDOCTER");
        String userInfoLogin 			    = m_persist.getString("loginUserInfo","");
        String userInfoSuLogin 			    = m_persist.getString("viewSysUserDoctorInfoAndHospital","");
        mLoginUserInfo = new Gson().fromJson(userInfoLogin,UserInfo.class);
        try {
            mProvideViewSysUserPatientInfoAndRegion = new Gson().fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
        }catch (Exception e)
        {

        }
        System.out.println(mProvideViewSysUserPatientInfoAndRegion);
    }


    /**
     * 初始化ImageLoader
     * @param context
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * 设置新消息
     */
    public void setNewsMessage() {
        gMainActivity.setNewsMessageView();

    }

//    /**
//     * 定位
//     */
//    private void startLocate() {
//       //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
//        LocationClient locationClient = new LocationClient(getApplicationContext());
////声明LocationClient类实例并配置定位参数
//        LocationClientOption locationOption = new LocationClientOption();
//        MyLocationListener myLocationListener = new MyLocationListener();
////注册监听函数
//        locationClient.registerLocationListener(myLocationListener);
////可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
////可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
//        locationOption.setCoorType("bd09ll");
////可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
//        locationOption.setScanSpan(1000);
////可选，设置是否需要地址信息，默认不需要
//        locationOption.setIsNeedAddress(true);
////可选，设置是否需要地址描述
//        locationOption.setIsNeedLocationDescribe(true);
////可选，设置是否需要设备方向结果
//        locationOption.setNeedDeviceDirect(false);
////可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//        locationOption.setLocationNotify(true);
////可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        locationOption.setIgnoreKillProcess(true);
////可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        locationOption.setIsNeedLocationDescribe(true);
////可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        locationOption.setIsNeedLocationPoiList(true);
////可选，默认false，设置是否收集CRASH信息，默认收集
//        locationOption.SetIgnoreCacheException(false);
////可选，默认false，设置是否开启Gps定位
//        locationOption.setOpenGps(true);
////可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
//        locationOption.setIsNeedAltitude(false);
////设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
//        locationOption.setOpenAutoNotifyMode();
////设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
//        locationOption.setOpenAutoNotifyMode(3000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
////需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//        locationClient.setLocOption(locationOption);
////开始定位
//        locationClient.start();
//    }

    /**
     * 定位
     */
    private void startLocate() {
       //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        LocationClient locationClient = new LocationClient(getApplicationContext());
//声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        //MyLocationListener myLocationListener = new MyLocationListener();
//注册监听函数
        //locationClient.registerLocationListener(myLocationListener);
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
//开始定位
        locationClient.start();
    }



    public static boolean isApplicationBroughtToBackground(final Context context) {
        //创建activity管理对象
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        //取出RunningTask栈
        List<ActivityManager.RunningTaskInfo> list =activityManager.getRunningTasks(1);
        //判断是否为空
        if (!list.isEmpty()){
            //取出运行在栈顶的任务进程
            ComponentName componentName = list.get(0).topActivity;
            //判断任务进程的包名是否和想要判断的程序包名相同
            if (componentName.getPackageName().equals(context.getPackageName())){
                //相同则说明你该程序运行在前台，则返回ture
                return true;
            }
        }
        //否则返回false
        return false;
    }

    /**
     * 设置环信网络状态
     */
    public void setNetConnectionStateHX() {
        if (gMainActivity != null)
            gMainActivity.setHXNetWorkState();
    }
}