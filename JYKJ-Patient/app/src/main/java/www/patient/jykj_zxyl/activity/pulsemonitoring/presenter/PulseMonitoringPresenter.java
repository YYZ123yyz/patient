package www.patient.jykj_zxyl.activity.pulsemonitoring.presenter;

import www.patient.jykj_zxyl.activity.pulsemonitoring.contract.PulseMonitoringContract;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;

public class PulseMonitoringPresenter extends BasePresenterImpl<PulseMonitoringContract.View> implements PulseMonitoringContract.Presenter {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

}
