package www.patient.jykj_zxyl.activity.myself.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allin.commlibrary.CollectionUtils;
import com.hyphenate.easeui.order.SignOrderDetialActivity;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entity.mySelf.CommentInfo;
import entity.mySelf.MyOrderProcess;
import entity.mySelf.ZhlyDetailInfo;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.twjz.WDZS_WZXQActivity;
import www.patient.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.patient.jykj_zxyl.activity.myself.CommentActivity;
import www.patient.jykj_zxyl.activity.myself.LeaveMessageActivity;
import www.patient.jykj_zxyl.activity.myself.LeaveMessageShowActivity;
import www.patient.jykj_zxyl.activity.myself.ShowCommentActivity;
import www.patient.jykj_zxyl.activity.myself.order.OrderCompletedContract;
import www.patient.jykj_zxyl.activity.myself.order.OrderCompletedPresenter;
import www.patient.jykj_zxyl.activity.myself.order.activity.CancelContractResultActivity;
import www.patient.jykj_zxyl.activity.myself.order.adapter.OrderComplatedAdapter;
import www.patient.jykj_zxyl.base.base_bean.MultiItemEntity;
import www.patient.jykj_zxyl.base.base_view.LoadingLayoutManager;
import www.patient.jykj_zxyl.base.base_view.SimpleDividerItemDecoration;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseFragment;

/**
 * Description:已完成订单列表
 *
 * @author: qiuxinhai
 * @date: 2020-07-30 13:53
 */
public class OrderCompletedFragment extends AbstractMvpBaseFragment<OrderCompletedContract.View,
        OrderCompletedPresenter> implements OrderCompletedContract.View {
    private SmartRefreshLayout mRefreshLayout;//刷新列表
    private RecyclerView mRvList;//列表
    private OrderComplatedAdapter mOrderComplatedAdapter;
    private LoadingLayoutManager mLoadingLayout;//重新加载空页面管理
    private List<MultiItemEntity> mMultiItemEntitys;//多布局内容列表
    private  MyOrderProcess currentMyOrderProcess;
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_base_list;
    }

    public static OrderCompletedFragment newInstance() {
        OrderCompletedFragment fragment = new OrderCompletedFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    protected void initView(View view) {
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRvList = view.findViewById(R.id.rv_list);
        mMultiItemEntitys=new ArrayList<>();
        initLoadingAndRetryManager();
        //设置适配器
        setAdapter();
        //添加监听
        addListener();

    }

    @Override
    protected void initData() {
       mPresenter.sendSearchPatientMyOrderResCompletedRequest(pageSize+"",
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
        mRefreshLayout.setOnRefreshListener(refreshlayout -> {
            pageIndex=1;
            mPresenter.sendSearchPatientMyOrderResCompletedRequest(pageSize+"",
                    pageIndex+"",OrderCompletedFragment.this.getActivity());
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageIndex++;
                mPresenter.sendSearchPatientMyOrderResCompletedRequest(pageSize+"",
                        pageIndex+"",OrderCompletedFragment.this.getActivity());
            }
        });
        mOrderComplatedAdapter.setmOnItemClickListener(new OrderComplatedAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, View parview) {
                switch (parview.getId()) {
                    case R.id.item_root:
                        MyOrderProcess parbean = (MyOrderProcess) mMultiItemEntitys.get(position);
                        //MyOrderProcess parbean = (MyOrderProcess) getView().getTag();
                        ProvideViewInteractOrderTreatmentAndPatientInterrogation provideInteractOrderInfo = new ProvideViewInteractOrderTreatmentAndPatientInterrogation();
                        provideInteractOrderInfo.setOrderCode(parbean.getOrderCode());
                        // startActivity(new Intent(mActivity, TWJZ_JZJLActivity.class).putExtra("wzxx", parorder));
                        startActivity(new Intent(OrderCompletedFragment.this.getContext(), WDZS_WZXQActivity.class)
                                .putExtra("wzxx", provideInteractOrderInfo));
                        break;
                    case R.id.leave_btn:
                        MyOrderProcess myOrderProcess = (MyOrderProcess) mMultiItemEntitys.get(position);
                        currentMyOrderProcess=myOrderProcess;
                        mPresenter.sendSearchPatientOrderByCodeRequest(myOrderProcess.getOrderCode()
                                ,OrderCompletedFragment.this.getActivity());
                        break;
                    case R.id.opinion_btn:
                        MyOrderProcess myOrderProcess1 = (MyOrderProcess) mMultiItemEntitys.get(position);
                        currentMyOrderProcess=myOrderProcess1;
                        mPresenter.searchPatientMyOrderResCommentRequest(myOrderProcess1.getOrderCode()
                                ,OrderCompletedFragment.this.getActivity());
                        break;
                    case R.id.ll_item_root:
                        MyOrderProcess parbean1 = (MyOrderProcess) mMultiItemEntitys.get(position);
                        Bundle bundle=new Bundle();
                        bundle.putString("signCode",parbean1.getOrderCode());
                        bundle.putString("operDoctorCode",parbean1.getDoctorCode());
                        bundle.putString("operDoctorName",parbean1.getDoctorName());
                        startActivity(SignOrderDetialActivity.class,bundle);

                        break;
                        default:
                }
            }

            @Override
            public void onLongClick(int position, View view) {

            }

            @Override
            public void onClickLeaveMsg(int position) {

            }

            @Override
            public void onClickEvaluate(int position) {

            }

            @Override
            public void onClickCancelSucess(int position) {
                MyOrderProcess myOrderProcess = (MyOrderProcess) mMultiItemEntitys.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("mainDoctorCode",myOrderProcess.getDoctorCode());
                bundle.putString("mainDoctorName",myOrderProcess.getDoctorName());
                bundle.putInt("orderState",myOrderProcess.getFlagTreatmentState());
                bundle.putString("signNo",myOrderProcess.getSignNo());
                bundle.putString("orderId",myOrderProcess.getOrderCode());
                startActivity(CancelContractResultActivity.class,bundle);
            }
        });
    }

    /**
     * 设置适配器
     */
    private void setAdapter(){
        mRvList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mOrderComplatedAdapter=new OrderComplatedAdapter(this.getContext(),mMultiItemEntitys);
        mRvList.setAdapter(mOrderComplatedAdapter);
        SimpleDividerItemDecoration decor = new SimpleDividerItemDecoration(Objects.requireNonNull
                (getContext()), SimpleDividerItemDecoration.VERTICAL,true);
        decor.setDrawable(getResources().getDrawable(R.drawable.bg_shape_line));
        mRvList.addItemDecoration(decor);

    }

    @Override
    public void getSearchPatientMyOrderCompleteResult(List<MyOrderProcess> dataBeans) {
        if (pageIndex == 1) {
            mMultiItemEntitys.clear();
            mRefreshLayout.finishRefresh();
        }
        if (!CollectionUtils.isEmpty(dataBeans)) {
            mMultiItemEntitys.addAll(dataBeans);
            handleData(mMultiItemEntitys);
            mOrderComplatedAdapter.setDatas(mMultiItemEntitys);
            if (dataBeans.size()<pageSize) {
                mRefreshLayout.finishLoadMore();
            }
            mOrderComplatedAdapter.notifyDataSetChanged();
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
    public void getSearchPatientOrderByCodeResult(ZhlyDetailInfo zhlyDetailInfo) {
        if (null == zhlyDetailInfo.getFlagReplyState() || 1 == zhlyDetailInfo.getFlagReplyState() || 0 == zhlyDetailInfo.getFlagReplyState()) {
            Intent maintainintent = new Intent(mActivity, LeaveMessageActivity.class);
            maintainintent.putExtra("orderInfo", currentMyOrderProcess);
            maintainintent.putExtra("zhlyinfo", zhlyDetailInfo);
            mActivity.startActivity(maintainintent);
        } else if (2 == zhlyDetailInfo.getFlagReplyState() || 3 == zhlyDetailInfo.getFlagReplyState()) {
            Intent showintent = new Intent(mActivity, LeaveMessageShowActivity.class);
            showintent.putExtra("orderInfo", currentMyOrderProcess);
            showintent.putExtra("zhlyinfo", zhlyDetailInfo);
            mActivity.startActivity(showintent);
        }
    }

    @Override
    public void searchPatientCommentResult(CommentInfo commentInfo) {
        if (null != commentInfo&&commentInfo.getCommentId()!=0) {
            Intent showint = new Intent(mActivity, ShowCommentActivity.class);
            showint.putExtra("orderinfo", currentMyOrderProcess);
            showint.putExtra("commentinfo", commentInfo);
            mActivity.startActivity(showint);
        } else {
            Intent addint = new Intent(mActivity, CommentActivity.class);
            addint.putExtra("orderinfo", currentMyOrderProcess);
            mActivity.startActivity(addint);
        }
    }


    private void handleData(List<MultiItemEntity> mMultiItemEntitys){
        for (MultiItemEntity mMultiItemEntity : mMultiItemEntitys) {
            MyOrderProcess myOrderProcess =
                    (MyOrderProcess) mMultiItemEntity;
            Integer flagTreatmentState = myOrderProcess.getFlagTreatmentState();
            if (flagTreatmentState==4) {
                myOrderProcess.setItemType("2");
            }else{
                myOrderProcess.setItemType("1");
            }
//            myOrderProcess.setItemType(
//                    CommonMutipleGoOrderListItemType.MULTIPLE_CONTENT_SIGN_UP_TYPE);
        }
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
}
