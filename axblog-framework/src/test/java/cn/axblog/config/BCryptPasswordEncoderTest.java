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

package cn.axblog.config;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class BCryptPasswordEncoderTest {

    @Autowired

    @Test
    public void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = passwordEncoder.encode("ay123456");
        String encodedPassword2 = passwordEncoder.encode("xsh123456");
        System.out.println(encodedPassword);
        System.out.println(encodedPassword2);
        // 校验
//        boolean isMatch = passwordEncoder.matches("ay123456",
//            "$2a$10$8H0a2J2sH0pF9RODN.u/k.YSedkvo5QT57mqtqxQIjCZtpRbBhknK");
//        boolean isMatch2 = passwordEncoder.matches("ay123456",
//            "$2a$10$oD73ALotomxrEFuZFzRruOs17f8QNMnInl4d3CRd72dv3aw2LRd.S");
//        System.out.println(isMatch);
//        System.out.println(isMatch2);
    }

}
