package demo.qiniuyun.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URLEncoder;

import demo.qiniuyun.service.IQiniuyunService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Wenzhou
 * @since 2023/3/21 19:02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QiniuyunServiceImpl implements IQiniuyunService, InitializingBean {
    /**
     * uploadManager
     */
    private final UploadManager uploadManager;
    /**
     * bucketManager
     */
    private final BucketManager bucketManager;
    /**
     * auth
     */
    private final Auth auth;
    /**
     * bucket
     */
    @Value("${qiniu.bucket}")
    private String bucket;
    /**
     * bucket
     */
    @Value("${qiniu.dict}")
    private String dict;
    /**
     * putPolicy
     */
    private StringMap putPolicy;

    /**
     * uploadFile
     * 七牛云上传文件
     *
     * @param file 文件
     * @return 七牛上传Response
     * @throws QiniuException 七牛异常
     */
    @Override
    public Response uploadFile(File file) throws QiniuException {
        Response response = uploadManager.put(file, dict + "/" + file.getName(), getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = uploadManager.put(file, dict + "/" + file.getName(), getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public String getFile(String domain, String filename) {
        return getFile(domain, filename, false);
    }

    @Override
    @SneakyThrows
    public String getFile(String domain, String filename, boolean owned) {
        String encodedFileName = URLEncoder.encode(filename, "utf-8").replace("+", "%20");
        String url;
        if (owned) {
            String temp = String.format("%s/%s/%s", domain, dict, encodedFileName);
            long expireInSeconds = 3600;
            url = auth.privateDownloadUrl(temp, expireInSeconds);
        } else {
            String temp = String.format("%s/%s/%s", domain, dict, encodedFileName);
            url = auth.privateDownloadUrl(temp);
        }
        return url;
    }

    /**
     * afterPropertiesSet
     */
    @Override
    public void afterPropertiesSet() {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }

    /**
     * getUploadToken
     * 获取上传凭证
     *
     * @return 上传凭证
     */
    private String getUploadToken() {
        return auth.uploadToken(bucket, null, 3600, putPolicy);
    }

}
