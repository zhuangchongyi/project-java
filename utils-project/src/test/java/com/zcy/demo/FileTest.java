package com.zcy.demo;

import java.io.File;
import java.io.FileFilter;

public class FileTest {
    public static void main(String[] args) {
        // 获取文件系统中可用根目录的列表
        File[] files = File.listRoots();
        for (File file : files) {
            System.out.println(file.getPath());
        }
        System.out.println("----------------------------------");
        String path = System.getProperty("user.dir");
        File file = new File(path);
        File[] files1 = file.listFiles();
        for (File file1 : files1) {
            System.out.println(file1.getPath());
        }

        System.out.println("=====================================");
        FileFilter fileFilter = file1 -> {
            String name = file1.getName();
            String filename = name.toLowerCase();
            if (name.equals(".git") || name.endsWith(".idea") || filename.endsWith(".iml")) {
                return false;
                }
            return true;
        };
        String path2 = System.getProperty("user.dir");
        File file2 = new File(path2);
        File[] files2 = file2.listFiles(fileFilter);
        for (File file1 : files2) {
            System.out.println(file1.getPath());
        }

        System.out.println(System.getProperty("line.separator"));
    }
}
