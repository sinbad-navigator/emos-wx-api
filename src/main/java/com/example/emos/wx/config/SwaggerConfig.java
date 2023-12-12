package com.example.emos.wx.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration //加上这个注解，springboot才能得到这些配置信息
@EnableSwagger2
public class SwaggerConfig {

    @Bean //加上这个注解表示，这个方法返回的对象是要给springboot的
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title("EMOS在线办公系统");
        ApiInfo info = builder.build();
        docket.apiInfo(info);

        ApiSelectorBuilder selectorBuilder = docket.select();
        //设置什么包下的什么类什么方法要添加要到swagger里面
        selectorBuilder.paths(PathSelectors.any()); //设置所有包下的所有类
        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)); //添加了ApiOperation注解的方法 将添加到swagger
        docket = selectorBuilder.build();

        //让swagger支持jwt
        ApiKey apiKey = new ApiKey("token", "token", "token");
        //把apikey分装在list里面交给docket
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(apiKey);
        docket.securitySchemes(apiKeyList);

        //设置令牌的作用域 设置为全局
        AuthorizationScope scope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] scopes = {scope};
        SecurityReference reference = new SecurityReference("token",scopes);
        List refList = new ArrayList();
        refList.add(reference);
        SecurityContext context = SecurityContext.builder().securityReferences(refList).build();
        List ctxList = new ArrayList();
        ctxList.add(context);
        docket.securityContexts(ctxList);

        return docket;
    }
}
