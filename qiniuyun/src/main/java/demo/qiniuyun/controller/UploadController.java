package demo.qiniuyun.controller;

import com.qiniu.http.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import demo.qiniuyun.service.IQiniuyunService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UploadController
 *
 * @author Wenzhou
 * @since 2023/3/21 18:59
 */
@Slf4j
@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {
    /**
     * fileTempPath
     */
    @Value("${spring.servlet.multipart.location}")
    private String fileTempPath;

    /**
     * prefix
     */
    @Value("${qiniu.prefix}")
    private String prefix;

    /**
     * qiniuyunService
     */
    private final IQiniuyunService qiniuyunService;

    /**
     * local
     *
     * @param file MultipartFile
     * @return Dict
     */
    @PostMapping(value = "/local", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Dict local(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Dict.create().set("code", 400).set("message", "文件内容为空");
        }
        String fileName = file.getOriginalFilename();
        String rawFileName = CharSequenceUtil.subBefore(fileName, ".", true);
        String fileType = CharSequenceUtil.subAfter(fileName, ".", true);
        String localFilePath = CharSequenceUtil.appendIfMissing(fileTempPath, "/") +
                rawFileName + "-" + DateUtil.current() + "." + fileType;
        try {
            file.transferTo(new File(localFilePath));
        } catch (IOException e) {
            log.error("【文件上传至本地】失败，绝对路径：{}", localFilePath);
            return Dict.create().set("code", 500).set("message", "文件上传失败");
        }

        log.info("【文件上传至本地】绝对路径：{}", localFilePath);
        return Dict.create().set("code", 200).set("message", "上传成功")
                .set("data", Dict.create().set("fileName", fileName).set("filePath", localFilePath));
    }

    /**
     * qiniuyun
     *
     * @param file MultipartFile
     * @return Dict
     */
    @PostMapping(value = "/qiniuyun", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Dict qiniuyun(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Dict.create().set("code", 400).set("message", "文件内容为空");
        }
        String fileName = file.getOriginalFilename();
        String rawFileName = CharSequenceUtil.subBefore(fileName, ".", true);
        String fileType = CharSequenceUtil.subAfter(fileName, ".", true);
        String localFilePath = CharSequenceUtil.appendIfMissing(fileTempPath, "/")
                + rawFileName + "-" + DateUtil.current() + "." + fileType;
        try {
            file.transferTo(new File(localFilePath));
            Response response = qiniuyunService.uploadFile(new File(localFilePath));
            if (response.isOK()) {
                JSONObject jsonObject = JSONUtil.parseObj(response.bodyString());
                String yunFileName = jsonObject.getStr("key");
                String yunFilePath = CharSequenceUtil.appendIfMissing(prefix, "/") + yunFileName;
                FileUtil.del(new File(localFilePath));

                log.info("【文件上传至七牛云】绝对路径：{}", yunFilePath);
                return Dict.create().set("code", 200).set("message", "上传成功").set("data", Dict.create()
                        .set("fileName", yunFileName).set("filePath", yunFilePath));
            } else {
                log.error("【文件上传至七牛云】失败，{}", JSONUtil.toJsonStr(response));
                FileUtil.del(new File(localFilePath));
                return Dict.create().set("code", 500).set("message", "文件上传失败");
            }
        } catch (IOException e) {
            log.error("【文件上传至七牛云】失败，绝对路径：{}", localFilePath);
            return Dict.create().set("code", 500).set("message", "文件上传失败");
        }
    }
}
