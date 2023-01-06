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
import cn.axblog.domain.entity.Category;
import cn.axblog.domain.vo.CategoryListVo;
import cn.axblog.mapper.CategoryMapper;
import cn.axblog.service.CategoryService;
import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2023-01-04 20:21:51
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<CategoryListVo> getList() {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();

        // TODO：根据用户ID筛选文章分类列表

        // 筛选状态正常的文章分类
        lqw.eq(Category::getStatus, SystemConstant.CATEGORY_STATUS_NORMAL);
        List<Category> categories = this.list(lqw);

        // 使用Hutool工具包硬拷贝成VO对象返回给前端
        return CglibUtil.copyList(categories, CategoryListVo::new);
    }
}

