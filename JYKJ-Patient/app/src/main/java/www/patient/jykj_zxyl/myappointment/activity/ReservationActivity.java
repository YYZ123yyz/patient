package www.patient.jykj_zxyl.myappointment.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.contrarywind.listener.OnItemSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.ProvideDoctorSetSchedulingInfoGroupDate;
import entity.ProvideDoctorSetSchedulingInfoGroupTime;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import entity.wdzs.ProvideInteractPatientInterrogation;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.WZXXOrderActivity;
import www.patient.jykj_zxyl.activity.home.twjz.WZXXActivity;
import www.patient.jykj_zxyl.activity.myself.order.RefusedOrderContract;
import www.patient.jykj_zxyl.activity.myself.order.RefusedOrderPresenter;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientCommitBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientDoctorInfoBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientListBean;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.Contract.ReservationContract;
import www.patient.jykj_zxyl.myappointment.Presenter.ResevationPresenter;
import www.patient.jykj_zxyl.myappointment.adapter.Item_Reservation_List_Adapter;
import www.patient.jykj_zxyl.myappointment.adapter.MyAdapter;
import www.patient.jykj_zxyl.myappointment.adapter.Reservation_List_Adapter;
import www.patient.jykj_zxyl.myappointment.adapter.Reservation_TitleAdapter;
import www.patient.jykj_zxyl.myappointment.bean.IDCardBean;
import www.patient.jykj_zxyl.myappointment.dialog.AppDialog;
import www.patient.jykj_zxyl.myappointment.dialog.ErrorDialog;
import www.patient.jykj_zxyl.myappointment.dialog.Reservation_SuccessDialog;
import www.patient.jykj_zxyl.myappointment.dialog.SuccessfulDialog;
import www.patient.jykj_zxyl.util.ActivityUtil;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.MyUtil;
import www.patient.jykj_zxyl.util.ToastUtils;
import www.patient.jykj_zxyl.util.Util;

/*
 * 预约
 * */
public class ReservationActivity extends AbstractMvpBaseActivity<ReservationContract.View,
        ResevationPresenter> implements ReservationContract.View {
    ArrayAdapter<String> adapter;
    private LinearLayout llBack;
    private TextView className;
    private LinearLayout linClass;
    private RecyclerView classRe;
    private ReservationActivity mActivity;
    private JYKJApplication mApp;
    private String userCode;
    private String userName;
    private String loginDoctorPosition;
    private RecyclerView class_rv;
    private LinearLayoutManager layoutManager;
    private Reservation_List_Adapter reservation_list_adapter;
    private List<ReservePatientListBean> listBean;
    private RecyclerView reservation_rv;
    private GridLayoutManager layoutManage;
    private Reservation_TitleAdapter reservation_titleAdapter;
    private Spinner spinnerView;
    private String deviceTimeOfYM;
    private Spinner spinnerplanet;
    private long times;
    private long times1;
    private TextView commit;
    private Reservation_SuccessDialog reservation_successDialog;
    private ErrorDialog errorDialog;
    private SuccessfulDialog successfulDialog;
    private int count = 3;
    private TextView num;
    private String format;
    private Item_Reservation_List_Adapter item_reservation_list_adapter;
    private int blockNo;
    private String reserveDateRosterCode;
    private String status;
    private long selectedItemId;
    private String selectedItem;
    private int currentDatePos;
    private String currentTime;
    private String orderCode;
    private String odertype;
    private String reserveRosterDateCode;
    private String commitconfim;
    private String reserveProjectCode;
    private String mainDoctorName;
    private String reserveProjectName;
    private int viewReserveToDoctorCount;
    private ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend;
    private ProvideDoctorSetSchedulingInfoGroupDate provideDoctorSetSchedulingInfoGroupDate;
    private String linPhone;
    private String mainDoctorCode;
    private String viewTimesPeriod;
    private ProvideInteractPatientInterrogation mProvideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();
    private String reserveStatus;
    private AppDialog appDialog;
    private TextView determine;
    private TextView tv_cancel;
    private TextView appdialog_tv;
    private int treatmentType;
    private List<ReservePatientListBean.ItemTimesBean> itemTimes;
    private String errMsg;
    private TextView msg1;
    private TextView msg2;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_reservation;
    }

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        listBean = new ArrayList<>();
    }

    @Override
    protected void initView() {
        super.initView();
        provideViewDoctorExpertRecommend = new ProvideViewDoctorExpertRecommend();
        provideDoctorSetSchedulingInfoGroupDate = new ProvideDoctorSetSchedulingInfoGroupDate();
        Intent intent = getIntent();
        userCode = intent.getStringExtra("userCode");
        userName = intent.getStringExtra("userName");
        linPhone = intent.getStringExtra("linPhone");
        loginDoctorPosition = intent.getStringExtra("loginDoctorPosition");
        status = intent.getStringExtra("status");
        Log.e("TAG", "initView:状态 " + status);
        mApp = (JYKJApplication) getApplication();
        ActivityUtil.setStatusBarMain(this);
        spinnerView = findViewById(R.id.spinnerplanet);
        //当前时间
        deviceTimeOfYM = DateUtils.getDeviceTimeOfYMD();
        class_rv = findViewById(R.id.class_rv);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        class_rv.setLayoutManager(layoutManager);
        llBack = findViewById(R.id.ll_back);
        linClass = findViewById(R.id.lin_class);
        //日历展示
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        reservation_rv = findViewById(R.id.reservation_rv);
        reservation_rv.setLayoutManager(ms);
        commit = findViewById(R.id.commit);
        //预约中
        successfulDialog = new SuccessfulDialog(this);
        //预约失败dialog
        errorDialog = new ErrorDialog(this);


        //预约成功
        reservation_successDialog = new Reservation_SuccessDialog(this);
        //有操作的dialog
        appDialog = new AppDialog(this);
        addListener();
    }

    @Override
    protected void initData() {
        super.initData();
        //类型
        mPresenter.sendReservationClassRequest("900060");
        //预约头部查询
        mPresenter.sendReservationTiteRequest(mApp.loginDoctorPosition, userCode, userName);
        //列表查询
        mPresenter.sendReservationListRequest(mApp.loginDoctorPosition, userCode, userName,
                deviceTimeOfYM, 1);
    }

    private void addListener() {
        //返回按钮
        llBack.setOnClickListener(v -> finish());
        //提交
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //预约提交
                if (TextUtils.isEmpty(deviceTimeOfYM)) {
                    Toast.makeText(ReservationActivity.this, "请选择预约时间", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(reserveDateRosterCode)){
                    Toast.makeText(ReservationActivity.this, "请选择时间段", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.sendReservationCommitRequest(mApp.loginDoctorPosition, "1", userCode,
                        userName, mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),
                        mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(), reserveDateRosterCode, status, deviceTimeOfYM, 1 + "",
                        selectedItem, blockNo + "", "");
            }
        });
        //预约成功的确定按钮
        reservation_successDialog.setOnClickListener(new Reservation_SuccessDialog.OnClickListener() {
            @Override
            public void onClickSucessBtn() {
                if(status.equals("1")){
                    //确认
                    Intent intent = new Intent();
                    intent.setClass(context, WZXXOrderActivity.class);
                    intent.putExtra("orderID", orderCode);
                    intent.putExtra("orderType", reserveProjectCode);
                    intent.putExtra("provideViewDoctorExpertRecommend", provideViewDoctorExpertRecommend);
                    intent.putExtra("provideDoctorSetSchedulingInfoGroupDate", provideDoctorSetSchedulingInfoGroupDate);
                    intent.putExtra("provideInteractPatientInterrogation", mProvideInteractPatientInterrogation);
                    startActivity(intent);
                    reservation_successDialog.dismiss();
                }
               else{
                    Intent intent = new Intent(ReservationActivity.this, MyAppointmentActivity.class);
                    startActivity(intent);
                    finish();
                    reservation_successDialog.dismiss();
                }


            }
        });
    }


    @Override
    public void showLoading(int code) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //获取预约头部
    @Override
    public void getReservationTiteResult(List<ReservePatientDoctorInfoBean> reservePatientDoctorInfoBean) {
        if (reservePatientDoctorInfoBean != null) {
            reservation_titleAdapter = new Reservation_TitleAdapter(reservePatientDoctorInfoBean);
            reservation_rv.setAdapter(reservation_titleAdapter);
            reservation_titleAdapter.setOnClickListener(new Reservation_TitleAdapter.onClickListener() {
                @Override
                public void onClick(View view, int position) {
                    reservation_titleAdapter.setDefSelect(position);
                    //预约时间
                    times = reservePatientDoctorInfoBean.get(position).getTimes();
                    Date date = new Date(ReservationActivity.this.times);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                    format = sdf.format(date);
                    deviceTimeOfYM=format;
                    listBean.clear();
                    mPresenter.sendReservationListRequest(mApp.loginDoctorPosition, userCode, userName,
                            deviceTimeOfYM, 1);
                }
            });
            currentDatePos = getCurrentDatePos(reservePatientDoctorInfoBean);
            reservation_titleAdapter.setDefSelect(currentDatePos);

        }

    }

    private int getCurrentDatePos(List<ReservePatientDoctorInfoBean> patientDoctorInfoBeans) {
        int pos = -1;
        for (int i = 0; i < patientDoctorInfoBeans.size(); i++) {
            long times = patientDoctorInfoBeans.get(i).getTimes();
            currentTime = DateUtils.getDeviceTimeOfYMD();
            String stringTimeOfYMD = DateUtils.getStringTimeOfYMD(times);
            if (currentTime.equals(stringTimeOfYMD)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    //获取预约列表
    @Override
    public void getReservationListResult(List<ReservePatientListBean> reservePatientListBeans) {
        if (reservePatientListBeans != null) {
            listBean.addAll(reservePatientListBeans);
            reservation_list_adapter = new Reservation_List_Adapter(listBean);
            class_rv.setAdapter(reservation_list_adapter);
            reservation_list_adapter.setOnClickListener(new Reservation_List_Adapter.onClickListener() {

                @Override
                public void onClick(View view, int position) {
                    viewTimesPeriod = listBean.get(position).getViewTimesPeriod();
                    item_reservation_list_adapter = new Item_Reservation_List_Adapter(listBean.get(position).getItemTimes());
                    itemTimes = listBean.get(position).getItemTimes();
                    item_reservation_list_adapter.setPosition(position);
                    item_reservation_list_adapter.setDate(itemTimes);
                    item_reservation_list_adapter.notifyDataSetChanged();
                }

                @Override
                public void onClickChoosedItem(ReservePatientListBean.ItemTimesBean itemTimesBean) {
                    blockNo = itemTimesBean.getBlockNo();
                }

                @Override
                public void onClickChoosedReseveCode(ReservePatientListBean reservePatientListBean) {
                    reserveDateRosterCode = reservePatientListBean.getReserveDateRosterCode();
                }

            });

        }
    }

    /**
     * 获取预约列表失败
     *
     * @param msg
     */
    @Override
    public void getReservationListResultError(String msg) {
        //    ToastUtils.showToast(msg);
    }

    /**
     * 您有已签约的服务
     *
     * @param confim 确认Id
     * @param msg
     */
    @Override
    public void getReservationCommitConfimResult(String confim, String msg) {
        commitconfim = confim;
        appDialog.show();
        TextView tv = appDialog.findViewById(R.id.appdialog_tv);
        tv.setText("您有已签约的服务，可以在我的签约医生列表进行预约。\n您确定继续该付费项目的预约吗？");
        //取消
        appDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appDialog.dismiss();
            }
        });
        //确定
        appDialog.findViewById(R.id.tv_determine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sendReservationCommitRequest(mApp.loginDoctorPosition, "1", userCode,
                        userName, mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),
                        mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(), reserveDateRosterCode, status, deviceTimeOfYM, 1 + "",
                        selectedItem, blockNo + "", confim);
                appDialog.dismiss();
            }
        });
    }

    /**
     * 身份证 认证
     *
     * @param msg
     */
    @Override
    public void getReservationCommitIdCardCheckResult(String msg) {
        new AlertDialog.Builder(ReservationActivity.this).setTitle("请先去认证")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        MyUtil.cleanExternalCache(ReservationActivity.this);
                        MyUtil.cleanInternalCache(ReservationActivity.this);
                        try {
                            Intent intent = new Intent(ReservationActivity.this, VerifiedActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("取消", null).show();

    }

    /**
     * 预约成功
     *
     * @param reservePatientCommitBeans
     */
    @Override
    public void getReservationCommitResult(ReservePatientCommitBean reservePatientCommitBeans) {
        if (reservePatientCommitBeans != null) {
            reserveStatus = reservePatientCommitBeans.getReserveStatus();
            reserveRosterDateCode = reservePatientCommitBeans.getReserveRosterDateCode();
            orderCode = reservePatientCommitBeans.getOrderCode();
            reserveProjectCode = reservePatientCommitBeans.getReserveProjectCode();
            treatmentType = reservePatientCommitBeans.getTreatmentType();
            //医生名字
            mainDoctorName = reservePatientCommitBeans.getMainDoctorName();
            //医生Code
            mainDoctorCode = reservePatientCommitBeans.getMainDoctorCode();
            //预约项目
            reserveProjectName = reservePatientCommitBeans.getReserveProjectName();
            //多少位
            viewReserveToDoctorCount = reservePatientCommitBeans.getViewReserveToDoctorCount();
            //医生code
            provideViewDoctorExpertRecommend.setDoctorCode(reservePatientCommitBeans.getMainDoctorCode());
            provideViewDoctorExpertRecommend.setUserName(mainDoctorName);
            provideViewDoctorExpertRecommend.setLinkPhone(linPhone);
            mApp.gPayOrderCode = orderCode;
            mProvideInteractPatientInterrogation.setDoctorCode(mainDoctorCode);
            mProvideInteractPatientInterrogation.setDoctorName(mainDoctorName);
            float price = reservePatientCommitBeans.getOrderAndSignPrice();
            if (reserveProjectCode.equals("1")) {
                provideViewDoctorExpertRecommend.setImgTextPrice(price);
            } else if (reserveProjectCode.equals("2")) {
                provideViewDoctorExpertRecommend.setAudioPrice(price);
            } else if (reserveProjectCode.equals("3")) {
                provideViewDoctorExpertRecommend.setVideoPrice(price);
            } else if (reserveProjectCode.equals("4")) {
                provideViewDoctorExpertRecommend.setSigningPrice(price);
            }
            String s = DateUtils.stampToDate(reservePatientCommitBeans.getReserveConfigStart());
            Date date = DateUtils.getDate(s);
            provideDoctorSetSchedulingInfoGroupDate.setWorkDate(date);
            provideDoctorSetSchedulingInfoGroupDate.setChoice(true);
        }
        mHandler.sendEmptyMessageDelayed(200, 1000);
        successfulDialog.show();
    }

    /**
     * 预约成功的dialog
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 200) {
                if (count > 0) {
                    num = successfulDialog.findViewById(R.id.success_num);
                    num.setText(count + "");
                    count--;
                    mHandler.sendEmptyMessageDelayed(200, 1000);
                } else {
                    successfulDialog.dismiss();
                    //预约成功
                    reservation_successDialog.show();
                    //医生名字
                    TextView name = reservation_successDialog.findViewById(R.id.doctor_name);
                    name.setText(mainDoctorName);
                    //预约项目
                    TextView doctor_class = reservation_successDialog.findViewById(R.id.doctor_class);
                    doctor_class.setText(reserveProjectName);
                    //号源
                    TextView patient_number = reservation_successDialog.findViewById(R.id.patient_number);
                    patient_number.setText("当天号源第" + viewReserveToDoctorCount + "位");
                }
            }
        }
    };


    /**
     * 预约失败
     *
     * @param msg
     */
    @Override
    public void getReservationCommitResultError(String msg) {
        handler.sendEmptyMessageDelayed(100, 1000);
        errMsg = msg;
        successfulDialog.show();

    }

    /**
     *  未支付的订单
     * @param reservePatientCommitBeans
     */
    @Override
    public void getReservationunpaidResultError(ReservePatientCommitBean reservePatientCommitBeans) {
        if(reservePatientCommitBeans!=null){
             mainDoctorCode = reservePatientCommitBeans.getReservePatientDoctorInfo().getMainDoctorCode();
            Log.e("TAG", "未 "+mainDoctorCode );
            mainDoctorName=  reservePatientCommitBeans.getReservePatientDoctorInfo().getMainDoctorName();
            Log.e("TAG", "未 "+mainDoctorName );
            orderCode = reservePatientCommitBeans.getReservePatientDoctorInfo().getOrderCode();
            Log.e("TAG", "未 "+orderCode );
            int treatmentType = reservePatientCommitBeans.getReservePatientDoctorInfo().getTreatmentType();
            Log.e("TAG", "未 "+treatmentType );
            //医生code
            provideViewDoctorExpertRecommend.setDoctorCode(reservePatientCommitBeans.getReservePatientDoctorInfo().getMainDoctorCode());
            provideViewDoctorExpertRecommend.setUserName(mainDoctorName);
            //   provideViewDoctorExpertRecommend.setLinkPhone(linPhone);
            mApp.gPayOrderCode = orderCode;
            mProvideInteractPatientInterrogation.setDoctorCode(mainDoctorCode);
            mProvideInteractPatientInterrogation.setDoctorName(mainDoctorName);
            float price =
                    (float) reservePatientCommitBeans.getReservePatientDoctorInfo().getOrderAndSignPrice();
            Log.e("TAG", "未 "+price );
            if (treatmentType==1) {
                provideViewDoctorExpertRecommend.setImgTextPrice(price);
            } else if (treatmentType==2) {
                provideViewDoctorExpertRecommend.setAudioPrice(price);
            } else if (treatmentType==3) {
                provideViewDoctorExpertRecommend.setVideoPrice(price);
            } else if (treatmentType==4) {
                provideViewDoctorExpertRecommend.setSigningPrice(price);
            }
            String ss = DateUtils.stampToDate(reservePatientCommitBeans.getReservePatientDoctorInfo().getReserveConfigStart());
            Log.e("TAG", "未 "+ss );
            Date date = DateUtils.getDate(ss);
            provideDoctorSetSchedulingInfoGroupDate.setWorkDate(date);
            provideDoctorSetSchedulingInfoGroupDate.setChoice(true);
            appDialog.show();
            TextView tv = appDialog.findViewById(R.id.appdialog_tv);
            tv.setText(reservePatientCommitBeans.getMessage());
            //取消
            appDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    appDialog.dismiss();
                }
            });
            //确定
            TextView tv_determine = appDialog.findViewById(R.id.tv_determine);
            tv_determine.setText("去支付");
            tv_determine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //确认
                    Intent intent = new Intent();
                    intent.setClass(context, WZXXOrderActivity.class);
                     intent.putExtra("orderID", orderCode);
                    String s = String.valueOf(treatmentType);
                    intent.putExtra("orderType", s);
                    intent.putExtra("provideViewDoctorExpertRecommend", provideViewDoctorExpertRecommend);
                    intent.putExtra("provideDoctorSetSchedulingInfoGroupDate", provideDoctorSetSchedulingInfoGroupDate);
                    intent.putExtra("provideInteractPatientInterrogation", mProvideInteractPatientInterrogation);
                    startActivity(intent);
                    appDialog.dismiss();
                    finish();
                }
            });
        }

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                if (count > 0) {
                    num = successfulDialog.findViewById(R.id.success_num);
                    num.setText(count + "");
                    count--;
                    handler.sendEmptyMessageDelayed(100, 1000);
                } else {
                    successfulDialog.dismiss();
                    //errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    if (TextUtils.isEmpty(errMsg)){
                        errorDialog.show();
                     /*   errorDialog.findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });*/
                    }else {
                        ErrorDialog  errorMsgDialog = new ErrorDialog(ReservationActivity.this,errMsg);
                        errorMsgDialog.show();
                       /* errorDialog.findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });*/
                    }


//                    errorDialog.setFiledMsg(errMsg);



                }
            }
        }
    };

    @Override
    public void getReservationDailog() {

    }

    //预约中
    private void reservation_success() {
        successfulDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successfulDialog.show();
        handler.sendEmptyMessageDelayed(100, 1000);
    }


    @Override
    public void getReservationClassResult(List<BaseReasonBean> baseReasonBeans) {
        if (baseReasonBeans != null) {
            List<BaseReasonBean> base = new ArrayList<BaseReasonBean>();
            for (BaseReasonBean baseReasonBean : baseReasonBeans) {
                base.add(baseReasonBean);
            }
            MyAdapter myAdapter = new MyAdapter(this, base);
            //绑定Adapter
            spinnerView.setAdapter(myAdapter);

            List<String> m = new ArrayList<>();
            for (BaseReasonBean baseReasonBean : baseReasonBeans) {
                m.add(baseReasonBean.getAttrName());
            }
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, m);
            adapter.setDropDownViewResource(android.
                    R.layout.simple_spinner_dropdown_item);
            spinnerView.setAdapter(adapter);
            spinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedItemId = spinnerView.getSelectedItemId();
                    selectedItem = (String) spinnerView.getSelectedItem();
                    int select = (int) selectedItemId;
                    //预约查询列表查询
                    mPresenter.sendReservationListRequest(mApp.loginDoctorPosition, userCode, userName,
                            deviceTimeOfYM, select);


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }


    @Override
    public void getReservationIDCardResult(String msg) {

    }

    @Override
    public void getReservationIDCardResultError(String msg) {

    }

    @Override
    public void getReservationCancelResult(boolean isSucess, String msg) {

    }

    @Override
    public void getSearchSignPatientDoctorOrderResult(OrderDetialBean orderDetialBean) {

    }


}