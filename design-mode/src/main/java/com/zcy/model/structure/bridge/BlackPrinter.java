package com.zcy.model.structure.bridge;

class BlackPrinter implements Printer{
    @Override
    public void print(int radius, int x, int y) {
        System.out.println("Black: " + radius +", x: " +x+", "+ y +"]");
    }
}
