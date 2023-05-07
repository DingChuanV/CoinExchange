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
 * 系统资讯公告信息
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notice")
@ApiModel(value="Notice对象", description="系统资讯公告信息")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "文章状态")
    private Integer status;

    @ApiModelProperty(value = "文章排序，越大越靠前")
    private Integer sort;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "最后修改时间")
    private Date lastUpdateTime;

    @ApiModelProperty(value = "创建日期")
    private Date created;


}
