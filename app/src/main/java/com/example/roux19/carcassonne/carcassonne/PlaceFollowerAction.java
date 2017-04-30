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
 * a place follower action
 */
public class PlaceFollowerAction extends GameAction
{

    public static final long serialVersionUID = 69011340113401134L;

    private int zone; //the touch event corresponds to a specific zone

    /**
     * Follower
     * create a place follower action
     * @param initZone
     * @param player
     */
    public PlaceFollowerAction(int initZone, GamePlayer player )
    {
        super(player);
        zone = initZone;
    }

    public int getZone() {
        return zone;
    }

}
