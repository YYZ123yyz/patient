package www.patient.jykj_zxyl.myappointment.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.hyphenate.easeui.utils.ActivityUtil;

import www.patient.jykj_zxyl.R;

/*
* 全部患者
* */
public class AllDepartmentsActivity extends AppCompatActivity implements View.OnClickListener {
private AllDepartmentsActivity mActivity;
    private RelativeLayout ri_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=AllDepartmentsActivity.this;
        setContentView(R.layout.activity_all_departments);
        ActivityUtil.setStatusBarMain(mActivity);

        initView();
    }

    private void initView() {
        ri_back = findViewById(R.id.ri_back);
        ri_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ri_back:
                finish();
                break;
        }
    }
}