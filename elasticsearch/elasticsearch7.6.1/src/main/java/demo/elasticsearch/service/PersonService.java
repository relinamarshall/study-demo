package demo.elasticsearch.service;

import org.springframework.lang.Nullable;

import java.util.List;

import demo.elasticsearch.model.Person;

/**
 * PersonService
 *
 * @author Wenzhou
 * @since 2023/3/15 22:15
 */
public interface PersonService {
    /**
     * create Index
     *
     * @param index elasticsearch index name
     */
    void createIndex(String index);

    /**
     * delete Index
     *
     * @param index elasticsearch index name
     */
    void deleteIndex(String index);

    /**
     * insert document source
     *
     * @param index elasticsearch index name
     * @param list  data source
     */
    void insert(String index, List<Person> list);

    /**
     * update document source
     *
     * @param index elasticsearch index name
     * @param list  data source
     */
    void update(String index, List<Person> list);

    /**
     * delete document source
     *
     * @param person delete data source and allow null object
     */
    void delete(String index, @Nullable Person person);

    /**
     * search all doc records
     *
     * @param index elasticsearch index name
     * @return person list
     */
    List<Person> searchList(String index);
}
