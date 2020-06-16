package www.patient.jykj_zxyl.wxapi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import entity.ProvideInteractOrderInfo;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.activity.home.OrderMessage_OrderPayActivity;
import www.patient.jykj_zxyl.activity.home.patient.WDYSActivity;
import www.patient.jykj_zxyl.application.JYKJApplication;
import www.patient.jykj_zxyl.util.LoadSharedPreferences;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI iwxapi;
    private TextView textView;
    private Toolbar toolbar;
    private Integer code = 0;
    public static final String PAY_STATE = "PAY_STATE";
    public static final String PAY_MESSAGE = "PAY_MESSAGE";
    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;
    public static final int CANCEL = -2;
    public String categoryKey;
    public String paramAppid = "";
    public String paramProductid = "";

    private JYKJApplication     mApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wxpay_result_activity);
        initToolbar();
        mApp = (JYKJApplication) getApplication();
        setDisplayHomeAsUpEnabled(true);
        setTitle("支付结果");
        textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        paramAppid = intent.getStringExtra("pay_appid");
        paramAppid = (null==paramAppid || paramAppid.length()==0)?"wx4ccb2ac1c5491336":paramAppid;
        paramProductid = intent.getStringExtra("pay_productid");
        if (intent.hasExtra(PAY_STATE)) {
            code = intent.getIntExtra(PAY_STATE, 0);
        }
        /*if (intent.hasExtra("categoryKey"))
        {
            categoryKey = intent.getStringExtra("categoryKey");
        }
        if (categoryKey==null){
            SharedPreferences userSettings= getSharedPreferences("paycharapt", 0);
            categoryKey = userSettings.getString("categoryKey","");
        }*/

        if (code == 0) {
            //iwxapi = WXAPIFactory.createWXAPI(this,paramAppid);
            iwxapi = WXAPIFactory.createWXAPI(this,paramAppid);
            iwxapi.registerApp(paramAppid);
            Intent parintent = getIntent();
            iwxapi.handleIntent(parintent, this);
        } else {
            if (code == FAILURE) {
                showMessageError();
            } else if (code == SUCCESS) {
                showMessage();
            } else {
                showMessage("未知错误");
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (code == null || code == 0) {
            iwxapi.handleIntent(intent, this);
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String error = baseResp.errCode == 0 ? "成功" : "失败";
            if (baseResp.errCode == 0) {
                showMessage();
            } else {
                showMessageError();
            }
        }
    }

    public void showMessageError() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).
                setTitle("支付结果").
                setMessage("当前支付失败").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        alertDialog.show();
    }

    public void showMessage() {
        Toast.makeText(WXPayEntryActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
//        ProvideInteractOrderInfo provideInteractOrderInfo = new ProvideInteractOrderInfo();
//        provideInteractOrderInfo.setOrderCode(mApp.gPayOrderCode);
        for (int i = 0; i < mApp.gPayCloseActivity.size(); i++)
        {
            mApp.gPayCloseActivity.get(i).finish();
        }
        startActivity(new Intent(WXPayEntryActivity.this,WDYSActivity.class));
        finish();
    }

    public void showMessage(String message) {
        if (!TextUtils.isEmpty(paramProductid)) {
            editPaymentOK(paramProductid);
        }
        AlertDialog alertDialog = new AlertDialog.Builder(this).
                setTitle("支付结果").
                setMessage(message).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
        alertDialog.show();
    }

    public void editPaymentOK(String productId) {
        LoadSharedPreferences.getSharedPreferences(getApplicationContext()).edit().putBoolean(productId, true).apply();
    }

    private TextView textViewTitle;
    private ImageView imageViewUp;

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        textViewTitle = getToolbar().findViewById(R.id.action_bar_title);
        imageViewUp = getToolbar().findViewById(R.id.up);
    }

    public void setTitle(@Nullable String title) {
        if (getToolbar() != null) {
            if (textViewTitle == null) textViewTitle = getToolbar().findViewById(R.id.up);
            if (textViewTitle != null) {
                textViewTitle.setText(title);
                textViewTitle.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void setDisplayHomeAsUpEnabled(boolean isUp) {
        if (getToolbar() != null) {
            if (imageViewUp == null) imageViewUp = getToolbar().findViewById(R.id.up);
            if (imageViewUp != null) {
                imageViewUp.setVisibility(isUp ? View.VISIBLE : View.GONE);
                imageViewUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
            if (textViewTitle != null) {
                textViewTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }
    }
}
