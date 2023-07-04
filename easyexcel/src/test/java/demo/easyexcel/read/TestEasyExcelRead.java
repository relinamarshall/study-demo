package demo.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

import demo.easyexcel.read.dto.CellDataReadDemoDto;
import demo.easyexcel.read.dto.ConverterDto;
import demo.easyexcel.read.dto.DemoDto;
import demo.easyexcel.read.dto.DemoExtraDto;
import demo.easyexcel.read.dto.ExceptionDemoDto;
import demo.easyexcel.read.listener.CellDataDemoHeadDataListener;
import demo.easyexcel.read.listener.CheckHeadListener;
import demo.easyexcel.read.listener.ConverterDataListener;
import demo.easyexcel.read.listener.DemoDataListener;
import demo.easyexcel.read.listener.DemoExceptionListener;
import demo.easyexcel.read.listener.DemoExtraListener;
import demo.easyexcel.read.listener.DemoHeadDataListener;
import demo.easyexcel.read.listener.NoModelDataListener;
import demo.easyexcel.util.PathUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * TestEasyExcelRead
 *
 * @author Wenzhou
 * @since 2023/3/18 2:43
 */
@Slf4j
public class TestEasyExcelRead {
    /**
     * simpleRead
     * 最简单的读
     * <p>
     * 1.创建excel对应的实体对象 参照{@link demo.easyexcel.read.dto.DemoDto}
     * <p>
     * 2.由于默认一行行的读取excel,所以需要创建excel一行行的回调监听器 参照{@link DemoDataListener}
     * <p>
     * 3.直接读即可
     */
    @Test
    public void simpleRead() {
        String fileName = PathUtil.getPath("demo", "demo.xlsx");
        log.info(fileName);

        // 写法1 JDK8+ 不用额外写一个DemoDataListener
        // 需要指定读用哪个class去读 然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回 直接调用使用数据就行
        EasyExcel.read(fileName, DemoDto.class,
                new PageReadListener<DemoDto>(dataList -> dataList.forEach(demoData -> {
                    System.out.println("method01:读取到一个数据" + JSON.toJSONString(demoData));
                    log.info("method01:读取到一个数据{}", JSON.toJSONString(demoData));
                }))).sheet().doRead();

        // 写法2 匿名内部类 不用额外写一个DemoDataListener
        EasyExcel.read(fileName, DemoDto.class, new ReadListener<DemoDto>() {
            /**
             * BATCH_COUNT
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 5;

            /**
             * cachedDataList
             * 临时存储
             */
            private List<DemoDto> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(DemoDto data, AnalysisContext analysisContext) {
                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                saveData();
                System.out.println("method02:所有数据解析完成!");
                log.info("method02:所有数据解析完成!");
            }

            /**
             * saveData
             * 加上存储数据库
             */
            private void saveData() {
                System.out.println("method02:" + cachedDataList.size() + "条数据,开始存储数据库!");
                System.out.println("method02:存储数据库成功!");
                log.info("method02:{}条数据,开始存储数据库!", cachedDataList.size());
                log.info("method02:存储数据库成功!");
            }
        }).sheet().doRead();

        // 有个重点 DemoDataListener 不能被Spring 管理 每次读取操作都要new 然后里面要用到spring可以构造方法传入
        // 写法3
        EasyExcel.read(fileName, DemoDto.class, new DemoDataListener()).sheet().doRead();

        // 写法4 一个文件一个reader
        try (ExcelReader excelReader = EasyExcel
                .read(fileName, DemoDto.class, new DemoDataListener()).build()) {
            // 构建一个sheet 这里可以指明名字或no
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            // 读取一个sheet
            excelReader.read(readSheet);
        }
    }

    /**
     * repeatedRead
     * 读多个或者全部sheet 这里注意一个sheet不能读取多次 多次读取需要重新读取文件
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DemoDto}
     * <p>
     * 2.由于默认一行行的读取excel 所以需要创建excel一行行的回调监听器 参照{@link DemoDataListener}
     * <p>
     * 3.直接读即可
     */
    @Test
    public void repeatedRead() {
        String fileName = PathUtil.getPath("demo", "demo.xlsx");
        log.info(fileName);

        // 读取全部sheet
        // 这里需要注意DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次；
        // 然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(fileName, DemoDto.class, new DemoDataListener()).doReadAll();

        // 写法2
        try (ExcelReader excelReader = EasyExcel.read(fileName, DemoDto.class, new DemoDataListener()).build()) {
            // 这里为了简单  所以注册了 同样的head listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 = EasyExcel.readSheet(0).head(DemoDto.class)
                    .registerReadListener(new DemoDataListener()).build();
            ReadSheet readSheet2 = EasyExcel.readSheet(1).head(DemoDto.class)
                    .registerReadListener(new DemoDataListener()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去 不然有个问题就是03版本excel 会读取多次 浪费性能
            excelReader.read(readSheet1, readSheet2);
        }
    }

    /**
     * converterRead
     * 日期、数字或者自定义格式转换
     * 默认读的转换器{@link DefaultConverterLoader#loadDefaultReadConverter()}
     * <p>
     * 1.创建excel对应的实体对象 参照{@link ConverterDto} 里面可以使用注解{@link DateTimeFormat}、{@link NumberFormat}或自定义注解
     * <p>
     * 2.由于默认一行行的读取excel,所以需要创建excel行监听器 参照{@link ConverterDataListener}
     * <p>
     * 3.直接读即可
     */
    @Test
    public void converterRead() {
        String fileName = PathUtil.getPath("demo", "demo.xlsx");
        log.info(fileName);

        // 这里 需要指定读用哪个class去读 然后读取第一个sheet
        EasyExcel.read(fileName, ConverterDto.class, new ConverterDataListener())
                // 这里注意 也可以registerConverter来指定自定义监听器 但是这个转换变成全局了 所有java为string,excel的都会用这个转换器
                // 如果就想单个字段使用请使用@ExcelProperty 指定 converter
                // .registerConverter(new CustomStringStringConverter())
                .sheet().doRead();

    }

    /**
     * complexHeaderRead
     * 多行头
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DemoDto}
     * <p>
     * 2.由于默认一行行读取 所需要创建一行行监听器 {@link DemoHeadDataListener}
     * <p>
     * 3.设置headRowNumber参数 然后读 这里要注意headRowNumber 如果不指定 会根据
     */
    @Test
    public void complexHeaderRead() {
        String fileName = PathUtil.getPath("demo", "demo.xlsx");
        log.info(fileName);

        // 这里 需要指定读用那个 class去读 然后读取第一个sheet
        EasyExcel.read(fileName, DemoDto.class, new DemoDataListener()).sheet()
                // 这里可以设置1 因为头就是第一行 如果多行头 可以设置其他值 不传入也可以 因为默认会根据DemoData来解析，他没有指定头 也就是默认就是1
                .headRowNumber(1).doRead();
    }

    /**
     * headRead
     * 读取表头数据
     * <p>
     * 1.创建excel对应的实体对象 参照 {@link DemoDto}
     * <p>
     * 2.回调 参照{@link DemoHeadDataListener}
     * <p>
     * 3.直接读即可
     */
    @Test
    public void headRead() {
        String fileName = PathUtil.getPath("demo", "demo.xlsx");
        log.info(fileName);

        EasyExcel.read(fileName, DemoDto.class, new DemoHeadDataListener()).sheet().doRead();
    }

    /**
     * extraRead
     * 额外信息(批注、超链接、合并单元格信息读取)
     * 由于是流式读取，没法在读取到单元格数据的时候直接读取到额外信息，所以只能最后通知哪些单元格哪些额外信息
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DemoExtraDto}
     * <p>
     * 2.由于默认一部读取excel,所以需要创建excel一行行的回调监听器 参考{@link DemoExtraListener}
     * <p>
     * 3.直接读即可
     */
    @Test
    public void extraRead() {
        String fileName = PathUtil.getPath("demo", "extra.xlsx");
        log.info(fileName);

        EasyExcel.read(fileName, DemoExtraDto.class, new DemoExtraListener())
                // 需要读取批注 默认不读取
                .extraRead(CellExtraTypeEnum.COMMENT)
                // 需要读取超链接 默认不读取
                .extraRead(CellExtraTypeEnum.HYPERLINK)
                // 需要读取合并单元格信息 默认不读取
                .extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
    }

    /**
     * cellDataRead
     * 读取公式和单元格类型
     * <p>
     * 1.创建excel对应实体对象 参照{@link CellDataReadDemoDto}
     * <p>
     * 2.回调监听器 参照{@link CellDataDemoHeadDataListener}
     * <p>
     * 3.直接都即可
     */
    @Test
    public void cellDataRead() {
        String fileName = PathUtil.getPath("demo", "cellDataDemo.xlsx");
        log.info(fileName);

        EasyExcel.read(fileName, CellDataReadDemoDto.class,
                new CellDataDemoHeadDataListener()).sheet().doRead();
    }

    /**
     * exceptionRead
     * 数据转换等异常处理
     * <p>
     * 1.创建excel对应的实体对象 参照{@link ExceptionDemoDto}
     * <p>
     * 2.回调器 参照{@link DemoExceptionListener}
     * <p>
     * 3.直接读即可
     */
    @Test
    public void exceptionRead() {
        String fileName = PathUtil.getPath("demo", "demo.xlsx");
        log.info(fileName);

        EasyExcel.read(fileName, ExceptionDemoDto.class, new DemoExceptionListener()).sheet().doRead();
    }

    /**
     * synchronousRead
     * 同步的返回 不推荐使用 如果数据量大会把数据放到内存里面
     */
    @Test
    public void synchronousRead() {
        String fileName = PathUtil.getPath("demo", "demo.xlsx");
        log.info(fileName);

        // 这里 需要指定读用哪个class去读 然后读取第一个sheet 同步会自动finish
        List<DemoDto> list = EasyExcel.read(fileName).head(DemoDto.class).sheet().doReadSync();
        for (DemoDto data : list) {
            System.out.println(JSON.toJSONString(data));
        }

        // 这里 也可以不指定class 返回一个list 读取第一个sheet 同步读取会自动finish
        List<Map<Integer, String>> listMap = EasyExcel.read(fileName).sheet().doReadSync();
        for (Map<Integer, String> data : listMap) {
            System.out.println(JSON.toJSONString(data));
        }
    }

    /**
     * noModeRead
     * 不创建对象的读
     */
    @Test
    public void noModeRead() {
        String fileName = PathUtil.getPath("demo", "demo.xlsx");
        log.info(fileName);

        // 这里 只要， 然后读取 第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, new NoModelDataListener()).sheet().doRead();
    }

    /**
     * 校验表头
     */
    @Test
    public void checkExcelHead() {
        String fileName = PathUtil.getPath("demo", "demo.xlsx");
        log.info(fileName);
        // 穿的为空不校验
        String[] head = {"字符串标题", "日期标题", "数字标题"};
        CheckHeadListener<DemoDto> listener = new CheckHeadListener<>(head, s ->
                StringUtils.hasText(s.getString()) && s.getDate() != null && s.getDoubleData() != null,
                s -> {
                    log.info("{}", JSONObject.toJSONString(s));
                    return true;
                });
        EasyExcel.read(fileName, DemoDto.class, listener).sheet().doRead();
        log.info("{}", JSONObject.toJSONString(listener.buildImportResultVo()));
    }
}
