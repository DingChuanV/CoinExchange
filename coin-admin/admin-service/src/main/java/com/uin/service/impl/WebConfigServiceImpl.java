package com.uin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uin.domain.WebConfig;
import com.uin.mapper.WebConfigMapper;
import com.uin.service.IWebConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站配置信息 服务实现类
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements IWebConfigService {

}
