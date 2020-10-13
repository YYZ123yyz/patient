package www.patient.jykj_zxyl.myappointment.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.ActivityUtil;

/**
 * Created by G on 2020/10/9 16:20
 * 通用webActivity
 */
public class UniversalWebActivity extends AppCompatActivity {

    private Context mContext;
    private UniversalWebActivity mActivity;
    private WebView webView;

    private RelativeLayout rl_back;
    private String tittle;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_universal_web);
        mContext = this;
        mActivity = this;
        ActivityUtil.setStatusBarMain(mActivity);
        initData();
        initLayout();
    }

    private void initData() {
        tittle = getIntent().getStringExtra("tittle");
        url = getIntent().getStringExtra("url");

    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        TextView tvTittle = findViewById(R.id.tittle);
        tvTittle.setText(tittle);
        rl_back = (RelativeLayout) this.findViewById(R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

