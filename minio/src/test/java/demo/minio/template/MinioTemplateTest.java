package demo.minio.template;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import demo.minio.MinioApplicationTest;
import io.minio.Result;
import io.minio.StatObjectResponse;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * MinioTemplateTest
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/7/23
 */
@Slf4j
@Component
public class MinioTemplateTest extends MinioApplicationTest {
    @Autowired
    private MinioTemplate minioTemplate;

    /**
     * 获取所有的桶
     */
    @Test
    @SneakyThrows
    public void testListBuckets() {
        List<Bucket> buckets = minioTemplate.listBuckets();
        buckets.forEach(item -> {
            log.info("{}", item.name() + ":" + item.creationDate());
        });
        Assert.assertNotNull(buckets);
    }

    /**
     * 获取指定桶名
     */
    @Test
    @SneakyThrows
    public void testGetBucket() {
        Optional<Bucket> bucket = minioTemplate.getBucket("test");
        bucket.ifPresent(item -> log.info("{}:{}", item.name(), item.creationDate()));
        Assert.assertTrue(bucket.isPresent());
    }

    /**
     * 判断桶是否存在
     */
    @Test
    @SneakyThrows
    public void testExistsBucket() {
        boolean exists = minioTemplate.existsBucket("test");
        boolean notExists = minioTemplate.existsBucket("test2");
        Assert.assertTrue(exists);
        Assert.assertFalse(notExists);
    }

    /**
     * 创建桶
     */
    @Test
    @SneakyThrows
    public void testCreateBucket() {
        minioTemplate.creatBucket("new");
        boolean exists = minioTemplate.existsBucket("new");
        Assert.assertTrue(exists);
    }

    /**
     * 删除桶
     */
    @Test
    @SneakyThrows
    public void testRemoveBucket() {
        minioTemplate.removeBucket("new");
        boolean exists = minioTemplate.existsBucket("new");
        Assert.assertFalse(exists);
    }

    /**
     * 获取桶内信息
     */
    @Test
    @SneakyThrows
    public void testListFiles() {
        Iterable<Result<Item>> results = minioTemplate.listFiles("test", true);
        for (Result<Item> item : results) {
            log.info("{}:{}", item.get().objectName(), item.get().lastModified());
        }
        Assert.assertNotNull(results);
    }

    /**
     * 获取指定文件
     */
    @Test
    @SneakyThrows
    public void testGetFile() {
        InputStream file = minioTemplate.getFile("test", "linux.png");
        Assert.assertNotNull(file);
    }

    /**
     * 获取指定文件信息
     */
    @Test
    @SneakyThrows
    public void testGetFileInfo() {
        StatObjectResponse fileInfo = minioTemplate.getFileInfo("test", "linux.png");
        log.info("{}", fileInfo.toString());
        Assert.assertNotNull(fileInfo);
    }

    /**
     * 获取文件下载链接
     */
    @Test
    @SneakyThrows
    public void testGetObjectURL() {
        String url = minioTemplate.getObjectURL("test", "linux.png", 7);
        log.info(url);
        Assert.assertNotNull(url);
    }

    /**
     * 上传文件
     */
    @Test
    @SneakyThrows
    public void testUpload01() {
        File file = new File("F:\\Project\\SpringBoot\\study-demo\\minio\\src\\test\\resources\\点赞.png");
        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            byte[] cache = new byte[10240];
            inputStream.read(cache);
            MockMultipartFile multipartFile = new MockMultipartFile("点赞2.png", "点赞2.png",
                    "image/png", cache);
            multipartFile.getContentType();
            minioTemplate.uploadFile("test", multipartFile);
        }
    }

    /**
     * 上传文件
     */
    @Test
    @SneakyThrows
    public void testUpload02() {
        File file = new File("F:\\Project\\SpringBoot\\study-demo\\minio\\src\\test\\resources\\点赞.png");
        minioTemplate.uploadFile("test", "点赞2.png",
                Files.newInputStream(file.toPath()), "image/png");
    }

    /**
     * 删除指定文件
     */
    @Test
    @SneakyThrows
    public void testRemoveFile() {
        minioTemplate.removeFile("test", "点赞2.png");
    }
}
