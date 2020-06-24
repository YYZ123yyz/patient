package entity.mySelf;

import java.io.Serializable;

public class UpPasswordInfo implements Serializable {
    private String loginPatientPosition;
    private String requestClientType;
    private String operPatientCode;
    private String operPatientName;
    private String oldPassWord;
    private String newPassWord;
    private String sendUserLinkPhone;
    private String bindingSmsVerifyData;
    private String bindingSmsVerifyTokenData;

    public String getLoginPatientPosition() {
        return loginPatientPosition;
    }

    public void setLoginPatientPosition(String loginPatientPosition) {
        this.loginPatientPosition = loginPatientPosition;
    }

    public String getRequestClientType() {
        return requestClientType;
    }

    public void setRequestClientType(String requestClientType) {
        this.requestClientType = requestClientType;
    }

    public String getOperPatientCode() {
        return operPatientCode;
    }

    public void setOperPatientCode(String operPatientCode) {
        this.operPatientCode = operPatientCode;
    }

    public String getOperPatientName() {
        return operPatientName;
    }

    public void setOperPatientName(String operPatientName) {
        this.operPatientName = operPatientName;
    }

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    public String getSendUserLinkPhone() {
        return sendUserLinkPhone;
    }

    public void setSendUserLinkPhone(String sendUserLinkPhone) {
        this.sendUserLinkPhone = sendUserLinkPhone;
    }

    public String getBindingSmsVerifyData() {
        return bindingSmsVerifyData;
    }

    public void setBindingSmsVerifyData(String bindingSmsVerifyData) {
        this.bindingSmsVerifyData = bindingSmsVerifyData;
    }

    public String getBindingSmsVerifyTokenData() {
        return bindingSmsVerifyTokenData;
    }

    public void setBindingSmsVerifyTokenData(String bindingSmsVerifyTokenData) {
        this.bindingSmsVerifyTokenData = bindingSmsVerifyTokenData;
    }
}
