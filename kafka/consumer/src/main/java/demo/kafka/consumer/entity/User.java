package demo.kafka.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * User
 *
 * @author Wenzhou
 * @since 2023/3/28 10:22
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * id
     */
    private String id;
    /**
     * name
     */
    private String name;
    /**
     * age
     */
    private Integer age;
}
