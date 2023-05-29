package demo.mongodb.pojo;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Book
 *
 * @author Wenzhou
 * @since 2023/5/29 16:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    /**
     * id
     */
    @Id
    private Long id;
    /**
     * name
     */
    private String name;
    /**
     * type
     */
    private String type;
    /**
     * desc
     */
    private String desc;
}
