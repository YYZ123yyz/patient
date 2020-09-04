package www.patient.jykj_zxyl.myappointment.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.ActivityUtil;

/*
* 病历
*
* Drug_Rv_Adapter
*
* */
public class MedicalRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setStatusBarMain(this);
        setContentView(R.layout.activity_medical_record);
    }
}