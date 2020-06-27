package entity.mySelf;

import java.io.Serializable;

public class SubCommentInfo implements Serializable {
    private String loginPatientPosition;
    private String requestClientType;
    private String operPatientCode;
    private String operPatientName;
    private String commentId;
    private String orderCode;
    private String treatmentType;
    private String doctorCode;
    private String commentType;
    private String commentContent;

    public String getLoginPatientPosition() {
        return loginPatientPosition;
    }

    public void setLoginPatientPosition(String loginPatientPosition) {
        this.loginPatientPosition = loginPatientPosition;
    }

    public String getRequestClientType() {
        return requestClientType;
    }

    public void setRequestClientType(String requestClientType) {
        this.requestClientType = requestClientType;
    }

    public String getOperPatientCode() {
        return operPatientCode;
    }

    public void setOperPatientCode(String operPatientCode) {
        this.operPatientCode = operPatientCode;
    }

    public String getOperPatientName() {
        return operPatientName;
    }

    public void setOperPatientName(String operPatientName) {
        this.operPatientName = operPatientName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
