package com.zcy.model.structure.bridge;
/**
 * @Author zhuangchongyi
 * @Description 桥接模式
 * 桥接模式将定义与其实现分离。它是一种结构模式。
 * 此模式涉及充当桥接的接口。桥使得具体类与接口实现者类无关。
 * 这两种类型的类可以改变而不影响对方。
 * @Date 2020/7/2 14:27
 */
class Circle extends Shape {
    private int x, y, radius;

    public Circle(Printer p, int x, int y, int radius) {
        super(p);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        print.print(radius, x, y);
    }
}
