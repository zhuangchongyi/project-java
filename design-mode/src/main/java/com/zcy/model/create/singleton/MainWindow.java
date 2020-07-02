package com.zcy.model.create.singleton;

/**
 * @Author zhuangchongyi
 * @Description 单例模式
 * 单例模式是一种创建模式。
 * 这种模式只涉及一个单独的类，它负责创建自己的对象。
 * 该类确保只创建单个对象。
 * 这个类提供了一种访问其唯一对象的方法
 * @Date 2020/7/2 12:28
 */
public class MainWindow {
    private static MainWindow instance = new MainWindow();

    // 私有化改造方法
    private MainWindow() {
    }

    public static MainWindow getInstance() {
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello World!");
    }

}
