package demo.exceptionhandler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.exceptionhandler.constant.Status;
import demo.exceptionhandler.exception.JsonException;
import demo.exceptionhandler.exception.PageException;
import demo.exceptionhandler.model.ApiResponse;

/**
 * DemoController
 *
 * @author Wenzhou
 * @since 2023/3/19 19:30
 */
@Controller
public class DemoController {
    /**
     * jsonException
     *
     * @return ApiResponse
     */
    @GetMapping("/json")
    @ResponseBody
    public ApiResponse jsonException() {
        throw new JsonException(Status.UNKNOWN_ERROR);
    }

    /**
     * pageException
     *
     * @return ModelAndView
     */
    @GetMapping("/page")
    public ModelAndView pageException() {
        throw new PageException(Status.UNKNOWN_ERROR);
    }
}
