package www.patient.jykj_zxyl.activity.myself.order.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allin.commlibrary.CollectionUtils;
import com.allin.commonadapter.ViewHolder;
import com.hyphenate.easeui.order.CancelConfirmDeitalActivity;
import com.hyphenate.easeui.order.CancelContractActivity;
import com.hyphenate.easeui.order.RefusedCancelContractActivity;
import com.hyphenate.easeui.order.SignOrderDetialActivity;
import com.hyphenate.easeui.ui.ChatActivity;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entity.ProvideInteractOrderInfo;
import entity.mySelf.MyOrderProcess;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.OrderMessage_OrderPayActivity;
import www.patient.jykj_zxyl.activity.myself.order.OrderOnGoingContract;
import www.patient.jykj_zxyl.activity.myself.order.OrderOnGoingPresenter;
import www.patient.jykj_zxyl.activity.myself.order.activity.CancelContractResultActivity;
import www.patient.jykj_zxyl.activity.myself.order.activity.RefundActivity;
import www.patient.jykj_zxyl.activity.myself.order.adapter.OrderOnGoingAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.MultiItemEntity;
import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.base_manager.OrderOperationManager;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;
import www.patient.jykj_zxyl.base.base_view.LoadingLayoutManager;
import www.patient.jykj_zxyl.base.base_view.SimpleDividerItemDecoration;
import www.patient.jykj_zxyl.base.enum_type.OrderStatusEnum;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseFragment;
import www.patient.jykj_zxyl.util.ToastUtils;

/**
 * Description:进行中订单列表
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 13:47
 */
public class OrderOnGoingFragment extends
        AbstractMvpBaseFragment<OrderOnGoingContract.View, OrderOnGoingPresenter>
        implements OrderOnGoingContract.View{

    private SmartRefreshLayout mRefreshLayout;//刷新列表
    private RecyclerView mRvList;//列表
    private OrderOnGoingAdapter mOrderOnGoingAdapter;//进行中适配器
    private LoadingLayoutManager mLoadingLayout;//重新加载空页面管理
    private List<MultiItemEntity> mMultiItemEntitys;//多布局内容列表
    private JYKJApplication mApp;
    private  MyOrderProcess myOrderProcess;
    private boolean isJumpIm;
    private int currentPos;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_base_list;
    }

    public static OrderOnGoingFragment newInstance() {
        OrderOnGoingFragment fragment = new OrderOnGoingFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    protected void initView(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRvList = view.findViewById(R.id.rv_list);
        mMultiItemEntitys=new ArrayList<>();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLoadingAndRetryManager();
        //添加监听
        addListener();
        //设置适配器
        setAdapter();
    }

    /**
     * 刷新数据
     */
    public void refreshLaodData(){
        pageIndex=1;
        mPresenter.sendSearchPatientMyOrderResOngoingRequest(pageSize+"",
                pageIndex+"",this.getActivity());
    }

    @Override
    protected void initData() {
        mPresenter.sendSearchPatientMyOrderResOngoingRequest(pageSize+"",
                pageIndex+"",this.getActivity());
    }



    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayout.setRetryListener(v -> {
            mLoadingLayout.showLoading();
        });
        mLoadingLayout.showLoading();
    }

    /**
     * 添加监听
     */
    private void addListener() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(Objects.requireNonNull(this.getContext())));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this.getContext()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex=1;
                mPresenter.sendSearchPatientMyOrderResOngoingRequest(pageSize+"",
                        pageIndex+"",OrderOnGoingFragment.this.getActivity());

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageIndex++;
                mPresenter.sendSearchPatientMyOrderResOngoingRequest(pageSize+"",
                        pageIndex+"",OrderOnGoingFragment.this.getActivity());

            }
        });
    }



    /**
     * 设置适配器
     */
    private void setAdapter(){
        mRvList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mOrderOnGoingAdapter= new OrderOnGoingAdapter(this.getContext(),mMultiItemEntitys);
        mRvList.setAdapter(mOrderOnGoingAdapter);
        SimpleDividerItemDecoration decor = new SimpleDividerItemDecoration(Objects.requireNonNull
                (getContext()), SimpleDividerItemDecoration.VERTICAL,true);
        decor.setDrawable(getResources().getDrawable(R.drawable.bg_shape_line));
        mRvList.addItemDecoration(decor);
        mOrderOnGoingAdapter.setOnClickItemListener(new OrderOnGoingAdapter.OnClickItemListener() {
            @Override
            public void onClickCancelContract(int pos) {
                myOrderProcess = (MyOrderProcess) mMultiItemEntitys.get(pos);
                Bundle bundle=new Bundle();
                bundle.putString("orderId",myOrderProcess.getOrderCode());
                bundle.putString("operDoctorCode",myOrderProcess.getDoctorCode());
                bundle.putString("operDoctorName",myOrderProcess.getDoctorName());
                startActivity(CancelContractActivity.class,bundle,100);
            }

            @Override
            public void onClickConsult(int pos) {
                isJumpIm=true;
                myOrderProcess = (MyOrderProcess) mMultiItemEntitys.get(pos);
                mPresenter.sendGetUserListRequest(myOrderProcess.getDoctorCode());

            }

            @Override
            public void onClickCancelOnGoing(int pos) {
                currentPos=pos;
                myOrderProcess = (MyOrderProcess) mMultiItemEntitys.get(pos);
                Bundle bundle=new Bundle();
                bundle.putString("mainDoctorCode",myOrderProcess.getDoctorCode());
                bundle.putString("mainDoctorName",myOrderProcess.getDoctorName());
                bundle.putInt("orderState",myOrderProcess.getFlagTreatmentState());
                bundle.putString("signNo",myOrderProcess.getSignNo());
                bundle.putString("orderId",myOrderProcess.getOrderCode());
                startActivity(CancelContractResultActivity.class,bundle,100);

            }

            @Override
            public void onClickItem(int position, ViewHolder holder) {
                currentPos=position;
                 myOrderProcess = (MyOrderProcess)
                        mMultiItemEntitys.get(position);
                Integer treatmentType = myOrderProcess.getTreatmentType();
                if (treatmentType==4) {
                    if(myOrderProcess.getFlagTreatmentState()
                            .toString().equals(OrderStatusEnum.orderDoctorCancelContractCode)){
                        Bundle bundle=new Bundle();
                        bundle.putString("orderId",myOrderProcess.getOrderCode());
                        bundle.putString("operDoctorCode",myOrderProcess.getDoctorCode());
                        bundle.putString("operDoctorName",myOrderProcess.getDoctorName());
                        startActivity(CancelConfirmDeitalActivity.class,bundle,100);

                    }else{
                        Bundle bundle=new Bundle();
                        bundle.putString("signCode",myOrderProcess.getOrderCode());
                        bundle.putString("operDoctorCode",myOrderProcess.getDoctorCode());
                        bundle.putString("operDoctorName",myOrderProcess.getDoctorName());
                        startActivity(SignOrderDetialActivity.class,bundle,100);
                    }

                }else{
                    ProvideInteractOrderInfo parorder = new ProvideInteractOrderInfo();
                    parorder.setOrderCode(myOrderProcess.getOrderCode());
                    startActivity(new Intent(mActivity, OrderMessage_OrderPayActivity.class)
                            .putExtra("provideInteractOrderInfo", parorder));
                }
            }

            @Override
            public void onClickRefund(int pos) {
                currentPos=pos;
                myOrderProcess = (MyOrderProcess)
                        mMultiItemEntitys.get(pos);
                String actualPayment = myOrderProcess.getActualPayment();
                Bundle bundle=new Bundle();
                bundle.putString("actualPayment",actualPayment);
                bundle.putString("orderId",myOrderProcess.getOrderCode());
                startActivity(RefundActivity.class,bundle,100);
            }

            @Override
            public void onClickRefused(int pos) {
                currentPos=pos;
                myOrderProcess = (MyOrderProcess)
                        mMultiItemEntitys.get(pos);
                Bundle bundle=new Bundle();
                bundle.putString("orderId",myOrderProcess.getOrderCode());
                bundle.putString("signOrderCode",myOrderProcess.getDoctorCode());
                bundle.putString("operDoctorName",myOrderProcess.getDoctorName());
                bundle.putString("operDoctorCode",myOrderProcess.getDoctorCode());
                startActivity(RefusedCancelContractActivity.class,bundle,100);
            }

            @Override
            public void onClickAgree(int pos) {
                currentPos=pos;
                showLoading("",null);
                myOrderProcess = (MyOrderProcess)
                        mMultiItemEntitys.get(pos);
                OrderOperationManager.getInstance().sendOrderCancelContractOperRequest(
                        myOrderProcess.getDoctorCode(),
                        myOrderProcess.getDoctorName(),
                        myOrderProcess.getOrderCode(), myOrderProcess.getSignNo(),
                        mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),
                        mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(),
                        "1", ParameUtil.loginDoctorPosition
                        , new OrderOperationManager.OnCallBackListener() {
                            @Override
                            public void onResult(boolean isSucess, String msg) {
                                dismissLoading();
                                if(isSucess){
                                    mPresenter.sendGetUserListRequest(myOrderProcess.getDoctorCode());
                                }else{
                                    ToastUtils.showToast(msg);
                                }
                            }
                        });
            }
        });

    }

    @Override
    public void showEmpty() {
        if(pageIndex == 1){
            mLoadingLayout.showEmpty();
        }

    }

    @Override
    public void showRetry() {
        if (pageIndex==1) {
            mLoadingLayout.showError();
        }
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();

    }

    private void handleData(List<MultiItemEntity> mMultiItemEntitys){
        for (MultiItemEntity mMultiItemEntity : mMultiItemEntitys) {
            MyOrderProcess myOrderProcess =
                    (MyOrderProcess) mMultiItemEntity;
            if (myOrderProcess.getTreatmentType()==4) {
                myOrderProcess.setItemType("1");
            }else{
                myOrderProcess.setItemType("2");
            }

        }
    }

    @Override
    public void getSearchPatientMyOrderOngoingResult(List<MyOrderProcess> dataBeans) {
        if (pageIndex == 1) {
            mMultiItemEntitys.clear();
            mRefreshLayout.finishRefresh();
        }
        if (!CollectionUtils.isEmpty(dataBeans)) {
            mMultiItemEntitys.addAll(dataBeans);
            handleData(mMultiItemEntitys);
            mOrderOnGoingAdapter.setDatas(mMultiItemEntitys);
            mOrderOnGoingAdapter.notifyDataSetChanged();
            mRefreshLayout.finishLoadMore();
        } else {
            if (pageIndex == 1) {
                mLoadingLayout.showEmpty();
            } else {
                mRefreshLayout.finishLoadMore();
            }
        }
        mLoadingLayout.showContent();
    }

    @Override
    public void getUserInfoResult(UserInfoBaseBean userInfoBaseBean) {
        if (ActivityStackManager.getInstance().exists(ChatActivity.class)) {
            ActivityStackManager.getInstance().finish(ChatActivity.class);
        }

        OrderMessage terminationOrder = getOrderMessage("terminationOrder", "1",myOrderProcess);
        Intent intent = new Intent();
        intent.setClass(Objects.requireNonNull(this.getContext()), ChatActivity.class);
        intent.putExtra("userCode", myOrderProcess.getDoctorCode());
        intent.putExtra("userName", myOrderProcess.getDoctorName());
        intent.putExtra("doctorUrl", userInfoBaseBean.getUserLogoUrl());
        intent.putExtra("patientUrl", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
        intent.putExtra("operDoctorName", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        terminationOrder.setImageUrl(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
        Bundle bundle=new Bundle();
        if(!isJumpIm){
            bundle.putSerializable("orderMsg",terminationOrder);
            intent.putExtras(bundle);
        }

        startActivity(intent);
        isJumpIm=false;
    }

    /**
     * 获取订单信息
     * @param messageType 消息类型
     * @param orderType 操作类型
     * @return orderMessage
     */
    private OrderMessage getOrderMessage(String messageType, String orderType,
                                         MyOrderProcess myOrderProcess) {

        @SuppressLint("DefaultLocale") String coatch = String.format("%d次/%s",
                myOrderProcess.getCoachValue(), myOrderProcess.getCoachUnitName());
        OrderMessage orderMessage = new OrderMessage(myOrderProcess.getOrderCode(),myOrderProcess.getSignNo(),
                myOrderProcess.getProCount() + "项",
                coatch, myOrderProcess.getTimesCode()+"个月",myOrderProcess.getActualPayment() + "", messageType, orderType);
        return orderMessage;

    }
}
