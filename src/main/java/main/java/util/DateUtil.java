package main.java.util;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by root on 9/7/17.
 */
public class DateUtil {
    final private static long MILLISPERHOUR=3600000;

    /**
     * format Date to formatted String of specific timezone
     * @param d
     * @param formatter
     * @param gmt
     * @return
     */
    public static String format(Date d, DateFormat formatter,int gmt){
        return formatter.format(DateUtil.transform(d,gmt));
    }
    public static Date transform(Date d,int gmt){
        return new Date(d.getTime()+gmt*MILLISPERHOUR);
    }
}
