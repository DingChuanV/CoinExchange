package com.uin.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {

  private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
  @Value("${no.auth.path:/admin/login}")
  private Set<String> noRequireTokenUris;
  private final RedisTemplate redisTemplate;

  /**
   * 1.判断该请求的接口资源路径是否需要token 2.取出用户的token 3.判断token是否有效
   *
   * @param exchange the current server exchange
   * @param chain    provides a way to delegate to the next filter
   * @return
   */
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (!isNeedToken(exchange)) {
      //non auth
      return chain.filter(exchange);
    }
    String token = getUserToken(exchange);
    if (StringUtils.isEmpty(token)) {
      return buildNoAuthResult(exchange);
    }
    Boolean aBoolean = redisTemplate.hasKey(token);
    if (aBoolean != null && aBoolean) {
      return chain.filter(exchange);
    }

    return buildNoAuthResult(exchange);
  }

  /**
   * 返回响应
   *
   * @param exchange
   * @return
   */
  private Mono<Void> buildNoAuthResult(ServerWebExchange exchange) {
    ServerHttpResponse response = exchange.getResponse();
    response.getHeaders().set("Content-Type", "application/json;charset=UTF-8");
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("error","NoAuthorization");
    jsonObject.put("errorMsg","Token is NULL or ERROR");
    DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));
    return response.writeWith(Flux.just(wrap));
  }

  /**
   * 从请求头获取token
   *
   * @param exchange
   * @return
   */
  private String getUserToken(ServerWebExchange exchange) {
    String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    return token == null ? null : token.replace("bearer", "");
  }

  private boolean isNeedToken(ServerWebExchange exchange) {
    String path = exchange.getRequest().getURI().getPath();
    if (noRequireTokenUris.contains(path)) {
      return false;//no need
    }
    return Boolean.TRUE;
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
