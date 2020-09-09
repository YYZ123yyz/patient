package www.patient.jykj_zxyl.base.base_bean;

import java.io.Serializable;

public class AncelAppBean implements Serializable {

    private String cancelReserveName;
    private String cancelReserveRemark;

    public String getCancelReserveName() {
        return cancelReserveName;
    }

    public void setCancelReserveName(String cancelReserveName) {
        this.cancelReserveName = cancelReserveName;
    }

    public String getCancelReserveRemark() {
        return cancelReserveRemark;
    }

    public void setCancelReserveRemark(String cancelReserveRemark) {
        this.cancelReserveRemark = cancelReserveRemark;
    }
}
