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

package cn.axblog.utils;

import static cn.axblog.common.constant.RedisConstant.CACHE_NULL_TTL;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheUtil {

    @Autowired
    private final StringRedisTemplate stringRedisTemplate;

    public RedisCacheUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 写入缓存，指定有效时间
     *
     * @param key      Redis的Key
     * @param value    Redis的Value
     * @param time     缓存的有效时间
     * @param timeUnit 有效时间的单位
     */
    public void set(String key, Object value, Long time, TimeUnit timeUnit) {
        // 使用Hutool工具包的JSONUtil.toJsonStr(value)将Java对象序列化成JSON字符串
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, timeUnit);
    }

    /**
     * 写入缓存，不指定有效时间
     *
     * @param key   Redis的Key
     * @param value Redis的Value
     */
    public void set(String key, Object value) {
        // 使用Hutool工具包的JSONUtil.toJsonStr(value)将Java对象序列化成JSON字符串
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
    }

    /**
     * 带缓存空值的查询缓存
     *
     * @param keyPrefix  Key前缀
     * @param id         Key的ID
     * @param type       Value的Java类型
     * @param dbFallback Bean的类型对应的数据层查询方法(函数式编程)
     * @param time       新查询的数据写入Redis缓存的有效时间
     * @param timeUnit   有效时间的单位
     * @param <T>        Value的Java类型
     * @param <ID>       Value的ID
     * @return　返回查询到的Bean对象
     */
    public <T, ID> T queryWithPassThrough(String keyPrefix, ID id, Class<T> type
        , Function<ID, T> dbFallback, Long time, TimeUnit timeUnit) {
        // 1.拼接完整的Key
        String key = keyPrefix + id;
        // 2.从Redis查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 3.判断是否存在
        if (StrUtil.isNotBlank(json)) {
            // 3.1 命中缓存，返回
            return JSONUtil.toBean(json, type);
        }
        // 4.判断是否为空字符串
        if (json != null) {
            // 4.1 为空串，返回null
            return null;
        }
        // 5.到此说明缓存没命中，根据ID查询数据库(此处使用函数式编程)
        T result = dbFallback.apply(id);
        // 6.判断在数据库中是否存在
        if (Objects.isNull(result)) {
            // 6.1 在数据库中不存在，将空字符串写入Redis
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 6.2 返回错误信息
            return null;
        }
        // 7.到此说明数据库存在，写入Redis
        this.set(key, result, time, timeUnit);
        // 8.返回
        return result;
    }
}
