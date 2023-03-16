## Elasticsearch7.6.1

> 此 demo 主要演示了 Spring Boot 如何集成 `elasticsearch-rest-high-level-client` 完成对 `ElasticSearch 7.x` 版本的基本
> CURD 操作
---

### Windows

```shell
环境地址
environment/elasticsearch-7.6.1

启动
environment/elasticsearch-7.6.1/bin/elasticsearch.bat
```

### Docker

1.下载镜像：`docker pull elasticsearch:7.6.1`

2.下载安装 `docker-compose`，参考文档： https://docs.docker.com/compose/install/

```bash
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```

3.编写docker-compose 文件

```yaml
version: "3"

services:
  es7:
    hostname: es7
    container_name: es7
    image: elasticsearch:7.6.1
    volumes:
      - "/data/es7/logs:/usr/share/es7/logs:rw"
      - "/data/es7/data:/usr/share/es7/data:rw"
    restart: on-failure
    ports:
      - "9200:9200"
      - "9300:9300"
    environment:
      cluster.name: elasticsearch
      discovery.type: single-node
    logging:
      driver: "json-file"
      options:
        max-size: "50m"
```

4.启动: `docker-compose -f elasticsearch.yaml up -d`

---

### Elasticsearch 升级

先升级到 6.8，索引创建，设置 mapping 等操作加参数：include_type_name=true，然后滚动升级到 7，旧索引可以用 type 访问。具体可以参考：

```shell
explorer https://www.elastic.co/cn/blog/moving-from-types-to-typeless-apis-in-elasticsearch-7-0
```

```shell
explorer https://www.elastic.co/guide/en/elasticsearch/reference/7.0/removal-of-types.html
```

---

### Elasticsearch 配置账密

1、修改elasticsearch.yml文件,在里面添加如下内容,并重启es

```shell
xpack.security.enabled: true
xpack.license.self_generated.type: basic
xpack.security.transport.ssl.enabled: true
```

2、进入es的安装根目录bin下 `本Demo密码全都是63967261`

```shell
# 按不同系统执行 设置用户名和密码的命令,为以下几个用户分别设置密码
# elastic,apm_system,kibana,kibana_system,logstash_system,beats_system,remote_monitoring_user
elasticsearch-setup-passwords interactive

Initiating the setup of passwords for reserved users elastic,apm_system,kibana,kibana_system,logstash_system,beats_system,remote_monitoring_user.
You will be prompted to enter passwords as the process progresses.
Please confirm that you would like to continue [y/N]y

Enter password for [elastic]: 
Reenter password for [elastic]: 
Enter password for [apm_system]: 
Reenter password for [apm_system]: 
Enter password for [kibana_system]: 
Reenter password for [kibana_system]: 
Enter password for [logstash_system]: 
Reenter password for [logstash_system]: 
Enter password for [beats_system]: 
Reenter password for [beats_system]: 
Enter password for [remote_monitoring_user]: 
Reenter password for [remote_monitoring_user]: 
Changed password for user [apm_system]
Changed password for user [kibana_system]
Changed password for user [kibana]
Changed password for user [logstash_system]
Changed password for user [beats_system]
Changed password for user [remote_monitoring_user]
Changed password for user [elastic]
```

3、如果有kibana,则配置kibana.yml

```shell
elasticsearch.username: "elastic"
elasticsearch.password: "63967261"
```