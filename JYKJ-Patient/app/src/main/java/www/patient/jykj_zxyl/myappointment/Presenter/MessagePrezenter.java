package www.patient.jykj_zxyl.myappointment.Presenter;

import android.util.Log;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.CheckListBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;
import www.patient.jykj_zxyl.myappointment.Contract.CheckListContract;
import www.patient.jykj_zxyl.myappointment.Contract.MessageContract;
import www.patient.jykj_zxyl.myappointment.bean.ViewInteractPatientMessageBean;

public class MessagePrezenter extends BasePresenterImpl<MessageContract.View>
        implements MessageContract.Presenter  {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

    @Override
    public void getMessageRequest(String loginPatientPosition, String requestClientType, String operPatientCode, String operPatientName, String orderCode) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginPatientPosition", loginPatientPosition);
        map.put("requestClientType", requestClientType);
        map.put("operPatientCode", operPatientCode);
        map.put("operPatientName", operPatientName);
        map.put("orderCode", orderCode);
        String s = RetrofitUtil.encodeParam(map);
        ApiHelper.getPatientTestApi().getMessage(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        ViewInteractPatientMessageBean viewInteractPatientMessageBean = GsonUtils.fromJson(resJsonData, ViewInteractPatientMessageBean.class);
                       mView.getMessageSucess(viewInteractPatientMessageBean);
                    }
                }

            }
        });
    }
}
