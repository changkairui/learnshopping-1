package com.neuedu.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateUtils {

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * Date -->String
     */
    //自定义时间格式
    public static String dateToStr(Date date,String formate){
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formate);
    }
    public static String dateToStr(Date date){
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }
    /**
     * String -->Date
     */
    public static Date strToDate(String str){
        //先是调时间的格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        //调用dateTimeFormatter里面的方法dateTimeFormatter.parseDateTime()
        DateTime dateTime = dateTimeFormatter.parseDateTime(str);
        //调用datetime里面得方法dateTime.toDate()
        return dateTime.toDate();
    }
    //自定义时间格式
    public static Date strToDate(String str,String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = dateTimeFormatter.parseDateTime(str);
        return dateTime.toDate();
    }

    public static void main(String[] args) {
        System.out.println(dateToStr(new Date()));
        System.out.println(strToDate("2018-12-10 14:54:42"));
    }


}
