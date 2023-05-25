package demo.webflux.handler;

import com.wf.captcha.ArithmeticCaptcha;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import demo.webflux.config.CodeMap;
import reactor.core.publisher.Mono;

/**
 * ImageCodeHandler
 *
 * @author Wenzhou
 * @since 2023/5/25 10:15
 */
public class ImageCodeHandler implements HandlerFunction<ServerResponse> {
    public static final Integer DEFAULT_IMAGE_WIDTH = 100;
    public static final Integer DEFAULT_IMAGE_HEIGHT = 40;
    public static final Integer DEFAULT_IMAGE_LEN = 2;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT, DEFAULT_IMAGE_LEN);
        String result = captcha.text();

        // 保存验证码信息
        String id = request.pathVariable("id");
        CodeMap.set(id, result);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        captcha.out(os);

        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(BodyInserters.fromResource(new ByteArrayResource(os.toByteArray())));
    }
}
