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

package cn.axblog.common;

import lombok.Data;

@Data
public class JSONResult<T> {

    // 响应业务状态
    private Integer code;
    // 响应消息
    private String msg;
    // 响应数据
    private T data;

    public JSONResult() {
        this.code = HttpCodeMsg.SUCCESS.getCode();
        this.msg = HttpCodeMsg.SUCCESS.getMsg();
    }

    public JSONResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> JSONResult<T> success(T object) {
        JSONResult<T> result = new JSONResult<>();
        result.code = HttpCodeMsg.SUCCESS.getCode();
        result.msg = HttpCodeMsg.SUCCESS.getMsg();
        result.data = object;
        return result;
    }

    public static <T> JSONResult<T> error(HttpCodeMsg codeMsg) {
        JSONResult<T> result = new JSONResult<>();
        result.code = codeMsg.getCode();
        result.msg = codeMsg.getMsg();
        return result;
    }
}
