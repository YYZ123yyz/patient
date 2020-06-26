package www.patient.jykj_zxyl.activity.home.patient;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.basicDate.ProvideBasicsDomain;
import entity.basicDate.ProvideBasicsRegion;
import entity.basicDate.ProvideHospitalDepartment;
import entity.basicDate.ProvideHospitalInfo;
import entity.basicDate.ProvideViewUnionDoctorDetailInfo;
import entity.shouye.ProvideViewDoctorExpertRecommend;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.adapter.JGBJtAdapter;
import www.patient.jykj_zxyl.adapter.YSZCtAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeTJZJAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.adapter.JGBJtAdapter;
import www.patient.jykj_zxyl.adapter.YSZCtAdapter;
import www.patient.jykj_zxyl.adapter.patient.fragmentShouYe.FragmentHomeTJZJAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.FullyGridLayoutManager;
import www.patient.jykj_zxyl.util.ProvincePicker;


/**
 * 推荐专家 ==》更多
 */

public class TJZJActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FragmentHomeTJZJAdapter mAdapter;
    private LinearLayout llBack;

    public          ProgressDialog              mDialogProgress =null;

    private         Context                     mContext;                                       //
    private         TJZJActivity                mActivity;
    private         JYKJApplication             mApp;

    private         String                      mNetRetStr;                 //返回字符串
    private         String                      mNetJGBJRetStr;                 //返回字符串
    private         String                      mNetYSZCRetStr;                 //返回字符串

    private         Handler                     mHandler;

    private         LinearLayout                mChoiceRegion;              //选择区域
    private         TextView                    mChoiceRegionText;                  //选择的地区

    private         LinearLayout                mChoiceHospital;                //选择医院
    private         TextView                    mChoiceHospitalText;                  //选择的地区

    private         LinearLayout                mChoiceDepartmentFLayout;           //选择一级科室布局
    private         TextView                    mChoiceDepartmentFText;             //选择一级科室显示
    private         LinearLayout                mChoiceDepartmentSLayout;           //选择二级科室布局
    private         TextView                    mChoiceDepartmentSText;             //选择二级科室显示

    private         EditText                    mSearchEditText;                        //输入框

    private         ProvincePicker              mPicker;                                            //省市县三级联动选择框
    public          Map<String,ProvideBasicsRegion> mChoiceRegionMap = new HashMap<>();                  //用户选择的省市区map
    private                 int                           mChoiceRegionLevel;                                       //选择的区域级别
    private                 String                      mChoiceRegionID;                                       //选择的区域ID

    private                 List<ProvideHospitalInfo>   mProvideHospitalInfos = new ArrayList<>();              //获取到的医院列表
    private                 String[]                    mProvideHospitalNameInfos;                              //医院对应的名称列表

    private                 int                             mChoiceHospitalIndex;           //选择的医院下标


    private                 TextView                    mSearchTextView;                    //搜索

    private                 int                         mNumPage = 1;                  //页数（默认，1）
    private                 int                         mRowNum = 10;                  //每页加载10条
    private                 int                         mSearchModel;                   //搜索模式  1=按条件搜索  其他=扫码搜索
    private                 List<ProvideViewUnionDoctorDetailInfo>          mUnionList = new ArrayList<>();

    private                 boolean                     loadDate = true;               //是否加载数据

    private             List<ProvideViewDoctorExpertRecommend> provideViewDoctorExpertRecommendList = new ArrayList<>();            //获取到的专家数据

    private                 ProvideViewDoctorExpertRecommend provideViewDoctorExpertRecommend;

    private                 List<ProvideHospitalDepartment>   mProvideHospitalDepartmentFInfos = new ArrayList<>();              //获取到的医院一级科室列表
    private                 String[]                        mProvideHospitalDepartmentFNameInfos;                              //医院一级科室对应的名称列表

    private                 List<ProvideHospitalDepartment>   mProvideHospitalDepartmentSInfos = new ArrayList<>();              //获取到的医院二级科室列表
    private                 String[]                    mProvideHospitalDepartmentSNameInfos;                              //医院二级科室对应的名称列表

    private DrawerLayout mDrawerLayout = null;

    private                 ImageView                       drawerLayout;                   //抽屉按钮

    private                 RecyclerView                        mJGBJRecycleView;                       //机构背景
    private                 RecyclerView                        mYSZCRecycleView;                       //医生职称
    private                 FullyGridLayoutManager              mGridLayoutManager;

    private                 List<ProvideBasicsDomain>           mProvideBasicsDominJGBJ = new ArrayList<>();
    private                 List<ProvideBasicsDomain>           mProvideBasicsDominYSZC = new ArrayList<>();

    private JGBJtAdapter mJGBJtAdapter;
    private YSZCtAdapter mYSZCtAdapter;

    private                 TextView                            tv_cz;                  //重置
    private                 TextView                            tv_qd;                  //确定

    private                 EditText                            tv_zdj,tv_zgj;          //最低价、最高价

    private                 LinearLayout                        choiceRegion;           //选择区域







    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_tjzj);
        mContext = this;
        mActivity = this;
        mApp = (JYKJApplication) getApplication();
        provideViewDoctorExpertRecommend = new ProvideViewDoctorExpertRecommend();
        mApp.gPayCloseActivity.add(mActivity);
        initView();
        initHandler();
        //获取所有联盟
        mNumPage = 1;
        mSearchModel = 1;
        getBasicDate(50001);
        getBasicDate(50003);
        Map<Integer,List<ProvideBasicsDomain>> gBasicDate = mApp.gBasicDate;
        //通过定位地区编码获取系统定位编码
        searchBasicsRegionByRegionCode();
//        getDate();

    }

    /**
     * 根据[行政地区编号]获取[地区主键编号]
     */
    private void searchBasicsRegionByRegionCode() {
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    ProvideBasicsRegion provideBasicsRegion = new ProvideBasicsRegion();
                    provideBasicsRegion.setLoginPatientPosition(mApp.loginDoctorPosition);
                    provideBasicsRegion.setRequestClientType("1");
                    provideBasicsRegion.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
                    provideBasicsRegion.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
                    provideBasicsRegion.setRegionCode(mApp.gGDLocation);
                    String str = new Gson().toJson(provideBasicsRegion);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "patientDataControlle/searchBasicsRegionByRegionCode");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                }
                mHandler.sendEmptyMessage(50);
            }
        }.start();
    }

    /**
     * 获取机构背景
     */
    private void getBasicDate(int basicCode) {
        new Thread() {
            public void run() {
//                //提交数据
                try {
                    ProvideBasicsDomain provideBasicsDomain = new ProvideBasicsDomain();
                    provideBasicsDomain.setBaseCode(basicCode);
                    String str = new Gson().toJson(provideBasicsDomain);
                    if (basicCode == 50001) {
                        mNetJGBJRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "basicDataController/getBasicsDomain");
                        mHandler.sendEmptyMessage(10);
                    }
                    if (basicCode == 50003)
                    {
                        mNetYSZCRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + str, Constant.SERVICEURL + "basicDataController/getBasicsDomain");
                        mHandler.sendEmptyMessage(11);
                    }



                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    return;
                }
            }
        }.start();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 5:
                        if (mNetRetStr == null || "".equals(mNetRetStr))
                        {
                            cacerProgress();
                            Toast.makeText(mContext,"网络故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        NetRetEntity netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                            loadDate = false;
//                            Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            List<ProvideViewDoctorExpertRecommend> list = JSON.parseArray(netRetEntity.getResJsonData(),ProvideViewDoctorExpertRecommend.class);
                            if (list != null)
                                provideViewDoctorExpertRecommendList.addAll(list);
                            if (list == null)
                                Toast.makeText(mContext,netRetEntity.getResMsg(),Toast.LENGTH_SHORT).show();
                            if (list == null || list.size() < mRowNum)
                                loadDate = false;
                        }

                        showDoctorExpertRecommend();
                        break;
                    case 0:
                        cacerProgress();
                        provideViewDoctorExpertRecommendList.clear();
                        provideViewDoctorExpertRecommend.setSearchDoctorName("");
                        mSearchEditText.setText("");
                        getDate();
//                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
//                        if (retEntity.getResCode() == 0)
//                        {
//                            Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        mAdapter.setDate(provideViewDoctorExpertRecommendList);
//                        mAdapter.notifyDataSetChanged();
                        break;
                    case 2:
//                        cacerProgress();
//                        retEntity = new Gson().fromJson(mNetRetStr,NetRetEntity.class);
//                        Toast.makeText(mContext,retEntity.getResMsg(),Toast.LENGTH_SHORT).show();
//                        mAdapter.setDate(provideViewDoctorExpertRecommendList);
//                        mAdapter.notifyDataSetChanged();
                        break;
                    case 10:
                        netRetEntity = new Gson().fromJson(mNetJGBJRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                           mProvideBasicsDominJGBJ = JSON.parseArray(netRetEntity.getResJsonData(),ProvideBasicsDomain.class);
                           mJGBJtAdapter.setDate(mProvideBasicsDominJGBJ);
                        }
                        break;
                    case 11:
                        netRetEntity = new Gson().fromJson(mNetYSZCRetStr, NetRetEntity.class);
                        if (netRetEntity.getResCode() == 1) {
                            mProvideBasicsDominYSZC = JSON.parseArray(netRetEntity.getResJsonData(),ProvideBasicsDomain.class);
                            mYSZCtAdapter.setDate(mProvideBasicsDominYSZC);
                        }
                        break;

                    case 50:
                        if (mNetRetStr == null || "".equals(mNetRetStr))
                        {
                            cacerProgress();
                            Toast.makeText(mContext,"网络故障",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        netRetEntity = JSON.parseObject(mNetRetStr,NetRetEntity.class);
                        if (netRetEntity.getResCode() == 0)
                        {
                           Toast.makeText(mContext,"定位失败，请手动选择区域",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            ProvideBasicsRegion provideBasicsRegion = JSON.parseObject(netRetEntity.getResJsonData(),ProvideBasicsRegion.class);
//                            if(provideBasicsRegion.getRegion_level() == 1)
//                                provideViewDoctorExpertRecommend.setSearchProvince(provideBasicsRegion.getRegion_id());
//                            if(provideBasicsRegion.getRegion_level() == 2)
                            provideViewDoctorExpertRecommend.setSearchArea(provideBasicsRegion.getRegion_id());
//                            if(provideBasicsRegion.getRegion_level() == 3)
//                                provideViewDoctorExpertRecommend.setSearchArea(provideBasicsRegion.getRegion_id());
                            getDate();
                        }
                        break;
                }
            }
        };
    }

    /**
     * 展示推荐专家信息
     */
    private void showDoctorExpertRecommend() {
        mAdapter.setDate(provideViewDoctorExpertRecommendList);
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
//        mChoiceDepartmentFLayout = (LinearLayout) this.findViewById(R.id.li_activityJoinDoctorsUnion_choiceDepartmentFLayout);
//        mChoiceDepartmentFText = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_choiceDepartmentFText);
//        mChoiceDepartmentSLayout = (LinearLayout) this.findViewById(R.id.li_activityJoinDoctorsUnion_choiceDepartmentSLayout);
//        mChoiceDepartmentSText = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_choiceDepartmentSText);
//        mChoiceDepartmentFLayout.setOnClickListener(new ButtonClick());
//        mChoiceDepartmentSLayout.setOnClickListener(new ButtonClick());

        mRecyclerView = this.findViewById(R.id.tjzj_rv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new FragmentHomeTJZJAdapter(provideViewDoctorExpertRecommendList,mContext);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new FragmentHomeTJZJAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(mContext,ZJXQActivity.class).putExtra("provideViewDoctorExpertRecommend",provideViewDoctorExpertRecommendList.get(position)));
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    int lastVisiblePosition = manager.findLastVisibleItemPosition();
                    if(lastVisiblePosition >= manager.getItemCount() - 1) {
                        if (loadDate)
                        {
                            mNumPage ++;
                            getDate();
                        }

                    }
                }
            }
        });



        mChoiceRegion = (LinearLayout)this.findViewById(R.id.li_activityJoinDoctorsUnion_choiceRegionText);
        mChoiceRegionText = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_choiceRegionText);
        mChoiceRegionText.setText(mApp.gGDLocationName);
        mChoiceRegion.setOnClickListener(new ButtonClick());

        mChoiceHospital = (LinearLayout)this.findViewById(R.id.li_activityJoinDoctorsUnion_choiceHospitalLayout);
        mChoiceHospitalText = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_choiceHospitalText);
        mSearchTextView = (TextView)this.findViewById(R.id.tv_activityJoinDoctorsUnion_searchText);

        mSearchEditText = (EditText)this.findViewById(R.id.et_search);

//        mChoiceRegion.setOnClickListener(new ButtonClick());
//        mChoiceHospital.setOnClickListener(new ButtonClick());
        llBack.setOnClickListener(new ButtonClick());
        mSearchTextView.setOnClickListener(new ButtonClick());



        drawerLayout = (ImageView)this.findViewById(R.id.drawerLayout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerLayout.setOnClickListener(new ButtonClick());

        mJGBJRecycleView = (RecyclerView)this.findViewById(R.id.rv_jgbj);
        mYSZCRecycleView = (RecyclerView)this.findViewById(R.id.rv_yszc);

        //创建默认的线性LayoutManager
        FullyGridLayoutManager gridLayoutManagerJGBJ = new FullyGridLayoutManager(mContext,3);
        gridLayoutManagerJGBJ.setOrientation(LinearLayout.VERTICAL);
        mJGBJRecycleView.setLayoutManager(gridLayoutManagerJGBJ);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mJGBJRecycleView.setHasFixedSize(true);

        mJGBJtAdapter = new JGBJtAdapter(mProvideBasicsDominJGBJ, mContext,mActivity);
        mJGBJRecycleView.setAdapter(mJGBJtAdapter);

        //创建默认的线性LayoutManager
        FullyGridLayoutManager gridLayoutManager = new FullyGridLayoutManager(mContext,3);
        gridLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mYSZCRecycleView.setLayoutManager(gridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mYSZCRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mYSZCtAdapter = new YSZCtAdapter(mProvideBasicsDominYSZC, mContext,mActivity);
        mYSZCRecycleView.setAdapter(mYSZCtAdapter);

        tv_cz = (TextView)this.findViewById(R.id.tv_cz);
        tv_qd = (TextView)this.findViewById(R.id.tv_qd);
        tv_zdj = (EditText)this.findViewById(R.id.tv_zdj);
        tv_zgj = (EditText)this.findViewById(R.id.tv_zgj);

        tv_cz.setOnClickListener(new ButtonClick());
        tv_qd.setOnClickListener(new ButtonClick());

    }

    /**
     * 机构就诊点击事件
     * @param position
     */
    public void setJGBJClick(int position) {
        for (int i = 0; i < mProvideBasicsDominJGBJ.size(); i++)
            mProvideBasicsDominJGBJ.get(i).setChoice(false);
        mProvideBasicsDominJGBJ.get(position).setChoice(true);
        mJGBJtAdapter.setDate(mProvideBasicsDominJGBJ);
    }


    /**
     * 医生职称点击事件
     * @param position
     */
    public void YSZCClick(int position) {
        for (int i = 0; i < mProvideBasicsDominYSZC.size(); i++)
            mProvideBasicsDominYSZC.get(i).setChoice(false);
        mProvideBasicsDominYSZC.get(position).setChoice(true);
        mYSZCtAdapter.setDate(mProvideBasicsDominYSZC);
    }





    /**
     *   获取进度条
     */

    public void getProgressBar(String title,String progressPrompt){
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
    public void cacerProgress(){
        if (mDialogProgress != null) {
            mDialogProgress.dismiss();
        }
    }




    class   ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_back:
                    finish();
                    break;

                case R.id.li_activityJoinDoctorsUnion_choiceRegionText:
                    if (mApp.gRegionProvideList == null || mApp.gRegionProvideList.size() == 0)
                    {
                        Toast.makeText(mContext,"区域数据为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //弹出对话框选择省份
                    mPicker = new ProvincePicker(mContext);
                    mPicker.setActivity(mActivity,6);
                    mPicker.show();
                    break;
                case R.id.drawerLayout:
                    // 按钮按下，将抽屉打开
                    mDrawerLayout.openDrawer(Gravity.RIGHT);

                    break;
                case R.id.tv_cz:
                    tv_zdj.setText("");
                    tv_zgj.setText("");
                    for (int i = 0; i < mProvideBasicsDominJGBJ.size(); i++)
                    {
                        mProvideBasicsDominJGBJ.get(i).setChoice(false);
                        mJGBJtAdapter.setDate(mProvideBasicsDominJGBJ);
                    }
                    for (int i = 0; i < mProvideBasicsDominYSZC.size(); i++)
                    {
                        mProvideBasicsDominYSZC.get(i).setChoice(false);
                        mYSZCtAdapter.setDate(mProvideBasicsDominYSZC);
                    }

                    break;
                case R.id.tv_qd:
                    mDrawerLayout.closeDrawers();
                    //
                    for (int i = 0; i < mProvideBasicsDominJGBJ.size(); i++)
                    {
                        if (mProvideBasicsDominJGBJ.get(i).isChoice())
                            provideViewDoctorExpertRecommend.setSearchHospitalType(mProvideBasicsDominJGBJ.get(i).getAttrCode()+"");
                    }

                    for (int i = 0; i < mProvideBasicsDominYSZC.size(); i++)
                    {
                        if (mProvideBasicsDominYSZC.get(i).isChoice())
                            provideViewDoctorExpertRecommend.setSearchDoctorTitle(mProvideBasicsDominYSZC.get(i).getAttrCode()+"");
                    }

                    provideViewDoctorExpertRecommend.setSearchPriceSectionLow(tv_zdj.getText().toString());
                    provideViewDoctorExpertRecommend.setSearchPriceSectionHigh(tv_zgj.getText().toString());
                    provideViewDoctorExpertRecommendList.clear();
                    getDate();
                    break;
//                case R.id.li_activityJoinDoctorsUnion_choiceHospitalLayout:
//                    if (provideViewDoctorExpertRecommend.getProvince() == null || "".equals(provideViewDoctorExpertRecommend.getProvince()))
//                    {
//                        Toast.makeText(mContext,"请选择地区",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    showChoiceHospitalView();
//                    break;
//
//                case R.id.li_activityJoinDoctorsUnion_choiceDepartmentFLayout:
//                    if (provideViewDoctorExpertRecommend.getSearchHospitalInfoCode() == null || "".equals(provideViewDoctorExpertRecommend.getSearchHospitalInfoCode()))
//                    {
//                        Toast.makeText(mContext,"请选择医院",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    showChoiceHospitalDepartmentFView();
//                    break;
//
//                case R.id.li_activityJoinDoctorsUnion_choiceDepartmentSLayout:
//                    if (provideViewDoctorExpertRecommend.getSearchDepartmentId() == null || "".equals(provideViewDoctorExpertRecommend.getSearchDepartmentId()))
//                    {
//                        Toast.makeText(mContext,"请选择一级科室",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    showChoiceHospitalDepartmentSView();
//                    break;

                case R.id.tv_activityJoinDoctorsUnion_searchText:
                    mNumPage = 1;
                    mSearchModel = 1;

                    loadDate = true;
                    provideViewDoctorExpertRecommendList.clear();
                    provideViewDoctorExpertRecommend.setSearchName(mSearchEditText.getText().toString());
                    getDate();
                    break;

            }
        }
    }

    /**
     * 选择二级科室
     */
    private void showChoiceHospitalDepartmentSView() {
        if (mProvideHospitalDepartmentSInfos != null)
        {
            mProvideHospitalDepartmentSNameInfos = new String[mProvideHospitalDepartmentSInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalDepartmentSInfos.size(); i++)
        {
            mProvideHospitalDepartmentSNameInfos[i] = mProvideHospitalDepartmentSInfos.get(i).getDepartmentName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择二级科室");
        listDialog.setItems(mProvideHospitalDepartmentSNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalDepartmentSText(which);
            }
        });
        listDialog.show();
    }

    /**
     * 显示科室名称以及获取数据
     * @param index
     */
    private void showChoiceHospitalDepartmentSText(int index) {
        mChoiceDepartmentSText.setText(mProvideHospitalDepartmentSInfos.get(index).getDepartmentName());
        provideViewDoctorExpertRecommend.setSearchDepartmentSecondId(mProvideHospitalDepartmentSInfos.get(index).getHospitalDepartmentId()+"");
        provideViewDoctorExpertRecommendList.clear();
        getDate();
    }

    /**
     * 选择一级科室
     */
    private void showChoiceHospitalDepartmentFView() {
        if (mProvideHospitalDepartmentFInfos != null)
        {
            mProvideHospitalDepartmentFNameInfos = new String[mProvideHospitalDepartmentFInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalDepartmentFInfos.size(); i++)
        {
            mProvideHospitalDepartmentFNameInfos[i] = mProvideHospitalDepartmentFInfos.get(i).getDepartmentName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择一级科室");
        listDialog.setItems(mProvideHospitalDepartmentFNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalDepartmentText(which);
            }
        });
        listDialog.show();
    }

    /**
     * 显示科室名称以及获取数据
     * @param index
     */
    private void showChoiceHospitalDepartmentText(int index) {
        mChoiceDepartmentFText.setText(mProvideHospitalDepartmentFInfos.get(index).getDepartmentName());
        provideViewDoctorExpertRecommend.setSearchDepartmentId(mProvideHospitalDepartmentFInfos.get(index).getHospitalDepartmentId()+"");
        provideViewDoctorExpertRecommend.setSearchDepartmentSecondId("");
        mChoiceDepartmentSText.setText("请选择二级科室");
        provideViewDoctorExpertRecommendList.clear();
        getDate();
        //获取科室信息
        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    //获取二级科室
                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(mChoiceHospitalIndex).getHospitalInfoCode());
                    provideHospitalDepartment.setHospitalDepartmentId(mProvideHospitalDepartmentFInfos.get(index).getHospitalDepartmentId());
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取二级科室信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //二级科室信息获取成功
                    mProvideHospitalDepartmentSInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 搜索
     */
    private void getDate() {

        provideViewDoctorExpertRecommend.setRowNum(mRowNum+"");
        provideViewDoctorExpertRecommend.setPageNum(mNumPage+"");
        provideViewDoctorExpertRecommend.setLoginUserPosition(mApp.loginDoctorPosition);
        provideViewDoctorExpertRecommend.setRequestClientType("1");
        provideViewDoctorExpertRecommend.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        provideViewDoctorExpertRecommend.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        provideViewDoctorExpertRecommend.setSearchDoctorName(mSearchEditText.getText().toString());
//        provideViewDoctorExpertRecommend.setShowNum("4");

        new Thread(){
            public void run(){
                try {
                    String string = new Gson().toJson(provideViewDoctorExpertRecommend);
                    String urlStr = Constant.SERVICEURL+"patientSearchDoctorControlle/searchIndexExpertRecommendDoctorMoreShow";
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+string, Constant.SERVICEURL+"patientSearchDoctorControlle/searchIndexExpertRecommendDoctorMoreShow");
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(5);
            }
        }.start();
    }

    /**
     * 显示医院
     */
    private void showChoiceHospitalText(int index) {
        mChoiceHospitalText.setText(mProvideHospitalInfos.get(index).getHospitalName());
        provideViewDoctorExpertRecommend.setSearchHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
        provideViewDoctorExpertRecommend.setSearchDepartmentId("");
        provideViewDoctorExpertRecommend.setSearchDepartmentSecondId("");
        mChoiceDepartmentSText.setText("请选择二级科室");
        mChoiceDepartmentFText.setText("请选择一级科室");
        //获取一级科室
        getProgressBar("请稍候。。。。","正在获取数据");
        new Thread(){
            public void run(){
                try {
                    //获取一级科室
                    ProvideHospitalDepartment provideHospitalDepartment = new ProvideHospitalDepartment();
                    provideHospitalDepartment.setHospitalInfoCode(mProvideHospitalInfos.get(index).getHospitalInfoCode());
                    provideHospitalDepartment.setHospitalDepartmentId(0);
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo="+new Gson().toJson(provideHospitalDepartment),Constant.SERVICEURL+"hospitalDataController/getHospitalDepartment");
                    NetRetEntity netRetEntity = new Gson().fromJson(mNetRetStr, NetRetEntity.class);
                    if (netRetEntity.getResCode() == 0) {
                        NetRetEntity retEntity = new NetRetEntity();
                        retEntity.setResCode(0);
                        retEntity.setResMsg("获取一级科室信息失败："+netRetEntity.getResMsg());
                        mNetRetStr = new Gson().toJson(retEntity);
                        mHandler.sendEmptyMessage(0);
                        return;
                    }
                    //一级科室信息获取成功
                    mProvideHospitalDepartmentFInfos = new Gson().fromJson(netRetEntity.getResJsonData(), new TypeToken<List<ProvideHospitalDepartment>>(){}.getType());
                } catch (Exception e) {
                    NetRetEntity retEntity = new NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员："+e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 选择医院对话框
     *
     */
    private void showChoiceHospitalView() {
        if (mProvideHospitalInfos != null)
        {
            mProvideHospitalNameInfos = new String[mProvideHospitalInfos.size()];
        }
        for (int i = 0; i < mProvideHospitalInfos.size(); i++)
        {
            mProvideHospitalNameInfos[i] = mProvideHospitalInfos.get(i).getHospitalName();
        }
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mContext);
        listDialog.setTitle("请选择医院");
        listDialog.setItems(mProvideHospitalNameInfos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showChoiceHospitalText(which);
            }
        });
        listDialog.show();
    }


    /**
     * 设置所在地区显示
     */
    public void setRegionText() {
        provideViewDoctorExpertRecommend.setSearchProvince(mChoiceRegionMap.get("provice").getRegion_id());
        if ("sqb".equals(mChoiceRegionMap.get("city").getRegion_id())) {
            mChoiceRegionText.setText(mChoiceRegionMap.get("provice").getRegion_name());
            mChoiceRegionLevel = 1;               //市级所有，则是省级
            mChoiceRegionID = mChoiceRegionMap.get("provice").getRegion_id();
            provideViewDoctorExpertRecommend.setSearchCity("");
            provideViewDoctorExpertRecommend.setSearchArea("");
        } else if (mChoiceRegionMap.get("dist") == null || "qqb".equals(mChoiceRegionMap.get("dist").getRegion_id())) {
            mChoiceRegionText.setText(mChoiceRegionMap.get("city").getRegion_name());
            mChoiceRegionLevel = 2;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
            provideViewDoctorExpertRecommend.setSearchArea("");
        }
        if (!"sqb".equals(mChoiceRegionMap.get("city").getRegion_id())) {
            provideViewDoctorExpertRecommend.setSearchCity(mChoiceRegionMap.get("city").getRegion_id());
            mChoiceRegionLevel = 2;               //市级
            mChoiceRegionID = mChoiceRegionMap.get("city").getRegion_id();
        }
        if (mChoiceRegionMap.get("dist") != null && !"qqb".equals(mChoiceRegionMap.get("dist").getRegion_id())) {
            mChoiceRegionText.setText(mChoiceRegionMap.get("dist").getRegion_name());
            provideViewDoctorExpertRecommend.setSearchArea(mChoiceRegionMap.get("dist").getRegion_id());
            mChoiceRegionLevel = 3;               //区级全部，则是市级
            mChoiceRegionID = mChoiceRegionMap.get("dist").getRegion_id();
        }
        provideViewDoctorExpertRecommendList.clear();
        mSearchEditText.setText("");
        getDate();
    }



}
