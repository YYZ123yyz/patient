package www.patient.jykj_zxyl.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.RxHttpUtils;

import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.R;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-07-16 14:37
 */
public class ToastUtils {


    private static Toast mToast;

    public ToastUtils() {
    }

    @SuppressLint("ShowToast")
    public static void showToast(String msg) {
        if (mToast == null) {

            LayoutInflater inflater = LayoutInflater.from(JYKJApplication.getConText());
            View view = inflater.inflate(R.layout.toast_common, null);
            LinearLayout llRootView = view.findViewById(R.id.ll_root_view);
            TextView content = view.findViewById(R.id.tv_toast_content);
            content.setText(msg);
            llRootView.setBackgroundResource(R.mipmap.toast_prompt);
            mToast = Toast.makeText(RxHttpUtils.getContext(), msg, Toast.LENGTH_SHORT);
            mToast.setView(view);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(msg);
        }

        mToast.show();
    }

}
