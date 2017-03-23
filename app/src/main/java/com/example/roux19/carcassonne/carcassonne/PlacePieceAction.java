package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

/**
 * Created by roux19 on 2/27/2017.
 *
 * place a peice action
 */
public class PlacePieceAction extends GameAction {

    //board coordinates
    private int xCor;
    private int yCor;

    /**
     * PlacePeiceAction
     * creates a place peice action
     * @param initXCor
     * @param initYCor
     * @param initPlayer
     */
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
