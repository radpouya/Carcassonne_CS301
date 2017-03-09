package com.example.roux19.carcassonne.carcassonne;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.example.roux19.carcassonne.carcassonne.CurrTile;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Pouya Rad, Malcolm Roux, Sean Tan, Jake Galves
 */
public class GameBoard extends SurfaceView implements View.OnTouchListener
{
    //arrays correspond to eachothers indexes
    private ArrayList<Bitmap> drawnTiles = new ArrayList<Bitmap>(); //array of bitmaps
    private ArrayList<Integer> xCors = new ArrayList<Integer>(); //array of x coordinates
    private ArrayList<Integer> yCors = new ArrayList<Integer>(); //array of y coordinates

    private float mx, my, origX, origY; //temp

    boolean isMove = false;

    CurrTile currTile; //reference to current tile

    public GameBoard(Context context)
    {
        super(context);
        setWillNotDraw(false);
    }

    public GameBoard(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        setWillNotDraw(false);
    }

    /**
     * setCurrTile
     * sets reference to CurrTile
     * @param foo
     */
    public void setCurrTile(CurrTile foo)
    {
        this.currTile = foo;
    }

    /**
     * onDraw
     * called to draw view
     * draws all bitmaps in array
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //iterate thorugh and draw bitmaps based on x and y coordinates
        for( int i = 0; i<drawnTiles.size(); i++)
        {
            canvas.drawBitmap(drawnTiles.get(i), null, new RectF(xCors.get(i),yCors.get(i),xCors.get(i)+200,yCors.get(i)+200),null);

        }
    }

    @Override
    /**
     * onTouch
     * allows the user to scroll on the surface view to move around the map and place pieces
     * @param v, e
     */
    public boolean onTouch( View v, MotionEvent e)
    {

        float curX, curY;



        switch (e.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mx = e.getX();
                my = e.getY();
                origX = e.getX();
                origY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if ( Math.abs(e.getX()-origX) > 50 || Math.abs(e.getY()-origY) > 50 ) { isMove = true; }
                curX = e.getX();
                curY = e.getY();
                v.scrollBy((int)(mx-curX), (int)(my-curY));
                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
                if (isMove) {
                    curX = e.getX();
                    curY = e.getY();
                    v.scrollBy((int) (mx - curX), (int) (my - curY));
                    isMove = false;
                    return false;
                }
                else {
                    return placePiece( v, e);
                }
        }

        return true;
    }

    /**
     * onTouch
     * adds appropriate bitmap to bitmap array
     * also adds x and y coordinates based on location of touch
     * @param v
     * @param event
     * @return
     */
    public boolean placePiece(View v, MotionEvent event)
    {
        Random rand = new Random(); //who cares

        int touchX = (int)(event.getX()+v.getScrollX());
        int touchY = (int)(event.getY()+v.getScrollY());

        int placeX = 0;
        int placeY = 0;

        if (touchX<0) { placeX = -200;}
        if (touchY<0) { placeY = -200;}

        //set coordinates
        xCors.add((Integer)(touchX-touchX%200+placeX)); //round down to nearest 200
        yCors.add((Integer)(touchY-touchY%200+placeY)); //round down to nearest 200

        /**
         * External Citation
         *  Date: 2/16/17
         *  Problem: rotate a bitmap to make another bitmap
         *  Resource: stackoverflow.com/questions/3440690/rotating-a-bitmap-using-matrix
         *  Solution: make a matrix, rotate the matrix, make a bitmap copy using the matrix*
         */
        Matrix mat = new Matrix();

        float rotation = currTile.getRotation();

        mat.postRotate(rotation);

        Bitmap draw = Bitmap.createBitmap(currTile.getCurrTile(), 0,0,
                currTile.getCurrTile().getWidth(), currTile.getCurrTile().getHeight(), mat, true);

        drawnTiles.add(draw); //add bitmap to array
        currTile.invalidate(); //redraw curTile
        this.invalidate(); //draw me like one of your french girls


        return false; //yeah, really a toss up between true and false... true makes it like draw continuously, like bad
    }

}
