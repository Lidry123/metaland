package com.hjw.metaland.config;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Calendar;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hjw.metaland.common.BizException;
import com.hjw.metaland.system.mapper.LogMapper;
import com.hjw.metaland.system.mapper.dto.Log;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class OperLogAspect {

  @Resource
  LogMapper logMapper;

  @Around("@annotation(operation)")
  public Object around(ProceedingJoinPoint joinPoint, Operation operation) throws Throwable {
    if (!operation.enable()) {
      return joinPoint.proceed();
    }

    Long startTime = System.currentTimeMillis();
    try {
      Object result = joinPoint.proceed();
      log(startTime, joinPoint, operation, result, 200, null);
      return result;
    } catch (Throwable e) {
      if (e instanceof UndeclaredThrowableException) {
        Throwable cause = e.getCause();
        if (cause instanceof BizException) {
          e = cause;
        }
      }
      log(startTime, joinPoint, operation, null, 500, e);
      throw e;
    }
  }

  void log(Long startTime, ProceedingJoinPoint joinPoint, Operation operation, Object retValue, Integer status,
      Throwable err) {
    Log logger = new Log();
    logger.setName(operation.name());
    logger.setTime((int) (System.currentTimeMillis() - startTime));
    logger.setType(operation.type());

    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest req = attributes.getRequest();
    logger.setStatus(status);

    String url = req.getRequestURL().toString();
    String query = req.getQueryString();
    if (query != null && query.length() > 0) {
      url += "?" + query;
    }
    if (logger.getTime() > 1000) {
      log.warn("发现耗时请求：" + url);
    }
    url = req.getMethod() + " " + url;
    logger.setUrl(url);

    String reqMethod = req.getMethod();
    if (reqMethod.equals("POST")) {
      // 获取参数
      String args = JSONUtil.toJsonStr(joinPoint.getArgs());
      logger.setReq(args);
    }

    if (retValue != null) {
      logger.setRes(JSONUtil.toJsonStr(retValue));
    }

    if (err != null) {
      logger.setRes(err.getMessage());
      logger.setException(err.getStackTrace().toString());
    }

    // 获取用户真实ip地址
    String ip;
    if (req.getHeader("x-forwarded-for") == null) {
      ip = req.getRemoteAddr();
    } else {
      ip = req.getHeader("x-forwarded-for");
    }

    if (ip.equals("0:0:0:0:0:0:0:1")) {
      ip = "127.0.0.1";
    }
    logger.setIpaddr(ip);

    try {
      Long userId = (Long) StpUtil.getLoginId();
      logger.setUserId(userId.intValue());
    } catch (Exception e) {
    }

    logMapper.insert(logger);
  }
}
