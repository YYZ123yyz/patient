package www.patient.jykj_zxyl.activity.pulsemonitoring.contract;

import www.patient.jykj_zxyl.base.mvp.BasePresenter;
import www.patient.jykj_zxyl.base.mvp.BaseView;

public class PulseMonitoringContract {
    public interface View extends BaseView {

    }
    public interface Presenter extends BasePresenter<PulseMonitoringContract.View> {

    }
}
