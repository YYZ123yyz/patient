package www.patient.jykj_zxyl.base.base_bean;

import java.util.List;

/**
 * Created by G on 2020/10/12 18:55
 */
public class RefusedOrderBean {
    /**
     * age : 1
     * audioCaochRateUnitCode :
     * audioCaochRateUnitName :
     * audioCoachDuration : 0
     * audioCoachDurationUnitCode :
     * audioCoachDurationUnitName :
     * audioCoachRate : 0
     * audioNum : 0
     * audioTotle : 0
     * callCaochRateUnitCode :
     * callCaochRateUnitName :
     * callCoachDuration : 0
     * callCoachDurationUnitCode :
     * callCoachDurationUnitName :
     * callCoachRate : 0
     * callNum : 0
     * callTotle : 0
     * detectRate : 1
     * detectRateUnitCode : 90051003
     * detectRateUnitName : 天
     * figureTextCaochRateUnitCode :
     * figureTextCaochRateUnitName :
     * figureTextCoachDuration : 0
     * figureTextCoachDurationUnitCode :
     * figureTextCoachDurationUnitName :
     * figureTextCoachRate : 0
     * figureTextNum : 0
     * figureTextTotle : 0
     * gender : 1
     * genderName : 男
     * mainDoctorAlias : 未设置
     * mainDoctorCode : e39b6a5bb5384ebfa4abd854c36e86ba
     * mainDoctorName : 齐研
     * mainPatientCode : df90285cf3be47e2b74f24837f0b4652
     * mainUserName : User_490886
     * mainUserNameAlias :
     * orderDetailList : [{"configDetailTypeCode":"10","configDetailTypeName":"监测类","mainConfigDetailCode":"110","mainConfigDetailName":"血压","mainSignCode":"bbc3c4d39a234d5f997ef699ef4327a8","rate":1,"rateUnitCode":"90051003","rateUnitName":"天","signOrderConfigDetailCode":"2050ca9693f54d1d83072fcd034f0882","signOrderConfigDetailId":4897,"totlePrice":10},{"configDetailTypeCode":"10","configDetailTypeName":"监测类","mainConfigDetailCode":"120","mainConfigDetailName":"服药","mainSignCode":"bbc3c4d39a234d5f997ef699ef4327a8","rate":1,"rateUnitCode":"90051003","rateUnitName":"天","signOrderConfigDetailCode":"372fce575bfa496c86adc940476eae69","signOrderConfigDetailId":4898,"totlePrice":20}]
     * showSignEndTime : 2021-10-12
     * showSignStartTime : 2020-10-12
     * signCode : bbc3c4d39a234d5f997ef699ef4327a8
     * signDuration : 12
     * signDurationUnit : 月
     * signEndTime : 1633968000000
     * signId : 661
     * signNo : 0104202010121851412903558882
     * signOtherServiceCode : 110,120
     * signOtherServiceName : 血压,服药
     * signPrice : 10950.0
     * signStartTime : 1602432000000
     * signStatus : 10
     * signUnit : 90051005
     * version : 2
     * videoCaochRateUnitCode :
     * videoCaochRateUnitName :
     * videoCoachDuration : 0
     * videoCoachDurationUnitCode :
     * videoCoachDurationUnitName :
     * videoCoachRate : 0
     * videoNum : 0
     * videoTotle : 0
     */

    private int age;
    private String audioCaochRateUnitCode;
    private String audioCaochRateUnitName;
    private int audioCoachDuration;
    private String audioCoachDurationUnitCode;
    private String audioCoachDurationUnitName;
    private int audioCoachRate;
    private int audioNum;
    private int audioTotle;
    private String callCaochRateUnitCode;
    private String callCaochRateUnitName;
    private int callCoachDuration;
    private String callCoachDurationUnitCode;
    private String callCoachDurationUnitName;
    private int callCoachRate;
    private int callNum;
    private int callTotle;
    private int detectRate;
    private String detectRateUnitCode;
    private String detectRateUnitName;
    private String figureTextCaochRateUnitCode;
    private String figureTextCaochRateUnitName;
    private int figureTextCoachDuration;
    private String figureTextCoachDurationUnitCode;
    private String figureTextCoachDurationUnitName;
    private int figureTextCoachRate;
    private int figureTextNum;
    private int figureTextTotle;
    private int gender;
    private String genderName;
    private String mainDoctorAlias;
    private String mainDoctorCode;
    private String mainDoctorName;
    private String mainPatientCode;
    private String mainUserName;
    private String mainUserNameAlias;
    private String showSignEndTime;
    private String showSignStartTime;
    private String signCode;
    private int signDuration;
    private String signDurationUnit;
    private long signEndTime;
    private int signId;
    private String signNo;
    private String signOtherServiceCode;
    private String signOtherServiceName;
    private double signPrice;
    private long signStartTime;
    private String signStatus;
    private String signUnit;
    private int version;
    private String videoCaochRateUnitCode;
    private String videoCaochRateUnitName;
    private int videoCoachDuration;
    private String videoCoachDurationUnitCode;
    private String videoCoachDurationUnitName;
    private int videoCoachRate;
    private int videoNum;
    private int videoTotle;
    private List<OrderDetailListBean> orderDetailList;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAudioCaochRateUnitCode() {
        return audioCaochRateUnitCode;
    }

    public void setAudioCaochRateUnitCode(String audioCaochRateUnitCode) {
        this.audioCaochRateUnitCode = audioCaochRateUnitCode;
    }

    public String getAudioCaochRateUnitName() {
        return audioCaochRateUnitName;
    }

    public void setAudioCaochRateUnitName(String audioCaochRateUnitName) {
        this.audioCaochRateUnitName = audioCaochRateUnitName;
    }

    public int getAudioCoachDuration() {
        return audioCoachDuration;
    }

    public void setAudioCoachDuration(int audioCoachDuration) {
        this.audioCoachDuration = audioCoachDuration;
    }

    public String getAudioCoachDurationUnitCode() {
        return audioCoachDurationUnitCode;
    }

    public void setAudioCoachDurationUnitCode(String audioCoachDurationUnitCode) {
        this.audioCoachDurationUnitCode = audioCoachDurationUnitCode;
    }

    public String getAudioCoachDurationUnitName() {
        return audioCoachDurationUnitName;
    }

    public void setAudioCoachDurationUnitName(String audioCoachDurationUnitName) {
        this.audioCoachDurationUnitName = audioCoachDurationUnitName;
    }

    public int getAudioCoachRate() {
        return audioCoachRate;
    }

    public void setAudioCoachRate(int audioCoachRate) {
        this.audioCoachRate = audioCoachRate;
    }

    public int getAudioNum() {
        return audioNum;
    }

    public void setAudioNum(int audioNum) {
        this.audioNum = audioNum;
    }

    public int getAudioTotle() {
        return audioTotle;
    }

    public void setAudioTotle(int audioTotle) {
        this.audioTotle = audioTotle;
    }

    public String getCallCaochRateUnitCode() {
        return callCaochRateUnitCode;
    }

    public void setCallCaochRateUnitCode(String callCaochRateUnitCode) {
        this.callCaochRateUnitCode = callCaochRateUnitCode;
    }

    public String getCallCaochRateUnitName() {
        return callCaochRateUnitName;
    }

    public void setCallCaochRateUnitName(String callCaochRateUnitName) {
        this.callCaochRateUnitName = callCaochRateUnitName;
    }

    public int getCallCoachDuration() {
        return callCoachDuration;
    }

    public void setCallCoachDuration(int callCoachDuration) {
        this.callCoachDuration = callCoachDuration;
    }

    public String getCallCoachDurationUnitCode() {
        return callCoachDurationUnitCode;
    }

    public void setCallCoachDurationUnitCode(String callCoachDurationUnitCode) {
        this.callCoachDurationUnitCode = callCoachDurationUnitCode;
    }

    public String getCallCoachDurationUnitName() {
        return callCoachDurationUnitName;
    }

    public void setCallCoachDurationUnitName(String callCoachDurationUnitName) {
        this.callCoachDurationUnitName = callCoachDurationUnitName;
    }

    public int getCallCoachRate() {
        return callCoachRate;
    }

    public void setCallCoachRate(int callCoachRate) {
        this.callCoachRate = callCoachRate;
    }

    public int getCallNum() {
        return callNum;
    }

    public void setCallNum(int callNum) {
        this.callNum = callNum;
    }

    public int getCallTotle() {
        return callTotle;
    }

    public void setCallTotle(int callTotle) {
        this.callTotle = callTotle;
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

    public String getFigureTextCaochRateUnitCode() {
        return figureTextCaochRateUnitCode;
    }

    public void setFigureTextCaochRateUnitCode(String figureTextCaochRateUnitCode) {
        this.figureTextCaochRateUnitCode = figureTextCaochRateUnitCode;
    }

    public String getFigureTextCaochRateUnitName() {
        return figureTextCaochRateUnitName;
    }

    public void setFigureTextCaochRateUnitName(String figureTextCaochRateUnitName) {
        this.figureTextCaochRateUnitName = figureTextCaochRateUnitName;
    }

    public int getFigureTextCoachDuration() {
        return figureTextCoachDuration;
    }

    public void setFigureTextCoachDuration(int figureTextCoachDuration) {
        this.figureTextCoachDuration = figureTextCoachDuration;
    }

    public String getFigureTextCoachDurationUnitCode() {
        return figureTextCoachDurationUnitCode;
    }

    public void setFigureTextCoachDurationUnitCode(String figureTextCoachDurationUnitCode) {
        this.figureTextCoachDurationUnitCode = figureTextCoachDurationUnitCode;
    }

    public String getFigureTextCoachDurationUnitName() {
        return figureTextCoachDurationUnitName;
    }

    public void setFigureTextCoachDurationUnitName(String figureTextCoachDurationUnitName) {
        this.figureTextCoachDurationUnitName = figureTextCoachDurationUnitName;
    }

    public int getFigureTextCoachRate() {
        return figureTextCoachRate;
    }

    public void setFigureTextCoachRate(int figureTextCoachRate) {
        this.figureTextCoachRate = figureTextCoachRate;
    }

    public int getFigureTextNum() {
        return figureTextNum;
    }

    public void setFigureTextNum(int figureTextNum) {
        this.figureTextNum = figureTextNum;
    }

    public int getFigureTextTotle() {
        return figureTextTotle;
    }

    public void setFigureTextTotle(int figureTextTotle) {
        this.figureTextTotle = figureTextTotle;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
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

    public String getShowSignEndTime() {
        return showSignEndTime;
    }

    public void setShowSignEndTime(String showSignEndTime) {
        this.showSignEndTime = showSignEndTime;
    }

    public String getShowSignStartTime() {
        return showSignStartTime;
    }

    public void setShowSignStartTime(String showSignStartTime) {
        this.showSignStartTime = showSignStartTime;
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

    public String getSignOtherServiceCode() {
        return signOtherServiceCode;
    }

    public void setSignOtherServiceCode(String signOtherServiceCode) {
        this.signOtherServiceCode = signOtherServiceCode;
    }

    public String getSignOtherServiceName() {
        return signOtherServiceName;
    }

    public void setSignOtherServiceName(String signOtherServiceName) {
        this.signOtherServiceName = signOtherServiceName;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVideoCaochRateUnitCode() {
        return videoCaochRateUnitCode;
    }

    public void setVideoCaochRateUnitCode(String videoCaochRateUnitCode) {
        this.videoCaochRateUnitCode = videoCaochRateUnitCode;
    }

    public String getVideoCaochRateUnitName() {
        return videoCaochRateUnitName;
    }

    public void setVideoCaochRateUnitName(String videoCaochRateUnitName) {
        this.videoCaochRateUnitName = videoCaochRateUnitName;
    }

    public int getVideoCoachDuration() {
        return videoCoachDuration;
    }

    public void setVideoCoachDuration(int videoCoachDuration) {
        this.videoCoachDuration = videoCoachDuration;
    }

    public String getVideoCoachDurationUnitCode() {
        return videoCoachDurationUnitCode;
    }

    public void setVideoCoachDurationUnitCode(String videoCoachDurationUnitCode) {
        this.videoCoachDurationUnitCode = videoCoachDurationUnitCode;
    }

    public String getVideoCoachDurationUnitName() {
        return videoCoachDurationUnitName;
    }

    public void setVideoCoachDurationUnitName(String videoCoachDurationUnitName) {
        this.videoCoachDurationUnitName = videoCoachDurationUnitName;
    }

    public int getVideoCoachRate() {
        return videoCoachRate;
    }

    public void setVideoCoachRate(int videoCoachRate) {
        this.videoCoachRate = videoCoachRate;
    }

    public int getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
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
         * configDetailTypeCode : 10
         * configDetailTypeName : 监测类
         * mainConfigDetailCode : 110
         * mainConfigDetailName : 血压
         * mainSignCode : bbc3c4d39a234d5f997ef699ef4327a8
         * rate : 1
         * rateUnitCode : 90051003
         * rateUnitName : 天
         * signOrderConfigDetailCode : 2050ca9693f54d1d83072fcd034f0882
         * signOrderConfigDetailId : 4897
         * totlePrice : 10.0
         */

        private String configDetailTypeCode;
        private String configDetailTypeName;
        private String mainConfigDetailCode;
        private String mainConfigDetailName;
        private String mainSignCode;
        private int rate;
        private String rateUnitCode;
        private String rateUnitName;
        private String signOrderConfigDetailCode;
        private int signOrderConfigDetailId;
        private double totlePrice;

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
    }
}
