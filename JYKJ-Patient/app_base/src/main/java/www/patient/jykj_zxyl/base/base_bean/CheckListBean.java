package www.patient.jykj_zxyl.base.base_bean;

import java.io.Serializable;
import java.util.List;

public class CheckListBean implements Serializable {


    /**
     * chiefComplaint : The first thing 1pplll
     * createDate : 1600756516000
     * departmentId : 1
     * departmentName : 内科
     * departmentSecondId : 14
     * departmentSecondName : 呼吸内科
     * diagnosisName : 冠心病,     		冠心病,     		冠心病,
     * doctorCode : 7b5d2d0205164f12974a3e228f5a6083
     * doctorName : 医生测试002
     * flagHistoryAllergy : 1
     * historyAllergy : gms
     * historyNew : Ppppp
     * historyPast : jws
     * hospitalInfoCode : 04d72a13c44e40848f07
     * hospitalInfoName : 北京市阜外医院
     * interactOrderInspectionList : [{"amount":1,"applyDepartmentName":"2222","createDate":1600165794000,"doctorCode":"7b5d2d0205164f12974a3e228f5a6083","gradeCode":"jcdj0075025f4a22a17241dcd04fb002","gradeContentCode":"02001","gradeContentName":"一般","inspectionCode":"1000000004","inspectionName":"胸部CT","inspectionParentCode":"20","inspectionParentName":"标本","inspectionTarget":"哦哦","inspectionTimes":1600963200000,"inspectionType":20,"inspectionTypeName":"标本","moneys":300,"orderCode":"0101202009181608445105661560","patientCode":"4dcc513a5dd34fa09a7a229a175e5c11","precautions":"test","price":300,"sampleOrLocationCode":"500000002,     \t\t\t\t500000003,     \t\t\t\t500000004","sampleOrLocationName":"胃,     \t\t\t\t肾脏,     \t\t\t\t肝脏","spec":"个","unit":"个"}]
     * medicalExamination : Ct
     * patientAge : 6
     * patientGender : 1
     * patientLogoUrl : https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eq84OzgEUTZfOS62xVNrkR0wfDIc5rIVWRwwDbcvjQiaxc5X98dDSrdopFmgGuaRtJKFbkWDXpe3Bw/132
     * patientName : 搬运工
     * prescribeSumAmount : 0.0
     * recordCode : 4ffb22478de546f79c0d2e95b8e3f64c
     * treatmentCardNum : 666888
     * viewMedicalHistory : 现病史：Ppppp
     既往史：jws
     过敏史：gms
     */

    private String chiefComplaint;
    private long createDate;
    private String departmentId;
    private String departmentName;
    private String departmentSecondId;
    private String departmentSecondName;
    private String diagnosisName;
    private String doctorCode;
    private String doctorName;
    private int flagHistoryAllergy;
    private String historyAllergy;
    private String historyNew;
    private String historyPast;
    private String hospitalInfoCode;
    private String hospitalInfoName;
    private String medicalExamination;
    private int patientAge;
    private int patientGender;
    private String patientLogoUrl;
    private String patientName;
    private double prescribeSumAmount;
    private String recordCode;
    private String treatmentCardNum;
    private String viewMedicalHistory;
    private List<InteractOrderInspectionListBean> interactOrderInspectionList;

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentSecondId() {
        return departmentSecondId;
    }

    public void setDepartmentSecondId(String departmentSecondId) {
        this.departmentSecondId = departmentSecondId;
    }

    public String getDepartmentSecondName() {
        return departmentSecondName;
    }

    public void setDepartmentSecondName(String departmentSecondName) {
        this.departmentSecondName = departmentSecondName;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
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

    public int getFlagHistoryAllergy() {
        return flagHistoryAllergy;
    }

    public void setFlagHistoryAllergy(int flagHistoryAllergy) {
        this.flagHistoryAllergy = flagHistoryAllergy;
    }

    public String getHistoryAllergy() {
        return historyAllergy;
    }

    public void setHistoryAllergy(String historyAllergy) {
        this.historyAllergy = historyAllergy;
    }

    public String getHistoryNew() {
        return historyNew;
    }

    public void setHistoryNew(String historyNew) {
        this.historyNew = historyNew;
    }

    public String getHistoryPast() {
        return historyPast;
    }

    public void setHistoryPast(String historyPast) {
        this.historyPast = historyPast;
    }

    public String getHospitalInfoCode() {
        return hospitalInfoCode;
    }

    public void setHospitalInfoCode(String hospitalInfoCode) {
        this.hospitalInfoCode = hospitalInfoCode;
    }

    public String getHospitalInfoName() {
        return hospitalInfoName;
    }

    public void setHospitalInfoName(String hospitalInfoName) {
        this.hospitalInfoName = hospitalInfoName;
    }

    public String getMedicalExamination() {
        return medicalExamination;
    }

    public void setMedicalExamination(String medicalExamination) {
        this.medicalExamination = medicalExamination;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public int getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(int patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientLogoUrl() {
        return patientLogoUrl;
    }

    public void setPatientLogoUrl(String patientLogoUrl) {
        this.patientLogoUrl = patientLogoUrl;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public double getPrescribeSumAmount() {
        return prescribeSumAmount;
    }

    public void setPrescribeSumAmount(double prescribeSumAmount) {
        this.prescribeSumAmount = prescribeSumAmount;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getTreatmentCardNum() {
        return treatmentCardNum;
    }

    public void setTreatmentCardNum(String treatmentCardNum) {
        this.treatmentCardNum = treatmentCardNum;
    }

    public String getViewMedicalHistory() {
        return viewMedicalHistory;
    }

    public void setViewMedicalHistory(String viewMedicalHistory) {
        this.viewMedicalHistory = viewMedicalHistory;
    }

    public List<InteractOrderInspectionListBean> getInteractOrderInspectionList() {
        return interactOrderInspectionList;
    }

    public void setInteractOrderInspectionList(List<InteractOrderInspectionListBean> interactOrderInspectionList) {
        this.interactOrderInspectionList = interactOrderInspectionList;
    }

    public static class InteractOrderInspectionListBean {
        /**
         * amount : 1.0
         * applyDepartmentName : 2222
         * createDate : 1600165794000
         * doctorCode : 7b5d2d0205164f12974a3e228f5a6083
         * gradeCode : jcdj0075025f4a22a17241dcd04fb002
         * gradeContentCode : 02001
         * gradeContentName : 一般
         * inspectionCode : 1000000004
         * inspectionName : 胸部CT
         * inspectionParentCode : 20
         * inspectionParentName : 标本
         * inspectionTarget : 哦哦
         * inspectionTimes : 1600963200000
         * inspectionType : 20
         * inspectionTypeName : 标本
         * moneys : 300.0
         * orderCode : 0101202009181608445105661560
         * patientCode : 4dcc513a5dd34fa09a7a229a175e5c11
         * precautions : test
         * price : 300.0
         * sampleOrLocationCode : 500000002,     				500000003,     				500000004
         * sampleOrLocationName : 胃,     				肾脏,     				肝脏
         * spec : 个
         * unit : 个
         */

        private double amount;
        private String applyDepartmentName;
        private long createDate;
        private String doctorCode;
        private String gradeCode;
        private String gradeContentCode;
        private String gradeContentName;
        private String inspectionCode;
        private String inspectionName;
        private String inspectionParentCode;
        private String inspectionParentName;
        private String inspectionTarget;
        private long inspectionTimes;
        private int inspectionType;
        private String inspectionTypeName;
        private double moneys;
        private String orderCode;
        private String patientCode;
        private String precautions;
        private double price;
        private String sampleOrLocationCode;
        private String sampleOrLocationName;
        private String spec;
        private String unit;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getApplyDepartmentName() {
            return applyDepartmentName;
        }

        public void setApplyDepartmentName(String applyDepartmentName) {
            this.applyDepartmentName = applyDepartmentName;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getDoctorCode() {
            return doctorCode;
        }

        public void setDoctorCode(String doctorCode) {
            this.doctorCode = doctorCode;
        }

        public String getGradeCode() {
            return gradeCode;
        }

        public void setGradeCode(String gradeCode) {
            this.gradeCode = gradeCode;
        }

        public String getGradeContentCode() {
            return gradeContentCode;
        }

        public void setGradeContentCode(String gradeContentCode) {
            this.gradeContentCode = gradeContentCode;
        }

        public String getGradeContentName() {
            return gradeContentName;
        }

        public void setGradeContentName(String gradeContentName) {
            this.gradeContentName = gradeContentName;
        }

        public String getInspectionCode() {
            return inspectionCode;
        }

        public void setInspectionCode(String inspectionCode) {
            this.inspectionCode = inspectionCode;
        }

        public String getInspectionName() {
            return inspectionName;
        }

        public void setInspectionName(String inspectionName) {
            this.inspectionName = inspectionName;
        }

        public String getInspectionParentCode() {
            return inspectionParentCode;
        }

        public void setInspectionParentCode(String inspectionParentCode) {
            this.inspectionParentCode = inspectionParentCode;
        }

        public String getInspectionParentName() {
            return inspectionParentName;
        }

        public void setInspectionParentName(String inspectionParentName) {
            this.inspectionParentName = inspectionParentName;
        }

        public String getInspectionTarget() {
            return inspectionTarget;
        }

        public void setInspectionTarget(String inspectionTarget) {
            this.inspectionTarget = inspectionTarget;
        }

        public long getInspectionTimes() {
            return inspectionTimes;
        }

        public void setInspectionTimes(long inspectionTimes) {
            this.inspectionTimes = inspectionTimes;
        }

        public int getInspectionType() {
            return inspectionType;
        }

        public void setInspectionType(int inspectionType) {
            this.inspectionType = inspectionType;
        }

        public String getInspectionTypeName() {
            return inspectionTypeName;
        }

        public void setInspectionTypeName(String inspectionTypeName) {
            this.inspectionTypeName = inspectionTypeName;
        }

        public double getMoneys() {
            return moneys;
        }

        public void setMoneys(double moneys) {
            this.moneys = moneys;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getPatientCode() {
            return patientCode;
        }

        public void setPatientCode(String patientCode) {
            this.patientCode = patientCode;
        }

        public String getPrecautions() {
            return precautions;
        }

        public void setPrecautions(String precautions) {
            this.precautions = precautions;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSampleOrLocationCode() {
            return sampleOrLocationCode;
        }

        public void setSampleOrLocationCode(String sampleOrLocationCode) {
            this.sampleOrLocationCode = sampleOrLocationCode;
        }

        public String getSampleOrLocationName() {
            return sampleOrLocationName;
        }

        public void setSampleOrLocationName(String sampleOrLocationName) {
            this.sampleOrLocationName = sampleOrLocationName;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
