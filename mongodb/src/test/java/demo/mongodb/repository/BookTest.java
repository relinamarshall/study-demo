package demo.mongodb.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import cn.hutool.json.JSONUtil;
import demo.mongodb.MongoDBApplicationTest;
import demo.mongodb.pojo.Book;
import lombok.extern.slf4j.Slf4j;

/**
 * BookTest
 *
 * @author Wenzhou
 * @since 2023/5/29 16:52
 */
@Slf4j
public class BookTest extends MongoDBApplicationTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void addBook() {
        Book book = new Book();
        book.setId(11L);
        book.setName("活着");
        book.setType("余华");
        book.setDesc("活着");
        mongoTemplate.save(book);
    }

    @Test
    public void findAll() {
        List<Book> all = mongoTemplate.findAll(Book.class);
        System.out.println(all);
    }

    @Test
    public void findByTypeLike() {
        List<Book> books = bookRepository.findByTypeLike("余华");
        log.info("【books】= {}", JSONUtil.toJsonStr(books));
    }
}
