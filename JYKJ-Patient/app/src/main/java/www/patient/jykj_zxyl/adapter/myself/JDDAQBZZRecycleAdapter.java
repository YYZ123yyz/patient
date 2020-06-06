package www.patient.jykj_zxyl.adapter.myself;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import entity.ProvideBasicsImg;
import entity.basicDate.ProvideBasicsDomain;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.myself.ZZXXActivity;
import www.patient.jykj_zxyl.activity.myself.MyOrderActivity;

/**
 * 建档档案 ==》症状信息
 */
public class JDDAQBZZRecycleAdapter extends RecyclerView.Adapter<JDDAQBZZRecycleAdapter.ViewHolder> {
    public List<ProvideBasicsDomain> datas = new ArrayList<>();
    private ZZXXActivity mActivity;
    private         OnItemClickListener     mOnItemClickListener;
    private         Context                 mContext;


    public JDDAQBZZRecycleAdapter(List<ProvideBasicsDomain> list, Context context, ZZXXActivity mainActivity){
        mContext = context;
        datas = list;
        mActivity = mainActivity;
    }

    //重新设置数据
    public void setDate(List<ProvideBasicsDomain> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_zzxx_qbzz_al,viewGroup,false);
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
        viewHolder.tv_text.setText(datas.get(position).getAttrName());
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
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件
            public TextView tv_text;                                //类型

            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.item_fragmentYLZX_rmjxLayout);
                tv_text = (TextView)view.findViewById(R.id.tv_text);
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