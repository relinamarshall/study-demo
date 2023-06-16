package demo.utils.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * ISimpleCache
 *
 * @author Wenzhou
 * @since 2023/6/15 10:00
 */
public interface ISimpleCache {
    void put(String k, Object v);

    default <T> T get(String k, T defaultValue) {
        T value = this.get(k);
        return Optional.ofNullable(value).orElse(defaultValue);
    }

    <T> T get(String k);

    default <T> List<T> multiGet(String... keys) {
        HashSet<String> set = new HashSet<>(keys.length);
        Collections.addAll(set, keys);
        return this.multiGet(set);
    }

    default <T> List<T> multiGet(Set<String> keys) {
        List<T> items = new ArrayList<>(keys.size());
        for (String key : keys) {
            T item = this.get(key);
            if (null != item) {
                items.add(item);
            }
        }
        return items;
    }

    default <T> void multiPut(Map<String, T> map) {
        for (Map.Entry<String, T> item : map.entrySet()) {
            this.put(item.getKey(), item.getValue());
        }
    }

    void remove(String k);

    void removeKeys(String... keys);

    boolean exists(String key);

    void clear();
}
