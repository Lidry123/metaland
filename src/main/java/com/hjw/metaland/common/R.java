package com.hjw.metaland.common;

import lombok.Data;
import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    public static final String SERVER_ERROR = "服务器运行错误";
    public static final String INVALID_PARAM = "参数无效";
    public static final String INVALID_CAPTCHA = "验证码无效";
    public static final int C_SUCCESS = 200;
    public static final int C_NO_AUTH = 401;
    public static final int C_NO_LOGIN = 403;
    public static final int C_ERROR = 500;

    /**
     * 状态码
     */
    private int code;
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 数据
     */
    private T data;
    private String msg;

    private R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = code == C_SUCCESS;
    }

    public static R data(Object data) {
        return new R(C_SUCCESS, data, "执行成功");
    }

    public static R success() {
        return new R(C_SUCCESS, null, "成功");
    }

    public static R fail(String msg) {
        return new R(C_ERROR, null, msg);
    }

    public static R fail(Integer code, String msg) {
        return new R(code, null, msg);
    }
}
