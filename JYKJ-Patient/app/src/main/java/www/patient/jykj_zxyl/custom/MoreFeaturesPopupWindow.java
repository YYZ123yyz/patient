package www.patient.jykj_zxyl.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.DoctorsUnionActivity;
import www.patient.jykj_zxyl.activity.home.MyClinicActivity;
import www.patient.jykj_zxyl.activity.home.MyPatientActivity;
import www.patient.jykj_zxyl.activity.home.NewsActivity;
import www.patient.jykj_zxyl.activity.home.QRCodeActivity;
import www.patient.jykj_zxyl.activity.home.myself.JDDAActivity;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.activity.home.tjhz.AddPatientActivity;
import www.patient.jykj_zxyl.activity.myself.MedicationRecordActivity;
import www.patient.jykj_zxyl.activity.myself.MedicationSettingsActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.fragment.FragmentShouYe;
import zxing.android.CaptureActivity;

public class MoreFeaturesPopupWindow extends PopupWindow implements View.OnClickListener {

    private FragmentShouYe  fragmentShouYe;
    private MedicationSettingsActivity mMedicationSettingsActivity;
    private MedicationRecordActivity mMedicationRecordActivity;
    private Context mContext;
    private View mPopView;
    private TextView tvSys;
    private TextView tvYqth;
     private LinearLayout       tv_bzfk;
    private TextView tvTjhz;
    private LinearLayout tvWdzs;
    private LinearLayout tvWdxx;
    private TextView tvCjlm;
    private TextView tvFqhz;
    private TextView tvWdbb;
    private TextView tvBzfk;

    public MoreFeaturesPopupWindow(Activity context) {
        super(context);
        mContext = context;
        init(context);

        setPopupWindow(context);
        tvSys.setOnClickListener(this);
        tvYqth.setOnClickListener(this);
        tvTjhz.setOnClickListener(this);
        tvWdzs.setOnClickListener(this);
        tvWdxx.setOnClickListener(this);
        tv_bzfk.setOnClickListener(this);
//        tvCjlm.setOnClickListener(this);

    }

    private void init(Activity context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.popup_window_more_feature, null);
        tvSys = mPopView.findViewById(R.id.tv_sys);
        tvYqth = mPopView.findViewById(R.id.tv_yqth);
        tvTjhz = mPopView.findViewById(R.id.tv_tjhz);
        tvWdzs = mPopView.findViewById(R.id.tv_wdzs);
        tvWdxx = mPopView.findViewById(R.id.tv_wdxx);
        tv_bzfk = mPopView.findViewById(R.id.tv_bzfk);

    }

    /**
     * 设置窗口的相关属性
     */
    private void setPopupWindow(Activity activity) {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setOutsideTouchable(true);

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        activity.getWindow().setAttributes(lp);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sys:
                Intent intent = new Intent(mContext, CaptureActivity.class);
                if (fragmentShouYe != null)
                    fragmentShouYe.startActivityForResult(intent,fragmentShouYe.REQUEST_CODE_SCAN);
                if (mMedicationSettingsActivity != null)
                    mMedicationSettingsActivity.startActivityForResult(intent,fragmentShouYe.REQUEST_CODE_SCAN);
                if (mMedicationRecordActivity != null)
                    mMedicationRecordActivity.startActivityForResult(intent,fragmentShouYe.REQUEST_CODE_SCAN);
                break;
            case R.id.tv_yqth:
                intent = new Intent(mContext, TJZJActivity.class);
                if (fragmentShouYe != null)
                    fragmentShouYe.startActivity(intent);
                if (mMedicationSettingsActivity != null)
                    mMedicationSettingsActivity.startActivity(intent);
                if (mMedicationRecordActivity != null)
                    mMedicationRecordActivity.startActivity(intent);
                break;
            case R.id.tv_tjhz:
                intent = new Intent(mContext, WDYSActivity.class);
                if (fragmentShouYe != null)
                    fragmentShouYe.startActivity(intent);
                if (mMedicationSettingsActivity != null)
                    mMedicationSettingsActivity.startActivity(intent);
                if (mMedicationRecordActivity != null)
                    mMedicationRecordActivity.startActivity(intent);
                break;
            case R.id.tv_wdzs:
                intent = new Intent(mContext, MyOrderActivity.class);
                if (fragmentShouYe != null)
                    fragmentShouYe.startActivity(intent);
                if (mMedicationSettingsActivity != null)
                    mMedicationSettingsActivity.startActivity(intent);
                if (mMedicationRecordActivity != null)
                    mMedicationRecordActivity.startActivity(intent);
                break;
            case R.id.tv_wdxx:
                intent = new Intent(mContext, JDDAActivity.class);
                if (fragmentShouYe != null)
                    fragmentShouYe.startActivity(intent);
                if (mMedicationSettingsActivity != null)
                    mMedicationSettingsActivity.startActivity(intent);
                if (mMedicationRecordActivity != null)
                    mMedicationRecordActivity.startActivity(intent);
                break;
//            case R.id.tv_cjlm:
//                mContext.startActivity(new Intent(mContext,DoctorsUnionActivity.class));
//                break;
            case  R.id.tv_bzfk:
                intent = new Intent(mContext, QRCodeActivity.class);
                if (fragmentShouYe != null)
                    fragmentShouYe.startActivity(intent);
                if (mMedicationSettingsActivity != null)
                    mMedicationSettingsActivity.startActivity(intent);
                if (mMedicationRecordActivity != null)
                    mMedicationRecordActivity.startActivity(intent);
                break;
        }
    }

    /**
     * 设置入口为首页
     * @param mFragment
     */
    public void setSouYeFragment(FragmentShouYe mFragment) {
        fragmentShouYe = mFragment;
    }



    /**
     * 设置入口为用药设置
     * @param mActivity
     */
    public void setMedicationSettingsActivity(MedicationSettingsActivity mActivity) {
        mMedicationSettingsActivity = mActivity;
    }

    /**
     * 设置入口为用药打卡
     * @param medicationRecordActivity
     */
    public void setMedicationRecordActivity(MedicationRecordActivity medicationRecordActivity) {
        mMedicationRecordActivity = medicationRecordActivity;
    }
}
