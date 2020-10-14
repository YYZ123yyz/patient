package www.patient.jykj_zxyl.presenter;

import android.util.Log;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.MedicalRecordBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;
import www.patient.jykj_zxyl.myappointment.Contract.InquiryContract;
import www.patient.jykj_zxyl.myappointment.Contract.MedicalRecordContract;
import www.patient.jykj_zxyl.myappointment.bean.InquiryBean;

/**
 * Created by G on 2020/9/27 10:58
 */
public class InquiryPresenter extends BasePresenterImpl<InquiryContract.View> implements InquiryContract.Presenter {

    private static final String INQUIRY_DATA="inquiry_data";
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }


    @Override
    public void getDataDet(String params) {
        ApiHelper.getPatientTestApi().getInquiryDet(params).compose(
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
                            LogUtils.e("病历接口数据"+resJsonData);
                            InquiryBean orderDetialBean = GsonUtils.fromJson(resJsonData, InquiryBean.class);
                            mView.hasData(orderDetialBean);
                        }
                    }else {
//                        mView.getDataFailure(baseBean.getResMsg());
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
                return INQUIRY_DATA;
            }
        });
    }

    @Override
    public void submitData(String params) {
        ApiHelper.getPatientTestApi().submitInquiryDet(params).compose(
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
//                        String resJsonData = baseBean.getResJsonData();
                        mView.commitSucess();
                    }else {
                        LogUtils.e("错误信息xxxxxxxxxxxxxxxxxxxxxxxxxxx"+baseBean.getResMsg());
//                        mView.getDataFailure(baseBean.getResMsg());
                        mView.commitFiled(baseBean.getResMsg());
                    }
                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                mView.commitFiled(s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return INQUIRY_DATA;
            }
        });
    }
}
