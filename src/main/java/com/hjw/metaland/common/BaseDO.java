package com.hjw.metaland.common;

import java.util.Date;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hjw.metaland.config.Long2String;

import lombok.Data;

@Data
public class BaseDO {
  public static final Integer BOOL_TRUE = 1;
  public static final Integer BOOL_FALSE = 0;

  @TableId(type = IdType.ASSIGN_ID)
  @JsonSerialize(using = Long2String.class)
  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;
}
