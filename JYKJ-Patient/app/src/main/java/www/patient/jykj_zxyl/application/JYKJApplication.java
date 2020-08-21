package www.patient.jykj_zxyl.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;

import com.allen.library.RxHttpUtils;
import com.allen.library.config.OkHttpConfig;
import com.allen.library.cookie.store.SPCookieStore;
import com.allen.library.manage.RxUrlManager;
import com.allin.commlibrary.LibApp;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.hyhd.DemoHelper;
import com.hyphenate.easeui.hyhd.model.CallReceiver;
import com.hyphenate.easeui.utils.ExtEaseUtils;
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
import netService.HttpNetService;
import okhttp3.OkHttpClient;
import www.patient.jykj_zxyl.BuildConfig;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_utils.SharedPreferences_DataSave;
import www.patient.jykj_zxyl.base.http.AppUrlConfig;


public class JYKJApplication extends Application {

    public boolean gRefreshDate = false;                   //客户管理中是否需要刷新行政区划数据
    public Map<Integer, List<ProvideBasicsDomain>> gBasicDate = new HashMap<>();    //基础数据
    public List<Activity> gActivityList = new ArrayList();
    public ImageLoader imageLoader = ImageLoader.getInstance();
    public DisplayImageOptions mImageOptions;                                                // DisplayImageOptions是用于设置图片显示的类

    public SharedPreferences_DataSave m_persist;                                                //数据存储

    public UserInfo mLoginUserInfo;                         //登录需要的用户信息
    public ProvideViewSysUserPatientInfoAndRegion mProvideViewSysUserPatientInfoAndRegion;       //登录成功后服务端返回的真实用户信息

    public List<ProvideBasicsRegion> gRegionList = new ArrayList<>();                    //所有区域数据
    public List<ProvideBasicsRegion> gRegionProvideList = new ArrayList<>();            //省级区域数据
    public List<ProvideBasicsRegion> gRegionCityList = new ArrayList<>();                //市级区域数据
    public List<ProvideBasicsRegion> gRegionDistList = new ArrayList<>();                //区县级区域数据

    public String loginDoctorPosition = "20.154455^23.41548512";       //用户所处的经纬度

    public boolean isUpdateReviewUnion = false;                        //加入联盟审核信息是否更新，默认否
    public MainActivity gMainActivity;
    public boolean gNetConnectionHX;
    public boolean gNetWorkTextView = false;                       //是否显示网络状态
    public ProvideUnionDoctorOrg unionSettionChoiceOrg;                          //医生联盟，设置联盟成员联盟层级
    public Integer mMsgTimeInterval = 5;                               //轮询新消息时间，初始设置5分钟
    public String gBitMapString;                                  //身份证拍照bitmap
    public String gPayOrderCode;
    public List<Activity> gPayCloseActivity = new ArrayList<>();          //支付成功之后需要关闭的页面
    public String gProviceName;                                   //定位获取到的位置信息
    public String gGDLocation;                                    //高德定位编码
    public String gGDLocationName;                                //高德定位名称
    public static Context gContext;
    private String mNetRetStr;                 //返回字符串


    private List<ProvideDoctorPatientUserInfo> mProvideDoctorPatientUserInfo = new ArrayList<>();

    public int gNewMessageNum = 0;                                  //新消息数量
    public int gMessageNum = 10;                //可发送的消息数量
    public long gVoiceTime = 20;                 //可拨打语音消息时长（单位：秒）
    public long gVedioTime = 30;                 //可拨打视频消息时长（单位：秒）


    public List<ProvideBasicsDomain> gProvideBasicsDomainJGBJ = new ArrayList<>();             //机构背景
    public List<ProvideBasicsDomain> gProvideBasicsDomainYSZC = new ArrayList<>();                                 //医生职称

    public String hxErrorMessage;
    //    public BDLocationListener myListener = new MyLocationListener();
    public LocationClient mLocationClient = null;

    private MainActivity mainActivity;

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

    @SuppressLint("HandlerLeak")
    public Handler gHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mProvideViewSysUserPatientInfoAndRegion.getPatientCode() == null)
                        mProvideViewSysUserPatientInfoAndRegion.setPatientCode("");
                    if (mProvideViewSysUserPatientInfoAndRegion.getUserPwd() == null)
                        mProvideViewSysUserPatientInfoAndRegion.setUserPwd("");
                    String userAccountHuanxin = mProvideViewSysUserPatientInfoAndRegion.getPatientCode();
                    System.out.println("患者编码" + mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    System.out.println("患者密码" + mProvideViewSysUserPatientInfoAndRegion.getQrCode());


                    EMClient.getInstance().login(userAccountHuanxin, mProvideViewSysUserPatientInfoAndRegion.getQrCode(), new EMCallBack() {
                        @Override
                        public void onSuccess() {

                            System.out.println("登录成功");
                            setNewsMessage();

                            System.out.println("登录成功");
                            gMainActivity.registHX();

                        }

                        @Override
                        public void onError(int i, String s) {

                            System.out.println("登录失败");
                            hxErrorMessage = s;
//                            gMainActivity.setHZTabView();
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


    public void loginIM() {
        /*new Thread(){
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
        }.start();*/
        final String IMTAG = "imlog";

        new Thread() {
            public void run() {
                //注册
                try {
                    EMClient.getInstance().login(mProvideViewSysUserPatientInfoAndRegion.getPatientCode(), mProvideViewSysUserPatientInfoAndRegion.getQrCode(), new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            Log.d(IMTAG, "登录成功");

                            // ** manually load all local groups and conversation
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();

                            // update current user's display name for APNs
                            boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(mProvideViewSysUserPatientInfoAndRegion.getUserName());
                            if (!updatenick) {
                                Log.e(IMTAG, "更新用户昵称");
                            }
                            DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                            String retuser = EMClient.getInstance().getCurrentUser();
                            setNewsMessage();
                            Log.e("iis", retuser);
                        }

                        @Override
                        public void onProgress(int progress, String status) {
                            Log.d(IMTAG, "登录中...");
                        }

                        @Override
                        public void onError(final int code, final String message) {
                            if (code == 101 || code == 204) {
                                try {
                                    EMClient.getInstance().createAccount(mProvideViewSysUserPatientInfoAndRegion.getPatientCode(), mProvideViewSysUserPatientInfoAndRegion.getQrCode());
                                    gHandler.sendEmptyMessage(1);
                                } catch (Exception logex) {
                                    Log.e(IMTAG, "登录失败: " + code);
                                }
                            }
                            Log.d(IMTAG, "登录失败: " + code);
                        }
                    });
                } catch (Exception ex) {
                    Log.e(IMTAG, ex.getMessage());
                }
            }
        }.start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化IM聊天界面
        gContext = getApplicationContext();
        DemoHelper.getInstance().init(gContext);
        //获取本地缓存
        getPersistence();
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(gContext, options);
        LibApp.init(this);
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
                .showStubImage(R.mipmap.hztx)                    // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.hztx)             // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.hztx)                // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                            // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(0))    // 设置成圆角图片
                .build();                                    // 创建配置过得DisplayImageOption对象
        saveIMNumInfo();
        getIMNumInfo();
        initRxHttpUtils();
        //addImMessageListener();
//        startLocate();

    }

    public static Context getConText() {
        return gContext;
    }

    /**
     * 保存用户信息
     */
    public void saveUserInfo() {
        //数据存储,以json字符串的格式保存
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.putString("loginUserInfo", new Gson().toJson(mLoginUserInfo));
        m_persist.putString("viewSysUserDoctorInfoAndHospital", new Gson().toJson(mProvideViewSysUserPatientInfoAndRegion));
        ExtEaseUtils.getInstance().setNickName(mProvideViewSysUserPatientInfoAndRegion.getUserName());
        ExtEaseUtils.getInstance().setImageUrl(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
        ExtEaseUtils.getInstance().setUserId(mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        m_persist.commit();
        DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(mProvideViewSysUserPatientInfoAndRegion.getUserName());
        DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
        DemoHelper.getInstance().setCurrentUserName(mProvideViewSysUserPatientInfoAndRegion.getPatientCode()); // 环信Id
    }

    /**
     * 保存图文音视频通话限制信息
     */
    public void saveIMNumInfo() {
        //数据存储,以json字符串的格式保存
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.putInt("messageNum", gMessageNum);
        m_persist.putLong("voiceTime", gVoiceTime);
        m_persist.putLong("vedioTime", gVedioTime);
        m_persist.commit();
    }

    //获取图文音视频通话限制信息
    public void getIMNumInfo() {
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        gMessageNum = m_persist.getInt("messageNum", 0);
        gVoiceTime = m_persist.getLong("voiceTime", 0);
        gVedioTime = m_persist.getLong("vedioTime", 0);
        System.out.println(gMessageNum);
        System.out.println(gVoiceTime);
        System.out.println(gVedioTime);
        System.out.println(gVedioTime);
    }


    /**
     * 添加消息监听
     */
    private void addImMessageListener() {
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                System.out.println(list);
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });
    }

    /**
     * 退出登录
     */
    public void LoginOut(final Activity activity) {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("tag", "run: " + "您已退出登录!");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, final String message) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }


    //清空数据
    public void cleanPersistence() {
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        m_persist.remove("loginUserInfo");
        m_persist.remove("viewSysUserDoctorInfoAndHospital");
        mLoginUserInfo.setUserPhone("");
        mLoginUserInfo.setUserPwd("");
        m_persist.commit();
    }

    //获取缓存数据
    public void getPersistence() {
        m_persist = new SharedPreferences_DataSave(this, "JYKJDOCTER");
        String userInfoLogin = m_persist.getString("loginUserInfo", "");
        String userInfoSuLogin = m_persist.getString("viewSysUserDoctorInfoAndHospital", "");
        mLoginUserInfo = new Gson().fromJson(userInfoLogin, UserInfo.class);
        try {
            mProvideViewSysUserPatientInfoAndRegion = new Gson().fromJson(userInfoSuLogin, ProvideViewSysUserPatientInfoAndRegion.class);
        } catch (Exception e) {

        }
        System.out.println(mProvideViewSysUserPatientInfoAndRegion);
    }


    /**
     * 初始化ImageLoader
     *
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
        locationOption.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
//开始定位
        locationClient.start();
    }


    public static boolean isApplicationBroughtToBackground(final Context context) {
        //创建activity管理对象
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        //取出RunningTask栈
        List<ActivityManager.RunningTaskInfo> list = activityManager.getRunningTasks(1);
        //判断是否为空
        if (!list.isEmpty()) {
            //取出运行在栈顶的任务进程
            ComponentName componentName = list.get(0).topActivity;
            //判断任务进程的包名是否和想要判断的程序包名相同
            if (componentName.getPackageName().equals(context.getPackageName())) {
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


    /**
     * 全局请求的统一配置（以下配置根据自身情况选择性的配置即可）
     */
    private void initRxHttpUtils() {

        //一个项目多url的配置方法
        RxUrlManager.getInstance().setMultipleUrl(AppUrlConfig.getAllApiUrl());

        RxHttpUtils
                .getInstance()
                .init(this)
                .config()
                //自定义factory的用法
                //.setCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.setConverterFactory(ScalarsConverterFactory.create(),GsonConverterFactory.create(GsonAdapter.buildGson()))
                //配置全局baseUrl
                .setBaseUrl(AppUrlConfig.SERVICE_API_ONLINE_URL)
                //开启全局配置
                .setOkClient(createOkHttp());


//        TODO: 2018/5/31 如果以上OkHttpClient的配置满足不了你，传入自己的 OkHttpClient 自行设置
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        builder
//                .addInterceptor(log_interceptor)
//                .readTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .connectTimeout(10, TimeUnit.SECONDS);
//
//        RxHttpUtils
//                .getInstance()
//                .init(this)
//                .config()
//                .setBaseUrl(BuildConfig.BASE_URL)
//                .setOkClient(builder.build());

    }

    private OkHttpClient createOkHttp() {
        //        获取证书
//        InputStream cerInputStream = null;
//        InputStream bksInputStream = null;
//        try {
//            cerInputStream = getAssets().open("YourSSL.cer");
//            bksInputStream = getAssets().open("your.bks");
//        } catch (IOExceptio
//        n e) {
//            e.printStackTrace();
//        }

        OkHttpClient okHttpClient = new OkHttpConfig
                .Builder(this)
                //添加公共请求头
                .setHeaders(() -> {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("requestClientVerify", HttpNetService.requestClientVerify);
                    hashMap.put("requestLoginTokenValue", HttpNetService.requestLoginTokenValue);
                    return hashMap;

                })
                //添加自定义拦截器
                //.setAddInterceptor()
                //开启缓存策略(默认false)
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //music_btn_paus、在没有网络的时候，去读缓存中的数据。
                .setCache(true)
                .setHasNetCacheTime(1)//默认有网络时候缓存60秒
                //全局持久话cookie,保存到内存（new MemoryCookieStore()）或者保存到本地（new SPCookieStore(this)）
                //不设置的话，默认不对cookie做处理
                .setCookieType(new SPCookieStore(this))

                //可以添加自己的拦截器(比如使用自己熟悉三方的缓存库等等)
                //.setAddInterceptor(null)
                //全局ssl证书认证
                //1、信任所有证书,不安全有风险（默认信任所有证书）
                //.setSslSocketFactory()
                //music_btn_paus、使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(cerInputStream)
                //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(bksInputStream,"123456",cerInputStream)
                //设置Hostname校验规则，默认实现返回true，需要时候传入相应校验规则即可
                //.setHostnameVerifier(null)
                //全局超时配置
                .setReadTimeout(60)
                //全局超时配置
                .setWriteTimeout(60)
                //全局超时配置
                .setConnectTimeout(60)
                //全局是否打开请求log日志
                .setDebug(BuildConfig.DEBUG)
                .build();

        return okHttpClient;
    }

}
