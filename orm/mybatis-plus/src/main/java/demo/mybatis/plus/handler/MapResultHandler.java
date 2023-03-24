package demo.mybatis.plus.handler;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ResultHandler
 * <p>
 * 描述:将有来有两个返回值且需要将其中一个作为key一个作为value返回的ResultHandler
 *
 * @author Wenzhou
 * @since 2023/3/24 17:18
 */
@Component
public class MapResultHandler<K, V> implements ResultHandler<Map<K, V>> {
    /**
     * mappedResults
     */
    private final Map<K, V> mappedResults = new HashMap<>();
    /**
     * KEY
     */
    private static final String KEY = "key";
    /**
     * VALUE
     */
    private static final String VALUE = "value";

    /**
     * handleResult
     *
     * @param resultContext ResultContext
     */
    @Override
    public void handleResult(ResultContext<? extends Map<K, V>> resultContext) {
        Map map = resultContext.getResultObject();
        //key和value是xml中映射的
        mappedResults.put((K) map.get(KEY), (V) map.get(VALUE));
    }

    /**
     * getMappedResults
     *
     * @return Map<K, V>
     */
    public Map<K, V> getMappedResults() {
        return mappedResults;
    }
}
