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

package cn.axblog.domain.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2023-01-06 10:24:50
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    //用户ID
    private Long id;
    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;
    //用户类型：0普通用户；1管理员用户
    private String type;
    //账号状态：0正常；1停用
    private String status;
    //邮箱
    private String email;
    //手机号
    private String phonenumber;
    //用户性别：0男；1女；2保密
    private String sex;
    //头像
    private String avatar;
    //文章分类创建用户ID
    private Long createUser;
    //文章分类创建时间
    private LocalDateTime createTime;
    //文章分类修改用户ID
    private Long updateUser;
    //文章分类修改时间
    private LocalDateTime updateTime;
    //删除标志：0未删除
    private Integer delFlag;

}

