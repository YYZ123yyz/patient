package www.patient.jykj_zxyl.base.base_bean;

import java.util.List;

/**
 * Created by G on 2020/10/8 17:29
 */
public class AllDepartmentBean {
    private List<HospitalDepartmentListBean> hospitalDepartmentList;
    private List<TitleHospitalDepartmentBean> titleHospitalDepartment;

    public List<HospitalDepartmentListBean> getHospitalDepartmentList() {
        return hospitalDepartmentList;
    }

    public void setHospitalDepartmentList(List<HospitalDepartmentListBean> hospitalDepartmentList) {
        this.hospitalDepartmentList = hospitalDepartmentList;
    }

    public List<TitleHospitalDepartmentBean> getTitleHospitalDepartment() {
        return titleHospitalDepartment;
    }

    public void setTitleHospitalDepartment(List<TitleHospitalDepartmentBean> titleHospitalDepartment) {
        this.titleHospitalDepartment = titleHospitalDepartment;
    }

    public static class HospitalDepartmentListBean {
        /**
         * departmentCode : 14
         * departmentName : 呼吸内科
         * hospitalDepartmentId : 14
         * hospitalInfoCode : 0
         * parentId : 1
         * sort : 1
         */

        private String departmentCode;
        private String departmentName;
        private int hospitalDepartmentId;
        private String hospitalInfoCode;
        private int parentId;
        private int sort;
        private String index;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getDepartmentCode() {
            return departmentCode;
        }

        public void setDepartmentCode(String departmentCode) {
            this.departmentCode = departmentCode;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public int getHospitalDepartmentId() {
            return hospitalDepartmentId;
        }

        public void setHospitalDepartmentId(int hospitalDepartmentId) {
            this.hospitalDepartmentId = hospitalDepartmentId;
        }

        public String getHospitalInfoCode() {
            return hospitalInfoCode;
        }

        public void setHospitalInfoCode(String hospitalInfoCode) {
            this.hospitalInfoCode = hospitalInfoCode;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

    public static class TitleHospitalDepartmentBean {
        /**
         * departmentCode : 15
         * departmentName : 消化内科
         * hospitalDepartmentId : 15
         * hospitalInfoCode : 0
         * parentId : 1
         * sort : 2
         * viewDepartmentImg : https://jiuyihtn.com/DepartmentImg/老年病科.png
         */

        private String departmentCode;
        private String departmentName;
        private int hospitalDepartmentId;
        private String hospitalInfoCode;
        private int parentId;
        private int sort;
        private String viewDepartmentImg;

        public String getDepartmentCode() {
            return departmentCode;
        }

        public void setDepartmentCode(String departmentCode) {
            this.departmentCode = departmentCode;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public int getHospitalDepartmentId() {
            return hospitalDepartmentId;
        }

        public void setHospitalDepartmentId(int hospitalDepartmentId) {
            this.hospitalDepartmentId = hospitalDepartmentId;
        }

        public String getHospitalInfoCode() {
            return hospitalInfoCode;
        }

        public void setHospitalInfoCode(String hospitalInfoCode) {
            this.hospitalInfoCode = hospitalInfoCode;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getViewDepartmentImg() {
            return viewDepartmentImg;
        }

        public void setViewDepartmentImg(String viewDepartmentImg) {
            this.viewDepartmentImg = viewDepartmentImg;
        }
    }
}
