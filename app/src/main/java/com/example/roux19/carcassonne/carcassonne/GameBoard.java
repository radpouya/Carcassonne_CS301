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
                    //rotate blah blah blah
                    /**
                     External Citation
                     Date: 16 February 2017
                     Problem: Rotate a bitmap
                     Resource: http://stackoverflow.com/questions/8722359/scale-rotate-bitmap-using-
                     matrix-in-android
                     Solution: Used ammended example code
                     */
                    Bitmap toBeDrawn = BitmapFactory.decodeResource(myActivity.getResources(), gameState.getBoard()[i][j].getBitmapRes());
                    Matrix mat = new Matrix();
                    float rotation = gameState.getBoard()[i][j].getRotation();
                    mat.postRotate(rotation);
                    toBeDrawn = Bitmap.createBitmap(toBeDrawn,0,0,toBeDrawn.getWidth(),toBeDrawn.getHeight(),mat,true);

                    //draw it
                    canvas.drawBitmap(toBeDrawn, null, new RectF(200*i,200*j,200*i+200,200*j+200),null);

                    //draw the possible follower
                    for( int k = 0; k < gameState.getBoard()[i][j].getTileAreas().size(); k++) {
                        if ( gameState.getBoard()[i][j].getTileAreas().get(k).getFollower() != null )
                        {
                            //keep track of top left and will be adjusted through zones
                            int xCor = 200*i;
                            int yCor = 200*j;
                            //the zone in which the follower is
                            int fz = gameState.getBoard()[i][j].getTileAreas().get(k).getFollower().getPos();
                            //adjusting the x y corrdinate based on zone
                            //(note) we should 1. use a meathod in follower 2. use case notation
                            if ( fz == 0 ) {
                                xCor += 40;
                                yCor += 10;
                            }
                            else if ( fz == 1 ) {
                                xCor += 10;
                                yCor += 40;
                            }
                            else if ( fz == 2 ) {
                                xCor += 10;
                                yCor += 90;
                            }
                            else if ( fz == 3 ) {
                                xCor += 10;
                                yCor += 140;
                            }
                            else if ( fz == 4 ) {
                                xCor += 40;
                                yCor += 170;
                            }
                            else if ( fz == 5 ) {
                                xCor += 90;
                                yCor += 170;
                            }
                            else if ( fz == 6 ) {
                                xCor += 140;
                                yCor += 170;
                            }
                            else if ( fz == 7 ) {
                                xCor += 170;
                                yCor += 140;
                            }
                            else if ( fz == 8 ) {
                                xCor += 170;
                                yCor += 90;
                            }
                            else if ( fz == 9 ) {
                                xCor += 170;
                                yCor += 40;
                            }
                            else if ( fz == 10 ) {
                                xCor += 140;
                                yCor += 10;
                            }
                            else if ( fz == 11 ) {
                                xCor += 90;
                                yCor += 10;
                            }
                            else if ( fz == 12 ) {
                                xCor += 90;
                                yCor += 90;
                            }
                            Paint paint = new Paint(0xFF000000);
                            //draw the follower
                            canvas.drawRect(xCor, yCor, xCor+20, yCor+20, paint);
                        }
                    }

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
