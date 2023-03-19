package demo.exceptionhandler.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.exceptionhandler.exception.JsonException;
import demo.exceptionhandler.exception.PageException;
import demo.exceptionhandler.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * ExceptionHandler
 * <p>
 * 统一异常处理
 *
 * @author Wenzhou
 * @since 2023/3/19 19:27
 */
@Slf4j
@ControllerAdvice
public class DemoExceptionHandler {
    /**
     * DEFAULT_ERROR_VIEW
     */
    private static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * jsonErrorHandler
     * 统一 json 异常处理
     *
     * @param exception JsonException
     * @return 统一返回 json 格式
     */
    @ResponseBody
    @ExceptionHandler(value = JsonException.class)
    public ApiResponse jsonErrorHandler(JsonException exception) {
        log.error("【JsonException】:{}", exception.getMessage());
        return ApiResponse.ofException(exception);
    }

    /**
     * pageErrorHandler
     * 统一 页面 异常处理
     *
     * @param exception PageException
     * @return 统一跳转到异常页面
     */
    @ExceptionHandler(value = PageException.class)
    public ModelAndView pageErrorHandler(PageException exception) {
        log.error("【DemoPageException】:{}", exception.getMessage());
        ModelAndView view = new ModelAndView();
        view.addObject("message", exception.getMessage());
        view.setViewName(DEFAULT_ERROR_VIEW);
        return view;
    }
}
