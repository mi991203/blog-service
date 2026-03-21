# blog-api Docker 部署文档

## 1. 这份方案做了什么

这份方案已经为 `blog-api` 准备好了 Docker 部署所需内容：

- `blog-api/Dockerfile`
- `blog-api/src/main/resources/application-docker.yml`
- `.dockerignore`

你只需要会用基础 `docker` 命令，就可以在本地把 `blog-api` 打成镜像并运行起来。

## 2. 启动前提

在执行 Docker 命令前，请先确认下面 3 件事：

- Docker Desktop 已启动
- MySQL 已启动
- Redis 已启动

当前容器默认连接宿主机上的服务：

- MySQL 主机：`host.docker.internal`
- MySQL 端口：`3306`
- 数据库名：`blog`
- Redis 主机：`host.docker.internal`
- Redis 端口：`6379`
- 应用端口：`8083`

说明：

- `host.docker.internal` 表示 Docker 容器访问你电脑本机
- 这个写法适合你现在这种“数据库和 Redis 在本机，应用跑在 Docker 里”的场景

## 3. 第一步：构建镜像

在项目根目录执行：

```powershell
docker build -f blog-api/Dockerfile -t blog-api:1.0.0 .
```

说明：

- `docker build`：构建镜像
- `-f blog-api/Dockerfile`：指定使用 `blog-api` 模块下的 Dockerfile
- `-t blog-api:1.0.0`：给镜像起名字，镜像名是 `blog-api`，标签是 `1.0.0`
- 最后的 `.`：表示构建上下文是当前项目根目录

为什么最后要用项目根目录作为上下文：

- 因为 `blog-api` 依赖 `blog-user` 和 `blog-common`
- 构建镜像时需要把这些模块一起复制进容器中进行 Maven 打包

## 4. 第二步：启动容器

在项目根目录执行：

```powershell
docker run -d --name blog-api -p 8083:8083 blog-api:1.0.0
```

说明：

- `docker run`：启动一个容器
- `-d`：后台运行
- `--name blog-api`：容器名称叫 `blog-api`
- `-p 8083:8083`：把你电脑的 `8083` 端口映射到容器的 `8083` 端口

执行完成后，你可以访问：

```text
http://localhost:8083
```

如果返回 `404`，这通常是正常的，表示服务已经启动，但项目没有定义根路径 `/` 的接口。

## 5. 查看运行状态

查看容器是否启动成功：

```powershell
docker ps
```

查看应用日志：

```powershell
docker logs -f blog-api
```

当日志中出现类似下面内容时，表示启动成功：

```text
Started BlogApiApplication
```

## 6. 停止和删除容器

停止容器：

```powershell
docker stop blog-api
```

删除容器：

```powershell
docker rm blog-api
```

如果你想重新运行，建议按这个顺序执行：

```powershell
docker stop blog-api
docker rm blog-api
docker run -d --name blog-api -p 8083:8083 blog-api:1.0.0
```

## 7. 如果你的数据库密码不是 123456

可以在启动容器时覆盖默认配置，例如：

```powershell
docker run -d --name blog-api -p 8083:8083 `
  -e DB_HOST=host.docker.internal `
  -e DB_PORT=3306 `
  -e DB_NAME=blog `
  -e DB_USERNAME=root `
  -e DB_PASSWORD=你的密码 `
  -e REDIS_HOST=host.docker.internal `
  -e REDIS_PORT=6379 `
  blog-api:1.0.0
```

说明：

- `-e` 表示传入环境变量
- 如果你的 MySQL、Redis 地址或密码和默认值不同，就用这种方式改

## 8. 常用排查

如果容器启动失败，优先检查下面几项：

- `docker logs blog-api`
- 本机 `3306` 和 `6379` 是否真的可用
- MySQL 的用户名密码是否正确
- 数据库 `blog` 是否存在
- 本机 `8083` 端口是否已经被别的程序占用

## 9. 最常用的两条命令

构建镜像：

```powershell
docker build -f blog-api/Dockerfile -t blog-api:1.0.0 .
```

启动容器：

```powershell
docker run -d --name blog-api -p 8083:8083 blog-api:1.0.0
```
