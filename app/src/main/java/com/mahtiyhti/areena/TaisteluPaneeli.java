package com.mahtiyhti.areena;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class TaisteluPaneeli extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 141;
    public static final int HEIGHT = 229;
    private boolean initialize = true;
    private MainThread thread;
    private Background bg;
    public Player player;
    public Player focus;
    private Button alas, vasen, oikea, ylos;
    private Float scaleX, scaleY;

    public TaisteluPaneeli(Context context){
        super(context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);


        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter<1000)
        {
            counter++;
            try {thread.setRunning(false);
                thread.join();
                retry = false;

            }catch(InterruptedException e){e.printStackTrace();}

        }
    }

    public int getLeveys(){
        return getWidth();
    }
    public int getKorkeus(){
        return getHeight();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.tausta));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.morko), 66, 40, 3);

        //we can safely start the gaame loop
        thread.setRunning(true);
        thread.start();
    }

    public void drawMovementButtons(Integer x, Integer  y, Canvas canvas){
        if(initialize) {
            alas = new Button(84, 84, BitmapFactory.decodeResource(getResources(), R.drawable.alas));
            alas.setPosition(x, y);
            oikea = new Button(84, 84, BitmapFactory.decodeResource(getResources(), R.drawable.oikea));
            oikea.setPosition(x + 86, y);
            vasen = new Button(84, 84, BitmapFactory.decodeResource(getResources(), R.drawable.vasen));
            vasen.setPosition(x - 86, y);
            ylos = new Button(84, 84, BitmapFactory.decodeResource(getResources(), R.drawable.ylos));
            ylos.setPosition(x, y - 86);
            initialize = false;
        }

        alas.draw(canvas);
        oikea.draw(canvas);
        vasen.draw(canvas);
        ylos.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if (event.getAction()==MotionEvent.ACTION_DOWN){
            return true;
        }

        if(event.getAction()==MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();

            if (alas.btn_rect.contains(x, y) && focus != null) {
                focus.setY(focus.getY() + focus.getHeight());

            } else if (oikea.btn_rect.contains(x, y) && focus != null) {
                focus.setX(focus.getX() + focus.getWidth());

            } else if (vasen.btn_rect.contains(x, y) && focus != null) {
                focus.setX(focus.getX() - focus.getWidth());

            } else if (ylos.btn_rect.contains(x, y) && focus != null) {
                focus.setY(focus.getY() - focus.getHeight());

            }else if (player.getRectangle().contains(x/scaleX/player.scaleX, y/scaleY/player.scaleY)) {
                this.focus = player;

            }else if(!player.getRectangle().contains(x/scaleX/player.scaleX, y/scaleY/player.scaleY)){
                this.focus=null;
            }
            update();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        if(player.getPlaying() && focus==player) {
            player.update();
        }
    }

    @Override
    public void draw(Canvas canvas){
        int width = getWidth();
        int height = getHeight();
        final float scaleFactorX = width/(WIDTH*1.f);
        final float scaleFactorY = height/(HEIGHT*1.f);
        scaleX = scaleFactorX;
        scaleY = scaleFactorY;
        if(canvas!=null){
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState);
            drawMovementButtons(width/2, height-90, canvas);

        }
    }

}
