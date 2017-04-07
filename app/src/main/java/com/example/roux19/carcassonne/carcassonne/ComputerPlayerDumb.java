package com.example.roux19.carcassonne.carcassonne;

import android.graphics.Point;

import com.example.roux19.carcassonne.game.GameComputerPlayer;
import com.example.roux19.carcassonne.game.infoMsg.GameInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

/**
 * Created by roux19 on 2/27/2017.
 */
public class ComputerPlayerDumb extends GameComputerPlayer
{
    boolean needsToRotate = false;

    public ComputerPlayerDumb( String name )
    {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        Random random = new Random();

        if( !(info instanceof CarcassonneState) ) return;

        CarcassonneState state = (CarcassonneState)info;

        if ( state.getPlyrTurn() != this.playerNum) return;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if( needsToRotate )
        {
            needsToRotate = false;
            game.sendAction(new rotateAction(true, this));
        }
        else if( state.getTurnPhase() == 'p' )
        {
            ArrayList<Point> possibleMoves = new ArrayList<Point>();
            for( int i = 1; i<127; i++ )
            {
                for( int j = 1; j<127; j++ )
                {
                    if( state.getBoard()[i][j] != null )
                    {
                        if ( state.getBoard()[i-1][j] == null )
                        {
                            possibleMoves.add(new Point(i-1,j));
                        }
                        if ( state.getBoard()[i+1][j] == null )
                        {
                            possibleMoves.add(new Point(i+1,j));
                        }
                        if ( state.getBoard()[i][j-1] == null )
                        {
                            possibleMoves.add(new Point(i,j-1));
                        }
                        if ( state.getBoard()[i][j+1] == null )
                        {
                            possibleMoves.add(new Point(i,j+1));
                        }
                    }
                }
            }

            tryToPlace(possibleMoves, state);
        }
        else if( state.getTurnPhase() == 'f' || state.getTurnPhase() == 'e' )
        {
            game.sendAction(new EndTurnAction(this));
        }
    }

    private boolean tryToPlace( ArrayList<Point> possibleMoves, CarcassonneState state )
    {
        for( Point move : possibleMoves ) {
            if (state.isLegalMove(move.x, move.y) ) {
                PlacePieceAction ppa = new PlacePieceAction(move.x,move.y,this);
                game.sendAction(ppa);
                return true;
            }
        }

        needsToRotate = true;
        return false;
    }
}
