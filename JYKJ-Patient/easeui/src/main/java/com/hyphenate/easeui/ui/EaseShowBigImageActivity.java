/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.model.EaseImageCache;
import com.hyphenate.easeui.utils.ActivityUtil;
import com.hyphenate.easeui.utils.EaseLoadLocalBigImgTask;
import com.hyphenate.easeui.widget.photoview.EasePhotoView;
import com.hyphenate.easeui.widget.photoview.PhotoViewAttacher;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.ImageUtils;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * download and show original image
 * 
 */
public class EaseShowBigImageActivity extends EaseBaseActivity {
	private static final String TAG = "ShowBigImage"; 
	private ProgressDialog pd;
	private EasePhotoView image;
	private int default_res = R.drawable.ease_default_image;
	private String localFilePath;
	private Bitmap bitmap;
	private boolean isDownloaded;
	//开始的坐标值
	private int startY;
	private int startX;
	//开始的宽高
	private int startWidth;
	private int startHeight;
	//X、Y的移动距离
	private int xDelta;
	private int yDelta;
	//X、Y的缩放比例
	private float mWidthScale;
	private float mHeightScale;
	private static final int DURATION = 200;
	//背景色
	private ColorDrawable colorDrawable;
	private RelativeLayout rlRootView;
	@SuppressLint({"NewApi", "ResourceAsColor"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.ease_activity_show_big_image);
		super.onCreate(savedInstanceState);
		ActivityUtil.setStatusBarMain(this,R.color.blackColor);
		//设置背景色，后面需要为其设置渐变动画
		//全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		image =  findViewById(R.id.image);
		rlRootView=findViewById(R.id.rl_root_view);
		colorDrawable = new ColorDrawable(ContextCompat.getColor(this, R.color.blackColor));
		rlRootView.setBackground(colorDrawable);
		ProgressBar loadLocalPb = (ProgressBar) findViewById(R.id.pb_load_local);
		default_res = getIntent().getIntExtra("default_image", R.mipmap.docter_heard);
		Uri uri = getIntent().getParcelableExtra("uri");
		localFilePath = getIntent().getExtras().getString("localUrl");
		String msgId = getIntent().getExtras().getString("messageId");
		EMLog.d(TAG, "show big msgId:" + msgId );


		//show the image if it exist in local path
		if (uri != null && new File(uri.getPath()).exists()) {
			EMLog.d(TAG, "showbigimage file exists. directly show it");
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			// int screenWidth = metrics.widthPixels;
			// int screenHeight =metrics.heightPixels;
			bitmap = EaseImageCache.getInstance().get(uri.getPath());
			if (bitmap == null) {
				EaseLoadLocalBigImgTask task = new EaseLoadLocalBigImgTask(this, uri.getPath(), image, loadLocalPb, ImageUtils.SCALE_IMAGE_WIDTH,
						ImageUtils.SCALE_IMAGE_HEIGHT);
				if (android.os.Build.VERSION.SDK_INT > 10) {
					task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				} else {
					task.execute();
				}
			} else {
				image.setImageBitmap(bitmap);
			}
		} else if(msgId != null) {
		    downloadImage(msgId);
		}else {
			image.setImageResource(default_res);
		}

		image.setOnPhotoTapListener((view, x, y) -> onBackPressed());

	}


	@Override
	protected void onStart() {
		super.onStart();
		startY = image.getTop();
		startX = image.getLeft();
		startWidth = image.getWidth();
		startHeight = image.getHeight();
		//注册一个回调函数，当一个视图树将要绘制时调用这个回调函数。
		ViewTreeObserver observer = image.getViewTreeObserver();
		observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				image.getViewTreeObserver().removeOnPreDrawListener(this);
				int[] screenLocation = new int[2];
				image.getLocationOnScreen(screenLocation);
				//动画需要移动的距离
				xDelta = startX - screenLocation[0];
				yDelta = startY - screenLocation[1];
				//计算缩放比例
				mWidthScale = (float) startWidth / image.getWidth();
				mHeightScale = (float) startHeight / image.getHeight();
				enterAnimation(new Runnable() {
					@Override
					public void run() {
						//开始动画之后要做的操作
					}
				});
				//返回 true 继续绘制，返回false取消。
				return true;
			}
		});
	}

	private void enterAnimation(final Runnable enterAction) {
		//放大动画
		image.setPivotX(0);
		image.setPivotY(0);
		image.setScaleX(mWidthScale);
		image.setScaleY(mHeightScale);
		image.setTranslationX(xDelta);
		image.setTranslationY(yDelta);
		TimeInterpolator sDecelerator = new DecelerateInterpolator();
		image.animate().setDuration(DURATION).scaleX(1).scaleY(1).
				translationX(0).translationY(0).setInterpolator(sDecelerator).withEndAction(enterAction);
		//设置背景渐变成你设置的颜色
		ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0, 255);
		bgAnim.setDuration(DURATION);
		bgAnim.start();
	}

	private void exitAnimation(final Runnable endAction) {
		//缩小动画
		image.setPivotX(0);
		image.setPivotY(0);
		image.setScaleX(1);
		image.setScaleY(1);
		image.setTranslationX(0);
		image.setTranslationY(0);

		TimeInterpolator sInterpolator = new AccelerateInterpolator();
		image.animate().setDuration(DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
				translationX(xDelta).translationY(yDelta).setInterpolator(sInterpolator).withEndAction(endAction);
		//设置背景渐透明
		ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0);
		bgAnim.setDuration(DURATION);
		bgAnim.start();
	}



	/**
	 * download image
	 * 
	 * @param remoteFilePath
	 */
	@SuppressLint("NewApi")
	private void downloadImage(final String msgId) {
        EMLog.e(TAG, "download with messageId: " + msgId);
		String str1 = getResources().getString(R.string.Download_the_pictures);
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCanceledOnTouchOutside(false);
		pd.setMessage(str1);
		pd.show();
		File temp = new File(localFilePath);
		final String tempPath = temp.getParent() + "/temp_" + temp.getName();
		final EMCallBack callback = new EMCallBack() {
			public void onSuccess() {
			    EMLog.e(TAG, "onSuccess" );
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
                        new File(tempPath).renameTo(new File(localFilePath));

                        DisplayMetrics metrics = new DisplayMetrics();
						getWindowManager().getDefaultDisplay().getMetrics(metrics);
						int screenWidth = metrics.widthPixels;
						int screenHeight = metrics.heightPixels;

						bitmap = ImageUtils.decodeScaleImage(localFilePath, screenWidth, screenHeight);
						if (bitmap == null) {
							image.setImageResource(default_res);
						} else {
							image.setImageBitmap(bitmap);
							EaseImageCache.getInstance().put(localFilePath, bitmap);
							isDownloaded = true;
						}
						if (isFinishing() || isDestroyed()) {
						    return;
						}
						if (pd != null) {
							pd.dismiss();
						}
					}
				});
			}

			public void onError(final int error, String msg) {
				EMLog.e(TAG, "offline file transfer error:" + msg);
				File file = new File(tempPath);
				if (file.exists()&&file.isFile()) {
					file.delete();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (EaseShowBigImageActivity.this.isFinishing() || EaseShowBigImageActivity.this.isDestroyed()) {
						    return;
						}
                        image.setImageResource(default_res);
                        pd.dismiss();
                        if (error == EMError.FILE_NOT_FOUND) {
							Toast.makeText(getApplicationContext(), R.string.Image_expired, Toast.LENGTH_SHORT).show();
						}
					}
				});
			}

			public void onProgress(final int progress, String status) {
				EMLog.d(TAG, "Progress: " + progress);
				final String str2 = getResources().getString(R.string.Download_the_pictures_new);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
                        if (EaseShowBigImageActivity.this.isFinishing() || EaseShowBigImageActivity.this.isDestroyed()) {
                            return;
                        }
						pd.setMessage(str2 + progress + "%");
					}
				});
			}
		};
		
		EMMessage msg = EMClient.getInstance().chatManager().getMessage(msgId);
		msg.setMessageStatusCallback(callback);

		EMLog.e(TAG, "downloadAttachement");
		EMClient.getInstance().chatManager().downloadAttachment(msg);
	}

	@Override
	public void onBackPressed() {

		int[] screenLocation = new int[2];
		image.getLocationOnScreen(screenLocation);
		xDelta = startX - screenLocation[0];
		yDelta = startY - screenLocation[1];
		mWidthScale = (float) startWidth / image.getWidth();
		mHeightScale = (float) startHeight / image.getHeight();

		exitAnimation(new Runnable() {
			public void run() {
				//结束动画要做的操作
				//finish();


				if (isDownloaded)
					setResult(RESULT_OK);
				finish();
				overridePendingTransition(0, 0);
			}
		});



//		if (isDownloaded)
//			setResult(RESULT_OK);
//		finish();
	}
}
