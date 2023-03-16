package demo.elasticsearch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import cn.hutool.json.JSONUtil;
import demo.elasticsearch.model.Person;
import demo.elasticsearch.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

/**
 * EsController
 *
 * @author Wenzhou
 * @since 2023/3/15 20:54
 */
@RestController
@RequiredArgsConstructor
public class EsController {
    /**
     * personRepository
     */
    private final PersonRepository personRepository;

    /**
     * selectRangeAge
     *
     * @param min Integer
     * @param max Integer
     * @return String
     */
    @GetMapping("/selectRangeAge/{min}/{max}")
    public String selectRangeAge(@PathVariable("min") Integer min,
                                 @PathVariable("max") Integer max) {
        List<Person> personList = personRepository.findByAgeBetween(min, max);
        return JSONUtil.toJsonStr(personList);
    }
}
