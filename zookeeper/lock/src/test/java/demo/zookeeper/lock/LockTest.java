package demo.zookeeper.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import demo.zookeeper.lock.annotation.ZooLock;
import demo.zookeeper.lock.aspectj.ZooLockAspect;
import lombok.extern.slf4j.Slf4j;

/**
 * LockTest
 *
 * @author Wenzhou
 * @since 2023/3/28 17:24
 */
@Slf4j
public class LockTest extends ZookeeperLockApplicationTest {
    /**
     * count
     */
    private Integer count = 1000;
    /**
     * executorService
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);
    /**
     * zkClient
     */
    @Autowired
    private CuratorFramework zkClient;

    /**
     * getCount
     *
     * @return Integer
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 不使用分布式锁，程序结束查看count的值是否为0
     */
    @Test
    public void test() throws InterruptedException {
        IntStream.range(0, 1000).forEach(i -> executorService.execute(this::doBuy));
        TimeUnit.MINUTES.sleep(1);
        log.error("count值为{}", count);
    }

    /**
     * 测试AOP分布式锁
     */
    @Test
    public void testAopLock() throws InterruptedException {
        // 测试类中使用AOP需要手动代理
        LockTest target = new LockTest();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        ZooLockAspect aspect = new ZooLockAspect(zkClient);
        factory.addAspect(aspect);
        LockTest proxy = factory.getProxy();
        IntStream.range(0, 1000).forEach(i -> executorService.execute(() -> proxy.aopBuy(i)));
        TimeUnit.MINUTES.sleep(1);
        log.error("count值为{}", proxy.getCount());
    }

    /**
     * 测试手动加锁
     */
    @Test
    public void testManualLock() throws InterruptedException {
        IntStream.range(0, 1000).forEach(i -> executorService.execute(this::manualBuy));
        TimeUnit.MINUTES.sleep(1);
        log.error("count值为{}", count);
    }

    @ZooLock(key = "buy", timeout = 1, timeUnit = TimeUnit.MINUTES)
    public void aopBuy(int userId) {
        log.info("{} 正在出库。。。", userId);
        doBuy();
        log.info("{} 扣库存成功。。。", userId);
    }

    /**
     * manualBuy
     */
    public void manualBuy() {
        String lockPath = "/buy";
        log.info("try to buy sth.");
        try {
            InterProcessMutex lock = new InterProcessMutex(zkClient, lockPath);
            try {
                if (lock.acquire(1, TimeUnit.MINUTES)) {
                    doBuy();
                    log.info("buy successfully!");
                }
            } finally {
                lock.release();
            }
        } catch (Exception e) {
            log.error("zk error");
        }
    }

    /**
     * doBuy
     */
    public void doBuy() {
        count--;
        log.info("count值为{}", count);
    }
}
