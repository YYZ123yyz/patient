package entity.mySelf;

import java.io.Serializable;
import java.util.Date;

import www.patient.jykj_zxyl.base.base_bean.MultiItemEntity;

public class MyOrderProcess  extends MultiItemEntity implements Serializable {
    private String doctorCode;
    private String doctorName;
    private String actualPayment;
    private String doctorReceiveShow;
    private Integer flagColor;
    private Integer flagTreatmentState;
    private Integer limitAudioShow;
    private Integer limitImgTextShow;
    private Integer limitPhoneShow;
    private Date limitSigningExpireDate;
    private Integer limitVideoShow;
    private String orderCode;
    private Date orderDate;
    private Date treatmentDateEnd;
    private Date treatmentDateStart;
    private String  treatmentLinkPhone;
    private String  treatmentTimeSlot;
    private Integer treatmentType;
    private String  treatmentTypeName;
    private Date treatmentDate;
    private String treatmentTimeSlotName;

    public String getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(String actualPayment) {
        this.actualPayment = actualPayment;
    }

    private String coachUnitCode;
    private String coachUnitName;
    private Integer coachValue;
    private Integer proCount;
    private String timesCode;
    private String timesName;
    private String signNo;

    public String getCoachUnitCode() {
        return coachUnitCode;
    }

    public void setCoachUnitCode(String coachUnitCode) {
        this.coachUnitCode = coachUnitCode;
    }

    public String getCoachUnitName() {
        return coachUnitName;
    }

    public void setCoachUnitName(String coachUnitName) {
        this.coachUnitName = coachUnitName;
    }

    public Integer getCoachValue() {
        return coachValue;
    }

    public void setCoachValue(Integer coachValue) {
        this.coachValue = coachValue;
    }

    public Integer getProCount() {
        return proCount;
    }

    public void setProCount(Integer proCount) {
        this.proCount = proCount;
    }

    public String getTimesCode() {
        return timesCode;
    }

    public void setTimesCode(String timesCode) {
        this.timesCode = timesCode;
    }

    public String getTimesName() {
        return timesName;
    }

    public void setTimesName(String timesName) {
        this.timesName = timesName;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorReceiveShow() {
        return doctorReceiveShow;
    }

    public void setDoctorReceiveShow(String doctorReceiveShow) {
        this.doctorReceiveShow = doctorReceiveShow;
    }

    public Integer getFlagColor() {
        return flagColor;
    }

    public void setFlagColor(Integer flagColor) {
        this.flagColor = flagColor;
    }

    public Integer getFlagTreatmentState() {
        return flagTreatmentState;
    }

    public void setFlagTreatmentState(Integer flagTreatmentState) {
        this.flagTreatmentState = flagTreatmentState;
    }

    public Integer getLimitAudioShow() {
        return limitAudioShow;
    }

    public void setLimitAudioShow(Integer limitAudioShow) {
        this.limitAudioShow = limitAudioShow;
    }

    public Integer getLimitImgTextShow() {
        return limitImgTextShow;
    }

    public void setLimitImgTextShow(Integer limitImgTextShow) {
        this.limitImgTextShow = limitImgTextShow;
    }

    public Integer getLimitPhoneShow() {
        return limitPhoneShow;
    }

    public void setLimitPhoneShow(Integer limitPhoneShow) {
        this.limitPhoneShow = limitPhoneShow;
    }

    public Date getLimitSigningExpireDate() {
        return limitSigningExpireDate;
    }

    public void setLimitSigningExpireDate(Date limitSigningExpireDate) {
        this.limitSigningExpireDate = limitSigningExpireDate;
    }

    public Integer getLimitVideoShow() {
        return limitVideoShow;
    }

    public void setLimitVideoShow(Integer limitVideoShow) {
        this.limitVideoShow = limitVideoShow;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getTreatmentDateEnd() {
        return treatmentDateEnd;
    }

    public void setTreatmentDateEnd(Date treatmentDateEnd) {
        this.treatmentDateEnd = treatmentDateEnd;
    }

    public Date getTreatmentDateStart() {
        return treatmentDateStart;
    }

    public void setTreatmentDateStart(Date treatmentDateStart) {
        this.treatmentDateStart = treatmentDateStart;
    }

    public String getTreatmentLinkPhone() {
        return treatmentLinkPhone;
    }

    public void setTreatmentLinkPhone(String treatmentLinkPhone) {
        this.treatmentLinkPhone = treatmentLinkPhone;
    }

    public String getTreatmentTimeSlot() {
        return treatmentTimeSlot;
    }

    public void setTreatmentTimeSlot(String treatmentTimeSlot) {
        this.treatmentTimeSlot = treatmentTimeSlot;
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

    public Date getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(Date treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public String getTreatmentTimeSlotName() {
        return treatmentTimeSlotName;
    }

    public void setTreatmentTimeSlotName(String treatmentTimeSlotName) {
        this.treatmentTimeSlotName = treatmentTimeSlotName;
    }
}
