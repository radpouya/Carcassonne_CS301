package com.example.roux19.carcassonne.carcassonne;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

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

    private Button getRotateRightAndEndTurn;

    private CarcassonneState state;

    private GameMainActivity myActivity;

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

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
