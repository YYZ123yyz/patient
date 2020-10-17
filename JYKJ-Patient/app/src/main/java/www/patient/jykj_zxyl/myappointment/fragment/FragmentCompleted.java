package www.patient.jykj_zxyl.myappointment.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_CFQActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_ZHLYActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.MyReservationListBean;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseFragment;
import www.patient.jykj_zxyl.custom.SwipeRecyclerView;
import www.patient.jykj_zxyl.myappointment.Contract.ReservationListContract;
import www.patient.jykj_zxyl.myappointment.Presenter.ResevationListPresenter;
import www.patient.jykj_zxyl.myappointment.activity.InspectionApplicationFormActivity;
import www.patient.jykj_zxyl.myappointment.activity.MedicalRecordActivity;
import www.patient.jykj_zxyl.myappointment.activity.MessageActivity;
import www.patient.jykj_zxyl.myappointment.adapter.Fragment_CompletedAdapter;
import www.patient.jykj_zxyl.myappointment.adapter.Fragment_VisitingAdapter;

/**
 * 已完成列表
 */
public class FragmentCompleted  extends AbstractMvpBaseFragment<ReservationListContract.View,
        ResevationListPresenter> implements ReservationListContract.View {
    Unbinder unbinder;
    private Context mContext;
    private Handler mHandler;
    private MyOrderActivity mActivity;
    private JYKJApplication mApp;
    private LinearLayoutManager layoutManager;
    private Fragment_CompletedAdapter fragment_completedAdapter;
    private RecyclerView rvNo;
    private String reserveCode;
    private List<MyReservationListBean> myresetvation;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_completed;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mContext = getContext();
        mApp = (JYKJApplication) getActivity().getApplication();
        myresetvation = new ArrayList<>();
        //创建默认的线性LayoutManager
        rvNo = view.findViewById(R.id.rv_no);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        rvNo.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvNo.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.sendMyReservationListRequest(mApp.loginDoctorPosition,mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(),"0","10","1");
    }

    @Override
    public void getMyReservationListResult(List<MyReservationListBean> myReservationListBeans) {
        if(myReservationListBeans!=null){
            myresetvation.addAll(myReservationListBeans);
            fragment_completedAdapter = new Fragment_CompletedAdapter(myresetvation, getContext());
            rvNo.setAdapter(fragment_completedAdapter);
            addClickListener();
        }
    }

    /**
     * 适配器的点击事件
     */
    private void addClickListener() {
        //检查单
        fragment_completedAdapter.setmOnItemClickListener(new Fragment_CompletedAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), InspectionApplicationFormActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        //处方单
        fragment_completedAdapter.setOnItemClickZXListener(new Fragment_CompletedAdapter.OnItemClickZXListener() {
            @Override
            public void onClick(int position) {
                String reserveCode = myresetvation.get(position).getReserveCode();
                Intent intent = new Intent(getContext(), WDYS_JZJL_CFQActivity.class);
                intent.putExtra("orderCode",reserveCode);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        //查看病历
        fragment_completedAdapter.setOnItemClickXYListener(new Fragment_CompletedAdapter.OnItemClickXYListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(getActivity(), MedicalRecordActivity.class));
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        //诊后留言
        fragment_completedAdapter.setOnItemMessageClickListener(new Fragment_CompletedAdapter.OnItemMessageClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("orderCode",myresetvation.get(position).getOrderCode());
                startActivity(MessageActivity.class,bundle);
            }
        });
    }

    @Override
    public void showLoading(int code) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void showWaitLoading() {

    }

    @Override
    public void hideWaitLoading() {

    }
}
