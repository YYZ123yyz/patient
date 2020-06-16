package entity.patientapp;

/**
 * 图片上传参数
 */
public class UpLoadImgParment {

    private             String                  loginPatientPosition;
    private             String                  requestClientType;
    private             String                  operPatientCode;
    private             String                  operPatientName;
    private             String                  orderCode;
    private             String                  imgBase64Data;
    private             String                  imgCode;


    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getImgBase64Data() {
        return imgBase64Data;
    }

    public void setImgBase64Data(String imgBase64Data) {
        this.imgBase64Data = imgBase64Data;
    }
}
