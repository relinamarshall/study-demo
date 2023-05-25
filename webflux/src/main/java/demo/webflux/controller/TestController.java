package demo.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import reactor.core.publisher.Mono;

/**
 * TestController
 *
 * @author Wenzhou
 * @since 2023/5/25 9:48
 */
@RestController
public class TestController {
    /**
     * hello
     *
     * @return String
     */
    @GetMapping("/hello")
    public String hello() {
        long start = System.currentTimeMillis();
        String result = getHelloWorld();
        System.out.println("普通接口耗时：" + (System.currentTimeMillis() - start) + "ms");
        return result;
    }


    /**
     * 不过需要注意的是，接口的响应时间并不会因为使用了 WebFlux 而缩短，服务端的处理结果还是得由 worker 线程处理完成之后再返回给前端。
     *
     * @return Mono<String>
     */
    @GetMapping("/hello2")
    public Mono<String> hello2() {
        long start = System.currentTimeMillis();
        Mono<String> result = Mono.fromSupplier(() -> getHelloWorld());
        System.out.println("WebFlux 接口耗时：" + (System.currentTimeMillis() - start) + "ms");
        return result;
    }

    private String getHelloWorld() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello World";
    }
}
