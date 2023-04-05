package com.uin.authorization.constant;

public class LoginConstant {
  /**
   * 管理员登录
   */
  public static final String ADMIN_TYPE = "admin_type" ;

  /**
   * 用户/会员登录
   */
  public static final String MEMBER_TYPE  = "member_type" ;

  public static final String QUERY_ADMIN_SQL =
      "SELECT `id` ,`username`, `password`, `status` FROM sys_user WHERE username = ? ";

  public static final String QUERY_ROLE_CODE_SQL =
      "SELECT `code` FROM sys_role LEFT JOIN sys_user_role ON sys_role.id = sys_user_role.role_id WHERE sys_user_role.user_id= ?";

  public static final String QUERY_ALL_PERMISSIONS =
      "SELECT `name` FROM sys_privilege";
  public static final String QUERY_PERMISSION_SQL =
      "SELECT `name` FROM sys_privilege LEFT JOIN sys_role_privilege ON sys_role_privilege.privilege_id = "
          + "sys_privilege.id LEFT JOIN sys_user_role ON sys_role_privilege.role_id = sys_user_role.role_id WHERE sys_user_role.user_id = ?";

  public static final String ADMIN_CODE = "ROLE_ADMIN";
}
