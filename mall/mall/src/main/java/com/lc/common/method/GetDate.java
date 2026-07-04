package com.lc.common.method;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDate
{
    public String GetToday()
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public String GetYesterday()
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis()-1000*60*60*24);
        return formatter.format(date);
    }

    public String GetMonth()
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public String TransToDate(Date date)
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public String TransToDateMonth(Date date)
    {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM");
        return formatter.format(date);
    }

    public String GetLastMon()
    {
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);//月份减一
        date=calendar.getTime();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM");
        return formatter.format(date);
    }

}
