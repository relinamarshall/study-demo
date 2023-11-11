package demo.utils.util;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

import lombok.experimental.UtilityClass;

/**
 * ArgsUtil
 * <p>
 * 参数工具类
 *
 * @author Wenzhou
 * @since 2023/7/13 17:00
 */
@UtilityClass
public class ArgUtil {
    /**
     * ifElse
     *
     * @param in       boolean
     * @param trueGet  Supplier
     * @param falseGet Supplier
     * @return T
     */
    public static <T> T ifElse(boolean in, Supplier<T> trueGet, Supplier<T> falseGet) {
        return in ? trueGet.get() : falseGet.get();
    }

    /**
     * ifTrueDo
     *
     * @param in     boolean
     * @param arg    T
     * @param action Consumer
     */
    public static <T> void ifTrueDo(boolean in, T arg, Consumer<T> action) {
        if (in) {
            action.accept(arg);
        }
    }

    /**
     * ifTrueDo
     *
     * @param in     boolean
     * @param action Runnable
     */
    public static void ifTrueDo(boolean in, Runnable action) {
        if (in) {
            action.run();
        }
    }

    /**
     * ifBlankDo
     *
     * @param in     String
     * @param action Consumer
     */
    public static void ifBlankDo(String in, Consumer<String> action) {
        ifTrueDo(StringUtils.isBlank(in), in, action);
    }

    /**
     * ifEmptyDo
     *
     * @param in     String
     * @param action String
     */
    public static void ifEmptyDo(String in, Consumer<String> action) {
        ifTrueDo(StringUtils.isEmpty(in), in, action);
    }

    /**
     * ifNullDo
     *
     * @param in     T
     * @param action Consumer
     */
    public static <T> void ifNullDo(T in, Consumer<T> action) {
        ifTrueDo(null == in, in, action);
    }

    /**
     * ifNotNullDo
     *
     * @param in     T
     * @param action Consumer
     */
    public static <T> void ifNotNullDo(T in, Consumer<T> action) {
        ifTrueDo(null != in, in, action);
    }

    /**
     * ifBlankGet
     *
     * @param in       String
     * @param supplier Supplier
     * @return String
     */
    public static String ifBlankGet(String in, Supplier<String> supplier) {
        return StringUtils.isBlank(in) ? supplier.get() : in;
    }

    /**
     * ifBlankGet
     *
     * @param in      String
     * @param elseVal String
     * @return String
     */
    public static String ifBlankGet(String in, String elseVal) {
        return StringUtils.isBlank(in) ? elseVal : in;
    }

    /**
     * ifEmptyGet
     *
     * @param in       String
     * @param supplier Supplier
     * @return String
     */
    public static String ifEmptyGet(String in, Supplier<String> supplier) {
        return StringUtils.isEmpty(in) ? supplier.get() : in;
    }

    /**
     * ifEmptyGet
     *
     * @param in      String
     * @param elseVal String
     * @return String
     */
    public static String ifEmptyGet(String in, String elseVal) {
        return StringUtils.isEmpty(in) ? elseVal : in;
    }

    /**
     * ifNullGet
     *
     * @param in       T
     * @param supplier Supplier
     * @return T
     */
    public static <T> T ifNullGet(T in, Supplier<T> supplier) {
        return null == in ? supplier.get() : in;
    }

    /**
     * ifNullGet
     *
     * @param in      T
     * @param elseVal T
     * @return T
     */
    public static <T> T ifNullGet(T in, T elseVal) {
        return null == in ? elseVal : in;
    }

    /**
     * ifTrueGet
     *
     * @param b  boolean
     * @param tV T
     * @param fV T
     * @return T
     */
    public static <T> T ifTrueGet(boolean b, T tV, T fV) {
        return b ? tV : fV;
    }
}
