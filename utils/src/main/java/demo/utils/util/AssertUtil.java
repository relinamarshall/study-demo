package demo.utils.util;

import lombok.experimental.UtilityClass;

/**
 * AssertUtil
 *
 * @author Wenzhou
 * @since 2023/6/16 11:08
 */
@UtilityClass
public final class AssertUtil {
    public static void assertTrue(boolean condition, String msg) {
        if (!condition) {
            throw new IllegalArgumentException(msg);
        }
    }
}
