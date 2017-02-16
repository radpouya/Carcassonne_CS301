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
 * Created by galves19 on 2/15/2017.
 */
public class CurrTile extends SurfaceView implements View.OnTouchListener
{

    boolean isHighlighted = false;
    public Bitmap currentTile = BitmapFactory.decodeResource(getResources(), R.drawable.one);
    Paint highlightColor = new Paint();

    public CurrTile(Context context)
    {
        super(context);
        highlightColor.setColor(0x7FFF0000);
        setWillNotDraw(false);
    }

    public CurrTile(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        highlightColor.setColor(0x7FFF0000);
        setWillNotDraw(false);
    }

    public void setCurrTile(Bitmap tile)
    {
        currentTile = tile;
    }

    public Bitmap getCurrTile()
    {
        return currentTile;
    }
    @Override
    public void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(currentTile, null, new RectF(0,0,400,400),null);
        if(isHighlighted) canvas.drawRect(0,0,400,400,highlightColor);


    }

    @Override
    public boolean onTouch( View v, MotionEvent event )
    {
        isHighlighted = !isHighlighted; //wow malcolm is soooooooo smart
        this.invalidate();
        return false;
    }
}
