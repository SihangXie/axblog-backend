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
 * (Article)表实体类
 *
 * @author makejava
 * @since 2022-12-22 21:17:51
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private Long id;
    //文章所属用户ID
    private Long userId;
    //标题
    private String title;
    //正文
    private String content;
    //文章类型：1文章；2草稿
    private String type;
    //摘要
    private String summary;
    //文章所属分类ID
    private Long categoryId;
    //缩略图
    private String thumbnail;
    //是否置顶：0否；1是
    private String isTop;
    //状态：0已发布；1草稿
    private String status;
    //评论数
    private Integer commentCount;
    //访问量
    private Long viewCount;
    //是否允许评论：1是；0否
    private String isComment;
    //文章创建用户ID
    private Long createUser;
    //文章创建时间
    private LocalDateTime createTime;
    //文章修改用户ID
    private Long updateUser;
    //文章修改时间
    private LocalDateTime updateTime;
    //删除标志：0未删除；1已删除
    private Integer delFlag;

}

