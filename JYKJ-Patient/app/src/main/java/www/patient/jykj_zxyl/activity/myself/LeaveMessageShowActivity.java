package www.patient.jykj_zxyl.activity.myself;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import entity.mySelf.MyOrderProcess;
import entity.mySelf.ZhlyDetailInfo;
import entity.mySelf.ZhlyImgInfo;
import entity.mySelf.ZhlyReplyInfo;
import indi.liyi.viewer.ImageViewer;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.myself.ZhlyShowAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;

import java.util.ArrayList;
import java.util.List;

public class LeaveMessageShowActivity extends AppCompatActivity {
    private Context mContext;
    private JYKJApplication mApp;
    private LeaveMessageShowActivity mActivity;
    private RecyclerView mRecycleView;
    private MyOrderProcess paroder;
    private List<ZhlyDetailInfo> mDatas = new ArrayList();
    private List<ZhlyReplyInfo> mReplies = new ArrayList();
    private List<ZhlyImgInfo> mImgs = new ArrayList();
    private ImageViewer imageViewer;
    private             LinearLayoutManager                 layoutManager;
    private ZhlyShowAdapter zhlyShowAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mApp = (JYKJApplication)getApplication();
        mActivity = LeaveMessageShowActivity.this;
        setContentView(R.layout.activity_zhly_show);
        paroder = (MyOrderProcess)getIntent().getSerializableExtra("orderInfo");
        initLayout();
    }

    private void initLayout()  {
        imageViewer = findViewById(R.id.imageViewer);
        mRecycleView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        zhlyShowAdapter = new ZhlyShowAdapter(LeaveMessageShowActivity.this,mDatas,mImgs,mReplies,imageViewer);
        mRecycleView.setAdapter(zhlyShowAdapter);
        loadData();
    }

    void loadData(){

    }
}

