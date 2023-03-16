package demo.elasticsearch.exception;

import demo.elasticsearch.common.ResultCode;
import lombok.Getter;

/**
 * ElasticsearchException
 *
 * @author Wenzhou
 * @since 2023/3/15 22:09
 */
public class ElasticsearchException extends RuntimeException {
    /**
     * code
     */
    @Getter
    private int code;

    /**
     * msg
     */
    @Getter
    private String msg;

    /**
     * @param resultCode {@link demo.elasticsearch.common.ResultCode}
     */
    public ElasticsearchException(ResultCode resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    /**
     * @param message String
     */
    public ElasticsearchException(String message) {
        super(message);
    }

    /**
     * @param code Integer
     * @param msg  String
     */
    public ElasticsearchException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param message String
     * @param cause   Throwable
     */
    public ElasticsearchException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause Throwable
     */
    public ElasticsearchException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message            String
     * @param cause              Throwable
     * @param enableSuppression  boolean
     * @param writableStackTrace boolean
     */
    public ElasticsearchException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
