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
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;

import java.util.Arrays;

/**
 * OauthSwaggerConfig <br/>
 * 描述 : OauthSwaggerConfig <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-04-27 下午3:52 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
@Configuration
public class OauthSwaggerConfig {

    @Bean
    @Description(value = "oauth api配置")
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("安全验证api文档")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    @Bean
    @Description(value = "安全授权配置")
    public SecurityConfiguration security() {
        // String clientId, String clientSecret, String realm, String appName, String apiKey, ApiKeyVehicle apiKeyVehicle, String apiKeyName, String scopeSeparator
        SecurityConfiguration configuration = new SecurityConfiguration(
                "clientId",
                "clientSecret",
                null,
                "swagger",
                "appkey",
                null,
                "apiKeyName",
                "scopeSeparator"
        );
        return configuration;
    }

    /**
     * 描述API如何保护: 基本认证|OAuth2|...
     */
    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint("/token", "oauthtoken"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint("/authorize", "clientId", "clientId"))
                .build();
        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
                .grantTypes(Arrays.asList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
        return oauth;
    }

    /**
     * 授权类型
     */
    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations"),
                new AuthorizationScope("info", "Access foo API")};
        return scopes;
    }

    /**
     * 安全上下文
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.regex("/info/*"))
                .build();
    }

}