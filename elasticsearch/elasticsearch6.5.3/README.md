## Elasticsearch6.5.3
> 此 demo 主要演示了 Spring Boot 如何集成 `spring-boot-starter-data-elasticsearch` 完成对 ElasticSearch 的高级使用技巧，包括创建索引、配置映射、删除索引、增删改查基本操作、复杂查询、高级查询、聚合查询等。

---

### Windows
下载地址
```shell
https://www.elastic.co/cn/downloads/past-releases/elasticsearch-6-5-3
```

### Docker
1. 下载镜像：`docker pull elasticsearch:6.5.3`
2. 运行容器：`docker run -d -p 9200:9200 -p 9300:9300 --name elasticsearch-6.5.3 elasticsearch:6.5.3`
3. 进入容器：`docker exec -it elasticsearch-6.5.3 /bin/bash`
4. 安装 ik 分词器：`./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.5.3/elasticsearch-analysis-ik-6.5.3.zip`
5. 修改 es 配置文件：`vi ./config/elasticsearch.yml`
   ```yaml
   cluster.name: "docker-cluster"
   network.host: 0.0.0.0

   # minimum_master_nodes need to be explicitly set when bound on a public IP
   # set to 1 to allow single node clusters
   # Details: https://github.com/elastic/elasticsearch/pull/17288
   discovery.zen.minimum_master_nodes: 1

   # just for elasticsearch-head plugin
   http.cors.enabled: true
   http.cors.allow-origin: "*"
   ```
6. 退出容器：`exit`
7. 停止容器：`docker stop elasticsearch-6.5.3`
8. 启动容器：`docker start elasticsearch-6.5.3`

---

### 参考
- ElasticSearch 官方文档：
  https://www.elastic.co/guide/en/elasticsearch/reference/6.x/getting-started.html
- spring-data-elasticsearch 官方文档：
  https://docs.spring.io/spring-data/elasticsearch/docs/3.1.2.RELEASE/reference/html/
  
---

### 问题
```
java: 程序包org.springframework.boot不存在

原因 idea配置的maven加载不到autoconfigure

方法一
idea 设置 
构建、运行、部署 > 构建工具 > 运行程序 > 勾上【将IDE构建/运行操作委托给Maven】

方法二
通过 mvn -U idea:idea 命令重新加载maven包
打开终端，然后执行 mvn -U idea:idea 命令，之火重新运行项目
```

