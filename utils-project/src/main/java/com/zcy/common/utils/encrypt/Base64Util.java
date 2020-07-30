package com.zcy.common.utils.encrypt;


import java.util.Base64;
/**
  * @Author zhuangchongyi
  * @Description Base64加密工具类
  * @Date 2020/6/23 16:01
  */
public class Base64Util {
    /**
     * 加密字符串
     *
     * @param inputData
     * @return
     */
    public static String decodeData(String inputData, String charset) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.getDecoder().decode(inputData.getBytes(charset)), charset);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 解密加密后的字符串
     *
     * @param inputData
     * @return
     */
    public static String encodeData(String inputData, String charset) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.getEncoder().encode(inputData.getBytes(charset)), charset);
        } catch (Exception e) {
        }
        return null;
    }

}
