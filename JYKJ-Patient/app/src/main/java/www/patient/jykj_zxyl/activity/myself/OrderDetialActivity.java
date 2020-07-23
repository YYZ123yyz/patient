package www.patient.jykj_zxyl.activity.myself;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_activity.BaseActivity;
import www.patient.jykj_zxyl.base.base_view.BaseToolBar;

/**
 * Description:订单详情
 *
 * @author: qiuxinhai
 * @date: 2020-07-22 13:51
 */
public class OrderDetialActivity extends BaseActivity {
    @BindView(R.id.txt_main_title)
    TextView txtMainTitle;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_pay_status)
    TextView tvPayStatus;
    @BindView(R.id.tv_consulting_doctor)
    TextView tvConsultingDoctor;
    @BindView(R.id.tv_consultant_name)
    TextView tvConsultantName;
    @BindView(R.id.tv_consultant_phone)
    TextView tvConsultantPhone;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_appointment_time)
    TextView tvAppointmentTime;
    @BindView(R.id.tv_service_price_total)
    TextView tvServicePriceTotal;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_validity_date)
    TextView tvValidityDate;
    @BindView(R.id.tv_service_item)
    TextView tvServiceItem;
    @BindView(R.id.tv_payment)
    TextView tvPayment;
    @BindView(R.id.tv_payment_method)
    TextView tvPaymentMethod;
    @BindView(R.id.iv_weixin_icon)
    ImageView ivWeixinIcon;
    @BindView(R.id.toolbar)
    BaseToolBar mBaseToolBar;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_order_detial;
    }

    @Override
    protected void initView() {
        super.initView();
        mBaseToolBar = findViewById(R.id.toolbar);
        txtMainTitle.setText("订单详情");
        //添加监听
        addListener();
    }


    @Override
    protected void initData() {
        super.initData();
    }


    /**
     * 添加监听
     */
    private void addListener() {
        mBaseToolBar.setLeftTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
