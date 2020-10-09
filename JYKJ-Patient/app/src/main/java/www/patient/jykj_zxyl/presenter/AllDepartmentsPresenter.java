package www.patient.jykj_zxyl.presenter;

import android.util.Log;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.AllDepartmentBean;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.MedicalRecordBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.LogUtils;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;
import www.patient.jykj_zxyl.myappointment.Contract.AllDepartmentsContract;
import www.patient.jykj_zxyl.myappointment.Contract.MedicalRecordContract;

/**
 * Created by G on 2020/10/8 16:57
 */
public class AllDepartmentsPresenter extends BasePresenterImpl<AllDepartmentsContract.View> implements AllDepartmentsContract.Presenter {

    private static final String ALL_DEPARTMENTS = "all_derpartments";

    @Override
    protected Object[] getRequestTags() {
        return new Object[]{ALL_DEPARTMENTS};
    }


    @Override
    public void getAlldepartments(String params) {
        ApiHelper.getPatientTestApi().getAlldepartments(params).compose(
                Transformer.switchSchedulers(new ILoadingView() {
                    @Override
                    public void showLoadingView() {
                        if (mView != null) {
                            mView.showLoading(100);
                        }
                    }

                    @Override
                    public void hideLoadingView() {
                        if (mView != null) {
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
                            LogUtils.e("病历接口数据" + resJsonData);
                            AllDepartmentBean orderDetialBean = GsonUtils.fromJson(resJsonData, AllDepartmentBean.class);
                            if (orderDetialBean.getHospitalDepartmentList() != null && orderDetialBean.getHospitalDepartmentList().size() > 0) {
                                List<AllDepartmentBean.HospitalDepartmentListBean> hospitalDepartmentList = orderDetialBean.getHospitalDepartmentList();
                                mView.getAlldetmentsSucess(hospitalDepartmentList);
                            }
                            if (orderDetialBean.getTitleHospitalDepartment().size() > 0) {
                                List<AllDepartmentBean.TitleHospitalDepartmentBean> titleHospitalDepartment = orderDetialBean.getTitleHospitalDepartment();
                                mView.getTittleMentsSucess(titleHospitalDepartment);
                            }
                        }
                    } else {
                        mView.getDataFailure(baseBean.getResMsg());
                    }
                }


            }

            @Override
            protected void onError(String s) {
                super.onError(s);
                Log.e("xxx", "onError: " + s);
                if (mView != null) {
                    mView.showRetry();
                }
            }

            @Override
            protected String setTag() {
                return ALL_DEPARTMENTS;
            }
        });
    }
}
