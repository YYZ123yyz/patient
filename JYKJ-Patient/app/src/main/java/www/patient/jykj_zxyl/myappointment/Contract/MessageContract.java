package www.patient.jykj_zxyl.myappointment.Contract;

import java.util.List;

import www.patient.jykj_zxyl.base.base_bean.AllDepartmentBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;
import www.patient.jykj_zxyl.myappointment.bean.ViewInteractPatientMessageBean;

public class MessageContract {
    public interface View extends BaseView {
        /**
         * 获取诊后留言
         * @param viewInteractPatientMessageBeans
         */
        void getMessageSucess(ViewInteractPatientMessageBean viewInteractPatientMessageBeans);

        /**
         * 提交成功
         * @param msg
         */
        void getMessageCommitSucess(String msg);

        /**
         * 提交失败
         * @param msg
         */
        void getMessageCommitError(String msg);
    }

    public interface Presenter extends BasePresenter<MessageContract.View> {
        /**
         * 获取诊后留言 入参
         * @param loginPatientPosition
         * @param requestClientType
         * @param operPatientCode
         * @param operPatientName
         * @param orderCode
         */
        void getMessageRequest(String loginPatientPosition,
                               String requestClientType,
                               String operPatientCode,
                               String operPatientName,
                               String orderCode);

        /**
         * 提交
         * @param loginPatientPosition
         * @param requestClientType
         * @param operPatientCode
         * @param operPatientName
         * @param messageId
         * @param imgCode
         * @param orderCode
         * @param treatmentType
         * @param patientLinkPhone
         * @param messageContent
         * @param imgIdArray
         * @param imgBase64Array
         */
        void getMessageCommitRequest(String loginPatientPosition,
                               String requestClientType,
                               String operPatientCode,
                               String operPatientName,
                               String messageId,
                                     String imgCode,
                                     String orderCode,
                                     String treatmentType,
                                     String patientLinkPhone,
                                     String messageContent,
                                     String imgIdArray,
                                     String imgBase64Array





                                     );
    }
}
