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
package com.edurt.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * SwaggerController <br/>
 * 描述 : SwaggerController <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-05-09 下午11:32 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
@RestController
@Api(value = "swagger对外api接口", description = "该接口注解主要用于描述swagger的功能点")
public class SwaggerController {

    @ApiOperation(value = "打印测试swagger信息", notes = "打印测试示例", tags = {"swagger", "info"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "拒绝访问"),
            @ApiResponse(code = 404, message = "资源未找到"),
            @ApiResponse(code = 200, message = "访问接口成功")
    })
    @RequestMapping(value = "info", method = RequestMethod.GET)
    String info() {
        return "我是Swagger测试示例";
    }

}