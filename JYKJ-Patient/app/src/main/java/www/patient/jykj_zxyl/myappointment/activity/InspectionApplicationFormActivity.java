package www.patient.jykj_zxyl.myappointment.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.ActivityUtil;
/*
* 检验申请单
* */


public class InspectionApplicationFormActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setStatusBarMain(this);
        setContentView(R.layout.activity_inspection_application_form);
    }
}