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

import www.patient.jykj_zxyl.util.Util;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.wdzs.ProvideViewInteractOrderTreatmentAndPatientInterrogation;
import www.patient.jykj_zxyl.util.Util;

/**
 *  图文就诊未完成
 */
public class TWJZNoFinishRecycleAdapter extends RecyclerView.Adapter<TWJZNoFinishRecycleAdapter.ViewHolder> {
    public          List<ProvideViewInteractOrderTreatmentAndPatientInterrogation>                    datas = new ArrayList<>();
    private         OnItemClickListener             mOnItemClickListener;           //用户资料点击事件

    private         OnKJCFItemClickListener         mKJCFOnItemClickListener;          //开具处方点击事件
    private         OnWZXXItemClickListener         mOnWZXXItemClickListener;          //开具处方点击事件
    private         OnHYHDItemClickListener         mOnHYHDItemClickListener;          //开具处方点击事件
    private         OnZHLYItemClickListener         mOnZHLYItemClickListener;          //开具处方点击事件
    private         OnCFQItemClickListener          mOnCFQItemClickListener;          //开具处方点击事件

    private         Context                         mContext;


    public TWJZNoFinishRecycleAdapter(List<ProvideViewInteractOrderTreatmentAndPatientInterrogation> list, Context context){
        mContext = context;
        datas = list;
    }

    //重新设置数据
    public void setDate(List<ProvideViewInteractOrderTreatmentAndPatientInterrogation> list){
        datas = list;
    }

        //创建新View，被LayoutManager所调用
       @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activitytwjz_nofinishinfo,viewGroup,false);
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
        viewHolder.mDate.setText(Util.dateToStr(datas.get(position).getTreatmentDateStart()));
        viewHolder.mUserName.setText(datas.get(position).getPatientName());
        if (datas.get(position).getInterrogationGender() == 0)
            viewHolder.mSex.setText("未知");
        if (datas.get(position).getInterrogationGender() == 1)
            viewHolder.mSex.setText("男");
        if (datas.get(position).getInterrogationGender() == 2)
            viewHolder.mSex.setText("女");
        viewHolder.mAge.setText(datas.get(position).getInterrogationBirthday());
        viewHolder.mJD.setText(datas.get(position).getFlagTreatmentStateName());
        viewHolder.mType.setText(datas.get(position).getTreatmentTypeName());
        if (datas.get(position).getTreatmentType() == 1)
        {
            viewHolder.mText01.setVisibility(View.VISIBLE);
            viewHolder.mText02.setVisibility(View.VISIBLE);
            viewHolder.mText03.setVisibility(View.GONE);
            viewHolder.mText04.setVisibility(View.GONE);
            viewHolder.mText05.setVisibility(View.GONE);
            viewHolder.mText06.setVisibility(View.GONE);
            if (datas.get(position).getTreatmentDateEnd() != null)
                viewHolder.mText01.setText("服务截止时间："+Util.dateToStr(datas.get(position).getTreatmentDateEnd()));
            else
                viewHolder.mText01.setVisibility(View.GONE);
            if (datas.get(position).getLimitImgText() == -1)
                viewHolder.mText02.setText("剩余图文消息数量：无限制");
            else
                viewHolder.mText02.setText("剩余图文消息数量："+datas.get(position).getLimitImgText()+"(条)");
        }
        if (datas.get(position).getTreatmentType() == 2)
        {
            viewHolder.mText01.setVisibility(View.VISIBLE);
            viewHolder.mText02.setVisibility(View.VISIBLE);
            viewHolder.mText03.setVisibility(View.GONE);
            viewHolder.mText04.setVisibility(View.GONE);
            viewHolder.mText05.setVisibility(View.GONE);
            viewHolder.mText06.setVisibility(View.GONE);
            viewHolder.mText01.setText("预约服务时间："+datas.get(position).getTreatmentTimeSlotName());
            viewHolder.mText02.setText("剩余音频时长："+datas.get(position).getLimitAudio()+"(分钟)");
        }
        if (datas.get(position).getTreatmentType() == 3)
        {
            viewHolder.mText01.setVisibility(View.VISIBLE);
            viewHolder.mText02.setVisibility(View.VISIBLE);
            viewHolder.mText03.setVisibility(View.GONE);
            viewHolder.mText04.setVisibility(View.GONE);
            viewHolder.mText05.setVisibility(View.GONE);
            viewHolder.mText06.setVisibility(View.GONE);
            viewHolder.mText01.setText("预约服务时间："+datas.get(position).getTreatmentTimeSlotName());
            viewHolder.mText02.setText("剩余视频时长："+datas.get(position).getLimitVideo()+"(分钟)");
        }
        if (datas.get(position).getTreatmentType() == 4)
        {
            viewHolder.mText01.setVisibility(View.VISIBLE);
            viewHolder.mText02.setVisibility(View.VISIBLE);
            viewHolder.mText03.setVisibility(View.VISIBLE);
            viewHolder.mText04.setVisibility(View.VISIBLE);
            viewHolder.mText05.setVisibility(View.VISIBLE);
            viewHolder.mText06.setVisibility(View.VISIBLE);
            viewHolder.mText01.setText("服务天数："+datas.get(position).getLimitSigning()+"(天)");
            viewHolder.mText02.setText("服务截止时间："+Util.dateToStr(datas.get(position).getLimitSigningExpireDate()));
            if (datas.get(position).getLimitImgText() == -1)
                viewHolder.mText03.setText("剩余图文消息数量：无限制");
            else
                viewHolder.mText03.setText("剩余图文消息数量："+datas.get(position).getLimitImgText()+"(条)");
            viewHolder.mText04.setText("剩余通话时长："+datas.get(position).getLimitPhone()+"(分钟)");
            viewHolder.mText05.setText("预约音频时长："+datas.get(position).getLimitAudio()+"(分钟)");
            viewHolder.mText06.setText("剩余视频时长："+datas.get(position).getLimitVideo()+"(分钟)");

        }
        if (datas.get(position).getTreatmentType() == 5)
        {
            viewHolder.mText01.setVisibility(View.VISIBLE);
            viewHolder.mText02.setVisibility(View.VISIBLE);
            viewHolder.mText03.setVisibility(View.VISIBLE);
            viewHolder.mText04.setVisibility(View.GONE);
            viewHolder.mText05.setVisibility(View.GONE);
            viewHolder.mText06.setVisibility(View.GONE);
            viewHolder.mText01.setText("接听电话："+datas.get(position).getTreatmentLinkPhone());
            viewHolder.mText02.setText("预约服务时间："+datas.get(position).getTreatmentTimeSlotName());
            viewHolder.mText03.setText("剩余通话时长："+datas.get(position).getLimitPhone());
        }
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

        //开具处方
        if (mKJCFOnItemClickListener != null)
        {
            viewHolder.mKJCF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mKJCFOnItemClickListener.onClick(position);
                }
            });

            viewHolder.mKJCF.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mKJCFOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

        //问诊信息
        if (mOnWZXXItemClickListener != null)
        {
            viewHolder.mWZXX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnWZXXItemClickListener.onClick(position);
                }
            });

            viewHolder.mWZXX.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnWZXXItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

        //患医互动
        if (mOnHYHDItemClickListener != null)
        {
            viewHolder.mYHHD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnHYHDItemClickListener.onClick(position);
                }
            });

            viewHolder.mYHHD.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnHYHDItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

        //处方签
        if (mOnCFQItemClickListener != null)
        {
            viewHolder.mCFQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnCFQItemClickListener.onClick(position);
                }
            });

            viewHolder.mCFQ.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnCFQItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }


        //诊后留言
        if (mOnZHLYItemClickListener != null)
        {
            viewHolder.mZHLY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnZHLYItemClickListener.onClick(position);
                }
            });

            viewHolder.mZHLY.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    mOnZHLYItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }


//        if (datas.get(position).getTwjzZT() == 1)
//        {
//            viewHolder.mCFQ.setVisibility(View.GONE);
//            viewHolder.mYHHD.setVisibility(View.VISIBLE);
//            viewHolder.mKJCF.setVisibility(View.GONE);
//            viewHolder.mZHLY.setVisibility(View.GONE);
//        }
//        if (datas.get(position).getTwjzZT() == 2)
//        {
//            viewHolder.mCFQ.setVisibility(View.GONE);
//            viewHolder.mYHHD.setVisibility(View.GONE);
//            viewHolder.mKJCF.setVisibility(View.VISIBLE);
//            viewHolder.mZHLY.setVisibility(View.GONE);
//        }
//        if (datas.get(position).getTwjzZT() == 3)
//        {
//            viewHolder.mCFQ.setVisibility(View.VISIBLE);
//            viewHolder.mYHHD.setVisibility(View.GONE);
//            viewHolder.mKJCF.setVisibility(View.GONE);
//            viewHolder.mZHLY.setVisibility(View.VISIBLE);
//        }
    }
        //获取数据的数量
        @Override
        public int getItemCount(){

        return datas.size();
//            return 10;
        }




        //自定义的ViewHolder，持有每个Item的的所有界面元素

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout mClickLinearLayout;                     //整个布局，用来监听点击事件

            private TextView    mDate;                                  //时间
            private TextView    mType;                                  //就诊类型
            private TextView    mUserName;                                  // 姓名
            private TextView    mSex;                                  //性别
            private TextView    mAge;                                  //年龄
            private TextView    mJD;                                  //当前进度
            private TextView    mText01;                                  //截止时间
            private TextView    mText02;                                  //
            private TextView    mText03;                                  //
            private TextView    mText04;                                  //
            private TextView    mText05;                                  //
            private TextView    mText06;                                  //

            public TextView     mWZXX;                                  //问诊信息
            public TextView     mYHHD;                                  //医患互动
            public TextView     mKJCF;                                  //开具处方
            public TextView     mZHLY;                                  //诊后留言
            public TextView     mCFQ;                                  //处方签


            public ViewHolder(View view){
                super(view);
                mClickLinearLayout = (LinearLayout) view.findViewById(R.id.li_itemActivityTWJZ_hzxx);

                mType = (TextView)view.findViewById(R.id.tv_type);
                mDate = (TextView)view.findViewById(R.id.tv_date);
                mUserName = (TextView)view.findViewById(R.id.tv_username);
                mSex = (TextView)view.findViewById(R.id.tv_sex);
                mAge = (TextView)view.findViewById(R.id.tv_age);
                mJD = (TextView)view.findViewById(R.id.tv_jd);
                mText01 = (TextView)view.findViewById(R.id.tv_test01);
                mText02 = (TextView)view.findViewById(R.id.tv_test02);
                mText03 = (TextView)view.findViewById(R.id.tv_test03);
                mText04 = (TextView)view.findViewById(R.id.tv_test04);
                mText05 = (TextView)view.findViewById(R.id.tv_test05);
                mText06 = (TextView)view.findViewById(R.id.tv_test06);

                mWZXX = (TextView)view.findViewById(R.id.item_fragmentTWJX_wzxx);
                mYHHD = (TextView)view.findViewById(R.id.item_fragmentTWJX_hyhd);
                mKJCF = (TextView)view.findViewById(R.id.item_fragmentTWJX_kjcf);
                mZHLY = (TextView)view.findViewById(R.id.item_fragmentTWJX_zhly);
                mCFQ = (TextView)view.findViewById(R.id.item_fragmentTWJX_cfq);

            }
        }



    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    //开具处方点击事件
    public interface OnKJCFItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnKJCFItemClickListener(OnKJCFItemClickListener onKJCFItemClickListener ){
        this.mKJCFOnItemClickListener=onKJCFItemClickListener;
    }

    //问诊信息点击事件
    public interface OnWZXXItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnWZXXItemClickListener(OnWZXXItemClickListener onWZXXItemClickListener ){
        this.mOnWZXXItemClickListener=onWZXXItemClickListener;
    }

    //诊后留言点击事件
    public interface OnZHLYItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnZHLYItemClickListener(OnZHLYItemClickListener onWZXXItemClickListener ){
        this.mOnZHLYItemClickListener=onWZXXItemClickListener;
    }

    //处方签点击事件
    public interface OnCFQItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnCFQItemClickListener(OnCFQItemClickListener onCFQItemClickListener ){
        this.mOnCFQItemClickListener=onCFQItemClickListener;
    }

    //医患互动击事件
    public interface OnHYHDItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnHYHDItemClickListener(OnHYHDItemClickListener onHYHDItemClickListener ){
        this.mOnHYHDItemClickListener=onHYHDItemClickListener;
    }


}