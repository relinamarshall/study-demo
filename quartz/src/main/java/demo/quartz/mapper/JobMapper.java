package demo.quartz.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

import demo.quartz.entity.domain.JobAndTrigger;

/**
 * JobMapper
 *
 * @author Wenzhou
 * @since 2023/3/22 15:35
 */
@Component
public interface JobMapper {
    /**
     * 查询定时作业和触发器列表
     *
     * @return 定时作业和触发器列表
     */
    List<JobAndTrigger> list();
}
