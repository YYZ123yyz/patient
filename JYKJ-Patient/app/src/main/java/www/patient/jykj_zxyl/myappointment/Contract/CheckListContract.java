package www.patient.jykj_zxyl.myappointment.Contract;

import www.patient.jykj_zxyl.base.base_bean.CheckListBean;
import www.patient.jykj_zxyl.base.base_bean.MedicalRecordBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

public class CheckListContract {
    public interface View extends BaseView {
        void getCheckListSucess(CheckListBean checkListBean);

    }

    public interface Presenter extends BasePresenter<CheckListContract.View> {
        void sendCheckListRequest(String loginPatientPosition,
                                  String requestClientType,
                                  String mainPatientCode,
                                  String mainPatientName,
                                  String orderCode
        );
    }
}
