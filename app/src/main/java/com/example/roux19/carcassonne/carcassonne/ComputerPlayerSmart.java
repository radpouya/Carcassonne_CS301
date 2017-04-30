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
import java.util.HashMap;

/**
 * Created by roux19 on 2/27/2017.
 */
public class ComputerPlayerSmart extends GameComputerPlayer {

   //keeps track of rotations
    int numRotations;
   //keeps track of what frequency we are at
    int tryingFreq;

    public ComputerPlayerSmart( String name )
    {
        super(name);
        tryingFreq = 4;
        numRotations = 0;

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
            //get an array of all possible moves
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
            //HashMap to keep track of how often a specific move is in PossibleMoves
            HashMap<Point, Integer> freqMoves = new HashMap<Point, Integer>();
            //transpose PossibleMoves into FreqMoves keeping track of frequency
            for(Point p : possibleMoves) {
               //if this move is in FreqMoves
                if(!freqMoves.isEmpty() && freqMoves.containsKey(p)) {
                   //Increment the frequency stored for this move
                    int freq = freqMoves.get(p);
                    freq++;
                    freqMoves.put(p, freq);
                } else {
                    //otherwise add the move with a frequency of 1
                    freqMoves.put(p, 1);
                }
            }
            //try to place piece with respect to frequency
            loopThroughFrequencies(freqMoves, state);
            possibleMoves.clear();
        }
        else if( state.getTurnPhase() == 'f' )
        {
            //try to place best follower
            tryToPlaceFollower(state);
        }
        else if( state.getTurnPhase() == 'e' )
        {
            //end turn
            game.sendAction(new EndTurnAction(this));
        }
    }

    /**
     * try to place a follower in a completed area
     * if not possible, try to place a follower that would yield best score
     * if not possible, end turn
     * @param state
     * @return
     */
    private boolean tryToPlaceFollower( CarcassonneState state )
    {

        if( state.getRemainingFollowers().get(this.playerNum) < 1 )
        {
            game.sendAction(new EndTurnAction(this));
            return false;
        }

        if( !tryToPlaceFollowerCompleted( state ) ) {
            if ( !tryToPlaceFollowerScore(state) ) {
                game.sendAction(new EndTurnAction(this));
                return false;
            }
        }

        return true;

    }

    /**
     *try to place a tile on certain peices that show up most frequently in possible moves
     * decrement frequency if tile is not placed
     * @param freqMoves
     * @param state
     * @return
     */
    private boolean loopThroughFrequencies( HashMap<Point, Integer> freqMoves, CarcassonneState state )
    {
        //if we do not have a place that has this frequency then decrement and rotat
        if( !freqMoves.containsValue(tryingFreq) ) {
            tryingFreq--;
            //important to rotate so it receives a state again
            game.sendAction(new rotateAction(true, this));
            return true;
        }
        else
        {
            //try this frequency
            tryToPlacePiece(freqMoves, state);
            return true;
        }
    }

    private boolean tryToPlacePiece( HashMap<Point, Integer> freqMoves, CarcassonneState state )
    {
        //for all possible moves
        for( Point move : freqMoves.keySet()) {
            //if it has the current trying frequency
            if( freqMoves.get(move) == tryingFreq )
            {
                //if it is a legal move
                if (state.isLegalMove(move.x, move.y) ) {
                    //place the piece
                    PlacePieceAction ppa = new PlacePieceAction(move.x,move.y,this);
                    //reset the tryingFreq and numRotations
                    tryingFreq = 4;
                    numRotations = 0;
                    game.sendAction(ppa);
                    return true;
                }
            }
        }


        //if we have tried every rotation
        if( numRotations == 3 ) {
            //decrement the trying freq and reset the rotations
            tryingFreq--;
            numRotations=0;
        }
        else //otherwise
        {
            //we are going to try the next rotation
            numRotations++;
        }
        //if we haven't placed a tile rotate the peice
        game.sendAction(new rotateAction(true, this));
        return true;
    }

    /**
     * try to place a follower on already completed areas
     * @param state
     * @return
     * true for having placed a follower
     * false for no follower placed
     */
    public boolean tryToPlaceFollowerCompleted( CarcassonneState state ) {

        //loop through the areas of cur tile
        for( Area area : state.getCurrTile().getTileAreas() )
        {
            //helper
            ArrayList<Area> touchedAreas = new ArrayList<Area>();


            //if this area is completed
            if( area.isCompleted(state,state.getxCurrTile(),state.getyCurrTile(),touchedAreas)) {
                touchedAreas.clear();
                int areaToPlace = state.getCurrTile().getAreaIndexFromZone(area.getOccZones().get(0));
                //if this area is legal to place on
                if(state.getCurrTile().isPlaceable(areaToPlace,state.getxCurrTile(),
                        state.getyCurrTile(),state,touchedAreas)) {
                    //send the place peice action
                    game.sendAction(new PlaceFollowerAction(area.getOccZones().get(0), this));
                    return true;
                }

            }
        }

        return false;

    }

    /**
     * tries to place a follower on the highest scoring area on cur tile
     * @param state
     * @return
     * true for having place a follower
     * false for no follower placed
     */
    public boolean tryToPlaceFollowerScore( CarcassonneState state ) {

        //array to store the scores of the areas in cur tile
        int[] scoresOfAreas = new int[state.getCurrTile().getTileAreas().size()];
        int i = 0;
        for( Area area: state.getCurrTile().getTileAreas()) {
            //find and set the scores of areas
            ArrayList<Area> areasToScore = new ArrayList<Area>();
            area.createPropagation(state,state.getxCurrTile(),state.getyCurrTile(),areasToScore);
            scoresOfAreas[i] = area.getScore(areasToScore);
            i++;
        }

        //array to keep track of the index of each area
        int[] areaIndex =  new int[scoresOfAreas.length];
        for( int a = 0; a < areaIndex.length; a++ ) areaIndex[a] = a;
        // this is just going to be 1,2,3,...

        //insertion sort on scores of areas while keeping areaIndex as a parrallel array
        for( int a = 0; a < areaIndex.length; a++ ) {
            int temp =  scoresOfAreas[a];
            int tempIndex = areaIndex[a];
            int j;
            for( j = a+1; j < areaIndex.length; j++ ) {
                if( scoresOfAreas[j] > scoresOfAreas[a] ) {
                    areaIndex[a] = areaIndex[j];
                    scoresOfAreas[a] = scoresOfAreas[j];
                    areaIndex[j] = tempIndex;
                    scoresOfAreas[j] = temp;
                }
            }
        }

        //loop through index array which is sorted by score highest to lowest
        for( int index : areaIndex ) {
            ArrayList<Area> touchedAreas = new ArrayList<Area>();
            //if this is a legal move
            if(state.getCurrTile().isPlaceable(index,state.getxCurrTile(),state.getyCurrTile(),
                    state,touchedAreas)) {
                //send the action
                game.sendAction(new PlaceFollowerAction(
                        state.getCurrTile().getTileAreas().get(index).getOccZones().get(0), this));
                //return true for having placed a follower
                return true;
            }
        }

        //darn, we couldn't place a follower
        return false;
    }
}
