package com.hjw.metaland.system.mapper.dto;

import com.hjw.metaland.common.BaseDO;

import lombok.Data;

@Data
public class DictData extends BaseDO {
  /** 字典标识 */
  private String dictKey;
  /** 名称;逻辑删除 */
  private String name;
  /** 值 */
  private String value;
  /** 备注 */
  private String remark;
  /** 状态;0正常 1禁用 */
  private Integer status;
}
