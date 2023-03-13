package demo.xxl.job.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;

import org.springframework.stereotype.Component;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import static com.xxl.job.core.biz.model.ReturnT.FAIL;
import static com.xxl.job.core.biz.model.ReturnT.SUCCESS;

/**
 * 测试定时任务
 *
 * @author Wenzhou
 * @date 2023-03-13 16:06
 */
@Slf4j
@Component
public class XxlJobHandler {

    /**
     * execute
     *
     * @param param String
     * @return ReturnT<String>
     */
    @XxlJob("demoTask")
    public ReturnT<String> execute(String param) {
        // 可以动态获取传递过来的参数，根据参数不同，当前调度的任务不同
        log.info("【param】= {}", param);
        XxlJobLogger.log("demo task run at : {}", DateUtil.now());
        return RandomUtil.randomInt(1, 11) % 2 == 0 ? SUCCESS : FAIL;
    }
}