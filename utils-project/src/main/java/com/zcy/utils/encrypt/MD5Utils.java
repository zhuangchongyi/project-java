package com.zcy.utils.encrypt;

import java.security.MessageDigest;

/**
 * @Author zhuangchongyi
 * @Description MD5加密工具类
 * @Date 2020/6/23 16:01
 */
public class MD5Utils {
    private static final String hexDigIts[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * MD5加密
     *
     * @param origin  加密字符
     * @param charset 编码
     * @return
     */
    public static String MD5Encode(String origin, String charset) {
        String result = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            if (null == charset || "".equals(charset))
                result = byteArrayToHexString(md5.digest(origin.getBytes()));
            else
                result = byteArrayToHexString(md5.digest(origin.getBytes(charset)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * MD5加密
     *
     * @param origin  加密字符
     * @param salt 盐值
     * @param charset 编码
     * @return
     */
    public static String MD5Encode(String origin, String salt, String charset) {
        String result = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            origin = origin + salt;
            if (null == charset || "".equals(charset))
                result = byteArrayToHexString(md5.digest(origin.getBytes()));
            else
                result = byteArrayToHexString(md5.digest(origin.getBytes(charset)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String byteArrayToHexString(byte[] digest) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            buffer.append(byteToHexString(digest[i]));
        }
        return buffer.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }

}
