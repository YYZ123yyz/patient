package entity;

import java.util.List;

public class addTaskingParment {

    private             String                  loginPatientPosition;
    private             String                  requestClientType;
    private             String                  operPatientCode;
    private             String                  operPatientName;
    private             String                  recordUserType;
    private             String                  patientCode;
    private             String                  takingMedicineType;
    private List<ProvideTakingMedicineInfo> takingMedicineInfoList;


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

    public String getRecordUserType() {
        return recordUserType;
    }

    public void setRecordUserType(String recordUserType) {
        this.recordUserType = recordUserType;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getTakingMedicineType() {
        return takingMedicineType;
    }

    public void setTakingMedicineType(String takingMedicineType) {
        this.takingMedicineType = takingMedicineType;
    }

    public List<ProvideTakingMedicineInfo> getTakingMedicineInfoList() {
        return takingMedicineInfoList;
    }

    public void setTakingMedicineInfoList(List<ProvideTakingMedicineInfo> takingMedicineInfoList) {
        this.takingMedicineInfoList = takingMedicineInfoList;
    }
}
