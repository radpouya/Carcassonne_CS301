package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

/**
 * Created by roux19 on 2/27/2017.
 *
 * send when someone wants to end the turn
 */
public class EndTurnAction extends GameAction
{

    public static final long serialVersionUID = 800858008580085L;

    public EndTurnAction(GamePlayer player)
    {
        super(player);
    }
}
