package com.joseph.supplier.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getTime(String p) {
        SimpleDateFormat sdf = new SimpleDateFormat(p);
        return sdf.format(new Date());
    }

    public static String getTime(String p, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(p);
        return sdf.format(date);
    }

    public static Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
