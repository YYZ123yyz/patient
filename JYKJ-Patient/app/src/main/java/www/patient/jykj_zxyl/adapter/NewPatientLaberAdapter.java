package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import entity.mySelf.ProvidePatientLabel;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.RedeemRecordActivity;
import www.patient.jykj_zxyl.util.Util;

import java.util.ArrayList;
import java.util.List;

public class NewPatientLaberAdapter extends RecyclerView.Adapter<NewPatientLaberAdapter.ViewHolder> {
    public List<ProvidePatientLabel> datas = new ArrayList<>();
    private RedeemRecordActivity mActivity;
    private NewPatientLaberAdapter.OnItemClickListener mOnItemClickListener;
    private Context mContext;


    public NewPatientLaberAdapter(List<ProvidePatientLabel> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvidePatientLabel> list){
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public NewPatientLaberAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_patientlaber_patientlabers,viewGroup,false);
        NewPatientLaberAdapter.ViewHolder vh = new NewPatientLaberAdapter.ViewHolder(view);
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
    public void onBindViewHolder(NewPatientLaberAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.mCreateDate.setText(Util.dateToStr(datas.get(position).getCreateDate()));
        viewHolder.mLaberName.setText(datas.get(position).getUserLabelSecondName());
    }
    //获取数据的数量
    @Override
    public int getItemCount(){
        if (datas == null )
            return 0;
        return datas.size();
    }




    //自定义的ViewHolder，持有每个Item的的所有界面元素

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
        public TextView mCreateDate;                                //创建日期
        public TextView     mLaberName;                             //标签名


        public ViewHolder(View view){
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            mCreateDate = (TextView)view.findViewById(R.id.tv_activityPatientLaber_createDate);
            mLaberName = (TextView)view.findViewById(R.id.tv_activityPatientLaber_laberName);
        }
    }



    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(NewPatientLaberAdapter.OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }
}
