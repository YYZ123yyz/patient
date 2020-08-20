package www.patient.jykj_zxyl.base.base_bean;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-03 17:43
 */
public class PaymentBean {


    /**
     * appId : wx4ccb2ac1c5491336
     * nonceStr : 20200803174149PYH6YL095PJZP2N5KV
     * packagePrepayId : Sign=WXPay
     * partnerid : 1579159781
     * prepayid : wx03174148994169a59b1747c71501133100
     * sign : 2CECBAFA6A845C091F2AC8B0AE61400C
     * timeStamp : 1596447709
     */

    private String appId;
    private String nonceStr;
    private String packagePrepayId;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timeStamp;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackagePrepayId() {
        return packagePrepayId;
    }

    public void setPackagePrepayId(String packagePrepayId) {
        this.packagePrepayId = packagePrepayId;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
