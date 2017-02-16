package com.example.roux19.carcassonne;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    CurrTile currTile;
    GameBoard gameBoard;
    Button rotateLeft;
    Button rotateRight;
    public static final Bitmap[] TILES = new Bitmap[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currTile = (CurrTile)findViewById(R.id.curTile);
        gameBoard = (GameBoard)findViewById(R.id.gameBoard);
        rotateLeft = (Button)findViewById(R.id.rotateLeft);
        rotateRight = (Button)findViewById(R.id.rotateRight);


        rotateRight.setOnClickListener(new RotateRight());
        rotateLeft.setOnClickListener(new RotateLeft());
        currTile.setOnTouchListener(currTile);
        gameBoard.setOnTouchListener(gameBoard);

        TILES[0] = BitmapFactory.decodeResource(getResources(), R.drawable.one);
        TILES[1] = BitmapFactory.decodeResource(getResources(), R.drawable.two);
        TILES[2] = BitmapFactory.decodeResource(getResources(), R.drawable.three);
        TILES[3] = BitmapFactory.decodeResource(getResources(), R.drawable.road);

        gameBoard.setCurrTile(currTile);

        currTile.invalidate();
        gameBoard.invalidate();
    }

    private class RotateRight implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            currTile.setRotation(currTile.getRotation()+90);
        }
    }

    private class RotateLeft implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            currTile.setRotation(currTile.getRotation()-90);
        }
    }
}
