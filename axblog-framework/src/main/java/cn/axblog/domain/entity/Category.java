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
 * (Category)表实体类
 *
 * @author makejava
 * @since 2023-01-04 19:46:02
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Long id;
    //文章分类所属用户ID
    private Long userId;
    //文章分类名称
    private String name;
    //父分类ID，如果没有父分类就为-1
    private Long pid;
    //分类描述
    private String description;
    //状态：0正常；1禁用
    private String status;
    //缩略图
    private String thumbnail;
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

