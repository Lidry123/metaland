package com.hjw.metaland.system.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjw.metaland.common.R;
import com.hjw.metaland.system.mapper.dto.Dict;
import com.hjw.metaland.system.mapper.dto.DictData;
import com.hjw.metaland.system.service.DictDataService;
import com.hjw.metaland.system.service.DictService;

import jakarta.annotation.Resource;

/**
 * 字典管理
 */
@RestController
@RequestMapping("/dict")
public class DictController {
  @Resource
  private DictService dictService;
  @Resource
  private DictDataService dictDataService;

  @PostMapping("/list")
  public R list() {
    return R.data(dictService.list());
  }

  @PostMapping("/add")
  public R add(@RequestBody Dict dict) {
    return R.data(dictService.save(dict));
  }

  @PostMapping("/edit")
  public R edit(@RequestBody Dict dict) {
    return R.data(dictService.updateById(dict));
  }

  @PostMapping("/remove")
  public R remove(@RequestBody Dict dict) {
    dict = dictService.getById(dict.getId());
    dictService.removeById(dict.getId());
    dictDataService.remove(dictDataService.lambdaQuery().eq(DictData::getDictKey, dict.getKey()));
    return R.success();
  }

  @PostMapping("/add/value")
  public R addValue(@RequestBody DictData value) {
    return R.data(dictDataService.save(value));
  }

  @PostMapping("/edit/value")
  public R editValue(@RequestBody DictData value) {
    return R.data(dictDataService.updateById(value));
  }

  @PostMapping("/remove/value")
  public R removeValue(@RequestBody DictData value) {
    dictDataService.removeById(value.getId());
    return R.success();
  }

}
