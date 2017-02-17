package com.example.roux19.carcassonne;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.R.drawable;
import android.view.View;

import java.util.jar.Attributes;

/**
 * @author Pouya Rad, Malcolm Roux, Sean Tan, Jake Galves
 */
public class CurrTile extends SurfaceView implements View.OnTouchListener
{

    boolean isHighlighted = false; //keeps track of if the tile should be highlighted
    public Bitmap currentTile = BitmapFactory.decodeResource(getResources(), R.drawable.one); //get our first bitmap
    Paint highlightColor = new Paint(); //our hilight color

    public CurrTile(Context context)
    {
        super(context);
        highlightColor.setColor(0x7FFF0000); //set our highlight color (transparent red)
        setWillNotDraw(false);
    }

    public CurrTile(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        highlightColor.setColor(0x7FFF0000); //set our highlight color (transparent red)
        setWillNotDraw(false);
    }

    public void setCurrTile(Bitmap tile)
    {
        currentTile = tile;
    } //set the current tile ok

    public Bitmap getCurrTile()
    {
        return currentTile;
    } //give the current tile

    /**
     * onDraw
     * draw approprite current tile
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK); //pretty sure this does nothing usefull
        canvas.drawBitmap(currentTile, null, new RectF(0,0,400,400),null); //draw the current bitmap
        if(isHighlighted) canvas.drawRect(0,0,400,400,highlightColor); //draw highlight overlay if appropriate


    }

    /**
     * onTouch
     * highlights the tile when you touch it
     * also dehighlights
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch( View v, MotionEvent event )
    {
        isHighlighted = !isHighlighted; //wow malcolm is soooooooo smart
        this.invalidate(); //draw me
        return false;
    }
}
