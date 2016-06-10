package com.mahtiyhti.areena;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;
    protected Matrix matrix = new Matrix();
    protected RectF rect;

    public void setX(int x){
        this.x = x;
    }

    public void setY (int y){
        this.y =y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public RectF getRectangle(){
        rect = new RectF(0, 0, (float)width, (float)height);
        matrix.setTranslate(this.x, this.y);
        matrix.mapRect(rect);
        return rect;}
}
