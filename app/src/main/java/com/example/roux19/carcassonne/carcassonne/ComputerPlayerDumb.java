package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GameComputerPlayer;
import com.example.roux19.carcassonne.game.infoMsg.GameInfo;

/**
 * Created by roux19 on 2/27/2017.
 */
public class ComputerPlayerDumb extends GameComputerPlayer
{

    public ComputerPlayerDumb( String name )
    {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {

    }
}
