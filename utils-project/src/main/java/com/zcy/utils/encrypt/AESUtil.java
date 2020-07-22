package com.zcy.utils.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author zhuangchongyi
 * @Description AES加密工具类
 * @Date 2020/7/6 18:12
 */
public class AESUtil {

    // 密钥
    public static String KEY = "AD42F6697B035B7580E4FEF93BE20BAD";
    private static final String CHARSET = "UTF-8";
    // 偏移量
    private static final int OFFSET = 16;
    // 此处使用AES-128-CBC加密模式，key需要为16位。
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";

    /**
     * 加密
     *
     * @param content
     * @return
     */
    public static String encode(String content) {
        return encode(content, KEY);
    }

    /**
     * 解密
     *
     * @param content
     * @return
     */
    public static String decode(String content) {
        return decode(content, KEY);
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param key     加密秘钥
     * @return 加密之后
     */
    public static String encode(String content, String key) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(), 0, OFFSET);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, skey, iv);// 初始化
            byte[] result = cipher.doFinal(content.getBytes(CHARSET));
            return new BASE64Encoder().encode(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES（256）解密
     *
     * @param content 待解密内容
     * @param key     解密密钥
     * @return 解密之后
     */
    public static String decode(String content, String key) {
        try {
            SecretKeySpec skewy = new SecretKeySpec(key.getBytes(), ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(), 0, OFFSET);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, skewy, iv);// 初始化
            byte[] result = cipher.doFinal(new BASE64Decoder().decodeBuffer(content));
            return new String(result, CHARSET); // 解密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) throws Exception {
//        String s = "hello world";
//        // 加密
//        System.out.println("加密前：" + s);
//        String encryptResultStr = encode(s);
//        System.out.println("加密后：" + encryptResultStr);
//        // 解密
//        System.out.println("解密后：" + decode(encryptResultStr));
//    }
}