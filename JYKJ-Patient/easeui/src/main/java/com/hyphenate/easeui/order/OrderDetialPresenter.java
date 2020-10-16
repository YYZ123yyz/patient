package com.hyphenate.easeui.order;
import android.app.Activity;
import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allin.commlibrary.CollectionUtils;

import java.util.HashMap;
import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.PaymentBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;

/**
 * Description:订单详情Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:25
 */
public class OrderDetialPresenter extends BasePresenterImpl<OrderDetialContract.View>
        implements OrderDetialContract.Presenter {
    /**发送订单详情请求Tag*/
    private static final String SEND_GET_ORDER_DETIAL_REQUEST_TAG="send_get_order_detial_request_tag";
    /**操作订单请求Tag*/
    private static final String SEND_GET_OPER_ORDER_REQUEST_TAG="send_get_oper_order_request_tag";
    /**支付订单请求Tag*/
    private static final String SEND_GET_PATIENT_ORDER_PAYMENT_REQUEST_TAG="send_get_patient_order_request_tag";
    /**获取医生或者患者信息Tag*/
    private static final String SEND_GET_USERINFO_REQUEST_TAg="send_get_userinfo_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_GET_ORDER_DETIAL_REQUEST_TAG,
                SEND_GET_OPER_ORDER_REQUEST_TAG,
                SEND_GET_PATIENT_ORDER_PAYMENT_REQUEST_TAG,SEND_GET_USERINFO_REQUEST_TAg};
    }

    @Override
    public void sendSearchOrderDetialRequest(String signOrderCode ,String operDoctorCode, String operDoctorName) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();


        hashMap.put("loginPatientPosition",ParameUtil.loginDoctorPosition);
        hashMap.put("requestClientType", "1");
        hashMap.put("orderCode",signOrderCode);
        hashMap.put("searchPatientCode",operDoctorCode);
        hashMap.put("searchPatientName",operDoctorName);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchSignPatientDoctorOrder2(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(100);
                }
            }

            @Override
            public void hideLoadingView() {
                if (mView!=null) {
                    mView.hideLoading();
                }
            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        if (StringUtils.isNotEmpty(resJsonData)) {
                            OrderDetialBean orderDetialBean = GsonUtils.fromJson(resJsonData, OrderDetialBean.class);
                            mView.getSearchOrderDetialResult(orderDetialBean);
                        }
                    }else if(resCode==0){
                        if (baseBean.getResData().equals("2010098")) {
                            mView.showEmpty();
                        }
                    }
                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                LogUtils.e("失败原因"+s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return SEND_GET_ORDER_DETIAL_REQUEST_TAG;
            }
        });


    }



    @Override
    public void sendOrderOperRequest(String mainDoctorCode, String mainDoctorName, String signCode,
                                     String signNo, String mainPatientCode, String mainUserName,
                                     String confimresult,Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("mainDoctorCode",mainDoctorCode);
        hashMap.put("mainDoctorName",mainDoctorName);
        hashMap.put("loginDoctorPosition",ParameUtil.loginDoctorPosition);
        hashMap.put("signCode",signCode);
        hashMap.put("signNo",signNo);
        hashMap.put("mainPatientCode",mainPatientCode);
        hashMap.put("mainUserName",mainUserName);
        hashMap.put("confimresult",confimresult);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operSignOrderStatus(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(101);
                }
            }

            @Override
            public void hideLoadingView() {
                if (mView!=null) {
                    mView.hideLoading();
                }
            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    if (baseBean.getResCode()==1) {
                        mView.getOrderOperResult(true,confimresult);
                    } else{
                        mView.getOrderOperResult(false,confimresult);
                    }
                }

            }
            @Override
            protected void onError(String s) {
                super.onError(s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return SEND_GET_OPER_ORDER_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendOperPatientOrderPayRequest(String orderCode, String flagPayType, Activity activity) {
        HashMap<String, Object> hashMap = ParameUtil.buildBasePatientParam(activity);
        hashMap.put("orderCode",orderCode);
        hashMap.put("flagPayType",flagPayType);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operPatientOrderPay(s).compose(Transformer.switchSchedulers(
                new ILoadingView() {
            @Override
            public void showLoadingView() {
                if (mView!=null) {
                    mView.showLoading(102);
                }
            }

            @Override
            public void hideLoadingView() {
                if (mView!=null) {
                    mView.hideLoading();
                }
            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        if (flagPayType.equals("1")) {
                            PaymentBean paymentBean = GsonUtils.fromJson(baseBean.getResJsonData()
                                    , PaymentBean.class);
                            mView.getWeixPaymentResult(paymentBean);
                        }else if(flagPayType.equals("2")){
                            mView.getAliPaymentResult(baseBean.getResJsonData());
                        }

                    }else{
                        mView.getPaymentResultError(baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected String setTag() {
                return SEND_GET_PATIENT_ORDER_PAYMENT_REQUEST_TAG;
            }
        });

    }

    @Override
    public void sendGetUserListRequest(String userCodeList) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("userCodeList",userCodeList);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getUserInfoList(s).
                compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        String resJsonData = baseBean.getResJsonData();
                        List<UserInfoBaseBean> userInfoBaseBeans = GsonUtils.jsonToList(resJsonData, UserInfoBaseBean.class);
                        if (!CollectionUtils.isEmpty(userInfoBaseBeans)) {
                            mView.getUserInfoResult(userInfoBaseBeans.get(0));
                        }

                    }
                }

            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_GET_USERINFO_REQUEST_TAg;
            }
        });

    }


}
