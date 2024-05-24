package com.hjw.metaland.biz.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjw.metaland.biz.mapper.dto.AlgModel;
import com.hjw.metaland.biz.mapper.dto.AlgModelInst;
import com.hjw.metaland.biz.service.AlgModelInstService;
import com.hjw.metaland.biz.service.AlgModelService;
import com.hjw.metaland.common.R;
import com.hjw.metaland.utils.RequestUtil;

import cn.hutool.core.io.FileUtil;
import jakarta.annotation.Resource;

/**
 * 算法模型及运行管理
 */
@RestController
@RequestMapping("/model")
public class AlgModelController {
  @Resource
  private AlgModelService algModelService;
  @Resource
  AlgModelInstService algModelInstService;

  @RequestMapping("/query")
  public R query(@RequestBody JSONObject body) {
    IPage<AlgModel> pager = RequestUtil.getPager(body);
    String name = body.getString("name");
    var q = algModelService.lambdaQuery();
    if (StringUtils.isNotBlank(name)) {
      q.like(AlgModel::getName, name);
    }
    return R.data(q.page(pager));
  }

  @RequestMapping("/add")
  public R add(@RequestBody AlgModel model) {
    var cnt = algModelService.lambdaQuery().eq(AlgModel::getName, model.getName()).count();
    if (cnt > 0) {
      return R.fail("模型名称重复");
    }

    if (model.getPath() == null)
      return R.fail("未设置路径");
    File file = new File(model.getPath());
    if (!file.exists()) {
      return R.fail("路径不存在");
    }

    File paramFile = new File(file, "params.json");
    File mainFile = new File(file, "main.py");
    if (!paramFile.exists() || !mainFile.exists()) {
      return R.fail("路径下缺少params.json或main.py文件");
    }

    String params = FileUtil.readString(paramFile, Charset.forName("UTF-8"));
    model.setProps(params);
    model.setCreateAt(Calendar.getInstance().getTime());
    model.setUpdateAt(Calendar.getInstance().getTime());
    algModelService.save(model);
    return R.success();
  }

  @RequestMapping("/edit")
  public R edit(@RequestBody AlgModel model) {
    var cnt = algModelService.lambdaQuery().eq(AlgModel::getName, model.getName())
        .ne(AlgModel::getId, model.getId())
        .count();
    if (cnt > 0) {
      return R.fail("模型名称重复");
    }
    if (model.getPath() == null)
      return R.fail("未设置路径");
    File file = new File(model.getPath());
    if (!file.exists()) {
      return R.fail("路径不存在");
    }

    File paramFile = new File(file, "params.json");
    File mainFile = new File(file, "main.py");
    if (!paramFile.exists() || !mainFile.exists()) {
      return R.fail("路径下缺少params.json或main.py文件");
    }

    String params = FileUtil.readString(paramFile, Charset.forName("UTF-8"));
    model.setProps(params);
    algModelService.updateById(model);
    return R.success();
  }

  @RequestMapping("/remove")
  public R remove(@RequestBody AlgModel model) {
    var cnt = algModelService.lambdaQuery().eq(AlgModel::getId, model.getId()).count();
    if (cnt == 0) {
      return R.fail("模型不存在");
    }
    algModelService.removeById(model);
    return R.success();
  }

  @RequestMapping("/run")
  public R run(@RequestBody JSONObject body) {
    Long modelId = body.getLong("modelId");
    String params = body.getString("params");

    AlgModel model = algModelService.getById(modelId);
    AlgModelInst inst = new AlgModelInst();
    inst.setName(model.getName());
    inst.setVersion(model.getVersion());
    inst.setProps(params);
    inst.setStatus(AlgModelInst.INIT);
    inst.setCreateAt(Calendar.getInstance().getTime());
    inst.setUpdateAt(Calendar.getInstance().getTime());
    algModelInstService.save(inst);
    return R.success();
  }

  @RequestMapping("/inst/info")
  public R instLogs(@RequestBody AlgModelInst model) {
    var val = algModelInstService.getById(model.getId());
    return R.data(val);
  }

  @RequestMapping("/inst/query")
  public R instQuery(@RequestBody JSONObject body) {
    Long modelId = body.getLong("modelId");

    var cnt = algModelService.lambdaQuery().eq(AlgModel::getId, modelId).count();
    if (cnt == 0) {
      return R.fail("模型不存在");
    }
    IPage<AlgModelInst> pager = RequestUtil.getPager(body);
    var rlt = algModelInstService.lambdaQuery().orderByDesc(AlgModelInst::getCreateAt).page(pager);
    return R.data(rlt);
  }
}
