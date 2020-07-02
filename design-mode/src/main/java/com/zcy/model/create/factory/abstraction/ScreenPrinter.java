package com.zcy.model.create.factory.abstraction;

public class ScreenPrinter implements Printer{
    @Override
    public void print() {
        System.out.println("屏幕打印");
    }
}
