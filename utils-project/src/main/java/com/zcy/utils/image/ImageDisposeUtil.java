package com.zcy.utils.image;

import com.zcy.common.constants.FileSuffixTypeConstant;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

public class ImageDisposeUtil {

//    public static void main(String[] args) {
//        String path = ImageDisposeUtil.class.getResource("/").getPath().concat("girl.jpg");
//        String base64 = imageToBase64(path);
//        System.out.println(base64ToStream(base64));
//    }

    /**
     * 图片转base64编码
     *
     * @param imgPpath 图片路径
     * @return
     */
    private static String imageToBase64(String imgPpath) {
        File file = new File(imgPpath);
        if (!file.isFile() || !file.exists())
            return null;
        byte[] data = null;
        InputStream in = null;
        // 读取图片字节流
        try {
            in = new FileInputStream(imgPpath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(Objects.requireNonNull(data));
    }

    /**
     * base64字符串转图片
     *
     * @param base64Code
     * @return
     */
    private static String base64ToStream(String base64Code) {
        BASE64Decoder decoder = new BASE64Decoder();
        InputStream in = null;
        String path = null;
        try {
            byte[] bytes = decoder.decodeBuffer(base64Code);
            in = new ByteArrayInputStream(bytes);
            BufferedImage bi = ImageIO.read(in);
            path = System.getProperty("user.dir").concat("/source/base64ToStream.jpg");
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            ImageIO.write(bi, FileSuffixTypeConstant.JPG, file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        return path;
    }


}
