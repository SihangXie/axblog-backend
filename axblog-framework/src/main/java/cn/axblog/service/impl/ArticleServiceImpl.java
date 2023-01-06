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

import cn.axblog.common.constant.SystemConstant;
import cn.axblog.domain.entity.Article;
import cn.axblog.domain.entity.Category;
import cn.axblog.domain.vo.ArticleDetailVo;
import cn.axblog.domain.vo.ArticleListVo;
import cn.axblog.mapper.ArticleMapper;
import cn.axblog.service.ArticleService;
import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Article)表服务实现类
 *
 * @author makejava
 * @since 2022-12-22 21:31:05
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * 获取博客文章分页列表
     *
     * @param currentPage 当前页码
     * @param pageSize    每页显示文章数量
     * @param name        用于根据文章标题进行搜索
     * @return
     */
    @Override
    public Page<ArticleListVo> getPage(Long currentPage, Long pageSize, String name, Long categoryId) {
        Page<Article> articlePage = new Page<>(currentPage, pageSize);
        Page<ArticleListVo> articleVoPage = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();

        // 根据标题查询
        lqw.like(Strings.isNotEmpty(name), Article::getTitle, name);
        // 根据文章分类ID查询
        lqw.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        // 文章状态必须是以发布
        lqw.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
        // 文章类型必须不能是草稿
        lqw.eq(Article::getType, SystemConstant.ARTICLE_TYPE_NORMAL);
        // 先按文章是否置顶排序，再按创建时间降序
        lqw.orderByDesc(Article::getIsTop).orderByDesc(Article::getCreateTime);
        // 调用MP进行分页查询
        page(articlePage, lqw);

        // 除了records字段其他都拷贝
        BeanUtils.copyProperties(articlePage, articleVoPage, "records");
        // 获取原来的records字段的集合
        List<Article> articleRecords = articlePage.getRecords();
        // 使用Hutool工具包进行硬拷贝
        List<ArticleListVo> articleVoRecords = CglibUtil.copyList(articleRecords, ArticleListVo::new);

        return articleVoPage.setRecords(articleVoRecords);
    }

    @Override
    public ArticleDetailVo getArticleDetail(Long articleId) {

        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Article::getId, articleId);

        // TODO 根据用户ID筛选文章

        Article article = this.getOne(lqw);

        // 将查询结果封装为VO
        ArticleDetailVo articleDetailVo = CglibUtil.copy(article, ArticleDetailVo.class);
        // 根据分类ID查询该博文对应的分类名称
        Category category = categoryService.getById(article.getCategoryId());
        if (null != category) {
            articleDetailVo.setCategoryName(category.getName());
        }

        // 文章被查看则其访问量加一
        article.setViewCount(article.getViewCount() + 1);
        this.updateById(article);

        return articleDetailVo;
    }
}

