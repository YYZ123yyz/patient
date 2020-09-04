package www.patient.jykj_zxyl.myappointment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import www.patient.jykj_zxyl.R;

/*
* 付款成功dialog
* */
public class Payment_SuccessDialog extends Dialog {
    private Context mContext;
    private View mRootView;
    private ImageView success;

    public Payment_SuccessDialog(@NonNull Context context) {
        super(context,R.style.MyDialog);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(24, 0, 24, 80);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
       init(context);

    }

    private void init(Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.payment_success_layout, null);
    }
}
