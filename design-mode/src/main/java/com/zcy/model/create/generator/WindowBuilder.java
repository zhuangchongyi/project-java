package com.zcy.model.create.generator;


/**
 * @Author zhuangchongyi
 * @Description 生成器模式
 * 生成器模式用于使用简单对象创建复杂对象。它从小而简单的对象逐步创建更大的对象。
 * 生成器模式是另一个创建模式
 * @Date 2020/7/2 12:42
 */
public class WindowBuilder {
    public static MainWindow createWindow() {
        MainWindow mainWindow = new MainWindow();
        ToolBar toolBar = new ToolBar();
        Menu menu = new Menu();
        mainWindow.setMenu(menu);
        mainWindow.setToolBar(toolBar);
        return mainWindow;
    }
}
