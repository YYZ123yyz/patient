package www.patient.jykj_zxyl.activity.myself.setting;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import entity.mySelf.VersionInfo;
import entity.mySelf.conditions.QueryVersionCond;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.myself.VersionAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.IConstant;
import www.patient.jykj_zxyl.util.INetAddress;
import www.patient.jykj_zxyl.util.StrUtils;

import java.util.ArrayList;
import java.util.List;

public class UpdateVersionShowActivity extends AppCompatActivity {
    private Context mContext;
    private JYKJApplication mApp;
    private int mPageNum = 1;
    private List<VersionInfo> mDatas = new ArrayList();
    RecyclerView version_data_holder;
    private VersionAdapter mVersionAdapter;
    LoadDataTask  loadDataTask;
    boolean mLoadDate = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp=(JYKJApplication)getApplication();
        mContext = UpdateVersionShowActivity.this;
        setContentView(R.layout.activity_update_version_show);
        version_data_holder = findViewById(R.id.version_data_holder);
        findViewById(R.id.back).setOnClickListener(new ButtonClick());
        int viewWidth = getWindowManager().getDefaultDisplay().getWidth() - dp2px(this, 20f);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        version_data_holder.setLayoutManager(manager);
        mVersionAdapter = new VersionAdapter(mDatas,viewWidth);
        version_data_holder.setAdapter(mVersionAdapter);
        version_data_holder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = manager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= manager.getItemCount() - 1) {
                            mPageNum++;
                            setData();
                        }
                    }
                }
            }
        });
        setData();
    }

    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
            }
        }
    }

    void setData(){
        QueryVersionCond querybean = new QueryVersionCond();
        querybean.setPageNum(String.valueOf(mPageNum));
        querybean.setLoginPatientPosition(mApp.loginDoctorPosition);
        querybean.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        querybean.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        querybean.setRequestClientType("1");
        querybean.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
        loadDataTask = new LoadDataTask(querybean);
        loadDataTask.execute();
    }

    class LoadDataTask extends AsyncTask<Void,Void, List<VersionInfo>>{
        QueryVersionCond queryVersionCond;
        LoadDataTask(QueryVersionCond queryVersionCond){
            this.queryVersionCond = queryVersionCond;
        }

        @Override
        protected List<VersionInfo> doInBackground(Void... voids) {
            mLoadDate = false;
            List<VersionInfo> retlist = new ArrayList();
            queryVersionCond.setPageNum(String.valueOf(mPageNum));
            try {
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(queryVersionCond), Constant.SERVICEURL+ INetAddress.QUERY_VERSION_URL);
                NetRetEntity retEntity = JSON.parseObject(retnetstr,NetRetEntity.class);
                if(1==retEntity.getResCode() && StrUtils.defaultStr(retEntity.getResJsonData()).length()>3){
                    retlist = JSON.parseArray(retEntity.getResJsonData(),VersionInfo.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return retlist;
        }

        @Override
        protected void onPostExecute(List<VersionInfo> versionInfos) {
            if(versionInfos.size()>0){
                mDatas.addAll(versionInfos);
                mVersionAdapter.setData(mDatas);
                mVersionAdapter.notifyDataSetChanged();
            }else{
                if(mPageNum>1){
                    mPageNum = mPageNum - 1;
                }
            }
            mLoadDate = true;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        int res = 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        if (dpValue < 0)
            res = -(int) (-dpValue * scale + 0.5f);
        else
            res = (int) (dpValue * scale + 0.5f);
        return res;
    }
}
