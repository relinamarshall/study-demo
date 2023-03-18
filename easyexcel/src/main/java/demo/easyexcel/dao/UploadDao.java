package demo.easyexcel.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import demo.easyexcel.dto.UploadDto;
import lombok.extern.slf4j.Slf4j;

/**
 * UploadDao
 *
 * @author Wenzhou
 * @since 2023/3/18 3:01
 */
@Slf4j
@Repository
public class UploadDao {
    /**
     * save
     *
     * @param list List<UploadDto>
     */
    public void save(List<UploadDto> list) {
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
        for (UploadDto data : list) {
            log.info(data.toString());
        }

    }
}
