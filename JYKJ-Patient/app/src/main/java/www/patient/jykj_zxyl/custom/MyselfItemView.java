package www.patient.jykj_zxyl.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import www.patient.jykj_zxyl.R;


public class MyselfItemView extends LinearLayout {

    private Context mContext;
    private TextView tvTitle;
    private ImageView ivImg;


    public MyselfItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.item_mine,this,true);
        tvTitle = findViewById(R.id.tv_title);
        ivImg = findViewById(R.id.iv_img);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.mine_item);
        if (typedArray != null){
            //处理文字
            String text_title  = typedArray.getString(R.styleable.mine_item_text_title);
            tvTitle.setText(text_title);

            //处理是否有图标
            boolean aBoolean = typedArray.getBoolean(R.styleable.mine_item_have_img, false);
            if (aBoolean){
                ivImg.setVisibility(View.VISIBLE);
            }else{
                ivImg.setVisibility(View.GONE);
            }

            //处理图标
            int resourceId = typedArray.getResourceId(R.styleable.mine_item_img_src, R.mipmap.myself_default);
            ivImg.setImageResource(resourceId);
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public void setTitleText(String title){
        tvTitle.setText(title);
    }

    public void setImageResoure(int resId){
        ivImg.setImageResource(resId);
    }

    public void setViewVisibility(boolean b){
        if (b){
            ivImg.setVisibility(GONE);
        }else{
            ivImg.setVisibility(VISIBLE);
        }
    }
}
