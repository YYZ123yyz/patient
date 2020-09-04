package www.patient.jykj_zxyl.myappointment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;

public class MyAdapter extends BaseAdapter {
    private List<BaseReasonBean> mList;
    private Context mContext;

    public MyAdapter(Context pContext, List<BaseReasonBean> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 下面是重要代码
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
        convertView = _LayoutInflater.inflate(R.layout.item_custom, null);
        if (convertView != null) {
            TextView name = convertView.findViewById(R.id.name);
            name.setText(mList.get(position).getAttrName());
        }
        return convertView;
    }
}