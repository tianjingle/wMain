package com.scaffold.zmain.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author 123
 */
public final class DateUtil {

    private static final Calendar CAL = Calendar.getInstance();
    
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");

    public static final SimpleDateFormat COMPACT_DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat COMPACT_DATE_TIME_FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat COMPACT_TIME_FORMATTER = new SimpleDateFormat("HHmmss");
    public static final SimpleDateFormat GREENWICH2DAtA=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DateUtil() {
    }

    /**
     * 格林尼治时间转化为date
     * @param greenWichDateStr
     * @return
     */
    public static Date parseGreenwichDate(String greenWichDateStr){
        try {
            return (Date) GREENWICH2DAtA.parse(greenWichDateStr);
        } catch (ParseException ex) {
            throw new RuntimeException("Date parse error: " + greenWichDateStr
                    + " , expected format is " + GREENWICH2DAtA.toPattern(),ex);
        }
    }
    /**
     * 构造一个Date对象。
     *@see #newDate(int,int,int,int,int,int)
     */
    public static Date newDate(int year, int month, int day) {
        return newDate(year, month, day, 0, 0, 0);
    }

    /**
     * 构造一个Date对象。<br/>
     *@param month 从0开始,表示一月份
     *@param day 从1开始，表示第一天
     */
    public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
        synchronized (CAL) {
            CAL.set(Calendar.YEAR, year);
            CAL.set(Calendar.MONTH, month);
            CAL.set(Calendar.DAY_OF_MONTH, day);
            CAL.set(Calendar.HOUR_OF_DAY, hour);
            CAL.set(Calendar.MINUTE, minute);
            CAL.set(Calendar.SECOND, second);
            return CAL.getTime();
        }
    }

    /**
     * 返回当前时间
     */
    public static Date now() {
        synchronized (CAL) {
            CAL.setTimeInMillis(System.currentTimeMillis());
            return CAL.getTime();
        }
    }

    /**
     * 返回明天的日期对象
     */
    public static Date tomorrow() {
        return tomorrow(now());
    }

    /** 
     * 取得date的下一天。
     */
    public static Date tomorrow(Date date) {
        synchronized (CAL) {
            CAL.setTime(date);
            CAL.add(Calendar.DATE, 1);
            return CAL.getTime();
        }
    }

    /**
     * 返回昨天的日期对象
     */
    public static Date yesterday() {
        return yesterday(now());
    }

    /** 
     * 取得date的前一天。
     */
    public static Date yesterday(Date date) {
        synchronized (CAL) {
            CAL.setTime(date);
            CAL.add(Calendar.DATE, -1);
            return CAL.getTime();
        }
    }

    /**
     *  取得date的后 n 天.<br/>
     */
    public static Date afterDay(Date date, int n) {
        synchronized (CAL) {
            CAL.setTime(date);
            CAL.add(Calendar.DATE, n);
            return CAL.getTime();
        }
    }
    
    /**
     * 取得date的后 n 秒
     */
    public static Date afterSeconds(Date date, int n) {
        synchronized (CAL) {
            CAL.setTime(date);
            CAL.add(Calendar.SECOND, n);
            return CAL.getTime();
        }
    }
    
    /**
     * 取得日期的某个字段。<br/>
     * 如： getField(date,Calendar.YEAR)
     */
    public static int getField(Date d, int field) {
        synchronized (CAL) {
            CAL.setTime(d);
            return CAL.get(field);
        }
    }

    /**
     * 设置日期的某个字段。<br/>
     */
    public static Date setField(Date d, int field, int value) {
        synchronized (CAL) {
            CAL.setTime(d);
            CAL.set(field, value);
            return CAL.getTime();
        }
    }

    /**
     * 将日期对象包含的时间信息清除。<br/>
     * 即得到的时间为 00:00:00 。
     */
    public static Timestamp clearTime(Date d) {
        synchronized (CAL) {
            CAL.setTime(d);
            CAL.set(Calendar.HOUR_OF_DAY, 0);
            CAL.set(Calendar.MINUTE, 0);
            CAL.set(Calendar.SECOND, 0);
            CAL.set(Calendar.MILLISECOND, 0);
            return new Timestamp(CAL.getTimeInMillis());
        }
    }

    
    /**
     * 解析format指定的格式的日期
     */
    public static Date parseDate(String date, String format){
        return parseDate(date, new SimpleDateFormat(format));
    }

        
    /**
     * 解析format指定的格式的日期
     */
    public static Date parseDate(String date, SimpleDateFormat format){
        try {
            return format.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException("Date parse error: " + date 
                    + " , expected format is " + format.toPattern(),ex);
        }
    }

    /**
     * 将日期以 format指定的格式输出。
     */
    public static synchronized String formatDate(Date date, SimpleDateFormat format) {
        if(date==null){
            return "";
        }
        return format.format(date);
    }
    
    /**
     * 将日期以 format指定的格式输出。
     */
    public static String formatDate(Date date, String format) {
        if(date==null){
            return "";
        }
        return formatDate(date,new SimpleDateFormat(format));
    }

    /**
     * 将日期以 2007-01-30 的格式输出。
     */
    public static synchronized String formatDate(Date date) {
        if(date==null){
            return "";
        }
        return DATE_FORMATTER.format(date);
    }
    /**
     * 将日期以 20070130 的格式输出。
     */
    public static synchronized String formatCompactDate(Date date) {
        if(date==null){
            return "";
        }
        return COMPACT_DATE_FORMATTER.format(date);
    }
    
    /**
     * 解析 2007-01-30 的格式的日期
     */
    public static Date parseDate(String date){
        try {
            return DATE_FORMATTER.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException("Date parse error: " + date 
                    + " , expected format is " + DATE_FORMATTER.toPattern(),ex);
        }
    }
    
    /**
     * 解析 20070130 的格式的日期
     */
    public static Date parseCompactDate(String date){
        try {
            return COMPACT_DATE_FORMATTER.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException("Date parse error: " + date 
                    + " , expected format is " + COMPACT_DATE_FORMATTER.toPattern(),ex);
        }
    }
    

    /**
     * 将日期以 2007-01-30 08:30:59 的格式输出。
     */
    public static synchronized String formatDateTime(Date date) {
        if(date==null){
            return "";
        }
            return DATE_TIME_FORMATTER.format(date);
    }
    
    /**
     * 将日期以 20070130083059 的格式输出。
     */
    public static synchronized String formatCompactDateTime(Date date) {
        if(date==null){
            return "";
        }
        return COMPACT_DATE_TIME_FORMATTER.format(date);
    }
    
    /**
     * 解析 2007-01-30 08:30:59 的格式的日期
     */
    public static Date parseDateTime(String dateTime){
        try {
            return DATE_TIME_FORMATTER.parse(dateTime);
        } catch (ParseException ex) {
            throw new RuntimeException("Date parse error: " + dateTime 
                    + " , expected format is " + DATE_TIME_FORMATTER.toPattern(),ex);
        }
    }
    
    /**
     * 解析 20070130083059 的格式的日期
     */
    public static Date parseCompactDateTime(String dateTime){
        try {
            return COMPACT_DATE_TIME_FORMATTER.parse(dateTime);
        } catch (ParseException ex) {
            throw new RuntimeException("Date parse error: " + dateTime 
                    + " , expected format is " + COMPACT_DATE_TIME_FORMATTER.toPattern(),ex);
        }
    }
    
    /**
     * 将日期以 08:30:59 的格式输出。
     */
    public static synchronized String formatTime(Date date) {
        if(date==null){
            return "";
        }
        return TIME_FORMATTER.format(date);
    }
    
    /**
     * 将日期以 083059 的格式输出。
     */
    public static synchronized String formatCompactTime(Date date) {
        if(date==null){
            return "";
        }
        return COMPACT_TIME_FORMATTER.format(date);
    }
    
    /**
     * 解析 08:30:59 的格式的日期。
     */
    public static Date parseTime(String time){
        try {
            return TIME_FORMATTER.parse(time);
        } catch (ParseException ex) {
            throw new RuntimeException("Date parse error: " + time 
                    + " , expected format is " + TIME_FORMATTER.toPattern(),ex);
        }
    }
    
    /**
     * 解析 083059 的格式的日期。
     */
    public static Date parseCompactTime(String time){
        try {
            return COMPACT_TIME_FORMATTER.parse(time);
        } catch (ParseException ex) {
            throw new RuntimeException("Date parse error: " + time 
                    + " , expected format is " + COMPACT_TIME_FORMATTER.toPattern(),ex);
        }
    }

    /**
     * 返回日期范围，含首尾日期。 [beginDate,endDate]
     */
    public static DateRange newDateRange(Date beginDate, Date endDate) {
        return new DateRange(beginDate, endDate);
    }
    /**
     * 日期范围。<br/>
     * 没有synchronized，不是线程安全的。
     */
    public static class DateRange {

        private final Calendar cal = Calendar.getInstance();
        private Date beginDate;
        private Date endDate;

        DateRange(Date beginDate, Date endDate) {
            this.beginDate = clearTime(beginDate);
            this.endDate = clearTime(endDate);
            cal.setTime(yesterday(this.beginDate));
        }

        /**
         * 当前日期
         */
        private Date current() {
            return cal.getTime();
        }

        /**
         * 下一天。<br/>
         * 请先使用 <code>hasNext()</code> 进行判断。
         *@exception RuntimeException 如果到达了日期范围结尾，则抛出RuntimeException。
         */
        public Date next() {
            if (hasNext()) {
                cal.add(Calendar.DATE, 1);
                return cal.getTime();
            } else {
                throw new RuntimeException("over range!");
            }
        }

        /**
         * 是否有下一天
         */
        public boolean hasNext() {
            return current().before(endDate);
        }
    }
    
    /**
     * 解析 20070130 的格式的日期
     */
    public static java.sql.Date parseCompactSqlDate(String date){
        try {
        	Date utilDate=COMPACT_DATE_FORMATTER.parse(date);
        	
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException ex) {
            throw new RuntimeException("Date parse error: " + date 
                    + " , expected format is " + COMPACT_DATE_FORMATTER.toPattern(),ex);
        }
    }
}
