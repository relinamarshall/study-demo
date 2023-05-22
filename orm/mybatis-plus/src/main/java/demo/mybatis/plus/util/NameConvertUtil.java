package demo.mybatis.plus.util;

import lombok.experimental.UtilityClass;

/**
 * NameConvertUtil
 *
 * @author Wenzhou
 * @since 2023/5/22 16:54
 */
@UtilityClass
public final class NameConvertUtil {
    /**
     * isUpperCase
     *
     * @param name String
     * @return boolean
     */
    public static boolean isUpperCase(String name) {
        return name.matches("^[^a-z]+$");
    }

    /**
     * isCamelCase
     *
     * @param name String
     * @return boolean
     */
    public static boolean isCamelCase(String name) {
        return name.matches("^[^a-z].*");
    }


    /**
     * toCamelCase
     *
     * @param underName String
     * @return String
     */
    public String toCamelCase(String underName) {
        StringBuilder name = new StringBuilder(underName.length());
        boolean toUpper = false;
        char[] chars = underName.toCharArray();

        for (char ch : chars) {
            if (ch == '_') {
                if (name.length() > 0) {
                    toUpper = true;
                }
            } else {
                name.append(toUpper ? Character.toUpperCase(ch) : Character.toLowerCase(ch));
                toUpper = false;
            }
        }

        return name.toString();
    }
}
