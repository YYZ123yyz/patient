package entity.mySelf;

import java.io.Serializable;

public class SubSymptomInfo implements Serializable {
    private String loginPatientPosition;
    private String requestClientType;
    private String operPatientCode;
    private String operPatientName;
    private String healthyId;
    private String onsetSymptoms;
    private String currentSymptoms;
    private String complication;
    private String combinedDisease;
    private String currentTreatmentPlan;
    private String stateOfIllness;

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

    public String getHealthyId() {
        return healthyId;
    }

    public void setHealthyId(String healthyId) {
        this.healthyId = healthyId;
    }

    public String getOnsetSymptoms() {
        return onsetSymptoms;
    }

    public void setOnsetSymptoms(String onsetSymptoms) {
        this.onsetSymptoms = onsetSymptoms;
    }

    public String getCurrentSymptoms() {
        return currentSymptoms;
    }

    public void setCurrentSymptoms(String currentSymptoms) {
        this.currentSymptoms = currentSymptoms;
    }

    public String getComplication() {
        return complication;
    }

    public void setComplication(String complication) {
        this.complication = complication;
    }

    public String getCombinedDisease() {
        return combinedDisease;
    }

    public void setCombinedDisease(String combinedDisease) {
        this.combinedDisease = combinedDisease;
    }

    public String getCurrentTreatmentPlan() {
        return currentTreatmentPlan;
    }

    public void setCurrentTreatmentPlan(String currentTreatmentPlan) {
        this.currentTreatmentPlan = currentTreatmentPlan;
    }

    public String getStateOfIllness() {
        return stateOfIllness;
    }

    public void setStateOfIllness(String stateOfIllness) {
        this.stateOfIllness = stateOfIllness;
    }
}
