package www.patient.jykj_zxyl.myappointment.Presenter;

import android.util.Log;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.MyReservationListBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;
import www.patient.jykj_zxyl.myappointment.Contract.ReservationContract;
import www.patient.jykj_zxyl.myappointment.Contract.ReservationListContract;
import www.patient.jykj_zxyl.myappointment.bean.IDCardBean;

public class ResevationListPresenter extends BasePresenterImpl<ReservationListContract.View>
        implements ReservationListContract.Presenter  {


    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

    @Override
    public void sendMyReservationListRequest(String loginPatientPosition, String mainPatientCode, String mainPatientName, String reserveStatusType, String rowNum, String pageNum) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginPatientPosition", loginPatientPosition);
        map.put("mainPatientCode", mainPatientCode);
        map.put("mainPatientName", mainPatientName);
        map.put("reserveStatusType", reserveStatusType);
        map.put("rowNum", rowNum);
        map.put("pageNum", pageNum);
        String s = RetrofitUtil.encodeParam(map);
        ApiHelper.getApiService().myResevationMyList(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        Log.e("TAG", "onSuccessResult:就诊中 "+resJsonData );
                        List<MyReservationListBean> myReservationListBeans = GsonUtils.jsonToList(resJsonData, MyReservationListBean.class);
                        mView.getMyReservationListResult(myReservationListBeans);
                    }
                }

            }
        });
    }
}
