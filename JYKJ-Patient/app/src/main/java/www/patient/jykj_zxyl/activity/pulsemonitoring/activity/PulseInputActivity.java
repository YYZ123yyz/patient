package www.patient.jykj_zxyl.activity.pulsemonitoring.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.pulsemonitoring.contract.PulseInputContract;
import www.patient.jykj_zxyl.activity.pulsemonitoring.presenter.PulseInputPresenter;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.custom.VerticalRulerView;

/**
 * 脉搏录入
 */
public class PulseInputActivity extends AbstractMvpBaseActivity<PulseInputContract.View, PulseInputPresenter>
        implements PulseInputContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.vsv_low_blood)
    VerticalRulerView vsvLowBlood;
    @BindView(R.id.tv_low_value)
    TextView tvLowValue;
    @BindView(R.id.vsv_heart_rate)
    VerticalRulerView vsvHeartRate;
    @BindView(R.id.tv_rate_value)
    TextView tvRateValue;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_save)
    TextView tvSave;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pulse_input;
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}