package www.patient.jykj_zxyl.activity.myself;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.OperUpdPatientConditionTakingMedicineStateParement;
import entity.ProvidePatientConditionTakingMedicine;
import entity.ProvidePatientConditionTakingRecordGroup;
import entity.shouye.ProvidePatientConditionTakingRecord;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.MedicationRecordAdapter;
import www.patient.jykj_zxyl.custom.MoreFeaturesPopupWindow;
import www.patient.jykj_zxyl.library.PullToRefreshListView;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.MedicationRecordAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.library.PullToRefreshListView;

/**
 * 服药打卡
 */
public class MedicationRecordActivity extends Activity {

    public ProgressDialog mDialogProgress = null;
    private String mNetRetStr;                 //获取返回字符串

    private JYKJApplication mApp;

    private int mRowNum = 10;                    //
    private int mPageNum = 1;
    private boolean mLoadDate = true;

    private Handler mHandler;

    Context context;
    public long lasttime = 0;
    private PullToRefreshListView ms_pull_refresh_list;
    private List<Map<String, Object>> list;
    private SimpleAdapter adapter;
    private List<ProvidePatientConditionTakingMedicine> providePatientConditionTakingMedicines = new ArrayList<>();
    private List<ProvidePatientConditionTakingRecordGroup> providePatientConditionTakingRecordGroups = new ArrayList<>();
    private RecyclerView mRecycleView;
    private MedicationRecordAdapter mAdapter;
    private MedicationRecordActivity medicationRecordActivity;

    private MoreFeaturesPopupWindow mPopupWindow;

    ImageView iv_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = this;
        mApp = (JYKJApplication) getApplication();
        setContentView(R.layout.medication_record);
        medicationRecordActivity = this;
        initHandler();
        //获取返回按钮
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        //获取添加按钮
        iv_add = (ImageView) findViewById(R.id.iv_add);
        //获取服药打卡按钮
        LinearLayout ms_punch = (LinearLayout) findViewById(R.id.ms_punch);
        //获取服药统计按钮
        LinearLayout ms_statistics = (LinearLayout) findViewById(R.id.ms_statistics);
        //获取界面列表
//		ms_pull_refresh_list = (RE)findViewById(R.id.ms_pull_refresh_list);
        //给返回按钮添加点击事件
        iv_back.setOnClickListener(new OnclickListener());
        //给添加按钮添加点击事件
        iv_add.setOnClickListener(new OnclickListener());
        //给服药打卡按钮添加点击事件
        ms_punch.setOnClickListener(new OnclickListener());
        //给服药统计按钮添加点击事件
        ms_statistics.setOnClickListener(new OnclickListener());

        mRecycleView = (RecyclerView) this.findViewById(R.id.ms_pull_refresh_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(manager);
        mAdapter = new MedicationRecordAdapter(providePatientConditionTakingRecordGroups, this, medicationRecordActivity);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = manager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= manager.getItemCount() - 1) {
                            mPageNum++;
                            getDate();
                        }
                    }
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getDate();
    }

    /**
     *
     */
    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 2:
                        cacerProgress();
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0) {
//                            Toast.makeText(MedicationRecordActivity.this,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            mLoadDate = false;
                        } else {
                            List<ProvidePatientConditionTakingMedicine> list = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionTakingMedicine.class);
                            if (list == null || list.size() == 0)
                                mLoadDate = false;
                            else if (list.size() < mRowNum) {
                                mLoadDate = false;
                                providePatientConditionTakingRecordGroups = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionTakingRecordGroup.class);
                                mAdapter.setDate(providePatientConditionTakingRecordGroups);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mLoadDate = true;
                                providePatientConditionTakingRecordGroups = JSON.parseArray(netRetEntity.getResJsonData(), ProvidePatientConditionTakingRecordGroup.class);
                                mAdapter.setDate(providePatientConditionTakingRecordGroups);
                                mAdapter.notifyDataSetChanged();
                            }

                        }
                        break;
                    case 5:
                        cacerProgress();
                        getDate();
                        break;
                }
            }
        };
    }

    /**
     * 获取服药数据
     */
    private void getDate() {
        ProvidePatientConditionTakingRecordGroup providePatientConditionTakingRecordGroup = new ProvidePatientConditionTakingRecordGroup();
        providePatientConditionTakingRecordGroup.setRowNum(mRowNum + "");
        providePatientConditionTakingRecordGroup.setPageNum(mPageNum + "");
        providePatientConditionTakingRecordGroup.setLoginPatientPosition(mApp.loginDoctorPosition);
        providePatientConditionTakingRecordGroup.setRequestClientType("1");
        providePatientConditionTakingRecordGroup.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        providePatientConditionTakingRecordGroup.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        providePatientConditionTakingRecordGroup.setSearchTakingMedicine("-1");
        getProgressBar("请稍候", "正在获取数据");
        //连接网络，登录
        new Thread() {
            public void run() {
                try {
                    String str = new Gson().toJson(providePatientConditionTakingRecordGroup);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(providePatientConditionTakingRecordGroup), Constant.SERVICEURL + "PatientConditionControlle/searchPatientStateResTakingMedicineRecord");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }

    /**
     * 点击已服用
     *
     * @param providePatientConditionTakingRecordGroup
     */
    public void yfy(ProvidePatientConditionTakingRecord providePatientConditionTakingRecordGroup) {
        getProgressBar("请稍候", "正在处理");
        OperUpdPatientConditionTakingMedicineStateParement operUpdPatientConditionTakingMedicineStateParement = new OperUpdPatientConditionTakingMedicineStateParement();
        operUpdPatientConditionTakingMedicineStateParement.setLoginPatientPosition(mApp.loginDoctorPosition);
        operUpdPatientConditionTakingMedicineStateParement.setRequestClientType("1");
        operUpdPatientConditionTakingMedicineStateParement.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operUpdPatientConditionTakingMedicineStateParement.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        operUpdPatientConditionTakingMedicineStateParement.setTakingRecordId(providePatientConditionTakingRecordGroup.getTakingRecordId() + "");
        operUpdPatientConditionTakingMedicineStateParement.setPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operUpdPatientConditionTakingMedicineStateParement.setFlagTakingMedicine("3");

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(operUpdPatientConditionTakingMedicineStateParement);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.patient.jykj_zxyl.application.Constant.SERVICEURL + "PatientConditionControlle/operUpdPatientConditionTakingMedicineState");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(5);
            }
        }.start();
    }

    /**
     * 点击未服用
     *
     * @param providePatientConditionTakingRecordGroup
     */
    public void wfy(ProvidePatientConditionTakingRecord providePatientConditionTakingRecordGroup) {
        getProgressBar("请稍候", "正在处理");
        OperUpdPatientConditionTakingMedicineStateParement operUpdPatientConditionTakingMedicineStateParement = new OperUpdPatientConditionTakingMedicineStateParement();
        operUpdPatientConditionTakingMedicineStateParement.setLoginPatientPosition(mApp.loginDoctorPosition);
        operUpdPatientConditionTakingMedicineStateParement.setRequestClientType("1");
        operUpdPatientConditionTakingMedicineStateParement.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operUpdPatientConditionTakingMedicineStateParement.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        operUpdPatientConditionTakingMedicineStateParement.setTakingRecordId(providePatientConditionTakingRecordGroup.getTakingRecordId() + "");
        operUpdPatientConditionTakingMedicineStateParement.setPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        operUpdPatientConditionTakingMedicineStateParement.setFlagTakingMedicine("1");

        new Thread() {
            public void run() {
                try {
                    String string = new Gson().toJson(operUpdPatientConditionTakingMedicineStateParement);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + string, www.patient.jykj_zxyl.application.Constant.SERVICEURL + "PatientConditionControlle/operUpdPatientConditionTakingMedicineState");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(5);
            }
        }.start();
    }

    /**
     * 返回按钮
     * 添加按钮
     * 服药打卡按钮
     * 服药统计按钮
     * 点击事件
     *
     * @author BigBoyTian
     */
    class OnclickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (isFastDoubleClick()) {
                Toast.makeText(context, "请不要频繁点击按钮", Toast.LENGTH_SHORT).show();
                return;
            } else {
                int id = v.getId();
                Intent intent;
                switch (id) {
                    case R.id.iv_back:
                        //MedicationSettingsActivity.class 替换成需要跳转的界面
                        finish();
                        break;
                    case R.id.iv_add:
                        mPopupWindow = new MoreFeaturesPopupWindow(MedicationRecordActivity.this);
                        mPopupWindow.setMedicationRecordActivity(MedicationRecordActivity.this);
                        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
                            mPopupWindow.showAsDropDown(iv_add, 0, 0);
                        }
                        break;
                    case R.id.ms_punch:
                        //MedicationSettingsActivity.class 替换成需要跳转的界面
                        intent = new Intent(context, MedicationSettingsActivity.class);
                        //传到下一个界面的参数
                        String[] punch = new String[]{"n1"};
                        intent.putExtra("list", punch);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.ms_statistics:
                        //MedicationSettingsActivity.class 替换成需要跳转的界面
                        intent = new Intent(context, MedicationActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 下拉列表刷新及数据加载
     *
     * @author BigBoyTian
     */
    private class GetDataTask extends AsyncTask<String, Void, String[]> {
        // 后台处理部分
        @Override
        protected String[] doInBackground(String... params) {
            // Simulates a background job.
            //网络请求结果（结果为JSON格式的字符串）
            return new String[]{""};
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void onPostExecute(String[] rs) {
            //字符串转换为JSON格式数据
            JSONObject json = JSONObject.parseObject(rs[0]);
            //获取json数据内的List<Map<String, Object>>
//			if (json.get("datalist") == null)
//				return;
//			list = (List<Map<String, Object>>) json.get("datalist");
//			adapter = new SimpleAdapter(context, list, R.layout.ms_list,
//					new String[]{"ms_name","ms_rate","ms_time1","ms_time2","ms_time3"},
//					new int[]{R.id.ms_name,R.id.ms_rate,R.id.ms_time1,R.id.ms_time2,R.id.ms_time3});
//			ListView actualListView = ms_pull_refresh_list.getRefreshableView();
//			adapter.notifyDataSetChanged();
//			ms_pull_refresh_list.onRefreshComplete();
//			actualListView.setAdapter(adapter);
            //这句是必有的，AsyncTask规定的格式
            super.onPostExecute(rs);
        }
    }

    /**
     * 列表子选项的点击事件
     *
     * @author BigBoyTian
     */
    class ItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            // TODO Auto-generated method stub
            if (isFastDoubleClick()) {
                Toast.makeText(context, "请不要频繁点击按钮", Toast.LENGTH_SHORT).show();
                return;
            } else {
//				Intent intent = new Intent(context,StudentRate.class);
//				startActivity(intent);
//				finish();
            }
        }
    }

    /**
     * 防止频繁按下按钮
     *
     * @param
     * @return
     */
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long seconds = time - lasttime;
        if (seconds > 0 && seconds < 500)
            return true;
        lasttime = time;
        return false;
    }

    /**
     * 获取进度条
     */

    public void getProgressBar(String title, String progressPrompt) {
        if (mDialogProgress == null) {
            mDialogProgress = new ProgressDialog(this);
        }
        mDialogProgress.setTitle(title);
        mDialogProgress.setMessage(progressPrompt);
        mDialogProgress.setCancelable(false);
        mDialogProgress.show();
    }

    /**
     * 取消进度条
     */
    public void cacerProgress() {
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }

}
