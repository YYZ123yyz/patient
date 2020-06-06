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
import entity.ProvideInteractOrderPrescribe;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.Util;

/**
 *  我的医生 ==》就诊记录==处方签
 */
public class WDYS_JZJL_CFQRecycleAdapter extends RecyclerView.Adapter<WDYS_JZJL_CFQRecycleAdapter.ViewHolder> {
    public          List<ProvideInteractOrderPrescribe>                    datas = new ArrayList<>();
    private         OnItemClickListener             mOnItemClickListener;           //点击事件
    private         OnItemDeleteClickListener             mOnItemDeleteClickListener;           //删除

    private         Context                         mContext;


    public WDYS_JZJL_CFQRecycleAdapter(List<ProvideInteractOrderPrescribe> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideInteractOrderPrescribe> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wdyscfq,viewGroup,false);
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

        viewHolder.tv_ypmc.setText(datas.get(position).getDrugName());
        viewHolder.tv_cflx.setText(datas.get(position).getPrescribeTypeName());
        viewHolder.tv_gmsl.setText("购买数量："+datas.get(position).getDrugAmountName());
        viewHolder.tv_yysl.setText("用药数量："+datas.get(position).getUseNumName());

        viewHolder.tv_fypl.setText("服用频率："+datas.get(position).getUseFrequency()+"次/天");

        viewHolder.tv_fyzq.setText("服用周期："+datas.get(position).getUseCycle()+"天");
        viewHolder.tv_sm.setText(datas.get(position).getUseDesc());


//        if (mOnItemClickListener != null)
//        {
//            viewHolder.mClickLinearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mOnItemClickListener.onClick(position);
//                }
//            });
//
//            viewHolder.mClickLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//
//                @Override
//                public boolean onLongClick(View view) {
//                    mOnItemClickListener.onLongClick(position);
//                    return false;
//                }
//            });
//
//
//        }

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
//        return 10;
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
//            public TextView mClickDeleteLinearLayout;                     //删除
            public TextView     tv_ypmc;                                   //药品名称
            public TextView     tv_cflx;                                  //处方类型

            public TextView     tv_gmsl;                                    //购买数量
            public TextView     tv_yysl;                                    //用药数量
            public TextView     tv_fypl;                                    //服用频率
            public TextView     tv_fyzq;                                    //服用周期
            public TextView     tv_sm;                                    //补充说明

//

            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.clickLayout);
//                mClickDeleteLinearLayout = (TextView) view.findViewById(R.id.tv_delete);
                tv_ypmc = (TextView)view.findViewById(R.id.tv_ypmc);
                tv_cflx = (TextView)view.findViewById(R.id.tv_cflx);
                tv_gmsl = (TextView)view.findViewById(R.id.tv_gmsl);
                tv_yysl = (TextView)view.findViewById(R.id.tv_yysl);
                tv_fyzq = (TextView)view.findViewById(R.id.tv_fyzq);
                tv_sm = (TextView)view.findViewById(R.id.tv_sm);
                tv_fypl = (TextView)view.findViewById(R.id.tv_fypl);


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