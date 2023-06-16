package demo.utils.cache;

import java.util.concurrent.TimeUnit;

/**
 * BaseCache
 *
 * @author Wenzhou
 * @since 2023/6/15 16:03
 */
public abstract class BaseCache implements ICache {
    private String name;
    protected int maxTtl;
    protected int maxTti;

    public BaseCache(String name, int maxTtl, int maxTti) {
        this.name = name;
        this.maxTtl = maxTtl;
        this.maxTti = maxTti;
    }

    public String getName() {
        return this.name;
    }

    protected boolean isSupportTti() {
        return true;
    }

    public <T> T getAndRefreshTti(String key) {
        T val = this.get(key);
        if (!this.isSupportTti() && this.maxTti > 0) {
            this.setTtlTimeout(key, (long) this.maxTti, TimeUnit.SECONDS);
        }
        return val;
    }

    public void refresh(ICacheLoader loader) {
        loader.load(this);
    }

    public void removeKeys(String... keys) {
        for (String key : keys) {
            this.remove(key);
        }
    }
}
