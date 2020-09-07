package www.patient.jykj_zxyl.base.base_bean;

import java.io.Serializable;

public class BaseStatusBean implements Serializable {
    private String status;
    private String message;
    private String confim;

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

    public String getConfim() {
        return confim;
    }

    public void setConfim(String confim) {
        this.confim = confim;
    }
}
