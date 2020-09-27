package www.patient.jykj_zxyl.myappointment.Contract;

import www.patient.jykj_zxyl.base.base_bean.MedicalRecordBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Created by G on 2020/9/27 10:58
 */
public class InquiryContract {


    public interface View extends BaseView {
        void hasNoData();

        void hasData();
    }

    public interface Presenter extends BasePresenter<View> {

        void getDataDet(String params);

        void submitData(String params);

    }


}
