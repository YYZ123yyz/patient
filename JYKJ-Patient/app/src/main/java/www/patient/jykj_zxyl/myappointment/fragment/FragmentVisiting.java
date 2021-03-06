package www.patient.jykj_zxyl.myappointment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.patient.WDYS_JZJL_WZZLActivity;
import www.patient.jykj_zxyl.activity.home.twjz.InquiryDataActivity;
import www.patient.jykj_zxyl.activity.myself.order.activity.RefundActivity;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.base.base_bean.MyReservationListBean;
import www.patient.jykj_zxyl.base.mvp.AbstractMvpBaseFragment;
import www.patient.jykj_zxyl.myappointment.Contract.ReservationListContract;
import www.patient.jykj_zxyl.myappointment.Presenter.ResevationListPresenter;
import www.patient.jykj_zxyl.myappointment.activity.CancelAppointmentActivity;
import www.patient.jykj_zxyl.myappointment.activity.MedicalRecordActivity;
import www.patient.jykj_zxyl.myappointment.adapter.Fragment_VisitingAdapter;
import www.patient.jykj_zxyl.util.DateUtils;

public class FragmentVisiting extends AbstractMvpBaseFragment <ReservationListContract.View,
        ResevationListPresenter> implements ReservationListContract.View {
    private JYKJApplication mApp;
    private LinearLayoutManager layoutManager;
    private Fragment_VisitingAdapter fragment_visitingAdapter;
    private RecyclerView rvNo;
    private String reserveStatus;
    private String reserveCode;
    private List<MyReservationListBean> list;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_visiting;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        list = new ArrayList<>();
        mApp = (JYKJApplication) getActivity().getApplication();
        rvNo = view.findViewById(R.id.rv_no);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(getActivity());
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
        list.clear();
        mPresenter.sendMyReservationListRequest(mApp.loginDoctorPosition,mApp.mProvideViewSysUserPatientInfoAndRegion.getPatientCode(),mApp.mProvideViewSysUserPatientInfoAndRegion.getUserName(),"1","10","1");
    }

    @Override
    public void getMyReservationListResult(List<MyReservationListBean> myReservationListBeans) {
           if(myReservationListBeans!=null){
               list.addAll(myReservationListBeans);
               fragment_visitingAdapter = new Fragment_VisitingAdapter(list, getContext());
               rvNo.setAdapter(fragment_visitingAdapter);

               for (MyReservationListBean myReservationListBean : myReservationListBeans) {
                   reserveStatus = myReservationListBean.getReserveStatus();
                   reserveCode = myReservationListBean.getReserveCode();

               }
               fragment_visitingAdapter.setOnInquiryListen(new Fragment_VisitingAdapter.OnInquiryDataClick() {
                   @Override
                   public void onClick(int position) {
                       Intent intent = new Intent(getActivity(), InquiryDataActivity.class);
                       intent.putExtra("order",myReservationListBeans.get(position).getOrderCode());
                       intent.putExtra("operPatientCode",myReservationListBeans.get(position).getMainPatientCode());
                       intent.putExtra("operPatientName",myReservationListBeans.get(position).getMainPatientName());
                       intent.putExtra("treatmentType",String.valueOf(myReservationListBeans.get(position).getTreatmentType()));
                       intent.putExtra("doctorCode",String.valueOf(myReservationListBeans.get(position).getMainDoctorCode()));
                       intent.putExtra("doctorName",String.valueOf(myReservationListBeans.get(position).getMainDoctorName()));
                       startActivity(intent);
                   }
               });




               if(!TextUtils.isEmpty(reserveStatus)){
                   if(reserveStatus.equals("10")){
                       fragment_visitingAdapter.setOnItemClickXYListener(new Fragment_VisitingAdapter.OnItemClickXYListener() {
                           @Override
                           public void onClick(int position) {
                               Bundle bundle=new Bundle();
                               bundle.putString("reserveCode",reserveCode);
                               bundle.putString("doctorName",myReservationListBeans.get(position).getMainDoctorName());
                               bundle.putString("doctorCode",myReservationListBeans.get(position).getMainDoctorCode());
                               bundle.putString("doctorUrl",myReservationListBeans.get(position).getDoctorLogoUrl());
                               //订单编号
                               bundle.putString("SignCode",myReservationListBeans.get(position).getReserveCode());
                               long reserveConfigStart = myReservationListBeans.get(position).getReserveConfigStart();
                               String s = DateUtils.getStringTimeOfSSS(reserveConfigStart);
                               //预约时间
                               bundle.putString("Appointment",s);
                               //结束时间
                               long reserveConfigEnd = myReservationListBeans.get(position).getReserveConfigEnd();
                               String ss = DateUtils.stampToDate(reserveConfigEnd);
                               bundle.putString("endTime",ss);
                               //预约项目
                               bundle.putString("class",myReservationListBeans.get(position).getReserveProjectName());
                               int treatmentType = myReservationListBeans.get(position).getTreatmentType();
                               if(treatmentType==1){
                                   bundle.putString("type","一次性就诊");
                               }else {
                                   bundle.putString("type","签约就诊");
                               }
                               startActivity(CancelAppointmentActivity.class,bundle,100);
                           }

                           @Override
                           public void onLongClick(int position) {

                           }
                       });
//                       fragment_visitingAdapter.setmOnItemClickDataListener(new Fragment_VisitingAdapter.OnItemClickDataListener() {
//                           @Override
//                           public void onClick(int position) {
////                               Intent intent = new Intent(getContext(), AncelAppointmentActivity.class);
////                               intent.putExtra("reserveCode",reserveCode);
////                               startActivity(intent);
//                           }
//                       });
                   }
                   else if(reserveStatus.equals("20")||reserveStatus.equals("30")){

                   }
               }


               fragment_visitingAdapter.setOnItemClickZXListener(new Fragment_VisitingAdapter.OnItemClickZXListener() {
                   @Override
                   public void onClick(int position) {
                       if (myReservationListBeans.get(position).getIsGenerateMedicalRecord() ==1){
                           String reserveCode = myReservationListBeans.get(position).getOrderCode();
                           Intent intent = new Intent(getContext(), MedicalRecordActivity.class);
                           intent.putExtra("reserveCode",reserveCode);
                           startActivity(intent);
                       }else {
                           ToastUtils.showShort("暂未生成病历!");
                       }

                   }

                   @Override
                   public void onLongClick(int position) {

                   }
               });
//               fragment_visitingAdapter.setmOnItemClickListener(new Fragment_VisitingAdapter.OnItemClickListener() {
//                   @Override
//                   public void onClick(int position) {
//                       Intent intent = new Intent(getContext(), OrderDetialActivity.class);
//                       startActivity(intent);
//                   }
//
//                   @Override
//                   public void onLongClick(int position) {
//
//                   }
//               });

           }
    }
}
