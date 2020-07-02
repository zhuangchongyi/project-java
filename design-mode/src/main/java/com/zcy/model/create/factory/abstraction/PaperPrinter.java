package com.zcy.model.create.factory.abstraction;

public class PaperPrinter implements Printer{
    @Override
    public void print() {
        System.out.println("纸打印");
    }
}
