package com.hjw.metaland.system.mapper.dto;

import java.util.List;

import com.hjw.metaland.common.BaseDO;

import lombok.Data;

@Data
public class Dict extends BaseDO {
  /** 标识 */
  private String key;
  /** 是否系统字典 */
  private Integer system;
  /** 备注 */
  private String remark;

  /**
   * 字典值列表
   */
  private List<DictData> values;
}
