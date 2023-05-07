package com.uin.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author dingchuan
 */
@Data
public class JwtToken {
  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("token_type")
  private String tokenType;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("expires_in")
  private Long expiresIn;
  /**
   * 范围
   */
  private String scope;
  /**
   * JWT ID
   */
  private String jti;
}
