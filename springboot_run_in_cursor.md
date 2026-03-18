# 介绍SpringBoot项目(Maven构建)在cursor中的运行方式

编译所有模块
mvn -DskipTests clean compile

只编译某个模块
mvn -pl blog-article -am -DskipTests clean compile

指定要运行哪个模块
mvn -pl blog-api spring-boot:run

