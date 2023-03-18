package demo.easyexcel.util;

import java.util.Objects;

/**
 * FileUtil
 *
 * @author Wenzhou
 * @since 2023/3/17 11:09
 */
public class FileUtil {
    /**
     * getPath
     *
     * @return String
     */
    public static String getPath() {
        return Objects.requireNonNull(FileUtil.class.getResource("/")).getPath();
    }
}
