package demo.security.exception;

/**
 * GlobalException
 *
 * @author Wenzhou
 * @since 2023/3/16 22:33
 */
public class GlobalException extends RuntimeException {
    /**
     * message
     */
    String message;

    /**
     * code
     */
    String code;

    /**
     * GlobalException
     */
    public GlobalException() {
        super();
    }

    /**
     * GlobalException
     *
     * @param message String
     * @param code    String
     */
    public GlobalException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
