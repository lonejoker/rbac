package com.xiaobai.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:33
 * @Version 1.0
 * @ClassName JwtUtil
 * @Description 类方法说明：jwt工具类
 */
public class JwtUtil {
    //    有效时间
    public static final Long Jwt_TTL = 60 * 60 * 1000L;
    //    设置秘钥明文
    public static final String Jwt_KEY = "xiaobai";

    public static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname createJWT
     * @Description 类方法说明：token要存放的数据（json格式）
     * @Return 返回值：java.lang.String
     * @Params java.lang.String subject
     * @Date 2022/2/3 下午 23:10
     */
    public static String createJWT(String subject) {
        //设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname createJWT
     * @Description 类方法说明：token要存放的数据（json格式），ttlMillis：超时时间
     * @Return 返回值：java.lang.String
     * @Params java.lang.String subject
     * @Params java.lang.Long ttlMillis
     * @Date 2022/2/3 下午 23:12
     */
    public static String createJWT(String subject, Long ttlMillis) {
        //设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.Jwt_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid) // 唯一的id
                .setSubject(subject) // 主题，可以是json数据
                .setIssuer("xiaobai") // 签发者
                .setIssuedAt(now) // 签发时间
                .signWith(signatureAlgorithm, secretKey) // 使用HS256对称加密算法签名，第二个参数是秘钥
                .setExpiration(expDate);
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname createJWT
     * @Description 类方法说明：创建token
     * @Return 返回值：java.lang.String
     * @Params java.lang.String id
     * @Params java.lang.String subject
     * @Params java.lang.Long ttlMillis
     * @Date 2022/2/3 下午 23:21
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    public static void main(String[] args) {
        String token = "";
        Claims claims = parseJWT(token);
        System.out.println(claims);
    }


    /**
     * @author 终于白发始于青丝
     * @Methodname generalKey
     * @Description 类方法说明：生成加密后的秘钥：secretKey
     * @Return 返回值：javax.crypto.SecretKey
     * @Params
     * @Date 2022/2/3 下午 23:23
     */
    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JwtUtil.Jwt_KEY);
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }

    /**
     * @author 终于白发始于青丝
     * @Methodname parseJWT
     * @Description 类方法说明：解析
     * @Return 返回值：io.jsonwebtoken.Claims
     * @Params java.lang.String token
     * @Date 2022/2/3 下午 23:25
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}