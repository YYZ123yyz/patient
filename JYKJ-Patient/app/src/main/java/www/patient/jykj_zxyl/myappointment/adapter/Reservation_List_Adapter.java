package www.patient.jykj_zxyl.myappointment.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allin.commlibrary.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.ReservePatientListBean;
import www.patient.jykj_zxyl.util.DateUtils;

/*
 *
 * */
public class Reservation_List_Adapter extends RecyclerView.Adapter<Reservation_List_Adapter.ViewHolder> {
    private List<ReservePatientListBean> datas;
    private onClickListener onClickListener;
    int position ;
    public Reservation_List_Adapter(List<ReservePatientListBean> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation,
                parent, false);
        return new Reservation_List_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ReservePatientListBean reservePatientListBean = datas.get(position);
        long startTimes = reservePatientListBean.getStartTimes();
        long endTimes = reservePatientListBean.getEndTimes();
        String start = DateUtils.getStringTime1(startTimes);
        String end = DateUtils.getStringTime1(endTimes);
        holder.item_title.setText(reservePatientListBean.getViewTimesPeriod() + " "
                + start + "-" + " " + end + reservePatientListBean.getSourceTypeName());

        holder.item_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v, position);
            }
        });

        Item_Reservation_List_Adapter item_reservation_list_adapter = new Item_Reservation_List_Adapter(datas.get(position).getItemTimes());
        holder.item_rv.setLayoutManager(new GridLayoutManager(holder.item_rv.
                getContext(), 3, GridLayoutManager.VERTICAL, false));
        item_reservation_list_adapter.setDate(datas.get(position).getItemTimes());
        holder.item_rv.setAdapter(item_reservation_list_adapter);
        item_reservation_list_adapter.setOnClickListener(new Item_Reservation_List_Adapter.onClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view, int pos) {
                setChoosedTimes(datas,position,pos);
                ReservePatientListBean.ItemTimesBean itemTimesBean = datas.get(position).getItemTimes().get(pos);
                ReservePatientListBean reservePatientListBean1 = datas.get(position);
                if (onClickListener!=null) {
                    onClickListener.onClickChoosedItem(itemTimesBean);
                    onClickListener.onClickChoosedReseveCode(reservePatientListBean1);
                }
                item_reservation_list_adapter.setDate(datas.get(position).getItemTimes());
                item_reservation_list_adapter.notifyDataSetChanged();
                Reservation_List_Adapter.this.notifyDataSetChanged();
            }
        });
    }
    private void setChoosedTimes( List<ReservePatientListBean> datas,int pos,int childPos){
        if (!CollectionUtils.isEmpty(datas)) {
            for (int i = 0; i < datas.size(); i++) {
                if(pos==i){
                    ReservePatientListBean reservePatientListBean = datas.get(i);
                    List<ReservePatientListBean.ItemTimesBean> itemTimes = reservePatientListBean.getItemTimes();
                    for (int j = 0; j< itemTimes.size(); j++) {
                        if(childPos==j){
                            itemTimes.get(j).setSelectState(Boolean.TRUE);
                        }else{
                            itemTimes.get(j).setSelectState(Boolean.FALSE);
                        }
                    }
                }else{
                    ReservePatientListBean reservePatientListBean = datas.get(i);
                    if(reservePatientListBean!=null){
                        List<ReservePatientListBean.ItemTimesBean> itemTimes = reservePatientListBean.getItemTimes();
                        if(itemTimes!=null){
                            for (ReservePatientListBean.ItemTimesBean itemTime : itemTimes) {
                                itemTime.setSelectState(Boolean.FALSE);
                            }
                        }

                    }

                }
            }

        }
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //重新设置数据
    public void setDate(List<ReservePatientListBean> list) {
        datas = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item_title;
        private RecyclerView item_rv;
        private LinearLayout item_lin;

        public ViewHolder(View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            item_rv = itemView.findViewById(R.id.item_rv);
            item_lin = itemView.findViewById(R.id.item_lin);
        }
    }
    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface onClickListener {
        void onClick(View view, int position);

        void onClickChoosedItem( ReservePatientListBean.ItemTimesBean itemTimesBean);
        void onClickChoosedReseveCode(ReservePatientListBean reservePatientListBean);
    }
}
