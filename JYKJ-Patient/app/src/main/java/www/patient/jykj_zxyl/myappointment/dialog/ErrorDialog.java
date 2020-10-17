package www.patient.jykj_zxyl.myappointment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;

/*
 * 预约失败
 * */
public class ErrorDialog extends Dialog {

    private TextView msg1;
    private TextView msg2;
    private String errMsg;

    public ErrorDialog(@NonNull Context context) {
        super(context, R.style.MyCommonDialog);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }

    public ErrorDialog(@NonNull Context context, String msg) {
        super(context, R.style.MyCommonDialog);
        errMsg = msg;
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.error_layout);


        msg1 = findViewById(R.id.msg_1);
        msg2 = findViewById(R.id.msg_2);

        if (errMsg !=null && !TextUtils.isEmpty(errMsg)){
            setFiledMsg();
        }
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    public void setFiledMsg() {
        msg1.setText(errMsg);
        msg2.setVisibility(View.GONE);

    }
}

