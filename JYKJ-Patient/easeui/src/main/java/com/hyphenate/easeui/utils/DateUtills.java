package com.hyphenate.easeui.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by G on 2020/9/25 10:10
 */
public class DateUtills {

    public static boolean isCompareDateTime(String date1, String endTime,String startTime) {
        boolean flag = false;

        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //日期【String】转换为【Date】
            Date nowTime = df.parse(date1);
            Date endTimej = df.parse(endTime);
            Date startTimej = df.parse(startTime);
            /*
             * java.util.Date.before(Date when) 方法检查此日期是否在指定日期date之前
             * 参数:  when -- 要比较的日期
             * 返回值： true如果Date对象在when表示的时刻之前，否则为false。
             *
             * date1 在 date2 之前，返回true；【1<2  true】
             * date1 在 date2 之后，返回false；【1>2  false】
             * date1 和 date2 相等，返回false；【1=2  false】
             * */
            boolean end = nowTime.before(endTimej);//true
            boolean start = startTimej.before(nowTime);//true
            flag = (end && start);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Method Error：public static boolean isCompareDateTime(String date1, String date2) ==> " + e.getMessage());
            return false;
        }
        return flag;
    }



    /**
     * 获取当前系统时间，并以年-月-日 时:分:秒的格式输出
     *
     * @return
     */
    public static String getCurrentFormart() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }
}
