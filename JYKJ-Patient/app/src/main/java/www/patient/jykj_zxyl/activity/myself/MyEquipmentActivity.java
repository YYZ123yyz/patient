package www.patient.jykj_zxyl.activity.myself;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_activity.BaseActivity;

/**
 * Created by G on 2020/9/17 14:58
 */
public class MyEquipmentActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout emptyRl;
    private TextView tittle;
    private RecyclerView equipmentRecycle;
    private TextView bindTv;
    private RelativeLayout back;
    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_equipment;
    }

    @Override
    protected void initView() {
        emptyRl =  findViewById(R.id.empty_rl);
        tittle =findViewById(R.id.tittle);
        equipmentRecycle =findViewById(R.id.equipment_list);
        equipmentRecycle.setLayoutManager(new LinearLayoutManager(this));
        bindTv =findViewById(R.id.tv_bind);
        bindTv.setOnClickListener(this);
        back =findViewById(R.id.ri_back);
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
