# blog-api 模块 Maven 启动文档

## 1. 启动前提

- 已安装 `JDK 1.8`
- 已安装 `Maven 3.9.x`
- MySQL 已启动，且 `blog-api/src/main/resources/application.yml` 中配置可用
- Redis 已启动，且 `blog-api/src/main/resources/application.yml` 中配置可用

当前项目默认使用的连接配置：

- MySQL: `localhost:3306/blog`
- Redis: `localhost:6379`
- 应用端口: `8083`

## 2. 推荐启动方式

先在项目根目录执行：

```powershell
mvn -pl blog-api -am clean install -DskipTests
```

说明：

- `-pl blog-api`：只指定处理 `blog-api` 模块
- `-am`：自动把 `blog-api` 依赖的模块一起构建，例如 `blog-common`、`blog-user`
- `clean install`：重新编译并安装到本地 Maven 仓库，确保 `blog-api` 启动时能拿到最新依赖
- `-DskipTests`：跳过测试，加快启动前准备速度

然后进入 `blog-api` 目录执行：

```powershell
mvn spring-boot:run
```

说明：

- 这条命令会直接启动 `blog-api` 的 Spring Boot 应用
- 命令不会自动退出，控制台持续输出日志表示服务正在运行

## 3. 为什么是两条命令

这个项目的 `blog-api` 依赖同仓库里的其他模块。

如果直接在根目录执行某些写法，`spring-boot:run` 可能会落到父工程上，或者 `blog-api` 运行时拿不到最新的依赖类。

先执行一次：

```powershell
mvn -pl blog-api -am clean install -DskipTests
```

再执行：

```powershell
mvn spring-boot:run
```

是这个项目当前最稳定、最容易理解、也已经验证通过的原生 Maven 启动方式。

## 4. 已验证结果

已在本机实际验证通过，结果如下：

- 验证时间：`2026-03-21 09:55:16 +08:00`
- 启动日志包含：`Started BlogApiApplication`
- 监听端口：`8083`
- 端口检测结果：`localhost:8083 = True`
- 访问 `http://localhost:8083/` 返回 `404`

其中根路径返回 `404` 是正常现象，表示服务已经启动，但项目没有定义 `/` 这个接口。

## 5. 成功标志

当控制台出现类似下面这行日志时，表示启动成功：

```text
Started BlogApiApplication in 5.169 seconds
```

## 6. 启动命令汇总

在项目根目录：

```powershell
mvn -pl blog-api -am clean install -DskipTests
```

进入 `blog-api` 目录后：

```powershell
mvn spring-boot:run
```
