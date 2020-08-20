package www.patient.jykj_zxyl.fragment.myself;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import entity.HZIfno;
import entity.mySelf.ProvideInteractOrderInfo;
import entity.mySelf.conditions.QueryOrder;
import netService.HttpNetService;
import netService.entity.NetRetEntity;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.activity.ylzx.YLZXWebActivity;
import www.patient.jykj_zxyl.adapter.RMJXRecycleAdapter;
import www.patient.jykj_zxyl.adapter.myself.MyOrderNoRecycleAdapter;
import www.patient.jykj_zxyl.application.Constant;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.custom.OnRecyclerItemClickListener;
import www.patient.jykj_zxyl.custom.SwipeRecyclerView;
import www.patient.jykj_zxyl.util.IConstant;
import www.patient.jykj_zxyl.util.INetAddress;

import android.widget.AbsListView.OnScrollListener;

/**
 * 我的订单未完成
 * Created by admin on 2016/6/1.
 */
public class FragmentMyOrderNo extends Fragment {
    private Context mContext;
    private Handler mHandler;
    private MyOrderActivity mActivity;
    private JYKJApplication mApp;

    private SwipeRecyclerView mRMJXRecycleView;              //热门精选列表
    private LinearLayoutManager layoutManager;
    private MyOrderNoRecycleAdapter mAdapter;       //适配器
    private List<ProvideInteractOrderInfo> mHZEntyties = new ArrayList<>();            //所有数据
    private List<ProvideInteractOrderInfo> mHZEntytiesClick = new ArrayList<>();            //点击之后的数据
    private int pageno = 1;
    private int lastVisibleIndex = 0;
    private LoadDataTask loadDataTask = null;
    private boolean mLoadDate = true;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myorder_no, container, false);
        mContext = getContext();
        mActivity = (MyOrderActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        mHZEntyties = new ArrayList<>();
        setData();
        return v;
    }


    /**
     * 初始化界面
     */
    private void initLayout(View view) {
        mRMJXRecycleView = (SwipeRecyclerView) view.findViewById(R.id.rv_no);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        mRMJXRecycleView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRMJXRecycleView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new MyOrderNoRecycleAdapter(mHZEntyties, mContext, mActivity);
        mRMJXRecycleView.setAdapter(mAdapter);
        /*mAdapter.setOnItemClickListener(new MyOrderNoRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext,YLZXWebActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });*/
        initRecycleView();
        mRMJXRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLoadDate) {
                        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                        if (lastVisiblePosition >= layoutManager.getItemCount() - 1) {
                            pageno++;
                            setData();
                        }
                    }
                }
            }
        });
    }

    void initRecycleView() {
        mRMJXRecycleView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRMJXRecycleView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                //Toast.makeText(mContext, datas.get(vh.getLayoutPosition()).getTitle(), Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent();
                intent.setClass(mContext,YLZXWebActivity.class);
                startActivity(intent);*/
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //判断被拖拽的是否是前两个，如果不是则执行拖拽
                if (vh.getLayoutPosition() != 0 && vh.getLayoutPosition() != 1) {
                    mItemTouchHelper.startDrag(vh);

                    //获取系统震动服务
                    Vibrator vib = (Vibrator) mActivity.getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
                    vib.vibrate(70);

                }
            }
        });
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            /**
             * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，如果是网格类RecyclerView则还应该多有LEFT和RIGHT
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
//                    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mHZEntyties, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mHZEntyties, i, i - 1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//                myAdapter.notifyItemRemoved(position);
//                datas.remove(position);
            }

            /**
             * 重写拖拽可用
             * @return
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            /**
             * 长按选中Item的时候开始调用
             *
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 手指松开的时候还原
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }
        });

        mItemTouchHelper.attachToRecyclerView(mRMJXRecycleView);

        mRMJXRecycleView.setRightClickListener(new SwipeRecyclerView.OnRightClickListener() {
            @Override
            public void onRightClick(int position, String id) {
                mHZEntyties.remove(position);
//                myAdapter.notifyItemRemoved(position);
                mAdapter.notifyDataSetChanged();
                //Toast.makeText(mContext, " position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        break;
                }
            }
        };
    }


    class ButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {


            }
        }
    }

    /**
     * 设置数据
     */
    private void setData() {
        /*for (int i = 0; i < 40; i++)
        {
            HZIfno hzIfno = new HZIfno();
            if (i%3 == 0)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(1);
            }
            if (i%3 == 1)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(-1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(2);
            }
            if (i%3 == 2)
            {
                hzIfno.setHzName("测试名"+i);
                hzIfno.setHzAge((30+(i%3))+"");
                hzIfno.setHzSex(1);
                hzIfno.setLaber("患者标签：高血压I期");
                hzIfno.setState(3);
            }
            mHZEntyties.add(hzIfno);
        }*/
        //mAdapter.setDate(mHZEntyties);
        //mAdapter.notifyDataSetChanged();
        //if(null==loadDataTask){
        QueryOrder queorder = new QueryOrder();
        queorder.setPageNum(String.valueOf(pageno));
        queorder.setLoginPatientPosition(mApp.loginDoctorPosition);
        queorder.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
        queorder.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
        queorder.setRequestClientType("1");
        queorder.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
        loadDataTask = new LoadDataTask(queorder);
        loadDataTask.execute();
        //}
    }

    /*@Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(null==loadDataTask){
            QueryOrder queorder = new QueryOrder();
            queorder.setPageNum(String.valueOf(pageno));
            queorder.setLoginPatientPosition(mApp.loginDoctorPosition);
            queorder.setOperPatientCode(mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode());
            queorder.setOperPatientName(mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName());
            queorder.setRequestClientType("1");
            queorder.setRowNum(String.valueOf(IConstant.PGAE_SIZE));
            loadDataTask = new LoadDataTask(queorder);
            loadDataTask.execute();
        }else{
            pageno = pageno + 1;
            loadDataTask.execute();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
    }*/

    class LoadDataTask extends AsyncTask<Void, Void, List<ProvideInteractOrderInfo>> {
        private QueryOrder queryOrder;

        public LoadDataTask(QueryOrder queryOrder) {
            this.queryOrder = queryOrder;
        }

        @Override
        protected List<ProvideInteractOrderInfo> doInBackground(Void... voids) {
            mLoadDate = false;
            List<ProvideInteractOrderInfo> retlist = new ArrayList();
            queryOrder.setPageNum(String.valueOf(pageno));
            try {
                String retnetstr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(queryOrder), Constant.SERVICEURL + INetAddress.QUERY_UNIMPLEMENT_ORDER_URL);
                NetRetEntity retnetbean = JSON.parseObject(retnetstr, NetRetEntity.class);
                if (1 == retnetbean.getResCode() && !TextUtils.isEmpty(retnetbean.getResJsonData()) && retnetbean.getResJsonData().length() > 3) {
                    retlist = JSON.parseArray(retnetbean.getResJsonData(), ProvideInteractOrderInfo.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return retlist;
        }

        @Override
        protected void onPostExecute(List<ProvideInteractOrderInfo> hzIfnos) {
            if (hzIfnos.size() > 0) {
                mHZEntyties.addAll(hzIfnos);
                mAdapter.setDate(mHZEntyties);
                mAdapter.notifyDataSetChanged();
            } else {
                if (pageno > 1) {
                    pageno = pageno - 1;
                }
            }
            mLoadDate = true;
        }
    }
}
