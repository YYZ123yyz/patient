package www.patient.jykj_zxyl.myappointment.Contract;

import www.patient.jykj_zxyl.base.base_bean.CheckListBean;
import www.patient.jykj_zxyl.base.base_bean.PrescriptionDetBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

public class PrescriptionDetContract {
    public interface View extends BaseView {
        void getPrescriptionDetSucess(PrescriptionDetBean prescriptionDetBean);

        void downLoadUrl(String url);
    }
    public interface Presenter extends BasePresenter<PrescriptionDetContract.View> {
        void sendPrescriptionDetRequest(String loginPatientPosition,
                                  String requestClientType,
                                  String mainPatientCode,
                                  String mainPatientName,
                                  String orderCode
        );


        void getDownloadDet(String params);
    }
}
