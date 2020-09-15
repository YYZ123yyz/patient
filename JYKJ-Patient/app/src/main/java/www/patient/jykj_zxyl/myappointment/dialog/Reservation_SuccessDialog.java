package www.patient.jykj_zxyl.myappointment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_WZZLActivity;
import www.patient.jykj_zxyl.activity.home.patient.WZXXOrderActivity;

public class Reservation_SuccessDialog extends Dialog {
    private Context mContext;
    private View mRootView;
    private TextView btn_success;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public Reservation_SuccessDialog(@NonNull Context context) {
        super(context, R.style.MyCommonDialog);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    //    init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.success_reservation);
        btn_success= findViewById(R.id.btn_success) ;
        addListener();
    }
    void addListener(){
        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null) {
                    onClickListener.onClickSucessBtn();
                }
            }
        });
    }

    public interface OnClickListener{
        void onClickSucessBtn();
    }

}
