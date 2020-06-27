package entity.mySelf.conditions;

import java.io.Serializable;

public class QueryUserCond implements Serializable {
    private String userCodeList;

    public String getUserCodeList() {
        return userCodeList;
    }

    public void setUserCodeList(String userCodeList) {
        this.userCodeList = userCodeList;
    }
}
