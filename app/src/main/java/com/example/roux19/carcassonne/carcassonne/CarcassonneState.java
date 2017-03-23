package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.infoMsg.GameState;
import com.example.roux19.carcassonne.game.infoMsg.TimerInfo;

import java.util.ArrayList;

/**
 * Created by roux19 on 2/20/2017.
 *
 * holds all information of the game
 * if someone flips a table this will let us recreate it and then some
 */
public class CarcassonneState extends GameState
{

    //keep track of what phase of the turn we are in
    //useful and responsible static final variables
    public static final char PIECE_PHASE = 'p';
    public static final char FOLLOWER_PHASE = 'f';
    public static final char END_TURN_PHASE = 'e';

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
     * creates the intial settup of a game state
     */
    public CarcassonneState( )
    {
        board = new Tile[128][128];
        currTile = null;
        plyrTurn = 0;
        tileRemaining = 50;
        xCurrTile = 0;
        yCurrTile = 0;
        turnPhase = PIECE_PHASE;
    }

    /**
     * CarcassoneeState
     * creastes a full carcassonee state form parameters
     * i dont think we will ever use this but who cares
     * @param initBoard
     * @param initTileRemaining
     * @param initPlyrTurn
     * @param initCurrTile
     * @param initScores
     * @param initRemainingFollowers
     * @param initXCurrTile
     * @param initYCurrTile
     * @param initTurnPhase
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
        if (initState.board != null) {
            board = new Tile[initState.board.length][initState.board[0].length];
            for (int i = 0; i < initState.getBoard().length; i++) {
                for (int j = 0; j < initState.getBoard()[0].length; j++) {
                    if (initState.board[i][j] != null) {
                        board[i][j] = new Tile(initState.getBoard()[i][j]);
                    }
                }
            }
        }

        if (initState.scores != null) {
            for (int i = 0; i < initState.scores.size(); i++) {
                scores.add(initState.getScores().get(i));
                remainingFollowers.add(initState.getRemainingFollowers().get(i));
            }
        }

        xCurrTile = initState.getxCurrTile();
        yCurrTile = initState.getyCurrTile();

        turnPhase = initState.getTurnPhase();
        plyrTurn = initState.getPlyrTurn();
        tileRemaining = initState.getTileRemaining();
        if (initState.currTile != null) {
            currTile = new Tile(initState.getCurrTile());
        }
    }

    /**
     * addPlayers
     * adds the game state varibles that depend on the number of players
     * used at the game start
     * @param players
     */
    public void addPlayers(GamePlayer[] players)
    {
        for(int i = 0; i < players.length; i++)
        {
            scores.add(0);
            remainingFollowers.add(7);
        }
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

    public void setRemainingFollowers( ArrayList<Integer> newRemainingFollowers)
    { remainingFollowers = newRemainingFollowers; }

    public void setScores( ArrayList<Integer> newScores ){ scores = newScores; }

    public void setxCurrTile(int xCurrTile) { this.xCurrTile = xCurrTile; }

    public void setyCurrTile(int yCurrTile) { this.yCurrTile = yCurrTile; }

    public void setTurnPhase(char turnPhase) { this.turnPhase = turnPhase; }
}
