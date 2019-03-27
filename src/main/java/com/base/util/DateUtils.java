package com.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils{

    private DateUtils(){}

    public static String dateModify(Date date, int daynum, String dateFormat){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, daynum);
        date = calendar.getTime();
        SimpleDateFormat format=new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    public static String dateModify(Date date, int daynum){
        return dateModify(date, daynum, "yyyy-MM-dd");
    }

    public static String dateModify(int daynum){
        return dateModify(new Date(), daynum, "yyyy-MM-dd");
    }

}
