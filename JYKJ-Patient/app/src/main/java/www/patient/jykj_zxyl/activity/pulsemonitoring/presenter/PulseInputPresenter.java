package www.patient.jykj_zxyl.activity.pulsemonitoring.presenter;

import www.patient.jykj_zxyl.activity.pulsemonitoring.contract.PulseInputContract;
import www.patient.jykj_zxyl.activity.pulsemonitoring.contract.PulseMonitoringContract;
import www.patient.jykj_zxyl.base.mvp.BasePresenterImpl;

public class PulseInputPresenter extends BasePresenterImpl<PulseInputContract.View> implements PulseInputContract.Presenter {
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }

}
