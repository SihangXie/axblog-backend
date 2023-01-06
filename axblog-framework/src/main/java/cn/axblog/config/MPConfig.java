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

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // 声明为配置类，以便被Spring扫描到，读取配置
public class MPConfig {

    @Bean   // /Spring第三方Bean注解，以便被Spring管理
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 1.创建MP的拦截器容器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 2.往MP拦截器容器中添加分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 3.返回拦截器容器
        return interceptor;
    }
}
