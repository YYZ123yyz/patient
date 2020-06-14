package www.patient.jykj_zxyl.util;

public class StrUtils {
    public static String defaultStr(Object paraobj){
        String retstr = "";
        if(null!=paraobj){
            retstr = paraobj.toString();
        }
        return retstr;
    }
}
