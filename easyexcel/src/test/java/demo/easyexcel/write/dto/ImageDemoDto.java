package demo.easyexcel.write.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.data.WriteCellData;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * ImageDemoDto
 *
 * @author Wenzhou
 * @since 2023/3/17 16:02
 */
@Setter
@Getter
@EqualsAndHashCode
public class ImageDemoDto {
    /**
     * file
     */
    private File file;
    /**
     * inputStream
     */
    private InputStream inputStream;
    /**
     * 如果String类型 必须指定转换器 ，String默认转换成String
     */
    @ExcelProperty(converter = StringImageConverter.class)
    private String string;
    /**
     * byteArray
     */
    private byte[] byteArray;
    /**
     * 根据url导入
     */
    private URL url;
    /**
     * 根据文件导出 并设置导出的位置
     */
    private WriteCellData<Void> writeCellData;
}
