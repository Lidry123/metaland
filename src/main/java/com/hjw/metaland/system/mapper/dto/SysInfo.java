package com.hjw.metaland.system.mapper.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hjw.metaland.common.BaseDO;

import lombok.Data;

@Data
@TableName("SYS_INFO")
public class SysInfo extends BaseDO {
  private String name;
  private String key;
  private String value;
  private Integer pub;
}
