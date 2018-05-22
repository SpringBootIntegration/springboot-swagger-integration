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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * SwaggerConfig <br/>
 * 描述 : SwaggerConfig <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-05-22 下午10:55 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    @Description(value = "配置swagger信息")
    public Docket swagger() {
        // 使用swagger2
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        // 设置包扫描路径 公开所有API的文档
        // 公开所有API文档是不可去的,可以通过将参数传递给docket类的apis()和paths()方法来限制swagger api的显示
        docket.select().apis(RequestHandlerSelectors.basePackage("com.edurt.controller"))
                // 设置过滤api地址, 可以使用any() none(), regex() ant()
                .paths(PathSelectors.ant("/info/**"))
                // 构建swagger
                .build();
        docket.apiInfo(apiInfo());

        // 全局错误
        List<ResponseMessage> messages = new ArrayList<ResponseMessage>();
        messages.add(new ResponseMessageBuilder().code(400)
                .message("400错误")
                .responseModel(new ModelRef("出现错误啦"))
                .build());
        // 必须设置为不使用默认的错误提示
        docket.useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, messages);

        return docket;
    }

    @Bean
    @Description(value = "配置swagger api描述信息")
    public ApiInfo apiInfo() {
        // 构建联系人 name, url, email
        Contact contact = new Contact(
                "轻课堂",
                "https://www.edurt.com",
                "support@edurt.com"
        );
        // 构建api描述信息
        ApiInfo apiInfo = new ApiInfo(
                "SpringBoot Swagger Intergration",
                "SpringBoot整合Swagger自动化文档",
                "1.0.0",
                "TOS",
                contact,
                "Apache License",
                "https://www.apache.org"
        );
        return apiInfo;
    }

}