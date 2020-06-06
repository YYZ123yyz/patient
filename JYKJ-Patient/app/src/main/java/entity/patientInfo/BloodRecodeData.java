package entity.patientInfo;

public class BloodRecodeData {

    private int bloodPressureId;
    private int bloodTypeSecond;
    private String bloodTypeSecondName;
    private int heartRateNum;
    private int highPressureNum;
    private int lowPressureNum;
    private String patientCode;
    private long recordDate;
    private long recordTime;
    private int recordTimeType;
    private int recordType;

    public int getBloodPressureId() {
        return bloodPressureId;
    }

    public void setBloodPressureId(int bloodPressureId) {
        this.bloodPressureId = bloodPressureId;
    }

    public int getBloodTypeSecond() {
        return bloodTypeSecond;
    }

    public void setBloodTypeSecond(int bloodTypeSecond) {
        this.bloodTypeSecond = bloodTypeSecond;
    }

    public String getBloodTypeSecondName() {
        return bloodTypeSecondName;
    }

    public void setBloodTypeSecondName(String bloodTypeSecondName) {
        this.bloodTypeSecondName = bloodTypeSecondName;
    }

    public int getHeartRateNum() {
        return heartRateNum;
    }

    public void setHeartRateNum(int heartRateNum) {
        this.heartRateNum = heartRateNum;
    }

    public int getHighPressureNum() {
        return highPressureNum;
    }

    public void setHighPressureNum(int highPressureNum) {
        this.highPressureNum = highPressureNum;
    }

    public int getLowPressureNum() {
        return lowPressureNum;
    }

    public void setLowPressureNum(int lowPressureNum) {
        this.lowPressureNum = lowPressureNum;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public long getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(long recordDate) {
        this.recordDate = recordDate;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public int getRecordTimeType() {
        return recordTimeType;
    }

    public void setRecordTimeType(int recordTimeType) {
        this.recordTimeType = recordTimeType;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }
}
