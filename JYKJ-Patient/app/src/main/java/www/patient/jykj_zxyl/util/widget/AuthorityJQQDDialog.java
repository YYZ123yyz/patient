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

public class AuthorityJQQDDialog extends Dialog {

    private ProvideViewMyDoctorOrderAndTreatment mProvideViewMyDoctorOrderAndTreatment;



    private Context         mContext;

    public          ProgressDialog              mDialogProgress =null;

    private JYKJApplication     mApp;

    private         String                      mNetRetStr;                 //返回字符串
    private         Handler                     mHandler;


    private         ImageView   iv_gb;
    private         TextView    tv_qd;


    public AuthorityJQQDDialog(@NonNull Context context) {
        super(context, R.style.MyCommonDialog);
        mContext = context;
        mApp = (JYKJApplication) context.getApplicationContext();
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity. CLIP_HORIZONTAL);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public void setmProvideViewMyDoctorOrderAndTreatment(ProvideViewMyDoctorOrderAndTreatment provideViewMyDoctorOrderAndTreatment){
        mProvideViewMyDoctorOrderAndTreatment = provideViewMyDoctorOrderAndTreatment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.jqqd);
        iv_gb = (ImageView)this.findViewById(R.id.iv_gb);
        tv_qd = (TextView)this.findViewById(R.id.tv_qd);
        iv_gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        tv_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
    }


}
