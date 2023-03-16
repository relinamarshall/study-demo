package demo.shiro.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler
 *
 * @author Wenzhou
 * @since 2023/3/16 20:49
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * exceptionHandler
     *
     * @param e Exception
     * @return String
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        if (e instanceof AuthorizationException) {
            return "views/noauth";
        }
        return "";
    }
}
