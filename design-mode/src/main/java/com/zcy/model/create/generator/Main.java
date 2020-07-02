package com.zcy.model.create.generator;

public class Main {
    public static void main(String[] args) {
        MainWindow window = WindowBuilder.createWindow();
        System.out.println(window.getMenu());
        System.out.println(window.getToolBar());
    }
}
