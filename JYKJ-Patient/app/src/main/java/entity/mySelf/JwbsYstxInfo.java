package entity.mySelf;

import java.io.Serializable;
import java.util.Date;

public class JwbsYstxInfo implements Serializable {
    private Long createDate;
    private String doctorCode;
    private String doctorName;
    private Integer medicalId;
    private String medicalTypeName;
    private String patientCode;
    private String patientName;
    private String treatmentPlanCode;

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
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

    public Integer getMedicalId() {
        return medicalId;
    }

    public void setMedicalId(Integer medicalId) {
        this.medicalId = medicalId;
    }

    public String getMedicalTypeName() {
        return medicalTypeName;
    }

    public void setMedicalTypeName(String medicalTypeName) {
        this.medicalTypeName = medicalTypeName;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTreatmentPlanCode() {
        return treatmentPlanCode;
    }

    public void setTreatmentPlanCode(String treatmentPlanCode) {
        this.treatmentPlanCode = treatmentPlanCode;
    }
}
