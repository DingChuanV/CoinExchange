package com.uin.authorization.service;

import static com.uin.authorization.constant.LoginConstant.QUERY_MEMBER_SQL;

import com.uin.authorization.constant.LoginConstant;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

  private static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);
  private static final String LOGIN_TYPE = "login_type";
  private final JdbcTemplate jdbcTemplate;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    String parameter = servletRequestAttributes.getRequest().getParameter(LOGIN_TYPE);
    if (StringUtils.isEmpty(parameter)) {
      log.warn("login type:{}", parameter);
      throw new AuthorizationServiceException("登录类型为NULL");
    }
    String grantType = servletRequestAttributes.getRequest().getParameter("grant_type");
    if (grantType.equals(LoginConstant.REFRESH_TOKEN)) {
      username = adjustUsername(username, parameter);
    }
    switch (parameter) {

      case LoginConstant.ADMIN_TYPE:
        return loadSysUserByUsername(username);
      case LoginConstant.MEMBER_TYPE:
        return loadMemberUserByUsername(username);
      default:
        throw new AuthorizationServiceException("暂不支持的登录方式:" + parameter);
    }
  }

  /**
   * @param username  id
   * @param loginType
   * @return
   */
  private String adjustUsername(String username, String loginType) {
    if (LoginConstant.ADMIN_TYPE.equals(loginType)) {
      return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
    }
    if (LoginConstant.MEMBER_TYPE.equals(loginType)) {
      return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
    }
    return username;

  }

  /**
   * 会员登录
   *
   * @return
   */
  private UserDetails loadMemberUserByUsername(String username) {
    return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_SQL, (RowMapper<UserDetails>) (rs, rowNum) -> {
      if (rs.wasNull()) {
        throw new UsernameNotFoundException("会员：" + username + "不存在");
      }
      long id = rs.getLong("id"); // 获取用户的id
      String password = rs.getString("password");
      int status = rs.getInt("status");
      return new User(
          String.valueOf(id),
          password,
          status == 1,
          true,
          true,
          true,
          Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
      );
    }, username, username);

  }

  /**
   * 系统人员登录
   *
   * @return
   */
  private UserDetails loadSysUserByUsername(String username) {
    return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, (rs, rowNum) -> {
      if (rs.wasNull()) {
        throw new UsernameNotFoundException("用户：" + username + "不存在");
      }
      Long id = rs.getLong("id");
      String password = rs.getString("password");
      int status = rs.getInt("status");
      User user = new User(
          String.valueOf(id), // 使用用户的id 代替用户的名称，这样会使得后面的很多情况得以处理
          password,
          status == 1,
          true,
          true,
          true,
          getUserPermissions(id));
      return user;
    }, username);
  }

  private Set<SimpleGrantedAuthority> getUserPermissions(Long id) {
    // 查询用户是否为管理员
    String code = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
    List<String> permissions = null;
    if (LoginConstant.ADMIN_CODE.equals(code)) { // 管理员
      permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
    } else {
      permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_PERMISSION_SQL, String.class, id);
    }
    if (permissions == null || permissions.isEmpty()) {
      return Collections.EMPTY_SET;
    }
    return permissions
        .stream()
        .distinct() // 去重
        .map(
            perm -> new SimpleGrantedAuthority(perm) // perm - >security可以识别的权限
        )
        .collect(Collectors.toSet());
  }
}
