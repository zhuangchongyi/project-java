package com.zcy.model.structure.bridge;

class ColorPrinter implements Printer{
    @Override
    public void print(int radius, int x, int y) {
        System.out.println("Color: " + radius +", x: " +x+", "+ y +"]");
    }
}
