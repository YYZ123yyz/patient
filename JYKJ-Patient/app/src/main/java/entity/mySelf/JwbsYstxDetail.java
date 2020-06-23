package entity.mySelf;

import java.io.Serializable;

public class JwbsYstxDetail implements Serializable {
    private String auxiliaryCheck;
    private String chiefComplaint;
    private String doctorCode;
    private String doctorName;
    private String medicalExamination;
    private String medicalTypeName;
    private String patientCode;
    private String patientName;
    private String presentIllness;
    private String treatmentPlanCode;

    public String getAuxiliaryCheck() {
        return auxiliaryCheck;
    }

    public void setAuxiliaryCheck(String auxiliaryCheck) {
        this.auxiliaryCheck = auxiliaryCheck;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
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

    public String getMedicalExamination() {
        return medicalExamination;
    }

    public void setMedicalExamination(String medicalExamination) {
        this.medicalExamination = medicalExamination;
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

    public String getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(String presentIllness) {
        this.presentIllness = presentIllness;
    }

    public String getTreatmentPlanCode() {
        return treatmentPlanCode;
    }

    public void setTreatmentPlanCode(String treatmentPlanCode) {
        this.treatmentPlanCode = treatmentPlanCode;
    }
}
