package demo.qiniuyun.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;

/**
 * IQiniuyunService
 * <p>
 * 七牛云上传Service
 *
 * @author Wenzhou
 * @since 2023/3/21 19:01
 */
public interface IQiniuyunService {
    /**
     * 七牛云上传文件
     *
     * @param file 文件
     * @return 七牛上传Response
     * @throws QiniuException 七牛异常
     */
    Response uploadFile(File file) throws QiniuException;

    /**
     * getFile
     * 获取文件
     *
     * @param domain   String
     * @param filename String
     * @return String
     */
    String getFile(String domain, String filename);

    /**
     * getFile
     * 获取文件
     *
     * @param domain   String
     * @param filename String
     * @param owned    boolean
     * @return String
     */
    String getFile(String domain, String filename, boolean owned);
}
