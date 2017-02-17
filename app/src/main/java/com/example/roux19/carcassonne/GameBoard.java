package com.example.roux19.carcassonne;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by galves19 on 2/15/2017.
 */
public class GameBoard extends SurfaceView implements View.OnTouchListener
{
    //arrays correspond to eachothers indexes
    private ArrayList<Bitmap> drawnTiles = new ArrayList<Bitmap>(); //array of bitmaps
    private ArrayList<Integer> xCors = new ArrayList<Integer>(); //array of x coordinates
    private ArrayList<Integer> yCors = new ArrayList<Integer>(); //array of y coordinates

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

    /**
     * onTouch
     * adds appropriate bitmap to bitmap array
     * also adds x and y coordinates based on location of touch
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        Random rand = new Random(); //who cares

        //set coordinates
        xCors.add((Integer)((int)event.getX()-(int)event.getX()%200)); //round down to nearest 200
        yCors.add((Integer)((int)event.getY()-(int)event.getY()%200)); //round down to nearest 200

        /**
         * External Citation
         *  Date: 2/16/17
         *  Problem: rotate a bitmap to make another bitmap
         *  Resource: stackoverflow.com/questions/3440690/rotating-a-bitmap-using-matrix
         *  Solution: make a matrix, rotate the matrix, make a bitmap copy using the matrix
         */
        Matrix mat = new Matrix();

        float rotation = currTile.getRotation();

        mat.postRotate(rotation);

        Bitmap draw = Bitmap.createBitmap(currTile.getCurrTile(), 0,0, currTile.getCurrTile().getWidth(), currTile.getCurrTile().getHeight(), mat, true);

        drawnTiles.add(draw); //add bitmap to array
        currTile.setCurrTile(MainActivity.TILES[rand.nextInt(4)]); //change current tile to random tile
        currTile.invalidate(); //redraw curTile
        this.invalidate(); //draw me like one of your french girls


        return false; //yeah, really a toss up between true and false... true makes it like draw continuously, like bad
    }
}
