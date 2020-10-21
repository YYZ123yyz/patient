package www.patient.jykj_zxyl.activity.pulsemonitoring.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.TrendActivity;
import www.patient.jykj_zxyl.activity.home.patient.BloodEntryActivity;
import www.patient.jykj_zxyl.activity.pulsemonitoring.contract.PulseMonitoringContract;
import www.patient.jykj_zxyl.activity.pulsemonitoring.presenter.PulseMonitoringPresenter;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;

/**
 * 脉搏监控
 */
public class PulseMonitoringActivity extends AbstractMvpBaseActivity<PulseMonitoringContract.View, PulseMonitoringPresenter>
        implements PulseMonitoringContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_low_num)
    TextView tvLowNum;
    @BindView(R.id.iv_blood_type)
    ImageView ivBloodType;
    @BindView(R.id.tv_update_time)
    TextView tvUpdateTime;
    @BindView(R.id.ll_luru)
    LinearLayout llLuru;
    @BindView(R.id.ll_qushi)
    LinearLayout llQushi;
    @BindView(R.id.ll_jilu)
    LinearLayout llJilu;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_advisory_doctor)
    TextView tvAdvisoryDoctor;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pulse_monitoring;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @OnClick({R.id.ll_luru,R.id.ll_back,R.id.ll_qushi,R.id.ll_jilu})
    public void onClick(View v) {
        switch (v.getId()) {
            //脉搏录入
            case R.id.ll_luru:
                startActivity(new Intent(this, PulseInputActivity.class));
                break;
            //关闭当前页面
            case R.id.ll_back:
                finish();
                break;
                //数据趋势
            case R.id.ll_qushi:
                startActivity(new Intent(this, TrendActivity.class));
                break;
                //脉搏异常记录
            case R.id.ll_jilu:
                startActivity(new Intent(this, AbnormalPulseActivity.class));
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();

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