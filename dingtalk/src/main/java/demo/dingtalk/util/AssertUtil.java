package demo.dingtalk.util;


import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Supplier;

import lombok.experimental.UtilityClass;

/**
 * AssertUtil
 * <p>
 * 校验工具类
 *
 * @author Wenzhou
 * @since 2023/7/11 19:32
 */
@UtilityClass
public class AssertUtil {
    /**
     * assertTrue
     *
     * @param condition boolean
     * @param message   String
     */
    public static void assertTrue(boolean condition, String message) {
        check(condition, () -> new IllegalArgumentException(message));
    }

    /**
     * notNull
     *
     * @param object  Object
     * @param message String
     */
    public static void notNull(Object object, String message) {
        check(object != null, () -> new IllegalArgumentException(message));
    }

    /**
     * notBlank
     *
     * @param input   String
     * @param message String
     */
    public static void notBlank(String input, String message) {
        check(StringUtils.isNotBlank(input), () -> new IllegalArgumentException(message));
    }

    /**
     * notEmpty
     *
     * @param input   String
     * @param message String
     */
    public static void notEmpty(String input, String message) {
        check(StringUtils.isNotEmpty(input), () -> new IllegalArgumentException(message));
    }

    /**
     * notEmptyList
     *
     * @param list List
     * @param msg  String
     */
    public static void notEmptyList(List<?> list, String msg) {
        check(null != list && !list.isEmpty(), () -> new IllegalArgumentException(msg));
    }

    /**
     * notBlank
     *
     * @param check    boolean
     * @param supplier Supplier
     */
    public static void check(boolean check, Supplier<? extends RuntimeException> supplier) {
        if (!check) throw supplier.get();
    }
}
