package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.infoMsg.GameState;
import com.example.roux19.carcassonne.game.infoMsg.TimerInfo;

import java.util.ArrayList;

/**
 * Created by roux19 on 2/20/2017.
 */
public class CarcassonneState extends GameState
{

    //game state variables
    private Tile[][] board;
    private Tile currTile;
    private int plyrTurn;
    private int tileRemaining;
    private ArrayList<Integer> scores;

    /**
     * CarcassonneState
     * creates deep copy with all parameters of the game state
     * @param
     */
    public CarcassonneState( Tile[][] initBoard, int initPlyrTurn, int initTileRemaining,
                             Tile initCurrTile, ArrayList<Integer> initScores)
    {
        for( int i = 0; i < initBoard.length; i++)
        {
            for ( int j = 0; j < initBoard[0].length; j++)
            {
                board[i][j] = new Tile(initBoard[i][j]);
            }
        }

        for( int i = 0; i < scores.size(); i++)
        {
            scores.add(initScores.get(i));
        }

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
        }


        plyrTurn = initState.getPlyrTurn();
        tileRemaining = initState.getTileRemaining();
        currTile = new Tile(initState.getCurrTile());
    }


    //getters
    public Tile[][] getBoard(){ return board; }

    public Tile getCurrTile(){ return currTile; }

    public int getPlyrTurn(){ return plyrTurn; }

    public int getTileRemaining(){ return tileRemaining; }

    public ArrayList<Integer> getScores(){ return scores; }

    //setters
    public void setBoard( Tile[][] newBoard ){ board = newBoard; }

    public void setCurrTile( Tile newCurrTile ){ currTile = newCurrTile; }

    public void setPlyrTurn( int newPlyrTurn ){ plyrTurn = newPlyrTurn; }

    public void setTileRemaining( int newTileRemaining ){ tileRemaining = newTileRemaining; }

    public void setScores( ArrayList<Integer> newScores ){ scores = newScores; }
}
