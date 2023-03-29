## Demo-Zookeeper

> 主要演示了如何使用 Spring Boot 集成 Zookeeper 结合AOP实现分布式锁。

---

### zookeeper 启动

```shell
nohup sh zkServer.sh --config ../conf start &
```

### zookeeper 关闭

```shell
zkServer.sh stop
```