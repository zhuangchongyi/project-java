package com.zcy.model.create.factory.abstraction;

import com.zcy.model.create.factory.Shape;

/**
 * @Author zhuangchongyi
 * @Description 定义一个抽象类来获取打印机和形状对象的工厂
 * @Date 2020/7/2 12:18
 */
public abstract class AbstractFactory {
    abstract Printer getPrinter(String type);

    abstract Shape getShape(String shape);
}
