package www.patient.jykj_zxyl.activity.home.patient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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

import entity.ProvideDoctorSetSchedulingInfoGroupDate;
import entity.ProvideInteractOrderInfo;
import entity.ProvideViewMyDoctorOrderAndTreatment;
import entity.ProvideWechatPayModel;
import entity.XYEntiy;
import entity.patientapp.ProvideMarketingAvailableCoupons;
import entity.patientapp.ProvideMarketingAvailableIntegral;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import entity.wdzs.ProvideInteractPatientInterrogation;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeTJZJAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeTJZJAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.pay.PayResult;
import www.patient.jykj_zxyl.util.Util;
import www.patient.jykj_zxyl.wxapi.WXPayEntryActivity;


/**
 * 专家详情==》订单信息
 */
public class WZXXOrderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FragmentHomeTJZJAdapter mAdapter;
    private LinearLayout llBack;

    public          ProgressDialog              mDialogProgress =null;

    private         Context                     mContext;                                       //
    private WZXXOrderActivity mActivity;
    private         JYKJApplication             mApp;

    private         String                      mNetRetStr;                 //返回字符串
    private         Handler                     mHandler;

    private         TextView                    mJZLX;                      //订单类型
    private         TextView                    mZXYS;                      //咨询医生
    private         TextView                    mGXZF;                      //共需支付
    private         TextView                    mKYYHQ;                     //可用优惠券
    private         TextView                    mXZYHQ;                     //选择优惠券
    private         TextView                    mKYJF;                      //可用积分
    private         ImageView                   mSYJF;                      //使用积分
    private         ImageView                   mWXZF;                      //微信支付
    private         ImageView                   mZFBZF;                     //支付宝支付

    private         TextView                    mCommit;                    //提交

    private         ProvideInteractPatientInterrogation mProvideInteractPatientInterrogation = new ProvideInteractPatientInterrogation();               //提交的问诊资料
    private         String                      mOrderNum;                  //订单号
    private         String                      mOrderType;                 //订单类型

    private         ProvideMarketingAvailableCoupons mProvideMarketingAvailableCoupons;             //获取到的优惠券

    private         LinearLayout                zf_weixin;                  //支付（微信支付）
    private         LinearLayout                zf_zhifubao;                     //支付（支付宝）
    private         int                         payModel = 1;                   //支付模式 1=微信支付  2=支付宝支付
    private ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend;                          //专家信息
    private ProvideDoctorSetSchedulingInfoGroupDate provideDoctorSetSchedulingInfoGroupDate;

    private         XYEntiy                 mXYEntity;
    public          IWXAPI                  msgApi;

    private         TextView                fwjzsj;                         //服务截止时间
    private         EditText                qysc;

    private         LinearLayout            li_qysc;                    //签约时长
    private         LinearLayout            li_fwjzsj;                  //服务截止时间
    private static final int SDK_PAY_FLAG = 3;
    private String pay_appid;
    private String pay_productid;

    private void initView() {
        zf_weixin = (LinearLayout)this.findViewById(R.id.zf_weixin);
        qysc = (EditText)this.findViewById(R.id.qysc);
        li_qysc = (LinearLayout)this.findViewById(R.id.li_qysc);
        li_fwjzsj = (LinearLayout)this.findViewById(R.id.li_fwjzsj);
        zf_zhifubao = (LinearLayout)this.findViewById(R.id.zf_zhifubao);
        zf_weixin.setOnClickListener(new ButtonClick());
        zf_zhifubao.setOnClickListener(new ButtonClick());
        mGXZF = (TextView)this.findViewById(R.id.gxzf);
        mJZLX = (TextView)this.findViewById(R.id.jzlx);
        fwjzsj = (TextView)this.findViewById(R.id.fwjzsj);
        qysc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null || "".equals(String.valueOf(charSequence)))
                    return;
                //获取n个月后的时间
                String str = getAfterMonth(Util.getCurrentFormart(),Integer.valueOf(charSequence+""));
                //判断是否超过服务截止时间
                try {
                    boolean is = compare(str,Util.dateToStr(mXYEntity.getLimitSigningExpireDate()));
                    if (!is)
                    {
                        Toast.makeText(mContext,"超过医生服务截止时间",Toast.LENGTH_SHORT).show();
                        qysc.setText("");
                    }
                    else
                    {
                        Float price = Float.valueOf(charSequence+"")*Float.valueOf(mXYEntity.getPriceBasics());
                        mGXZF.setText("￥"+price);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        switch (Integer.valueOf(mOrderType))
        {
            case 1:
                mJZLX.setText("图文就诊");
                if (provideViewDoctorExpertRecommend.getImgTextPrice() == null)
                    mGXZF.setText("未设置");
                else
                    mGXZF.setText("￥"+provideViewDoctorExpertRecommend.getImgTextPrice());
                li_qysc.setVisibility(View.GONE);
                li_fwjzsj.setVisibility(View.GONE);
                break;
            case 5:
                mJZLX.setText("电话就诊");
                if (provideViewDoctorExpertRecommend.getPhonePrice() == null)
                    mGXZF.setText("未设置");
                else
                    mGXZF.setText("￥"+provideViewDoctorExpertRecommend.getPhonePrice());
                li_fwjzsj.setVisibility(View.GONE);
                li_qysc.setVisibility(View.GONE);
                break;
            case 2:
                mJZLX.setText("音频就诊");
                if (provideViewDoctorExpertRecommend.getAudioPrice() == null)
                    mGXZF.setText("未设置");
                else
                    mGXZF.setText("￥"+provideViewDoctorExpertRecommend.getAudioPrice());
                li_fwjzsj.setVisibility(View.GONE);
                li_qysc.setVisibility(View.GONE);
                break;
            case 3:
                mJZLX.setText("视频就诊");
                if (provideViewDoctorExpertRecommend.getVideoPrice() == null)
                    mGXZF.setText("未设置");
                else
                    mGXZF.setText("￥"+provideViewDoctorExpertRecommend.getVideoPrice());
                li_fwjzsj.setVisibility(View.GONE);
                li_qysc.setVisibility(View.GONE);
                break;
            case 4:
                mJZLX.setText("签约就诊");
                if (provideViewDoctorExpertRecommend.getSigningPrice() == null)
                    mGXZF.setText("未设置");
                else
                    mGXZF.setText("￥"+provideViewDoctorExpertRecommend.getSigningPrice());
                li_fwjzsj.setVisibility(View.GONE);
                li_qysc.setVisibility(View.GONE);
                break;
            case 6:
                mJZLX.setText("签约就诊");
                li_fwjzsj.setVisibility(View.VISIBLE);
                li_qysc.setVisibility(View.VISIBLE);
                fwjzsj.setText(Util.dateToStr(mXYEntity.getLimitSigningExpireDate()));
//                if (provideViewDoctorExpertRecommend.getSigningPrice() == null)
//                    mGXZF.setText("未设置");
//                else
//                    mGXZF.setText("￥"+provideViewDoctorExpertRecommend.getSigningPrice());
                break;
        }
        mZXYS = (TextView)this.findViewById(R.id.zxys);
        mZXYS.setText(mProvideInteractPatientInterrogation.getDoctorName());


//        mKYYHQ = (TextView)this.findViewById(R.id.wkyyhq);
//        mXZYHQ = (TextView)this.findViewById(R.id.xzyhq);
        mKYJF = (TextView)this.findViewById(R.id.kyjf);
        mSYJF = (ImageView)this.findViewById(R.id.xzjf);
        mWXZF = (ImageView)this.findViewById(R.id.wxzf);
        mZFBZF = (ImageView)this.findViewById(R.id.zfbzf);
        mCommit = (TextView)this.findViewById(R.id.commit);
        mCommit.setOnClickListener(new ButtonClick());
        setZFModel();
    }

    /**
     * 设置支付模式
     */
    public void setZFModel(){
        if (payModel == 1)
        {
            mWXZF.setBackgroundResource(R.mipmap.zf_choice);
            mZFBZF.setBackgroundResource(R.mipmap.no_gx111);
        }
        if (payModel == 2)
        {
            mWXZF.setBackgroundResource(R.mipmap.no_gx111);
            mZFBZF.setBackgroundResource(R.mipmap.zf_choice);

        }
    }

    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;

                case R.id.zf_weixin:
                    payModel = 1;
                    setZFModel();
                    break;

                case R.id.zf_zhifubao:
                    payModel = 2;
                    setZFModel();
                    break;

                case R.id.commit:
//                    weichatPay(null);
//                    //生成订单
                    if ("6".equals(mOrderType))
                        commitXY();
                    else
                        commit();
                    break;

            }
        }
    }


    /**
     * 获取n个月后的时间
     * @param inputDate
     * @param number
     * @return
     */
    public static String  getAfterMonth(String inputDate,int number) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = sdf.parse(inputDate);//初始日期
        }catch(Exception e){

        }
        c.setTime(date);//设置日历时间
        c.add(Calendar.MONTH,number);//在日历的月份上增加6个月
        String strDate = sdf.format(c.getTime());//的到你想要得6个月后的日期
        return strDate;
    }

    /**
     * 比较两个时间大小
     * @param time1
     * @param time2
     * @return
     * @throws ParseException
     */
    public boolean compare(String time1,String time2) throws ParseException
    {
        //如果想比较日期则写成"yyyy-MM-dd"就可以了
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //将字符串形式的时间转化为Date类型的时间
        Date a=sdf.parse(time1);
        Date b=sdf.parse(time2);
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if(a.before(b))
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

    /**
     * 续约
     */
    private void commitXY() {
        ProvideInteractOrderInfo provideInteractOrderInfo = new ProvideInteractOrderInfo();
        provideInteractOrderInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideInteractOrderInfo.setRequestClientType("1");
        provideInteractOrderInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractOrderInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideInteractOrderInfo.setSigningDoctorCode(mProvideInteractPatientInterrogation.getDoctorCode());
        provideInteractOrderInfo.setTreatmentSigningId(mXYEntity.getTreatmentSigningId());
        if (qysc.getText().toString() == null || "".equals(qysc.getText().toString()))
        {
            Toast.makeText(mContext,"续约时长不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        provideInteractOrderInfo.setSigningMonth(qysc.getText().toString());
        provideInteractOrderInfo.setSigningExpireDate(Util.dateToStr(mXYEntity.getLimitSigningExpireDate()));
        Float price = Float.valueOf(qysc.getText().toString())*Float.valueOf(mXYEntity.getPriceBasics());
        provideInteractOrderInfo.setServiceTotal(price);
        provideInteractOrderInfo.setOrderTotal(price);
        provideInteractOrderInfo.setActualPayment(price);
        provideInteractOrderInfo.setPriceDiscountCoupon(Float.valueOf("0.00"));
        provideInteractOrderInfo.setPriceDiscountIntegral(Float.valueOf("0.00"));
        provideInteractOrderInfo.setCouponsHaveCode("0");
        provideInteractOrderInfo.setIntegralHaveCode("0");
        provideInteractOrderInfo.setIntegralDeductionMoney("0");
        provideInteractOrderInfo.setFlagPayType(String.valueOf(payModel));
        provideInteractOrderInfo.setOperPatientPhone(mApp.mProvideViewSysUserPatientInfoAndRegion.getLinkPhone());
        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideInteractOrderInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideInteractOrderInfo),Constant.SERVICEURL+"PatientMyDoctorControlle/operIndexMyDoctorSigningRenewal");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
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

    /**
     * 生成支付订单
     */
    private void commit() {
        ProvideInteractOrderInfo provideInteractOrderInfo = new ProvideInteractOrderInfo();
        provideInteractOrderInfo.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideInteractOrderInfo.setRequestClientType("1");
        provideInteractOrderInfo.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractOrderInfo.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideInteractOrderInfo.setOrderCode(mOrderNum);
        provideInteractOrderInfo.setTreatmentType(Integer.valueOf(mOrderType));
        provideInteractOrderInfo.setDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());
        provideInteractOrderInfo.setDoctorName(provideViewDoctorExpertRecommend.getUserName());
        provideInteractOrderInfo.setPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideInteractOrderInfo.setPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        switch (Integer.valueOf(mOrderType))
        {
            case 1:
                provideInteractOrderInfo.setServiceTotal(provideViewDoctorExpertRecommend.getImgTextPrice());
                provideInteractOrderInfo.setOrderTotal(provideViewDoctorExpertRecommend.getImgTextPrice());
                provideInteractOrderInfo.setActualPayment(provideViewDoctorExpertRecommend.getImgTextPrice());
                break;
            case 2:
                provideInteractOrderInfo.setServiceTotal(provideViewDoctorExpertRecommend.getPhonePrice());
                provideInteractOrderInfo.setOrderTotal(provideViewDoctorExpertRecommend.getPhonePrice());
                provideInteractOrderInfo.setActualPayment(provideViewDoctorExpertRecommend.getPhonePrice());
                break;
            case 3:
                provideInteractOrderInfo.setServiceTotal(provideViewDoctorExpertRecommend.getAudioPrice());
                provideInteractOrderInfo.setOrderTotal(provideViewDoctorExpertRecommend.getAudioPrice());
                provideInteractOrderInfo.setActualPayment(provideViewDoctorExpertRecommend.getAudioPrice());
                break;
            case 4:
                provideInteractOrderInfo.setServiceTotal(provideViewDoctorExpertRecommend.getVideoPrice());
                provideInteractOrderInfo.setOrderTotal(provideViewDoctorExpertRecommend.getVideoPrice());
                provideInteractOrderInfo.setActualPayment(provideViewDoctorExpertRecommend.getVideoPrice());
                break;
            case 5:
                provideInteractOrderInfo.setServiceTotal(provideViewDoctorExpertRecommend.getSigningPrice());
                provideInteractOrderInfo.setOrderTotal(provideViewDoctorExpertRecommend.getSigningPrice());
                provideInteractOrderInfo.setActualPayment(provideViewDoctorExpertRecommend.getSigningPrice());
                break;
        }
        provideInteractOrderInfo.setPriceDiscountCoupon(Float.valueOf("0.00"));
        provideInteractOrderInfo.setPriceDiscountIntegral(Float.valueOf("0.00"));

        switch (Integer.valueOf(mOrderType))
        {
            case 2:
                provideInteractOrderInfo.setTreatmentDate(Util.dateToStr(provideDoctorSetSchedulingInfoGroupDate.getWorkDate()));
                for (int i = 0; i < provideDoctorSetSchedulingInfoGroupDate.getGroupTimeList().size(); i++)
                {
                    if (provideDoctorSetSchedulingInfoGroupDate.getGroupTimeList().get(i).isChoice())
                        provideInteractOrderInfo.setTreatmentTimeSlot(provideDoctorSetSchedulingInfoGroupDate.getGroupTimeList().get(i).getDayTimeSlot());
                }
                break;
            case 3:
                provideInteractOrderInfo.setTreatmentDate(Util.dateToStr(provideDoctorSetSchedulingInfoGroupDate.getWorkDate()));
                for (int i = 0; i < provideDoctorSetSchedulingInfoGroupDate.getGroupTimeList().size(); i++)
                {
                    if (provideDoctorSetSchedulingInfoGroupDate.getGroupTimeList().get(i).isChoice())
                        provideInteractOrderInfo.setTreatmentTimeSlot(provideDoctorSetSchedulingInfoGroupDate.getGroupTimeList().get(i).getDayTimeSlot());
                }
                break;
            case 5:
                provideInteractOrderInfo.setTreatmentDate(Util.dateToStr(provideDoctorSetSchedulingInfoGroupDate.getWorkDate()));
                for (int i = 0; i < provideDoctorSetSchedulingInfoGroupDate.getGroupTimeList().size(); i++)
                {
                    if (provideDoctorSetSchedulingInfoGroupDate.getGroupTimeList().get(i).isChoice())
                        provideInteractOrderInfo.setTreatmentTimeSlot(provideDoctorSetSchedulingInfoGroupDate.getGroupTimeList().get(i).getDayTimeSlot());
                }
                break;
        }


        provideInteractOrderInfo.setTreatmentLinkPhone(provideViewDoctorExpertRecommend.getLinkPhone());
        provideInteractOrderInfo.setCouponsHaveCode("0");
        provideInteractOrderInfo.setIntegralHaveCode("0");
        provideInteractOrderInfo.setIntegralDeductionMoney("0");
        provideInteractOrderInfo.setFlagPayType(String.valueOf(payModel));
        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideInteractOrderInfo);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideInteractOrderInfo),Constant.SERVICEURL+"patientInteractDataControlle/operInteractOrderInfoGenerate");
//                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideInteractOrderInfo),"http://192.168.3.15:8081/jyJXTWeiChat/weiChatPayDemo");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_jzdd);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        mApp.gPayCloseActivity.add(mActivity);
        mProvideInteractPatientInterrogation = (ProvideInteractPatientInterrogation) getIntent().getSerializableExtra("provideInteractPatientInterrogation");
        provideViewDoctorExpertRecommend = (ProvideViewDoctorExpertRecommend) getIntent().getSerializableExtra("provideViewDoctorExpertRecommend");
        provideDoctorSetSchedulingInfoGroupDate= (ProvideDoctorSetSchedulingInfoGroupDate) getIntent().getSerializableExtra("provideDoctorSetSchedulingInfoGroupDate");
        mOrderNum = getIntent().getStringExtra("orderID");
        mOrderType = getIntent().getStringExtra("orderType");
        if ("6".equals(mOrderType))
        {
            mXYEntity = (XYEntiy) getIntent().getSerializableExtra("xyEntiy");
        }
        initView();
        initHandler();
//        getYHQDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getDate();
    }

    /**
     * 获取订单详情
     */
    private void getDate() {
        getProgressBar("请稍候","正在获取数据。。。");
        ProvideViewMyDoctorOrderAndTreatment provideViewMyDoctorOrderAndTreat= new ProvideViewMyDoctorOrderAndTreatment();
        provideViewMyDoctorOrderAndTreat.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideViewMyDoctorOrderAndTreat.setRequestClientType("1");
        provideViewMyDoctorOrderAndTreat.setSearchPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewMyDoctorOrderAndTreat.setSearchPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideViewMyDoctorOrderAndTreat.setOrderCode(mOrderNum);
        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideViewMyDoctorOrderAndTreat);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, www.patient.jykj_zxyl.application.Constant.SERVICEURL+"msgDataControlle/searchPatientMsgInteractOrderInfoDetail");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(6);
            }
        }.start();
    }

    /**
     * 获取优惠券
     */
    private void getYHQDate() {
        ProvideMarketingAvailableCoupons provideMarketingAvailableCoupons = new ProvideMarketingAvailableCoupons();
        provideMarketingAvailableCoupons.setLoginPatientPosition(mApp.loginDoctorPosition);
        provideMarketingAvailableCoupons.setRequestClientType("1");
        provideMarketingAvailableCoupons.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideMarketingAvailableCoupons.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideMarketingAvailableCoupons.setOrderCode(mOrderNum);
        provideMarketingAvailableCoupons.setTreatmentType(mOrderType);
        if ("6".equals(mOrderType))
        {
            provideMarketingAvailableCoupons.setDoctorCode(mProvideInteractPatientInterrogation.getDoctorCode());
            provideMarketingAvailableCoupons.setDoctorName(mProvideInteractPatientInterrogation.getDoctorName());
        }
        else
        {
            provideMarketingAvailableCoupons.setDoctorCode(provideViewDoctorExpertRecommend.getDoctorCode());
            provideMarketingAvailableCoupons.setDoctorName(provideViewDoctorExpertRecommend.getUserName());
        }


        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideMarketingAvailableCoupons),Constant.SERVICEURL+"patientInteractDataControlle/getInteractPatientInterrogationResPatientCoupon");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);

                    if (netRetEntity.getResCode() == 1) {
                        mProvideMarketingAvailableCoupons = JSON.parseObject(netRetEntity.getResJsonData(),ProvideMarketingAvailableCoupons.class);
                    }
                    //获取积分信息
                    ProvideMarketingAvailableIntegral provideMarketingAvailableIntegral = new ProvideMarketingAvailableIntegral();
                    provideMarketingAvailableIntegral.setLoginPatientPosition(mApp.loginDoctorPosition);
                    provideMarketingAvailableIntegral.setRequestClientType("1");
                    provideMarketingAvailableIntegral.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    provideMarketingAvailableIntegral.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    provideMarketingAvailableIntegral.setOrderCode(mOrderNum);
                    provideMarketingAvailableIntegral.setTreatmentType(mOrderType);
                    provideMarketingAvailableIntegral.setDoctorCode(provideViewDoctorExpertRecommend.getPatientCode());
                    provideMarketingAvailableIntegral.setDoctorName(provideViewDoctorExpertRecommend.getUserName());
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideMarketingAvailableCoupons),Constant.SERVICEURL+"patientInteractDataControlle/getInteractPatientInterrogationResPatientIntegral");
                    netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    mHandler.sendEmptyMessage(0);
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

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0: {
                        cacerProgress();
                        break;
                    }
                    case 1: {
                        cacerProgress();
//                        ProvideWechatPayModel provideWechatPayModel = JSON.parseObject(mNetRetStr,ProvideWechatPayModel.class);
//                        //开始调起微信支付
//                        weichatPay(provideWechatPayModel);
                        if (mNetRetStr.contains("&sign_type=")) {
                            //mNetRetStr = mNetRetStr.substring(mNetRetStr.indexOf("&sign=")+"&sign=".length(),mNetRetStr.indexOf("&sign_type="));
                            pay_appid = mNetRetStr.substring(mNetRetStr.indexOf("&app_id=") + "&app_id=".length(), mNetRetStr.indexOf("&biz_content="));
                            pay_productid = mNetRetStr.substring(mNetRetStr.indexOf("&app_id=") + "&app_id=".length(), mNetRetStr.indexOf("&biz_content="));
                            NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                            sendAliPay(netRetEntity.getResJsonData());
                        } else {
                            NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
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
                    }
                    case SDK_PAY_FLAG:
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        /**
                         * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();
                        String resultStatus = payResult.getResultStatus();
                        Intent intent = new Intent(WZXXOrderActivity.this, WXPayEntryActivity.class);
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
                    case 6:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        else if(netRetEntity.getResCode() == 1)
                        {
                            ProvideViewMyDoctorOrderAndTreatment provideViewMyDoctorOrderAndTreatment = JSON.parseObject(netRetEntity.getResJsonData(),ProvideViewMyDoctorOrderAndTreatment.class);
                            showDate(provideViewMyDoctorOrderAndTreatment);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 展示支付状态
     * @param provideViewMyDoctorOrderAndTreatment
     */
    private void showDate(ProvideViewMyDoctorOrderAndTreatment provideViewMyDoctorOrderAndTreatment) {
//        switch (provideViewMyDoctorOrderAndTreatment.getPaymentModeName())
    }


    public void sendAliPay(final String orderInfo){
        //final String orderInfo = provideWechatPayModel.getSign();
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(WZXXOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                mApp.gPayOrderCode =  mOrderNum;
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
        //boolean a = msgApi.registerApp(provideWechatPayModel.getAppId());
        //调起微信支付
        if(null==msgApi) {
            msgApi = WXAPIFactory.createWXAPI(this, "wx4ccb2ac1c5491336");
            msgApi.registerApp("wx4ccb2ac1c5491336");
        }
        PayReq request = new PayReq();
        request.appId = provideWechatPayModel.getAppId();
        request.partnerId = provideWechatPayModel.getPartnerid();
        String prepare_id = provideWechatPayModel.getPrepayid();
        request.prepayId= prepare_id;
        request.packageValue = "Sign=WXPay";
        request.nonceStr=provideWechatPayModel.getNonceStr();
        request.timeStamp= provideWechatPayModel.getTimeStamp();
        request.sign= provideWechatPayModel.getSign();
        request.signType = "MD5";
        mApp.gPayOrderCode = mOrderNum;
        boolean result = msgApi.sendReq(request);
        //System.out.println();
//        mHandler.sendEmptyMessage(1);

//        PayReq request = new PayReq();
//        request.appId = "wxaf6f64f6a5878261";
//        request.partnerId = "1579159781";
//        String prepare_id = provideWechatPayModel.getPackagePrepayId();
//        request.prepayId= "wx0621151327932593af383eac1045584700";
//        request.packageValue = "Sign=WXPay";
//        request.nonceStr="20200606211504AENSGI6S8ZWDV99CX2";
//        request.timeStamp="1591449304";
//        request.sign= "EB8344528804DF94E47AEFC1411507ED";
//        request.signType = "MD5";
//        boolean result = msgApi.sendReq(request);
    }


    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
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
    public void cacerProgress(){
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
