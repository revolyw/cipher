# cipher

### 2017-01-10
1. 新增使用原生FilterChain来走csrf防护，避免过重的spring-security FilterChain
2. 重写HttpSessionCsrfTokenRepository来生产csrf防护token

### 2017-01-03
1. 新增freemarker相关配置及依赖
2. 新增全局拦截器(HttpInterceptor)用于做请求前后置处理
3. 新增HTTP(对request及response的封装)及HTTPHandlerMethodArgumentResolver(使Controller接收HTTP实例)
4. 新增CommonController(@Controller，返回视图解析器解析的视图),区别于RestfulContoller(@RestController,返回消息转换器转换的视图)

### 2016-12-27
1. 新增Spring-framework.security-web,config包,web.xml添加DelegateFilter
2. 新增CSRF protection相关代码CsrfFilter，CsrfTokenFactory，spring-framework.security.xml
3. 新增403页面及csrf演示demo页面（CSRFPage.jsp,CSRF.jsp）
4. 新增重定向至腾讯企业邮箱的接口

### 2016-12-16
1. 新增404及500跳转页并在web.xml中配置
2. 新增异常捕捉切面framework.ExceptionAspect（新增aspectjweaver的jar包依赖）
3. 修改LoggerUtil类，现可通过反射自动获取写日志的位置

### 2016-12-08

1. 新增测试实时修改jvm参数的访问接口

### 2016-12-05

1. 引入apache-commons-lang3工具包

### 2016-12-03

1. 新增枚举工具类util.EnumUtil，用于处理由type.EnumInterface接口规范的枚举类型
2. LoggerHandler改为LoggerUtil,可以更精确定位日志输出点,并且重载了几种常用的日志级别输出方法，使用更简洁
3. 新增util包下各个工具类的单元测试类
4. 新增commons-beanutils包依赖
5. 新增util.ObjectUtil一般对象工具类，并添加泛型对象深拷贝方法及其对应单元测试
6. 新增model.BaseModel抽象类,所有model pojo都将继承该类，该类对pojo常用的功能做了一般实现，如toJson，toString等

### 2016-11-25

1. 在RestfulController中添加了forward和beForwarded两个控制器方法，用于演示利用ModelAndView做转发，并利用Jsp EL表达式来输出转发过程中包装的数据

### 2016-11-24

1. 优化ExcelUtil，新增对xlsx文件的解析支持，并新增对公式类型的单元格数据的兼容处理

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

