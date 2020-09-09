package www.patient.jykj_zxyl.myappointment.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import www.patient.jykj_zxyl.R;
/*
* 预约中
* */
public class SuccessfulDialog extends Dialog {
    private Context mContext;
    private View mRootView;
    private int count = 3;
    private TextView num;
    private SuccessfulDialog successfulDialog;
    private ImageView img;

    public SuccessfulDialog(@NonNull Context context) {
        super(context, R.style.MyCommonDialog);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(24, 0, 24, 80);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.success_layout);
        successfulDialog = SuccessfulDialog.this;
        img = findViewById(R.id.img);
        RotateAnimation animation = new RotateAnimation(0, 360);
        animation.setDuration(100000);//设定转一圈的时间
        animation.setRepeatCount(Animation.INFINITE);//设定无限循环
        animation.setRepeatMode(Animation.RESTART);
        img.startAnimation(animation);
    }

}
