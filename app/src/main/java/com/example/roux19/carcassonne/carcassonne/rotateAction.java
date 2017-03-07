package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

/**
 * Created by roux19 on 2/27/2017.
 */
public class rotateAction extends GameAction
{

    private boolean isClockwise;

    public rotateAction(boolean initIsClockWise, GamePlayer player)
    {
        super(player);

        isClockwise = initIsClockWise;
    }

    public boolean isClockwise() {
        return isClockwise;
    }
}
