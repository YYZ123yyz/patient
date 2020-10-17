package www.patient.jykj_zxyl.myappointment.bean;

import java.util.List;

public class ViewInteractPatientMessageBean {

    /**
     * doctorLogoUrl : https://jiuyihtn.com/fileUpload/doctorImage/logo/915b29f3d1b7451fa1d4995a8f91b156/logo_20201013154013.jpg
     * interactPatientMessageActiveList : [{"doctorName":"邱新海测试医生","doctorReplyContent":"非你急急急","doctorReplyDate":1602691200000,"flagDoctorReplyType":3,"flagDoctorReplyTypeName":"紧急","flagMessageType":1,"messageTypeName":"主动发起"},{"doctorName":"邱新海测试医生","doctorReplyContent":"非你急急急","doctorReplyDate":1602691200000,"flagDoctorReplyType":3,"flagDoctorReplyTypeName":"紧急","flagMessageType":1,"messageTypeName":"主动发起"},{"doctorName":"邱新海测试医生","doctorReplyContent":"非你急急急","doctorReplyDate":1602691200000,"flagDoctorReplyType":3,"flagDoctorReplyTypeName":"紧急","flagMessageType":1,"messageTypeName":"主动发起"},{"doctorName":"邱新海测试医生","doctorReplyContent":"急急急","doctorReplyDate":1602691200000,"flagDoctorReplyType":3,"flagDoctorReplyTypeName":"紧急","flagMessageType":1,"messageTypeName":"主动发起"}]
     * messageId : 0
     * messageImgArray :
     * messageImgIdArray :
     * orderCode : 0101202010141554073068640192
     * patientLinkPhone :
     * patientLogoUrl : https://jiuyihtn.com/fileUpload/http://114.215.137.171:8040//defaultImageShow.jpg
     * treatmentType : 1
     * treatmentTypeName : 图文就诊
     */

    private String doctorLogoUrl;
    private int messageId;
    private String messageImgArray;
    private String messageImgIdArray;
    private String orderCode;
    private String patientLinkPhone;
    private String patientLogoUrl;
    private int treatmentType;
    private String treatmentTypeName;
    private List<InteractPatientMessageActiveListBean> interactPatientMessageActiveList;

    public String getDoctorLogoUrl() {
        return doctorLogoUrl;
    }

    public void setDoctorLogoUrl(String doctorLogoUrl) {
        this.doctorLogoUrl = doctorLogoUrl;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageImgArray() {
        return messageImgArray;
    }

    public void setMessageImgArray(String messageImgArray) {
        this.messageImgArray = messageImgArray;
    }

    public String getMessageImgIdArray() {
        return messageImgIdArray;
    }

    public void setMessageImgIdArray(String messageImgIdArray) {
        this.messageImgIdArray = messageImgIdArray;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPatientLinkPhone() {
        return patientLinkPhone;
    }

    public void setPatientLinkPhone(String patientLinkPhone) {
        this.patientLinkPhone = patientLinkPhone;
    }

    public String getPatientLogoUrl() {
        return patientLogoUrl;
    }

    public void setPatientLogoUrl(String patientLogoUrl) {
        this.patientLogoUrl = patientLogoUrl;
    }

    public int getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(int treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getTreatmentTypeName() {
        return treatmentTypeName;
    }

    public void setTreatmentTypeName(String treatmentTypeName) {
        this.treatmentTypeName = treatmentTypeName;
    }

    public List<InteractPatientMessageActiveListBean> getInteractPatientMessageActiveList() {
        return interactPatientMessageActiveList;
    }

    public void setInteractPatientMessageActiveList(List<InteractPatientMessageActiveListBean> interactPatientMessageActiveList) {
        this.interactPatientMessageActiveList = interactPatientMessageActiveList;
    }

    public static class InteractPatientMessageActiveListBean {
        /**
         * doctorName : 邱新海测试医生
         * doctorReplyContent : 非你急急急
         * doctorReplyDate : 1602691200000
         * flagDoctorReplyType : 3
         * flagDoctorReplyTypeName : 紧急
         * flagMessageType : 1
         * messageTypeName : 主动发起
         */

        private String doctorName;
        private String doctorReplyContent;
        private long doctorReplyDate;
        private int flagDoctorReplyType;
        private String flagDoctorReplyTypeName;
        private int flagMessageType;
        private String messageTypeName;

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getDoctorReplyContent() {
            return doctorReplyContent;
        }

        public void setDoctorReplyContent(String doctorReplyContent) {
            this.doctorReplyContent = doctorReplyContent;
        }

        public long getDoctorReplyDate() {
            return doctorReplyDate;
        }

        public void setDoctorReplyDate(long doctorReplyDate) {
            this.doctorReplyDate = doctorReplyDate;
        }

        public int getFlagDoctorReplyType() {
            return flagDoctorReplyType;
        }

        public void setFlagDoctorReplyType(int flagDoctorReplyType) {
            this.flagDoctorReplyType = flagDoctorReplyType;
        }

        public String getFlagDoctorReplyTypeName() {
            return flagDoctorReplyTypeName;
        }

        public void setFlagDoctorReplyTypeName(String flagDoctorReplyTypeName) {
            this.flagDoctorReplyTypeName = flagDoctorReplyTypeName;
        }

        public int getFlagMessageType() {
            return flagMessageType;
        }

        public void setFlagMessageType(int flagMessageType) {
            this.flagMessageType = flagMessageType;
        }

        public String getMessageTypeName() {
            return messageTypeName;
        }

        public void setMessageTypeName(String messageTypeName) {
            this.messageTypeName = messageTypeName;
        }
    }
}
