package com.coder520.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by   song 醉美柳舞之众星捧月
 * Date & Time  2017/9/26 13:55
 */
public class DateUtils {
    private static Calendar calendar = Calendar.getInstance();

    /**
     * Author song  醉美柳舞之众星捧月
     * Date & Time  2017/9/26 14:07
     * Description  得到今天是周几
     */
    public static int getTodayWeek() {
        calendar.setTime(new Date());
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
            week = 7;
        }
        return week;
    }

    /**
     * Author song  醉美柳舞之众星捧月
     * Date & Time  2017/9/26 14:07
     * Description  计算时间差 分钟数
     */
    public static int getMinute(Date startDate, Date endDate) {
        long start = startDate.getTime();
        long end = endDate.getTime();
        int minute = (int) (end - start) / (1000 * 60);
        return minute;
    }

    /**
     * Author song  醉美柳舞之众星捧月
     * Date & Time  2017/9/26 21:17
     * Description 获取当天的某个时间
     */
    public static Date getDate(int hour, int mintue) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, mintue);
        return calendar.getTime();
    }
}
