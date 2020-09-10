package www.patient.jykj_zxyl.myappointment.Contract;

import java.util.List;

import www.patient.jykj_zxyl.activity.myself.order.RefusedOrderContract;
import www.patient.jykj_zxyl.base.base_bean.BaseBean;
import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.base.base_bean.OrderDetialBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientCommitBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientDoctorInfoBean;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientListBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;
import www.patient.jykj_zxyl.myappointment.bean.IDCardBean;

public class ReservationContract {
    public interface View extends BaseView {
        /*
         *可预约页面头部查询
         * */
        void getReservationTiteResult(List<ReservePatientDoctorInfoBean> reservePatientDoctorInfoBean);

        //可预约列表查询
        void getReservationListResult(List<ReservePatientListBean> reservePatientListBeans);
        /**
         * 可预约列表查询获取失败
         */
        void getReservationListResultError(String msg);


        /**
         * 提交确认
         * @param confim 确认Id
         */
        void getReservationCommitConfimResult(String confim,String msg);

        /**
         *
         * @param msg
         */
        void getReservationCommitIdCardCheckResult(String msg);
        //预约提交
        void getReservationCommitResult(ReservePatientCommitBean reservePatientCommitBeans);

        /**
         * 提交失败
         */
        void getReservationCommitResultError(String msg);
        /**
         * 有未支付的订单
         */
        void getReservationunpaidResultError(ReservePatientCommitBean reservePatientCommitBeans);
        /**
         * 预约中的dialog
         * @param
         */
        void getReservationDailog();
        //类型选择
        void getReservationClassResult(List<BaseReasonBean> baseReasonBeans);

        //患者身份认证
        void getReservationIDCardResult(String msg);
        /**
         *  患者验证失败
         * @param msg
         */
        void  getReservationIDCardResultError(String msg);
        //取消预约
        void getReservationCancelResult(boolean isSucess, String msg);
        /**
         * 获取订单信息返回结果
         * @param orderDetialBean 订单信息
         */
        void getSearchSignPatientDoctorOrderResult(OrderDetialBean orderDetialBean);
    }

    public interface Presenter extends BasePresenter<View> {
        /*
         *可预约页面头部查询
         * */
        void sendReservationTiteRequest(String loginPatientPosition,
                                        String mainDoctorCode,
                                        String mainDoctorName);

        //可预约列表查询
        void sendReservationListRequest(String loginPatientPosition,
                                        String mainDoctorCode,
                                        String mainDoctorName,
                                        String times,
                                        Integer reserveType
        );

        //预约提交
        void sendReservationCommitRequest(String loginPatientPosition,
                                                String requestClientType,
                                                String mainDoctorCode,
                                                String mainDoctorName,
                                                String mainPatientCode,
                                                String mainPatientName,
                                                String reserveRosterDateCode,
                                                String treatmentType,
                                                String reserveTimes,
                                                String reserveProjectCode,
                                                String reserveProjectName,
                                                String blockNo,
                                                String confim);

        //类型选择
        void sendReservationClassRequest(String baseCode);

        //患者身份认证
        void sendReservationIDCardRequest(String loginPatientPosition,
                                               String patientCode,
                                               String userName,
                                               String idNumber
        );

        //取消预约
        void sendReservationCancelRequest(String loginPatientPosition,
                                          String mainPatientCode,
                                          String mainPatientName,
                                          String reserveCode,
                                          String cancelReserveCode,
                                          String cancelReserveName,
                                          String cancelReserveRemark
        );
        /**
         * 发送获取订单信息接口
         * @param signCode 订单code
         * @param operDoctorCode 医生code
         * @param operDoctorName 医生name
         */
        void sendSearchSignPatientDoctorOrderRequest(String signCode, String operDoctorCode, String operDoctorName);

    }
}
