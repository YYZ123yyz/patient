package www.patient.jykj_zxyl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.ProvideInteractOrderInfo;
import entity.wdzs.ProvideInteractOrderPrescribe;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.Util;

/**
 *  我的医生 ==》就诊列表
 */
public class TWDYS_JZLBRecycleAdapter extends RecyclerView.Adapter<TWDYS_JZLBRecycleAdapter.ViewHolder> {
    public          List<ProvideInteractOrderInfo>                    datas = new ArrayList<>();
    private         OnItemClickListener             mOnItemClickListener;           //点击事件
    private         OnItemDeleteClickListener             mOnItemDeleteClickListener;           //删除

    private         Context                         mContext;


    public TWDYS_JZLBRecycleAdapter(List<ProvideInteractOrderInfo> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideInteractOrderInfo> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wdys_jzlb,viewGroup,false);
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

        viewHolder.tv_dhjz.setText(datas.get(position).getTreatmentTypeName());
        viewHolder.tv_ywc.setText("["+datas.get(position).getFlagOrderStateName()+"]");
        if (datas.get(position).getFlagOrderState() == 1)
            viewHolder.tv_ywc.setTextColor(mContext.getResources().getColor(R.color.btn_logout_pressed));
        else
            viewHolder.tv_ywc.setTextColor(mContext.getResources().getColor(R.color.groabColor));
        if (datas.get(position).getTreatmentDate() != null)
            viewHolder.tv_jzsj.setText("就诊时间："+ datas.get(position).getTreatmentDate());
        viewHolder.tv_zj.setText("总价："+datas.get(position).getServiceTotal());
        viewHolder.tv_sfk.setText("实付款："+datas.get(position).getActualPayment());


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

//        if (mOnItemDeleteClickListener != null)
//        {
//            viewHolder.mClickDeleteLinearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mOnItemDeleteClickListener.onClick(position);
//                }
//            });
//
//            viewHolder.mClickDeleteLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//
//                @Override
//                public boolean onLongClick(View view) {
//                    mOnItemDeleteClickListener.onLongClick(position);
//                    return false;
//                }
//            });


//        }


    }
        //获取数据的数量
        @Override
        public int getItemCount(){

        return datas.size();
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
//            public TextView mClickDeleteLinearLayout;                     //删除
            public TextView     tv_dhjz;
            public TextView     tv_ywc;
            public TextView     tv_jzsj;
            public TextView     tv_zj;
            public TextView     tv_sfk;
//

            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.clickLayout);
//                mClickDeleteLinearLayout = (TextView) view.findViewById(R.id.tv_delete);
                tv_dhjz = (TextView)view.findViewById(R.id.tv_dhjz);
                tv_ywc = (TextView)view.findViewById(R.id.tv_ywc);
                tv_jzsj = (TextView)view.findViewById(R.id.tv_jzsj);
                tv_zj = (TextView)view.findViewById(R.id.tv_zj);
                tv_sfk = (TextView)view.findViewById(R.id.tv_sfk);

            }
        }



    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    public interface OnItemDeleteClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnDeleteItemClickListener(OnItemDeleteClickListener onItemDeleteClickListener ){
        this.mOnItemDeleteClickListener=onItemDeleteClickListener;
    }

}