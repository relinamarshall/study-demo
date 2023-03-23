package demo.quartz.controller;

import com.github.pagehelper.PageInfo;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import demo.quartz.common.ApiResponse;
import demo.quartz.entity.domain.JobAndTrigger;
import demo.quartz.entity.form.JobForm;
import demo.quartz.service.JobService;
import lombok.extern.slf4j.Slf4j;

/**
 * JobController
 *
 * @author Wenzhou
 * @since 2023/3/22 15:50
 */
@Slf4j
@RestController
@RequestMapping("/job")
public class JobController {
    /**
     * jobService
     */
    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * addJob
     * 保存定时任务
     *
     * @param form JobForm
     * @return ResponseEntity<ApiResponse>
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addJob(@Valid JobForm form) {
        try {
            jobService.addJob(form);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.msg(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ApiResponse.msg("操作成功"), HttpStatus.CREATED);
    }

    /**
     * deleteJob
     * 删除定时任务
     *
     * @param form JobForm
     * @return ResponseEntity<ApiResponse>
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteJob(JobForm form) throws SchedulerException {
        if (CharSequenceUtil.hasBlank(form.getJobGroupName(), form.getJobClassName())) {
            return new ResponseEntity<>(ApiResponse.msg("参数不能为空"), HttpStatus.BAD_REQUEST);
        }

        jobService.deleteJob(form);
        return new ResponseEntity<>(ApiResponse.msg("删除成功"), HttpStatus.OK);
    }

    /**
     * pauseJob
     * 暂停定时任务
     *
     * @param form JobForm
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping(params = "pause")
    public ResponseEntity<ApiResponse> pauseJob(JobForm form) throws SchedulerException {
        if (CharSequenceUtil.hasBlank(form.getJobGroupName(), form.getJobClassName())) {
            return new ResponseEntity<>(ApiResponse.msg("参数不能为空"), HttpStatus.BAD_REQUEST);
        }

        jobService.pauseJob(form);
        return new ResponseEntity<>(ApiResponse.msg("暂停成功"), HttpStatus.OK);
    }

    /**
     * resumeJob
     * 恢复定时任务
     *
     * @param form JobForm
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping(params = "resume")
    public ResponseEntity<ApiResponse> resumeJob(JobForm form) throws SchedulerException {
        if (CharSequenceUtil.hasBlank(form.getJobGroupName(), form.getJobClassName())) {
            return new ResponseEntity<>(ApiResponse.msg("参数不能为空"), HttpStatus.BAD_REQUEST);
        }

        jobService.resumeJob(form);
        return new ResponseEntity<>(ApiResponse.msg("恢复成功"), HttpStatus.OK);
    }

    /**
     * cronJob
     * 修改定时任务，定时时间
     *
     * @param form JobForm
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping(params = "cron")
    public ResponseEntity<ApiResponse> cronJob(@Valid JobForm form) {
        try {
            jobService.cronJob(form);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.msg(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ApiResponse.msg("修改成功"), HttpStatus.OK);
    }

    /**
     * jobList
     *
     * @param currentPage Integer
     * @param pageSize    Integer
     * @return ResponseEntity<ApiResponse>
     */
    @GetMapping
    public ResponseEntity<ApiResponse> jobList(Integer currentPage, Integer pageSize) {
        if (ObjectUtil.isNull(currentPage)) {
            currentPage = 1;
        }
        if (ObjectUtil.isNull(pageSize)) {
            pageSize = 10;
        }
        PageInfo<JobAndTrigger> all = jobService.list(currentPage, pageSize);
        return ResponseEntity.ok(ApiResponse.ok(Dict.create()
                .set("total", all.getTotal()).set("data", all.getList())));
    }
}
