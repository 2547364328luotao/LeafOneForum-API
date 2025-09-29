package xyz.luotao.v1.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class    JwtUtil {
    // 七天过期
    public static final long JWT_EXPIRE = 7 * 24 * 60 * 60 * 1000;
    public static final String JWT_secret = "luotaoaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    // 必须是 SecretKey（HMAC）
    private static final SecretKey KEY = Keys.hmacShaKeyFor(JWT_secret.getBytes(StandardCharsets.UTF_8));

    // 生成 token（JJWT 0.12+ API）
    public static String generateToken(String encipher_str) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + JWT_EXPIRE);
        return Jwts.builder()
                .subject(encipher_str)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(KEY, Jwts.SIG.HS512)
                .compact();
    }

    // 解析 token（JJWT 0.12+ API）
    public static String parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
