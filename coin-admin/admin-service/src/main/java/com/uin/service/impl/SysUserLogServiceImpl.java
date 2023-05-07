package com.uin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uin.domain.SysUserLog;
import com.uin.mapper.SysUserLogMapper;
import com.uin.service.ISysUserLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Service
public class SysUserLogServiceImpl extends ServiceImpl<SysUserLogMapper, SysUserLog> implements ISysUserLogService {

}
