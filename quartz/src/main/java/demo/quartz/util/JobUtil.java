package demo.quartz.util;

import demo.quartz.job.base.BaseJob;

/**
 * JobUtil
 * <p>
 * 定时任务反射工具类
 *
 * @author Wenzhou
 * @since 2023/3/22 15:44
 */
public class JobUtil {
    /**
     * 根据全类名获取Job实例
     *
     * @param classname Job全类名
     * @return {@link BaseJob} 实例
     * @throws Exception 泛型获取异常
     */
    public static BaseJob getClass(String classname) throws Exception {
        Class<?> clazz = Class.forName(classname);
        return (BaseJob) clazz.newInstance();
    }
}
