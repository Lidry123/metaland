package com.hjw.metaland.biz.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjw.metaland.biz.mapper.dto.CatalogNode;
import com.hjw.metaland.biz.service.CatalogNodeService;
import com.hjw.metaland.biz.service.CatalogService;
import com.hjw.metaland.common.R;

import jakarta.annotation.Resource;

/**
 * 数据目录管理
 */
@RestController
@RequestMapping("/catalog")
public class CatalogController {
  @Resource
  CatalogService catalogService;
  @Resource
  CatalogNodeService catalogNodeService;

  @GetMapping("/node/list")
  public R list() {
    return R.data(catalogNodeService.lambdaQuery().orderByAsc(CatalogNode::getOrderNum).list());
  }

  @PostMapping("/node/add")
  public R nodeAdd(@RequestBody CatalogNode node) {
    Long pid = node.getPid() == null ? 0L : node.getPid();
    Long cnt = catalogNodeService.lambdaQuery().eq(CatalogNode::getPid, pid)
        .eq(CatalogNode::getName, node.getName()).count();
    if (cnt > 0) {
      return R.fail("节点已存在");
    }
    return R.data(catalogNodeService.save(node));
  }

  @PostMapping("/node/edit")
  public R nodeEdit(@RequestBody CatalogNode node) {
    Long pid = node.getPid() == null ? 0L : node.getPid();
    Long cnt = catalogNodeService.lambdaQuery().eq(CatalogNode::getPid, pid)
        .ne(CatalogNode::getId, node.getId())
        .eq(CatalogNode::getName, node.getName()).count();
    if (cnt > 0) {
      return R.fail("节点已存在");
    }
    return R.data(catalogNodeService.updateById(node));
  }

  @PostMapping("/node/remove")
  @Transactional
  public R nodeRemove(@RequestBody CatalogNode node) {
    Long cnt = catalogNodeService.lambdaQuery().eq(CatalogNode::getPid, node.getId()).count();
    if (cnt > 0) {
      return R.fail("该节点下存在子节点，不允许删除");
    }
    catalogNodeService.removeById(node.getId());
    // TODO:: 移除节点关联数据
    return R.success();

    // Stack<Long> pids = new Stack<>();
    // pids.add(node.getId());
    // while (pids.size() > 0) {
    // Long pid = pids.pop();
    // catalogNodeService.removeById(pid);
    // // TODO:://移除节点关联数据
    // List<CatalogNode> children =
    // catalogNodeService.lambdaQuery().eq(CatalogNode::getPid,
    // node.getId()).list();
    // for (CatalogNode child : children) {
    // pids.add(child.getId());
    // }
    // }
    // return R.success();
  }

}
