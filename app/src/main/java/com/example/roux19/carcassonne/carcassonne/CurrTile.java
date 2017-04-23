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

import java.io.Serializable;

/**
 * @author Pouya Rad, Malcolm Roux, Sean Tan, Jake Galves
 *
 * Used to draw top right cur tile
 */
public class CurrTile extends SurfaceView implements Serializable
{

    private static final long serialVersionUID = 533712345696969L;
    private CarcassonneState gameState; //game state fed by gui player
    private GameMainActivity myActivity; //activity fed by gui player
    private Paint[] plyrPaints = new Paint[5];

    public CurrTile(Context context)
    {
        super(context);
        setWillNotDraw(false);
        for ( int i = 0; i < plyrPaints.length; i++ ) plyrPaints[i] = new Paint();
        plyrPaints[0].setColor(0xFF000000);
        plyrPaints[1].setColor(0xFFFF0000);
        plyrPaints[2].setColor(0xFF00FF00);
        plyrPaints[3].setColor(0xFF0000FF);
        plyrPaints[4].setColor(0xFFFFFF00);
    }

    public CurrTile(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        setWillNotDraw(false);
        for ( int i = 0; i < plyrPaints.length; i++ ) plyrPaints[i] = new Paint();
        plyrPaints[0].setColor(0xFF000000);
        plyrPaints[1].setColor(0xFFFF0000);
        plyrPaints[2].setColor(0xFF00FF00);
        plyrPaints[3].setColor(0xFF0000FF);
        plyrPaints[4].setColor(0xFFFFFF00);
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

        gameState.getCurrTile().drawTile(0,0,400,canvas,myActivity,plyrPaints);
    }

    //setters
    public void setState(CarcassonneState newGameState) {
        this.gameState = newGameState;
    }

    public void setMyActivity(GameMainActivity myActivity) {
        this.myActivity = myActivity;
    }
}
