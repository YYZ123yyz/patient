package www.patient.jykj_zxyl.adapter.myself;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.basicDate.ProvideBasicsDomain;
import entity.patientInfo.ProvidePatientLabel;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.myself.RedeemRecordActivity;
import www.patient.jykj_zxyl.util.Util;

/**
 * 症状信息选择适配器
 */
public class ZZXXChoiceAdapter extends RecyclerView.Adapter<ZZXXChoiceAdapter.ViewHolder> {
    public          List<ProvideBasicsDomain>            datas = new ArrayList<>();
    private         RedeemRecordActivity            mActivity;
    private         OnItemClickListener             mOnItemClickListener;
    private         Context                         mContext;


    public ZZXXChoiceAdapter(List<ProvideBasicsDomain> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideBasicsDomain> list){
        datas = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_zzxx_choice,viewGroup,false);
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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tv_zzxx_mc.setText(datas.get(position).getAttrName());
        if (datas.get(position).isChoice())
            viewHolder.iv_zzxx_choice.setBackgroundResource(R.mipmap.zzxx_choice_c);
        else
            viewHolder.iv_zzxx_choice.setBackgroundResource(R.mipmap.zzxx_choice);
        viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(position);
            }
        });
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
        public TextView     tv_zzxx_mc;                                //症状信息
        public ImageView     iv_zzxx_choice;                             //选择图标


        public ViewHolder(View view){
            super(view);
            mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_clickLayout);
            tv_zzxx_mc = (TextView)view.findViewById(R.id.tv_zzxx_mc);
            iv_zzxx_choice = (ImageView)view.findViewById(R.id.iv_zzxx_choice);
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