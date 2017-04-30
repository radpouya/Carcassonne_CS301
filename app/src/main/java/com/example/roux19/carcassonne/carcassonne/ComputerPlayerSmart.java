package com.example.roux19.carcassonne.carcassonne;

import android.graphics.Point;

import com.example.roux19.carcassonne.game.GameComputerPlayer;
import com.example.roux19.carcassonne.game.infoMsg.GameInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

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
     *
     * @param freqMoves
     * @param state
     * @return
     */
    private boolean loopThroughFrequencies( HashMap<Point, Integer> freqMoves, CarcassonneState state )
    {
        if( !freqMoves.containsValue(tryingFreq) ) {
            tryingFreq--;
            game.sendAction(new rotateAction(true, this));
        }
        else
        {
            tryToPlacePiece(freqMoves, state);
            return true;
        }
        return false;
    }

    private boolean tryToPlacePiece( HashMap<Point, Integer> freqMoves, CarcassonneState state )
    {
        for( Point move : freqMoves.keySet()) {
            if( freqMoves.get(move) == tryingFreq )
            {
                if (state.isLegalMove(move.x, move.y) ) {
                    PlacePieceAction ppa = new PlacePieceAction(move.x,move.y,this);
                    tryingFreq = 4;
                    numRotations = 0;
                    game.sendAction(ppa);
                    return true;
                }
            }
        }


        if( numRotations == 3 ) {
            tryingFreq--;
            numRotations=0;
        }
        else
        {
            numRotations++;
        }
        game.sendAction(new rotateAction(true, this));
        return true;
    }

    public boolean tryToPlaceFollowerCompleted( CarcassonneState state ) {

        for( Area area : state.getCurrTile().getTileAreas() )
        {
            ArrayList<Area> touchedAreas = new ArrayList<Area>();

            if( area.isCompleted(state,state.getxCurrTile(),state.getyCurrTile(),touchedAreas)) {
                touchedAreas.clear();
                int areaToPlace = state.getCurrTile().getAreaIndexFromZone(area.getOccZones().get(0));
                if(state.getCurrTile().isPlaceable(areaToPlace,state.getxCurrTile(),state.getyCurrTile(),state,touchedAreas)) {
                    game.sendAction(new PlaceFollowerAction(area.getOccZones().get(0), this));
                    return true;
                }

            }
        }

        return false;

    }

    public boolean tryToPlaceFollowerScore( CarcassonneState state ) {

        int[] scoresOfAreas = new int[state.getCurrTile().getTileAreas().size()];
        int i = 0;
        for( Area area: state.getCurrTile().getTileAreas()) {
            ArrayList<Area> areasToScore = new ArrayList<Area>();
            area.createPropagation(state,state.getxCurrTile(),state.getyCurrTile(),areasToScore);
            scoresOfAreas[i] = area.getScore(areasToScore);
            i++;
        }

        int[] areaIndex =  new int[scoresOfAreas.length];
        for( int a = 0; a < areaIndex.length; a++ ) areaIndex[a] = a;

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

        for( int index : areaIndex ) {
            ArrayList<Area> touchedAreas = new ArrayList<Area>();
            if(state.getCurrTile().isPlaceable(index,state.getxCurrTile(),state.getyCurrTile(),state,touchedAreas)) {
                game.sendAction(new PlaceFollowerAction(state.getCurrTile().getTileAreas().get(index).getOccZones().get(0), this));
                return true;
            }
        }




        return false;
    }
}
