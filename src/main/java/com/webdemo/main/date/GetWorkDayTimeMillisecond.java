package com.webdemo.main.date;

import java.util.Calendar;

/**
 * Created by lin on 2015/11/17.
 *
 * ԭ�ĵ�ַ��http://blog.csdn.net/wed840313/article/details/5655997
 */
public class GetWorkDayTimeMillisecond {

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));

    }

    /**
     * ��ȡ����ʱ��֮�ڵĹ�����ʱ�䣨ֻȥ����������֮�����ĩʱ�䣬�����ڼ���δȥ����
     * @param start -��ʼʱ�䣬����3�����ط��������Դ���long�ͣ�Long�ͣ���Date��
     * @param end -����ʱ�䣬����3�����ط��������Դ���long�ͣ�Long�ͣ���Date��
     * @return Long��ʱ������
     */
    public long getWorkdayTimeInMillis(long start, long end){
        //�����ʼʱ����ڽ���ʱ�䣬�����߽���
        if(start > end){
            long temp = start;
            start = end;
            end = temp;
        }
        //���ݲ�����ȡ��ʼʱ�������ʱ�����������
        Calendar sdate = Calendar.getInstance();
        Calendar edate = Calendar.getInstance();
        sdate.setTimeInMillis(start);
        edate.setTimeInMillis(end);
        //�������������ͬһ�ܲ��Ҷ�������ĩ���ڣ���ֱ�ӷ���ʱ������ִ��Ч��
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
