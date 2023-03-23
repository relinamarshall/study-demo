package demo.neo4j.config;

import org.neo4j.ogm.id.IdStrategy;

import cn.hutool.core.util.IdUtil;

/**
 * CustomIdStrategy
 *
 * @author Wenzhou
 * @since 2023/3/22 17:39
 */
public class CustomIdStrategy implements IdStrategy {

    @Override
    public Object generateId(Object o) {
        return IdUtil.fastUUID();
    }
}
