package com.zcy.model.create.prototype;

public class Rectangle extends Shape {
    public Rectangle() {
        type = "rectangle";
    }

    @Override
    void draw() {
        System.out.println("rectangle draw");
    }
}
