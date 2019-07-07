package com.example.newdiplwm;

import androidx.room.TypeConverter;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {

    //    @TypeConverter
//    public static Timestamp fromTimestamp(Timestamp value) {
//        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        return value == null ? null : value;
//    }
//
//    @TypeConverter
//    public static Timestamp dateToTimestamp(Date date) {
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        return date == null ? null : Timestamp.valueOf(date.toString());
//    }
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    static DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss" );
    @TypeConverter
    public static Date fromTimestamp(String value) {

        String valueee = value;
        try {
            return  dateFormatter.parse(value) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static String dateToTimestamp(Date date) {
        return date == null ? null : date.toString();
    }


}
