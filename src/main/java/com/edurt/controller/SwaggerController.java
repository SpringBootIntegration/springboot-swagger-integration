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

import com.edurt.bean.SwaggerBean;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 直接通过路径进行传输数据
     */
    @ApiOperation(value = "通过路径传递参数")
    // 开始进行数据的传输
    @ApiImplicitParams(
            // dataType 对应数据参数的类型
            // paramType 对应参数的传递方式  path/body/header/form
            // 需要注意的是: 传递的参数必须与swagger api注解参数一致
            @ApiImplicitParam(name = "id", value = "我是传输的数据title", required = true, dataType = "String", paramType = "path")
    )
    @RequestMapping(value = "info/path/{id}", method = RequestMethod.GET)
    String infoByPath(@PathVariable(name = "id") String id) {
        return "我是Swagger文档,用于标识路径传递数据 " + id;
    }

    /**
     * 通过form表单传输数据
     */
    @ApiOperation(value = "通过form表单传递参数")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "bean", value = "我是传递的form数据", required = false, dataType = "SwaggerBean", paramType = "body")
    )
    @RequestMapping(value = "info/form", method = RequestMethod.POST)
    String infoByForm(@RequestBody SwaggerBean bean) {
        return bean.toString();
    }

}