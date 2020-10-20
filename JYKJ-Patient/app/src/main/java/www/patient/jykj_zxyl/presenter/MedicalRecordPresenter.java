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
import www.patient.jykj_zxyl.myappointment.Contract.MedicalRecordContract;
import www.patient.jykj_zxyl.myappointment.Contract.MedicalRecordContract.Presenter;


/**
 * Created by G on 2020/9/14 9:49
 */
public class MedicalRecordPresenter extends BasePresenterImpl<MedicalRecordContract.View> implements Presenter {

    private static final String GET_MEDICALRECORD_DET="get_medicalrecord_det";
    @Override
    protected Object[] getRequestTags() {
        return new Object[]{GET_MEDICALRECORD_DET};
    }


    @Override
    public void getRecordDet(String params) {
        ApiHelper.getPatientTestApi().getPatientRecordDet(params).compose(
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
                            MedicalRecordBean orderDetialBean = GsonUtils.fromJson(resJsonData, MedicalRecordBean.class);
                            mView.getMedicalRecordSucess(orderDetialBean);
                        }
                    }else {
                        mView.getDataFailure(baseBean.getResMsg());
                    }
                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                Log.e("xxx", "onError: "+s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return GET_MEDICALRECORD_DET;
            }
        });
    }

    @Override
    public void commitDet(String params) {
        ApiHelper.getPatientTestApi().confirmPatientRecordDet(params).compose(
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
                        mView.commitDetSucess();
                       /* if (StringUtils.isNotEmpty(resJsonData)) {
                            LogUtils.e("病历接口数据"+resJsonData);
                            MedicalRecordBean orderDetialBean = GsonUtils.fromJson(resJsonData, MedicalRecordBean.class);
                            mView.getMedicalRecordSucess(orderDetialBean);
                        }*/
                    }else {
                        mView.getDataFailure(baseBean.getResMsg());
                    }
                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                Log.e("xxx", "onError: "+s);
                if (mView!=null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return GET_MEDICALRECORD_DET;
            }
        });
    }
}
