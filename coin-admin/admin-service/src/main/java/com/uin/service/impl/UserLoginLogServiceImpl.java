package com.uin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uin.domain.UserLoginLog;
import com.uin.mapper.UserLoginLogMapper;
import com.uin.service.IUserLoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录日志 服务实现类
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements IUserLoginLogService {

}
