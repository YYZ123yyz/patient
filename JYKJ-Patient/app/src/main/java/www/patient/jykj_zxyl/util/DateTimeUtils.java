package www.patient.jykj_zxyl.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils implements Serializable {
    public static String formatLongDate(Long paramdate,String format){
        Date pardate = new Date(paramdate);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(pardate);
    }
}
