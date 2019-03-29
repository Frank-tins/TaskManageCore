package com.task.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils{

    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    private static final String YYYY_MM_DD_HH_MM_SS = "";

    private static final SimpleDateFormat format_YYYY_MM_DD = new SimpleDateFormat(YYYY_MM_DD);

    private static final SimpleDateFormat format_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);


    private DateUtils(){}

    public static String dateModify(Date date, int daynum){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, daynum);
        date = calendar.getTime();
        return format_YYYY_MM_DD.format(date);
    }

    public static String dateModify(int daynum){
        return dateModify(new Date(), daynum);
    }


    public static String formatDate(Date date, String s) {
        Audit.isNotNull("DateUtils -- parameter is null", date, s);
        SimpleDateFormat format = new SimpleDateFormat(s);
        return format.format(date);
    }
}
