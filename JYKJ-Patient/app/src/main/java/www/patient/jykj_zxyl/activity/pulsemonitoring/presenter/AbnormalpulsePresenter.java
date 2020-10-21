package www.patient.jykj_zxyl.activity.pulsemonitoring.presenter;

import www.patient.jykj_zxyl.activity.pulsemonitoring.contract.AbnormalPulseContract;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;

public class AbnormalpulsePresenter extends BasePresenterImpl<AbnormalPulseContract.View> implements AbnormalPulseContract.Presenter {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

}
