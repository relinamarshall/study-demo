package demo.quartz.job;

import org.quartz.JobExecutionContext;

import cn.hutool.core.date.DateUtil;
import demo.quartz.job.base.BaseJob;
import lombok.extern.slf4j.Slf4j;

/**
 * HelloJob
 *
 * @author Wenzhou
 * @since 2023/3/22 15:37
 */
@Slf4j
public class HelloJob implements BaseJob {
    @Override
    public void execute(JobExecutionContext context) {
        log.error("Hello Job 执行时间: {}", DateUtil.now());
    }
}
