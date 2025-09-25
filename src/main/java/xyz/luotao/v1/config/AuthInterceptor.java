// java
// 文件: `src/main/java/xyz/luotao/v1/config/AuthInterceptor.java`
package xyz.luotao.v1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.luotao.v1.common.ResponseMessage;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行非控制器（比如静态资源）
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("user") != null;

        String auth = request.getHeader("Authorization"); // 建议形如: Bearer <token>
        boolean hasToken = auth != null && !auth.isBlank();

        if (loggedIn || hasToken) {
            return true;
        }

        // 未登录统一返回 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        ResponseMessage body = new ResponseMessage(401, "未登录，拒绝访问", null);
        new ObjectMapper().writeValue(response.getWriter(), body);
        return false;
    }
}
