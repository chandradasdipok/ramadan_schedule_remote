package com.example.user.ramadan_schedule.utils;

import android.util.Log;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by chandradasdipok on 5/17/2016.
 */
public class CustomTime {

    private String timeString;
    int[] time=new int[6];

    public int getYear() {
        return time[0];
    }

    public int getMonth() {
        return time[1];
    }

    public int getDay() {
        return time[2];
    }

    public int getHour() {
        return time[3];
    }

    public int getMinute() {
        return time[4];
    }

    public int getSecond() {
        return time[5];
    }


    public CustomTime(String timeString) {
        this.timeString = timeString;
        StringTokenizer stringTokenizer = new StringTokenizer(timeString,"- :",false);
        int i=0;
        while (stringTokenizer.hasMoreTokens() & i<6){
            time[i]= Integer.parseInt(stringTokenizer.nextToken());
            i++;
        }
    }

    public CustomTime(Calendar localCalendar){
        this(localCalendar.get(Calendar.YEAR)+ "-" + (localCalendar.get(Calendar.MONTH)+1) + "-" + localCalendar.get(Calendar.DATE) + " " + localCalendar.get(Calendar.HOUR_OF_DAY) + ":" + localCalendar.get(Calendar.MINUTE) + ":" + localCalendar.get(Calendar.SECOND));
    }
    public String subtractTimeFrom(CustomTime customTime){
        String retString ="";
        long minuend = customTime.calculateTimeInSecond();
        long subtrahend = this.calculateTimeInSecond();
        long subtract = minuend - subtrahend;

        if (subtract<=60){
            retString ="just now";
        }
        else if(subtract<=60*60){
            retString = (subtract/60)+" minute(s) ago";
        }
        else if (subtract <= 24*60*60){
            retString = (subtract/(60*60))+" hour(s) ago";
        }
        else if(subtract <= 30*24*60*60){
            retString = (subtract/(24*60*60))+" day(s) ago";
        }
        else if(subtract <= 12*30*24*60*60){
            retString = (subtract/(30*24*60*60))+" month(s) ago";
        }
        else
            retString = (subtract/(12*30*24*60*60))+" year(s) ago";
        return retString;
    }
    public long calculateTimeInSecond(){
        return  ((((((getYear()%2010)*12+getMonth())*30+getDay())*24+getHour())*60+getMinute())*60+getSecond());
    }
    @Override
    public String toString() {
        return timeString;
    }

    public void testTimeStamp(){
        Calendar localCalendar = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(localCalendar.getTimeInMillis());
        Log.d("Time",timestamp.toString());
    }

    public static String convertToAMPM(Calendar localCalendar){
        switch (localCalendar.get(Calendar.AM_PM)){
            case Calendar.AM:
                return "am";
            default:
                return "pm";
        }
    }
    public static String convertToDayString(Calendar localCalendar){
        switch (localCalendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.MONDAY:
                return "Mon";
            case Calendar.TUESDAY:
                return "Tue";
            case Calendar.WEDNESDAY:
                return "Wed";
            case Calendar.THURSDAY:
                return "Thu";
            case Calendar.FRIDAY:
                return "Fri";
            case Calendar.SATURDAY:
                return "Sat";
            default:
                return "Sun";
        }
    }
    public static String convertToMonthString(Calendar localCalendar){
        switch (localCalendar.get(Calendar.MONTH)){
            case Calendar.JANUARY:
                return "Jan";
            case Calendar.FEBRUARY:
                return "Feb";
            case Calendar.MARCH:
                return "Mar";
            case Calendar.APRIL:
                return "Apr";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "Jun";
            case Calendar.JULY:
                return "Jul";
            case Calendar.AUGUST:
                return "Aug";
            case Calendar.SEPTEMBER:
                return "Sep";
            case Calendar.OCTOBER:
                return "Oct";
            case Calendar.NOVEMBER:
                return "Nov";
            default:
                return "Dec";
        }
    }

}
