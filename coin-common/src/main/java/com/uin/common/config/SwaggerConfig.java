package com.uin.common.config;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author dingchuan
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerConfig.SwaggerProperties.class)
public class SwaggerConfig {

  @Autowired
  private SwaggerProperties swaggerProperties;

  @Bean
  public Docket docket() {
    Docket docket = new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
        .paths(PathSelectors.any())
        .build();
    // 安全的配置
    docket.securitySchemes(securitySchemes()) // 安全规则
        .securityContexts(securityContexts()); // 安全配置的上下问
    return docket;
  }

  private List<SecurityContext> securityContexts() {
    return Arrays.asList(new SecurityContext(Arrays.asList(new SecurityReference("Authorization",
        new AuthorizationScope[]{new AuthorizationScope("Global", "accessResource")})), PathSelectors.any()));
  }

  private List<? extends SecurityScheme> securitySchemes() {
    return Arrays.asList(new ApiKey("Authorization", "Authorization", "Authorization"));
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().contact(
            new Contact(swaggerProperties.getName(), swaggerProperties.getUrl(), swaggerProperties.getEmail())
        )
        .title(swaggerProperties.getTitle())
        .description(swaggerProperties.getDescription())
        .version(swaggerProperties.getVersion())
        .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
        .build();
  }

  @Data
  @ConfigurationProperties(prefix = "swagger2")
  class SwaggerProperties {

    /**
     * 包扫描的路径
     */
    private String basePackage;

    /**
     * 联系人的名称
     */
    private String name;

    /**
     * 联系人的主页
     */
    private String url;

    /**
     * 联系人的邮箱
     */
    private String email;

    /**
     * API的标题
     */
    private String title;

    /**
     * API的描述
     */
    private String description;

    /**
     * API的版本号
     */
    private String version;

    /**
     * API的服务团队
     */
    private String termsOfServiceUrl;
  }
}
