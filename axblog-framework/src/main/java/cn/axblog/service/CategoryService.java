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

package cn.axblog.service;

import cn.axblog.domain.entity.Category;
import cn.axblog.domain.vo.CategoryListVo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2023-01-04 20:16:37
 */
public interface CategoryService extends IService<Category> {

    List<CategoryListVo> getList();
}

