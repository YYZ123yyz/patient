package www.patient.jykj_zxyl.base.base_manager;

import android.app.Activity;

import com.allen.library.interceptor.Transformer;
import com.allen.library.interfaces.ILoadingView;
import com.allen.library.observer.CommonObserver;

import java.util.HashMap;

import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.http.ApiHelper;
import www.patient.jykj_zxyl.base.http.CommonDataObserver;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;

/**
 * Description:订单社交操作
 *
 * @author: qiuxinhai
 * @date: 2020-08-03 14:38
 */
public class OrderOperationManager {
    public static Object OnCallBackListener;

    private static class InstanceHolder {
        private static final OrderOperationManager INSTANCE = new OrderOperationManager();
    }

    public static OrderOperationManager getInstance() {
        return InstanceHolder.INSTANCE;
    }


    /**
     * 订单操作接口
     *
     * @param mainDoctorCode  医生编码
     * @param mainDoctorName  医生姓名
     * @param signCode        签约订单编码
     * @param signNo          签约订单编号
     * @param mainPatientCode 患者编码
     * @param mainUserName    患者名称
     * @param confimresult    操作： 0拒绝，1同意，2需修改
     */
    public void sendOrderOperRequest(String mainDoctorCode, String mainDoctorName
            , String signCode, String signNo, String mainPatientCode
            , String mainUserName, String confimresult, String loginDoctorPosition,
                                            OnCallBackListener onCallBackListener) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition", loginDoctorPosition);
        hashMap.put("mainDoctorCode", mainDoctorCode);
        hashMap.put("mainDoctorName", mainDoctorName);
        hashMap.put("signCode", signCode);
        hashMap.put("signNo", signNo);
        hashMap.put("mainPatientCode", mainPatientCode);
        hashMap.put("mainUserName", mainUserName);
        hashMap.put("confimresult", confimresult);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operSignOrderStatus(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        if (onCallBackListener != null) {
                            int resCode = baseBean.getResCode();
                            if (resCode == 1) {
                                onCallBackListener.onResult(true, baseBean.getResJsonData());
                            } else {
                                onCallBackListener.onResult(false, baseBean.getResMsg());
                            }
                        }
                    }


                });
    }


    /**
     * 订单操作接口
     *
     * @param mainDoctorCode  医生编码
     * @param mainDoctorName  医生姓名
     * @param signCode        签约订单编码
     * @param signNo          签约订单编号
     * @param mainPatientCode 患者编码
     * @param mainUserName    患者名称
     * @param confimresult    操作： 1同意，2需修改
     */
    public void sendOrderCancelContractOperRequest(String mainDoctorCode, String mainDoctorName
            , String signCode, String signNo, String mainPatientCode
            , String mainUserName, String confimresult, String loginDoctorPosition,
                                     OnCallBackListener onCallBackListener) {
        HashMap<String, Object> hashMap = ParameUtil.buildBaseParam();
        hashMap.put("loginDoctorPosition", loginDoctorPosition);
        hashMap.put("mainDoctorCode", mainDoctorCode);
        hashMap.put("mainDoctorName", mainDoctorName);
        hashMap.put("signCode", signCode);
        hashMap.put("signNo", signNo);
        hashMap.put("mainPatientCode", mainPatientCode);
        hashMap.put("mainUserName", mainUserName);
        hashMap.put("confimresult", confimresult);
        hashMap.put("refuseReasonClassCode","");
        hashMap.put("refuseReasonClassName","");
        hashMap.put("refuseRemark","");
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().operTerminationConfim(s).compose(Transformer.switchSchedulers())
                .subscribe(new CommonDataObserver() {
                    @Override
                    protected void onSuccessResult(BaseBean baseBean) {
                        if (onCallBackListener != null) {
                            int resCode = baseBean.getResCode();
                            if (resCode == 1) {
                                onCallBackListener.onResult(true, "");
                            } else {
                                onCallBackListener.onResult(false, baseBean.getResMsg());
                            }
                        }
                    }


                });
    }


    /**
     * 发送病例确认操作
     * @param orderCode 订单code
     */
    public void sendOperPatientMedicalRecordConfirmRequest(String orderCode, Activity activity,
                                                           OnCallBackListener onCallBackListener){
        HashMap<String, Object> hashMap = ParameUtil.buildBasePatientParam(activity);
        hashMap.put("orderCode",orderCode);
        String s = RetrofitUtil.encodeParam(hashMap);
        ApiHelper.getApiService().confirmPatientRecordDet(s).compose(
                Transformer.switchSchedulers()).subscribe(new CommonDataObserver() {
            @Override
            protected void onSuccessResult(BaseBean baseBean) {
                if (onCallBackListener != null) {
                    int resCode = baseBean.getResCode();
                    if (resCode == 1) {
                        onCallBackListener.onResult(true, "");
                    } else {
                        onCallBackListener.onResult(false, baseBean.getResMsg());
                    }
                }
            }
        });
    }


    public interface OnCallBackListener {

        void onResult(boolean isSucess, String msg);
    }
}
