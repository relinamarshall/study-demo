package demo.easyexcel.fill;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import demo.easyexcel.fill.dto.FillDto;
import demo.easyexcel.util.PathUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * TestEasyExcelFill
 *
 * @author Wenzhou
 * @since 2023/3/17 12:26
 */
@Slf4j
public class TestEasyExcelFill {
    /**
     * simpleFill
     * <p>
     * 最简单的填充
     */
    @Test
    public void simpleFill() {
        // 模板注意 用{}来表示你要用的变量 如果本来就有‘{’,‘}’特殊字符 用‘\{’，'\}'代替
        String templateFileName = PathUtil.getPath("fill", "simple.xlsx");
        log.info(templateFileName);

        // 方案1 根据对象填充
        String fileName = PathUtil.getOutPath("simpleFillFormObject");
        log.info(fileName);
        // 这里 会填充到第一个sheet 然后文件流会自动关闭
        FillDto fillData = new FillDto();
        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(fillData);

        // 方案2 根据Map填充
        fileName = PathUtil.getOutPath("simpleFillFormMap");
        log.info(fileName);
        // 这里 会填充到第一个sheet 然后文件流会自动关闭
        Map<String, Object> map = MapUtils.newHashMap();
        map.put("name", "张三");
        map.put("number", 5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);
    }

    /**
     * listFill
     * <p>
     * 填充列表
     */
    @Test
    public void listFill() {
        // 模板注意 用{}来表示你要用的变量 如果本来就有‘{’,‘}’特殊字符 用‘\{’，'\}'代替
        // 填充list 的时候还要注意 模板中{.}多了个点 表示list
        String templateFileName = PathUtil.getPath("fill", "list.xlsx");
        log.info(templateFileName);

        // 方案1 一下子全部放到内存里面 并填充
        String fileName = PathUtil.getOutPath("listFill");
        log.info(fileName);
        // 这里 会填充到第一个sheet 然后文件流会自动关闭
        // 分页查询数据
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(this::data);

        // 方案2 分多次 填充 会使用文件缓存（省内存） jdk8
        fileName = PathUtil.getOutPath("listFill");
        log.info(fileName);
        EasyExcel.write(fileName)
                .withTemplate(templateFileName)
                .sheet()
                .doFill(this::data);

        // 方案3 分多次 填充 会使用文件缓存 （省内存）
        fileName = PathUtil.getOutPath("listFill");
        log.info(fileName);
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(data(), writeSheet);
            excelWriter.fill(data(), writeSheet);
        }
    }

    /**
     * complexFill
     * <p>
     * 复杂的填充
     */
    @Test
    public void complexFill() {
        String templateFileName = PathUtil.getPath("fill", "complex.xlsx");
        log.info(templateFileName);
        String fileName = PathUtil.getOutPath("complexFill");
        log.info(fileName);

        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            // 这里注意 入参用了 forceNewRow list后如果数据
            //      FALSE 会占用数据行，数据行是固定的
            //      TRUE 会在数据行之前添加数据，数据行会随着list内容移动
            // forceNewRow 如果设置了true 有个缺点 就是他会把所有数据放入内存 所以慎用
            // 简单的说 如果你的模板有list ,且list不是最后一行 下面还有数据需要填充
            //      就必须设置forceNewRow=true 但是这个就会把所有数据放到内存 会耗内存
            // 如果数据量大 list不是最后一行 参照下一个
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(data(), fillConfig, writeSheet);
            excelWriter.fill(data(), fillConfig, writeSheet);
            Map<String, Object> map = MapUtils.newHashMap();
            map.put("date", "2022年8月30日16:40:10");
            map.put("total", 1000);
            excelWriter.fill(map, writeSheet);
        }
    }

    /**
     * complexFillWithTable
     * <p>
     * 数据量大的复杂填充
     */
    @Test
    public void complexFillWithTable() {
        String templateFileName = PathUtil.getPath("fill", "complexFillWithTable.xlsx");
        log.info(templateFileName);
        String fileName = PathUtil.getOutPath("complexFillWithTable");
        log.info(fileName);

        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            // 直接写数据
            excelWriter.fill(data(), writeSheet);
            excelWriter.fill(data(), writeSheet);

            // 写入list之前的数据
            HashMap<String, Object> map = new HashMap<>();
            map.put("date", "2022年8月30日16:40:10");
            excelWriter.fill(map, writeSheet);

            // list 后面还有个统计 想办法手动写入
            // 这里偷懒直接用list 也可以用对象
            List<List<String>> totalListList = ListUtils.newArrayList();
            List<String> totalList = ListUtils.newArrayList();
            totalListList.add(totalList);
            totalList.add(null);
            totalList.add(null);
            totalList.add(null);
            // 第四列
            totalList.add("统计:1000");
            // 这里是write别和fill搞错了
            excelWriter.write(totalListList, writeSheet);
            // 总体上写法比较复杂 但是也没有想到好的版本 异步的去写入excel 不支持行的删除和移动 也不支持备注这种的写入
            // 所以也排除了可以新建一个 然后一点点复制过来的方案
            // 最后导致list要新增行的时候，后面的列的数据没法后移，后续会继续想想解决方案
        }
    }

    /**
     * horizontalFill
     * <p>
     * 横向的填充
     */
    @Test
    public void horizontalFill() {
        String templateFileName = PathUtil.getPath("fill", "horizontal.xlsx");
        log.info(templateFileName);
        String fileName = PathUtil.getOutPath("horizontalFill");
        log.info(fileName);

        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
            excelWriter.fill(data(), fillConfig, writeSheet);
            excelWriter.fill(data(), fillConfig, writeSheet);
            Map<String, Object> map = new HashMap<>();
            map.put("date", "2022年8月30日16:40:10");
            excelWriter.fill(map, writeSheet);
        }
    }

    /**
     * compositeFill
     * <p>
     * 多列表组合填充
     */
    @Test
    public void compositeFill() {
        String templateFileName = PathUtil.getPath("fill", "composite.xlsx");
        log.info(templateFileName);
        String fileName = PathUtil.getOutPath("compositeFill");
        log.info(fileName);

        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
            // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是data1 然后多个list必须用FillWrapper包裹
            excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
            excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
            excelWriter.fill(new FillWrapper("data2", data()), writeSheet);
            excelWriter.fill(new FillWrapper("data2", data()), writeSheet);
            excelWriter.fill(new FillWrapper("data3", data()), writeSheet);
            excelWriter.fill(new FillWrapper("data3", data()), writeSheet);

            Map<String, Object> map = new HashMap<>();
            //map.put("date", "2022年8月30日16:40:10");
            map.put("date", new Date());

            excelWriter.fill(map, writeSheet);
        }
    }

    /**
     * data
     *
     * @return List<FillDto>
     */
    private List<FillDto> data() {
        List<FillDto> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            FillDto fillData = new FillDto();
            list.add(fillData);
            fillData.setName("张三");
            fillData.setNumber(5.2);
            fillData.setDate(new Date());
        }
        return list;
    }
}
