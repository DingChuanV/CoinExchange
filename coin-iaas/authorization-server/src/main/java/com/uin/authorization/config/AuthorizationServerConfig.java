package com.uin.authorization.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

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
  private static final int accessTokenValiditySeconds = 3600;
  private static final int refreshTokenValiditySeconds = 7 * 3600;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

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
        .userDetailsService(userDetailsService);
    log.info("authenticationManager:{},authenticationManager:{}", authenticationManager, userDetailsService);
    super.configure(endpoints);
  }
}
