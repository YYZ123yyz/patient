package com.hyphenate.easeui.order;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allin.commlibrary.CollectionUtils;
import com.google.android.gms.common.api.Api;

import java.util.HashMap;
import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.SignOrderInfoBean;
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
 * Description:解约详情Presenter
 *
 * @author: qiuxinhai
 * @date: 2020-07-28 18:25
 */
public class CancelConfirmDetialContractPresenter extends BasePresenterImpl<CancelConfirmDetialContract.View>
        implements CancelConfirmDetialContract.Presenter {

    private static final String SEND_GET_ORDER_DETIAL_BY_CODE="send_get_order_detial_code";
    private static final String SEND_GET_CANCEL_CONTRACT_CONFIRM_REQUEST_TAG="send_get_cancel_contract_confirm_request_tag";
    /**获取医生或者患者信息Tag*/
    private static final String SEND_GET_USERINFO_REQUEST_TAg="send_get_userinfo_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{SEND_GET_ORDER_DETIAL_BY_CODE
                ,SEND_GET_CANCEL_CONTRACT_CONFIRM_REQUEST_TAG,SEND_GET_USERINFO_REQUEST_TAg};
    }


    @Override
    public void sendSearchOrderDetialRequest(String signOrderCode, String operDoctorCode,
                                             String operDoctorName) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition",ParameUtil.loginDoctorPosition);
        hashMap.put("signOrderCode",signOrderCode);
        hashMap.put("operDoctorCode",operDoctorCode);
        hashMap.put("operDoctorName",operDoctorName);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getPatientTestApi().searchSignPatientDoctorOrder(s).compose(
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
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return SEND_GET_ORDER_DETIAL_BY_CODE;
            }
        });
    }



    @Override
    public void sendCancelContractConConfirmRequest(String loginDoctorPosition, String mainDoctorCode,
                                                    String mainDoctorName, String signCode, String signNo,
                                                    String mainPatientCode, String mainUserName,
                                                    String confimresult, String refuseReasonClassCode,
                                                    String refuseReasonClassName, String refuseRemark) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition",loginDoctorPosition);
        hashMap.put("mainDoctorCode",mainDoctorCode);
        hashMap.put("mainDoctorName",mainDoctorName);

        hashMap.put("signCode",signCode);
        hashMap.put("signNo",signNo);
        hashMap.put("mainPatientCode",mainPatientCode);
        hashMap.put("mainUserName",mainUserName);
        hashMap.put("confimresult",confimresult);
        hashMap.put("refuseReasonClassCode",refuseReasonClassCode);
        hashMap.put("refuseReasonClassName",refuseReasonClassName);
        hashMap.put("refuseRemark",refuseRemark);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operTerminationConfim(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getCancelContractConfirmResult(true,"");
                    }else{
                        mView.getCancelContractConfirmResult(false,baseBean.getResMsg());
                    }
                }
            }

            @Override
            protected void onError(String s) {
                super.onError(s);
            }

            @Override
            protected String setTag() {
                return SEND_GET_CANCEL_CONTRACT_CONFIRM_REQUEST_TAG;
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

    @Override
    public void getOrderDet(String params) {
        ApiHelper.getApiService().searchSignPatientDoctorOrder2(params).compose(
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
                            LogUtils.e("拒绝解约详情 xxx  "+resJsonData);
                            OrderDetialBean orderDetialBean = GsonUtils.fromJson(resJsonData, OrderDetialBean.class);
                            LogUtils.e("拒绝解约详情  "+orderDetialBean.toString());
                            mView.getDetSucess(orderDetialBean);
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
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return "getResureDet";
            }
        });




    }
}
