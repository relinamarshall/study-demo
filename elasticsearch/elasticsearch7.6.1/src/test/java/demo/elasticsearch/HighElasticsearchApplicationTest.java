package demo.elasticsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import demo.elasticsearch.constants.ElasticsearchConstant;
import demo.elasticsearch.model.Person;
import demo.elasticsearch.service.PersonService;

/**
 * HighElasticsearchApplicationTest
 *
 * @author Wenzhou
 * @since 2023/3/15 22:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HighElasticsearchApplicationTest {

    @Autowired
    private PersonService personService;

    /**
     * 测试删除索引
     */
    @Test
    public void deleteIndexTest() {
        personService.deleteIndex(ElasticsearchConstant.INDEX_NAME);
    }

    /**
     * 测试创建索引
     */
    @Test
    public void createIndexTest() {
        personService.createIndex(ElasticsearchConstant.INDEX_NAME);
    }

    /**
     * 测试新增
     */
    @Test
    public void insertTest() {
        List<Person> list = new ArrayList<>();
        list.add(Person.builder().age(11).birthday(new Date())
                .country("CN").id(1L).name("张三").remark("test1").build());
        list.add(Person.builder().age(22).birthday(new Date())
                .country("US").id(2L).name("李四").remark("test2").build());
        list.add(Person.builder().age(33).birthday(new Date())
                .country("ID").id(3L).name("王五").remark("test3").build());

        personService.insert(ElasticsearchConstant.INDEX_NAME, list);
    }

    /**
     * 测试更新
     */
    @Test
    public void updateTest() {
        Person person = Person.builder().age(33).birthday(new Date())
                .country("ID_update").id(3L).name("呵呵update").remark("test3_update").build();
        List<Person> list = new ArrayList<>();
        list.add(person);
        personService.update(ElasticsearchConstant.INDEX_NAME, list);
    }

    /**
     * 测试删除
     */
    @Test
    public void deleteTest() {
        personService.delete(ElasticsearchConstant.INDEX_NAME, Person.builder().id(1L).build());
    }

    /**
     * 测试查询
     */
    @Test
    public void searchListTest() {
        List<Person> personList = personService.searchList(ElasticsearchConstant.INDEX_NAME);
        System.out.println(personList);
    }
}