package entity.user;

public class UserInfo {
    private                     String                  userPhone;                          //用户手机号
    private                     String                  userPwd;                            //用户密码
    private                     String                  userLoginSmsVerify;                 //用户短信验证码
    private                     String                  loginPatientPosition;
    private                     String                  tokenSmsVerify;                     //短信验证码Tokend
    private                     String                  requestClientType;

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

    public String getTokenSmsVerify() {
        return tokenSmsVerify;
    }

    public void setTokenSmsVerify(String tokenSmsVerify) {
        this.tokenSmsVerify = tokenSmsVerify;
    }

    public String getUserLoginSmsVerify() {
        return userLoginSmsVerify;
    }

    public void setUserLoginSmsVerify(String userLoginSmsVerify) {
        this.userLoginSmsVerify = userLoginSmsVerify;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
