package demo.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import demo.easyexcel.dao.UploadDao;
import demo.easyexcel.dto.DownLoadDto;
import demo.easyexcel.dto.UploadDto;
import demo.easyexcel.listener.UploadDataListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TestEasyExcelWeb
 *
 * @author Wenzhou
 * @since 2023/3/18 3:07
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class TestEasyExcelWeb {
    /**
     * uploadDAO
     */
    private final UploadDao uploadDao;

    /**
     * toIndex
     *
     * @return String
     */
    @GetMapping({"/", "/index", "/index.html"})
    public String toIndex() {
        return "index";
    }

    /**
     * download
     * <p>
     * 文件下载(失败了会返回一个有部分数据的Excel)
     * <p>
     * 1.创建excel对应的实体对象 参照{@link DownLoadDto}
     * <p>
     * 2.设置返回的参数
     * <p>
     * 3.直接写 这里注意 finish的时候会自动关闭OutputStream,当然外面再关闭流问题不大
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        /**
         * 正则表达式先把第一个'\'当作转义字符，得到"\+"
         * Java字符串又把"\+"中的'\'当作转义字符，得到'+'
         * 所以这条语句是把字符串中所有的'+'替换成'%20'，在URL中%20代表空格
         * 比如在网页输入框中输入了abcd+efg，提交表单的时候将其替换为abcd%20efg
         */
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DownLoadDto.class).sheet("模板").doWrite(data());
    }

    /**
     * downloadFailedUsingJson
     * <p>
     * 文件下载并且失败的时候返回Json (默认失败了会返回一个有部分数据的excel)
     *
     * @param response HttpServletResponse
     */
    @GetMapping("downloadFailedUsingJson")
    public void downloadFailedUsingJson(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("测试", "utf-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            throw new RuntimeException("模拟下载中出错");

            // 这里需要设置不关闭流
            //EasyExcel.write(response.getOutputStream(), DownLoadData.class)
            // .autoCloseStream(Boolean.FALSE).sheet("模板").doWrite(data());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            HashMap<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败:>" + e.getMessage());
            log.info("下载文件失败:>{}", e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    /**
     * upload
     * <p>
     * 文件上传
     * <p>
     * 1.创建excel对应的实体对象 参照{@link UploadDto}
     * <p>
     * 2.由于默认一行行的读取excel,所以需要创建excel一行行的回调监听器 参照{@link UploadDataListener}
     * <p>
     * 3.直接读即可
     * double四舍五入
     * <p>
     * 1.(double) Math.round(d * 100) / 100;
     * <p>
     * 2.BigDecimal  setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()
     * <p>
     * - BigDecimal.ROUND_HALF_UP表示四舍五入
     * <p>
     * - BigDecimal.ROUND_HALF_DOWN也是五舍六入
     * <p>
     * - BigDecimal.ROUND_UP表示进位处理（就是直接加1）
     * <p>
     * - BigDecimal.ROUND_DOWN表示直接去掉尾数
     * <p>
     * 3.new DecimalFormat("#.00")
     * <p>
     * 4.String.format("%.2f", d);
     */
    @ResponseBody
    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            String str = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            log.info("文件名后缀为:{}", str);
            if (!"xlsx".equals(str) && (!"xls".equals(str))) {
                return "error>请上传Excel文件";
            }
        }

        double fileSize = Math.round(file.getSize() / 1024.0 * 100) / 100.0;
        log.info("文件大小为:{}KB", fileSize);
        if (fileSize >= 1024.0) {
            return "error>文件大小大于1MB,上传失败";
        }
        log.info("文件名字为:{}", originalFilename);

        EasyExcelFactory.read(file.getInputStream(), UploadDto.class,
                new UploadDataListener(uploadDao)).sheet().doRead();
        return "success";
    }

    /**
     * data
     *
     * @return List<DownLoadDto>
     */
    private List<DownLoadDto> data() {
        List<DownLoadDto> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DownLoadDto data = new DownLoadDto();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
