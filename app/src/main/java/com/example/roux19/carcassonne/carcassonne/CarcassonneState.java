package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.infoMsg.GameState;
import com.example.roux19.carcassonne.game.infoMsg.TimerInfo;

import java.util.ArrayList;

/**
 * Created by roux19 on 2/20/2017.
 */
public class CarcassonneState extends GameState
{
    public static final char PIECE_PHASE = 'p';
    public static final char FOLLOWER_PHASE = 'f';

    //game state variables
    private Tile[][] board; //array of all placed tiles
    private Tile currTile; //reference to the current tile
    private int plyrTurn; //keeps track of whomes's turn it is
    private int tileRemaining; //amount of tiles to be placed before game ends
    private ArrayList<Integer> scores = new ArrayList<Integer>(); //score of each player
    private ArrayList<Integer> remainingFollowers = new ArrayList<Integer>(); //remaining followers for each player
    private int xCurrTile; //if curTile is already placed then saves xCor in board, -1 if not placed
    private int yCurrTile; //same as xCurrTile but with y coordinate
    private char turnPhase;

    /**
     * CarcassonneState
     * creates deep copy with all parameters of the game state
     * @param
     */
    public CarcassonneState( Tile[][] initBoard, int initTileRemaining, int initPlyrTurn,
                             Tile initCurrTile, ArrayList<Integer> initScores,
                             ArrayList<Integer> initRemainingFollowers,
                             int initXCurrTile, int initYCurrTile, char initTurnPhase)
    {
        for( int i = 0; i < initBoard.length; i++)
        {
            for ( int j = 0; j < initBoard[0].length; j++)
            {
                if ( initBoard[i][j] != null) {
                    board[i][j] = new Tile(initBoard[i][j]);
                }
            }
        }

        for( int i = 0; i < scores.size(); i++)
        {
            scores.add(initScores.get(i));
            remainingFollowers.add(initRemainingFollowers.get(i));
        }

        xCurrTile = initXCurrTile;
        yCurrTile = initYCurrTile;

        turnPhase = initTurnPhase;
        plyrTurn = initPlyrTurn;
        tileRemaining = initTileRemaining;
        currTile = new Tile(initCurrTile);
    }

    /**
     * CarcassonneState
     * creates a deep copy of another CarcassonneState
     * @param initState
     */
    public CarcassonneState( CarcassonneState initState )
    {
        for( int i = 0; i < initState.getBoard().length; i++)
        {
            for ( int j = 0; j < initState.getBoard()[0].length; j++)
            {
                board[i][j] = new Tile(initState.getBoard()[i][j]);
            }
        }

        for( int i = 0; i < scores.size(); i++)
        {
            scores.add(initState.getScores().get(i));
            remainingFollowers.add(initState.getRemainingFollowers().get(i));
        }

        xCurrTile = initState.getxCurrTile();
        yCurrTile = initState.getyCurrTile();

        turnPhase = initState.getTurnPhase();
        plyrTurn = initState.getPlyrTurn();
        tileRemaining = initState.getTileRemaining();
        currTile = new Tile(initState.getCurrTile());
    }


    //getters
    public Tile[][] getBoard(){ return board; }

    public Tile getCurrTile(){ return currTile; }

    public int getPlyrTurn(){ return plyrTurn; }

    public int getTileRemaining(){ return tileRemaining; }

    public char getTurnPhase(){ return turnPhase; }

    public ArrayList<Integer> getRemainingFollowers() { return remainingFollowers; }

    public ArrayList<Integer> getScores(){ return scores; }

    public int getxCurrTile() { return xCurrTile; }

    public int getyCurrTile() { return yCurrTile; }

    //setters
    public void setBoard( Tile[][] newBoard ){ board = newBoard; }

    public void setCurrTile( Tile newCurrTile ){ currTile = newCurrTile; }

    public void setPlyrTurn( int newPlyrTurn ){ plyrTurn = newPlyrTurn; }

    public void setTileRemaining( int newTileRemaining ){ tileRemaining = newTileRemaining; }

    public void setRemainingFollowers( ArrayList<Integer> newRemainingFollowers) { remainingFollowers = newRemainingFollowers; }

    public void setScores( ArrayList<Integer> newScores ){ scores = newScores; }

    public void setxCurrTile(int xCurrTile) { this.xCurrTile = xCurrTile; }

    public void setyCurrTile(int yCurrTile) { this.yCurrTile = yCurrTile; }

    public void setTurnPhase(char turnPhase) { this.turnPhase = turnPhase; }
}
