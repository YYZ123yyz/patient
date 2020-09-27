package www.patient.jykj_zxyl.myappointment.Presenter;

import android.util.Log;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.CheckListBean;
import www.patient.jykj_zxyl.base.base_bean.MyReservationListBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;
import www.patient.jykj_zxyl.myappointment.Contract.CheckListContract;
import www.patient.jykj_zxyl.myappointment.Contract.ReservationListContract;

public class CheckListPresenter  extends BasePresenterImpl<CheckListContract.View>
        implements CheckListContract.Presenter {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

    @Override
    public void sendCheckListRequest(String loginPatientPosition, String requestClientType, String mainPatientCode, String mainPatientName, String orderCode) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginPatientPosition", loginPatientPosition);
        map.put("requestClientType", requestClientType);
        map.put("mainPatientCode", mainPatientCode);
        map.put("mainPatientName", mainPatientName);
        map.put("orderCode", orderCode);
        String s = RetrofitUtil.encodeParam(map);
        ApiHelper.getPatientTestApi().getCheckListDet(s).compose(Transformer.switchSchedulers(new ILoadingView() {
            @Override
            public void showLoadingView() {

            }

            @Override
            public void hideLoadingView() {

            }
        })).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        String resJsonData = baseBean.getResJsonData();
                        Log.e("TAG", "onSuccessResult:检查检验单 "+resJsonData );
                        CheckListBean checkListBean = GsonUtils.fromJson(resJsonData, CheckListBean.class);
                        mView.getCheckListSucess(checkListBean);
                    }
                }

            }
        });
    }
}
