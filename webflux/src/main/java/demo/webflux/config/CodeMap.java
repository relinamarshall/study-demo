package demo.webflux.config;

import java.util.HashMap;
import java.util.Map;

/**
 * CodeMap
 *
 * @author Wenzhou
 * @since 2023/5/25 10:17
 */
public class CodeMap {

    private CodeMap() {

    }
    private static final Map<String, String> captchaMap = new HashMap<>();

    public static String get(String key) {
        return captchaMap.getOrDefault(key, null);
    }

    public static void set(String key, String code) {
        captchaMap.put(key, code);
        System.out.println(captchaMap);
    }
}
