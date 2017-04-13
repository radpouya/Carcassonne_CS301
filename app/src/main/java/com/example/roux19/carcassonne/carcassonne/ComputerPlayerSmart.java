package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GameComputerPlayer;
import com.example.roux19.carcassonne.game.infoMsg.GameInfo;

/**
 * Created by roux19 on 2/27/2017.
 */
public class ComputerPlayerSmart extends GameComputerPlayer {

    public ComputerPlayerSmart( String name )
    {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if( !(info instanceof CarcassonneState) ) return;

        CarcassonneState state = (CarcassonneState)info;

        if ( state.getPlyrTurn() != this.playerNum) return;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
