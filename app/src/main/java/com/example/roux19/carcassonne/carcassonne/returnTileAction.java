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
 * return tile action
 * we know the naming convention is off
 * refactoring makes committing impossible
 */
public class returnTileAction  extends GameAction
{

    public static final long serialVersionUID = 420420420420420L;

    public returnTileAction(GamePlayer player)
    {
        super(player);
    }

}
