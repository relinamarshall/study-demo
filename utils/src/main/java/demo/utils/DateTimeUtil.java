package demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import lombok.experimental.UtilityClass;

/**
 * DateTimeUtil
 * <p>
 * 时间工具类
 *
 * @author Wenzhou
 * @since 2023/6/14 16:40
 */
@UtilityClass
public final class DateTimeUtil {
    public static final long MS_OF_SECOND = 1000L;
    public static final long MS_OF_MINUTE = 60000L;
    public static final long MS_OF_HOUR = 3600000L;
    public static final long MS_OF_DAY = 86400000L;

    private static final FastDateFormat DATE_FMT_8 = FastDateFormat.getInstance("yyyyMMdd");
    private static final FastDateFormat DATE_FMT_10 = FastDateFormat.getInstance("yyyy-MM-dd");
    private static final FastDateFormat DATE_FMT_17 = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
    private static final FastDateFormat DATE_FMT_19 = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public static Date parseDate8(String dateStr) {
        return parseDate(DATE_FMT_8, dateStr);
    }

    public static Date parseDate10(String dateStr) {
        return parseDate(DATE_FMT_10, dateStr);
    }

    public static Date parseDate17(String dateStr) {
        return parseDate(DATE_FMT_17, dateStr);
    }

    public static Date parseDate19(String dateStr) {
        return parseDate(DATE_FMT_19, dateStr);
    }

    private Date parseDate(FastDateFormat dateFmt, String dateStr) {
        try {
            if (dateStr.length() == dateFmt.getPattern().length()) {
                return dateFmt.parse(dateStr);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期参数格式应为" + dateFmt.getPattern());
        }
        throw new IllegalArgumentException("日期参数格式应为" + dateFmt.getPattern());
    }

    public static String formatDate8(Date date) {
        return DATE_FMT_8.format(date);
    }

    public static String formatDate10(Date date) {
        return DATE_FMT_10.format(date);
    }

    public static String formatDate17(Date date) {
        return DATE_FMT_17.format(date);
    }

    public static String formatDate19(Date date) {
        return DATE_FMT_19.format(date);
    }

    public static Date addSecond(Date date, long second) {
        return new Date(date.getTime() + second * MS_OF_SECOND);
    }

    public static Date addMinute(Date date, long minute) {
        return new Date(date.getTime() + minute * MS_OF_MINUTE);
    }

    public static Date addHour(Date date, long hour) {
        return new Date(date.getTime() + hour * MS_OF_HOUR);
    }

    public static Date addDay(Date date, long day) {
        return new Date(date.getTime() + day * MS_OF_DAY);
    }

    public static String format(Date date, String format) {
        return (new SimpleDateFormat(format)).format(date);
    }

    public static Integer calcAge(String birthDt) {
        return calcAge(birthDt, new Date());
    }

    public static Integer calcAge(String birthDt, Date nowDt) {
        if (StringUtils.isBlank(birthDt)) {
            return null;
        } else {
            Date dt = birthDt.length() == 8 ? parseDate8(birthDt) : parseDate10(birthDt);
            String birthYear = DateFormatUtils.format(dt, "yyyy");
            String nowYear = DateFormatUtils.format(nowDt, "yyyy");
            int age = Integer.parseInt(nowYear) - Integer.parseInt(birthYear);
            return Math.max(age, 0);
        }
    }

    public static Date getNowDate() {
        return parseDate10(formatDate10(new Date()));
    }

    public static long getBetweenDay(Date begin, Date end) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate beginLocalDate = begin.toInstant().atZone(zoneId).toLocalDate();
        LocalDate endLocalDate = end.toInstant().atZone(zoneId).toLocalDate();
        return endLocalDate.toEpochDay() - beginLocalDate.toEpochDay();
    }
}
