package www.patient.jykj_zxyl.myappointment.fragment;

import android.content.Context;
import android.content.Intent;
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
import www.patient.jykj_zxyl.myappointment.activity.AncelAppointmentActivity;
import www.patient.jykj_zxyl.myappointment.adapter.Fragment_VisitingAdapter;

public class FragmentVisiting extends Fragment {
    Unbinder unbinder;
    private Context mContext;
    private Handler mHandler;
    private MyOrderActivity mActivity;
    private JYKJApplication mApp;
    private LinearLayoutManager layoutManager;
    private Fragment_VisitingAdapter fragment_visitingAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_visiting, container, false);
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
//        RecyclerView rvNo=v.findViewById(R.id.rv_no);
//        //创建默认的线性LayoutManager
//        layoutManager = new LinearLayoutManager(mContext);
//        layoutManager.setOrientation(LinearLayout.VERTICAL);
//        rvNo.setLayoutManager(layoutManager);
//        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        rvNo.setHasFixedSize(true);

       //取消预约的点击事件
        fragment_visitingAdapter.setOnItemClickXYListener(new Fragment_VisitingAdapter.OnItemClickXYListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), AncelAppointmentActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
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
