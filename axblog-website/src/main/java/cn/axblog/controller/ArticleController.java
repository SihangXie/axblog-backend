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

package cn.axblog.controller;


import cn.axblog.common.JSONResult;
import cn.axblog.domain.vo.ArticleDetailVo;
import cn.axblog.domain.vo.ArticleListVo;
import cn.axblog.service.impl.ArticleServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Article)表控制层
 *
 * @author makejava
 * @since 2022-12-22 21:47:56
 */
@RestController
@RequestMapping("/blog/article")
public class ArticleController {

    /**
     * 服务对象
     */
    @Autowired
    private ArticleServiceImpl articleService;

    @GetMapping("/page")
    public JSONResult<Page<ArticleListVo>> page(Long page, Long pageSize, String name, Long categoryId) {
        return JSONResult.success(articleService.getPage(page, pageSize, name, categoryId));
    }

    @GetMapping("/{id}")
    public JSONResult<ArticleDetailVo> getArticleDetail(@PathVariable Long id) {
        return JSONResult.success(articleService.getArticleDetail(id));
    }
}

