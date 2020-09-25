package www.patient.jykj_zxyl.base.base_bean;

/**
 * Created by G on 2020/9/23 16:46
 */
public class CheckNumBean {
    /**
     * isBinding : 1
     * isReserveing : 1
     * isSigning : 1
     * orderCode : 0101202009211029186041510611
     * reserveCode : 5b0e859ac6514d56983e09cced994929
     * reserveConfigEnd : 1600704000000
     * reserveConfigStart : 1600617600000
     * sumDuration : -1
     * useDuration : 0
     */

    private int isBinding;
    private int isReserveing;
    private int isSigning;
    private String orderCode;
    private String reserveCode;
    private long reserveConfigEnd;
    private long reserveConfigStart;
    private int sumDuration;
    private int useDuration;

    public int getIsBinding() {
        return isBinding;
    }

    public void setIsBinding(int isBinding) {
        this.isBinding = isBinding;
    }

    public int getIsReserveing() {
        return isReserveing;
    }

    public void setIsReserveing(int isReserveing) {
        this.isReserveing = isReserveing;
    }

    public int getIsSigning() {
        return isSigning;
    }

    public void setIsSigning(int isSigning) {
        this.isSigning = isSigning;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(String reserveCode) {
        this.reserveCode = reserveCode;
    }

    public long getReserveConfigEnd() {
        return reserveConfigEnd;
    }

    public void setReserveConfigEnd(long reserveConfigEnd) {
        this.reserveConfigEnd = reserveConfigEnd;
    }

    public long getReserveConfigStart() {
        return reserveConfigStart;
    }

    public void setReserveConfigStart(long reserveConfigStart) {
        this.reserveConfigStart = reserveConfigStart;
    }

    public int getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(int sumDuration) {
        this.sumDuration = sumDuration;
    }

    public int getUseDuration() {
        return useDuration;
    }

    public void setUseDuration(int useDuration) {
        this.useDuration = useDuration;
    }
}
