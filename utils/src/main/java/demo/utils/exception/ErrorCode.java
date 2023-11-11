package demo.utils.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

/**
 * ErrorCode
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/9/3
 */
@ToString
public final class ErrorCode implements Serializable {
    /**
     * map
     */
    private static final Map<String, ErrorCode> map = new HashMap<>();
    /**
     * SERVICE_INNER_ERROR
     */
    public static final ErrorCode SERVICE_INNER_ERROR =
            register("001000", "系统内部错误,请稍后再试", 500);

    /**
     * code
     */
    @Getter
    private String code;
    /**
     * message
     */
    @Getter
    private String message;
    /**
     * httpCode
     */
    @Getter
    private int httpCode;

    /**
     * of
     *
     * @param code String
     * @return ErrorCode
     */
    public static ErrorCode of(String code) {
        return map.get(code);
    }

    /**
     * ErrorCode
     *
     * @param code     String
     * @param message  String
     * @param httpCode int
     */
    public ErrorCode(String code, String message, int httpCode) {
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }

    /**
     * register
     *
     * @param code    String
     * @param message String
     * @return ErrorCode
     */
    public static ErrorCode register(String code, String message) {
        return register(code, message, 200);
    }

    /**
     * register
     *
     * @param code     String
     * @param message  String
     * @param httpCode int
     * @return ErrorCode
     */
    public static ErrorCode register(String code, String message, int httpCode) {
        ErrorCode errorCode = new ErrorCode(code, message, httpCode);
        map.put(errorCode.getCode(), errorCode);
        return errorCode;
    }
}
