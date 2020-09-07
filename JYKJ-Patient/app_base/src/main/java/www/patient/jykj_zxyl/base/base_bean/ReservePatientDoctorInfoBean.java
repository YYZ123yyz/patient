package www.patient.jykj_zxyl.base.base_bean;

public class ReservePatientDoctorInfoBean {

    /**
     * times : 1598544000000
     * viewReserveCount : 40
     * week : 6
     */

    private long times;
    private int viewReserveCount;
    private int week;

    private boolean isChoosed;

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public int getViewReserveCount() {
        return viewReserveCount;
    }

    public void setViewReserveCount(int viewReserveCount) {
        this.viewReserveCount = viewReserveCount;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
