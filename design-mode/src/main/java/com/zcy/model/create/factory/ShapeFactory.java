package com.zcy.model.create.factory;

/**
 * @Author zhuangchongyi
 * @Description 定义一个形状工厂
 *   工厂模式是一种创建模式，因为此模式提供了更好的方法来创建对象。
 *   在工厂模式中，我们创建对象而不将创建逻辑暴露给客户端
 * @Date 2020/7/2 12:05
 */
public class ShapeFactory {
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circular();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }


    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape1 = shapeFactory.getShape("CIRCLE");
        shape1.draw();
        Shape shape2 = shapeFactory.getShape("RECTANGLE");
        shape2.draw();
        Shape shape3 = shapeFactory.getShape("SQUARE");
        shape3.draw();
    }
}
