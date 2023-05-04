package com.uin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uin.domain.WorkIssue;
import com.uin.mapper.WorkIssueMapper;
import com.uin.service.IWorkIssueService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 工单记录 服务实现类
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Service
public class WorkIssueServiceImpl extends ServiceImpl<WorkIssueMapper, WorkIssue> implements IWorkIssueService {

}
