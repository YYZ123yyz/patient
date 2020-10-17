package com.hyphenate.easeui.order;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allin.commlibrary.CollectionUtils;

import java.util.HashMap;
import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.SignOrderInfoBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;
import www.patient.jykj_zxyl.base.http.ParameUtil;

/**
 * Description:解约Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:25
 */
public class CancelContractPresenter extends BasePresenterImpl<CancelContract.View>
        implements CancelContract.Presenter {
    /**获取解约原因请求tag*/
    private static final String SEND_GET_CANCEL_CONTRACT_REQUEST_TAG="send_get_cancel_contract_request_tag";
    /**发送解约请求tag*/
    private static final String SEND_OPER_TERMINITION_SUBMIT_REQUEST_TAG="send_oper_terminition_submit_request_tag";
    /**获取患者订单请求tag*/
    private static final String SEND_SEARCH_SIGN_INFO_REQUEST_TAG="send_search_sign_info_request_tag";
    /**发送获取订单信息请求tag*/
    private static final String SEND_SEARCH_SIGN_PATIENT_DOCTOR_ORDER_REQUEST_TAG
            ="send_search_sign_patient_doctor_order_request_tag";
    /**获取医生或者患者信息Tag*/
    private static final String SEND_GET_USERINFO_REQUEST_TAG="send_get_userinfo_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_GET_CANCEL_CONTRACT_REQUEST_TAG,
                SEND_OPER_TERMINITION_SUBMIT_REQUEST_TAG,
                SEND_SEARCH_SIGN_INFO_REQUEST_TAG,SEND_SEARCH_SIGN_PATIENT_DOCTOR_ORDER_REQUEST_TAG};
    }

    @Override
    public void sendGetBasicsDomainRequest(String baseCode) {

        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("baseCode",baseCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getBasicsDomain(s).compose(
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
                String resJsonData = baseBean.getResJsonData();
                List<BaseReasonBean> cancelContractBeans
                        = GsonUtils.jsonToList(resJsonData, BaseReasonBean.class);
                if (mView!=null) {
                    mView.getBasicDomainResult(cancelContractBeans);
                }
            }

            @Override
            protected String setTag() {
                return SEND_GET_CANCEL_CONTRACT_REQUEST_TAG;
            }
        });
    }

    @Override
    public void setOperTerminationSumbitRequest(String loginDoctorPosition,
                                                String mainDoctorCode,
                                                String mainDoctorName,
                                                String signCode,
                                                String signNo,
                                                String mainPatientCode,
                                                String mainUserName,
                                                String refuseReasonClassCode,
                                                String refuseReasonClassName,
                                                String refuseRemark) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition",ParameUtil.loginDoctorPosition);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        hashMap.put("mainDoctorName",mainDoctorName);
        hashMap.put("signCode",signCode);
        hashMap.put("signNo",signNo);
        hashMap.put("mainPatientCode",mainPatientCode);
        hashMap.put("mainUserName",mainUserName);
        hashMap.put("refuseReasonClassCode",refuseReasonClassCode);
        hashMap.put("refuseReasonClassName",refuseReasonClassName);
        hashMap.put("refuseRemark",refuseRemark);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operTerminationSumbit(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
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
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        mView.getCancelContractResult(true,"");
                    }else{
                        mView.getCancelContractResult(false,baseBean.getResMsg());
                    }

                }
            }

            @Override
            protected String setTag() {
                return SEND_OPER_TERMINITION_SUBMIT_REQUEST_TAG;
            }
        });
    }

    @Override
    public void sendSerchSignInfoByPatientCodeRequest(String loginDoctorPosition,
                                                      String mainPatientCode,
                                                      String mainDoctorCode) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition",loginDoctorPosition);
        hashMap.put("mainPatientCode",mainPatientCode);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().serchSignInfoByPatientCode(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {

            }

            @Override
            public void hideLoadingView() {

            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                int resCode = baseBean.getResCode();
                if (resCode==1) {
                    String resJsonData = baseBean.getResJsonData();
                    SignOrderInfoBean signOrderInfoBean
                            = GsonUtils.fromJson(resJsonData, SignOrderInfoBean.class);
                    if (mView!=null) {

                        mView.getSerchSignInfoByPatientCodeResult(signOrderInfoBean);
                    }
                }

            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_SIGN_INFO_REQUEST_TAG;
            }
        });
    }




    @Override
    public void sendSearchSignPatientDoctorOrderRequest(String signCode, String operDoctorCode,
                                                        String operDoctorName) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();




        hashMap.put("loginPatientPosition",ParameUtil.loginDoctorPosition);
        hashMap.put("orderCode",signCode);
        hashMap.put("requestClientType", "1");
        hashMap.put("searchPatientCode",operDoctorCode);
        hashMap.put("searchPatientName",operDoctorName);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().searchSignPatientDoctorOrder2(s)
                .compose(Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    int resCode = baseBean.getResCode();
                    if (resCode==1) {
                        OrderDetialBean orderDetialBean
                                = GsonUtils.fromJson(baseBean.getResJsonData(), OrderDetialBean.class);
                        mView.getSearchSignPatientDoctorOrderResult(orderDetialBean);
                    }
                }
            }

            @Override
            protected String setTag() {
                return SEND_SEARCH_SIGN_PATIENT_DOCTOR_ORDER_REQUEST_TAG;
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
                return SEND_GET_USERINFO_REQUEST_TAG;
            }
        });
    }
}
