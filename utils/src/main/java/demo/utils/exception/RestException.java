package demo.utils.exception;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

/**
 * RestException
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/9/3
 */
public class RestException extends RuntimeException {
    /**
     * error
     */
    @Getter
    private ErrorCode error;

    /**
     * RestException
     *
     * @param error ErrorCode
     */
    public RestException(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }


    /**
     * RestException
     *
     * @param error   ErrorCode
     * @param message String
     */
    public RestException(ErrorCode error, String message) {
        super(StringUtils.isEmpty(message) ? error.getMessage() : message);
        this.error = error;
    }

    /**
     * RestException
     *
     * @param error ErrorCode
     * @param cause Throwable
     */
    public RestException(ErrorCode error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
    }

    /**
     * RestException
     *
     * @param error   ErrorCode
     * @param message String
     * @param cause   Throwable
     */
    public RestException(ErrorCode error, String message, Throwable cause) {
        super(StringUtils.isEmpty(message) ? error.getMessage() : message, cause);
        this.error = error;
    }

    @Override
    public String toString() {
        return String.format("error code [%s], %s.", this.error.getCode(), super.toString());
    }
}
