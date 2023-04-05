package com.uin.authorization.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * 授权服务器配置
 */
@EnableAuthorizationServer
@RequiredArgsConstructor
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private static final Logger log = LoggerFactory.getLogger(AuthorizationServerConfig.class);
  private final String clientId = "coin-api";
  private final CharSequence charSequence = "coin-secret";
  private final String scope = "all";
  private static final int accessTokenValiditySeconds = 7 * 24 * 3600;// one week
  private static final int refreshTokenValiditySeconds = 30 * 24 * 3600;// one month
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  //private final RedisConnectionFactory redisConnectionFactory;

  /**
   * 添加第三方客户端
   *
   * @param clients
   * @throws Exception
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient(clientId)
        .secret(passwordEncoder.encode(charSequence))
        .scopes(scope)
        .authorizedGrantTypes("password", "refresh_token")
        .accessTokenValiditySeconds(accessTokenValiditySeconds)
        .refreshTokenValiditySeconds(refreshTokenValiditySeconds);
    log.info("Third party clientId:{},secret:{},scopes:{},accessTokenValiditySeconds:{},"
            + "refreshTokenValiditySeconds:{}", clientId,
        charSequence, scope, accessTokenValiditySeconds, refreshTokenValiditySeconds);
    super.configure(clients);
  }

  /**
   * 配置验证管理器，UserDetailService
   *
   * @param endpoints the endpoints configurer
   * @throws Exception
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
        .tokenStore(jwtTokenstore())
        .tokenEnhancer(jwtAccessTokenConverter());
    log.info("authenticationManager:{},authenticationManager:{},tokenStore:{},tokenEnhancer:{}", authenticationManager,
        userDetailsService, jwtTokenstore(), jwtAccessTokenConverter());
    super.configure(endpoints);
  }

  private TokenStore jwtTokenstore() {
    JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
    return jwtTokenStore;
  }

  private JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    // 读取classpath下的文件
    ClassPathResource pathResource = new ClassPathResource("coinexchange.jks");
    // 获取KeyStoreFactory
    KeyStoreKeyFactory factory = new KeyStoreKeyFactory(pathResource, "coinexchange".toCharArray());
    converter.setKeyPair(factory.getKeyPair("coinexchange", "coinexchange".toCharArray()));
    return converter;
  }

  /**
   * 使用Redis实现token分布式问题
   *
   * @return
   */
//  public TokenStore redisTokenstore() {
//    return new RedisTokenStore(redisConnectionFactory);
//  }

}
