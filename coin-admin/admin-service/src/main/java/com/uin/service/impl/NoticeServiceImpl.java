package com.uin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uin.domain.Notice;
import com.uin.mapper.NoticeMapper;
import com.uin.service.INoticeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统资讯公告信息 服务实现类
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
