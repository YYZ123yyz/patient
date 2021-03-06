package www.patient.jykj_zxyl.activity.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import entity.ProvideInteractOrderInfo;
import entity.ProvideViewMyDoctorOrderAndTreatment;
import entity.ProvideWechatPayModel;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.home.patient.WZXXOrderActivity;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeTJZJAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.pay.PayResult;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.Util;
import www.patient.jykj_zxyl.util.widget.AuthorityDialog;
import www.patient.jykj_zxyl.wxapi.WXPayEntryActivity;


/**
 * 消息中心 ==》订单消息 ==》订单支付
 */
public class OrderMessage_OrderPayActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FragmentHomeTJZJAdapter mAdapter;
    private LinearLayout llBack;

    public ProgressDialog mDialogProgress = null;

    private Context mContext;                                       //
    private OrderMessage_OrderPayActivity mActivity;
    private JYKJApplication mApp;

    private String mNetRetStr;                 //返回字符串
    private Handler mHandler;


    private TextView mCommit;                    //提交

    private ProvideInteractOrderInfo provideInteractOrderInfo = new ProvideInteractOrderInfo();               //订单信息


    private TextView tv_ddlx;                    //订单类型
    private TextView tv_zfzt;                    //支付状态
    private TextView tv_zxys;                    //咨询医生
    private TextView tv_xzr;                    //询诊人
    private TextView tv_zxdh;                    //诊询电话
    private TextView tv_ddrq;                    //订单日期
    private TextView tv_fwkssj;                    //服务开始时间
    private TextView tv_fwjzsj;                    //服务截止时间
    private TextView tv_fwzj;                    //服务总价
    private TextView tv_ddms1;                    //订单描述1
    private TextView tv_sfk;                    //实付款

    private TextView tv_serviceName;             //名称
    private LinearLayout tv_jzsj;                      //截止时间

//    private         TextView                    tv_kszx;                    //开始咨询

    private TextView tv_payModel;                    //支付类型
    private ImageView iv_payModel;                    //支付类型图片

    public IWXAPI msgApi;

    private ProvideViewMyDoctorOrderAndTreatment provideViewMyDoctorOrderAndTreatment;

    private AuthorityDialog mAuthorityDialog;

    private String pay_appid;
    private String pay_productid;
    private static final int SDK_PAY_FLAG = 3;
    private String orderId;
    private String orderCode;

    /**
     * 展示数据
     *
     * @param provideViewMyDoctorOrderAndTreatment
     */
    private void showDate(ProvideViewMyDoctorOrderAndTreatment provideViewMyDoctorOrderAndTreatment) {
        tv_ddlx.setText(provideViewMyDoctorOrderAndTreatment.getTreatmentTypeName());
        tv_zfzt.setText(provideViewMyDoctorOrderAndTreatment.getFlagOrderStateName());
        tv_zxys.setText(provideViewMyDoctorOrderAndTreatment.getMainDoctorName());
        tv_xzr.setText(provideViewMyDoctorOrderAndTreatment.getMainUserName());
        tv_zxdh.setText(provideViewMyDoctorOrderAndTreatment.getPatientPhone());
        if (provideViewMyDoctorOrderAndTreatment.getCreateDate() != 0)
            tv_ddrq.setText(DateUtils.getStringTimeOfSSS(provideViewMyDoctorOrderAndTreatment.getCreateDate()));

        tv_fwjzsj.setText(DateUtils.getStringTimeOfSSS(provideViewMyDoctorOrderAndTreatment.getReserveTime()));
        if (provideViewMyDoctorOrderAndTreatment.getServiceTotal() == 0)
            tv_fwzj.setText("0.0元");
        else
            tv_fwzj.setText(provideViewMyDoctorOrderAndTreatment.getServiceTotal() + "元");
        tv_ddms1.setText(provideViewMyDoctorOrderAndTreatment.getOrderDesc());
        if (provideViewMyDoctorOrderAndTreatment.getActualPayment() == 0)
            tv_sfk.setText("0.0元");
        else
            tv_sfk.setText(provideViewMyDoctorOrderAndTreatment.getActualPayment() + "元");

        switch (provideViewMyDoctorOrderAndTreatment.getTreatmentType()) {
            case 1:
                tv_jzsj.setVisibility(View.VISIBLE);
                tv_serviceName.setText("服务开始时间");
                tv_fwkssj.setText(DateUtils.getStringTimeOfSSS(provideViewMyDoctorOrderAndTreatment.getReserveTime()));
                break;
            case 2:
                tv_jzsj.setVisibility(View.GONE);
                tv_serviceName.setText("预约服务时间");
                if (provideViewMyDoctorOrderAndTreatment.getReserveTime() != 0)
                    tv_fwkssj.setText(DateUtils.getStringTimeOfSSS(provideViewMyDoctorOrderAndTreatment.getReserveTime()));
                break;
            case 3:
                tv_jzsj.setVisibility(View.GONE);
                tv_serviceName.setText("预约服务时间");
                if (provideViewMyDoctorOrderAndTreatment.getReserveTime() != 0)
                    tv_fwkssj.setText(DateUtils.getStringTimeOfSSS(provideViewMyDoctorOrderAndTreatment.getReserveTime()));
                break;
            case 4:
                tv_jzsj.setVisibility(View.VISIBLE);
                tv_serviceName.setText("服务开始时间");
                tv_fwkssj.setText(DateUtils.getStringTimeOfSSS(provideViewMyDoctorOrderAndTreatment.getReserveTime()));
                break;
            case 5:
                tv_jzsj.setVisibility(View.GONE);
                tv_serviceName.setText("预约服务时间");
                if (provideViewMyDoctorOrderAndTreatment.getReserveTime() != 0)
                    tv_fwkssj.setText(DateUtils.getStringTimeOfSSS(provideViewMyDoctorOrderAndTreatment.getReserveTime()));
                break;
        }

        //待付款
        if (provideViewMyDoctorOrderAndTreatment.getFlagOrderState() == 1) {
            mCommit.setVisibility(View.VISIBLE);
//           tv_kszx.setVisibility(View.GONE);
        } else if (provideViewMyDoctorOrderAndTreatment.getFlagOrderState() == 2) {
            mCommit.setVisibility(View.GONE);
//            tv_kszx.setVisibility(View.VISIBLE);
        } else {
            mCommit.setVisibility(View.GONE);
//           tv_kszx.setVisibility(View.GONE);
        }


            //0:未知;1:微信支付;2:支付宝支付;3:银联支付;
            switch (provideViewMyDoctorOrderAndTreatment.getPaymentMode()) {
                case 0:
                    tv_payModel.setText("未知");
                    iv_payModel.setVisibility(View.GONE);
                    break;
                case 1:
                    tv_payModel.setText("微信");
                    iv_payModel.setBackgroundResource(R.mipmap.zffs_wxzf);
                    break;
                case 2:
                    tv_payModel.setText("支付宝");
                    iv_payModel.setBackgroundResource(R.mipmap.pay_zfb);
                    break;
                case 3:
                    tv_payModel.setText("银联支付");
                    iv_payModel.setVisibility(View.GONE);
                    break;

        }
    }


    private void initView() {
        tv_ddlx = (TextView) this.findViewById(R.id.tv_ddlx);
        tv_zfzt = (TextView) this.findViewById(R.id.tv_zfzt);
        tv_zxys = (TextView) this.findViewById(R.id.tv_zxys);
        tv_xzr = (TextView) this.findViewById(R.id.tv_xzr);
        tv_zxdh = (TextView) this.findViewById(R.id.tv_zxdh);
        tv_ddrq = (TextView) this.findViewById(R.id.tv_ddrq);
        tv_fwkssj = (TextView) this.findViewById(R.id.tv_fwkssj);
        tv_fwjzsj = (TextView) this.findViewById(R.id.tv_fwjzsj);
        tv_fwzj = (TextView) this.findViewById(R.id.tv_fwzj);
        tv_ddms1 = (TextView) this.findViewById(R.id.tv_ddms1);
        tv_sfk = (TextView) this.findViewById(R.id.tv_sfk);
//        tv_kszx = (TextView)this.findViewById(R.id.tv_kszx);
//        tv_kszx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(mContext,WDYSActivity.class));
//            }
//        });
        tv_serviceName = (TextView) this.findViewById(R.id.tv_serviceName);
        tv_jzsj = (LinearLayout) this.findViewById(R.id.tv_jzsj);
        mCommit = (TextView) this.findViewById(R.id.commit);
        mCommit.setOnClickListener(new ButtonClick());

        tv_payModel = (TextView) this.findViewById(R.id.tv_payModel);
        iv_payModel = (ImageView) this.findViewById(R.id.iv_payModel);
        findViewById(R.id.ll_back).setOnClickListener(new ButtonClick());

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


    }

    /**
     * 支付
     */
    public void zf(int payModel) {
        mAuthorityDialog.cancel();
        getProgressBar("请稍候", "正在获取数据。。。");
        ProvideViewMyDoctorOrderAndTreatment provideViewMyDoctorOrderAndTreatmentParment = new ProvideViewMyDoctorOrderAndTreatment();
        provideViewMyDoctorOrderAndTreatmentParment.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewMyDoctorOrderAndTreatmentParment.setRequestClientType("1");
        provideViewMyDoctorOrderAndTreatmentParment.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewMyDoctorOrderAndTreatmentParment.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        if(!TextUtils.isEmpty(orderCode)){
            provideViewMyDoctorOrderAndTreatmentParment.setOrderCode(orderCode);
        }else{
            provideViewMyDoctorOrderAndTreatmentParment.setOrderCode(orderId);
        }
        provideViewMyDoctorOrderAndTreatmentParment.setFlagPayType(payModel + "");
        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideViewMyDoctorOrderAndTreatmentParment);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.patient.jykj_zxyl.application.Constant.SERVICEURL + "msgDataControlle/operPatientOrderPay");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }.start();
    }

    /**
     * 关闭支付
     */
    public void gbZF() {
        mAuthorityDialog.cancel();
    }


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;

                case R.id.zf_weixin:

                    break;

                case R.id.zf_zhifubao:

                    break;

                case R.id.commit:
                    if (provideViewMyDoctorOrderAndTreatment == null) {
                        Toast.makeText(mContext, "未获取到订单", Toast.LENGTH_SHORT).show();
                    }
                    mAuthorityDialog = new AuthorityDialog(mContext, mActivity);
                    mAuthorityDialog.setmProvideViewMyDoctorOrderAndTreatment(provideViewMyDoctorOrderAndTreatment);
                    mAuthorityDialog.show();
                    break;

            }
        }
    }


    /**
     * 获取n个月后的时间
     *
     * @param inputDate
     * @param number
     * @return
     */
    public static String getAfterMonth(String inputDate, int number) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(inputDate);//初始日期
        } catch (Exception e) {

        }
        c.setTime(date);//设置日历时间
        c.add(Calendar.MONTH, number);//在日历的月份上增加6个月
        String strDate = sdf.format(c.getTime());//的到你想要得6个月后的日期
        return strDate;
    }

    /**
     * 比较两个时间大小
     *
     * @param time1
     * @param time2
     * @return
     * @throws ParseException
     */
    public boolean compare(String time1, String time2) throws ParseException {
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //将字符串形式的时间转化为Date类型的时间
        Date a = sdf.parse(time1);
        Date b = sdf.parse(time2);
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if (a.before(b))
            return true;
        else
            return false;
		/*
		 * 如果你不喜欢用上面这个太流氓的方法，也可以根据将Date转换成毫秒
		if(a.getTime()-b.getTime()<0)
			return true;
		else
			return false;
		*/
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemessage_ddzf);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gPayCloseActivity.add(mActivity);
        provideInteractOrderInfo = (ProvideInteractOrderInfo) getIntent().getSerializableExtra("provideInteractOrderInfo");
        if(provideInteractOrderInfo!=null){
            orderCode = provideInteractOrderInfo.getOrderCode();
        }
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

//        provideViewDoctorExpertRecommend = (ProvideViewDoctorExpertRecommend) getIntent().getSerializableExtra("provideViewDoctorExpertRecommend");
//        provideDoctorSetSchedulingInfoGroupDate= (ProvideDoctorSetSchedulingInfoGroupDate) getIntent().getSerializableExtra("provideDoctorSetSchedulingInfoGroupDate");
//        mOrderNum = getIntent().getStringExtra("orderID");
//        mOrderType = getIntent().getStringExtra("orderType");
//        if ("6".equals(mOrderType))
//        {
//            mXYEntity = (XYEntiy) getIntent().getSerializableExtra("xyEntiy");
//        }
        initView();
        initHandler();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDate();
    }

    /**
     * 获取订单详情
     */
    private void getDate() {
        getProgressBar("请稍候", "正在获取数据。。。");
        ProvideViewMyDoctorOrderAndTreatment provideViewMyDoctorOrderAndTreat = new ProvideViewMyDoctorOrderAndTreatment();
        provideViewMyDoctorOrderAndTreat.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewMyDoctorOrderAndTreat.setRequestClientType("1");
        provideViewMyDoctorOrderAndTreat.setSearchPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewMyDoctorOrderAndTreat.setSearchPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        if(!TextUtils.isEmpty(orderCode)){
            provideViewMyDoctorOrderAndTreat.setOrderCode(orderCode);
        }else{
            provideViewMyDoctorOrderAndTreat.setOrderCode(orderId);
        }
        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(provideViewMyDoctorOrderAndTreat);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, Constant.SERVICEURL + "msgDataControlle/searchPatientMsgInteractOrderInfoDetail");
                    Log.e("TAG", "run: "+ mNetRetStr);
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }


    @SuppressLint("HandlerLeak")
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                        else if (netRetEntity.getResCode() == 1) {
                            provideViewMyDoctorOrderAndTreatment = JSON.parseObject(netRetEntity.getResJsonData(), ProvideViewMyDoctorOrderAndTreatment.class);
                            showDate(provideViewMyDoctorOrderAndTreatment);
                        }
                        break;
                    case 1:
                        cacerProgress();
//                        ProvideWechatPayModel provideWechatPayModel = JSON.parseObject(mNetRetStr,ProvideWechatPayModel.class);
//                        //开始调起微信支付
//                        weichatPay(provideWechatPayModel);
                        if (mNetRetStr.contains("&sign_type=")) {
                            //mNetRetStr = mNetRetStr.substring(mNetRetStr.indexOf("&sign=")+"&sign=".length(),mNetRetStr.indexOf("&sign_type="));
                            pay_appid = mNetRetStr.substring(mNetRetStr.indexOf("&app_id=") + "&app_id=".length(), mNetRetStr.indexOf("&biz_content="));
                            pay_productid = mNetRetStr.substring(mNetRetStr.indexOf("&app_id=") + "&app_id=".length(), mNetRetStr.indexOf("&biz_content="));
                            netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                            sendAliPay(netRetEntity.getResJsonData());
                        } else {
                            netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                            if (null != netRetEntity.getResMsg() && netRetEntity.getResMsg().length() > 0) {
                                Toast.makeText(mContext, netRetEntity.getResMsg(), Toast.LENGTH_SHORT).show();
                            }
                            if (netRetEntity.getResCode() == 1) {
                                ProvideWechatPayModel provideWechatPayModel = JSON.parseObject(netRetEntity.getResJsonData(), ProvideWechatPayModel.class);
                                //开始调起微信支付
                                pay_appid = provideWechatPayModel.getAppId();
                                pay_productid = pay_appid;
                                weichatPay(provideWechatPayModel);
                            }
                        }

                        break;
                    case SDK_PAY_FLAG:
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();
                        Intent intent = new Intent(OrderMessage_OrderPayActivity.this, WXPayEntryActivity.class);
                        intent.putExtra(WXPayEntryActivity.PAY_MESSAGE, resultInfo);
                        intent.putExtra("pay_appid", pay_appid);
                        intent.putExtra("pay_productid", pay_productid);
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(WXEntryActivity.this, getString(R.string.pay_success) + payResult);
                            intent.putExtra(WXPayEntryActivity.PAY_STATE, WXPayEntryActivity.SUCCESS);
                        } else if (TextUtils.equals(resultStatus, "6001")) {
//                         用户取消
                            intent.putExtra(WXPayEntryActivity.PAY_STATE, WXPayEntryActivity.CANCEL);
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(WXEntryActivity.this, getString(R.string.pay_failed) + payResult);
                            intent.putExtra(WXPayEntryActivity.PAY_STATE, WXPayEntryActivity.FAILURE);
                        }
                        startActivity(intent);
                        //PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        break;
                }
            }
        };
    }

    public void sendAliPay(final String orderInfo) {
        //final String orderInfo = provideWechatPayModel.getSign();
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
//                mApp.gPayOrderCode =  mOrderNum;
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        new Thread(payRunnable).start();
    }


    /**
     * 开始调起微信支付
     */
    private void weichatPay(ProvideWechatPayModel provideWechatPayModel) {
        //将appid注册到微信
        msgApi = WXAPIFactory.createWXAPI(mContext, null);
        boolean a = msgApi.registerApp(provideWechatPayModel.getAppId());
        //调起微信支付
        PayReq request = new PayReq();
        request.appId = provideWechatPayModel.getAppId();
        request.partnerId = provideWechatPayModel.getPartnerid();
        String prepare_id = provideWechatPayModel.getPrepayid();
        request.prepayId = prepare_id;
        request.packageValue = "Sign=WXPay";
        request.nonceStr = provideWechatPayModel.getNonceStr();
        request.timeStamp = provideWechatPayModel.getTimeStamp();
        request.sign = provideWechatPayModel.getSign();
        request.signType = "MD5";
        boolean result = msgApi.sendReq(request);
        System.out.println();
    }


    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
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


    /**
     * 显示医院
     */
    private void showChoiceHospitalText(int index) {
//        mChoiceHospitalText.setText(mProvideHospitalInfos.get(index).getHospitalName());
//        provideViewDoctorExpertRecommend.setSearchHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
//        provideViewDoctorExpertRecommend.setSearchDepartmentId("");
//        provideViewDoctorExpertRecommend.setSearchDepartmentSecondId("");
//        mChoiceDepartmentSText.setText("请选择二级科室");
//        mChoiceDepartmentFText.setText("请选择一级科室");
        //获取一级科室
//        getProgressBar("请稍候。。。。","正在获取数据");
//        new Thread(){
//            public void run(){
//                try {
        //获取一级科室
//                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
//                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
//                    provideHospitalDepartment.setHospitalDepartmentId(0);
//                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
//                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
//                    if (netRetEntity.getResCode() == 0) {
//                        NetRetEntity retEntity = new NetRetEntity();
//                        retEntity.setResCode(0);
//                        retEntity.setResMsg("获取一级科室信息失败："+netRetEntity.getResMsg());
//                        mNetRetStr = new Gson().toJson(retEntity);
//                        mHandler.sendEmptyMessage(0);
//                        return;
//                    }
//                    //一级科室信息获取成功
//                    mProvideHospitalDepartmentFInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
//                } catch (Exception e) {
//                    NetRetEntity retEntity = new NetRetEntity();
//                    retEntity.setResCode(0);
//                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
//                    mNetRetStr = new Gson().toJson(retEntity);
//                    e.printStackTrace();
//                }
//                mHandler.sendEmptyMessage(0);
//            }
//        }.start();
    }


}
