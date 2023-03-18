package demo.easyexcel.read.dao;

import java.util.List;

import demo.easyexcel.read.dto.DemoDto;
import lombok.extern.slf4j.Slf4j;

/**
 * DemoDao
 *
 * @author Wenzhou
 * @since 2023/3/18 2:09
 */
@Slf4j
public class DemoDao {
    /**
     * save
     *
     * @param cacheDataList List<DemoData>
     */
    public void save(List<DemoDto> cacheDataList) {
        // 如果是mybatis 尽量别直接调用多次insert 自己写一个mapper里面新增一个方法 batchInsert 所有数据一次性插入
        cacheDataList.forEach(data -> {
            log.info("模拟插入数据库中>数据为:{}", data);
        });
    }
}
