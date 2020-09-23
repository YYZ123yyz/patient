package www.patient.jykj_zxyl.myappointment.Contract;

import com.hyphenate.easeui.order.OrderDetialContract;

import www.patient.jykj_zxyl.base.base_bean.MedicalRecordBean;
import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

/**
 * Created by G on 2020/9/22 14:18
 */
public class MedicalRecordContract {

    public interface View extends BaseView {
        void getMedicalRecordSucess(MedicalRecordBean bean);

        void getDataFailure(String msg);
    }

    public interface Presenter extends BasePresenter<View> {

        void getRecordDet(String params);

    }
}
