## Demo-Admin

> 主要演示了 Spring Boot 如何集成 Admin 管控台，监控管理 Spring Boot 应用，分别为 admin 服务端和 admin 客户端，两个模块。

---

### 运行步骤

- 进入 `server` 服务端，启动管控台服务端程序
- 进入 `client` 客户端，启动客户端程序，注册到服务端
- 观察服务端里客户端程序的运行状态等信息
```shell
# 账密 admin admin
explorer http://localhost:8000/
```
- 测试客户端宕机发送邮件