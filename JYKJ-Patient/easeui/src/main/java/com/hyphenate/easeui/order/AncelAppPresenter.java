package com.hyphenate.easeui.order;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.google.gson.Gson;

import java.util.HashMap;

import www.patient.jykj_zxyl.base.base_bean.AncelAppBean;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.SignOrderInfoBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;

public class AncelAppPresenter extends BasePresenterImpl<AncelAppContract.View>
        implements AncelAppContract.Presenter {
    @Override
    public void sendAncelAppRequest(String loginPatientPosition, String mainPatientCode, String mainPatientName, String reserveCode) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginPatientPosition",ParameUtil.loginDoctorPosition);
        hashMap.put("mainPatientCode",mainPatientCode);
        hashMap.put("mainPatientName",mainPatientName);
        hashMap.put("reserveCode",reserveCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().cancelApp(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
        })).safeSubscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView!=null) {
                    int resCode = baseBean.getResCode();
                    if(resCode==1){
                        String resJsonData = baseBean.getResJsonData();
                        AncelAppBean ancelAppBean = GsonUtils.fromJson(resJsonData, AncelAppBean.class);
                        mView.getAncelAppResult(ancelAppBean);
                    }
                }
            }
        });
    }

    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

}
