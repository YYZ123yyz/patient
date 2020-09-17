package www.patient.jykj_zxyl.presenter;

import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;
import www.patient.jykj_zxyl.view.MedicalRecordView;

/**
 * Created by G on 2020/9/14 9:49
 */
public class MedicalRecordPresenter extends BasePresenterImpl<MedicalRecordView> {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }
}
