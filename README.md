# LeafOneForum API

简洁的校园论坛后端服务。基于 Spring Boot 3 + MyBatis(-Plus) + MySQL，采用会话与 JWT 结合的鉴权方案，支持注册与邮箱激活、登录/注销、用户信息、帖子分页检索、点赞与浏览量、回复与点赞，以及基础角色控制。

- 默认端口：8080（未在配置中显式修改）
- 全局拦截：除 /login、/sign、/sign/**、静态资源与文档外，其余接口默认需登录（会话或携带 Authorization 头）

## 技术栈
- Java 17、Spring Boot 3.4.x（web、actuator、mail）
- MyBatis / MyBatis-Plus（分页等）
- MySQL（mysql-connector-j）
- JJWT 0.12.x（HS512）
- Jakarta Validation（参数校验）

## 快速开始
1) 环境要求
- JDK 17 及以上
- Maven 3.9+
- 可用的 MySQL 实例与 SMTP 邮箱服务

2) 配置
- 请勿在生产环境直接使用仓库中明文的数据库与邮箱配置；建议基于 application-example.properties 复制为 application.properties，并按实际环境修改。
- 可通过环境变量或外部化配置覆盖 spring.datasource.* 与 spring.mail.* 等属性。

3) 启动
- 使用 Maven 构建并运行可执行 Jar，或直接通过 IDE 运行 V1Application。
- 启动后服务将监听在 http://localhost:8080。

## 认证与会话
- 登录成功后，服务会：
  - 在服务端 session 中保存当前用户（键名 user）
  - 生成一个 JWT（主题为用户 ID，默认 7 天过期，算法 HS512）返回给前端
- 后续请求可选择其一：
  - 继续复用同一会话（同源 + 携带 Cookie）
  - 使用请求头 Authorization: Bearer <token>
- 未登录或未携带有效 token 的受保护接口将返回 401。

## 统一响应结构
所有接口统一返回：
- code: 整型状态码（通常 200 成功；401/403/404/500 等表示异常）
- message: 文本信息
- data: 业务数据载荷（可为对象、分页结果或 null）

示例：
{
  "code": 200,
  "message": "success",
  "data": { ... }
}

## API 速览
说明仅摘取主要接口，字段以源码 DTO 为准（UserDto、PostDto、RepliesDto）。除显式标注外，均为受保护接口。

- 认证
  - POST /login（公开）
    - 入参：UserDto { email, passwordHash }
    - 出参：{ token, sessionId }
  - POST /logout

- 注册与激活
  - POST /sign（公开）：使用 UserDto { nickname, email, passwordHash } 注册；服务端发送激活邮件
  - GET /sign/{email}/{token}（公开）：邮箱激活

- 用户（/api/user）
  - GET /api/user：查询所有用户
  - GET /api/user/{id}：仅允许读取自己的信息，否则 403
  - POST /api/user：新增用户（表单或 JSON 绑定到 User）
  - POST /api/user/dormitory：新增/更新宿舍信息（绑定到 User）
  - PUT /api/user：更新用户信息（JSON 绑定到 User）
  - DELETE /api/user/{id}：删除用户

- 帖子（/api/post）
  - POST /api/post/create：创建帖子（PostDto），需具备发帖权限
  - GET /api/post/findByPage?pageNo=1：分页查询（每页 9 条）
  - GET /api/post/findByCategoryPage?categoryId=&pageNo=1：分类分页查询（每页 6 条）
  - GET /api/post/{id}：按 ID 获取帖子
  - PUT /api/post/like/{postId}：帖子点赞（幂等处理：已点过返回成功提示）
  - POST /api/post/view/{postId}：帖子浏览量 +1

- 回复（/api/replies）
  - POST /api/replies/add：新增回复，入参 RepliesDto { postId, content }
  - POST /api/replies/like?replyId=：回复点赞（幂等）
  - GET /api/replies/all：查询所有回复
  - GET /api/replies?postId=：按帖子 ID 查询回复

## CORS
- 允许来源：http://127.0.0.1:5500（可在 WebConfig 中调整）
- 允许方法：GET、POST、PUT、DELETE、OPTIONS
- 支持凭据（cookies）

## 构建与测试
- 使用 Maven 标准流程进行构建与打包。
- 集成测试类：xyz.luotao.v1.V1ApplicationTests（可通过 mvn test 运行）。

## 常见问题
- 401 未认证：
  - 未登录或 Authorization 头缺失/Token 过期
  - 接口被拦截器保护（见 WebConfig.addInterceptors）
- 403 禁止：尝试访问他人数据（如 GET /api/user/{id}）
- 激活邮件发送失败：注册成功但邮件失败时会返回 500 提示，请检查 SMTP 配置

## 安全与配置建议
- 切勿在公开仓库提交真实数据库与邮箱账号口令；生产部署请使用环境变量或配置中心
- 建议为 JWT 秘钥使用足够长度的随机字符串，并通过环境变量注入
- 为前后端分离场景合理配置 CORS 与 Cookie 属性（SameSite、Secure 等）

## 目录结构（节选）
- src/main/java/xyz/luotao/v1
  - controller：Login、Sign、User、Post、Replies、UserRole
  - config：AuthInterceptor、WebConfig
  - common：ResponseMessage
  - util：JwtUtil
- src/main/resources：application.properties（请覆盖为你的环境配置）

---
如需更详细的字段说明与示例，请参阅源码中的 DTO 与 Controller 注释，或补充 OpenAPI 文档。
