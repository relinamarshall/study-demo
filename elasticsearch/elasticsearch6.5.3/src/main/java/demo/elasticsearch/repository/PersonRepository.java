package demo.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

import demo.elasticsearch.model.Person;

/**
 * PersonRepository
 * <p>
 * 用户持久层
 *
 * @author Wenzhou
 * @since 2023/3/15 19:26
 */
public interface PersonRepository extends ElasticsearchRepository<Person, Long> {
    /**
     * 根据年龄区间查询
     *
     * @param min 最小值
     * @param max 最大值
     * @return 满足条件的用户列表
     */
    List<Person> findByAgeBetween(Integer min, Integer max);
}
