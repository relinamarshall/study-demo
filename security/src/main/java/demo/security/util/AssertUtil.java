package demo.security.util;

import java.util.function.Supplier;

import demo.security.exception.GlobalException;
import lombok.NoArgsConstructor;

/**
 * AssertUtil
 *
 * @author Wenzhou
 * @since 2023/3/16 22:28
 */
@NoArgsConstructor
public final class AssertUtil {
    /**
     * DEFAULT_ERROR_CODE
     */
    private static final String DEFAULT_ERROR_CODE = "500";

    /**
     * assertTrue
     *
     * @param b boolean
     * @param e RuntimeException
     */
    public static void assertTrue(boolean b, RuntimeException e) {
        if (!b) {
            throw e;
        }
    }

    /**
     * assertTrue
     *
     * @param b boolean
     * @param m String
     */
    public static void assertTrue(boolean b, String m) {
        if (!b) {
            throw new GlobalException(m, DEFAULT_ERROR_CODE);
        }
    }

    /**
     * assertTrue
     *
     * @param b boolean
     * @param s Supplier<GlobalException>
     */
    public static void assertTrue(boolean b, Supplier<RuntimeException> s) {
        if (!b) {
            throw s.get();
        }
    }

    /**
     * assertNotNull
     *
     * @param o Object
     * @param e RuntimeException
     */
    public static void assertNotNull(Object o, RuntimeException e) {
        if (null == o) {
            throw e;
        }
    }

    /**
     * assertNotNull
     *
     * @param o Object
     * @param m String
     */
    public static void assertNotNull(Object o, String m) {
        if (null == o) {
            throw new GlobalException(m, DEFAULT_ERROR_CODE);
        }
    }

    /**
     * assertNotNull
     *
     * @param o Object
     * @param s Supplier<GlobalException>
     */
    public static void assertNotNull(Object o, Supplier<RuntimeException> s) {
        if (null == o) {
            throw s.get();
        }
    }
}
