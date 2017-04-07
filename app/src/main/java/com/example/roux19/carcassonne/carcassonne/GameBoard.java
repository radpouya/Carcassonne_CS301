package com.example.roux19.carcassonne.carcassonne;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.example.roux19.carcassonne.R;
import com.example.roux19.carcassonne.game.GameMainActivity;

/**
 * @author Pouya Rad, Malcolm Roux, Sean Tan, Jake Galves
 *
 * used to draw the board
 */
public class GameBoard extends SurfaceView
{

    CarcassonneState gameState; //reference to gui player game state
    GameMainActivity myActivity; //reference to gui activity
    Paint[] plyrPaints = new Paint[5];

    public GameBoard(Context context)
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

    public GameBoard(Context context, AttributeSet attrs)
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
     * called to draw view
     * draws all tiles of the game board
     *
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        if(gameState == null )  return; //gotta have something

        //iterate thorugh and draw bitmaps based on x and y coordinates
        for( int i = 0; i<gameState.getBoard().length; i++)
        {
            for( int j = 0; j<gameState.getBoard()[0].length; j++)
            {
                //cant draw a nothing
                if(gameState.getBoard()[i][j] != null) {

                    gameState.getBoard()[i][j].drawTile(i,j,200,canvas,myActivity,plyrPaints);

                }
            }


        }
    }

    //setters
    public void setState( CarcassonneState newGameState ) { this.gameState = newGameState; }

    public void setMyActivity(GameMainActivity myActivity) {
        this.myActivity = myActivity;
    }
}
