package www.patient.jykj_zxyl.myappointment.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;

/**
 * 临床诊断适配器
 */
public class Item_ClinicalAdapter   extends RecyclerView.Adapter<Item_ClinicalAdapter.ViewHolder>{


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itme_clinical,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView item_tv;
        public ViewHolder(View itemView) {
            super(itemView);
            item_tv = itemView.findViewById(R.id.item_tv);
        }
    }
}
