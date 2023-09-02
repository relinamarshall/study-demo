package demo.mock.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * EasyExcelUtil
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/8/28
 */
@Slf4j
@UtilityClass
public class EasyExcelUtil {
    /**
     * analysisFileList
     *
     * @param file       MultipartFile
     * @param sheetIndex int
     * @param clazz      Class
     * @return List
     */
    public static List analysisFileList(MultipartFile file, int sheetIndex, Class clazz) {
        try (FileInputStream inputStream = (FileInputStream) file.getInputStream()) {
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(sheetIndex);
            params.setNeedVerify(true);
            return ExcelImportUtil.importExcel(inputStream, clazz, params);
        } catch (Exception e) {
            log.error("数据解析异常", e);
            throw new RuntimeException(e);
        }
    }
}
