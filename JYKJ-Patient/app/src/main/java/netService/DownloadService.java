package netService;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXFileObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.util.WechatShareManager;

import static www.patient.jykj_zxyl.activity.LoginActivity.isWeixinAvilible;

/**
 * Created by G on 2020/9/25 19:15
 */
public class DownloadService extends Service {

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

    /**
     * 目标文件存储的文件名
     */
    private String destFileName;
    private String updateInfo;

    private Context mContext;

    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Retrofit.Builder retrofit;
    private int preProgress = 0;
    private String url;
    private int code;
    private WechatShareManager mShareManager;
    private IWXAPI mWXApi;
    //    private long apkSize;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        destFileName = "cpre.pdf";
        updateInfo = "处方笺下载中...";
        url = intent.getStringExtra("url");
        mShareManager = WechatShareManager.getInstance(mContext);
        loadFile();
        return super.onStartCommand(intent, flags, startId);
    }

        private void shareFile(){
            if (mWXApi == null) {
                mWXApi = WXAPIFactory.createWXAPI(mContext, "wx4ccb2ac1c5491336", true);
            }
            mWXApi.registerApp("wx4ccb2ac1c5491336");

            WXFileObject wxFileObject=new WXFileObject();
            String filePath = mContext.getExternalFilesDir(null) + "/Download/cpre.pdf";
            wxFileObject.setFilePath(filePath);
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = wxFileObject;
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("pdf");

            req.message = msg;
            req.scene = 1;
            mWXApi.sendReq(req);
        }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type ;
    }


    /**
     * 下载文件
     */
    private void loadFile() {
        File dir = new File(destFileDir);
        if (!dir.exists()) {// 如果文件不存在新建一个
            dir.mkdirs();
        }
        initNotification();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder();
        }

        retrofit.baseUrl("http://jiuyihtn.com/zq/")
                .client(initOkHttpClient())
                .build()
                .create(IFileLoad.class)
                .loadFile()
                .enqueue(new FileCallback(destFileDir, destFileName) {

                    @Override
                    public void onSuccess(File file) {
                        File absoluteFile = file.getAbsoluteFile();
                        String path = file.getPath();
                        LogUtils.e("文件目录111"+absoluteFile);
                        LogUtils.e("文件目录22"+path);
                        // 安装软件
                        cancelNotification();
//                        installApk(file);

                        ToastUtils.showShort("完成");
                        LogUtils.e("文件目录"+destFileDir);
                        ShareFileToWeiXin();
//                        shareFile();
                    }

                    @Override
                    public void onLoading(long progress, long total) {

//                        if (apkSize > 0) {
//                            updateNotification(progress * 100 / apkSize);
//                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        cancelNotification();
                    }
                });
    }

    public interface IFileLoad {

        @GET("xian.pdf")
        Call<ResponseBody> loadFile();
    }

    /**
     * 安装软件
     *
     * @param file
     */
//    private void installApk(File file) {
//        Intent intent = new Intent();
//        // 执行动作
//        intent.setAction(Intent.ACTION_VIEW);
//        // 执行的数据类型
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        if (Build.VERSION.SDK_INT >= 24) {
//            //7.0
//            Uri uriForFile = FileProvider.getUriForFile(mContext,
//                    "com.www.jiuyi.netService", file);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
//        } else {
//            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        }
//        mContext.startActivity(intent);
//
//        /*Uri uri = Uri.fromFile(file);
//        Intent install = new Intent(Intent.ACTION_VIEW);
//        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        install.setDataAndType(uri, "application/vnd.android.package-archive");
//        // 执行意图进行安装
//        mContext.startActivity(install);*/
//    }

    /**
     * 初始化OkHttpClient
     *
     * @return
     */
    private OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(100000, TimeUnit.SECONDS);
        builder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                 ;
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
               /* Request request = chain.request()
                        .newBuilder()
                        .addHeader("Accept-Encoding","identity")
                        .build();*/
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new FileResponseBody(originalResponse))
                        .build();
            }
        });
        return builder.build();
    }

    /**
     * 初始化Notification通知
     */
    public void initNotification() {
        builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.logo)
                .setContentText("0%")
                .setContentTitle(updateInfo)
                .setProgress(100, 0, false);
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {

        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, (int) progress, false);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
        preProgress = (int) progress;

    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFY_ID);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void ToShare(int type, String title, String content, String url, int pictureResource) {
        //i   0是会话  1是朋友圈
        if (isWeixinAvilible(this)) {
            WechatShareManager.ShareContentWebpage mShareContent =
                    (WechatShareManager.ShareContentWebpage) mShareManager.getShareContentWebpag(title, content, url, pictureResource);

            mShareManager.shareByWebchat(mShareContent, type);
        } else {
            Toast.makeText(this, "您还没有安装微信，请先安装微信客户端", Toast.LENGTH_SHORT).show();
        }
    }




    public void shareToWechat(Context context) {
        // ...




        String filePath = context.getExternalFilesDir(null) + "/shareData/test.png";
        // 该filePath对应于xml/file_provider_paths里的第一行配置：，因此才可被共享

        File file = new File(filePath);
        String contentPath = getFileUri(context, file);

        // 使用contentPath作为文件路径进行分享
        // ...
    }


    private void ShareFileToWeiXin() {
        if (mWXApi == null) {
            mWXApi = WXAPIFactory.createWXAPI(mContext, "wx4ccb2ac1c5491336", true);
        }
        mWXApi.registerApp("wx4ccb2ac1c5491336");

        WXFileObject fileObj = new WXFileObject();
        fileObj.fileData = inputStreamToByte("storage/emulated/0/Download/cpre.pdf");//文件路径
        fileObj.filePath = "storage/emulated/0/Download/cpre.pdf" ;

        //使用媒体消息分享
        WXMediaMessage msg = new WXMediaMessage(fileObj);
        msg.title = "title";
        msg.messageExt ="pdf";
        //发送请求
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction =buildTransaction("pdf");
        //创建唯一标识
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        mWXApi.sendReq(req);
    }

    public  byte[] inputStreamToByte(String path)
    {
        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = fis.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    public String getFileUri(Context context, File file) {
        if (file == null || !file.exists()) {
            return null;
        }

        Uri contentUri = FileProvider.getUriForFile(context,
                "www.patient.jykj_zxyl.fileprovider",  // 要与`AndroidManifest.xml`里配置的`authorities`一致，假设你的应用包名为com.example.app
                file);

        // 授权给微信访问路径
        context.grantUriPermission("com.tencent.mm",  // 这里填微信包名
                contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

        return contentUri.toString();   // contentUri.toString() 即是以"content://"开头的用于共享的路径
    }

//    public void ToShare(int type, File ) {
//        //i   0是会话  1是朋友圈
//        if (isWeixinAvilible(context)) {
//            WechatShareManager.ShareContentWebpage mShareContent =
//                    (WechatShareManager.ShareContentWebpage) mShareManager.getShareContentWebpag(title, content, url, pictureResource);
//
//            mShareManager.shareByWebchat(mShareContent, type);
//        } else {
//            Toast.makeText(context, "您还没有安装微信，请先安装微信客户端", Toast.LENGTH_SHORT).show();
//        }
//    }
}
