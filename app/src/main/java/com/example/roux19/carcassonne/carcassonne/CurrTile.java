package com.example.roux19.carcassonne.carcassonne;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.example.roux19.carcassonne.R;
import com.example.roux19.carcassonne.game.GameMainActivity;

/**
 * @author Pouya Rad, Malcolm Roux, Sean Tan, Jake Galves
 *
 * Used to draw top right cur tile
 */
public class CurrTile extends SurfaceView
{

    private CarcassonneState gameState; //game state fed by gui player
    private GameMainActivity myActivity; //activity fed by gui player

    public CurrTile(Context context)
    {
        super(context);
        setWillNotDraw(false);
    }

    public CurrTile(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        setWillNotDraw(false);
    }


    /**
     * onDraw
     * draw approprite current tile
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        if( gameState == null ) return; //gotta have a game state

        //rotate the tile appropraitle
        /**
         External Citation (see GameBoard)
         Date: 16 February 2017
         Problem: Rotate a bitmap
         Resource: http://stackoverflow.com/questions/8722359/scale-rotate-bitmap-using-
         matrix-in-android
         Solution: Used ammended example code
         */
        Bitmap toBeDrawn = BitmapFactory.decodeResource(myActivity.getResources(), gameState.getCurrTile().getBitmapRes() );
        Matrix mat = new Matrix();
        float rotation = gameState.getCurrTile().getRotation();
        mat.postRotate(rotation);
        toBeDrawn = Bitmap.createBitmap(toBeDrawn,0,0,toBeDrawn.getWidth(),toBeDrawn.getHeight(),mat,true);

        //draw da tile
        canvas.drawBitmap(toBeDrawn, null, new RectF(0,0,400,400),null);
    }

    //setters
    public void setState(CarcassonneState newGameState) {
        this.gameState = newGameState;
    }

    public void setMyActivity(GameMainActivity myActivity) {
        this.myActivity = myActivity;
    }
}
