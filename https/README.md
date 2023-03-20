## Demo-Https

> 此 demo 主要演示了 Spring Boot 如何集成 https
---

## keytool

> 随着信息安全的重要性的日益提高，HTTP/FTP等越来越多的被迁到了
> HTTPS/SFTP，SSL/TLS已经是避无可避。了解一些简单的加密算法的基本理论或者常见工具，或者如何生成和使用证书，这些都在工作中应用的愈加广泛。Java自带的Keytool工具就是这样的一种工具，被广泛地用于管理密钥和证书

> keytool工具是JDK自带的工具，所以前提就是安装JDK。具体可以参照Maven的安装脚本，安装Maven的前提是安装JDK，所以下面的脚本执行之后，JDK自然会被安装。已经有JDK的可以跳过此步

### 安装确认

确认keytool可用

```shell
which keytool
```

### 命令说明

| 项目              | 详细                                                                                                    |
|-----------------|-------------------------------------------------------------------------------------------------------|
| -certreq	       | 创建证书请求                                                                                                |
| -changealias    | 	变更证书私钥                                                                                               |
| -exportcert     | 	导出证书                                                                                                 |
| -genkeypair     | 	创建密钥对                                                                                                |
| -genseckey      | 	创建加密密钥                                                                                               |
| -gencert        | 	依据证书请求创建证书                                                                                           |
| -importcert     | 	导入证书或者证书链                                                                                            |
| -importpass     | 	导入密码                                                                                                 |
| -importkeystore | 	从其他keystore中导入一个或者所有条目                                                                               |
| -printcert      | 	打印证书内容                                                                                               |
| -printcertreq   | 	打印证书请求的内容                                                                                            |
| -printcrl       | 	打印CRL文件内容                                                                                            |
| -genkey         | 	在用户主目录中创建一个默认文件".keystore",还会产生一个mykey的别名，mykey中包含用户的公钥、私钥和证书                                        |
| -alias          | 	产生别名 缺省值"mykey"                                                                                      |
| -keystore       | 	指定密钥库的名称(产生的各类信息将不在.keystore文件中)                                                                     |
| -keyalg         | 	指定密钥的算法 (如 RSA DSA（如果不指定默认采用DSA）)                                                                    |
| -validity       | 	指定创建的证书有效期多少天 缺省值90天                                                                                 |
| -keysize        | 	指定密钥长度 缺省值1024                                                                                       |
| -storepass      | 	指定密钥库的密码(获取keystore信息所需的密码)                                                                          |
| -keypass        | 	指定别名条目的密码(私钥的密码)                                                                                     |
| -dname          | 	指定证书拥有者信息 例如： “CN=名字与姓氏,OU=组织单位名称,O=组织名称,L=城市或区域名称,ST=州或省份名称,C=单位的两字母国家代码”                           |
| -list           | 	显示密钥库中的证书信息 keytool -list -v -keystore 指定keystore -storepass 密码                                      |
| -v              | 	显示密钥库中的证书详细信息                                                                                        |
| -export         | 	将别名指定的证书导出到文件 keytool -export -alias 需要导出的别名 -keystore 指定keystore -file 指定导出的证书位置及证书名称 -storepass 密码 |
| -file           | 	参数指定导出到文件的文件名                                                                                        |
| -delete         | 	删除密钥库中某条目 keytool -delete -alias 指定需删除的别 -keystore 指定keystore -storepass 密码                          |
| -printcert      | 	查看导出的证书信息 keytool -printcert -file 证书名称                                                              |
| -keypasswd      | 	修改密钥库中指定条目口令                                                                                         |
| -storepasswd    | 	修改keystore口令 keytool -storepasswd -keystore keystore的FULLPATH -storepass 原始密码 -new 新密码               |
| -import         | 	将已签名数字证书导入密钥库 keytool -import -alias 指定导入条目的别名 -keystore 指定keystore -file 需导入的证书                     ||

### 证书管理

> 证书的发行有专门的CA机构，但是基本上都是要付费的，CA机构又不是NGO，无利不起早，不然为什么人家要做这个呢。一般来说除非是非常正式的项目，一般的项目很多情况下使用自发行的证书即可。

### 基础知识

| 认证方式	 | 证书种类	       | 详细                                 |
|-------|-------------|------------------------------------|
| 单向认证	 | 服务器端证书	     | 客户端对服务器端的证书进行认证                    |
| 单向认证	 | 服务端证书/客户端证书 | 客户端对服务器端的证书进行认证,同时服务器端对客户端的证书也进行认证 |

### keystore生成

> 按照如下信息生成keystore

| 项目        | 详细                    |
|-----------|-----------------------|
| alias名称   | 	kstore               |
| keypass   | 	init123              |
| 算法        | 	RSA                  |
| 秘钥长度      | 	2048                 |
| 有效期限(天)   | 	30                   |
| 保存路径      | 	/tmp/kstore.keystore |
| storepass | 	init234              |

```shell
keytool -genkey -alias yifan -keypass 63967261 -keyalg RSA -keysize 2048 -validity 30 -keystore ./server.keystore -storepass 63967261
# -storepass 为密码

#hat is your first and last name?
#  [Unknown]:  michael
#What is the name of your organizational unit?
#  [Unknown]:  liumiaocn
#What is the name of your organization?
#  [Unknown]:  ngo
#What is the name of your City or Locality?
#  [Unknown]:  dalian
#What is the name of your State or Province?
#  [Unknown]:  liaoning
#What is the two-letter country code for this unit?
#  [Unknown]:  CN
#Is CN=michael, OU=liumiaocn, O=ngo, L=dalian, ST=liaoning, C=CN correct?
#  [no]:  [是 or yes]
```

### keystore确认

> 因为生成的/tmp/kstore.keystore非文本类型文件，无法直接确认内容，使用list子命令可以确认keystore的详细信息。

```shell
keytool -list -v -keystore ./server.keystore -storepass 63967261
```

### 证书导出

```shell
keytool -export -alias yifan -keystore ./server.keystore -file ./kstore.crt -rfc -storepass 63967261

# 查看内容
# cat ./kstore.crt 
```

> 注意: storepass的密码是/tmp/kstore.keystore生成时创建的密码，此处是作确认用，输入错误会提示：Keystore was tampered
> with,or password was incorrect

### 证书确认

```shell
keytool -printcert -file ./kstore.crt
```

### 证书导入

> 将生成的证书倒入到keystore中

```shell
keytool -import -alias yifan -file ./kstore.crt -keystore ./server.keystore -storepass 63967261 -keypass 63967261
```

### 证书删除

```shell
keytool -delete -alias yifan -keystore ./server.keystore -storepass 63967261
```