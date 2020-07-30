package com.zcy.common.utils.file;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CopyFileUtil {
//    public static void main(String[] args) {
//        String path = new File(CopyFileUtil.class.getResource("/").getPath()).getPath().concat("/source.rar");
//        String targetPath = System.getProperty("user.dir").concat("/source/source.rar");
//        ioCopyFile(path, targetPath);
//    }

    /**
     * io方式复制文件
     *
     * @param sourcePath 源数据
     * @param targetPath 目标数据
     */
    private static void ioCopyFile(String sourcePath, String targetPath) {
        long startTime = System.currentTimeMillis();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(sourcePath);
            out = new FileOutputStream(targetPath);
            byte[] data = new byte[1024];
            int len = -1;
            while ((len = in.read(data)) != -1) {
                out.write(data, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        long time = System.currentTimeMillis() - startTime;
        System.out.println("execute time: " + time);
    }

    /**
     * nio方式复制文件
     *
     * @param sourcePath 源数据
     * @param targetPath 目标数据
     */
    private static void nioCopyFile(String sourcePath, String targetPath) {
        long startTime = System.currentTimeMillis();
        try {
            FileChannel inChannel = FileChannel.open(Paths.get(sourcePath), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get(targetPath), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis() - startTime;
        System.out.println("execute time: " + time);
    }
}
