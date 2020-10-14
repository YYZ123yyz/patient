package www.patient.jykj_zxyl.activity.myself;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_activity.BaseActivity;

/**
 * Created by G on 2020/9/17 15:26
 */
public class AddEquipmentActivity extends BaseActivity implements View.OnClickListener {

    private TextView tittle;

    private RelativeLayout back;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_equipment;
    }

    @Override
    protected void initView() {

        tittle =findViewById(R.id.tittle);
        back =findViewById(R.id.ri_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bind:

                break;
            case R.id.ri_back:
                finish();
                break;
        }
    }
}

