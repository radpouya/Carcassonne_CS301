// Jake Galves, Pouya Rad, Malcolm Roux, Sean Tan
// CS 301 A - Spring 2017
// Dr. Andrew Nuxoll
// Team Project - Carcassonne
// HW Assignment 4 Final Release
// 1 May 2017

package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

/**
 * Created by roux19 on 2/27/2017.
 *
 * place a piece action
 */
public class PlacePieceAction extends GameAction {

    public static final long serialVersionUID = 8008513580085135L;

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
