package www.patient.jykj_zxyl.util;

import java.util.UUID;

public class MyId {
    public static String createUUID(){
        String retstr = UUID.randomUUID().toString().replaceAll("-","");
        return retstr;
    }
}
