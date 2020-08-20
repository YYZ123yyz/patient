package www.patient.jykj_zxyl.base.enum_type;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description:文件上传状态
 *
 * @author: qiuxinhai
 * @date: 2019-07-22 15:35
 */
@Retention(RetentionPolicy.SOURCE)

@IntDef({UploadStatusEnum.uploadPre,
        UploadStatusEnum.uploadLoading,
        UploadStatusEnum.uploadSucess,
        UploadStatusEnum.uploadError})
public @interface UploadStatusEnum {
    /**
     * 未上传
     */
    int uploadPre=-1;

    /**
     * 上传中
     */
    int uploadLoading=1;

    /**
     * 上传成功
     */
    int uploadSucess=2;
    /**
     * 上传失败
     */
    int uploadError=3;

}
