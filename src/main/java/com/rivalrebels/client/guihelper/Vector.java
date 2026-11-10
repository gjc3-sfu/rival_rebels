package com.rivalrebels.client.guihelper;

public class Vector {
   public int x;
   public int y;

   public Vector(int X, int Y) {
      this.x = X;
      this.y = Y;
   }

   public boolean isInsideRectangle(Rectangle rec) {
      return this.x >= rec.xMin && this.x <= rec.xMax && this.y >= rec.yMin && this.y <= rec.yMax;
   }
}