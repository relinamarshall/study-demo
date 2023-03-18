package demo.easyexcel.util;

import java.io.File;

/**
 * PathUtil
 *
 * @author Wenzhou
 * @since 2023/3/17 12:37
 */
public class PathUtil {
    /**
     * XLSX
     */
    public static final String XLSX = ".xlsx";

    /**
     * PATH
     */
    private static final String PATH = "F:/Project/SpringBoot/study-demo/easyexcel/src/main/resources/easyexcel";

    /**
     * PathUtil 私有构造
     */
    private PathUtil() {
    }

    /**
     * getPath
     *
     * @param dictPath String
     * @param fileName String
     * @return String
     */
    public static String getPath(String dictPath, String fileName) {
        return String.format("%s%s%s%s%s", PATH, File.separator, dictPath, File.separator, fileName);
    }

    /**
     * getOutPath
     *
     * @param path   String
     * @param suffix String
     * @return String
     */
    public static String getOutPath(String path, String suffix) {
        return String.format("%s%s%d%s", FileUtil.getPath(), path, System.currentTimeMillis(), suffix);
    }

    /**
     * getOutPath
     *
     * @param path String
     * @return String
     */
    public static String getOutPath(String path) {
        return String.format("%s%s%d%s", FileUtil.getPath(), path, System.currentTimeMillis(), XLSX);
    }
}
