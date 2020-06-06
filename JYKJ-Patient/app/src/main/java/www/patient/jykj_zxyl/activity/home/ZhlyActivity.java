package www.patient.jykj_zxyl.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import www.patient.jykj_zxyl.adapter.ZhlyAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.ZhlyAdapter;

/**
 * 诊后留言
 */
public class ZhlyActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private RecyclerView mRecyclerView;
    private ZhlyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhly);
        initView();
        initListener();
    }

    private void initView(){
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        mRecyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ZhlyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener(){
        llBack.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new ZhlyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
            startActivity(new Intent(ZhlyActivity.this,ZhlyReplyActivity.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
