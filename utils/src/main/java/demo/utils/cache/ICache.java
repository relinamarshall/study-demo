package demo.utils.cache;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ICache
 *
 * @author Wenzhou
 * @since 2023/6/15 15:57
 */
public interface ICache extends ISimpleCache{
    String getName();

    void put(String v1, Object v2, long v3, TimeUnit v5);

    void putAndRefreshTTL(String v1, Object v2);

    void putMap(String v1, Map<String, Object> v2);

    void putMap(String v1, String v2, Object v3);

    Map<String, Object> getEntries(String v1);

    void removeInMap(String v1, String v2);

    <T> T getAndRefreshTTL(String v1);

    <T> T getAndRefreshTti(String v1);

    <T> T getFromMap(String v1, String v2);

    void refresh(ICacheLoader loader);

    boolean setTtlTimeout(String v1, long v2, TimeUnit v3);
}
