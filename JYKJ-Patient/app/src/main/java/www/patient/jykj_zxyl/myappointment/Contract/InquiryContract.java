package www.patient.jykj_zxyl.myappointment.Contract;

import www.patient.jykj_zxyl.base.base_bean.MedicalRecordBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;
import www.patient.jykj_zxyl.myappointment.bean.InquiryBean;

/**
 * Created by G on 2020/9/27 10:58
 */
public class InquiryContract {


    public interface View extends BaseView {
        void hasNoData();

        void hasData(InquiryBean bean);

        void commitSucess();

        void commitFiled(String msg);
    }

    public interface Presenter extends BasePresenter<View> {

        void getDataDet(String params);

        void submitData(String params);

    }


}
