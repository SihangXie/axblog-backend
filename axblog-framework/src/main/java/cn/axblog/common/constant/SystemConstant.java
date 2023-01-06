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

package cn.axblog.common.constant;

public class SystemConstant {

    // 文章状态为草稿
    public static final Integer ARTICLE_STATUS_DRAFT = 1;
    // 文章状态为已发布文章
    public static final Integer ARTICLE_STATUS_NORMAL = 0;
    // 文章类型为草稿
    public static final Integer ARTICLE_TYPE_DRAFT = 2;
    // 文章类型为文章
    public static final Integer ARTICLE_TYPE_NORMAL = 1;
    // 文章为置顶
    public static final Integer ARTICLE_TOP = 1;
    // 文章为非置顶
    public static final Integer ARTICLE_NOT_TOP = 0;
    // 文章为允许评论
    public static final Integer ARTICLE_ALLOW_COMMENT = 1;
    // 文章为禁止评论
    public static final Integer ARTICLE_BAN_COMMENT = 0;
    // 文章分类状态为禁用
    public static final Integer CATEGORY_STATUS_BAN = 1;
    // 文章分类状态为正常
    public static final Integer CATEGORY_STATUS_NORMAL = 0;
}
