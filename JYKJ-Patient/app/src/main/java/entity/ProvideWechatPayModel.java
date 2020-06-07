package entity;


/**
 * 微信支付
 * 
 * @author JiaQ
 */
public class ProvideWechatPayModel implements java.io.Serializable {
	
    /************************************************ 【支付所需数据】 ************************************************/
    private String appId;//应用ID[调用接口提交的应用ID]
    private String mweb_url;//
    private String nonceStr;//随机字符串[微信返回的随机字符串]
    private String orderNo;//
    private String packagePrepayId;//预支付交易会话标识
    private String partnerid;
    private String sign;//签名[微信返回的签名]
    private String signType;//签名类型
    private String timeStamp;//时间戳
    private String tradeNo;//
    private String prepayid;


    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMweb_url() {
        return mweb_url;
    }

    public void setMweb_url(String mweb_url) {
        this.mweb_url = mweb_url;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPackagePrepayId() {
        return packagePrepayId;
    }

    public void setPackagePrepayId(String packagePrepayId) {
        this.packagePrepayId = packagePrepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
