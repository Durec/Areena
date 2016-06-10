package com.mahtiyhti.areena;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;


public class Player extends GameObject {
    private Bitmap spritesheet;
    private boolean playing = true;
    private Animation animation = new Animation();
    private long startTime;
    public Float scaleX, scaleY;

    public Player(Bitmap res, int w, int h, int numFrames){
        x= 8;
        y= 8;
        height = h;
        width = w;

        Bitmap [] image = new Bitmap [numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++){
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    public void update(){

        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100) {
            startTime = System.nanoTime();
        }
        animation.update();
    }

    public void draw(Canvas canvas){
        final float scaleFactorX = (TaisteluPaneeli.WIDTH/8)/(width*1.f);
        final float scaleFactorY = (TaisteluPaneeli.HEIGHT/16)/(height*1.f);
        scaleX = scaleFactorX;
        scaleY = scaleFactorY;
        canvas.scale(scaleFactorX, scaleFactorY);
        canvas.drawBitmap(animation.getImage(),x,y,null);

    }
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
}
