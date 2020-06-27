package www.patient.jykj_zxyl.activity.myself;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import entity.mySelf.MyOrderProcess;
import entity.mySelf.ZhlyDetailInfo;
import entity.mySelf.ZhlyImgInfo;
import entity.mySelf.conditions.QueryZhlyImgCond;
import indi.liyi.viewer.ImageViewer;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.myself.ZhlyShowAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.INetAddress;
import www.patient.jykj_zxyl.util.StrUtils;

import java.util.ArrayList;
import java.util.List;

public class LeaveMessageShowActivity extends AppCompatActivity {
    private Context mContext;
    private JYKJApplication mApp;
    private LeaveMessageShowActivity mActivity;
    private RecyclerView mRecycleView;
    private MyOrderProcess paroder;
    private List<ZhlyDetailInfo> mDatas = new ArrayList();
    private List<ZhlyImgInfo> mImgs = new ArrayList();
    private ImageViewer imageViewer;
    private             LinearLayoutManager                 layoutManager;
    private ZhlyShowAdapter zhlyShowAdapter;
    private ZhlyDetailInfo paramDetailInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paroder = (MyOrderProcess)getIntent().getSerializableExtra("orderInfo");
        paramDetailInfo = (ZhlyDetailInfo)getIntent().getSerializableExtra("zhlyinfo");
        mContext = getApplicationContext();
        mApp = (JYKJApplication)getApplication();
        mActivity = LeaveMessageShowActivity.this;
        setContentView(R.layout.activity_zhly_show);
        initLayout();
    }

    private void initLayout()  {
        imageViewer = findViewById(R.id.imageViewer);
        mRecycleView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        zhlyShowAdapter = new ZhlyShowAdapter(LeaveMessageShowActivity.this,mDatas,mImgs,imageViewer);
        mRecycleView.setAdapter(zhlyShowAdapter);
        loadData();
    }

    void loadData(){
        QueryZhlyImgCond imgcond = new QueryZhlyImgCond();
        imgcond.setImgCode(paramDetailInfo.getImgCode());
        imgcond.setLoginPatientPosition(mApp.loginDoctorPosition);
        imgcond.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        imgcond.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        imgcond.setOrderCode(paroder.getOrderCode());
        imgcond.setRequestClientType("1");
        LoadImgTask imgTask = new LoadImgTask(imgcond);
        imgTask.execute();
    }

    class LoadImgTask extends AsyncTask<Void,Void,List<ZhlyImgInfo>> {
        QueryZhlyImgCond queryCond;
        LoadImgTask(QueryZhlyImgCond queryCond){
            this.queryCond = queryCond;
        }
        @Override
        protected List<ZhlyImgInfo> doInBackground(Void... voids) {
            List<ZhlyImgInfo> retimgs = new ArrayList();
            try{
                String retstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryCond), Constant.SERVICEURL+ INetAddress.QUERY_ZHLY_IMG_INFO);
                NetRetEntity retEntity = JSON.parseObject(retstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retimgs = JSON.parseArray(retstr,ZhlyImgInfo.class);
                }
            }catch (Exception ex){

            }
            return retimgs;
        }

        @Override
        protected void onPostExecute(List<ZhlyImgInfo> zhlyImgInfos) {
            if(zhlyImgInfos.size()>0){
                for(int i=0;i<zhlyImgInfos.size();i++){
                    mImgs.add(zhlyImgInfos.get(i));
                }
            }
            mDatas.add(paramDetailInfo);
            zhlyShowAdapter.setDatas(mDatas,mImgs);
            zhlyShowAdapter.notifyDataSetChanged();
        }
    }

}

