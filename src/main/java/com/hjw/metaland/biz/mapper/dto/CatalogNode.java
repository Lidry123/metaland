package com.hjw.metaland.biz.mapper.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hjw.metaland.common.BaseDO;
import com.hjw.metaland.config.Long2String;

import lombok.Data;

@TableName("biz_catalog_node")
@Data
public class CatalogNode extends BaseDO {
  /**
   * 名称
   */
  private String name;
  /**
   * 节点类型编码;
   * 0000 目录节点
   * 0101 矢量
   * 0201 影像
   * 0301 XYZ 服务 0302 地形 0303 WMTS 0304 矢量瓦片...
   * 0401 孪生对象
   * 0501 模拟数据
   * 0601 模型
   * 0710 表格-元数据
   */
  private String type;
  /** 扩展属性json */
  private String props;
  /** 数据集ID;对应各类数据集ID */
  @JsonSerialize(using = Long2String.class)
  private Long datasetId;

  /** 备注 */
  private String remark;
  
  private Boolean visible;

  @JsonSerialize(using = Long2String.class)
  private Long pid;

  private Integer orderNum;
}
