package www.patient.jykj_zxyl.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import www.patient.jykj_zxyl.adapter.BloodRecordAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.BloodRecordAdapter;

/**
 * 血压记录
 */
public class BloodRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecycleView;

    private LinearLayoutManager layoutManager;
    private BloodRecordAdapter bloodRecordAdapter;       //适配器
    private LinearLayout llBack;
    private LinearLayout llLuru,llQushi,llJilu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_record);
        initView();
        initListener();
    }

    private void initView(){
        mRecycleView = (RecyclerView) this.findViewById(R.id.recycler_view);
        llBack = findViewById(R.id.ll_back);
        llLuru = findViewById(R.id.ll_luru);
        llQushi = findViewById(R.id.ll_qushi);
        llJilu = findViewById(R.id.ll_jilu);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
//        bloodRecordAdapter = new BloodRecordAdapter();
//        mRecycleView.setAdapter(bloodRecordAdapter);

    }

    private void initListener(){
        llBack.setOnClickListener(this);
        llLuru.setOnClickListener(this);
        llQushi.setOnClickListener(this);
        llJilu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_luru:

                break;
            case R.id.ll_qushi:
                startActivity(new Intent(BloodRecordActivity.this,TrendActivity.class));
                break;
            case R.id.ll_jilu:

                break;
        }
    }
}
