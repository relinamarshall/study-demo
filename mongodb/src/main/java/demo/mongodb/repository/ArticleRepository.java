package demo.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import demo.mongodb.pojo.Article;

/**
 * ArticleRepository
 *
 * @author Wenzhou
 * @since 2023/5/29 16:54
 */
public interface ArticleRepository extends MongoRepository<Article, Long> {
    /**
     * 根据标题模糊查询
     *
     * @param title 标题
     * @return 满足条件的文章列表
     */
    List<Article> findByTitleLike(String title);
}