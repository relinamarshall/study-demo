package demo.elasticsearch.common;

import org.springframework.lang.Nullable;

import java.io.Serializable;

import lombok.Data;

/**
 * Result
 *
 * @author Wenzhou
 * @since 2023/3/15 21:48
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1696194043024336235L;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * Result
     */
    public Result() {
    }

    /**
     * Result
     *
     * @param resultCode ResultCode
     */
    private Result(ResultCode resultCode) {
        this(resultCode.code, resultCode.msg);
    }

    /**
     * Result
     *
     * @param resultCode ResultCode
     * @param data       T
     */
    private Result(ResultCode resultCode, T data) {
        this(resultCode.code, resultCode.msg, data);
    }

    /**
     * Result
     *
     * @param code int
     * @param msg  String
     */
    private Result(int code, String msg) {
        this(code, msg, null);
    }

    /**
     * Result
     *
     * @param code int
     * @param msg  String
     * @param data T
     */
    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功
     *
     * @param <T> 泛型标记
     * @return 响应信息 {@code Result}
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS);
    }


    /**
     * 返回成功-携带数据
     *
     * @param data 响应数据
     * @param <T>  泛型标记
     * @return 响应信息 {@code Result}
     */
    public static <T> Result<T> success(@Nullable T data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }
}
