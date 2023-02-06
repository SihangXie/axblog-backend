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

package cn.axblog.service.impl;

import static cn.axblog.common.constant.RedisConstant.CACHE_WEBSITE_LOGIN_KEY;

import cn.axblog.common.exception.CustomException;
import cn.axblog.domain.entity.LoginUser;
import cn.axblog.domain.entity.User;
import cn.axblog.domain.vo.UserInfoVo;
import cn.axblog.domain.vo.WebsiteUserLoginVo;
import cn.axblog.service.LoginService;
import cn.axblog.utils.JWTUtil;
import cn.axblog.utils.RedisCacheUtil;
import cn.hutool.core.bean.BeanUtil;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public WebsiteUserLoginVo login(User user) {
        // 1.把前端传来的用户名和密码封装成了Authentication接口的实现类UsernamePasswordAuthenticationToken对象，等待被认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            user.getUserName(), user.getPassword());

        // 2.获取AuthenticationManager对象，调用其authenticate()方法进行认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 3.如果认证不通过，则抛出异常给出相应提示
        if (Objects.isNull(authenticate)) {
            throw new CustomException("登录失败");
        }

        // 4.如果认证通过，则使用userId生成一个JWT，放入JSONResult返回给前端
        // 4.1 authenticate对象的getPrincipal()方法返回的是UserDetails接口的实现类LoginUser对象
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 4.2 调用loginUser对象的getUser()方法后，就可以获取用户ID，注意转换成String字符串类型
        String userId = loginUser.getUser().getId().toString();
        // 4.3 调用JWT工具类生成token，payload是userId
        String token = JWTUtil.createJWT(userId);

        // 5.把这个JWT缓存进Redis，userId为key
        // 5.1 结合前缀构造Redis的Key
        String key = CACHE_WEBSITE_LOGIN_KEY + userId;
        // 5.2 写入Redis，有效期是永久，Value是loginUser对象
        redisCacheUtil.set(key, loginUser);

        // 6.把JWT token和用户信息封装成WebsiteUserLoginVo对象返回
        // 6.1 通过loginUser.getUser()获取User对象，再将其属性值复制给UserInfoVo对象
        UserInfoVo userInfoVo = BeanUtil.copyProperties(loginUser.getUser(), UserInfoVo.class);
        // 6.2 创建WebsiteUserLoginVo对象
        return new WebsiteUserLoginVo(token, userInfoVo);
    }

}
