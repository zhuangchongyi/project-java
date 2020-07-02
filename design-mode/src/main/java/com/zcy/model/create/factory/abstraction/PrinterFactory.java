package com.zcy.model.create.factory.abstraction;

import com.zcy.model.create.factory.Shape;

/**
 * @Author zhuangchongyi
 * @Description 定义一个形状工厂
 * 工厂模式是一种创建模式，因为此模式提供了更好的方法来创建对象。
 * 在工厂模式中，我们创建对象而不将创建逻辑暴露给客户端
 * @Date 2020/7/2 12:05
 */
public class PrinterFactory extends AbstractFactory {
    @Override
    Printer getPrinter(String type) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("paper")) {
            return new PaperPrinter();
        } else if (type.equalsIgnoreCase("web")) {
            return new WebPrinter();
        } else if (type.equalsIgnoreCase("Screen")) {
            return new ScreenPrinter();
        }
        return null;
    }

    @Override
    Shape getShape(String type) {
        return null;
    }
}
