## Demo-RabbitMQ

> 集成 RabbitMQ，演示基于直接队列模式、分列模式、主题模式、延迟队列的消息发送和接收。
---

### 注意

作者编写本demo时，RabbitMQ 版本使用 `3.7.7-management`，使用 docker 运行，下面是所有步骤：

1. 下载镜像

```bash
docker pull rabbitmq:3.7.7-management
```

2. 运行容器

```bash 
docker run -d -p 5671:5617 -p 5672:5672 -p 4369:4369 -p 15671:15671 -p 15672:15672 -p 25672:25672 --name rabbit-3.7.7 rabbitmq:3.7.7-management
```

3. 将环境中插件放到启动的容器中

```bash
# environment中rabbitmq_delayed_message_exchange-3.8.0.ez插件
docker cp ./rabbitmq_delayed_message_exchange-3.8.0.ez rabbit-3.7.7:/plugins
```

4. 进入容器

```bash
docker exec -it rabbit-3.7.7 /bin/bash
```

5. 给容器安装 解压工具 unzip

```bash
apt-get install -y unzip
# 找不到 就先更新 apt-get update
```

6. 解压插件包

```bash
unzip rabbitmq_delayed_message_exchange-3.8.0.ez
```

7. 启动延迟队列插件

```bash
rabbitmq-plugins enable rabbitmq_delayed_message_exchange
# The following plugins have been configured:
#   rabbitmq_delayed_message_exchange
#   rabbitmq_management
#   rabbitmq_management_agent
#   rabbitmq_web_dispatch
# Applying plugin configuration to rabbit@f72ac937f2be...
# The following plugins have been enabled:
#   rabbitmq_delayed_message_exchange
# 
# started 1 plugins.
```

8. 退出容器：`exit`
9. 停止容器：`docker stop rabbit-3.7.7`
10. 启动容器：`docker start rabbit-3.7.7`

> RabbitMQ UI
```bash
explorer http://192.168.139.6:15672/
# 账密 guest guest
```
---

### 参考

> SpringAMQP 官方文档

```shell
explorer https://docs.spring.io/spring-amqp/docs/2.1.0.RELEASE/reference/html/
```

RabbitMQ 官网

```shell
explorer http://www.rabbitmq.com/
# 延迟队列插件下载地址
explorer https://www.rabbitmq.com/community-plugins.html
```

RabbitMQ延迟队列

```shell
explorer https://www.cnblogs.com/vipstone/p/9967649.html
```
