package com.rivalrebels.client.guihelper;

public class Rectangle {
    public int xMin;
    public int xMax;
    public int yMin;
    public int yMax;

    public Rectangle(int x, int y, int w, int h) {
        this.xMin = x;
        this.xMax = x + w;
        this.yMin = y;
        this.yMax = y + h;
    }

    public boolean isVecInside(Vector vec) {
        return vec.x >= this.xMin && vec.x <= this.xMax && vec.y >= this.yMin && vec.y <= this.yMax;
    }
}