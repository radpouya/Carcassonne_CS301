// Jake Galves, Pouya Rad, Malcolm Roux, Sean Tan
// CS 301 A - Spring 2017
// Dr. Andrew Nuxoll
// Team Project - Carcassonne
// HW Assignment 4 Final Release
// 1 May 2017

package com.example.roux19.carcassonne.carcassonne;

import android.graphics.Point;
import com.example.roux19.carcassonne.game.GameComputerPlayer;
import com.example.roux19.carcassonne.game.infoMsg.GameInfo;
import java.util.ArrayList;
import java.util.Random;


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

        //if we have a state of our game
        if( !(info instanceof CarcassonneState) ) return;

        CarcassonneState state = (CarcassonneState)info;

        //if it is our turn
        if ( state.getPlyrTurn() != this.playerNum) return;

        //wait.....
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //check if the state is in the place tile phase
        if( state.getTurnPhase() == 'p' )
        {
            ArrayList<Point> possibleMoves = new ArrayList<Point>();
            /*loops through the game board, checks adjacent empty squares
            and stores their coordinates in the array list.*/
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

            //tries to place a piece randomly
            tryToPlacePeice(possibleMoves, state);
            possibleMoves.clear();
        }
        else if( state.getTurnPhase() == 'f' )
        {
            //tries to place a follower randomly
            tryToPlaceFollower(state);
        }
        else if( state.getTurnPhase() == 'e' )
        {
            //ends turns
            game.sendAction(new EndTurnAction(this));
        }
    }

    /**
     * Tries to place a follower in a random zone on a tile
     * if illegal move then just end turn
     * @param state
     * @return
     */
    private boolean tryToPlaceFollower( CarcassonneState state )
    {
        //find random area to place
        Random r = new Random();
        int zoneToPlace = r.nextInt(13);
        int areaToPlace = state.getCurrTile().getAreaIndexFromZone(zoneToPlace);
        //if it is a legal move and we have follwers remaining
        if (state.getCurrTile().isPlaceable( areaToPlace,
                state.getxCurrTile(), state.getyCurrTile(), state,
                new ArrayList<Area>()) &&
                state.getRemainingFollowers().get( this.playerNum ) > 0 )
        {
            //send a PlaceFollwerAction
            game.sendAction( new PlaceFollowerAction(zoneToPlace, this));
            return true;
        }
        else
        {
            //send a EndTurnAction
            game.sendAction( new EndTurnAction(this) );
            return false;
        }
    }

    /**
     * Tries to place a piece in one of the possible move coordinates.
     * @param possibleMoves
     * @param state
     * @return
     */
    private boolean tryToPlacePeice( ArrayList<Point> possibleMoves,
                                     CarcassonneState state )
    {
        //find a ramdon start point
        Random r = new Random();
        int randStart = r.nextInt(possibleMoves.size());

        //loops through all possible moves
        for( int i = 0; i < possibleMoves.size(); i++ ) {
            Point move = possibleMoves.get(randStart);

           //if this possible move is legal, then place piece
            if (state.isLegalMove(move.x, move.y) ) {
                PlacePieceAction ppa = new PlacePieceAction(move.x,move.y,this);
                game.sendAction(ppa);
                return true;
            }

            //increment randStart
            randStart = (randStart+1)%possibleMoves.size();
        }

        // if we havent placed a piece then end turn
        game.sendAction(new rotateAction(true, this));
        return false;
    }
}
