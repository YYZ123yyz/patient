package www.patient.jykj_zxyl.myappointment.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;

/*
* 药品适配器
* */
public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_health,
                parent, false);
        return new HealthAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  name,dose,price;
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
