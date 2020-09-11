package www.patient.jykj_zxyl.myappointment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.ActivityUtil;

/**
 * Created by G on 2020/9/11 16:36
 */
public class CheckListActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.setStatusBarMain(this);
        setContentView(R.layout.activity_checklist);
        ButterKnife.bind(this);

    }
}
