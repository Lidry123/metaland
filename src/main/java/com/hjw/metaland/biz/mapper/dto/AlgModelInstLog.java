package com.hjw.metaland.biz.mapper.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hjw.metaland.common.BaseDO;
import com.hjw.metaland.config.Long2String;

import lombok.Data;

/**
 * 模型运行日志
 */
@Data
public class AlgModelInstLog extends BaseDO {

  /**
   * 模型实例ID
   */
  @JsonSerialize(using = Long2String.class)
  Long modelInstId;

  /**
   * 模型ID
   */
  @JsonSerialize(using = Long2String.class)
  Long modelId;

  /**
   * 日志级别
   * 1 消息
   * 2 警告
   * 3 成功
   * 4 错误
   * 5 异常
   */
  Integer level;

  /**
   * 日志内容
   */
  String content;
}
