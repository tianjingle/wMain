package com.scaffold.zmain.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;

/**
 * 节假日工作时间处理
 * 假日只处理今年、去年的  1.1、5.1、10.1，和周末
 */
public class LocalDateTimeUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//日期格式化
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static List<String> holidays = new ArrayList<String>(); //节假日列表

    /**
     * 静态块初始化法定节日
     */
    static {
        //Calendar类对YEAR、MONTH、DAY_OF_MONTH、HOUR等日历字段之间的转换提供了一些方法
        Calendar c = Calendar.getInstance();  //日期处理类，通用对象，子类对象
        holidays.add(c.get(Calendar.YEAR)-1 + "-01-01");
        holidays.add(c.get(Calendar.YEAR)-1 + "-05-01");
        holidays.add(c.get(Calendar.YEAR)-1 + "-10-01");
        holidays.add(c.get(Calendar.YEAR) + "-01-01");
        holidays.add(c.get(Calendar.YEAR) + "-05-01");
        holidays.add(c.get(Calendar.YEAR) + "-10-01");
    }

    /**
     * 判断当天是否是节假日 节日只包含1.1；5.1；10.1
     *
     * @param date 时间
     * @return 非工作时间：true;工作时间：false
     */
    public static boolean isHolidayOrFestival(Date date) {
        boolean result = false;
        boolean isHolidayTmp = isHoliday(date);
        if (isHolidayTmp) {
            result = true;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            //周末直接为非工作时间
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                result = true;
            } else {//周内9点到17:30为工作时间
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                if (hour < 9 || (hour == 17 && minute > 30) || hour >= 18) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 非工作时间获取最近的工作时间
     * @param date 时间
     * @return 返回处理后时间，格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getPreWorkDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (!isHolidayOrFestival(date)) {
            return datechange(date, "yyyy-MM-dd HH:mm:ss");
        }
        //如果是周日最近的工作日为周五，日期减去2
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            c.add(Calendar.DAY_OF_MONTH, -2);
        }
        //如果是周六最近的工作日为周五，日期减去1
        else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        //如果是周一，并且为早上9点之前，最近的工作日为周五，日期减去3
        else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if (hour < 9) {
                c.add(Calendar.DAY_OF_MONTH, -3);
            }
        }else{
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if (hour < 9) {
                c.add(Calendar.DAY_OF_MONTH, -1);
            }
        }
        c.set(Calendar.HOUR_OF_DAY, 17);
        c.set(Calendar.MINUTE, 30);
        c.set(Calendar.SECOND, 0);
        return datechange(c.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String datechange(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String demo = sdf.format(date);
        return demo;
    }

    /**
     * 根据判断当前时间是否是节日
     *
     * @param date
     *            时间
     * @return
     */
    private static boolean isHoliday(Date date) {
        boolean result = false;
        String dateStr = sdf.format(date);
        if (holidays.size() > 0) {
            for (String holiday : holidays) {
                if (holiday.equals(dateStr)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
