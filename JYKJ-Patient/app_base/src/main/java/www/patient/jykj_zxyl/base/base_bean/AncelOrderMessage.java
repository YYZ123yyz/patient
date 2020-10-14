package www.patient.jykj_zxyl.base.base_bean;

import java.io.Serializable;

/**
 *  取消预约
 */
public class AncelOrderMessage implements Serializable {
    private String messageType;
    private String statusType;
    private String startTime;
    private String cancelTime;
    private String appointMentProject;
    private String appointMentType;
    private String orderId;
    private String imageUrl;
   
}
