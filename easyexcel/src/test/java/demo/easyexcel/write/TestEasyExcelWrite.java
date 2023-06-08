package demo.easyexcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.CommentData;
import com.alibaba.excel.metadata.data.FormulaData;
import com.alibaba.excel.metadata.data.HyperlinkData;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.metadata.data.RichTextStringData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.BooleanUtils;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import demo.easyexcel.util.PathUtil;
import demo.easyexcel.write.dto.ComplexHeadDto;
import demo.easyexcel.write.dto.ConverterDto;
import demo.easyexcel.write.dto.DemoDto;
import demo.easyexcel.write.dto.DemoMergeDto;
import demo.easyexcel.write.dto.DemoStyleDto;
import demo.easyexcel.write.dto.ImageDemoDto;
import demo.easyexcel.write.dto.IndexDto;
import demo.easyexcel.write.dto.LongestMatchColumnWidthDto;
import demo.easyexcel.write.dto.WidthAndHeightDto;
import demo.easyexcel.write.dto.WriteCellDemoDto;
import demo.easyexcel.write.handler.CommentWriteHandler;
import demo.easyexcel.write.handler.CustomCellWriteHandler;
import demo.easyexcel.write.handler.CustomSheetWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * TestEasyExcelWrite
 *
 * @author Wenzhou
 * @since 2023/3/17 17:45
 */
@Slf4j
public class TestEasyExcelWrite {
    /**
     * simpleWrite
     * 最简单的写
     * <p>
     * 1.创建excel对应的实体对象，参照{@link DemoDto}
     * <p>
     * 2.直接写即可
     */
    @Test
    public void simpleWrite() {
        // 注意 simpleWrite在数据量不大的情况下可以使用（5000以内，具体也要看实际情况），数据量大参照 重复多次写入

        // 写法1 JDK8+
        String fileName = PathUtil.getOutPath("simpleWrite");
        log.info(fileName);

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoDto.class)
                .sheet("模板")
                .doWrite(this::data);

        // 写法2
        fileName = PathUtil.getOutPath("simpleWrite");
        log.info(fileName);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoDto.class).sheet("模板").doWrite(data());

        // 写法3
        fileName = PathUtil.getOutPath("simpleWrite", ".xlsx");
        log.info(fileName);
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoDto.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(data(), writeSheet);
        }
    }

    /**
     * excludeOrIncludeWrite
     * 根据参数只导出指定列
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DemoDto}
     * <p>
     * 2.根据自己或者排除自己需要的列
     * <p>
     * 3.直接写即可
     */
    @Test
    public void excludeOrIncludeWrite() {
        String fileName = PathUtil.getOutPath("excludeOrIncludeWrite");
        log.info(fileName);
        // 这里需要注意 在使用ExcelProperty注解的使用，如果想不空列则需要加入order字段
        // 而不是index,order会忽略空列，然后继续往后，而index，不会忽略空列，在第几列就是第几列

        // 根据用户传入字段 假设要忽略 date
        HashSet<String> excludeColumnFieldNames = new HashSet<>();
        excludeColumnFieldNames.add("date");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet,名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoDto.class)
                .excludeColumnFieldNames(excludeColumnFieldNames)
                .sheet("模板")
                .doWrite(data());

        fileName = PathUtil.getOutPath("excludeOrIncludeWrite", ".xlsx");
        log.info(fileName);
        // 根据用户传入字段 假设只要导出 data
        Set<String> includeColumnFieldNames = new HashSet<>();
        includeColumnFieldNames.add("date");
        EasyExcel.write(fileName, DemoDto.class)
                .includeColumnFieldNames(includeColumnFieldNames)
                .sheet("模板")
                .doWrite(data());
    }

    /**
     * indexWrite
     * 指定写入的列
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DemoDto}
     * <p>
     * 2.使用{@link ExcelProperty} 注解指定写入的列
     * <p>
     * 3.直接写即可
     */
    @Test
    public void indexWrite() {
        String fileName = PathUtil.getOutPath("indexWrite");
        log.info(fileName);

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet,名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, IndexDto.class).sheet("模板").doWrite(this::indexDto);
    }

    /**
     * complexHeadWrite
     * 复杂头写入
     * <p>
     * 1.创建excel对应的实体对象 参照{@link ComplexHeadDto}
     * <p>
     * 2.使用{@link ExcelProperty} 注解指定复杂的头
     * <p>
     * 3.直接写即可
     */
    @Test
    public void complexHeadWrite() {
        String fileName = PathUtil.getOutPath("complexHeadWrite");
        log.info(fileName);

        EasyExcel.write(fileName, ComplexHeadDto.class).sheet("模板").doWrite(this::complexHeadDto);
    }

    /**
     * repeatedWrite
     * 重复多次写入
     * <p>
     * 1.创建excel对应的实体对象 参照{@link ComplexHeadDto}
     * <p>
     * 2.使用{@link ExcelProperty}注解指定复杂的头
     * <p>
     * 3.直接调用二次写入即可
     */
    @Test
    public void repeatedWrite() {
        // 方法1 如果写到同一个sheet
        String fileName = PathUtil.getOutPath("repeatedWrite");
        log.info(fileName);
        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoDto.class).build()) {
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            for (int i = 0; i < 5; i++) {
                List<DemoDto> data = data();
                excelWriter.write(data, writeSheet);
            }
        }

        // 方法2 如果写到不同的sheet 同一个对象
        fileName = PathUtil.getOutPath("repeatedWrite");
        log.info(fileName);
        // 这里 指定文件
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoDto.class).build()) {
            // 去调用写入，这里调用了五次，实际使用时根据数据库分页总数来，这里最终会写到5张sheet表中
            for (int i = 0; i < 5; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
                List<DemoDto> data = data();
                excelWriter.write(data, writeSheet);
            }
        }

        // 方法3 如果写到不同的sheet 不同的对象
        fileName = PathUtil.getOutPath("repeatedWrite");
        log.info(fileName);
        // 这里 指定文件
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).build()) {
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                // 这里注意DemoDto.class每次都可变 这里为方便 所以用一个class 实际上可以一直变
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).head(DemoDto.class).build();
                List<DemoDto> data = data();
                excelWriter.write(data, writeSheet);
            }
        }
    }

    /**
     * converterWrite
     * 日期、数字或者自定义格式转换
     * <p>
     * 1.创建excel对应的实体对象 参照{@link ConverterDto}
     * <p>
     * 2.使用{@link ExcelProperty} 配合使用注解{@link com.alibaba.excel.annotation.format.DateTimeFormat}、{@link com.alibaba.excel.annotation.format.NumberFormat}
     * <p>
     * 3.直接写即可
     */
    @Test
    public void converterWrite() {
        String fileName = PathUtil.getOutPath("converterWrite");
        log.info(fileName);

        // 这里 需要指定写用哪个Class去写 然后写到第一个sheet 名字为模板， 然后文件流会自动关闭
        EasyExcel.write(fileName, ConverterDto.class).sheet("模板").doWrite(this::converterDto);
    }

    /**
     * imageWrite
     * 图片导入
     * <p>
     * 1.创建excel对应的实体对象 参照{@link ImageDemoDto}
     * <p>
     * 2.直接写即可
     */
    @Test
    public void imageWrite() throws Exception {
        String fileName = PathUtil.getOutPath("imageWrite");
        log.info(fileName);

        String imagePath = PathUtil.getPath("converter", "img.jpg");
        try (InputStream inputStream = FileUtils.openInputStream(new File(imagePath))) {
            List<ImageDemoDto> list = ListUtils.newArrayList();
            ImageDemoDto imageDemoDto = new ImageDemoDto();
            list.add(imageDemoDto);
            // 放入五种类型的图片 实际使用只要选一种即可
            imageDemoDto.setByteArray(FileUtils.readFileToByteArray(new File(imagePath)));
            imageDemoDto.setFile(new File(imagePath));
            imageDemoDto.setString(imagePath);
            imageDemoDto.setInputStream(inputStream);
            imageDemoDto.setUrl(new URL("https://img0.baidu.com/it/u=1590170963,1165066544&fm=253&fmt=auto&app=138&f=PNG?w=500&h=423"));
            // 这里演示
            // 需要额外放入文字
            // 而且需要放入2个图片
            // 第一个图片靠左
            // 第二个靠右 而且要额外的占用他后面的单元格
            WriteCellData<Void> writeCellDto = new WriteCellData<>();
            imageDemoDto.setWriteCellData(writeCellDto);
            // 这里可以设置为Empty 则代表不需要其他数据了
            writeCellDto.setType(CellDataTypeEnum.STRING);
            writeCellDto.setStringValue("额外的放一些文字");

            // 可以放入多个图片
            List<ImageData> imageDtoList = new ArrayList<>();
            ImageData imageData = new ImageData();
            imageDtoList.add(imageData);
            //writeCellDto.setImageDtoList(imageDtoList);
            // 放入2进制图片
            imageData.setImage(FileUtils.readFileToByteArray(new File(imagePath)));
            // 图片类型
            imageData.setImageType(ImageData.ImageType.PICTURE_TYPE_JPEG);
            // 上 右 下 左需要留空
            // 这个类似于 css 的 margin
            // 这里实测 不能设置太大 超过单元格原始大小后 打开会提示修复 暂时为找到好的解决办法
            imageData.setTop(5);
            imageData.setRight(40);
            imageData.setBottom(5);
            imageData.setLeft(5);

            // 放入第二个图片
            imageData = new ImageData();
            imageDtoList.add(imageData);
            writeCellDto.setImageDataList(imageDtoList);
            imageData.setImage(FileUtils.readFileToByteArray(new File(imagePath)));
            imageData.setImageType(ImageData.ImageType.PICTURE_TYPE_JPEG);
            imageData.setTop(5);
            imageData.setRight(5);
            imageData.setBottom(5);
            imageData.setLeft(50);
            // 设置图片的位置 假设 现在目标 是 覆盖 当前单元格 和当前单元格右边的单元格
            // 起点相对于当前单元格为0 可以不写
            imageData.setRelativeFirstRowIndex(0);
            imageData.setRelativeFirstColumnIndex(0);
            imageData.setRelativeLastRowIndex(0);
            // 前面3个可以不写 下面这个需要写 也就是 结尾 需要相对当前单元格 往右移动一格
            imageData.setRelativeLastColumnIndex(1);

            // 写入数据
            EasyExcel.write(fileName, ImageDemoDto.class).sheet("模板").doWrite(list);
        }
    }

    /**
     * writeCellDtoWrite
     * 超链接、备注、公式、指定单个单元格的样式、单个单元格多种样式
     * <p>
     * 1.创建excel对应的实体对象 参照{@link WriteCellDemoDto}
     * <p>
     * 2.直接写即可
     */
    @Test
    public void writeCellDtoWrite() {
        String fileName = PathUtil.getOutPath("writeCellDtoWrite");
        log.info(fileName);

        WriteCellDemoDto writeCellDemoDto = new WriteCellDemoDto();
        // 设置超链接
        WriteCellData<String> hyperlink = new WriteCellData<>("百度一下");
        writeCellDemoDto.setHyperlink(hyperlink);
        HyperlinkData hyperlinkDto = new HyperlinkData();
        hyperlink.setHyperlinkData(hyperlinkDto);
        hyperlinkDto.setAddress("https://www.baidu.com");
        hyperlinkDto.setHyperlinkType(HyperlinkData.HyperlinkType.URL);

        // 设置备注
        WriteCellData<String> comment = new WriteCellData<>("备注的单元格信息");
        writeCellDemoDto.setCommentData(comment);
        CommentData commentDto = new CommentData();
        comment.setCommentData(commentDto);
        commentDto.setAuthor("Wenzhou");
        commentDto.setRichTextStringData(new RichTextStringData("这是一个备注"));
        // 备注的默认大小是按照单元格的大小  这里想调整到4个单元格那么大 所以向后 向下 名额外占用了一个单元格
        commentDto.setRelativeLastRowIndex(1);
        commentDto.setRelativeLastRowIndex(1);

        // 设置公式
        WriteCellData<String> formula = new WriteCellData<>();
        writeCellDemoDto.setFormulaData(formula);
        FormulaData formulaDto = new FormulaData();
        formula.setFormulaData(formulaDto);
        // 将 123456789 中的第一个数字替换成 2
        // 这里只是例子 如果真的涉及到公式 能内存算好尽量内存算好 公式能不用尽量不用
        formulaDto.setFormulaValue("REPLACE(123456789,1,1,2)");

        // 设置单个单元格的样式 当然样式 很多的话 也可以用注解等方式
        WriteCellData<String> writeCellStyle = new WriteCellData<>("单元格样式");
        writeCellStyle.setType(CellDataTypeEnum.STRING);
        writeCellDemoDto.setWriteCellStyle(writeCellStyle);
        WriteCellStyle writeCellStyleDto = new WriteCellStyle();
        writeCellStyle.setWriteCellStyle(writeCellStyleDto);
        // 这里需要指定 FillPatternType 为 FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色
        writeCellStyleDto.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景颜色
        writeCellStyleDto.setFillForegroundColor(IndexedColors.GREEN.getIndex());

        // 设置单个单元格多种样式
        WriteCellData<String> richText = new WriteCellData<>();
        richText.setType(CellDataTypeEnum.RICH_TEXT_STRING);
        writeCellDemoDto.setRichText(richText);
        RichTextStringData richTextStringDto = new RichTextStringData();
        richText.setRichTextStringDataValue(richTextStringDto);
        richTextStringDto.setTextString("红色绿色默认");
        // 前2个字红色
        WriteFont writeFont = new WriteFont();
        writeFont.setColor(IndexedColors.RED.getIndex());
        richTextStringDto.applyFont(0, 2, writeFont);
        // 接下来2个字绿色
        writeFont = new WriteFont();
        writeFont.setColor(IndexedColors.GREEN.getIndex());
        richTextStringDto.applyFont(2, 4, writeFont);

        List<WriteCellDemoDto> data = new ArrayList<>();
        data.add(writeCellDemoDto);
        //inMemory(true)不加 文字颜色没效果
        EasyExcel.write(fileName, WriteCellDemoDto.class).inMemory(true).sheet("模板").doWrite(data);
    }

    /**
     * templateWrite
     * 根据模板写入
     * <p>
     * 1.创建excel对应的实体对象 参照{@link IndexDto}
     * <p>
     * 2.使用{@link ExcelProperty} 注解指定写入的列
     * <p>
     * 3.使用withTemplate写取模板
     */
    @Test
    public void templateWrite() {
        String templateFileName = PathUtil.getPath("template", "template07.xlsx");
        log.info(templateFileName);

        String fileName = PathUtil.getOutPath("templateWrite");
        log.info(fileName);

        // 这里 需要指定写用哪个class去写 然后写到第一个sheet 名字为模板 ……
        EasyExcel.write(fileName, IndexDto.class)
                .withTemplate(templateFileName)
                .sheet("模板")
                .doWrite(indexDto());
    }

    /**
     * widthAndHeightWrite
     * 列宽、行高
     * <p>
     * 1.创建excel对应的实体对象 参照{@link WidthAndHeightDto}
     * <p>
     * 2.使用注解{@link ColumnWidth}、{@link HeadRowHeight}、{@link ContentRowHeight} 指定宽度或高度
     * <p>
     * 3.直接写即可
     */
    @Test
    public void widthAndHeightWrite() {
        String fileName = PathUtil.getOutPath("widthAndHeightWrite");
        log.info(fileName);

        // 这里 需要指定…… ……
        EasyExcel.write(fileName, WidthAndHeightDto.class)
                .sheet("模板").doWrite(widthAndHeightDto());
    }

    /**
     * annotationStyleWrite
     * 注解形式自定义样式
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DemoStyleDto}
     * <p>
     * 2.直接写即可
     */
    @Test
    public void annotationStyleWrite() {
        String fileName = PathUtil.getOutPath("annotationStyleWrite");
        log.info(fileName);

        EasyExcel.write(fileName, DemoStyleDto.class).sheet("模板").doWrite(annotationStyleDto());
    }

    /**
     * handlerStyleWrite
     * 拦截器形式自定义样式
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DemoDto}
     * <p>
     * 2.创建一个style策略 并注册
     * <p>
     * 3.直接写即可
     */
    @Test
    public void handlerStyleWrite() {
        // 方法1 使用已有的策略 推荐
        // HorizontalCellStyleStrategy 每一行的样式都一样 或者隔行一样
        // AbstractVerticalCellStyleStrategy 每一列的样式都一样 需要自己回调每一页
        String fileName = PathUtil.getOutPath("handlerStyleWrite");
        log.info(fileName);

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 20);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定FillPatternType 为 FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色
        // 头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景颜色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 20);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式  其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        // 这里 需要指定…… …… 并注册策略
        EasyExcel.write(fileName, DemoDto.class)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("模板")
                .doWrite(data());

        // 方法2 使用easyexcel的方式完全自己写 不太推荐 尽量使用已有策略(知道就行)
        fileName = PathUtil.getOutPath("handlerStyleWrite");
        log.info(fileName);

        EasyExcel.write(fileName, DemoDto.class).registerWriteHandler(new CellWriteHandler() {
                    @Override
                    public void afterCellDispose(CellWriteHandlerContext context) {
                        // 当前事件会在  数据设置到poi的cell里面才会回调
                        // 判断不是头的情况 如果是fill 的情况 这里会==null 所以用not true
                        if (BooleanUtils.isNotTrue(context.getHead())) {
                            // 第一个单元格
                            // 只要不是头 一定会有数据 当然fill的情况 可能要context.getCellDtoList()
                            // 这个需要看模板，因为一个单元格会有多个 WriteCellDto
                            WriteCellData<?> cellDto = context.getFirstCellData();
                            // 这里需要取cellDto 获取样式
                            // 很重要的一个原因是 WriteCellStyle 和 dataFormatDto绑定的 简单的说 比如你加了 DateTimeFormat
                            // ，已经将writeCellStyle里面的dataFormatDto 改了
                            // 如果你自己new了一个WriteCellStyle，可能注解的样式就失效了 然后 getOrCreateStyle 用于返回一个样式
                            // 如果为空，则创建一个返回
                            WriteCellStyle writeCellStyle = cellDto.getOrCreateStyle();
                            writeCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                            // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND
                            writeCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                            // 这样样式就设置好了 后面有个FillStyleCellWriteHandler
                            // 默认会将 WriteCellStyle 设置到 cell 里面去 所以可以不用管
                        }
                    }
                }).sheet("模板")
                .doWrite(data());

        // 方法3 使用poi的样式完全自己写 不推荐 (知道就行)
        // 坑1：style里面有dataformat 用来格式化数据的 所以自己设置可能导致格式化注解不生效
        // 坑2：不要一直去创建style 记得缓存起来 最多创建6W个就挂了
        fileName = PathUtil.getOutPath("handlerStyleWrite");
        log.info(fileName);

        EasyExcel.write(fileName, DemoDto.class).registerWriteHandler(new CellWriteHandler() {
                    @Override
                    public void afterCellDispose(CellWriteHandlerContext context) {
                        // 当前事件会在 数据设置到poi的cell里面才会回调
                        // 判断不是头的情况 如果是fill 的情况 这里会==null 所以用not true
                        if (BooleanUtils.isNotTrue(context.getHead())) {
                            Cell cell = context.getCell();
                            // 拿到poi的workbook
                            Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
                            // 这里千万记住 想办法能复用的地方把他缓存起来 一个表格最多创建6W个样式
                            // 不同单元格尽量传同一个 cellStyle
                            CellStyle cellStyle = workbook.createCellStyle();
                            cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                            // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND
                            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            cell.setCellStyle(cellStyle);

                            // 由于这里没有指定dataformat 最后展示的数据 格式可能会不太正确

                            // 这里要把 WriteCellDto的样式清空， 不然后面还有一个拦截器 FillStyleCellWriteHandler
                            // 默认会将 WriteCellStyle 设置到 cell里面去 会导致自己设置的不一样
                            context.getFirstCellData().setWriteCellStyle(null);
                        }
                    }
                }).sheet("模板")
                .doWrite(data());
    }

    /**
     * mergeWrite
     * 合并单元格
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DemoDto} {@link DemoMergeDto}
     * <p>
     * 2.创建一个merge策略 并注册
     * <p>
     * 3.直接写即可
     */
    @Test
    public void mergeWrite() {
        // 方法1 注解
        String fileName = PathUtil.getOutPath("mergeWrite");
        log.info(fileName);

        // 在DemoStyleDto里面加上ContentLoopMerge注解
        // 这里需要指定写用哪个class去写，然后到第一个sheet ,……
        EasyExcel.write(fileName, DemoMergeDto.class).sheet("模板").doWrite(mergeDto());

        // 方法2 自定义合并单元格策略
        fileName = PathUtil.getOutPath("mergeWrite");
        log.info(fileName);

        // 每隔2行会合并 把eachColumn 设置成 0 也就是数据的长度 所以就第一列会合并，当然其他合并策略也可以自己写
        LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // 这里 需要指定写用哪个class ……
        EasyExcel.write(fileName, DemoDto.class)
                .registerWriteHandler(loopMergeStrategy)
                .sheet("模板")
                .doWrite(data());
    }

    /**
     * tableWrite
     * 使用table去写入
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DemoDto}
     * <p>
     * 2.然后写入table即可
     */
    @Test
    public void tableWrite() {
        String fileName = PathUtil.getOutPath("tableWrite");
        log.info(fileName);

        // 方法1 这里直接写多个table的案例， 如果只有一个 也可以只一行代码搞定  参照其他案例
        // 这里需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, DemoDto.class).build()) {
            // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").needHead(Boolean.FALSE).build();
            // 这里必须指定需要头， table会继承 sheet的配置 sheet配置了不需要 ，table默认也是不需要
            WriteTable writeTable0 = EasyExcel.writerTable(0).needHead(Boolean.TRUE).build();
            WriteTable writeTable1 = EasyExcel.writerTable(1).needHead(Boolean.TRUE).build();
            // 第一次写入会创建头
            excelWriter.write(data(), writeSheet, writeTable0);
            // 第二次写入也会创建头，然后第一次的后面写入数据
            excelWriter.write(data(), writeSheet, writeTable1);
        }
    }

    /**
     * dynamicHeadWrite
     * 动态头 实时生成头写入
     * 思路是这样子的， 先创建List<String>头格式的sheet仅仅写入头，然后通过table 不写入头的方式 去写入数据
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DemoDto}
     * <p>
     * 2.然后写入table即可
     */
    @Test
    public void dynamicHeadWrite() {
        String fileName = PathUtil.getOutPath("dynamicHeadWrite");
        log.info(fileName);

        EasyExcel.write(fileName)
                // 这里放入动态头
                .head(head()).sheet("模板")
                //当然这里数据也可以用List<List<String>去传入
                .doWrite(data());
    }

    /**
     * longestMatchColumnWidthWrite
     * 自动列宽(不太精确)
     * 这个目前不是很好用，比如有数字就会导致换行。而且长度也不是刚好和实际长度一致。 所以需要精确到刚好列宽的慎用。
     * 当然也可以自己参照 {@link LongestMatchColumnWidthStyleStrategy} 重新实现
     * poi 自带{@link SXSSFSheet#autoSizeColumn(int)} 对中文支持也不太好
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link LongestMatchColumnWidthDto}
     * <p>
     * 2. 注册策略{@link LongestMatchColumnWidthStyleStrategy}
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void longestMatchColumnWidthWrite() {
        String fileName = PathUtil.getOutPath("longestMatchColumnWidthWrite");
        log.info(fileName);

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, LongestMatchColumnWidthDto.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .sheet("模板").doWrite(dataLong());
    }

    /**
     * customHandlerWrite
     * 下拉，超链接等自定义拦截器（上面几点都不符合但是要对单元格进行操作的参照这个）
     * demo这里实现2点
     * - 对第一行第一列的头超链接到: <a href="https://www.baidu.com">...</a>
     * - 对第一列第一行和第二行的数据新增下拉框，显示 测试1 测试2
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoDto}
     * <p>
     * 2. 注册拦截器 {@link CustomCellWriteHandler} {@link CustomSheetWriteHandler}
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void customHandlerWrite() {
        String fileName = PathUtil.getOutPath("customHandlerWrite");
        log.info(fileName);

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoDto.class)
                .registerWriteHandler(new CustomSheetWriteHandler())
                .registerWriteHandler(new CustomCellWriteHandler())
                .sheet("模板").doWrite(data());
    }

    /**
     * commentWrite
     * 插入批注
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoDto}
     * <p>
     * 2. 注册拦截器 {@link CommentWriteHandler}
     * <p>
     * 3. 直接写即可
     */
    @Test
    public void commentWrite() {
        String fileName = PathUtil.getOutPath("commentWrite");
        log.info(fileName);

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 这里要注意inMemory 要设置为true，才能支持批注。目前没有好的办法解决 不在内存处理批注。这个需要自己选择。
        EasyExcel.write(fileName, DemoDto.class).inMemory(Boolean.TRUE)
                .registerWriteHandler(new CommentWriteHandler())
                .sheet("模板").doWrite(data());
    }

    /**
     * variableTitleWrite
     * 可变标题处理(包括标题国际化等)
     * 简单的说用List<List<String>>的标题 但是还支持注解
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ConverterDto}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void variableTitleWrite() {
        // 写法1
        String fileName = PathUtil.getOutPath("variableTi1tleWrite");
        log.info(fileName);

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ConverterDto.class).head(variableTitleHead()).sheet("模板").doWrite(data());
    }

    /**
     * noModelWrite
     * 不创建对象的写
     */
    @Test
    public void noModelWrite() {
        // 写法1
        String fileName = PathUtil.getOutPath("noModelWrite");
        log.info(fileName);

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).head(head()).sheet("模板").doWrite(dataList());
    }

    /**
     * 不创建对象数据
     *
     * @return List
     */
    private List<List<Object>> dataList() {
        List<List<Object>> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            List<Object> data = ListUtils.newArrayList();
            data.add("字符串" + i);
            data.add(new Date());
            data.add(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 可变标题处理数据
     *
     * @return List
     */
    private List<List<String>> variableTitleHead() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("string" + System.currentTimeMillis());
        List<String> head1 = ListUtils.newArrayList();
        head1.add("number" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        head2.add("date" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    /**
     * 自动列宽 数据
     *
     * @return List
     */
    private List<LongestMatchColumnWidthDto> dataLong() {
        List<LongestMatchColumnWidthDto> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            LongestMatchColumnWidthDto data = new LongestMatchColumnWidthDto();
            data.setString("测试很长的字符串测试很长的字符串测试很长的字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(1000000000000.0);
            list.add(data);
        }
        return list;
    }

    /**
     * 动态头数据
     *
     * @return List
     */
    private List<List<String>> head() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("字符串" + System.currentTimeMillis());
        head0.add("字符串2" + System.currentTimeMillis());
        List<String> head1 = ListUtils.newArrayList();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    /**
     * 合并数据
     *
     * @return List
     */
    private List<DemoMergeDto> mergeDto() {
        List<DemoMergeDto> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoMergeDto data = new DemoMergeDto();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 注解样式数据
     *
     * @return List
     */
    private List<DemoStyleDto> annotationStyleDto() {
        List<DemoStyleDto> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoStyleDto data = new DemoStyleDto();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 宽高数据
     *
     * @return List
     */
    private List<WidthAndHeightDto> widthAndHeightDto() {
        List<WidthAndHeightDto> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            WidthAndHeightDto data = new WidthAndHeightDto();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 格式化数据
     *
     * @return List
     */
    private List<ConverterDto> converterDto() {
        List<ConverterDto> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            ConverterDto data = new ConverterDto();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 复杂头数据
     *
     * @return List<ComplexHeadDto>
     */
    private List<ComplexHeadDto> complexHeadDto() {
        List<ComplexHeadDto> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            ComplexHeadDto data = new ComplexHeadDto();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 指定列数据
     *
     * @return List<IndexDto>
     */
    private List<IndexDto> indexDto() {
        List<IndexDto> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            IndexDto data = new IndexDto();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 简单数据
     *
     * @return List<DemoDto>
     */
    private List<DemoDto> data() {
        List<DemoDto> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoDto data = new DemoDto();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
