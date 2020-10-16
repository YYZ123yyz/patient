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
    }

    public interface Presenter extends BasePresenter<MessageContract.View> {
        void getMessageRequest(String loginPatientPosition,
                               String requestClientType,
                               String operPatientCode,
                               String operPatientName,
                               String orderCode);
    }
}
