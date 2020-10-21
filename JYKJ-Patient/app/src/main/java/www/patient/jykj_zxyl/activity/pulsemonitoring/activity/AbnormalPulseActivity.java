package www.patient.jykj_zxyl.activity.pulsemonitoring.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.pulsemonitoring.contract.AbnormalPulseContract;
import www.patient.jykj_zxyl.activity.pulsemonitoring.presenter.AbnormalpulsePresenter;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;

/**
 * 脉搏异常列表
 * AbnormalPulseAdapter  适配器
 */
public class AbnormalPulseActivity extends AbstractMvpBaseActivity<AbnormalPulseContract.View, AbnormalpulsePresenter>
        implements AbnormalPulseContract.View {


    @BindView(R.id.iv_back_left)
    ImageView ivBackLeft;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.log_list)
    RecyclerView logList;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_abnormal_pulse;
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initView() {
        super.initView();
    }
  @OnClick({R.id.iv_back_left})
  public void onClick(View v){
        switch (v.getId()){
            //关闭当前页面
            case R.id.iv_back_left:
                finish();
                break;
        }
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