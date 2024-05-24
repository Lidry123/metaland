package com.hjw.metaland.utils;

import java.nio.charset.Charset;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import lombok.SneakyThrows;

/**
 * sm4国密加密解密工具集
 *
 * @date 2018年12月21日 下午2:45:31
 */
public class SM4Utils {
  static final String key = "K72dk3lI4=2E3try";
  static final SM4 sm4 = SmUtil.sm4(key.getBytes());

  static final String ENCODING = "UTF-8";

  @SneakyThrows
  public static String encrypt(String plain) {
    var buff = sm4.encryptHex(plain);
    return buff;
  }

  @SneakyThrows
  public static String decrypt(String plain) {
    var buff = sm4.decryptStr(plain, Charset.forName(ENCODING));
    return buff;
  }
}