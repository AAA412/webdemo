package com.webdemo.main.date;

import java.util.Calendar;

/**
 * Created by lin on 2015/11/17.
 *
 * 原文地址：http://blog.csdn.net/wed840313/article/details/5655997
 */
public class GetWorkDayTimeMillisecond {

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));

    }

    /**
     * 获取两个时间之内的工作日时间（只去掉两个日期之间的周末时间，法定节假日未去掉）
     * @param start -起始时间，共有3个重载方法，可以传入long型，Long型，与Date型
     * @param end -结束时间，共有3个重载方法，可以传入long型，Long型，与Date型
     * @return Long型时间差对象
     */
    public long getWorkdayTimeInMillis(long start, long end){
        //如果起始时间大于结束时间，将二者交换
        if(start > end){
            long temp = start;
            start = end;
            end = temp;
        }
        //根据参数获取起始时间与结束时间的日历对象
        Calendar sdate = Calendar.getInstance();
        Calendar edate = Calendar.getInstance();
        sdate.setTimeInMillis(start);
        edate.setTimeInMillis(end);
        //如果两个日期在同一周并且都不是周末日期，则直接返回时间差，增加执行效率
        if(sdate.get(Calendar.YEAR) == edate.get(Calendar.YEAR)
                && sdate.get(Calendar.WEEK_OF_YEAR) == edate.get(Calendar.WEEK_OF_YEAR)
                && sdate.get(Calendar.DAY_OF_WEEK) != 1 && sdate.get(Calendar.DAY_OF_WEEK) != 7
                && edate.get(Calendar.DAY_OF_WEEK) != 1 && edate.get(Calendar.DAY_OF_WEEK) != 7
            ){
            return new Long(end - start);
        }
        return 0L;
    }
}
