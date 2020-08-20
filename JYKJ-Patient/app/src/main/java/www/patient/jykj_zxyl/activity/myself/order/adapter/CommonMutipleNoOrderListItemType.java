package www.patient.jykj_zxyl.activity.myself.order.adapter;

import android.text.TextUtils;

import com.allin.commonadapter.IMulItemViewType;

import www.patient.jykj_zxyl.R;
import www.patient.jykj_zxyl.base.base_bean.MultiItemEntity;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-04 11:20
 */
public class CommonMutipleNoOrderListItemType implements IMulItemViewType<MultiItemEntity> {


    /**
     * 普通订单类型
     */
    public static final String MULTIPLE_CONTENT_ORDINARY_TYPE = "1";
    /**
     * 签约订单类型
     */
    public static final String MULTIPLE_CONTENT_SIGN_UP_TYPE = "2";
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int i, MultiItemEntity multiItemEntity) {
        String itemType = multiItemEntity.getItemType();
        if (TextUtils.isEmpty(itemType)) {
            return -1;
        }
        switch (itemType){
            case MULTIPLE_CONTENT_ORDINARY_TYPE://一张图片
                return 1;
            case MULTIPLE_CONTENT_SIGN_UP_TYPE://两张图片
                return 2;
            default:
                return -1;
        }


    }

    @Override
    public int getLayoutId(int viewType) {

        int layoutId = -1;
        switch (viewType){
            case 1:
                layoutId=R.layout.item_to_be_confirmed_order_card;
                break;
            case 2:
                layoutId=R.layout.item_fragment_myorder_no_1;
                break;
                default:
        }
        return layoutId;

    }
}
