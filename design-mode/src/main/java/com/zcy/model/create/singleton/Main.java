package com.zcy.model.create.singleton;

public class Main {
    public static void main(String[] args) {
        MainWindow mw = MainWindow.getInstance();
        mw.showMessage();
        System.out.println(mw);
        MainWindow mw2 = MainWindow.getInstance();
        System.out.println(mw2);
    }
}
