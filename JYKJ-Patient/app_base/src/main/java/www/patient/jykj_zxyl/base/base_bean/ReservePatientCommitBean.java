package www.patient.jykj_zxyl.base.base_bean;

public class ReservePatientCommitBean extends BaseStatusBean{

    /**
     * resCode : 1
     * resJsonData : {"status":"2","message":"您的身份证未填写，请填写身份证信息!"}
     * resMsg : 校验业务参数
     */

    private int resCode;
    private ResJsonDataBean resJsonData;
    private String resMsg;

    private String mainDoctorCode;
    private String mainDoctorName;
    private String mainPatientCode;
    private String mainPatientName;
    private int orderAndSignPrice;
    private String orderCode;
    private long reserveConfigEnd;
    private long reserveConfigStart;
    private String reserveProjectCode;
    private String reserveProjectName;
    private String reserveRosterDateCode;
    private long reserveTimes;
    private int treatmentType;
    private int viewReserveToDoctorCount;
    private String reserveStatus;

    public String getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

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

    public long getReserveConfigEnd() {
        return reserveConfigEnd;
    }

    public void setReserveConfigEnd(long reserveConfigEnd) {
        this.reserveConfigEnd = reserveConfigEnd;
    }

    public long getReserveConfigStart() {
        return reserveConfigStart;
    }

    public void setReserveConfigStart(long reserveConfigStart) {
        this.reserveConfigStart = reserveConfigStart;
    }

    public String getReserveProjectCode() {
        return reserveProjectCode;
    }

    public void setReserveProjectCode(String reserveProjectCode) {
        this.reserveProjectCode = reserveProjectCode;
    }

    public String getReserveProjectName() {
        return reserveProjectName;
    }

    public void setReserveProjectName(String reserveProjectName) {
        this.reserveProjectName = reserveProjectName;
    }

    public String getReserveRosterDateCode() {
        return reserveRosterDateCode;
    }

    public void setReserveRosterDateCode(String reserveRosterDateCode) {
        this.reserveRosterDateCode = reserveRosterDateCode;
    }

    public long getReserveTimes() {
        return reserveTimes;
    }

    public void setReserveTimes(long reserveTimes) {
        this.reserveTimes = reserveTimes;
    }

    public int getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(int treatmentType) {
        this.treatmentType = treatmentType;
    }

    public int getViewReserveToDoctorCount() {
        return viewReserveToDoctorCount;
    }

    public void setViewReserveToDoctorCount(int viewReserveToDoctorCount) {
        this.viewReserveToDoctorCount = viewReserveToDoctorCount;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public ResJsonDataBean getResJsonData() {
        return resJsonData;
    }

    public void setResJsonData(ResJsonDataBean resJsonData) {
        this.resJsonData = resJsonData;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public static class ResJsonDataBean {
        /**
         * status : 2
         * message : 您的身份证未填写，请填写身份证信息!
         */

        private String status="";
        private String message;
        /**
         * mainDoctorCode : 09fbf816f3df4fd896e97fd5b13b8b2b
         * mainDoctorName : 张强1
         * mainPatientCode : 54f4ee7c30124539b29879aae61dc786
         * mainPatientName : 张强
         * orderAndSignPrice : 200
         * orderCode : 0101202009031851288341510762
         * reserveConfigEnd : 1599403680000
         * reserveConfigStart : 1599403320000
         * reserveProjectCode : 1
         * reserveProjectName : 图文就诊
         * reserveRosterDateCode : 2812bfec0a9945bea7fc658ce22ec800
         * reserveTimes : 1599321600000
         * treatmentType : 1
         * viewReserveToDoctorCount : 8
         */


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
