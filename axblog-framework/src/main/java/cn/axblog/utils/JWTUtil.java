/*
 * Copyright (c) 2022 Axblog's Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.axblog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JWTUtil {

    // JWT token的有效时间为24h
    private static final Long JWT_TTL = 1000 * 60 * 60 * 24L;
    // 数字签名SIGNATURE明文
    private static final String SIGNATURE = "SihangXie";


    /**
     * 创建JwtBuilder
     *
     * @param subject   数据负载payload，可以使用JSON格式
     * @param ttlMillis JWT token过期时间，不指定默认为24h
     * @return JwtBuilder，JWT token的构建器
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis) {
        // 1.如果调用者没有指定JWT token的有效期，就采用默认的24h
        if (Objects.isNull(ttlMillis)) {
            ttlMillis = JWT_TTL;
        }
        // 2.按照JWT三部分结构依次设置
        return Jwts.builder()
            // 2.1 设置Header头信息
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "HS256")
            // 2.2 设置数据负载payload
            .setId(getUUID())    // token ID
            .setSubject(subject)   // 主题，可以是JSON数据
            .setExpiration(new Date(System.currentTimeMillis() + ttlMillis)) // 过期时间
            .setIssuedAt(new Date(System.currentTimeMillis()))  // 签发时间
            // 2.3 设置数字签名SIGNATURE
            .signWith(SignatureAlgorithm.HS256, generalKey(SIGNATURE));
    }

    /**
     * 根据所给的数据负载payload创建JWT token，默认有效时间为24h
     *
     * @param subject 主题作为数据负载payload，可以是JSON数据
     * @return 返回JWT token字符串
     */
    public static String createJWT(String subject) {
        return getJwtBuilder(subject, null).compact();
    }

    /**
     * 根据所给的数据负载payload创建JWT token，自定义有效时间
     *
     * @param subject   主题作为数据负载payload，可以是JSON数据
     * @param ttlMillis 有效时间，单位为毫秒
     * @return 返回JWT token字符串
     */
    public static String createJWT(String subject, Long ttlMillis) {
        return getJwtBuilder(subject, ttlMillis).compact();
    }

    public static Claims parseJWT(String jwtToken) {
        // 1.创建JWT解析器对象
        JwtParser jwtParser = Jwts.parser();
        // 2.先用数字签名验证，通过后再解析token字符串，返回是一个集合
        Jws<Claims> claimsJws = jwtParser.setSigningKey(generalKey(SIGNATURE)).parseClaimsJws(jwtToken);
        // 3.把集合提取出来
        return claimsJws.getBody();
    }

    /**
     * 采用对称加密算法AES加密数字签名
     *
     * @param str 待加密的数字签名
     * @return 经过加密后的数字签名
     */
    public static SecretKey generalKey(String str) {
        // Base64加密数字签名SIGNATURE
        byte[] encodedSignature = Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8));
        // 根据Base64加密的SIGNATURE生成SecretKey密钥，加密算法采用对称加密AES算法
        return new SecretKeySpec(encodedSignature, 0, encodedSignature.length, "AES");
    }

    /**
     * 创建没有短横线"-"间隔的UUID
     *
     * @return 返回没有短横线"-"间隔的UUID字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
