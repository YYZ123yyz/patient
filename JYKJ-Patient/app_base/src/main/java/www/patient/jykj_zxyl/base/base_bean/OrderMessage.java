package www.patient.jykj_zxyl.base.base_bean;

import java.io.Serializable;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-03 19:17
 */
public class OrderMessage implements Serializable {
    private String orderId;
    private String singNo;
    private String monitoringType;
    private String coach;
    private String signUpTime;
    private String price;
    private String orderType;
    private String messageType;
    private String imageUrl;
    private String isPatient;
    private String signId;

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    //预约类型
    private String statusType;
    private String startTime;
    private String cancelTime;
    private String appointMentProject;
    private String appointMentType;

    private String  endTime;
    private String  patientType;
    private String  opStatus;

    //医生已接诊
    private String receiveTime;
    private String surplusTimes;

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getSurplusTimes() {
        return surplusTimes;
    }

    public void setSurplusTimes(String surplusTimes) {
        this.surplusTimes = surplusTimes;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getAppointMentProject() {
        return appointMentProject;
    }

    public void setAppointMentProject(String appointMentProject) {
        this.appointMentProject = appointMentProject;
    }

    public String getAppointMentType() {
        return appointMentType;
    }

    public void setAppointMentType(String appointMentType) {
        this.appointMentType = appointMentType;
    }

    public OrderMessage(String orderId, String singNo,
                        String monitoringType, String coach, String signUpTime,
                        String price, String messageType, String orderType,String singId) {
        this.orderId = orderId;
        this.singNo = singNo;
        this.monitoringType = monitoringType;
        this.coach = coach;
        this.signUpTime = signUpTime;
        this.price = price;
        this.messageType = messageType;
        this.orderType = orderType;
        this.signId =singId;
    }

    public OrderMessage(String orderId,
                        String messageType,
                        String statusType,
                        String startTime,
                        String cancelTime,
                        String appointMentProject,
                        String appointMentType) {
        this.orderId = orderId;
        this.messageType = messageType;
        this.statusType = statusType;
        this.startTime = startTime;
        this.cancelTime = cancelTime;
        this.appointMentProject = appointMentProject;
        this.appointMentType = appointMentType;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMonitoringType() {
        return monitoringType;
    }

    public void setMonitoringType(String monitoringType) {
        this.monitoringType = monitoringType;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getSignUpTime() {
        return signUpTime;
    }

    public void setSignUpTime(String signUpTime) {
        this.signUpTime = signUpTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSingNo() {
        return singNo;
    }

    public void setSingNo(String singNo) {
        this.singNo = singNo;
    }

    public String getIsPatient() {
        return isPatient;
    }

    public void setIsPatient(String isPatient) {
        this.isPatient = isPatient;
    }
}
