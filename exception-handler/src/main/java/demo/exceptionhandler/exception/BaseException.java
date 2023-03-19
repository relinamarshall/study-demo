package demo.exceptionhandler.exception;

import demo.exceptionhandler.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BaseException
 *
 * @author Wenzhou
 * @since 2023/3/19 19:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    /**
     * code
     */
    private Integer code;
    /**
     * message
     */
    private String message;

    /**
     * BaseException
     *
     * @param status Status
     */
    public BaseException(Status status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    /**
     * BaseException
     *
     * @param code    Integer
     * @param message String
     */
    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
