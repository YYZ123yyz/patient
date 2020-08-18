package www.patient.jykj_zxyl.base.base_bean;

import java.util.List;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-07-31 09:36
 */
public class OrderDetialBean {


    /**
     * audioLastCount : 0
     * audioTotle : 2
     * callLastCount : 0
     * callTotle : 2
     * createDate : 1595957058000
     * createMan : af50b8a090144fa8b4ffc6ecfe9ff0de
     * detectRate : 3
     * detectRateUnitCode : DAY
     * detectRateUnitName : 天
     * figureTextLastCount : 0
     * figureTextTotle : 2
     * flagDel : 1
     * mainDoctorAlias : 未配置
     * mainDoctorCode : af50b8a090144fa8b4ffc6ecfe9ff0de
     * mainDoctorName : 贾青
     * mainPatientCode : ae0b55c6be7f4b1588f0ea73b8653d26
     * mainUserName : A～杨
     * mainUserNameAlias : A～杨
     * orderDetailList : [{"configDetailTypeCode":"001","configDetailTypeName":"健康检测","createDate":1595957058000,"createMan":"af50b8a090144fa8b4ffc6ecfe9ff0de","duration":20,"durationUnitCode":"FEN","durationUnitName":"分钟","flagDel":1,"mainConfigDetailCode":"001","mainConfigDetailName":"血压","mainSignCode":"46bfb3db89df4b198eee3f1105355fdf","rate":15,"rateUnitCode":"DAY","rateUnitName":"天","signOrderConfigDetailCode":"2ec1e59f-d0f7-11ea-a536-00ffaabbccdd","signOrderConfigDetailId":12,"totlePrice":50,"updateDate":1596075245000,"updateMan":"af50b8a090144fa8b4ffc6ecfe9ff0de","value":20},{"configDetailTypeCode":"001","configDetailTypeName":"健康检测","createDate":1595957058000,"createMan":"af50b8a090144fa8b4ffc6ecfe9ff0de","duration":20,"durationUnitCode":"FEN","durationUnitName":"分钟","flagDel":1,"mainConfigDetailCode":"002","mainConfigDetailName":"睡眠","mainSignCode":"46bfb3db89df4b198eee3f1105355fdf","rate":15,"rateUnitCode":"DAY","rateUnitName":"天","signOrderConfigDetailCode":"2ec1f992-d0f7-11ea-a536-00ffaabbccdd","signOrderConfigDetailId":13,"totlePrice":60,"updateDate":1596075245000,"updateMan":"af50b8a090144fa8b4ffc6ecfe9ff0de","value":30}]
     * signCode : 46bfb3db89df4b198eee3f1105355fdf
     * signDuration : 12
     * signDurationUnit : 12月
     * signEndTime : 1627401600000
     * signId : 7
     * signNo : 20200729012418566618
     * signPrice : 6000.0
     * signStartTime : 1595865600000
     * signStatus : 10
     * signUnit : 月
     * updateDate : 1596079031000
     * updateMan : ae0b55c6be7f4b1588f0ea73b8653d26
     * version : 4
     * videoLastCount : 0
     * videoTotle : 2
     */

    private int audioLastCount;
    private int audioTotle;
    private int callLastCount;
    private int callTotle;
    private long createDate;
    private String createMan;
    private int detectRate;
    private String detectRateUnitCode;
    private String detectRateUnitName;
    private int figureTextLastCount;
    private int figureTextTotle;
    private int flagDel;
    private String mainDoctorAlias;
    private String mainDoctorCode;
    private String mainDoctorName;
    private String mainPatientCode;
    private String mainUserName;
    private String mainUserNameAlias;
    private String patientAge;
    private String patientGender;

    private String signCode;
    private int signDuration;
    private String signDurationUnit;
    private long signEndTime;
    private int signId;
    private String signNo;
    private double signPrice;
    private long signStartTime;
    private String signStatus;
    private String signUnit;
    private long updateDate;
    private String updateMan;
    private int version;
    private int videoLastCount;
    private int videoTotle;
    //扩展
    private  String refuseReasonClassCode;  //拒绝/解约原因分类code
    private  String refuseReasonClassName;  //拒绝/解约原因分类名称
    private List<OrderDetailListBean> orderDetailList;

    public int getAudioLastCount() {
        return audioLastCount;
    }

    public void setAudioLastCount(int audioLastCount) {
        this.audioLastCount = audioLastCount;
    }

    public int getAudioTotle() {
        return audioTotle;
    }

    public void setAudioTotle(int audioTotle) {
        this.audioTotle = audioTotle;
    }

    public int getCallLastCount() {
        return callLastCount;
    }

    public void setCallLastCount(int callLastCount) {
        this.callLastCount = callLastCount;
    }

    public int getCallTotle() {
        return callTotle;
    }

    public void setCallTotle(int callTotle) {
        this.callTotle = callTotle;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public int getDetectRate() {
        return detectRate;
    }

    public void setDetectRate(int detectRate) {
        this.detectRate = detectRate;
    }

    public String getDetectRateUnitCode() {
        return detectRateUnitCode;
    }

    public void setDetectRateUnitCode(String detectRateUnitCode) {
        this.detectRateUnitCode = detectRateUnitCode;
    }

    public String getDetectRateUnitName() {
        return detectRateUnitName;
    }

    public void setDetectRateUnitName(String detectRateUnitName) {
        this.detectRateUnitName = detectRateUnitName;
    }

    public int getFigureTextLastCount() {
        return figureTextLastCount;
    }

    public void setFigureTextLastCount(int figureTextLastCount) {
        this.figureTextLastCount = figureTextLastCount;
    }

    public int getFigureTextTotle() {
        return figureTextTotle;
    }

    public void setFigureTextTotle(int figureTextTotle) {
        this.figureTextTotle = figureTextTotle;
    }

    public int getFlagDel() {
        return flagDel;
    }

    public void setFlagDel(int flagDel) {
        this.flagDel = flagDel;
    }

    public String getMainDoctorAlias() {
        return mainDoctorAlias;
    }

    public void setMainDoctorAlias(String mainDoctorAlias) {
        this.mainDoctorAlias = mainDoctorAlias;
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

    public String getMainUserName() {
        return mainUserName;
    }

    public void setMainUserName(String mainUserName) {
        this.mainUserName = mainUserName;
    }

    public String getMainUserNameAlias() {
        return mainUserNameAlias;
    }

    public void setMainUserNameAlias(String mainUserNameAlias) {
        this.mainUserNameAlias = mainUserNameAlias;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public int getSignDuration() {
        return signDuration;
    }

    public void setSignDuration(int signDuration) {
        this.signDuration = signDuration;
    }

    public String getSignDurationUnit() {
        return signDurationUnit;
    }

    public void setSignDurationUnit(String signDurationUnit) {
        this.signDurationUnit = signDurationUnit;
    }

    public long getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(long signEndTime) {
        this.signEndTime = signEndTime;
    }

    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public double getSignPrice() {
        return signPrice;
    }

    public void setSignPrice(double signPrice) {
        this.signPrice = signPrice;
    }

    public long getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(long signStartTime) {
        this.signStartTime = signStartTime;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getSignUnit() {
        return signUnit;
    }

    public void setSignUnit(String signUnit) {
        this.signUnit = signUnit;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(String updateMan) {
        this.updateMan = updateMan;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public int getVideoLastCount() {
        return videoLastCount;
    }

    public void setVideoLastCount(int videoLastCount) {
        this.videoLastCount = videoLastCount;
    }

    public int getVideoTotle() {
        return videoTotle;
    }

    public void setVideoTotle(int videoTotle) {
        this.videoTotle = videoTotle;
    }

    public List<OrderDetailListBean> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailListBean> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public static class OrderDetailListBean {
        /**
         * configDetailTypeCode : 001
         * configDetailTypeName : 健康检测
         * createDate : 1595957058000
         * createMan : af50b8a090144fa8b4ffc6ecfe9ff0de
         * duration : 20
         * durationUnitCode : FEN
         * durationUnitName : 分钟
         * flagDel : 1
         * mainConfigDetailCode : 001
         * mainConfigDetailName : 血压
         * mainSignCode : 46bfb3db89df4b198eee3f1105355fdf
         * rate : 15
         * rateUnitCode : DAY
         * rateUnitName : 天
         * signOrderConfigDetailCode : 2ec1e59f-d0f7-11ea-a536-00ffaabbccdd
         * signOrderConfigDetailId : 12
         * totlePrice : 50.0
         * updateDate : 1596075245000
         * updateMan : af50b8a090144fa8b4ffc6ecfe9ff0de
         * value : 20
         */

        private String configDetailTypeCode;
        private String configDetailTypeName;
        private long createDate;
        private String createMan;
        private int duration;
        private String durationUnitCode;
        private String durationUnitName;
        private int flagDel;
        private String mainConfigDetailCode;
        private String mainConfigDetailName;
        private String mainSignCode;
        private int rate;
        private String rateUnitCode;
        private String rateUnitName;
        private String signOrderConfigDetailCode;
        private int signOrderConfigDetailId;
        private double totlePrice;
        private long updateDate;
        private String updateMan;
        private int value;

        public String getConfigDetailTypeCode() {
            return configDetailTypeCode;
        }

        public void setConfigDetailTypeCode(String configDetailTypeCode) {
            this.configDetailTypeCode = configDetailTypeCode;
        }

        public String getConfigDetailTypeName() {
            return configDetailTypeName;
        }

        public void setConfigDetailTypeName(String configDetailTypeName) {
            this.configDetailTypeName = configDetailTypeName;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getCreateMan() {
            return createMan;
        }

        public void setCreateMan(String createMan) {
            this.createMan = createMan;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getDurationUnitCode() {
            return durationUnitCode;
        }

        public void setDurationUnitCode(String durationUnitCode) {
            this.durationUnitCode = durationUnitCode;
        }

        public String getDurationUnitName() {
            return durationUnitName;
        }

        public void setDurationUnitName(String durationUnitName) {
            this.durationUnitName = durationUnitName;
        }

        public int getFlagDel() {
            return flagDel;
        }

        public void setFlagDel(int flagDel) {
            this.flagDel = flagDel;
        }

        public String getMainConfigDetailCode() {
            return mainConfigDetailCode;
        }

        public void setMainConfigDetailCode(String mainConfigDetailCode) {
            this.mainConfigDetailCode = mainConfigDetailCode;
        }

        public String getMainConfigDetailName() {
            return mainConfigDetailName;
        }

        public void setMainConfigDetailName(String mainConfigDetailName) {
            this.mainConfigDetailName = mainConfigDetailName;
        }

        public String getMainSignCode() {
            return mainSignCode;
        }

        public void setMainSignCode(String mainSignCode) {
            this.mainSignCode = mainSignCode;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public String getRateUnitCode() {
            return rateUnitCode;
        }

        public void setRateUnitCode(String rateUnitCode) {
            this.rateUnitCode = rateUnitCode;
        }

        public String getRateUnitName() {
            return rateUnitName;
        }

        public void setRateUnitName(String rateUnitName) {
            this.rateUnitName = rateUnitName;
        }

        public String getSignOrderConfigDetailCode() {
            return signOrderConfigDetailCode;
        }

        public void setSignOrderConfigDetailCode(String signOrderConfigDetailCode) {
            this.signOrderConfigDetailCode = signOrderConfigDetailCode;
        }

        public int getSignOrderConfigDetailId() {
            return signOrderConfigDetailId;
        }

        public void setSignOrderConfigDetailId(int signOrderConfigDetailId) {
            this.signOrderConfigDetailId = signOrderConfigDetailId;
        }

        public double getTotlePrice() {
            return totlePrice;
        }

        public void setTotlePrice(double totlePrice) {
            this.totlePrice = totlePrice;
        }

        public long getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(long updateDate) {
            this.updateDate = updateDate;
        }

        public String getUpdateMan() {
            return updateMan;
        }

        public void setUpdateMan(String updateMan) {
            this.updateMan = updateMan;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public String getRefuseReasonClassCode() {
        return refuseReasonClassCode;
    }

    public void setRefuseReasonClassCode(String refuseReasonClassCode) {
        this.refuseReasonClassCode = refuseReasonClassCode;
    }

    public String getRefuseReasonClassName() {
        return refuseReasonClassName;
    }

    public void setRefuseReasonClassName(String refuseReasonClassName) {
        this.refuseReasonClassName = refuseReasonClassName;
    }
}
