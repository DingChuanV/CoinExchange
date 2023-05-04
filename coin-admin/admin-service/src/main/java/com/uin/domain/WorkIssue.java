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
 * 工单记录
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("work_issue")
@ApiModel(value="WorkIssue对象", description="工单记录")
public class WorkIssue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id(提问用户id)")
    private Long userId;

    @ApiModelProperty(value = "回复人id")
    private Long answerUserId;

    @ApiModelProperty(value = "回复人名称")
    private String answerName;

    @ApiModelProperty(value = "工单内容")
    private String question;

    @ApiModelProperty(value = "回答内容")
    private String answer;

    @ApiModelProperty(value = "状态：1-待回答；2-已回答；")
    private Boolean status;

    @ApiModelProperty(value = "修改时间")
    private Date lastUpdateTime;

    @ApiModelProperty(value = "创建时间")
    private Date created;


}
