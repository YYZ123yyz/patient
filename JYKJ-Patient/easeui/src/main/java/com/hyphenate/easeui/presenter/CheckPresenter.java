package com.hyphenate.easeui.presenter;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.hyphenate.easeui.contract.CheckContract;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;

import www.patient.jykj_zxyl.base.base_bean.CheckNumBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;

/**
 * Created by G on 2020/9/23 16:26
 */
public class CheckPresenter extends BasePresenterImpl<CheckContract.View>
        implements CheckContract.Presenter {


    private static final String GETCHECK_NUM="get_checknum";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{};
    }

    @Override
    public void getCheckNum(String params) {
        LogUtils.e("请求开始");
        ApiHelper.getPatientTestApi().getCheckNum(params).compose(
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
                            CheckNumBean orderDetialBean = GsonUtils.fromJson(resJsonData, CheckNumBean.class);
                            mView.getCheckNumSucess(orderDetialBean);
                        }
                    }else {
                        mView.getDataFiled(baseBean.getResMsg());
                    }
                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                LogUtils.e("请求失败"+s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return GETCHECK_NUM;
            }
        });

    }

    @Override
    public void submitData(String params) {
        ApiHelper.getPatientTestApi().submitCheckNum(params).compose(
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
                        LogUtils.e("确认结果"+resJsonData);
                        if (StringUtils.isNotEmpty(resJsonData)) {
//                            CheckNumBean orderDetialBean = GsonUtils.fromJson(resJsonData, CheckNumBean.class);
//                            mView.getCheckNumSucess(orderDetialBean);

                        }
                    }else {
                        mView.getDataFiled(baseBean.getResMsg());
                    }
                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                LogUtils.e("请求失败"+s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return GETCHECK_NUM;
            }
        });
    }
}
