package www.patient.jykj_zxyl.bean;

/**
 * Created by G on 2020/10/15 10:17
 */
public class HistoryDetBean {

    /**
     * imgCode : eb14d46aaec14a12b4c64d408c8cf006
     * patientCode : 997667ba22e84a2a91ce735a1a5043f8
     * recordContent : hjhgu
     * recordId : 81
     * recordImgArray : https://jiuyihtn.com/fileUpload/patientConditionDiseaseRecordImage/diseaseRecord/997667ba22e84a2a91ce735a1a5043f8/DiseaseRecord_20201014174411_1.jpg,https://jiuyihtn.com/fileUpload/patientConditionDiseaseRecordImage/diseaseRecord/997667ba22e84a2a91ce735a1a5043f8/DiseaseRecord_20201014174411_2.jpg,https://jiuyihtn.com/fileUpload/patientConditionDiseaseRecordImage/diseaseRecord/997667ba22e84a2a91ce735a1a5043f8/DiseaseRecord_20201014174411_3.jpg
     * recordImgIdArray : 2,3,4
     * recordName : hjii
     * treatmentDate : 1602604800000
     */

    private String imgCode;
    private String patientCode;
    private String recordContent;
    private int recordId;
    private String recordImgArray;
    private String recordImgIdArray;
    private String recordName;
    private long treatmentDate;

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getRecordImgArray() {
        return recordImgArray;
    }

    public void setRecordImgArray(String recordImgArray) {
        this.recordImgArray = recordImgArray;
    }

    public String getRecordImgIdArray() {
        return recordImgIdArray;
    }

    public void setRecordImgIdArray(String recordImgIdArray) {
        this.recordImgIdArray = recordImgIdArray;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public long getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(long treatmentDate) {
        this.treatmentDate = treatmentDate;
    }
}
