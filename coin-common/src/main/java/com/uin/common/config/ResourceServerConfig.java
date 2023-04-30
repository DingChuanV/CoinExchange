package com.uin.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

/**
 * 资源服务器配置类
 *
 * @author dingchuan
 */
@Configuration
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  /**
   * ResourceServerConfigurerAdapter优先级高于ResourceServerConfigurer，用于保护oauth相关的endpoints.
   * 同时主要作用于用户的登录（form login，Basic auth）
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {
    // 由于使用jwt，不需要csrf
    http.csrf().disable();
    // 基于token，不需要session
    http.sessionManagement().disable();
    http.authorizeRequests()
        .antMatchers("/v2/api-docs",
        "/swagger-resources/configuration/ui",// 用来获取支持的动作
        "/swagger-resources",// 用来获取api-docs的URI
        "/swagger-resources/configuration/security",// 安全选项
        "/webjars/**",
        "/swagger-ui.html"
    ).permitAll()
        .antMatchers("/**").authenticated()
        .and().headers().cacheControl();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources){
    resources.tokenStore(jwtTokenStore());
  }

  private TokenStore jwtTokenStore() {
    JwtTokenStore store = new JwtTokenStore(jwtAccessTokenConverter());
    return store;
  }

  private JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    ClassPathResource classPathResource = new ClassPathResource("jks.txt");
    String publickey=null;
    try{
      byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
      publickey=new String(bytes,"utf-8");
    }catch (Exception e){
      log.info("读取公钥失败！");
    }
    jwtAccessTokenConverter.setVerifierKey(publickey);
    return jwtAccessTokenConverter;
  }
}
