package www.patient.jykj_zxyl.adapter.myself;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.mySelf.JwbsYstxInfo;
import entity.patientInfo.ProvidePatientConditionDiseaseRecord;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.RedeemRecordActivity;
import www.patient.jykj_zxyl.util.DateTimeUtils;
import www.patient.jykj_zxyl.util.DateUtils;
import www.patient.jykj_zxyl.util.StrUtils;
import www.patient.jykj_zxyl.util.Util;

/**
 * 建档档案 == 》 既往病史 ==》 医生填写
 */
public class JDDA_JWBS_YSTXAdapter extends RecyclerView.Adapter<JDDA_JWBS_YSTXAdapter.ViewHolder> {
    public          List<JwbsYstxInfo>            datas = new ArrayList<>();
    private         RedeemRecordActivity            mActivity;
    private         OnItemClickListener             mOnItemClickListener;
    private         Context                         mContext;


    public JDDA_JWBS_YSTXAdapter(List<JwbsYstxInfo> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<JwbsYstxInfo> list){
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_jdda_jwbs_ystx,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作

    /**
     * 展示数据
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
      //  viewHolder.mBM.setText(datas.get(position).getMedicalTypeName());
        viewHolder.tv_record_doctor.setText("记录人："+StrUtils.defaultStr(datas.get(position).getDoctorName()));
        if(null!=datas.get(position).getCreateDate()) {
            viewHolder.mDate.setText(DateTimeUtils.formatLongDate(datas.get(position).getCreateDate(),"yyyy-MM-dd HH:mm"));

        }
//        viewHolder.mType.setText(datas.get(position).getRecordTypeName());
        String parcon = StrUtils.defaultStr(datas.get(position).getTreatmentPlanCode());
        if(parcon.length()==0){
            parcon = "未填写";
        }
        viewHolder.mContent.setText(parcon);

        if (mOnItemClickListener != null)
        {
            viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });

            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

    }
    //获取数据的数量
    @Override
    public int getItemCount(){
        if (datas == null )
            return 0;
        return datas.size();
        //return 10;
    }




    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public TextView     mBM;                                //别名
        public TextView     mDate;                             //时间
        public TextView     mType;                             //类型
        public TextView     mContent;                             //内容
        public TextView  tv_record_doctor;

        public ViewHolder(View view){
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
        //    mBM = (TextView)view.findViewById(R.id.tv_activityPatientJWBS_bm);
            mDate = (TextView)view.findViewById(R.id.tv_activityPatientLaber_createDate);
            mType = (TextView)view.findViewById(R.id.tv_activityPatientLaber_laberName);
            mContent = (TextView)view.findViewById(R.id.tv_activityPatientLaber_content);
            tv_record_doctor = view.findViewById(R.id.tv_record_doctor);
        }
    }



    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}
