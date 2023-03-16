package demo.useragent.controller;

import com.google.common.collect.Maps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * UserAgentController
 *
 * @author Wenzhou
 * @since 2023/3/13 10:54
 */
@RestController
public class UserAgentController {

    /**
     * getInfo
     *
     * @param request HttpServletRequest
     * @return Map<String, String>
     */
    @GetMapping("/user-agent-info")
    public Map<String, String> getInfo(HttpServletRequest request) {
        Map<String, String> res = Maps.newHashMap();

        String agent = request.getHeader("User-Agent");
        //解析agent字符串
        UserAgent userAgent = eu.bitwalker.useragentutils.UserAgent.parseUserAgentString(agent);
        //获取浏览器对象
        Browser browser = userAgent.getBrowser();
        //获取操作系统对象
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

        res.put("browserName", browser.getName());
        res.put("browserType", browser.getBrowserType().toString());
        res.put("browserGroup", browser.getGroup().toString());
        res.put("browserManufacturer", browser.getManufacturer().toString());
        res.put("browserRenderingEngine", browser.getRenderingEngine().toString());
        res.put("browserVersion", browser.getVersion(agent).toString());

        res.put("systemName", operatingSystem.getName());
        res.put("systemDeviceType", operatingSystem.getDeviceType().toString());
        res.put("systemGroup", operatingSystem.getGroup().toString());
        res.put("systemManufacturer", operatingSystem.getManufacturer().toString());
        return res;
    }
}
