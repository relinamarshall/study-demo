### 一、UserAgentUtils简介

UserAgentUtils 是一个用来解析 User-Agent 字符串的 Java 类库。

其能够识别的内容包括：

- 超过150种不同的浏览器
- 7种不同的浏览器类型
- 超过60种不同的操作系统
- 6种不同的设备类型
- 9种不同的渲染引擎
- 9种不同的Web应用，如HttpClient、Bot

官网

```shell
explorer https://www.bitwalker.eu/software/user-agent-utils
```

GitHub

```shell
explorer https://github.com/HaraldWalker/user-agent-utils
```

### 二、Maven依赖

```xml
<!-- 解析 UserAgent 信息 -->
<dependency>
	<groupId>eu.bitwalker</groupId>
	<artifactId>UserAgentUtils</artifactId>
	<version>1.20</version>
</dependency>
```

### 三、用例

在web应用中通过request获取用户的Agent:

```java
String agent=request.getHeader("User-Agent");

// 解析agent字符串
UserAgent userAgent=UserAgent.parseUserAgentString(agent);
// 获取浏览器对象
Browser browser=userAgent.getBrowser();

System.out.println("浏览器名:"+browser.getName());
System.out.println("浏览器类型:"+browser.getBrowserType());
System.out.println("浏览器家族:"+browser.getGroup());
System.out.println("浏览器生产厂商:"+browser.getManufacturer());
System.out.println("浏览器使用的渲染引擎:"+browser.getRenderingEngine());
System.out.println("浏览器版本:"+userAgent.getBrowserVersion());

// 获取操作系统对象
OperatingSystem operatingSystem=userAgent.getOperatingSystem();
System.out.println("操作系统名:"+operatingSystem.getName());
System.out.println("访问设备类型:"+operatingSystem.getDeviceType());
System.out.println("操作系统家族:"+operatingSystem.getGroup());
System.out.println("操作系统生产厂商:"+operatingSystem.getManufacturer());
```