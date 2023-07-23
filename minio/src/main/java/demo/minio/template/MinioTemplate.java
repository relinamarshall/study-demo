package demo.minio.template;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * MinioConfig
 *
 * @author <a href="https://github.com/relinamarshall">Wenzhou</a>
 * @since 2023/7/23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MinioTemplate {
    /**
     * MinIO 客户端
     */
    private final MinioClient minioClient;

    /**
     * 查询所有存储桶
     *
     * @return Bucket 集合
     */
    public List<Bucket> listBuckets() throws Exception {
        return minioClient.listBuckets();
    }

    /**
     * 根据名称获取指定bucket
     *
     * @param bucketName bucket名称
     * @return Optional<Bucket>
     */
    public Optional<Bucket> getBucket(String bucketName) throws Exception {
        return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 桶是否存在
     *
     * @param bucketName 桶名
     * @return 是否存在
     */
    public boolean existsBucket(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 桶名
     */
    public void creatBucket(String bucketName) throws Exception {
        if (!existsBucket(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 删除一个空桶 如果存储桶存在对象不为空时，删除会报错。
     *
     * @param bucketName 桶名
     */
    public void removeBucket(String bucketName) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取桶的所有文件信息
     *
     * @param bucketName 桶名
     * @param recursive  是否递归查询
     * @return Iterable<Result < Item>>
     */
    public Iterable<Result<Item>> listFiles(String bucketName, boolean recursive) {
        return minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).recursive(recursive).build());
    }

    /**
     * 获取⽂件
     *
     * @param bucketName bucket名称
     * @param fileName   ⽂件名称
     * @return InputStream
     */
    public InputStream getFile(String bucketName, String fileName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }

    /**
     * 获取⽂件信息
     *
     * @param bucketName bucket名称
     * @param fileName   ⽂件名称
     */
    public StatObjectResponse getFileInfo(String bucketName, String fileName) throws Exception {
        return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }

    /**
     * 上传文件
     *
     * @return String 文件名称
     */
    public String uploadFile(String bucketName, MultipartFile file) throws Exception {
        //判断文件是否为空
        if (null == file || 0 == file.getSize()) {
            return null;
        }
        //判断存储桶是否存在  不存在则创建
        creatBucket(bucketName);
        //文件名
        String fileName = StringUtils.isBlank(file.getOriginalFilename()) ? file.getName() : file.getOriginalFilename();
        //新的文件名 = 存储桶文件名_时间戳.后缀名
        assert fileName != null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        fileName = bucketName + "_" + format.format(new Date()) + "_" + System.currentTimeMillis()
                + fileName.substring(fileName.lastIndexOf("."));
        //开始上传
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType()).build());
        return fileName;
    }

    /**
     * 上传⽂件
     *
     * @param bucketName bucket名称
     * @param fileName   ⽂件名称
     * @param stream     ⽂件流
     */
    public void uploadFile(String bucketName, String fileName, InputStream stream,String contentType) throws Exception {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName)
                .stream(stream, stream.available(), -1)
                .contentType(contentType).build());
    }

    /**
     * 获取⽂件外链
     *
     * @param bucketName bucket名称
     * @param fileName   ⽂件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    public String getObjectURL(String bucketName, String fileName, Integer expires) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET)
                .bucket(bucketName).object(fileName).expiry(expires).build());
    }

    /**
     * 删除⽂件
     *
     * @param bucketName bucket名称
     * @param fileName   ⽂件名称
     */
    public void removeFile(String bucketName, String fileName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }
}