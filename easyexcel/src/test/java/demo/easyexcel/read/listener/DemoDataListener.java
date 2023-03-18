package demo.easyexcel.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;

import java.util.List;

import demo.easyexcel.read.dao.DemoDao;
import demo.easyexcel.read.dto.DemoDto;
import lombok.extern.slf4j.Slf4j;

/**
 * DemoDataListener
 *
 * @author Wenzhou
 * @since 2023/3/18 2:25
 */
@Slf4j
public class DemoDataListener implements ReadListener<DemoDto> {
    /**
     * BATCH_COUNT
     * 每个5条存储数据库，实际使用可以是100条，然后清理list,方便内存回收
     */
    private static final int BATCH_COUNT = 10;

    /**
     * cacheDataList
     * 缓存的数据
     */
    private List<DemoDto> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * demoDao
     * 假设这个是一个DAO 当然有业务逻辑这个也可以是service 当然如果不用存储这个对象没用
     */
    private final DemoDao demoDao;

    /**
     * DemoDataListener
     */
    public DemoDataListener() {
        // 这里demo 所以随便new一个 实际中应该用spring构造函数注入的方式
        demoDao = new DemoDao();
    }

    /**
     * DemoDataListener
     * 如果使用了Spring 请使用这个构造方法 每次创建的时候需要把spring管理的类传进来
     *
     * @param demoData DemoDao
     */
    public DemoDataListener(DemoDao demoData) {
        this.demoDao = demoData;
    }

    /**
     * invoke
     * 这个每一条数据解析都会调用
     *
     * @param data            DemoDto
     * @param analysisContext AnalysisContext
     */
    @Override
    public void invoke(DemoDto data, AnalysisContext analysisContext) {
        System.out.println("自定义读取监听回调:解析到一条数据:" + JSON.toJSONString(data));
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cacheDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库 防止数据几万条数据在内存中 容易oom
        if (cacheDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * doAfterAllAnalysed
     * 所有数据解析完成了 都会来调用
     *
     * @param analysisContext AnalysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储 到数据库
        saveData();
        System.out.println("自定义读取监听回调:所有数据解析完成!");
        log.info("所有数据解析完成!");
    }

    /**
     * saveData
     * 加上存储数据库
     */
    private void saveData() {
        System.out.println("自定义读取监听回调:" + cacheDataList.size() + "条数据，开始存储数据库!");
        System.out.println("自定义读取监听回调:存储数据库成功");
        log.info("{}条数据，开始存储数据库!", cacheDataList.size());
        demoDao.save(cacheDataList);
        log.info("存储数据库成功!");
    }
}
