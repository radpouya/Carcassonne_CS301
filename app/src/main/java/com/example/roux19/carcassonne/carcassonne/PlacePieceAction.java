package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

/**
 * Created by roux19 on 2/27/2017.
 */
public class PlacePieceAction extends GameAction {

    private int xCor;
    private int yCor;
    //allowed piece to be placed
    public PlacePieceAction(int initXCor, int initYCor, GamePlayer initPlayer)
    {
        super(initPlayer);

        xCor = initXCor;
        yCor = initYCor;
    }

    //getters

    public int getxCor() {
        return xCor;
    }

    public int getyCor() {
        return yCor;
    }
}
