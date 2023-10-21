package demo.hazelcast.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/10/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {
    /**
     * name
     */
    private String name;
    /**
     * address
     */
    private String address;
}