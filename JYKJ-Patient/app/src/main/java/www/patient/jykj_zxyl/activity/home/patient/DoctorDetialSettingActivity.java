package www.patient.jykj_zxyl.activity.home.patient;


import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.easeui.order.CancelContractActivity;
import com.suke.widget.SwitchButton;

import java.util.HashMap;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_activity.BaseActivity;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.SignOrderInfoBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_view.BaseToolBar;
import www.patient.jykj_zxyl.base.enum_type.OrderStatusEnum;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;


/**
 * Description:医生详情设置页面
 *
 * @author: qiuxinhai
 * @date: 2020-08-01 10:59
 */
public class DoctorDetialSettingActivity extends BaseActivity {
    private BaseToolBar toolBar;
    private SwitchButton mSwitchBtn;
    private RelativeLayout mRlCancelContract;
    private TextView mTvSubmitBtn;
    private String doctorCode;
    private JYKJApplication mApp;
    /**获取患者订单请求tag*/
    private static final String SEND_SEARCH_SIGN_INFO_REQUEST_TAG="send_search_sign_info_request_tag";

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        Bundle extras = this.getIntent().getExtras();
        if (extras!=null) {
            doctorCode= extras.getString("doctorCode");
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_doctor_detial_setting;
    }


    @Override
    protected void initView() {
        super.initView();
        mApp = (JYKJApplication) this.getApplication();
        toolBar=findViewById(R.id.toolbar);
        mSwitchBtn=findViewById(R.id.sw_btn);
        mRlCancelContract=findViewById(R.id.rl_cancel_contract);
        mTvSubmitBtn=findViewById(R.id.tv_submit_btn);
        setToolBar();
        addListener();
    }

    /**
     * 设置Title，方法内的参数可自己定义，如左边文字，颜色，图片
     */
    private void setToolBar() {
        toolBar.setMainTitle("设置");
        //返回键
        toolBar.setLeftTitleClickListener(view -> finish());
    }

    /**
     * 添加监听
     */
    private void addListener(){
        mSwitchBtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
        mRlCancelContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData( ParameUtil.loginDoctorPosition,
                        mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),
                        doctorCode);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    /**
     * 获取订单详情数据
     * @param loginDoctorPosition
     * @param mainPatientCode
     * @param mainDoctorCode
     */
    private void getData(String loginDoctorPosition,String mainPatientCode,String mainDoctorCode){
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition",loginDoctorPosition);
        hashMap.put("mainPatientCode",mainPatientCode);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().serchSignInfoByPatientCode(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                showLoading("",null);
            }

            @Override
            public void hideLoadingView() {
                dismissLoading();

            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    String resJsonData = baseBean.getResJsonData();
                    SignOrderInfoBean signOrderInfoBean
                            = GsonUtils.fromJson(resJsonData, SignOrderInfoBean.class);
                    if (signOrderInfoBean!=null&&
                            signOrderInfoBean.getSignStatus()
                                    .equals(OrderStatusEnum.orderSignCompleteCode)) {
                        Bundle bundle=new Bundle();
                        bundle.putString("doctorCode",doctorCode);
                        bundle.putString("operDoctorCode",signOrderInfoBean.getMainDoctorCode());
                        bundle.putString("operDoctorName",signOrderInfoBean.getMainDoctorName());
                        bundle.putSerializable("orderMsg",signOrderInfoBean);
                        startActivity(CancelContractActivity.class,bundle);
                    }else{
                        ToastUtils.showShort("订单不存在");
                    }

                }else{
                    ToastUtils.showShort(baseBean.getResMsg());
                }

            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_SIGN_INFO_REQUEST_TAG;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
