package com.uin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户权限配置
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_privilege_user")
@ApiModel(value="SysRolePrivilegeUser对象", description="用户权限配置")
public class SysRolePrivilegeUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色Id")
    private Long roleId;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "权限Id")
    private Long privilegeId;


}
