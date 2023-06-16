package demo.utils.cache;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisCache
 *
 * @author Wenzhou
 * @since 2023/6/15 16:11
 */
public class RedisCache extends BaseCache {
    private static final Logger log = LoggerFactory.getLogger(RedisCache.class);
    private final ValueOperations<String, Object> valueOps;
    private final HashOperations<String, String, Object> hashOps;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCache(RedisTemplate<String, Object> redisTemplate, String name, int maxTtl, int maxTti) {
        super(name, maxTtl, maxTti);
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.hashOps = redisTemplate.opsForHash();
    }


    @Override
    public void put(String k, Object v) {
        this.putAndRefreshTTL(k, v);
    }

    @Override
    public void putAndRefreshTTL(String k, Object v) {
        if (this.maxTtl > 0) {
            this.put(k, v, this.maxTtl, TimeUnit.SECONDS);
        } else {
            this.valueOps.set(k, v);
        }
    }

    @Override
    protected boolean isSupportTti() {
        return false;
    }

    @Override
    public void put(String k, Object v, long t, TimeUnit timeUnit) {
        this.valueOps.set(k, v, t, timeUnit);
        if (log.isDebugEnabled()) {
            log.debug("[{}]缓存加入信息，key:{},value:{},有效期:{} {}", this.getName(), k, v, t, timeUnit);
        }
    }

    @Override
    public void putMap(String k, Map<String, Object> map) {
        this.hashOps.putAll(k, map);
        if (this.maxTtl > 0) {
            this.redisTemplate.expire(k, this.maxTtl, TimeUnit.SECONDS);
        }
        if (log.isDebugEnabled()) {
            log.debug("[{}]缓存加入信息，key:{},hasKeys:{},有效期:{} sec.", this.getName(), k, map.keySet(), this.maxTtl);
        }
    }

    @Override
    public void putMap(String k, String hashKey, Object value) {
        this.hashOps.put(k, hashKey, value);
        if (this.maxTtl > 0) {
            this.redisTemplate.expire(k, this.maxTtl, TimeUnit.SECONDS);
        }
        if (log.isDebugEnabled()) {
            log.debug("[{}]缓存加入信息，key:{},hasKeys:{},value:{},有效期:{} sec.", this.getName(), k, hashKey, value, this.maxTtl);
        }
    }

    @Override
    public Map<String, Object> getEntries(String key) {
        return this.hashOps.entries(key);
    }

    @Override
    public void removeInMap(String k, String hk) {
        this.hashOps.delete(k, hk);
        if (log.isDebugEnabled()) {
            log.debug("[{}]缓存加入信息，key:{},hasKeys:{}", this.getName(), k, hk);
        }
    }

    @Override
    public <T> T get(String k) {
        return (T) this.valueOps.get(k);
    }

    @Override
    public <T> List<T> multiGet(Set<String> keys) {
        return (List<T>) this.valueOps.multiGet(keys);
    }

    @Override
    public <T> void multiPut(Map<String, T> map) {
        this.valueOps.multiSet(map);
        if (this.maxTtl > 0) {
            for (String key : map.keySet()) {
                this.setTtlTimeout(key, this.maxTtl, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public <T> T getAndRefreshTTL(String k) {
        T value = (T) this.valueOps.get(k);
        if (this.maxTtl > 0) {
            this.setTtlTimeout(k, this.maxTtl, TimeUnit.SECONDS);
        }
        return value;
    }

    @Override
    public <T> T getFromMap(String k, String hk) {
        return (T) this.hashOps.get(k, hk);
    }

    @Override
    public void remove(String k) {
        this.redisTemplate.delete(k);
        if (log.isDebugEnabled()) {
            log.debug("{}缓存删除信息,key:{}", this.getName(), k);
        }
    }

    @Override
    public void clear() {
        throw new NotImplementedException("not support clear for redis cache!");
    }

    @Override
    public boolean setTtlTimeout(String key, long t, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(this.redisTemplate.expire(key, t, timeUnit));
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(this.redisTemplate.hasKey(key));
    }

    public long getExpire(String key, TimeUnit timeUnit) {
        return this.redisTemplate.getExpire(key, timeUnit);
    }

    public void removeKeys(String... keys) {
        this.redisTemplate.delete(Arrays.asList(keys));
        if (log.isDebugEnabled()) {
            log.debug("{}缓存删除信息,keys:{}", this.getName(), String.join(",", keys));
        }
    }
}
