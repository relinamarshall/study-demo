package demo.minio.controller;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import demo.minio.template.MinioTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MinioController
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/7/23
 */
@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class MinioController {
    private final MinioTemplate minioTemplate;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public String uploadFile(@RequestParam(name = "bucket", required = false, defaultValue = "test") String bucket,
                             @RequestParam(name = "file") MultipartFile file) {
        String result = null;
        try {
            result = minioTemplate.uploadFile(bucket, file);
        } catch (Exception e) {
            log.error("上传失败 : [{}]", Arrays.asList(e.getStackTrace()), e);
        }
        return result;
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/delete/{bucket}")
    public void deleteFile(@PathVariable("bucket") String bucket, @RequestParam(value = "fileName") String fileName) {
        try {
            minioTemplate.removeFile(bucket, fileName);
        } catch (Exception e) {
            log.error("删除失败 : [{}]", Arrays.asList(e.getStackTrace()), e);
        }
    }

    /**
     * 下载文件到本地
     */
    @GetMapping("/download/{bucket}/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("bucket") String bucket,
                                               @PathVariable("fileName") String fileName) {
        ResponseEntity<byte[]> responseEntity = null;
        try (InputStream stream = minioTemplate.getFile(bucket, fileName);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            //用于转换byte
            int n;
            byte[] buffer = new byte[4096];
            while (-1 != (n = stream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();

            //设置header
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Accept-Ranges", "bytes");
            httpHeaders.add("Content-Length", bytes.length + "");
            httpHeaders.add("Content-disposition", "attachment; filename=" + fileName);
            httpHeaders.add("Content-Type", "text/plain;charset=utf-8");
            responseEntity = new ResponseEntity<>(bytes, httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("下载失败 : [{}]", Arrays.asList(e.getStackTrace()), e);
        }
        return responseEntity;
    }

    /**
     * 在浏览器预览图片
     */
    @GetMapping("/view/{bucket}/{fileName}")
    public void viewImgFile(@PathVariable("bucket") String bucket,
                            @PathVariable("fileName") String objectName,
                            HttpServletResponse response) {
        response.setContentType("image/jpeg");
        try (ServletOutputStream out = response.getOutputStream();
             InputStream stream = minioTemplate.getFile(bucket, objectName);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            int n;
            byte[] buffer = new byte[4096];
            while (-1 != (n = stream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();
            out.write(bytes);
            out.flush();
        } catch (Exception e) {
            log.error("预览失败 : [{}]", Arrays.asList(e.getStackTrace()), e);
        }
    }
}
