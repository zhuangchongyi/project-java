package com.zcy.model.create.factory.abstraction;

public class WebPrinter implements Printer{
    @Override
    public void print() {
        System.out.println("网页打印");
    }
}
