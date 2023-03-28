## Demo-Kafka

> 主要演示了 Spring Boot 如何集成 kafka，实现消息的发送和接收。

### 环境准备

1. 解压文件

```shell
tar -zxvf kafka_2.12-3.0.1.tgz
```

2. 修改zookeeper配置文件 zookeeper.properties

```properties
tickTime=2000
initLimit=10
syncLimit=5
dataDir=/home/kafka/kafka3.0/zookeeper/datas
clientPort=2181
# yifan4 yifan5 yifan6 为hosts中配置的dns解析 可以直接修改为ip
server.0=yifan4:2888:3888
server.1=yifan5:2888:3888
server.2=yifan6:2888:3888
```

3. 启动zookeeper

```shell
# kafka/bin 目录下
nohup sh zookeeper-server-start.sh ../config/zookeeper.properties &
```

4. 修改kafka配置文件 server.properties

```properties
# 如果是集群 broker.id 需要集群内唯一
broker.id=2
log.dirs=/home/kafka/kafka3.0/datas
zookeeper.connect=yifan4:2181,yifan5:2181,yifan6:2181/kafka
```

5. 启动kafka

```shell
# kafka/bin 目录下
nohup sh kafka-server-start.sh ../config/server.properties &
# jps 查看是否zookeeper和kafka都启动成功
# 若启动错误，可以删除 datas 和 zookeeper 目录重新启动
```

6. 关闭
```shell
# 需先关kafka后关zookeeper
# kafka/bin 目录下
sh kafka-server-stop.sh
sh zookeeper-server-stop.sh
```

---

### 参考

Spring Boot 版本和 Spring-Kafka 的版本对应关系

```bash
explorer https://spring.io/projects/spring-kafka
```

Spring-Kafka 官方文档

```bash
explorer https://docs.spring.io/spring-kafka/docs/2.2.0.RELEASE/reference/html/
```