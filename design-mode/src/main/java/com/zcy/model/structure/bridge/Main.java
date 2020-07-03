package com.zcy.model.structure.bridge;

public class Main {
    public static void main(String[] args) {
        Shape redCircle = new Circle(new ColorPrinter(), 100, 100, 10);
        Shape blackCircle = new Circle(new BlackPrinter(), 100, 100, 10);

        redCircle.draw();
        blackCircle.draw();
    }
}
