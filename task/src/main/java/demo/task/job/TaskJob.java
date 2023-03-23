package demo.task.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * TaskJob
 *
 * @author Wenzhou
 * @since 2023/3/22 17:16
 */
@Slf4j
@Component
public class TaskJob {
    /**
     * open
     */
    @Value(value = "${open:false}")
    private boolean open;

    /**
     * job1
     * <p>
     * 按照标准时间来算，每隔 10s 执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void job1() {
        if (open) {
            log.info("【job1】开始执行：{}", DateUtil.formatDateTime(new Date()));
        }
    }

    /**
     * job2
     * <p>
     * 从启动时间开始，间隔 2s 执行
     * 固定间隔时间
     */
    @Scheduled(fixedRate = 2000)
    public void job2() {
        if (open) {
            log.info("【job2】开始执行：{}", DateUtil.formatDateTime(new Date()));
        }
    }

    /**
     * job3
     * <p>
     * 从启动时间开始，延迟 5s 后间隔 4s 执行
     * 固定等待时间
     */
    @Scheduled(fixedDelay = 4000, initialDelay = 5000)
    public void job3() {
        if (open) {
            log.info("【job3】开始执行：{}", DateUtil.formatDateTime(new Date()));
        }
    }
}
