package demo.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * QuartzApplication
 *
 * @author Wenzhou
 * @since 2023/3/22 15:33
 */
@SpringBootApplication
@MapperScan(basePackages = {"demo.quartz.mapper"})
public class QuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }
}
