package demo.utils.util;


import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Supplier;

import demo.utils.exception.ErrorCode;
import demo.utils.exception.RestException;
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
     * assertTrue
     *
     * @param condition boolean
     * @param error     ErrorCode
     */
    public static void assertTrue(boolean condition, ErrorCode error) {
        check(condition, () -> new RestException(error));
    }

    /**
     * assertTrue
     *
     * @param condition boolean
     * @param error     ErrorCode
     * @param message   String
     */
    public static void assertTrue(boolean condition, ErrorCode error, String message) {
        check(condition, () -> new RestException(error, message));
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
     * notNull
     *
     * @param object Object
     * @param error  ErrorCode
     */
    public static void notNull(Object object, ErrorCode error) {
        check(object != null, () -> new RestException(error));
    }

    /**
     * notNull
     *
     * @param object  Object
     * @param error   ErrorCode
     * @param message String
     */
    public static void notNull(Object object, ErrorCode error, String message) {
        check(object != null, () -> new RestException(error, message));
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
     * notBlank
     *
     * @param input     String
     * @param errorCode ErrorCode
     */
    public static void notBlank(String input, ErrorCode errorCode) {
        check(StringUtils.isNotBlank(input), () -> new RestException(errorCode));
    }

    /**
     * notBlank
     *
     * @param input     String
     * @param errorCode ErrorCode
     * @param message   String
     */
    public static void notBlank(String input, ErrorCode errorCode, String message) {
        check(StringUtils.isNotBlank(input), () -> new RestException(errorCode, message));
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
     * notEmpty
     *
     * @param input     String
     * @param errorCode ErrorCode
     */
    public static void notEmpty(String input, ErrorCode errorCode) {
        check(StringUtils.isNotEmpty(input), () -> new RestException(errorCode));
    }

    /**
     * notEmpty
     *
     * @param input     String
     * @param errorCode ErrorCode
     * @param msg       String
     */
    public static void notEmpty(String input, ErrorCode errorCode, String msg) {
        check(StringUtils.isNotEmpty(input), () -> new RestException(errorCode, msg));
    }

    /**
     * notEmptyList
     *
     * @param list List
     * @param msg  String
     */
    public static void notEmptyList(List<?> list, String msg) {
        check(null == list || list.isEmpty(), () -> new IllegalArgumentException(msg));
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
