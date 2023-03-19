package demo.exceptionhandler.exception;

import demo.exceptionhandler.constant.Status;
import lombok.Getter;

/**
 * JsonException
 * <p>
 * JSON异常
 *
 * @author Wenzhou
 * @since 2023/3/19 19:24
 */
@Getter
public class JsonException extends BaseException {
    /**
     * JsonException
     *
     * @param status Status
     */
    public JsonException(Status status) {
        super(status);
    }

    /**
     * JsonException
     *
     * @param code    Integer
     * @param message String
     */
    public JsonException(Integer code, String message) {
        super(code, message);
    }
}
