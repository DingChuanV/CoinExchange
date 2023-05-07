package com.uin.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户登录日志
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_login_log")
@ApiModel(value="UserLoginLog对象", description="用户登录日志")
public class UserLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "客户端类型	            1-PC	            2-IOS	            3-Android")
    private Integer clientType;

    @ApiModelProperty(value = "登录IP")
    private String loginIp;

    @ApiModelProperty(value = "登录地址")
    private String loginAddress;

    @ApiModelProperty(value = "登录时间")
    private Date loginTime;


}
