package com.example.roux19.carcassonne.carcassonne;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.roux19.carcassonne.R;
import com.example.roux19.carcassonne.game.GameHumanPlayer;
import com.example.roux19.carcassonne.game.GameMainActivity;
import com.example.roux19.carcassonne.game.infoMsg.GameInfo;

/**
 * Created by roux19 on 2/27/2017.
 */
public class HumanPlayer extends GameHumanPlayer implements View.OnTouchListener
{

    private CurrTile currTileView;

    private GameBoard gameBoardView;

    private Button rotateLeftAndCancel;

    private Button rotateRightAndEndTurn;

    private CarcassonneState state;

    private GameMainActivity myActivity;

    public static final Bitmap[] TILES = new Bitmap[4];

    public HumanPlayer( String name )
    {
        super(name);
    }

    protected void updateDisplay()
    {

    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {

        //uhhhhhh
        //activity.onCreate(savedInstanceState);
        activity.setContentView(R.layout.activity_main);

        //attach widgets
        currTileView = (CurrTile)activity.findViewById(R.id.curTile);
        gameBoardView = (GameBoard)activity.findViewById(R.id.gameBoard);
        rotateLeftAndCancel = (Button)activity.findViewById(R.id.rotateLeft);
        rotateRightAndEndTurn = (Button)activity.findViewById(R.id.rotateRight);

        //set listeners
        rotateRightAndEndTurn.setOnClickListener(new RotateRightAndEndTurn());
        rotateLeftAndCancel.setOnClickListener(new RotateLeftAndCancel());
        currTileView.setOnTouchListener(currTileView);
        gameBoardView.setOnTouchListener(gameBoardView);


        gameBoardView.setCurrTile(currTileView); //give gameBoard a reference to current tile

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    //listener to rotate right
    private class RotateRightAndEndTurn implements View.OnClickListener
    {
        /**
         * onClick
         * rotates the current tile when the rotate right button is pressed
         * uses previous rotation to find new rotation
         * @param v
         *
         */

        @Override
        public void onClick(View v)
        {
            currTileView.setRotation(currTileView.getRotation()+90);
        }
    }

    //listener to rotate left
    private class RotateLeftAndCancel implements View.OnClickListener
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
            currTileView.setRotation(currTileView.getRotation()-90);
        }
    }
}
