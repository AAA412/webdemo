package com.webdemo.main.date;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lin on 2015/11/17.
 *
 * 原文地址：http://blog.csdn.net/wed840313/article/details/5655997
 */
public class GetWorkDayTimeMillisecond {

    public static void main(String[] args) {

        GetWorkDayTimeMillisecond a = new GetWorkDayTimeMillisecond();
        Long b=a.getWorkdayTimeInMillis("2015-11-10 8-00-00", "2015-11-18 9-00-01", "yyyy-MM-dd HH-mm-ss");
        System.out.println(b);
        System.out.println(a.millisToDay(b));

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
            return end - start;
        }
        //首先取得其实日期结束日期的下个周一的日期
        Calendar snextM = getNextMonday(sdate);
        Calendar enextM = getNextMonday(edate);
        //获取这两个周一之间的实际天数
        int days = getDaysBetween(snextM, enextM);
        //获取这两个周一之间的工作日数（两个周一之间的天数肯定能被7整除，并且工作日数量占其中的5/7）
        int workdays = days/7*5;
        //获取开始时间偏移量
        long scharge = 0;
        if(sdate.get(Calendar.DAY_OF_WEEK) != 1 && sdate.get(Calendar.DAY_OF_WEEK) != 7){
            //只有在开始时间在非周末的时候才计算偏移量
            scharge += (7-sdate.get(Calendar.DAY_OF_WEEK))*24*3600000;
            scharge -= sdate.get(Calendar.HOUR_OF_DAY)*3600000;
            scharge -= sdate.get(Calendar.MINUTE)*60000;
            scharge -= sdate.get(Calendar.SECOND)*1000;
            scharge -= sdate.get(Calendar.MILLISECOND);
        }
        //获取结束时间的偏移量
        long echarge = 0;
        if(edate.get(Calendar.DAY_OF_WEEK)!=1 && edate.get(Calendar.DAY_OF_WEEK)!=7){
            //只有在结束时间为非周末的时候才计算偏移量
            echarge += (7-edate.get(Calendar.DAY_OF_WEEK))*24*3600000;
            echarge -= edate.get(Calendar.HOUR_OF_DAY)*3600000;
            echarge -= edate.get(Calendar.MINUTE)*60000;
            echarge -= edate.get(Calendar.SECOND)*1000;
            echarge -= edate.get(Calendar.MILLISECOND);
        }
        //计算最终结果，具体为：workdays加上开始时间的时间偏移量，减去结束时间的时间偏移量
        return workdays*24*3600000+scharge-echarge;
    }

    public Long getWorkdayTimeInMillis(Long start, Long end){
        return getWorkdayTimeInMillis(start.longValue(), end.longValue());
    }
    public Long getWorkdayTimeInMillis(Date start, Date end){
        return getWorkdayTimeInMillis(start.getTime(), end.getTime());
    }

    public Long getWorkdayTimeInMillis(String start, String end, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date sdate = null;
        Date edate = null;
        try {
            sdate = sdf.parse(start);
            edate = sdf.parse(end);
            return getWorkdayTimeInMillis(sdate, edate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Long(0);
        }
    }

    //获取下周周一
    public Calendar getNextMonday(Calendar calendar){
        int addnum = 9 - calendar.get(Calendar.DAY_OF_WEEK);
        if(addnum == 8) addnum = 1;//周日的情况
        calendar.add(Calendar.DATE, addnum);
        return calendar;
    }

    /**
     * 获取两个日期之间的实际天数，支持跨年
     */
    public int getDaysBetween(Calendar start, Calendar end){
        if(start.after(end)){
            Calendar swap = start;
            start = end;
            end = swap;
        }
        int days = end.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
        int y2 = end.get(Calendar.YEAR);
        if(start.get(Calendar.YEAR) != y2){
            start = (Calendar) start.clone();
            do{
                days += start.getActualMaximum(Calendar.DAY_OF_YEAR);
                start.add(Calendar.YEAR,1);
            }while(start.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    public String millisToDay(long time) {
        String str = "" ;
        time = time / 1000;
        int s = (int) (time % 60);
        int m = (int) (time / 60 % 60);
        int h = (int) (time / 3600 % 60);
        int day = (int) (time/86400);
        str = day + "天" + h + "小时" + m + "分" + s +"秒";
        return str ;
    }
}
