package entity;

/**
 * 扫一扫功能参数
 */
public class SYSParmentEntity {
    private         String          loginUserPosition;
    private         String          requestClientType;
    private         String          userUseType;
    private         String          operUserCode;
    private         String          operUserName;
    private         String          scanQrCode;

    public String getLoginUserPosition() {
        return loginUserPosition;
    }

    public void setLoginUserPosition(String loginUserPosition) {
        this.loginUserPosition = loginUserPosition;
    }

    public String getRequestClientType() {
        return requestClientType;
    }

    public void setRequestClientType(String requestClientType) {
        this.requestClientType = requestClientType;
    }

    public String getUserUseType() {
        return userUseType;
    }

    public void setUserUseType(String userUseType) {
        this.userUseType = userUseType;
    }

    public String getOperUserCode() {
        return operUserCode;
    }

    public void setOperUserCode(String operUserCode) {
        this.operUserCode = operUserCode;
    }

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public String getScanQrCode() {
        return scanQrCode;
    }

    public void setScanQrCode(String scanQrCode) {
        this.scanQrCode = scanQrCode;
    }
}
