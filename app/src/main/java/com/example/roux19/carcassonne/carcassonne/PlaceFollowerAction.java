package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

/**
 * Created by roux19 on 2/27/2017.
 *
 * a place follower acrion
 */
public class PlaceFollowerAction extends GameAction
{

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
    //getters
    public int getZone() {
        return zone;
    }
}
