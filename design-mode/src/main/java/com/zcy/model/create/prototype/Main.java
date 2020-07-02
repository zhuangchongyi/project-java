package com.zcy.model.create.prototype;

public class Main {
    public static void main(String[] args) {
        ShapeProtoType.init();
        Circle shape = (Circle) ShapeProtoType.getShapeMap("1");
        System.out.println(shape.getType());
        shape.draw();
        Square shape1 = (Square) ShapeProtoType.getShapeMap("2");
        System.out.println(shape1.getType());
        shape1.draw();
        Rectangle shape2 = (Rectangle) ShapeProtoType.getShapeMap("3");
        System.out.println(shape2.getType());
        shape2.draw();
    }
}
