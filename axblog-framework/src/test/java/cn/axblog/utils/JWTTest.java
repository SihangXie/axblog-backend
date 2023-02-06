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
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTTest {

    // JWT有效期：设置为24小时
    private final Long time = 1000 * 60 * 60 * 24L;
    // 数字签名信息
    private final String signature = "admin";

    @Test
    public void testJwtEncode() {
        // 1.创建JwtBuilder对象，用于创建JWT对象
        JwtBuilder jwtBuilder = Jwts.builder();
        // 2.使用JwtBuilder按照JWT三部分结构依次设置
        String jwtToken = jwtBuilder
            // 2.1 设置Header头信息
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "HS256")
            // 2.2 设置数据负载payload
            .claim("username", "Tom")
            .claim("role", "admin")
            .setSubject("admin-test")   // 主题
            .setExpiration(new Date(System.currentTimeMillis() + time)) // 过期时间
            .setId(UUID.randomUUID().toString())
            // 2.3 设置数字签名signature
            .signWith(SignatureAlgorithm.HS256, signature)
            // 2.4 将三部分拼接起来
            .compact();
        // 3.加密完成，输出JWT token
        System.out.println(jwtToken);
    }

    @Test
    public void testJwtDecode() {
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IlRvbSIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2NzU2Njk2MDIsImp0aSI6IjdiMGVlZDFlLTBiZjMtNDVlZS04NTgwLTIxOTlhMDk2ZWEzNyJ9.VyI9Ttdb-0AmDcC93KnG4gQTzroCI3sv8SNKlT29a8I";
        // 1.创建JWT解析器对象
        JwtParser jwtParser = Jwts.parser();
        // 2.先用数字签名验证，通过后再解析token字符串，返回是一个集合
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(jwtToken);
        // 3.把集合提取出来
        Claims claims = claimsJws.getBody();
        // 4.从claims获取你想要的信息
        System.out.println(claims.get("username"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getSubject());
        System.out.println(claims.getExpiration());
        System.out.println(claims.getId());
    }

    @Test
    public void testSecretKeySpec() {
        // 数字签名明文
        final String signature = "Sihang";
        // Base64加密
        byte[] encodedSignature = Base64.getEncoder().encode(signature.getBytes(StandardCharsets.UTF_8));
        // 生成SecretKey密钥
        SecretKeySpec key = new SecretKeySpec(encodedSignature, 0, encodedSignature.length, "AES");
        // Base64解密
        byte[] decodedSignature = Base64.getDecoder().decode(encodedSignature);
    }

    @Test
    public void testJWTUtil() {
//        System.out.println(JWTUtil.createJWT("15018119254"));
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzMjdmNmM5MTFmNDA0ZGM2OWQ2N2EwNzhiNWFhZjEwYiIsInN1YiI6IjE1MDE4MTE5MjU0IiwiZXhwIjoxNjc1NzQ5NTc4LCJpYXQiOjE2NzU2NjMxNzh9.-Jgl86uKynXRoKJUwBgynNhIOfkFycZTPQA29DyfGYY";
        Claims claims = JWTUtil.parseJWT(token);
        String phone = claims.getSubject();
        System.out.println(phone);
    }

}
