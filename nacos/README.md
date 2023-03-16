### Nacos官网
```shell
explorer http://nacos.io/zh-cn
```

### Github
```shell
explorer https://github.com/alibaba/nacos
```

### Nacos 单机启动
```shell
startup.cmd -m standalone
startup.sh -m standalone
```

### Nacos 集群启动
```shell
#集群配置文件 
environment/nacos-cluster/nacos1/conf/cluster.conf
#自定义数据库配置 
environment/nacos-cluster/nacos1/conf/application.properties
    db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
    db.user.0=root
    db.password.0=63967261

#启动
startup.cmd -p embedded
startup.sh -p embedded
```
