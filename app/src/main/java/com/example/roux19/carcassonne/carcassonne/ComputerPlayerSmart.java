package com.example.roux19.carcassonne.carcassonne;

import android.graphics.Point;

import com.example.roux19.carcassonne.game.GameComputerPlayer;
import com.example.roux19.carcassonne.game.infoMsg.GameInfo;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

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

        if( state.getTurnPhase() == 'p' )
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
            TreeMap<Point, Integer> freqMoves = new TreeMap<>();
            for(Point p : possibleMoves) {
                if(freqMoves.containsKey(p)) {
                    int freq = freqMoves.get(p);
                    freq++;
                    freqMoves.put(p, freq);
                } else {
                    freqMoves.put(p, 1);
                }
            }
            tryToPlacePeice(possibleMoves, state);
            possibleMoves.clear();
        }
        else if( state.getTurnPhase() == 'f' )
        {
            tryToPlaceFollower(state);
        }
        else if( state.getTurnPhase() == 'e' )
        {
            game.sendAction(new EndTurnAction(this));
        }
    }

    private boolean tryToPlaceFollower( CarcassonneState state )
    {
        Random r = new Random();
        int zoneToPlace = r.nextInt(13);
        int areaToPlace = state.getCurrTile().getAreaIndexFromZone(zoneToPlace);
        if (state.getCurrTile().isPlaceable( areaToPlace, state.getxCurrTile(), state.getyCurrTile(), state,
                new ArrayList<Area>()) && state.getRemainingFollowers().get( this.playerNum ) > 0 )
        {
            game.sendAction( new PlaceFollowerAction(zoneToPlace, this));
            return true;
        }
        else
        {
            game.sendAction( new EndTurnAction(this) );
            return false;
        }


    }

    private boolean tryToPlacePeice( ArrayList<Point> possibleMoves, CarcassonneState state )
    {
        Random r = new Random();
        int randStart = r.nextInt(possibleMoves.size());

        for( int i = 0; i < possibleMoves.size(); i++ ) {
            Point move = possibleMoves.get(randStart);

            if (state.isLegalMove(move.x, move.y) ) {
                PlacePieceAction ppa = new PlacePieceAction(move.x,move.y,this);
                game.sendAction(ppa);
                return true;
            }

            randStart = (randStart+1)%possibleMoves.size();
        }

        game.sendAction(new rotateAction(true, this));
        return false;
    }
}
