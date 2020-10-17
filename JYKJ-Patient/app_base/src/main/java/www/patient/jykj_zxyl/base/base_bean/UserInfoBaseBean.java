package www.patient.jykj_zxyl.base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-07 10:48
 */
public class UserInfoBaseBean {


    /**
     * gender : 1
     * newLoginDate : 2020-08-07
     * qrCode : JY0100YS200507184536G90U84
     * userCode : e712028a05ef4dc3b5160d4306407d43
     * userLogoUrl : http://thirdwx.qlogo.cn/mmopen/vi_32/mFSiaPPXGgiaibUY1ntJLmllEvdJtriaYqpDiaPqrarX8K3ia2TsickeoHc79jFCArrsvvUSvyia9Qa7bUibxYHRCYTntkA/132
     * userName : 陪你
     * userUseType : 5
     */

    private int gender;
    private String newLoginDate;
    private String qrCode;
    private String userCode;
    private String userLogoUrl;
    private String userName;
    private int userUseType;
    private int signDuration;
    private String signDurationUnit;
    private double signPrice;
    private String signCode;
    private String signNo;
    private int detectRate;
    private String detectRateUnitCode;
    private String detectRateUnitName;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNewLoginDate() {
        return newLoginDate;
    }

    public void setNewLoginDate(String newLoginDate) {
        this.newLoginDate = newLoginDate;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserLogoUrl() {
        return userLogoUrl;
    }

    public void setUserLogoUrl(String userLogoUrl) {
        this.userLogoUrl = userLogoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserUseType() {
        return userUseType;
    }

    public void setUserUseType(int userUseType) {
        this.userUseType = userUseType;
    }

    public String getDetectRateUnitName() {
        return detectRateUnitName;
    }

    public void setDetectRateUnitName(String detectRateUnitName) {
        this.detectRateUnitName = detectRateUnitName;
    }
}
