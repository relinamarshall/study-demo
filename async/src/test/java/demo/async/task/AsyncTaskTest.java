package demo.async.task;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

import demo.async.AsyncApplicationTest;
import lombok.extern.slf4j.Slf4j;

/**
 * AsyncTaskTest
 *
 * @author Wenzhou
 * @since 2023/3/19 18:13
 */
@Slf4j
@Component
public class AsyncTaskTest extends AsyncApplicationTest {
    /**
     * task
     */
    @Autowired
    private TaskFactory task;

    /**
     * asyncTaskTest
     * 测试异步任务
     */
    @Test
    public void asyncTaskTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        Future<Boolean> asyncTask1 = task.asyncTask1();
        Future<Boolean> asyncTask2 = task.asyncTask2();
        Future<Boolean> asyncTask3 = task.asyncTask3();

        // 调用 get() 阻塞主线程
        //asyncTask1.get();
        //asyncTask2.get();
        //asyncTask3.get();
        long end = System.currentTimeMillis();

        log.info("异步任务全部执行结束，总耗时：{} 毫秒", (end - start));
    }

    /**
     * taskTest
     * 测试同步任务
     */
    @Test
    public void taskTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        task.task1();
        task.task2();
        task.task3();
        long end = System.currentTimeMillis();

        log.info("同步任务全部执行结束，总耗时：{} 毫秒", (end - start));
    }
}
