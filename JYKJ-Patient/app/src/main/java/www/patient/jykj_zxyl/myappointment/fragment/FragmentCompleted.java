package www.patient.jykj_zxyl.myappointment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.custom.SwipeRecyclerView;
import www.patient.jykj_zxyl.myappointment.adapter.Fragment_CompletedAdapter;

public class FragmentCompleted extends Fragment {

    @BindView(R.id.rv_no)
    SwipeRecyclerView rvNo;
    Unbinder unbinder;
    private Context mContext;
    private Handler mHandler;
    private MyOrderActivity mActivity;
    private JYKJApplication mApp;
    private LinearLayoutManager layoutManager;
    private Fragment_CompletedAdapter fragment_completedAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_completed, container, false);
        mContext = getContext();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        //  mHZEntyties = new ArrayList<>();
        setData();
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    private void initLayout(View v) {
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        rvNo.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvNo.setHasFixedSize(true);


    }

    private void initHandler() {

    }

    private void setData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
