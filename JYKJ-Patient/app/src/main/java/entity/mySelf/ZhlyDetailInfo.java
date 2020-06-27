package entity.mySelf;

import java.io.Serializable;
import java.util.Date;

public class ZhlyDetailInfo implements Serializable {
    private String doctorName;
    private Integer flagReplyState;
    private Integer flagReplyType;
    private String flagReplyTypeName;
    private String imgCode;
    private String messageContent;
    private Date messageDate;
    private Integer messageId;
    private String patientLinkPhone;
    private String replyContent;
    private Date replyDate;
    private Integer treatmentType;
    private String treatmentTypeName;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getFlagReplyState() {
        return flagReplyState;
    }

    public void setFlagReplyState(Integer flagReplyState) {
        this.flagReplyState = flagReplyState;
    }

    public Integer getFlagReplyType() {
        return flagReplyType;
    }

    public void setFlagReplyType(Integer flagReplyType) {
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

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
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
