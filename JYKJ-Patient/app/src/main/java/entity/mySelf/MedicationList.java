package entity.mySelf;

public class MedicationList {
    private String rowNum;
    private String pageNum;
    private String loginPatientPosition;
    private String requestClientType;
    private String operPatientCode;
    private String operPatientName;
    private String searchTakingMedicine;
    private String searchDateType;
    private String searchDateStart;
    private String searchDateEnd;

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
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

    public String getSearchTakingMedicine() {
        return searchTakingMedicine;
    }

    public void setSearchTakingMedicine(String searchTakingMedicine) {
        this.searchTakingMedicine = searchTakingMedicine;
    }

    public String getSearchDateType() {
        return searchDateType;
    }

    public void setSearchDateType(String searchDateType) {
        this.searchDateType = searchDateType;
    }

    public String getSearchDateStart() {
        return searchDateStart;
    }

    public void setSearchDateStart(String searchDateStart) {
        this.searchDateStart = searchDateStart;
    }

    public String getSearchDateEnd() {
        return searchDateEnd;
    }

    public void setSearchDateEnd(String searchDateEnd) {
        this.searchDateEnd = searchDateEnd;
    }
}
