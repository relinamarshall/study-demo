package demo.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import demo.mongodb.pojo.Book;

/**
 * @author Wenzhou
 * @since 2023/5/29 17:04
 */
public interface BookRepository extends MongoRepository<Book, Long> {
    /**
     * findByTypeLike
     *
     * @param type String
     * @return List<Book>
     */
    List<Book> findByTypeLike(String type);
}
