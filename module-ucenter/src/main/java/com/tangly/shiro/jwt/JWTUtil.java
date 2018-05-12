package com.tangly.shiro.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author tangly
 */
@Slf4j
public class JWTUtil {


    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) throws AuthenticationException {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(secret);
        } catch (UnsupportedEncodingException e) {
            log.error("编码异常，");
        }
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username", username).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
        } catch (TokenExpiredException e){
            log.info("token过期 {}" , token );
            throw new AuthenticationException("token已过期");
        } catch (JWTVerificationException e) {
            log.error("token非法" , token);
            throw new AuthenticationException("token已失效");
        }
        return true;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @param expireDate token有效日期
     * @return 加密的token
     */
    public static String sign(String username, String secret , Date expireDate) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
