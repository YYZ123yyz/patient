package entity.mySelf;

import java.io.Serializable;
import java.util.Date;

public class ZhlyDetailInfo implements Serializable {
    private String flagReplyState;
    private String flagReplyType;
    private String flagReplyTypeName;
    private String imgCode;
    private String messageContent;
    private Date messageDate;
    private Integer messageId;
    private String patientLinkPhone;
    private Integer treatmentType;
    private String treatmentTypeName;

    public String getFlagReplyState() {
        return flagReplyState;
    }

    public void setFlagReplyState(String flagReplyState) {
        this.flagReplyState = flagReplyState;
    }

    public String getFlagReplyType() {
        return flagReplyType;
    }

    public void setFlagReplyType(String flagReplyType) {
        this.flagReplyType = flagReplyType;
    }

    public String getFlagReplyTypeName() {
        return flagReplyTypeName;
    }

    public void setFlagReplyTypeName(String flagReplyTypeName) {
        this.flagReplyTypeName = flagReplyTypeName;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getPatientLinkPhone() {
        return patientLinkPhone;
    }

    public void setPatientLinkPhone(String patientLinkPhone) {
        this.patientLinkPhone = patientLinkPhone;
    }

    public Integer getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(Integer treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getTreatmentTypeName() {
        return treatmentTypeName;
    }

    public void setTreatmentTypeName(String treatmentTypeName) {
        this.treatmentTypeName = treatmentTypeName;
    }
}
