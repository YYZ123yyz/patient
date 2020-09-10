package www.patient.jykj_zxyl.myappointment.Presenter;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;

import java.util.HashMap;
import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientCommitBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientDoctorInfoBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientListBean;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;
import www.patient.jykj_zxyl.myappointment.Contract.ReservationContract;
import www.patient.jykj_zxyl.myappointment.activity.ReservationActivity;
import www.patient.jykj_zxyl.myappointment.activity.VerifiedActivity;
import www.patient.jykj_zxyl.myappointment.bean.IDCardBean;
import www.patient.jykj_zxyl.util.tencenUtil.models.TrainTicketOCRRequest;

public class ResevationPresenter extends BasePresenterImpl<ReservationContract.View>
        implements ReservationContract.Presenter {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }
    /**获取订单详情信息*/
    private static final String SEND_SEARCH_SIGN_PATIENT_DOCTOR_ORDER_REQUEST_TAG="send_search_sign_patient_doctor_order_request_tag";
    @Override
    public void sendReservationTiteRequest(String loginPatientPosition, String mainDoctorCode, String mainDoctorName) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginPatientPosition", loginPatientPosition);
        hashMap.put("mainDoctorCode", mainDoctorCode);
        hashMap.put("mainDoctorName", mainDoctorName);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().resevationTitle(s).compose(
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
                })
        ).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                String resJsonData = baseBean.getResJsonData();
                List<ReservePatientDoctorInfoBean> reservePatientDoctorInfoBeans = GsonUtils.jsonToList(resJsonData, ReservePatientDoctorInfoBean.class);
                if (mView != null) {
                    mView.getReservationTiteResult(reservePatientDoctorInfoBeans);
                }
            }
        });
    }

    //可预约列表查询
    @Override
    public void sendReservationListRequest(String loginPatientPosition, String mainDoctorCode, String mainDoctorName, String times,Integer reserveType) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginPatientPosition", loginPatientPosition);
        hashMap.put("mainDoctorCode", mainDoctorCode);
        hashMap.put("mainDoctorName", mainDoctorName);
        hashMap.put("times", times);
        hashMap.put("reserveType", reserveType);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().resevationList(s).compose(
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
                })
        ).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if(resCode==1){
                        String resJsonData = baseBean.getResJsonData();
                        Log.e("TAG", "onSuccessResult:列表 "+resJsonData );
                        List<ReservePatientListBean> reservePatientListBeans = GsonUtils.jsonToList(resJsonData, ReservePatientListBean.class);
                        mView.getReservationListResult(reservePatientListBeans);
                    }else{
                        mView.getReservationListResultError(baseBean.getResMsg());
                    }

                }


            }
        });
    }

    @Override
    public void sendReservationCommitRequest(String loginPatientPosition, String requestClientType, String mainDoctorCode, String mainDoctorName, String mainPatientCode, String mainPatientName, String reserveRosterDateCode, String treatmentType, String reserveTimes, String reserveProjectCode, String reserveProjectName, String blockNo, String confim) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginPatientPosition", loginPatientPosition);
        hashMap.put("requestClientType", requestClientType);
        hashMap.put("mainDoctorCode", mainDoctorCode);
        hashMap.put("mainDoctorName", mainDoctorName);
        hashMap.put("mainPatientCode", mainPatientCode);
        hashMap.put("mainPatientName", mainPatientName);
        hashMap.put("reserveRosterDateCode", reserveRosterDateCode);
        hashMap.put("treatmentType", treatmentType);
        hashMap.put("reserveTimes", reserveTimes);
        hashMap.put("reserveProjectCode", reserveProjectCode);
        hashMap.put("reserveProjectName", reserveProjectName);
        hashMap.put("blockNo", blockNo);
        hashMap.put("confim", confim);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().resevationSubmit(s).compose(
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
                })
        ).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {

                if (mView != null) {
                    int resCode = baseBean.getResCode();
                    if(resCode==1){
                        String resJsonData = baseBean.getResJsonData();
                        Log.e("TAG", "预约成功: "+resJsonData );
                        ReservePatientCommitBean reservePatientCommitBean = GsonUtils.fromJson(resJsonData, ReservePatientCommitBean.class);
                        String status = reservePatientCommitBean.getStatus();
                        if (StringUtils.isNotEmpty(status)) {
                            if (status.equals("1")) {
                                mView.getReservationCommitConfimResult(reservePatientCommitBean.getConfim(),reservePatientCommitBean.getMessage());
                            }else if(status.equals("2")){
                                mView.getReservationCommitIdCardCheckResult(reservePatientCommitBean.getMessage());
                            }
                            else if(status.equals("3")){
                            mView.getReservationunpaidResultError(reservePatientCommitBean);
                            }
                        }else{
                            mView.getReservationCommitResult(reservePatientCommitBean);
                        }

                    }else{
                        mView.getReservationCommitResultError(baseBean.getResMsg());
                    }
                }
            }
        });
    }

    @Override
    public void sendReservationClassRequest(String baseCode) {

        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("baseCode", baseCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().getBasicsDomain(s).compose(
                Transformer.switchSchedulers(new ILoadingView() {
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
                        Log.e("TAG", "onSuccessResult: "+resJsonData );
                        List<BaseReasonBean> mCancelContractBeans
                                = GsonUtils.jsonToList(resJsonData, BaseReasonBean.class);
                        mView.getReservationClassResult(mCancelContractBeans);
                    }
                }


            }

        });
    }

    //患者身份认证
    @Override
    public void sendReservationIDCardRequest(String loginPatientPosition, String patientCode, String userName, String idNumber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginPatientPosition", loginPatientPosition);
        map.put("patientCode", patientCode);
        map.put("userName", userName);
        map.put("idNumber", idNumber);
        String s = RetrofitUtil.encodeParam(map);
        ApiHelper.getApiService().resevationIDCard(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        IDCardBean idCardBean = GsonUtils.fromJson(resJsonData, IDCardBean.class);
                        mView.getReservationIDCardResult(baseBean.getResMsg());
                    }else{
                        mView.getReservationIDCardResultError(baseBean.getResMsg());
                    }
                }

            }
        });
    }

    //取消
    @Override
    public void sendReservationCancelRequest(String loginPatientPosition, String mainPatientCode, String mainPatientName, String reserveCode, String cancelReserveCode, String cancelReserveName, String cancelReserveRemark) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginPatientPosition", loginPatientPosition);
        map.put("mainPatientCode", mainPatientCode);
        map.put("mainPatientName", mainPatientName);
        map.put("reserveCode", reserveCode);
        map.put("cancelReserveCode", cancelReserveCode);
        map.put("cancelReserveName", cancelReserveName);
        map.put("cancelReserveRemark", cancelReserveRemark);
        String s = RetrofitUtil.encodeParam(map);
        ApiHelper.getApiService().cancelResevation(s).compose(Transformer.switchSchedulers(new ILoadingView() {
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
                        mView.getReservationCancelResult(true,"");
                    }else{
                        mView.getReservationCancelResult(false,"");
                    }
                }

            }
        });
    }

    /**
     *
     * @param signCode 订单code
     * @param operDoctorCode 医生code
     * @param operDoctorName 医生name
     */
    @Override
    public void sendSearchSignPatientDoctorOrderRequest(String signCode, String operDoctorCode, String operDoctorName) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition",ParameUtil.loginDoctorPosition);
        hashMap.put("signOrderCode",signCode);
        hashMap.put("operDoctorCode",operDoctorCode);
        hashMap.put("operDoctorName",operDoctorName);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getPatientTestApi().searchSignPatientDoctorOrder(s)
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

}
