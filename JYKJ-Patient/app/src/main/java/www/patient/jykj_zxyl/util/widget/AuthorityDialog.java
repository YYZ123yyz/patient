package www.patient.jykj_zxyl.util.widget;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import entity.ProvideViewMyDoctorOrderAndTreatment;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.OrderMessage_OrderPayActivity;
import www.patient.jykj_zxyl.application.JYKJApplication;

public class AuthorityDialog extends Dialog {

    private ProvideViewMyDoctorOrderAndTreatment mProvideViewMyDoctorOrderAndTreatment;

    private TextView        tv_sfk;

    private TextView        commit;

    private LinearLayout    li_wxzf;
    private LinearLayout    li_zfbzf;

    private int             PayModel = 1;

    private ImageView       iv_wxzf;
    private ImageView       iv_zfbzf;

    private Context         mContext;

    public          ProgressDialog              mDialogProgress =null;

    private JYKJApplication     mApp;

    private         String                      mNetRetStr;                 //返回字符串
    private         Handler                     mHandler;

    private OrderMessage_OrderPayActivity mActivity;



    public AuthorityDialog(@NonNull Context context, OrderMessage_OrderPayActivity activity) {
        super(context, R.style.MyCommonDialog);
        mContext = context;
        mActivity = activity;
        mApp = (JYKJApplication) context.getApplicationContext();
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public void setmProvideViewMyDoctorOrderAndTreatment(ProvideViewMyDoctorOrderAndTreatment provideViewMyDoctorOrderAndTreatment){
        mProvideViewMyDoctorOrderAndTreatment = provideViewMyDoctorOrderAndTreatment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.pay_drawerlayout);
        tv_sfk = (TextView)this.findViewById(R.id.tv_sfk);
        tv_sfk.setText("￥"+mProvideViewMyDoctorOrderAndTreatment.getActualPayment()+"");
        li_wxzf = (LinearLayout)this.findViewById(R.id.li_wxzf);
        li_zfbzf = (LinearLayout)this.findViewById(R.id.li_zfbzf);
        iv_wxzf = (ImageView)this.findViewById(R.id.iv_wxzf);
        iv_zfbzf = (ImageView)this.findViewById(R.id.iv_zfbzf);

        li_wxzf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_wxzf.setVisibility(View.VISIBLE);
                iv_zfbzf.setVisibility(View.GONE);
                PayModel = 1;
            }
        });
        li_zfbzf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_wxzf.setVisibility(View.GONE);
                iv_zfbzf.setVisibility(View.VISIBLE);
                PayModel = 2;
            }
        });
        commit = (TextView)this.findViewById(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.zf(PayModel);
            }
        });
    }


}
