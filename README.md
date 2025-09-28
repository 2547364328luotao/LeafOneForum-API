# LeafOneForum API

LeafOneForum 是一个面向校园社区场景的轻量级论坛后端，覆盖注册激活、登录注销、帖子与回复互动、角色与权限控制等核心业务。项目基于 Spring Boot 3 构建，默认监听 `8080` 端口，并通过会话与 JWT 双通道支持前后端分离与同源部署。

- 全局拦截策略：除 `/login`、`/sign`、`/sign/**`、静态资源与公开文档外，其余接口默认要求已登录（会话或携带 `Authorization: Bearer <token>` 头）。

## ✨ 功能亮点
- 用户生命周期：注册 ➜ 邮箱激活 ➜ 登录/注销 ➜ 用户资料维护
- 内容互动：帖子创建、分页检索、分类筛选、点赞与浏览量统计
- 回复系统：帖子回复、回复点赞、幂等点赞判定
- 统一响应：所有接口返回统一结构 `code/message/data`
- 安全机制：JWT（HS512）+ 服务端 Session，认证失败返回 401/403
- 运维能力：Actuator 健康检查、Redis 缓存支持

## 🛠 技术栈
- Java 17
- Spring Boot 3.4.x（web、actuator、mail、data-redis）
- MyBatis 与 MyBatis-Plus（分页、Wrapper 与通用 CRUD）
- MySQL（`mysql-connector-j`）
- Redis（会话/缓存，`spring-boot-starter-data-redis`）
- JSON Web Token：JJWT 0.12.x
- Jakarta Validation + Hibernate Validator
- Swagger annotations（接口文档扩展）

## 📁 仓库结构速览
- `src/main/java/xyz/luotao/v1`
  - `controller`：登录、注册、帖子、回复、用户、角色等业务接口
  - `config`：认证拦截器、Web CORS 与 MyBatis-Plus 配置
  - `common`：统一响应封装
  - `util`：`JwtUtil` 等工具类
- `src/main/resources`
  - `application-example.properties`（示例配置）
  - `sql/leafone.sql`：数据库初始化脚本
  - `templates/`、`static/`：保留给后续静态资源
- `test/java/xyz/luotao/v1`：Spring Boot 集成测试

## ⚙️ 环境准备
1. JDK 17 或更高版本（需启用 `JAVA_HOME`）
2. Maven 3.9+
3. 可访问的 MySQL 数据库实例
4. 可用的 Redis 实例（默认通过 `spring.data.redis.url` 配置）
5. SMTP 邮箱服务（用于发送激活邮件）

> 📌 建议在开发/测试环境使用 `.env`、环境变量或外部化配置，而非直接修改仓库内的明文凭据。

## 🔧 配置说明
1. 复制 `src/main/resources/application-example.properties` 为 `application-local.properties`（或 `application.properties`）
2. 修改以下关键配置：
   - `spring.datasource.*`：数据库连接信息
   - `spring.mail.*`：SMTP 凭据与端口
   - `spring.data.redis.url`：Redis 连接串（格式 `redis://[:password@]host:port`）
   - `leafone.jwt.secret` 等自定义秘钥（如在配置中引入）
3. 如果需要覆盖配置，可使用命令行参数或环境变量（例如 `SPRING_DATASOURCE_URL`）。

## 🚀 快速开始
1. **初始化数据库**：导入 `src/main/resources/sql/leafone.sql` 到目标 MySQL 数据库，预置基础表结构与字典数据。
2. **构建应用**：
   ```bash
   mvn clean package
   ```
3. **运行**：
   ```bash
   java -jar target/v1-0.0.1-SNAPSHOT.jar
   ```
   或在 IDE 中运行 `V1Application` 主类。
4. **访问**：默认监听 `http://localhost:8080`。

## 🔐 认证与会话流程
1. 登录成功后：
   - 服务端 Session 中保存当前用户信息（键名 `user`）
   - 返回携带用户 ID 作为 Subject 的 JWT，默认 7 天过期
2. 后续请求可选择：
   - 复用同一浏览器会话（携带 Cookie）
   - 添加 `Authorization: Bearer <token>` 请求头
3. 未携带有效凭据访问受保护接口时返回 `401 Unauthorized`；越权访问返回 `403 Forbidden`。

## 📡 接口速览
以下仅列举部分核心接口，详细字段请参阅源码中的 DTO：

- 认证
  - `POST /login`（公开）：`UserDto { email, passwordHash }`
  - `POST /logout`
- 注册与激活
  - `POST /sign`（公开）：注册并发送激活邮件
  - `GET /sign/{email}/{token}`（公开）：邮箱激活
- 用户模块（`/api/user`）
  - `GET /api/user/{id}`：仅允许查询自己的信息
  - `POST /api/user/dormitory`：新增/更新宿舍信息
  - `PUT /api/user`、`DELETE /api/user/{id}`：更新/删除用户
- 帖子模块（`/api/post`）
  - `POST /api/post/create`：创建帖子（需权限）
  - `GET /api/post/findByPage?pageNo=1`：分页查询（9 条/页）
  - `GET /api/post/findByCategoryPage?categoryId=&pageNo=1`：分类分页（6 条/页）
  - `PUT /api/post/like/{postId}`：帖子点赞（幂等）
  - `POST /api/post/view/{postId}`：浏览量 +1
- 回复模块（`/api/replies`）
  - `POST /api/replies/add`：新增回复
  - `POST /api/replies/like?replyId=`：回复点赞（幂等）
  - `GET /api/replies?postId=`：按帖子 ID 查询回复

## 🌐 CORS 策略
- 默认允许来源：`http://127.0.0.1:5500`（可在 `WebConfig` 中调整）
- 允许方法：`GET`、`POST`、`PUT`、`DELETE`、`OPTIONS`
- 允许凭据：支持携带 Cookie

## ✅ 构建与测试
- 构建发布：`mvn clean package`
- 单元/集成测试：`mvn test`
- 核心测试类：`xyz.luotao.v1.V1ApplicationTests`

## ❓ 常见问题
- **401 未认证**：未登录或 token 过期，或接口被拦截器保护
- **403 禁止访问**：尝试访问他人资源（如 `GET /api/user/{id}`）
- **邮件发送失败**：检查 SMTP 配置、端口与安全策略
- **Redis 连接异常**：确认 `spring.data.redis.url` 与网络连通性

## 🔐 安全建议
- 避免提交真实数据库/邮箱/Redis 凭据到公共仓库
- 为 JWT 秘钥使用足够长度的随机字符串，并通过环境变量注入
- 若部署在公网环境，请结合 HTTPS、同源策略与更严格的 CORS 配置

---
更多 DTO 字段说明及业务细节可直接查阅源码注释；如需自动化接口文档，建议引入 Springdoc/OpenAPI 进一步完善。
