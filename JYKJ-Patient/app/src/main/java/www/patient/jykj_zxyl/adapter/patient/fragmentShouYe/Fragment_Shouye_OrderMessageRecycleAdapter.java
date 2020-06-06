package www.patient.jykj_zxyl.adapter.patient.fragmentShouYe;

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

import entity.HZIfno;
import entity.ProvideInteractOrderInfo;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;
import www.patient.jykj_zxyl.fragment.shouye.FragmentShouYe_DDXX;
import www.patient.jykj_zxyl.util.Util;

/**
 * 首页 == 》订单消息
 */
public class Fragment_Shouye_OrderMessageRecycleAdapter extends RecyclerView.Adapter<Fragment_Shouye_OrderMessageRecycleAdapter.ViewHolder> {
    public List<ProvideInteractOrderInfo> datas = new ArrayList<>();
    private FragmentShouYe_DDXX mFragmentShouYe_DDXX;
    private         OnItemClickListener     mOnItemClickListener;
    private         Context                 mContext;


    public Fragment_Shouye_OrderMessageRecycleAdapter(List<ProvideInteractOrderInfo> list, Context context, FragmentShouYe_DDXX mainActivity){
        mContext = context;
        datas = list;
        mFragmentShouYe_DDXX = mainActivity;
    }

    //重新设置数据
    public void setDate(List<ProvideInteractOrderInfo> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_ordermessage,viewGroup,false);
                ViewHolder vh = new ViewHolder(view);
                return vh;
       }
        //将数据与界面进行绑定的操作

    /**
     * 如果是第一条数据，那么他肯定是该组的第一个用户，所以显示组别
     * 如果该用户是该组的第一个用户，那么就显示组别
     * 否则不再显示
     *
     *
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tv_jzlx.setText(datas.get(position).getTreatmentTypeName());
        switch (datas.get(position).getFlagOrderState())
        {
            case 0:
                viewHolder.tv_zfzt.setTextColor(mContext.getResources().getColor(R.color.textColor_hztltabyj));
                break;
            case 1:
                viewHolder.tv_zfzt.setTextColor(mContext.getResources().getColor(R.color.textColor_hztltabyj));
                break;
                default:
                    viewHolder.tv_zfzt.setTextColor(mContext.getResources().getColor(R.color.groabColor));
                    break;
        }
        viewHolder.tv_zfzt.setText("["+datas.get(position).getFlagOrderStateName()+"]");
        if(datas.get(position).getOrderDate() != null)
            viewHolder.tv_zfsj.setText(Util.dateToStr(datas.get(position).getOrderDate()));
        else
            viewHolder.tv_zfsj.setText("未设置");
        viewHolder.tv_xxnr.setText(datas.get(position).getOrderShowContent());
        if (datas.get(position).getFlagPayBtn() == 0)
        {
            viewHolder.tv_zf.setVisibility(View.GONE);
            viewHolder.iv_sc.setVisibility(View.GONE);
        }
        else if (datas.get(position).getFlagPayBtn() == 1) {
            viewHolder.tv_zf.setVisibility(View.VISIBLE);
            viewHolder.iv_sc.setVisibility(View.VISIBLE);
        }
        viewHolder.iv_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentShouYe_DDXX.deleteOrderMessage(position);
            }
        });
        viewHolder.tv_zf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentShouYe_DDXX.zhifuOrderMessage(position);
            }
        });
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

        return datas.size();
//        return 10;
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
            public TextView tv_jzlx;                                //就诊类型
            public TextView tv_zfzt;                                  //就诊状态
            public TextView tv_zfsj;                                  //支付时间
            public TextView tv_xxnr;                               //消息内容
            public ImageView iv_sc;                               //删除
            public TextView tv_zf;                               //支付


            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.item_fragmentYLZX_rmjxLayout);

                tv_jzlx = (TextView)view.findViewById(R.id.tv_jzlx);
                tv_zfzt = (TextView)view.findViewById(R.id.tv_zfzt);
                tv_zfsj = (TextView)view.findViewById(R.id.tv_zfsj);
                tv_xxnr = (TextView)view.findViewById(R.id.tv_xxnr);
                iv_sc = (ImageView)view.findViewById(R.id.iv_sc);
                tv_zf = (TextView)view.findViewById(R.id.tv_zf);

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