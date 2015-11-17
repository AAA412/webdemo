package com.webdemo.main.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lin on 2015/11/17.
 */
public class MillisToDate {

    public static void main(String[] args) {

        System.out.println(getDateStr(System.currentTimeMillis()));

    }

    private static String getDateStr(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(calendar.getTime());
    }
}
