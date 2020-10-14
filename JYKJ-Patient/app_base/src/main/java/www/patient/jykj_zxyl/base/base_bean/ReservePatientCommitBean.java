package www.patient.jykj_zxyl.base.base_bean;

import com.google.gson.annotations.SerializedName;

public class ReservePatientCommitBean {
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
    private String status;
    private String message;
    private String confim;
    private int reserveType;
    /**
     * resCode : 1
     * resJsonData : {     		"confim":"462dc9e50ce9437faf88431d578bc64c",     		"message":"存在未支付的订单，请前往支付",     		"reservePatientDoctorInfo":{     			"mainDoctorCode":"250dda9c6ad544a4b6d16db7a2f1bcb6",     			"mainDoctorName":"新张强1",     			"mainPatientCode":"ae0b55c6be7f4b1588f0ea73b8653d26",     			"mainPatientName":"A～杨",     			"orderAndSignPrice":80.0000,     			"orderCode":"0101202009151617030271818356",     			"reserveCode":"f300efb3a53345828f10ffad9010b984",     			"reserveConfigStart":1600103700000,     			"reserveTimes":1600157823000,     			"reserveType":1,     			"treatmentType":1     		},     		"status":"3"     	}
     * resMsg : 校验业务参数
     */

    private String resJsonData;
    /**
     * reservePatientDoctorInfo : {"mainDoctorCode":"250dda9c6ad544a4b6d16db7a2f1bcb6","mainDoctorName":"新张强1","mainPatientCode":"ae0b55c6be7f4b1588f0ea73b8653d26","mainPatientName":"A～杨","orderAndSignPrice":80,"orderCode":"0101202009151617030271818356","reserveCode":"f300efb3a53345828f10ffad9010b984","reserveConfigStart":1600103700000,"reserveTimes":1600157823000,"reserveType":1,"treatmentType":1}
     */

    private ReservePatientDoctorInfoBean reservePatientDoctorInfo;


    public int getReserveType() {
        return reserveType;
    }

    public void setReserveType(int reserveType) {
        this.reserveType = reserveType;
    }

    public String getConfim() {
        return confim;
    }

    public void setConfim(String confim) {
        this.confim = confim;
    }

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


    public String getResJsonData() {
        return resJsonData;
    }

    public void setResJsonData(String resJsonData) {
        this.resJsonData = resJsonData;
    }

    public ReservePatientDoctorInfoBean getReservePatientDoctorInfo() {
        return reservePatientDoctorInfo;
    }

    public void setReservePatientDoctorInfo(ReservePatientDoctorInfoBean reservePatientDoctorInfo) {
        this.reservePatientDoctorInfo = reservePatientDoctorInfo;
    }

    public static class ReservePatientDoctorInfoBean {
        /**
         * mainDoctorCode : 250dda9c6ad544a4b6d16db7a2f1bcb6
         * mainDoctorName : 新张强1
         * mainPatientCode : ae0b55c6be7f4b1588f0ea73b8653d26
         * mainPatientName : A～杨
         * orderAndSignPrice : 80.0
         * orderCode : 0101202009151617030271818356
         * reserveCode : f300efb3a53345828f10ffad9010b984
         * reserveConfigStart : 1600103700000
         * reserveTimes : 1600157823000
         * reserveType : 1
         * treatmentType : 1
         */

        private String mainDoctorCode;
        private String mainDoctorName;
        private String mainPatientCode;
        private String mainPatientName;
        private double orderAndSignPrice;
        private String orderCode;
        private String reserveCode;
        private long reserveConfigStart;
        private long reserveTimes;
        private int reserveType;
        private int treatmentType;

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

        public double getOrderAndSignPrice() {
            return orderAndSignPrice;
        }

        public void setOrderAndSignPrice(double orderAndSignPrice) {
            this.orderAndSignPrice = orderAndSignPrice;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
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

        public String getReserveCode() {
            return reserveCode;
        }

        public void setReserveCode(String reserveCode) {
            this.reserveCode = reserveCode;
        }

    }
}
