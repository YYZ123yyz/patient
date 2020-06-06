package www.patient.jykj_zxyl.util;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.math.BigDecimal;

import netService.HttpNetService;

public class HttpUtils {
    //post  请求
    public static void sendOKHttpRequest(String url, String param, Callback callback) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), param);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("requestClientVerify",HttpNetService.requestClientVerify)
                .addHeader("requestLoginTokenValue",HttpNetService.requestLoginTokenValue)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    //get 请求
    public static void sendGetOHttpRequest(String url, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }



}
