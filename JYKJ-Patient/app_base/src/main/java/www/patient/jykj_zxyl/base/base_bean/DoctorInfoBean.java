package www.patient.jykj_zxyl.base.base_bean;

public class DoctorInfoBean {
    private String mainDoctorCode;
    private String mainDoctorName;
    private String mainPatientCode;
    private String mainPatientName;
    private int orderAndSignPrice;
    private String orderCode;
    private String reserveCode;
    private long reserveConfigStart;
    private long reserveTimes;
    private int reserveType;
    private int  treatmentType;

    public String getMainDoctorCode() {
        return mainDoctorCode;
    }

    public void setMainDoctorCode(String mainDoctorCode) {
        this.mainDoctorCode = mainDoctorCode;
    }

    public String getMainDoctorName() {
        return mainDoctorName;
    }

    public void setMainDoctorName(String mainDoctorName) {
        this.mainDoctorName = mainDoctorName;
    }

    public String getMainPatientCode() {
        return mainPatientCode;
    }

    public void setMainPatientCode(String mainPatientCode) {
        this.mainPatientCode = mainPatientCode;
    }

    public String getMainPatientName() {
        return mainPatientName;
    }

    public void setMainPatientName(String mainPatientName) {
        this.mainPatientName = mainPatientName;
    }

    public int getOrderAndSignPrice() {
        return orderAndSignPrice;
    }

    public void setOrderAndSignPrice(int orderAndSignPrice) {
        this.orderAndSignPrice = orderAndSignPrice;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(String reserveCode) {
        this.reserveCode = reserveCode;
    }

    public long getReserveConfigStart() {
        return reserveConfigStart;
    }

    public void setReserveConfigStart(long reserveConfigStart) {
        this.reserveConfigStart = reserveConfigStart;
    }

    public long getReserveTimes() {
        return reserveTimes;
    }

    public void setReserveTimes(long reserveTimes) {
        this.reserveTimes = reserveTimes;
    }

    public int getReserveType() {
        return reserveType;
    }

    public void setReserveType(int reserveType) {
        this.reserveType = reserveType;
    }

    public int getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(int treatmentType) {
        this.treatmentType = treatmentType;
    }
}
