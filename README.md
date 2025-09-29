# LeafOneForum API 🚀

让校园社区的互动更轻盈、更有趣！LeafOneForum 是一个面向校园场景的轻量级论坛后端，包办用户注册激活、登录注销、帖子互动、回复点赞、角色权限等核心流程。我们基于 Spring Boot 3 打造，默认监听 `8080` 端口，并通过 Session + JWT 双通道满足同源部署与前后端分离的需求。

> **访问策略一图流：** 除 `/login`、`/sign`、`/sign/**`、静态资源和公开文档外，其余接口都需要会话或 `Authorization: Bearer <token>` 才能畅通无阻。

## 🎯 为什么选择 LeafOneForum？
- **完整用户旅程**：注册 ➜ 邮箱激活 ➜ 登录/注销 ➜ 资料维护，一条龙无缝衔接。
- **帖子玩起来**：发帖、分页、分类、点赞、浏览量统计统统安排上。
- **回复也精彩**：回复发布、点赞、幂等校验，沉浸式互动体验。
- **统一返回体**：所有接口使用 `code/message/data` 结构，前端对接不再抓狂。
- **安全护航**：JWT HS512 + 服务端 Session 双保险，认证失败直给 401/403。
- **运维友好**：Actuator 健康检查、Redis 缓存加速，线上守护轻松无负担。

## 🧰 技术清单
- Java 17
- Spring Boot 3.4.x（web、actuator、mail、data-redis）
- MyBatis & MyBatis-Plus（分页、Wrapper、通用 CRUD）
- MySQL（`mysql-connector-j`）
- Redis（`spring-boot-starter-data-redis`）
- JWT：JJWT 0.12.x
- Jakarta Validation + Hibernate Validator
- Swagger 注解（方便扩展文档）

## � 项目地图
- `src/main/java/xyz/luotao/v1`
  - `controller`：登录、注册、帖子、回复、用户、角色接口全员在列。
  - `config`：认证拦截器、CORS、MyBatis-Plus 配置集合地。
  - `common`：统一响应、常用封装都在这儿。
  - `util`：例如 `JwtUtil` 等工具类。
- `src/main/resources`
  - `application-example.properties`：配置示例直接抄。
  - `sql/leafone.sql`：数据库初始化脚本一键导入。
  - `templates/`、`static/`：给后续前端资源预留的空间。
- `test/java/xyz/luotao/v1`：Spring Boot 集成测试随时待命。

## 🧑‍💻 本地准备工作
1. 安装 JDK 17+ 并配置好 `JAVA_HOME`。
2. 准备 Maven 3.9+。
3. 保证 MySQL、Redis、SMTP 邮箱服务可用。
4. 推荐使用环境变量或外部配置文件存放敏感凭据，别直接写进仓库哦。

## � 配置三步走
1. 复制 `src/main/resources/application-example.properties` 为 `application-local.properties`（或 `application.properties`）。
2. 填好这些关键项：
   - `spring.datasource.*`：数据库地址与账号。
   - `spring.mail.*`：SMTP 主机、端口和凭据。
   - `spring.data.redis.url`：Redis 连接串（`redis://[:password@]host:port`）。
   - `leafone.jwt.secret` 等自定义秘钥。
3. 如果需要覆盖配置，使用启动参数或环境变量（如 `SPRING_DATASOURCE_URL`）。

## ⚡️ 快速起飞
1. **初始化数据库**：导入 `src/main/resources/sql/leafone.sql`，表结构和字典数据一步到位。
2. **构建项目**：
   ```bash
   mvn clean package
   ```
3. **运行服务**：
   ```bash
   java -jar target/v1-0.0.1-SNAPSHOT.jar
   ```
   或直接在 IDE 中启动 `V1Application`。
4. **开始访问**：默认地址 `http://localhost:8080`，即刻登录体验。

## � 认证小剧场
1. 登录成功后：
   - Session 中记录当前用户信息（键名 `user`）。
   - 同时返回一个 Subject 为用户 ID 的 JWT（默认 7 天到期）。
2. 后续请求任选其一：
   - 同浏览器继续访问（自动携带 Cookie）。
   - 在请求头添加 `Authorization: Bearer <token>`。
3. 没带凭证访问受保护接口会得到 `401 Unauthorized`，越权访问则收 `403 Forbidden`。

## 📡 接口一览表
- **认证**
  - `POST /login`（公开）：`UserDto { email, passwordHash }`
  - `POST /logout`
- **注册与激活**
  - `POST /sign`（公开）：注册并发送激活邮件
  - `GET /sign/{email}/{token}`（公开）：邮箱激活链接
- **用户模块**（`/api/user`）
  - `GET /api/user/{id}`：只能查看自己的资料
  - `POST /api/user/dormitory`：新增或更新宿舍信息
  - `PUT /api/user`、`DELETE /api/user/{id}`：更新、删除用户
- **帖子模块**（`/api/post`）
  - `POST /api/post/create`：发帖（需权限）
  - `GET /api/post/findByPage?pageNo=1`：分页查询（每页 9 条）
  - `GET /api/post/findByCategoryPage?categoryId=&pageNo=1`：分类分页（每页 6 条）
  - `PUT /api/post/like/{postId}`：点赞（自带幂等）
  - `POST /api/post/view/{postId}`：浏览量 +1
- **回复模块**（`/api/replies`）
  - `POST /api/replies/add`：新增回复
  - `POST /api/replies/like?replyId=`：回复点赞（支持幂等）
  - `GET /api/replies?postId=`：按帖子 ID 查询回复

## � CORS 设置
- 默认允许来源：`http://127.0.0.1:5500`（可在 `WebConfig` 中自行调整）。
- 允许方法：`GET`、`POST`、`PUT`、`DELETE`、`OPTIONS`。
- 允许携带凭据：Cookie 照搬不误。

## ✅ 构建 & 测试
- 构建：`mvn clean package`
- 测试：`mvn test`
- 重点测试类：`xyz.luotao.v1.V1ApplicationTests`

## 🧩 常见疑问解答
- **401 未认证**：可能是没登录或 token 过期，或者被拦截器挡下。
- **403 禁止访问**：试图查看他人资源（如 `GET /api/user/{id}`）。
- **邮件发送失败**：检查 SMTP 地址、端口和授权码是否正确。
- **Redis 连接异常**：确认 `spring.data.redis.url` 配置和网络连通性。

## � 安全贴士
- 别把真实数据库、邮箱、Redis 凭据 commit 到仓库。
- 为 JWT 秘钥准备足够长度的随机字符串，并使用环境变量注入。
- 公网部署时配合 HTTPS、更严格的 CORS 与安全策略使用。

---

想看更细节的 DTO 字段和业务流程？直接 dive into 源码就能找到答案。如果想要自动化接口文档，欢迎引入 Springdoc/OpenAPI 扩展，我们也在持续探索中。祝你使用愉快！🌟
