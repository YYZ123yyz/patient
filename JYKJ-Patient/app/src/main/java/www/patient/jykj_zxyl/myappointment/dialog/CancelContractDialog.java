package www.patient.jykj_zxyl.myappointment.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import com.hyphenate.easeui.netService.HttpNetService;
import com.hyphenate.easeui.netService.entity.NetRetEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_adapter.CancelContractAdapter;
import www.patient.jykj_zxyl.base.base_bean.BaseReasonBean;
import www.patient.jykj_zxyl.myappointment.adapter.CancelAdapter;
import www.patient.jykj_zxyl.myappointment.bean.CancelContractBean;


/**
 * Description:解约dialog
 */
public class CancelContractDialog extends Dialog {
    private Context mContext;
    private View mRootView;
    private RecyclerView mRecyclerView;
    private ImageView ivCloseBtn;

    private CancelContractAdapter cancelContractAdapter;
    private List<BaseReasonBean> cancelContractBeans;
    private OnClickItemListener onClickItemListener;

    private String mNetRetStr;
    private Handler mHandler;
    private CancelAdapter cancelAdapter;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public CancelContractDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        setCanceledOnTouchOutside(true);//禁止点击空白区域消失
        Objects.requireNonNull(this.getWindow()).setDimAmount(0f);//核心代码 解决了无法去除遮罩
        init(context);
        duration();
        addListener();
        initHandler();
    }

    private void initHandler() {
        mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 2:
                        if (mNetRetStr != null && !mNetRetStr.equals("")) {
                            cancelContractBeans = JSON.parseArray(JSON.parseObject(mNetRetStr, NetRetEntity.class).getResJsonData(), BaseReasonBean.class);
                            cancelAdapter = new CancelAdapter(cancelContractBeans, mContext);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                            mRecyclerView.setAdapter(cancelAdapter);
                            cancelAdapter.setOnItemClickListener(new CancelAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(int position) {
                                    cancelContractBeans.get(position).setChoosed(!cancelContractBeans.get(position).isChoosed());
                                    cancelAdapter.setDate(cancelContractBeans);
                                    cancelAdapter.notifyDataSetChanged();
                                    if (onClickItemListener != null) {
                                        BaseReasonBean cancelContractBean = cancelContractBeans.get(position);
                                        onClickItemListener.onClickItem(cancelContractBean);
                                    }
                                }

                                @Override
                                public void onLongClick(int position) {

                                }
                            });
                        }
                        break;

                }
            }
        };
    }


    private void init(Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_cancel_contract, null);
        setContentView(mRootView);
        initView(mRootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
    }

    private void initView(View mRootView) {
        mRecyclerView = mRootView.findViewById(R.id.rv_list);
        ivCloseBtn = mRootView.findViewById(R.id.iv_close_btn);
    }

    private void duration() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("baseCode", "900061");
        new Thread() {
            public void run() {
                try {
                    mNetRetStr = HttpNetService.urlConnectionService("jsonDataInfo=" + new Gson().toJson(map), com.hyphenate.easeui.hyhd.model.Constant.SERVICEURL + "basicDataController/getBasicsDomain");
                    Log.e("TAG", "run: 取消预约原因 " + mNetRetStr);
                } catch (Exception e) {
                    com.hyphenate.easeui.netService.entity.NetRetEntity retEntity = new com.hyphenate.easeui.netService.entity.NetRetEntity();
                    retEntity.setResCode(0);
                    retEntity.setResMsg("网络连接异常，请联系管理员：" + e.getMessage());
                    mNetRetStr = new Gson().toJson(retEntity);
                    e.printStackTrace();

                }

                mHandler.sendEmptyMessage(2);
            }
        }.start();
    }

    /**
     * 设置数据
     *
     * @param datas 数据列表
     */
    public void setData(List<BaseReasonBean> datas) {
        cancelContractBeans = datas;
        cancelContractBeans.get(0).setChoosed(true);

    }

    /**
     * 添加监听
     */
    private void addListener() {
        ivCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelContractDialog.this.dismiss();
            }
        });
    }


    public interface OnClickItemListener {

        void onClickItem(BaseReasonBean cancelContractBean);
    }

}
