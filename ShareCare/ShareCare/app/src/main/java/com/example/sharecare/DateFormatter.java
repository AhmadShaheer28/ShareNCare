package com.example.sharecare;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static String dateTimeInputFormat = "yyyy-MM-dd HH:mm";
    private static String dateFormat = "yyyy-MM-dd";
    private static String timeFormat = "HH:mm";

    public static long convertToEpoch(long date) {

        return date / 1000;
    }

//    public static String formatToEpoch(String strDate) throws ParseException {
//
//        SimpleDateFormat f = new SimpleDateFormat(dateTimeInputFormat);
//        Date d = f.parse(strDate);
//        return d.getTime() / 1000;
//    }

    public static String convertDateTime(long time) {
        Date date = new Date(time * 1000);
        Format format = new SimpleDateFormat(dateTimeInputFormat);
        return format.format(date);
    }

    public static String convertDate(long time) {
        Date date = new Date(time * 1000);
        Format format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    public static String convertTime(long time) {
        Date date = new Date(time * 1000);
        Format format = new SimpleDateFormat(timeFormat);
        return format.format(date);
    }

    public static String timeToSting(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat(timeFormat);
        return format.format(date);
    }

    public static String dateToStr(Date date) {

        Format format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    public static String dateToDateTimeEndOfDay(String date) {
        return date + " 23:59";
    }

    public static String createDateTimeString(String date, String time) {
        return date + " " + time;
    }

//    public static long createDateTimeToEpoch(String date, String time) throws ParseException {
//        return formatToEpoch(createDateTimeString(date, time));
//    }

    public static String dateToDateTimeStartOfDay(String date) {
        return date + " 00:00";
    }


}
