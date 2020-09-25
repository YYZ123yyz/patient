package www.patient.jykj_zxyl.base.base_bean;

import java.io.Serializable;
import java.util.List;

public class PrescriptionDetBean implements Serializable {

    /**
     * createDate : 1600756516000
     * departmentId : 1
     * departmentName : 内科
     * departmentSecondId : 14
     * departmentSecondName : 呼吸内科
     * diagnosisCode : jibingzhenduanmingcheng001,jibingzhenduanmingcheng001,jibingzhenduanmingcheng001,
     * diagnosisName : 冠心病,冠心病,冠心病,
     * doctorCode : 7b5d2d0205164f12974a3e228f5a6083
     * doctorName : 医生测试002
     * hospitalInfoCode : 04d72a13c44e40848f07
     * hospitalInfoName : 北京市阜外医院
     * interactOrderPrescribeList : [{"prescribeInfo":[{"drugAmount":7,"drugAmountName":"(1盒/24粒)7.0/盒","drugCode":"be87duf92m044326b2a6e856bdabd001","drugMoneys":77,"drugName":"风热颗粒","drugOrderCode":"1a8df9a5c5c448429723497e599cb2d8","drugPack":"盒","orderCode":"0101202009181608445105661560","patientCode":"4dcc513a5dd34fa09a7a229a175e5c11","patientName":"Pan","prescribeDate":1600849246000,"prescribeType":1003301,"prescribeTypeName":"平台处方","prescribeVoucher":"6a433e94cefd4454b114ddfa49b28cae","specName":"1盒/24粒","specUnit":"粒","useCycle":7,"useDesc":"7777777","useFrequency":1,"useFrequencyCode":"1003501","useFrequencyName":"每天一次","useNum":7,"useNumName":"7 粒/次"}]},{"prescribeInfo":[{"drugAmount":5,"drugAmountName":"(1盒/24片)5.0/盒","drugCode":"be87duf92m044326b2a6e856bdabd017","drugMoneys":195,"drugName":"庆大霉素片","drugOrderCode":"0100926ef32149c08d9727e8616abd5c","drugPack":"盒","orderCode":"0101202009181608445105661560","patientCode":"4dcc513a5dd34fa09a7a229a175e5c11","patientName":"Pan","prescribeDate":1600864511000,"prescribeType":1003301,"prescribeTypeName":"平台处方","prescribeVoucher":"78731234489c4f2b844e7c86298a2247","specName":"1盒/24片","specUnit":"片","useCycle":1,"useDesc":"55","useFrequency":1,"useFrequencyCode":"1003501","useFrequencyName":"每天一次","useNum":6,"useNumName":"6 片/次"}]},{"prescribeInfo":[{"drugAmount":3,"drugAmountName":"(1盒/12瓶)3.0/盒","drugCode":"be87duf92m044326b2a6e856bdabd004","drugMoneys":42,"drugName":"霍香正气水","drugOrderCode":"212636319b364505b16b0f57800a327b","drugPack":"盒","orderCode":"0101202009181608445105661560","patientCode":"dafd840808d64027b64893eed11b97b8","patientName":"搬运工","prescribeDate":1600864511000,"prescribeType":1003301,"prescribeTypeName":"平台处方","prescribeVoucher":"aec6eaec67b24dc9b011cc6653c3e2bf","specName":"1盒/12瓶","specUnit":"瓶","useCycle":1,"useDesc":"我","useFrequency":1,"useFrequencyCode":"1003501","useFrequencyName":"每天一次","useNum":6,"useNumName":"6 瓶/次"}]},{"prescribeInfo":[{"drugAmount":1,"drugAmountName":"(1盒/24粒)1.0/盒","drugCode":"be87duf92m044326b2a6e856bdabd001","drugMoneys":11,"drugName":"风热颗粒","drugOrderCode":"27974250a35c44b68725712673afeddd","drugPack":"盒","orderCode":"0101202009181608445105661560","patientCode":"4dcc513a5dd34fa09a7a229a175e5c11","patientName":"Pan","prescribeDate":1600862968000,"prescribeType":1003302,"prescribeTypeName":"医院处方","prescribeVoucher":"b97f861d13324ce2af8660cc35ff6bfc","specName":"1盒/24粒","specUnit":"粒","useCycle":1,"useDesc":"急急急","useFrequency":1,"useFrequencyCode":"1003501","useFrequencyName":"每天一次","useNum":3,"useNumName":"3 粒/次"},{"drugAmount":3,"drugAmountName":"(1瓶/50片)3.0/瓶","drugCode":"be87duf92m044326b2a6e856bdabd013","drugMoneys":84,"drugName":"复方丹参片","drugOrderCode":"b4f46d1ade124db7b00617ffe19aa760","drugPack":"瓶","orderCode":"0101202009181608445105661560","patientCode":"4dcc513a5dd34fa09a7a229a175e5c11","patientName":"Pan","prescribeDate":1600862968000,"prescribeType":1003301,"prescribeTypeName":"平台处方","prescribeVoucher":"b97f861d13324ce2af8660cc35ff6bfc","specName":"1瓶/50片","specUnit":"片","useCycle":4,"useDesc":"3333333333333333333333333333333333333333333333333333","useFrequency":1,"useFrequencyCode":"1003501","useFrequencyName":"每天一次","useNum":4,"useNumName":"4 片/次"}]},{"prescribeInfo":[{"drugAmount":4,"drugAmountName":"(1盒/24粒)4.0/盒","drugCode":"be87duf92m044326b2a6e856bdabd007","drugMoneys":92,"drugName":"藿香正气软胶囊","drugOrderCode":"059e251e75eb465bb81ee9c5878f942d","drugPack":"盒","orderCode":"0101202009181608445105661560","patientCode":"4dcc513a5dd34fa09a7a229a175e5c11","patientName":"Pan","prescribeDate":1600854548000,"prescribeType":1003301,"prescribeTypeName":"平台处方","prescribeVoucher":"cb2411ca21464224a4e497b2a7f17aea","specName":"1盒/24粒","specUnit":"粒","useCycle":3,"useDesc":"4","useFrequency":1,"useFrequencyCode":"1003501","useFrequencyName":"每天一次","useNum":4,"useNumName":"4 粒/次"}]}]
     * patientAge : 6
     * patientGender : 1
     * patientName : 搬运工
     * prescribeSumAmount : 501.0
     * recordCode : 4ffb22478de546f79c0d2e95b8e3f64c
     * treatmentCardNum : 666888
     */

    private long createDate;
    private String departmentId;
    private String departmentName;
    private String departmentSecondId;
    private String departmentSecondName;
    private String diagnosisCode;
    private String diagnosisName;
    private String doctorCode;
    private String doctorName;
    private String hospitalInfoCode;
    private String hospitalInfoName;
    private int patientAge;
    private int patientGender;
    private String patientName;
    private double prescribeSumAmount;
    private String recordCode;
    private String treatmentCardNum;
    private List<InteractOrderPrescribeListBean> interactOrderPrescribeList;

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

    public String getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        this.diagnosisCode = diagnosisCode;
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

    public List<InteractOrderPrescribeListBean> getInteractOrderPrescribeList() {
        return interactOrderPrescribeList;
    }

    public void setInteractOrderPrescribeList(List<InteractOrderPrescribeListBean> interactOrderPrescribeList) {
        this.interactOrderPrescribeList = interactOrderPrescribeList;
    }

    public static class InteractOrderPrescribeListBean {
        private List<PrescribeInfoBean> prescribeInfo;

        public List<PrescribeInfoBean> getPrescribeInfo() {
            return prescribeInfo;
        }

        public void setPrescribeInfo(List<PrescribeInfoBean> prescribeInfo) {
            this.prescribeInfo = prescribeInfo;
        }

        public static class PrescribeInfoBean {
            /**
             * drugAmount : 7.0
             * drugAmountName : (1盒/24粒)7.0/盒
             * drugCode : be87duf92m044326b2a6e856bdabd001
             * drugMoneys : 77.0
             * drugName : 风热颗粒
             * drugOrderCode : 1a8df9a5c5c448429723497e599cb2d8
             * drugPack : 盒
             * orderCode : 0101202009181608445105661560
             * patientCode : 4dcc513a5dd34fa09a7a229a175e5c11
             * patientName : Pan
             * prescribeDate : 1600849246000
             * prescribeType : 1003301
             * prescribeTypeName : 平台处方
             * prescribeVoucher : 6a433e94cefd4454b114ddfa49b28cae
             * specName : 1盒/24粒
             * specUnit : 粒
             * useCycle : 7
             * useDesc : 7777777
             * useFrequency : 1
             * useFrequencyCode : 1003501
             * useFrequencyName : 每天一次
             * useNum : 7
             * useNumName : 7 粒/次
             */

            private double drugAmount;
            private String drugAmountName;
            private String drugCode;
            private double drugMoneys;
            private String drugName;
            private String drugOrderCode;
            private String drugPack;
            private String orderCode;
            private String patientCode;
            private String patientName;
            private long prescribeDate;
            private int prescribeType;
            private String prescribeTypeName;
            private String prescribeVoucher;
            private String specName;
            private String specUnit;
            private int useCycle;
            private String useDesc;
            private int useFrequency;
            private String useFrequencyCode;
            private String useFrequencyName;
            private int useNum;
            private String useNumName;
            private int type;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public double getDrugAmount() {
                return drugAmount;
            }

            public void setDrugAmount(double drugAmount) {
                this.drugAmount = drugAmount;
            }

            public String getDrugAmountName() {
                return drugAmountName;
            }

            public void setDrugAmountName(String drugAmountName) {
                this.drugAmountName = drugAmountName;
            }

            public String getDrugCode() {
                return drugCode;
            }

            public void setDrugCode(String drugCode) {
                this.drugCode = drugCode;
            }

            public double getDrugMoneys() {
                return drugMoneys;
            }

            public void setDrugMoneys(double drugMoneys) {
                this.drugMoneys = drugMoneys;
            }

            public String getDrugName() {
                return drugName;
            }

            public void setDrugName(String drugName) {
                this.drugName = drugName;
            }

            public String getDrugOrderCode() {
                return drugOrderCode;
            }

            public void setDrugOrderCode(String drugOrderCode) {
                this.drugOrderCode = drugOrderCode;
            }

            public String getDrugPack() {
                return drugPack;
            }

            public void setDrugPack(String drugPack) {
                this.drugPack = drugPack;
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

            public String getPatientName() {
                return patientName;
            }

            public void setPatientName(String patientName) {
                this.patientName = patientName;
            }

            public long getPrescribeDate() {
                return prescribeDate;
            }

            public void setPrescribeDate(long prescribeDate) {
                this.prescribeDate = prescribeDate;
            }

            public int getPrescribeType() {
                return prescribeType;
            }

            public void setPrescribeType(int prescribeType) {
                this.prescribeType = prescribeType;
            }

            public String getPrescribeTypeName() {
                return prescribeTypeName;
            }

            public void setPrescribeTypeName(String prescribeTypeName) {
                this.prescribeTypeName = prescribeTypeName;
            }

            public String getPrescribeVoucher() {
                return prescribeVoucher;
            }

            public void setPrescribeVoucher(String prescribeVoucher) {
                this.prescribeVoucher = prescribeVoucher;
            }

            public String getSpecName() {
                return specName;
            }

            public void setSpecName(String specName) {
                this.specName = specName;
            }

            public String getSpecUnit() {
                return specUnit;
            }

            public void setSpecUnit(String specUnit) {
                this.specUnit = specUnit;
            }

            public int getUseCycle() {
                return useCycle;
            }

            public void setUseCycle(int useCycle) {
                this.useCycle = useCycle;
            }

            public String getUseDesc() {
                return useDesc;
            }

            public void setUseDesc(String useDesc) {
                this.useDesc = useDesc;
            }

            public int getUseFrequency() {
                return useFrequency;
            }

            public void setUseFrequency(int useFrequency) {
                this.useFrequency = useFrequency;
            }

            public String getUseFrequencyCode() {
                return useFrequencyCode;
            }

            public void setUseFrequencyCode(String useFrequencyCode) {
                this.useFrequencyCode = useFrequencyCode;
            }

            public String getUseFrequencyName() {
                return useFrequencyName;
            }

            public void setUseFrequencyName(String useFrequencyName) {
                this.useFrequencyName = useFrequencyName;
            }

            public int getUseNum() {
                return useNum;
            }

            public void setUseNum(int useNum) {
                this.useNum = useNum;
            }

            public String getUseNumName() {
                return useNumName;
            }

            public void setUseNumName(String useNumName) {
                this.useNumName = useNumName;
            }
        }
    }
}
