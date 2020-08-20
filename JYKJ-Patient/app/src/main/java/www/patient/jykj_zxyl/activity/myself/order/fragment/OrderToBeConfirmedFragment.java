package www.patient.jykj_zxyl.activity.myself.order.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.allin.commlibrary.CollectionUtils;
import com.allin.commonadapter.ViewHolder;
import com.hyphenate.easeui.order.RefusedOrderActivity;
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
import entity.mySelf.ProvideInteractOrderInfo;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.order.OrderToBeConfirmedContract;
import www.patient.jykj_zxyl.activity.myself.order.OrderToBeconfirmedPresenter;
import www.patient.jykj_zxyl.activity.myself.order.adapter.CommonMutipleComplateOrderListItemType;
import www.patient.jykj_zxyl.activity.myself.order.adapter.OrderToBeConfirmedAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.MultiItemEntity;
import www.patient.jykj_zxyl.base.base_bean.OrderMessage;
import www.patient.jykj_zxyl.base.base_bean.UpdateOrderResultBean;
import www.patient.jykj_zxyl.base.base_bean.UserInfoBaseBean;
import www.patient.jykj_zxyl.base.base_manager.OrderOperationManager;
import www.patient.jykj_zxyl.base.base_utils.ActivityStackManager;
import www.patient.jykj_zxyl.base.base_utils.GsonUtils;
import www.patient.jykj_zxyl.base.base_utils.StringUtils;
import www.patient.jykj_zxyl.base.base_view.LoadingLayoutManager;
import www.patient.jykj_zxyl.base.base_view.SimpleDividerItemDecoration;
import www.patient.jykj_zxyl.base.base_view.SlideRecyclerView;
import www.patient.jykj_zxyl.base.http.ParameUtil;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseFragment;
import www.patient.jykj_zxyl.util.ToastUtils;

/**
 * Description:待确认订单列表
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 11:42
 */
public class OrderToBeConfirmedFragment extends
        AbstractMvpBaseFragment<OrderToBeConfirmedContract.View,
                OrderToBeconfirmedPresenter>implements OrderToBeConfirmedContract.View {

    private SmartRefreshLayout mRefreshLayout;//刷新列表
    private SlideRecyclerView mRvList;//列表
    private OrderToBeConfirmedAdapter mOrderToBeConfirmedAdapter;//待确认订单列表适配器
    private List<MultiItemEntity> mMultiItemEntitys;//多布局内容列表
    private LoadingLayoutManager  mLoadingLayout;//重新加载空页面管理
    private JYKJApplication mApp;
    private ProvideInteractOrderInfo provideInteractOrderInfo ;
    private List<ProvideInteractOrderInfo> mHZEntyties = new ArrayList<>();
    private int operationType;//1 同意 2修改 3拒绝
    /**是否走刷新*/
    private boolean isRefresh;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_sideslip_base_list;
    }



    public static OrderToBeConfirmedFragment newInstance() {
        OrderToBeConfirmedFragment fragment = new OrderToBeConfirmedFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    /**
     * 初始化loading页面
     */
    private void initLoadingAndRetryManager() {
        mLoadingLayout = LoadingLayoutManager.wrap(mRefreshLayout);
        mLoadingLayout.setRetryListener(v -> {
            mLoadingLayout.showLoading();
            mPresenter.sendSearchPatientMyOrderResIncompleteRequest(
                    pageSize+"",pageIndex+"",this.getActivity());
        });
        mLoadingLayout.showLoading();
    }

    @Override
    protected void initView(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRvList = view.findViewById(R.id.rv_list);
        mApp = (JYKJApplication) getActivity().getApplication();
        mMultiItemEntitys=new ArrayList<>();
        initLoadingAndRetryManager();
        addListener();
        setAdapter();
    }


    /**
     * 刷新数据
     */
    public void refreshLaodData(){
        pageIndex=1;
        mPresenter.sendSearchPatientMyOrderResIncompleteRequest(
                pageSize+"",pageIndex+"",this.getActivity());
    }
    @Override
    protected void initData() {
        mPresenter.sendSearchPatientMyOrderResIncompleteRequest(
                pageSize+"",pageIndex+"",this.getActivity());
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
                mPresenter.sendSearchPatientMyOrderResIncompleteRequest(
                        pageSize+"",pageIndex+"",
                        OrderToBeConfirmedFragment.this.getActivity());
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageIndex++;
                mPresenter.sendSearchPatientMyOrderResIncompleteRequest(
                        pageSize+"",pageIndex+"",
                        OrderToBeConfirmedFragment.this.getActivity());
            }

        });

    }



    /**
     * 设置适配器
     */
    private void setAdapter(){
        mRvList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mOrderToBeConfirmedAdapter=new OrderToBeConfirmedAdapter(this.getContext(),mMultiItemEntitys);
        SimpleDividerItemDecoration decor = new SimpleDividerItemDecoration(Objects.requireNonNull
                (getContext()), SimpleDividerItemDecoration.VERTICAL,true);
        decor.setDrawable(getResources().getDrawable(R.drawable.bg_shape_line));
        mRvList.addItemDecoration(decor);
        mRvList.setAdapter(mOrderToBeConfirmedAdapter);
        mOrderToBeConfirmedAdapter.setOnClickItemListener(new OrderToBeConfirmedAdapter.OnClickItemListener() {
            @Override
            public void onClickAgree(int position) {
                operationType=1;
                showLoading("",null);
                 provideInteractOrderInfo =
                        (ProvideInteractOrderInfo) mMultiItemEntitys.get(position);
                OrderOperationManager.getInstance().sendOrderOperRequest(
                        provideInteractOrderInfo.getDoctorCode(),
                        provideInteractOrderInfo.getDoctorName(),
                        provideInteractOrderInfo.getOrderCode(), provideInteractOrderInfo.getSignNo(),
                        mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),
                        mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(),
                        "1",ParameUtil.loginDoctorPosition
                        , new OrderOperationManager.OnCallBackListener() {
                            @Override
                            public void onResult(boolean isSucess, String msg) {
                                dismissLoading();
                                if(isSucess){
                                    mPresenter.sendGetUserListRequest(provideInteractOrderInfo.getDoctorCode());
                                }else{
                                    ToastUtils.showToast(msg);
                                }
                            }
                        });
            }

            @Override
            public void onClickUpdate(int position) {
                operationType=2;
                showLoading("",null);
                 provideInteractOrderInfo =
                        (ProvideInteractOrderInfo) mMultiItemEntitys.get(position);
                OrderOperationManager.getInstance().sendOrderOperRequest(
                        provideInteractOrderInfo.getDoctorCode(),
                        provideInteractOrderInfo.getDoctorName(),
                        provideInteractOrderInfo.getOrderCode(), provideInteractOrderInfo.getSignNo(),
                        mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),
                        mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(),
                        "2",ParameUtil.loginDoctorPosition
                        , new OrderOperationManager.OnCallBackListener() {
                            @Override
                            public void onResult(boolean isSucess, String msg) {

                                if(isSucess){
                                    if(StringUtils.isNotEmpty(msg)){
                                        UpdateOrderResultBean updateOrderResultBean
                                                = GsonUtils.fromJson(msg, UpdateOrderResultBean.class);
                                        if (updateOrderResultBean!=null) {
                                            provideInteractOrderInfo.setOrderCode(updateOrderResultBean.getSignCode());
                                            mPresenter.sendGetUserListRequest(provideInteractOrderInfo.getDoctorCode());
                                        }

                                    }

                                }else{
                                    ToastUtils.showToast(msg);
                                }
                            }
                        });
            }

            @Override
            public void onClickRefuse(int position) {
                operationType=3;
                provideInteractOrderInfo =
                        (ProvideInteractOrderInfo) mMultiItemEntitys.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("orderId",provideInteractOrderInfo.getOrderCode());
                bundle.putString("operDoctorCode",provideInteractOrderInfo.getDoctorCode());
                bundle.putString("operDoctorName",provideInteractOrderInfo.getDoctorName());
                startActivity(RefusedOrderActivity.class,bundle);
            }

            @Override
            public void onClickCancelContractRefuse(int position) {

            }

            @Override
            public void onClickCancelContractAgree(int position) {

            }

            @Override
            public void onClickPayment(int position) {

            }

            @Override
            public void onClickItem(int position, ViewHolder holder) {
                 provideInteractOrderInfo =
                        (ProvideInteractOrderInfo) mMultiItemEntitys.get(position);
                Integer treatmentType = provideInteractOrderInfo.getTreatmentType();
                if (treatmentType==4) {
                    Bundle bundle=new Bundle();
                    bundle.putString("signCode",provideInteractOrderInfo.getOrderCode());
                    bundle.putString("operDoctorCode",provideInteractOrderInfo.getDoctorCode());
                    bundle.putString("operDoctorName",provideInteractOrderInfo.getDoctorName());
                    startActivity(SignOrderDetialActivity.class,bundle);
                }else{

                }

            }

            @Override
            public void onClickDelete(int pos) {

            }
        });
    }

    /**
     * 获取订单信息
     * @param messageType 消息类型
     * @param orderType 操作类型
     * @return orderMessage
     */
    private OrderMessage getOrderMessage(String messageType, String orderType,
                                         ProvideInteractOrderInfo provideInteractOrderInfo) {

        @SuppressLint("DefaultLocale")
        String coatch = String.format("%d次/%s",
                provideInteractOrderInfo.getCoachValue(), provideInteractOrderInfo.getCoachUnitName());
        OrderMessage orderMessage = new OrderMessage(provideInteractOrderInfo.getOrderCode(),provideInteractOrderInfo.getSignNo(),
                provideInteractOrderInfo.getProCount() + "项",
                coatch, provideInteractOrderInfo.getTimesName(),provideInteractOrderInfo.getActualPayment() + "", messageType, orderType);
        return orderMessage;

    }

    @Override
    public void getSearchPatientMyOrderResInCompleteResult(List<ProvideInteractOrderInfo> dataBeans) {
        if (pageIndex == 1) {
            mMultiItemEntitys.clear();
            mRefreshLayout.finishRefresh();
        }
        if (!CollectionUtils.isEmpty(dataBeans)) {
            mMultiItemEntitys.addAll(dataBeans);
            handleData(mMultiItemEntitys);
            mOrderToBeConfirmedAdapter.setDatas(mMultiItemEntitys);
            if (dataBeans.size()<pageSize) {
                mRefreshLayout.finishLoadMore();
            }
            mOrderToBeConfirmedAdapter.notifyDataSetChanged();
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
        dismissLoading();
        OrderMessage orderCard=null;
        switch (operationType){
            case 1:
                orderCard = getOrderMessage("card",
                        "1", provideInteractOrderInfo);
                break;
            case 2:
                orderCard = getOrderMessage("card",
                        "2", provideInteractOrderInfo);
                break;
            case 3:
                orderCard = getOrderMessage("card",
                        "0", provideInteractOrderInfo);
                break;
                default:
        }

        if (ActivityStackManager.getInstance().exists(ChatActivity.class)) {
            ActivityStackManager.getInstance().finish(ChatActivity.class);
        }
        Intent intent = new Intent();
        intent.setClass(Objects.requireNonNull(this.getContext()), ChatActivity.class);
        intent.putExtra("userCode", provideInteractOrderInfo.getDoctorCode());
        intent.putExtra("userName", provideInteractOrderInfo.getDoctorName());
        intent.putExtra("doctorUrl", userInfoBaseBean.getUserLogoUrl());
        intent.putExtra("patientUrl", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
        intent.putExtra("operDoctorName", mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        if (orderCard!=null) {
            orderCard.setImageUrl(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserLogoUrl());
        }
        Bundle bundle=new Bundle();
        bundle.putSerializable("orderMsg",orderCard);
        intent.putExtras(bundle);
        startActivity(intent);
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

    }

    private void handleData(List<MultiItemEntity> mMultiItemEntitys){
        for (MultiItemEntity mMultiItemEntity : mMultiItemEntitys) {
            ProvideInteractOrderInfo provideInteractOrderInfo =
                    (ProvideInteractOrderInfo) mMultiItemEntity;
            if (provideInteractOrderInfo.getTreatmentType()==4) {
                provideInteractOrderInfo.setItemType(
                        CommonMutipleComplateOrderListItemType.MULTIPLE_CONTENT_ORDINARY_TYPE);
            }else{
                provideInteractOrderInfo.setItemType(
                        CommonMutipleComplateOrderListItemType.MULTIPLE_CONTENT_SIGN_UP_TYPE);
            }

        }
    }


}
