package entity;

public class ResBean {

    /**
     * resCode : 1
     * resData : 2
     * ResJsonData : {"country":"中国","doctorCode":"7860ac910ea94176893abc658aa20ca2","doctorId":17,"doctorTitleName":"未认证","flagDoctorStatus":0,"gender":1,"linkPhone":"18987935230","newLoginDate":1587468963000,"qrCode":"JY0100YS200421193603NJZ4VX","userAccount":"18987935230","userFamilyMain":0,"userLogoUrl":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoXIjPoDjMB42GBr4X05dzB3ExmgQ43a07FD1p1rJC8MESlFSLiccxicw6NAAvkvh3OraPIkcUSZR7w/132","userName":"哈哈?","userNameAlias":"未设置","userUseType":5}
     * resTokenData : eyJ0eXAiOiJKV1QiLCJ0eXBlIjoiSldUIiwiZW5jcnlwdGlvbiI6IkhTMjU2IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyUGhvbmUiOiIxODk4NzkzNTIzMCIsIk5vd0RhdGVUaW1lIjoxNTg3NDY5MDQxNzY5LCJleHAiOjE1ODc1NTU0NDEsInVzZXJJZCI6MTd9.F1_8Sm9Rad0RPcXzSroa0cf7ZGKonlcKIIqw1iyg49w
     */

    private int resCode;
    private String resData;
    private String ResJsonData;
    private String resTokenData;

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResData() {
        return resData;
    }

    public void setResData(String resData) {
        this.resData = resData;
    }

    public String getResJsonData() {
        return ResJsonData;
    }

    public void setResJsonData(String ResJsonData) {
        this.ResJsonData = ResJsonData;
    }

    public String getResTokenData() {
        return resTokenData;
    }

    public void setResTokenData(String resTokenData) {
        this.resTokenData = resTokenData;
    }
}
