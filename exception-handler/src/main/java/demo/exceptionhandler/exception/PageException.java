package demo.exceptionhandler.exception;

import demo.exceptionhandler.constant.Status;
import lombok.Getter;

/**
 * PageException
 * <p>
 * 页面异常
 *
 * @author Wenzhou
 * @since 2023/3/19 19:25
 */
@Getter
public class PageException extends BaseException {
    /**
     * PageException
     *
     * @param status Status
     */
    public PageException(Status status) {
        super(status);
    }

    /**
     * PageException
     *
     * @param code    Integer
     * @param message String
     */
    public PageException(Integer code, String message) {
        super(code, message);
    }
}
