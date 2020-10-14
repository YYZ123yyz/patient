package www.patient.jykj_zxyl.base.base_bean;

import java.util.List;

public class ReservePatientListBean {

    /**
     * endTimes : 1598565600000
     * itemTimes : [{"allCount":10,"blockNo":0,"hoursMins":"05:00","overCount":10},{"allCount":10,"blockNo":1,"hoursMins":"05:30","overCount":10}]
     * mainDoctorCode : 09fbf816f3df4fd896e97fd5b13b8b2b
     * mainDoctorName : 张强1
     * reserveCount : 20
     * reserveDateRosterCode : 8426ffb22ec045a0b9de00ae03ecd028
     * reserveType : 02
     * reserveTypeName : 临时放号
     * sourceType : 10
     * sourceTypeName : 图文
     * startTimes : 1598562000000
     * times : 1598544000000
     * viewTimesPeriod : 上午
     */

    private long endTimes;
    private String mainDoctorCode;
    private String mainDoctorName;
    private int reserveCount;
    private String reserveDateRosterCode;
    private String reserveType;
    private String reserveTypeName;
    private String sourceType;
    private String sourceTypeName;
    private long startTimes;
    private long times;
    private String viewTimesPeriod;
    private List<ItemTimesBean> itemTimes;
    private Boolean selectState = true;

    public Boolean getSelectState() {
        return selectState;
    }

    public void setSelectState(Boolean selectState) {
        this.selectState = selectState;
    }

    public long getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(long endTimes) {
        this.endTimes = endTimes;
    }

    public String getMainDoctorCode() {
        return mainDoctorCode;
    }

    public void setMainDoctorCode(String mainDoctorCode) {
        this.mainDoctorCode = mainDoctorCode;
    }

    public String getMainDoctorName() {
        return mainDoctorName;
    }

    public void setMainDoctorName(String mainDoctorName) {
        this.mainDoctorName = mainDoctorName;
    }

    public int getReserveCount() {
        return reserveCount;
    }

    public void setReserveCount(int reserveCount) {
        this.reserveCount = reserveCount;
    }

    public String getReserveDateRosterCode() {
        return reserveDateRosterCode;
    }

    public void setReserveDateRosterCode(String reserveDateRosterCode) {
        this.reserveDateRosterCode = reserveDateRosterCode;
    }

    public String getReserveType() {
        return reserveType;
    }

    public void setReserveType(String reserveType) {
        this.reserveType = reserveType;
    }

    public String getReserveTypeName() {
        return reserveTypeName;
    }

    public void setReserveTypeName(String reserveTypeName) {
        this.reserveTypeName = reserveTypeName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceTypeName() {
        return sourceTypeName;
    }

    public void setSourceTypeName(String sourceTypeName) {
        this.sourceTypeName = sourceTypeName;
    }

    public long getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(long startTimes) {
        this.startTimes = startTimes;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public String getViewTimesPeriod() {
        return viewTimesPeriod;
    }

    public void setViewTimesPeriod(String viewTimesPeriod) {
        this.viewTimesPeriod = viewTimesPeriod;
    }

    public List<ItemTimesBean> getItemTimes() {
        return itemTimes;
    }

    public void setItemTimes(List<ItemTimesBean> itemTimes) {
        this.itemTimes = itemTimes;
    }

    public static class ItemTimesBean {
        /**
         * allCount : 10
         * blockNo : 0
         * hoursMins : 05:00
         * overCount : 10
         */

        private int allCount;
        private int blockNo;
        private String hoursMins;
        private int overCount;
        private Boolean selectState = false;

        public Boolean getSelectState() {
            return selectState;
        }

        public void setSelectState(Boolean selectState) {
            this.selectState = selectState;
        }

        public int getAllCount() {
            return allCount;
        }

        public void setAllCount(int allCount) {
            this.allCount = allCount;
        }

        public int getBlockNo() {
            return blockNo;
        }

        public void setBlockNo(int blockNo) {
            this.blockNo = blockNo;
        }

        public String getHoursMins() {
            return hoursMins;
        }

        public void setHoursMins(String hoursMins) {
            this.hoursMins = hoursMins;
        }

        public int getOverCount() {
            return overCount;
        }

        public void setOverCount(int overCount) {
            this.overCount = overCount;
        }
    }
}
