package demo.mongodb.pojo;

/**
 * Article
 *
 * @author Wenzhou
 * @since 2023/5/29 16:54
 */

import org.springframework.data.annotation.Id;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 文章实体类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-28 16:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    /**
     * 文章id
     */
    @Id
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 点赞数量
     */
    private Long thumbUp;

    /**
     * 访客数量
     */
    private Long visits;
}

