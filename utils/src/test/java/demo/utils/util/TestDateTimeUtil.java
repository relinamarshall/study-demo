package demo.utils.util;

import org.junit.Test;

import java.util.Date;

/**
 * TestDateTimeUtil
 *
 * @author Wenzhou
 * @since 2023/6/14 17:21
 */
public class TestDateTimeUtil {
    @Test
    public void testParseDate() {
        System.out.println(DateTimeUtil.parseDate8("20230614"));
        System.out.println(DateTimeUtil.parseDate10("2023-06-14"));
        System.out.println(DateTimeUtil.parseDate17("20230614173000000"));
        System.out.println(DateTimeUtil.parseDate19("2023-06-14 17:30:00"));
    }

    @Test
    public void testFormatDate() {
        System.out.println(DateTimeUtil.formatDate8(new Date()));
        System.out.println(DateTimeUtil.formatDate10(new Date()));
        System.out.println(DateTimeUtil.formatDate17(new Date()));
        System.out.println(DateTimeUtil.formatDate19(new Date()));
    }

    @Test
    public void testAdd() {
        Date date = new Date();
        System.out.println(DateTimeUtil.formatDate19(date));
        System.out.println(DateTimeUtil.formatDate19(DateTimeUtil.addSecond(date, 60)));
        System.out.println(DateTimeUtil.formatDate19(DateTimeUtil.addMinute(date, 1)));
        System.out.println(DateTimeUtil.formatDate19(DateTimeUtil.addHour(date, 1)));
        System.out.println(DateTimeUtil.formatDate19(DateTimeUtil.addDay(date, 1)));
    }

    @Test
    public void testCalcAge() {
        Date date = new Date();
        System.out.println(DateTimeUtil.calcAge("20000510"));
        System.out.println(DateTimeUtil.calcAge("20000510", new Date()));
        System.out.println(DateTimeUtil.getNowDate());
        System.out.println(DateTimeUtil.getBetweenDay(DateTimeUtil.parseDate10("2023-02-10"),
                DateTimeUtil.parseDate10("2023-03-10")));
    }
}
