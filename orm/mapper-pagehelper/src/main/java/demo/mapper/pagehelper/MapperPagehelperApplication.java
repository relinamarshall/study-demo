package demo.mapper.pagehelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * MapperPagehelperApplication
 *
 * @author Wenzhou
 * @since 2023/3/21 16:49
 */
@SpringBootApplication
@MapperScan(basePackages = {"demo.mapper.pagehelper.mapper"})
public class MapperPagehelperApplication {
    public static void main(String[] args) {
        SpringApplication.run(MapperPagehelperApplication.class, args);
    }
}
