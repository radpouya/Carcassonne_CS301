package com.example.roux19.carcassonne;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

/**
 * @author Pouya Rad, Malcolm Roux, Sean Tan, Jake Galves
 *
 * (10 pts) made the rotate left button work
 *  (5pts) also made rotate right button work
 * (10 pts) touching the current tile highlights it
 *  (5pts) touching it again unhighlights it
 * (20 pts) touching the game board places the current tile in a grid with the appropriate rotation
 * (disscussed with Nuxol over email)
 */
public class MainActivity extends AppCompatActivity {

    //initialize widgets
    CurrTile currTile;
    GameBoard gameBoard;
    Button rotateLeft;
    Button rotateRight;

    //create array of bitmaps of tiles
    public static final Bitmap[] TILES = new Bitmap[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //uhhhhhh
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //attach widgets
        currTile = (CurrTile)findViewById(R.id.curTile);
        gameBoard = (GameBoard)findViewById(R.id.gameBoard);
        rotateLeft = (Button)findViewById(R.id.rotateLeft);
        rotateRight = (Button)findViewById(R.id.rotateRight);

        //set listeners
        rotateRight.setOnClickListener(new RotateRight());
        rotateLeft.setOnClickListener(new RotateLeft());
        currTile.setOnTouchListener(currTile);
        gameBoard.setOnTouchListener(gameBoard);

        //initialize tiles
        TILES[0] = BitmapFactory.decodeResource(getResources(), R.drawable.one);
        TILES[1] = BitmapFactory.decodeResource(getResources(), R.drawable.two);
        TILES[2] = BitmapFactory.decodeResource(getResources(), R.drawable.three);
        TILES[3] = BitmapFactory.decodeResource(getResources(), R.drawable.road);

        gameBoard.setCurrTile(currTile); //give gameBoard a reference to current tile

        //not sure this does anything... redraws it at the start?
        currTile.invalidate();
        gameBoard.invalidate();
    }

    //listener to rotate right
    private class RotateRight implements View.OnClickListener
    {
        /**
         * onClick
         * rotates the current tile when the rotate right button is pressed
         * uses previous rotation to find new rotation
         * @param v
         */
        @Override
        public void onClick(View v)
        {
            currTile.setRotation(currTile.getRotation()+90);
        }
    }

    //listener to rotate left
    private class RotateLeft implements View.OnClickListener
    {
        /**
         * onClick
         * rotates the current tile when the rotate left button is pressed
         * uses previous rotation to find new rotation
         * @param v
         */
        @Override
        public void onClick(View v)
        {
            currTile.setRotation(currTile.getRotation()-90);
        }
    }
}
