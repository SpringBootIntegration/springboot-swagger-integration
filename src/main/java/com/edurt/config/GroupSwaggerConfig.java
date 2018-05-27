/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.edurt.config;

import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * GroupSwaggerConfig <br/>
 * 描述 : GroupSwaggerConfig <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-05-27 下午9:50 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
@Configuration
public class GroupSwaggerConfig {

    @Bean
    @Description(value = "私有api文档")
    public Docket privateApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // api分组名称
                .groupName("私有api文档")
                // 改组api返回结果数据类型
                .genericModelSubstitutes(DeferredResult.class)
                // 改组api请求类型
                .genericModelSubstitutes(ResponseEntity.class)
                // 是否开启默认返回信息
                .useDefaultResponseMessages(false)
                // 代码生成
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.edurt.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    @Bean
    @Description(value = "公开api文档")
    public Docket publicApi() {
        Predicate<RequestHandler> predicate = input -> {
            Class<?> c = input.declaringClass();
            // 过滤error
            if (c == BasicErrorController.class) {
                return false;
            }
            // 注解的类
            if (c.isAnnotationPresent(ApiOperation.class)) {
                return true;
            }
            // 注解的方法
            if (input.isAnnotatedWith(ResponseBody.class)) {
                return true;
            }
            // 只有添加ApiOperation注解的类才会在API中显示
            if (input.isAnnotatedWith(ApiOperation.class)) {
                return true;
            }
            return false;
        };

        return new Docket(DocumentationType.SWAGGER_2)
                // api分组名称
                .groupName("公开api文档")
                // 改组api返回结果数据类型
                .genericModelSubstitutes(DeferredResult.class)
                // 改组api请求类型
                .genericModelSubstitutes(ResponseEntity.class)
                // 是否开启默认返回信息
                .useDefaultResponseMessages(false)
                // 代码生成
                .forCodeGeneration(true)
                .select()
                .apis(predicate)
                .paths(PathSelectors.any())
                .build();
    }

}