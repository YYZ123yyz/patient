package entity;

/**
 * 服药打卡参数
 */
public class OperUpdPatientConditionTakingMedicineStateParement {
    private             String              loginPatientPosition;
    private             String              requestClientType;
    private             String              operPatientCode;
    private             String              operPatientName;
    private             String              takingRecordId;
    private             String              patientCode;
    private             String              flagTakingMedicine;

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

    public String getTakingRecordId() {
        return takingRecordId;
    }

    public void setTakingRecordId(String takingRecordId) {
        this.takingRecordId = takingRecordId;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getFlagTakingMedicine() {
        return flagTakingMedicine;
    }

    public void setFlagTakingMedicine(String flagTakingMedicine) {
        this.flagTakingMedicine = flagTakingMedicine;
    }
}
