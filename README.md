# cipher

> 这是一个很纯粹的项目，为了集成我所学得一些零散的技术。方便日后复用。下面我将按时间结合技术点来记述cipher的进程。 2016-11

### 2016-11-23

1. 解决了一些jar包冲突和缺失，解决途径：可以通过在http://mvnrepository.com/上查找具体组件的依赖关系
2. 利用泛型对BaseDao做了spring data jpa接口及自定义接口的实现

### 2016-11-22

1. spring整合druid连接池及hibernate框架 **100%**
2. 加入servlet-api 3.1.0
3. 初步尝试构建Spring Data JPA
4. 初步设计通用DAO，改日须优化调整

### 2016-11-21

1. 今天做了一个重要的决定，将cipher所使用到的框架全部采用稳定的版本，并且全局使用配置文件而尽量不使用注解(java5) ，最新框架及技术的使用将在日后另起项目(cipher_spring_boot)做新的尝试.
2. 新增UserAgentUtil,用于对客户端信息做解析

### 2016-11-20

1. spring整合druid连接池及hibernate框架 **50%**

### 2016-11-17

1. 引入Apache POI依赖jar
2. 新增Excel解析工具（暂时只支持2003版本xls）
3. 配置log4j
4. 监听spring容器启动，并利用其初始化配置

### 2016-11-14
1. 接入spring mvc
2. 搭建Restful Controller

### 2016-11-13
1. 初始化[github仓库](https://github.com/revolyw/cipher)
2. 初始化maven项目结构
3. 引入spring基础依赖

