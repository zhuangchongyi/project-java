package com.zcy.model.create.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhuangchongyi
 * @Description 原型模式
 * 原型模式是创建模式之一。
 * 原型模式有助于创建具有更好性能的重复对象。
 * 在原型模式中，将返回一个现有对象的克隆，而不是创建新的对象。
 * 我们使用原型设计模式，如果创建一个新对象的成本是昂贵和资源密集型
 * @Date 2020/7/2 12:49
 */
public class ShapeProtoType {

    private static Map<String, Shape> shapeMap = new HashMap<>();

    public static Shape getShapeMap(String shapeId) {
        Shape shape = shapeMap.get(shapeId);
        return (Shape) shape.clone();
    }

    public static void init() {
        Circle circle = new Circle();
        circle.setId("1");
        shapeMap.put(circle.getId(), circle);

        Square square = new Square();
        square.setId("2");
        shapeMap.put(square.getId(), square);

        Rectangle rectangle = new Rectangle();
        rectangle.setId("3");
        shapeMap.put(rectangle.getId(), rectangle);
    }
}
