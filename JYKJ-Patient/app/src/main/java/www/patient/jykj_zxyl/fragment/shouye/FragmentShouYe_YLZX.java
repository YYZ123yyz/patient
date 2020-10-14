package www.patient.jykj_zxyl.fragment.shouye;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.HZIfno;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLHZZLActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLQTDKActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLTXHZActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLXYActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLYYXXActivity;
import www.patient.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.patient.jykj_zxyl.activity.ylzx.YLZX01Activity;
import www.patient.jykj_zxyl.activity.ylzx.YLZXWebActivity;
import www.patient.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.MainActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLHZZLActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLQTDKActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLTXHZActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLXYActivity;
import www.patient.jykj_zxyl.activity.hzgl.HZGLYYXXActivity;
import www.patient.jykj_zxyl.activity.myself.hzgl.HZGLSearchActivity;
import www.patient.jykj_zxyl.adapter.HZGLRecycleAdapter;
import www.patient.jykj_zxyl.application.JYKJApplication;


/**
 * 首页==》医疗资讯fragment
 * Created by admin on 2016/6/1.
 */
public class FragmentShouYe_YLZX extends Fragment {
    private Context mContext;
    private Handler mHandler;
    private MainActivity mActivity;
    private JYKJApplication mApp;

    private WebView mWebView;

    private TextView more;
    private LinearLayout ylzxWebView;
    private RecyclerView ylzx_re;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shouye_ylzx, container, false);
        mContext = getContext();
        mActivity = (MainActivity) getActivity();
        mApp = (JYKJApplication) getActivity().getApplication();
        initLayout(v);
        initHandler();
        return v;
    }

    private void initHandler() {

    }

    private void initLayout(View v) {
        ylzx_re = v.findViewById(R.id.ylzx_re);
        //创建默认的线性LayoutManager
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        ylzx_re.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        ylzx_re.setHasFixedSize(true);

        //AudioAdapter
    }


}
