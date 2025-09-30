package xyz.luotao.v1.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.luotao.v1.config.security.ForumUserDetailsService;
import xyz.luotao.v1.config.security.JwtAuthenticationFilter;

@Configuration // 声明这是配置类（等价于一个 @Bean 容器定义）
@EnableWebSecurity // 启用 Spring Security Web 支持
@EnableMethodSecurity(prePostEnabled = true) // 启用方法级别安全注解，如 @PreAuthorize/@PostAuthorize
public class SecurityConfig {

    // 注入自定义的用户细节服务，用于认证时按用户名加载用户与其权限
    private final ForumUserDetailsService userDetailsService;

    public SecurityConfig(ForumUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean // 密码编码器：BCrypt 强哈希算法，存储与校验用户密码
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // JWT 认证过滤器：从请求头提取并校验 JWT，成功后把认证信息放入 SecurityContext
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userDetailsService);
    }

    @Bean // AuthenticationManager：认证入口（供登录或其他认证流程调用）
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // 通过 HttpSecurity 共享对象拿到 AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        // 指定用户来源（UserDetailsService）和密码编码器
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean // 安全过滤器链的主配置
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 因为使用 JWT 做无状态认证，关闭 CSRF（表单/会话型应用才需要）
            .csrf(csrf -> csrf.disable())
            // 无状态会话：不创建/使用 HTTP Session
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 授权规则：按请求路径放行或要求认证
            .authorizeHttpRequests(authz -> authz
                // 允许匿名访问的端点（登录、注册、文档、健康检查、静态资源等）
                .requestMatchers("/login",
                                 "/sign",
                                 "/sign/**",
                                 "/error",
                                 "/actuator/**",
                                 "/swagger-ui/**",
                                 "/v3/api-docs/**",
                                 "/favicon.ico",
                                 "/public/**",
                                 "/static/**",
                                 "/redis/**").permitAll()
                // 允许 Spring Boot 常见静态资源路径（/css/**, /js/**, /images/** 等）
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // 其他请求都需要已认证
                .anyRequest().authenticated()
            )
            // 启用 HTTP Basic（可用于简单接口测试；生产可考虑关闭或仅用于特定端点）
            .httpBasic(Customizer.withDefaults());

        // 在用户名密码过滤器之前加入 JWT 过滤器，确保优先从 Token 完成认证
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

