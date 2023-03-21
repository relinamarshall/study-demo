package demo.qiniuyun.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import demo.qiniuyun.service.IQiniuyunService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * DownloadController
 *
 * @author Wenzhou
 * @since 2023/3/21 20:22
 */
@Slf4j
@RestController
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {
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
     * download
     *
     * @param filename String
     * @param response HttpServletResponse
     */
    @GetMapping(value = "/{filename}")
    public void download(@PathVariable("filename") String filename, HttpServletResponse response) {
        if (filename.isEmpty()) {
            log.info("文件名称不能为空");
            return;
        }
        try {
            String fileUrl = qiniuyunService.getFile(prefix, filename, false);
            response.sendRedirect(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * privateDownload
     *
     * @param filename String
     * @param response HttpServletResponse
     */
    @GetMapping(value = "/private/{filename}")
    public void privateDownload(@PathVariable("filename") String filename, HttpServletResponse response) {
        if (filename.isEmpty()) {
            return;
        }
        try {
            String privateFile = qiniuyunService.getFile(prefix, filename, true);
            response.sendRedirect(privateFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
