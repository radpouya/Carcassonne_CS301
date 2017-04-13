package com.example.roux19.carcassonne.carcassonne;

import android.graphics.BitmapFactory;

import com.example.roux19.carcassonne.R;
import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.LocalGame;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by roux19 on 2/27/2017.
 *
 * central hub of the game
 * receives actions and determines legality
 * modifies master gamestate
 * sends updated game states to plaeyers
 */
public class CarcassonneLocalGame extends LocalGame
{

    private CarcassonneState gameState; //master state of the game

    private ArrayList<Tile> tileDeck = new ArrayList<Tile>(); //list of possible tiles of the game

    public CarcassonneLocalGame( )
    {
        super();

        //hard coded initializers for the tile deck
        for( int i = 0; i < 16; i++ )
        {
            if ( i == 0 )
            {
                    char[] tempZones = {'c', 'f', 'r', 'f', 'f', 'f', 'f', 'f', 'r', 'f', 'c', 'c', 'r'};
                    int[] tempAreaProp = {0, 1, 2, 3, 3, 3, 3, 3, 2, 1, 0, 0, 2};
                for( int j = 0; j < 3; j++ ) {
                    tileDeck.add(new Tile(R.drawable.tile0, tempZones, tempAreaProp, 0));
                }
            }
            else if ( i == 1 )
            {
                char[] tempZones = {'c', 'f', 'r', 'f', 'f', 'r', 'f', 'f', 'f', 'f', 'c', 'c', 'f'};
                int[] tempAreaProp = {0, 1, 2, 3, 3, 2, 1, 1, 1, 1, 0, 0, 1};
                for( int j = 0; j < 3; j++ ) {
                    tileDeck.add(new Tile(R.drawable.tile1, tempZones, tempAreaProp, 0));
                }
            }
            else if ( i == 2 )
            {
                char[] tempZones = {'c', 'c', 'c', 'c', 'f', 'f', 'f', 'f', 'f', 'f', 'c', 'c', 'f'};
                int[] tempAreaProp = {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1};
                for( int j = 0; j < 3; j++ ) {
                    tileDeck.add(new Tile(R.drawable.tile2, tempZones, tempAreaProp, 0));
                }
            }
            else if ( i == 3 )
            {
                char[] tempZones = {'f', 'f', 'r', 'f', 'f', 'r', 'f', 'f', 'r', 'f', 'f', 'f', 'n'};
                int[] tempAreaProp = {0, 0, 1, 2, 2, 3, 4, 4, 5, 0, 0, 0, -1};
                for( int j = 0; j < 4; j++ ) {
                    tileDeck.add(new Tile(R.drawable.tile3, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 4) {
                char[] tempZones = {'c', 'c', 'c', 'c', 'f', 'f', 'f', 'c', 'c', 'c', 'c', 'c', 'c'};
                int[] tempAreaProp = {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0};
                for(int j = 0; j < 3; j++) {
                    tileDeck.add(new Tile(R.drawable.tile4, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 5) {
                char[] tempZones = {'f', 'c', 'c', 'c', 'f', 'f', 'f', 'c', 'c', 'c', 'f', 'f', 'c'};
                int[] tempAreaProp = {0, 1, 1, 1, 2, 2, 2, 1, 1, 1, 0, 0, 1};
                for(int j = 0; j < 1; j++) {
                    tileDeck.add(new Tile(R.drawable.tile5, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 6) {
                char[] tempZones = {'c', 'f', 'f', 'f', 'f', 'f', 'f', 'c', 'c', 'c', 'c', 'c', 'f'};
                int[] tempAreaProp = {0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 0, 0, 1};
                for(int j = 0; j < 2; j++) {
                    tileDeck.add(new Tile(R.drawable.tile6, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 7) {
                char[] tempZones = {'f', 'c', 'c', 'c', 'f', 'f', 'f', 'c', 'c', 'c', 'f', 'f', 'f'};
                int[] tempAreaProp = {0, 1, 1, 1, 0, 0, 0, 2, 2, 2, 0, 0, 0};
                for(int j = 0; j < 3; j++) {
                    tileDeck.add(new Tile(R.drawable.tile7, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 8) {
                char[] tempZones = {'c', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'c', 'c', 'f'};
                int[] tempAreaProp = {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1};
                for(int j = 0; j < 5; j++) {
                    tileDeck.add(new Tile(R.drawable.tile8, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 9) {
                char[] tempZones = {'c', 'f', 'f', 'f', 'f', 'r', 'f', 'f', 'r', 'f', 'c', 'c', 'f'};
                int[] tempAreaProp = {0, 1, 1, 1, 1, 2, 3, 3, 2, 1, 0, 0, 1};
                for(int j = 0; j < 3; j++) {
                    tileDeck.add(new Tile(R.drawable.tile9, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 10) {
                char[] tempZones = {'c', 'f', 'r', 'f', 'f', 'r', 'f', 'f', 'r', 'f', 'c', 'c', 'f'};
                int[] tempAreaProp = {0, 1, 2, 3, 3, 4, 5, 5, 6, 1, 0, 0, 1};
                for(int j = 0; j < 3; j++) {
                    tileDeck.add(new Tile(R.drawable.tile10, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 11) {
                char[] tempZones = {'c', 'c', 'c', 'c', 'f', 'r', 'f', 'f', 'r', 'f', 'c', 'c', 'f'};
                int[] tempAreaProp = {0, 0, 0, 0, 1, 2, 3, 3, 2, 1, 0, 0, 1};
                for(int j = 0; j < 3; j++) {
                    tileDeck.add(new Tile(R.drawable.tile11, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 12) {
                char[] tempZones = {'c', 'c', 'c', 'c', 'f', 'r', 'f', 'c', 'c', 'c', 'c', 'c', 'c'};
                int[] tempAreaProp = {0, 0, 0, 0, 1, 2, 3, 0, 0, 0, 0, 0, 0};
                for(int j = 0; j < 1; j++) {
                    tileDeck.add(new Tile(R.drawable.tile12, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 13) {
                char[] tempZones = {'f', 'f', 'r', 'f', 'f', 'r', 'f', 'f', 'r', 'f', 'f', 'r', 'n'};
                int[] tempAreaProp = {0, 0, 1, 2, 2, 3, 4, 4, 5, 6, 6, 7, -1};
                for(int j = 0; j < 1; j++) {
                    tileDeck.add(new Tile(R.drawable.tile13, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 14) {
                char[] tempZones = {'f', 'f', 'f', 'f', 'f', 'r', 'f', 'f', 'f', 'f', 'f', 'r', 'r'};
                int[] tempAreaProp = {0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 1, 1};
                for(int j = 0; j < 8; j++) {
                    tileDeck.add(new Tile(R.drawable.tile14, tempZones, tempAreaProp, 0));
                }
            }
            else if(i == 15) {
                char[] tempZones = {'f', 'f', 'r', 'f', 'f', 'r', 'f', 'f', 'f', 'f', 'f', 'f', 'r'};
                int[] tempAreaProp = {0, 0, 1, 2, 2, 1, 0, 0, 0, 0, 0, 0, 1};
                for(int j = 0; j < 9; j++) {
                    tileDeck.add(new Tile(R.drawable.tile15, tempZones, tempAreaProp, 0));
                }
            }
        }

        this.gameState = new CarcassonneState( ); //makes inital game state
        this.gameState.setCurrTile(randTile()); //sets the current tile to a rand tile
        this.gameState.getBoard()[64][64] = new Tile(tileDeck.get(0)); //places starting tile
    }

    @Override
    public void start( GamePlayer[] players )
    {
        super.start(players);
        gameState.addPlayers(players); //so that the game state knows how many players there are
        // only needed to initialize arrays of scores and follower counts
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // if there is no state to send, ignore
        if (gameState == null) {
            return;
        }
        CarcassonneState stateForPlayer = new CarcassonneState(gameState); // copy of state

        // send the modified copy of the state to the player
        p.sendInfo(stateForPlayer);
    }

    /**
     * choses a random tile
     * @return
     */
    private Tile randTile()
    {

        Random r = new Random();
        int i = r.nextInt(tileDeck.size()); //select a random tile
        Tile temp = new Tile(tileDeck.get(i)); //copy it
        tileDeck.remove(i); //remove it
        return temp; //return it

    }

    @Override
    protected boolean canMove(int playerIdx)
    {
        return gameState.getPlyrTurn() == playerIdx;
    }

    @Override
    protected String checkIfGameOver()
    {
        if ( tileDeck.isEmpty() )
        {
            gameState.endGameScore();
            int topScore = 0;
            int indexOfWinner = 0;
            for( int i = 0; i < gameState.getScores().size(); i++) {
                if( gameState.getScores().get(i) > topScore ) {
                    indexOfWinner = i;
                }
            }

            String winnerString = ""+playerNames[indexOfWinner]+" has won with a score of "
                    +gameState.getScores().get(indexOfWinner)+"!!!!!";
            return winnerString;
        }

        return null;
    }

    @Override
    protected boolean makeMove(GameAction action)
    {
        if ( action instanceof PlacePieceAction ){

            //retrieve action
            PlacePieceAction ppa = (PlacePieceAction)action;

            //check correct turn phase
            if(gameState.getTurnPhase() != CarcassonneState.PIECE_PHASE) return false;

            if (!gameState.isLegalMove(ppa.getxCor(), ppa.getyCor())) return false;

            //place da tile
            gameState.getBoard()[ppa.getxCor()][ppa.getyCor()] = gameState.getCurrTile();
            //set coordinates of tile
            gameState.setxCurrTile(ppa.getxCor());
            gameState.setyCurrTile(ppa.getyCor());
            //enter next phase
            gameState.setTurnPhase(CarcassonneState.FOLLOWER_PHASE);

            //action completed
            return true;

        }
        //allows you to enter follower phase and place a follower
        else if ( action instanceof PlaceFollowerAction ) {

            PlaceFollowerAction pfa = (PlaceFollowerAction)action;

            //if we are in ther correct phase
            if(gameState.getTurnPhase() != CarcassonneState.FOLLOWER_PHASE) return false;

            //if this player is out of followers
            if(gameState.getRemainingFollowers().get( getPlayerIdx(pfa.getPlayer())) < 1) return false;

            //get the index of the area we are placing on within the tile
            int indexOfTargetArea = gameState.getCurrTile().getAreaIndexFromZone( pfa.getZone() );

            //tells whether there is a follower conntedted to the area we are placing on (aka illegal move)
            ArrayList<Area> areas = new ArrayList<Area>(); //empty list for recursive algorithim
            if( !gameState.getCurrTile().isPlaceable(indexOfTargetArea, gameState.getxCurrTile(),
                    gameState.getyCurrTile(), gameState, areas) )
            {
                return false;
            }
            //please look at method comments to understand

            //places the follower in the area
            gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()].getTileAreas()
                    .get(indexOfTargetArea).setFollower( new Follower(pfa.getZone(), gameState.getPlyrTurn()));
            //decrements remaining followers
            gameState.getRemainingFollowers().set( getPlayerIdx(pfa.getPlayer()),
                    gameState.getRemainingFollowers().get( getPlayerIdx(pfa.getPlayer()))-1);
            //allows you to enter the end turn phase
            gameState.setTurnPhase(CarcassonneState.END_TURN_PHASE);

            return true;

        } //allows you to undo the peice phase and go back into the piece phase to replace
        // your piece
            else if ( action instanceof returnTileAction ) {

            //grab action
            returnTileAction rta = (returnTileAction) action;

            //make sure correct state
            if(gameState.getTurnPhase() != CarcassonneState.FOLLOWER_PHASE) return false;

            //erase the placed tile
            gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()] = null;

            //return to the place a peice phase
            gameState.setTurnPhase(CarcassonneState.PIECE_PHASE);

            return true;

        } //allows you to undo the end turn phase and go back into the follower phase to replace
        // your follower
        else if ( action instanceof ReturnFollowerAction ) {

            //grab action
            ReturnFollowerAction rfa = (ReturnFollowerAction) action;

            //ensure correct phase
            if(gameState.getTurnPhase() != CarcassonneState.END_TURN_PHASE) return false;

            //set all the areas within the tile to have no follower
            for( int i = 0; i < gameState.getCurrTile().getTileAreas().size(); i++) {
                gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()]
                        .getTileAreas().get(i).setFollower(null);
            }

            //increment remaining followers
            gameState.getRemainingFollowers().set( getPlayerIdx(rfa.getPlayer()),
                    gameState.getRemainingFollowers().get( getPlayerIdx(rfa.getPlayer()))+1);

            //return to follower phase
            gameState.setTurnPhase(CarcassonneState.FOLLOWER_PHASE);

            return true;
        }//tells state if a player has entered the end turn phase and ended their turn and  player
        // turn index +1
        else if ( action instanceof EndTurnAction ) {

            //grab action
            EndTurnAction eta = (EndTurnAction) action;

            //the only state you are not allowed to end the turn in is the peice phase
            if(gameState.getTurnPhase() == CarcassonneState.PIECE_PHASE) return false;

            //appropriately score the curr tile
            gameState.getCurrTile().endTurnScore( gameState, gameState.getxCurrTile(), gameState.getyCurrTile() );
            //go to next player's turn, loop around if necessary
            gameState.setPlyrTurn((gameState.getPlyrTurn()+1)%this.players.length);
            //get another tile
            gameState.setCurrTile(this.randTile());
            //go to starting phase of next turn
            gameState.setTurnPhase(CarcassonneState.PIECE_PHASE);


            return true;
        }//allows player whose turn it is to rotate a tile
        else if ( action instanceof rotateAction ) {

            //grab action
            rotateAction ra = (rotateAction) action;

            //if we are in the placr peice phase
            if( gameState.getTurnPhase() != CarcassonneState.PIECE_PHASE) return false;

            //rotate the tile, please look at meathod algorithim
            gameState.getCurrTile().rotateTile( ra.isClockwise() );

            return true;

        }

        return false;
    }

    public String[] getPlayerNames()
    {
        return playerNames;
    }
}
