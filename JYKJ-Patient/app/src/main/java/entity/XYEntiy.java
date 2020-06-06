package entity;

import java.io.Serializable;
import java.util.Date;

public class XYEntiy implements Serializable {
    private             String              doctorCode;
    private             String              flagOpening;
    private             Date                limitSigningExpireDate;
    private             String              priceBasics;
    private             String              treatmentSigningId;
    private             String              userName;

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getFlagOpening() {
        return flagOpening;
    }

    public void setFlagOpening(String flagOpening) {
        this.flagOpening = flagOpening;
    }

    public Date getLimitSigningExpireDate() {
        return limitSigningExpireDate;
    }

    public void setLimitSigningExpireDate(Date limitSigningExpireDate) {
        this.limitSigningExpireDate = limitSigningExpireDate;
    }

    public String getPriceBasics() {
        return priceBasics;
    }

    public void setPriceBasics(String priceBasics) {
        this.priceBasics = priceBasics;
    }

    public String getTreatmentSigningId() {
        return treatmentSigningId;
    }

    public void setTreatmentSigningId(String treatmentSigningId) {
        this.treatmentSigningId = treatmentSigningId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
