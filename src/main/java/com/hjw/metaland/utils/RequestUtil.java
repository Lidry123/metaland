package com.hjw.metaland.utils;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class RequestUtil {
  public static <T> IPage<T> getPager(JSONObject body) {
    Page<T> pager = new Page<T>();
    pager.setCurrent(body.getLongValue("page", 1));
    pager.setSize(body.getLongValue("size", 10));
    return pager;
  }
}
