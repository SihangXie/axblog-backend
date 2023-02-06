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

package cn.axblog.common.exception;

import cn.axblog.common.HttpCodeMsg;
import cn.axblog.common.JSONResult;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 用于添加用户时校验username唯一约束
     *
     * @param ex
     * @return
     */
    // 异常处理器注解，括号里是该方法要处理的异常类型
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public JSONResult<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // 1.判断异常信息是否包含违反唯一约束的前面两个字
        if (ex.getMessage().contains("Duplicate entry")) {
            // 2.按空格把异常信息分割成String数组
            String[] split = ex.getMessage().split(" ");
            // 3.取数组索引为2的字符串，即重复的username
            String msg = split[2] + "账号已存在，添加失败";
            // 4.返回错误提示信息
            return new JSONResult<>(501, msg);
        }
        // 5.如果不是则返回未知错误
        return JSONResult.error(HttpCodeMsg.UNKNOWN_EXCEPTION);
    }

    /**
     * 捕获删除分类失败的业务异常
     *
     * @param ce
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public JSONResult<String> customExceptionHandler(CustomException ce) {
        return new JSONResult<>(510, ce.getMessage());
    }
}
