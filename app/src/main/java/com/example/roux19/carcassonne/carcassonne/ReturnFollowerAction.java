package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

/**
 * Created by roux19 on 2/27/2017.
 *
 * return follower action
 */
public class ReturnFollowerAction extends GameAction
{

    public static final long serialVersionUID = 6969696969696969L;

    public ReturnFollowerAction(GamePlayer player)
    {
        super(player);
    }
}
