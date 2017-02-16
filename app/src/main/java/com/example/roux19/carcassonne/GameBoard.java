package com.example.roux19.carcassonne;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

    private ArrayList<Bitmap> drawnTiles = new ArrayList<Bitmap>();
    private ArrayList<Integer> xCors = new ArrayList<Integer>();
    private ArrayList<Integer> yCors = new ArrayList<Integer>();

    CurrTile currTile;

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

    public void setCurrTile(CurrTile foo)
    {
        this.currTile = foo;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        for( int i = 0; i<drawnTiles.size(); i++)
        {
            canvas.drawBitmap(drawnTiles.get(i), null, new RectF(xCors.get(i),yCors.get(i),xCors.get(i)+200,yCors.get(i)+200),null);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        Random rand = new Random();

        xCors.add((Integer)((int)event.getX()-(int)event.getX()%200));
        yCors.add((Integer)((int)event.getY()-(int)event.getY()%200));
        drawnTiles.add(currTile.getCurrTile());
        currTile.setCurrTile(MainActivity.TILES[rand.nextInt(4)]);
        currTile.invalidate();
        this.invalidate();


        return false;
    }
}
