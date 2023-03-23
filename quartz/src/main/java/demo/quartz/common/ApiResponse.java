package demo.quartz.common;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

import lombok.Data;

/**
 * ApiResponse
 *
 * @author Wenzhou
 * @since 2023/3/22 15:48
 */
@Data
public class ApiResponse implements Serializable {
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    /**
     * ApiResponse
     */
    public ApiResponse() {
    }

    /**
     * ApiResponse
     *
     * @param message String
     * @param data    Object
     */
    private ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    /**
     * 通用封装获取ApiResponse对象
     *
     * @param message 返回信息
     * @param data    返回数据
     * @return ApiResponse
     */
    public static ApiResponse of(String message, Object data) {
        return new ApiResponse(message, data);
    }

    /**
     * 通用成功封装获取ApiResponse对象
     *
     * @param data 返回数据
     * @return ApiResponse
     */
    public static ApiResponse ok(Object data) {
        return new ApiResponse(HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * 通用封装获取ApiResponse对象
     *
     * @param message 返回信息
     * @return ApiResponse
     */
    public static ApiResponse msg(String message) {
        return of(message, null);
    }
}
