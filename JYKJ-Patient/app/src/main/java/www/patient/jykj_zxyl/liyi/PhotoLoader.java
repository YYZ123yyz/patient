package www.patient.jykj_zxyl.liyi;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import indi.liyi.viewer.ImageLoader;
import www.patient.jykj_zxyl.liyi.glide.GlideApp;

public class PhotoLoader extends ImageLoader {

    @Override
    public void displayImage(final Object src, ImageView imageView, final LoadCallback callback) {
        GlideApp.with(imageView.getContext())
                .load(src)
//                .into(imageView);
                .into(new CustomViewTarget<ImageView, Drawable>(imageView) {

                    @Override
                    protected void onResourceLoading(@Nullable Drawable placeholder) {
                        super.onResourceLoading(placeholder);
//                        ProgressController.registerListener(src, new OnProgressListener() {
//                            @Override
//                            public void onProgress(float progress, long totalSize) {
//                                callback.onLoading(progress);
//                            }
//                        });
                        if(callback!=null){
                            callback.onLoadStarted(placeholder);
                        }
                    }

                    @Override
                    public void onResourceReady(Drawable resource,Transition<? super Drawable> transition) {
                        if(callback!=null) {
                            callback.onLoadSucceed(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(Drawable errorDrawable) {
                        if(callback!=null) {
                            callback.onLoadFailed(errorDrawable);
                        }
//                        ProgressController.unregisterListener(src);
                    }

                    @Override
                    protected void onResourceCleared(Drawable placeholder) {

                    }
                });
    }
}
