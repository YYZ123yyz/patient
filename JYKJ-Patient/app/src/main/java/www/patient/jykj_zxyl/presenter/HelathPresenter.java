package www.patient.jykj_zxyl.presenter;

import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;
import www.patient.jykj_zxyl.view.HealthView;
import www.patient.jykj_zxyl.view.MedicalRecordView;

/**
 * Created by G on 2020/9/16 17:52
 */
public class HelathPresenter extends BasePresenterImpl<HealthView> {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }
}
