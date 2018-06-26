# demospringboot
最近要交一份project项目作业，前后端分离，我负责后端，选用了Springboot+mybatis进行整合操作。

### 一.前期准备:
开发使用的IDE为IntelliJ IDEA:[https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)

maven仓库整合jar包：[http://mvnrepository.com/artifact/org.apache.maven/maven-plugin-api/3.5.3](http://mvnrepository.com/artifact/org.apache.maven/maven-plugin-api/3.5.3)

下完上述两样后，IDEA安装略，我们开始使用IDEA配置默认的maven库
![准备.gif](https://upload-images.jianshu.io/upload_images/9003228-e0a9489d1b708069.gif?imageMogr2/auto-orient/strip)
从GIF中可以看到，我们点击configure菜单项下的Project Defaults来设置项目的默认配置，从settings选项中，找到Maven库一栏，选择我们刚才下载好的maven仓库的安装目录。

创建好我们的数据表。数据库名为jiguo，表名为jiguo_user。
![数据表.JPG](https://upload-images.jianshu.io/upload_images/9003228-1a6404ad604a241b.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

以上，前期准备就准备好啦。


### 二.项目结构一栏以及实现步骤。
![概览.JPG](https://upload-images.jianshu.io/upload_images/9003228-f46909ca2c00dfbb.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

实现步骤：
1.Springboot项目创建
2.在pom.xml中，使用maven架包导入mybatis依赖dependency
3.逆向工程，配置自己的generatorConfig逆向工程的配置文件，然后使用maven插件执行出来，逆向出DAO层还有Model层
4.在application.properties中，配置我们的数据源 mybatis所需要的配置
5.MyMapper接口的创建
6.service层代码的创建
7.创建controller，处理前端的请求。
8.启动文件中设置DAO层扫描，这样就能识别出DAO层注解
@MapperScan(basePackages = "com.example.sl.xxxxx.dao")

####步骤一:Springboot项目创建
![1.1.JPG](https://upload-images.jianshu.io/upload_images/9003228-a53111a4a8e7046b.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![1.2.JPG](https://upload-images.jianshu.io/upload_images/9003228-ef6eb04bfd33dd8e.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![1.3.JPG](https://upload-images.jianshu.io/upload_images/9003228-e3be8594a9f690d8.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
完成创建一个名为demospringboot的项目

####步骤二:在pom.xml中，使用maven架包导入mybatis等依赖
pom.xml：
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example.sl</groupId>
    <artifactId>demospringboot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>demospringboot</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <!--alibaba-start-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.0</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.9</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.47</version>
        </dependency>
        <!--alibaba-end-->

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.41</version>
        </dependency>
        <!--mybatis-start-->
        <!--mybatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.1</version>
        </dependency>
        <!--generator-->
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.2</version>
            <scope>compile</scope>
            <optional>true</optional>
        </dependency>
        <!--mapper-->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>1.2.4</version>
        </dependency>
        <!--pagehelper-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.3</version>
        </dependency>
        <!--mybatis-end-->

        <!-- 引入 redis 依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>

            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.18.1</version>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.2</version>
                <configuration>
                    <verbose>true</verbose>
                    <overwrite>true</overwrite>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>5.1.29</version>
                    </dependency>
                    <dependency>
                        <groupId>tk.mybatis</groupId>
                        <artifactId>mapper</artifactId>
                        <version>4.0.0</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>

    </build>
</project>

```
![2.1.JPG](https://upload-images.jianshu.io/upload_images/9003228-c48874196e7387d7.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

import以后，我们导入了mybatis，mysql，阿里巴巴的sql检测插件，分页插件等。

####步骤三:逆向工程，配置自己的generatorConfig(resource包下)逆向工程的配置文件，然后使用maven插件执行出来，逆向出DAO层还有Model层
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!--1.jdbcConnection设置数据库连接-->
    <!--2.javaModelGenerator设置类的生成位置-->
    <!--3.sqlMapGenerator设置生成xml的位置-->
    <!--4.javaClientGenerator设置生成dao层接口的位置-->
    <!--5.table设置要进行逆向工程的表名以及要生成的实体类的名称-->


    <context id="default" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>
        <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <property name="mappers" value="com.example.sl.demospringboot.util.MyMapper"/>
        </plugin>

        <!-- optional，旨在创建class时，对注释进行控制 -->
        <commentGenerator>
            <property name="suppressDate" value="true"/>
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>

        <!--jdbc的数据库连接 -->
        <jdbcConnection
                driverClass="com.mysql.jdbc.Driver"
                connectionURL="jdbc:mysql://localhost:3306/jiguo?characterEncoding=utf-8"
                userId="root"
                password="root">
        </jdbcConnection>


        <!-- 非必需，类型处理器，在数据库类型和java类型之间的转换控制-->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>


        <!-- Model模型生成器,用来生成含有主键key的类，记录类 以及查询Example类
            targetPackage     指定生成的model生成所在的包名
            targetProject     指定在该项目下所在的路径
        -->
        <!--<javaModelGenerator targetPackage="com.mmall.pojo" targetProject=".\src\main\java">-->
        <javaModelGenerator targetPackage="com.example.sl.demospringboot.model" targetProject="./src/main/java">
            <!-- 是否允许子包，即targetPackage.schemaName.tableName -->
            <property name="enableSubPackages" value="false"/>
            <!-- 是否对model添加 构造函数 -->
            <property name="constructorBased" value="false"/>
            <!-- 是否对类CHAR类型的列的数据进行trim操作 -->
            <property name="trimStrings" value="true"/>
            <!-- 建立的Model对象是否 不可改变  即生成的Model对象不会有 setter方法，只有构造方法 -->
            <property name="immutable" value="false"/>
        </javaModelGenerator>

        <!--mapper映射文件生成所在的目录 为每一个数据库的表生成对应的SqlMap文件 -->
        <!--<sqlMapGenerator targetPackage="mappers" targetProject=".\src\main\resources">-->
        <sqlMapGenerator targetPackage="mappers" targetProject="./src/main/resources">
            <property name="enableSubPackages" value="false"/>
        </sqlMapGenerator>

        <!-- 客户端代码，生成易于使用的针对Model对象和XML配置文件 的代码
                type="ANNOTATEDMAPPER",生成Java Model 和基于注解的Mapper对象
                type="MIXEDMAPPER",生成基于注解的Java Model 和相应的Mapper对象
                type="XMLMAPPER",生成SQLMap XML文件和独立的Mapper接口
        -->

        <!-- targetPackage：mapper接口dao生成的位置 -->
        <!--<javaClientGenerator type="XMLMAPPER" targetPackage="com.mmall.dao" targetProject=".\src\main\java">-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.example.sl.demospringboot.dao" targetProject="./src/main/java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </javaClientGenerator>

        <table tableName="jiguo_user" domainObjectName="User" enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="false" selectByExampleQueryId="false"></table>
        <!-- geelynote mybatis插件的搭建 -->
    </context>
</generatorConfiguration>
```
然后执行我们的mybatis插件
![3.1.JPG](https://upload-images.jianshu.io/upload_images/9003228-c2568e35df247b22.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
就发现我们的DAO层和model层已经创建好了。对应jiguo数据库中的
jiguo_user表。

### 四。在application.properties中，配置我们的数据源 mybatis所需要的配置
```
server.port=8080//服务器的端口号
#mysql链接配置
spring.datasource.url=jdbc:mysql://localhost:3306/jiguo
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.durid.initialsize=1

#mybatis配置
#首先是实体类所在的包的名字
mybatis.type-aliases-package=com.example.sl.demospringboot.model
mybatis.mapper-locations=classpath:mappers/*.xml
#mybatis使用resources的xml来映射数据库表，这里就是resources下的mappers包的所有xml文件

#MyMapper是继承了一些封装好的方法接口 CRUD
#mapper
#mappers 多个接口时逗号隔开 通配mappers
mapper.mappers=com.example.sl.demospringboot.util.MyMapper
mapper.not-empty=false
mapper.identity=MYSQL

#pagehelper 分页插件
pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql
```

配置了服务器端口号，数据库的链接数据，以及mybatis的映射表关系，我们发现有一个MyMapper接口，是我们之前没创建的，这是一个通配mappers（封装了一些常用CRUD操作，可以通过实现接口来进行），在maven库中添加依赖的时候，我们已经添加了相关接口的库tk.mybatis。
所以我们执行步骤五。

####五.MyMapper接口的创建
util包下，创建MyMapper接口:
MyMapper：
```
package com.example.sl.demospringboot.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

//自定义接口集成大牛给我们写好的数据层的接口类
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
```
然后在DAO层继承这个接口。
UserMapper：
```
package com.example.sl.demospringboot.dao;

import com.example.sl.demospringboot.model.User;
import com.example.sl.demospringboot.util.MyMapper;

public interface UserMapper extends MyMapper<User> {
}
```
以上我们完成了DAO层和数据库的准备，然后我们开发我们的service层，业务层。

六.service层代码的创建
我们将UserMapper使用注解的方式，生成，减少内存消耗，再调用之前别人写好的接口方法，进行CRUD操作（自己定制的CRUD操作暂略）。
UserService:
```
package com.example.sl.demospringboot.service;

import com.example.sl.demospringboot.model.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();
}

```
UserServiceImpl:
```
package com.example.sl.demospringboot.service;

import com.example.sl.demospringboot.dao.UserMapper;
import com.example.sl.demospringboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }
}

```
####七.创建controller，处理前端的请求
UserController:
```
package com.example.sl.demospringboot.controller;

import com.example.sl.demospringboot.model.User;
import com.example.sl.demospringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<User> findAllUser(){
        List<User> userlist=userService.findAll();
        return userlist;
    }
}

```

####八..启动文件中设置DAO层扫描，这样就能识别出DAO层注解
@MapperScan(basePackages = "com.example.sl.xxxxx.dao")
```
package com.example.sl.demospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.example.sl.demospringboot.dao")//这个注解注意一下 放DAO层的包名 对这个包下进行注入
public class DemospringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemospringbootApplication.class, args);
    }
}

```
保存~~后台完成~

### 三.自定义一个login.html进行请求测试。(由于JS要跨域，这里不进行ajax请求了 这里我们直接使用表单 PS:本地可以将一个ajax请求的页面放在本地Apache容器中来解决）
login.html:
```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login.html</title>
</head>
<body>
<form action="/user/findAll">
    <input type="submit" value="login">
</form>
</body>
</html>
```
然后运行~
![演示.gif](https://upload-images.jianshu.io/upload_images/9003228-e1a550e5456fd9d5.gif?imageMogr2/auto-orient/strip)


测试完成~终于可以睡觉啦







