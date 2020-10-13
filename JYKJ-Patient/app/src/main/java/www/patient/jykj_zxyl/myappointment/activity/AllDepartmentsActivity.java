package www.patient.jykj_zxyl.myappointment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import com.hyphenate.easeui.utils.ActivityUtil;
import com.hyphenate.util.HanziToPinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.TJZJActivity;
import www.patient.jykj_zxyl.base.base_bean.AllDepartmentBean;
import www.patient.jykj_zxyl.base.base_view.WaveSideBar;
import www.patient.jykj_zxyl.base.http.RetrofitUtil;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseActivity;
import www.patient.jykj_zxyl.myappointment.Contract.AllDepartmentsContract;
import www.patient.jykj_zxyl.myappointment.adapter.AllDepHotAdapter;
import www.patient.jykj_zxyl.myappointment.adapter.AllDepartmentAdapter;
import www.patient.jykj_zxyl.myappointment.adapter.HotDepAdapter;
import www.patient.jykj_zxyl.presenter.AllDepartmentsPresenter;

/*
 * 更多科室
 *
 *  */
public class AllDepartmentsActivity extends AbstractMvpBaseActivity<AllDepartmentsContract.View, AllDepartmentsPresenter>
        implements AllDepartmentsContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleview;
    @BindView(R.id.side_bar)
    WaveSideBar sideBar;
    @BindView(R.id.hot_dep)
    RecyclerView hotDepRecycleview;
    private List<AllDepartmentBean.HospitalDepartmentListBean> hospitalDepartmentList;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_all_departments;
    }


    @Override
    protected void initView() {
        super.initView();
        mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        sideBar.setOnSelectIndexItemListener(index -> {
            for (int i = 0; i < hospitalDepartmentList.size(); i++) {
                if (hospitalDepartmentList.get(i).getIndex().equals(index)) {
                    ((LinearLayoutManager) mRecycleview.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                    return;
                }
            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        hotDepRecycleview.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        hospitalDepartmentList = new ArrayList<>();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginPatientPosition", "108.93425^34.23053");
        paramMap.put("requestClientType", "1");
        paramMap.put("hospitalCode", "0");
        mPresenter.getAlldepartments(RetrofitUtil.encodeParam(paramMap));
    }

    @OnClick({R.id.ri_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ri_back:
                finish();
                break;
        }
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    public void getAlldetmentsSucess(List<AllDepartmentBean.HospitalDepartmentListBean> departments) {
        for (int i = 0; i < departments.size(); i++) {
        /*    String firstLetter = (Pinyin.toPinyin(departments.get(i).getDepartmentName().charAt(0))).substring(0, 1);
            departments.get(i).setIndex(firstLetter);*/

        }
     //   Collections.sort(departments, pinyinComparator);
        hospitalDepartmentList = departments;
        AllDepartmentAdapter allDepartmentAdapter = new AllDepartmentAdapter(R.layout.item_all_departments, hospitalDepartmentList);
        allDepartmentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.showShort("dian wo la ");
                startActivity(new Intent(AllDepartmentsActivity.this, TJZJActivity.class));
            }
        });
        mRecycleview.setAdapter(allDepartmentAdapter);


    }

    @Override
    public void getTittleMentsSucess(List<AllDepartmentBean.TitleHospitalDepartmentBean> tittleDepments) {
        if (tittleDepments.size() > 0) {
            AllDepHotAdapter hotDepAdapter = new AllDepHotAdapter(R.layout.item_hot_dep, tittleDepments);
            hotDepAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(AllDepartmentsActivity.this, TJZJActivity.class));
                }
            });
            hotDepRecycleview.setAdapter(hotDepAdapter);
        }
    }

    @Override
    public void getDataFailure(String msg) {

    }
}