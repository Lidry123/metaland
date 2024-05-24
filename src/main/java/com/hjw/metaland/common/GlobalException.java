package com.hjw.metaland.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandle(Exception e) {
        log.error(e.getMessage(), e);
        try {
            if (e instanceof BizException) {
                var myExp = (BizException) e;
                return R.fail(myExp.getMessage());
            } else if (e instanceof NotLoginException) {
                return R.fail(R.C_NO_AUTH, "用户未登录");
            } else {
                return R.fail(R.SERVER_ERROR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return R.fail("系统异常");
        }
    }
}
