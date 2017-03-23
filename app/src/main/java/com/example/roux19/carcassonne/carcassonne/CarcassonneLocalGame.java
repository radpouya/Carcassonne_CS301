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
        for( int i = 0; i < 4; i++ )
        {
            if ( i == 0 )
            {
                char[] tempZones = {'c', 'f', 'r', 'f', 'f', 'f', 'f', 'f', 'r', 'f', 'c', 'c', 'r'};
                int[] tempAreaProp = {0, 1, 2, 3, 3, 3, 3, 3, 2, 1, 0, 0, 2};
                tileDeck.add( new Tile( R.drawable.tile0, tempZones, tempAreaProp, 0));
            }
            else if ( i == 1 )
            {
                char[] tempZones = {'c', 'f', 'r', 'f', 'f', 'r', 'f', 'f', 'f', 'f', 'c', 'c', 'r'};
                int[] tempAreaProp = {0, 1, 2, 3, 3, 2, 1, 1, 1, 1, 0, 0, 2};
                tileDeck.add( new Tile( R.drawable.tile1, tempZones, tempAreaProp, 0));
            }
            else if ( i == 2 )
            {
                char[] tempZones = {'c', 'c', 'c', 'c', 'f', 'f', 'f', 'f', 'f', 'f', 'c', 'c', 'f'};
                int[] tempAreaProp = {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1};
                tileDeck.add( new Tile( R.drawable.tile2, tempZones, tempAreaProp, 0));
            }
            else if ( i == 3 )
            {
                char[] tempZones = {'f', 'f', 'r', 'f', 'f', 'r', 'f', 'f', 'r', 'f', 'f', 'f', 'n'};
                int[] tempAreaProp = {0, 0, 1, 2, 2, 3, 4, 4, 5, 0, 0, 0, -1};
                tileDeck.add( new Tile( R.drawable.tile3, tempZones, tempAreaProp, 0));
            }
        }

        this.gameState = new CarcassonneState( ); //makes inital game state
        this.gameState.setCurrTile(randTile()); //sets the current tile to a rand tile
        this.gameState.getBoard()[5][5] = randTile(); //places starting tile
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

        return new Tile(tileDeck.get(i)); //copies it
        // (note) if we want the tile deck to diminish and not use repeats of the same tile
        // we could not copy it and keep track of if it is used within the tile class
        // we could not select the used tiles in this selection process

    }

    @Override
    protected boolean canMove(int playerIdx)
    {
        return gameState.getPlyrTurn() == playerIdx;
    }

    @Override
    protected String checkIfGameOver()
    {
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

            //check to make sure requested square is empty
            if(gameState.getBoard()[ppa.getxCor()][ppa.getyCor()]!= null) return false;

            //get references to tiles above, below, left and right
            Tile topTile = gameState.getBoard()[ppa.getxCor()][ppa.getyCor() - 1];
            Tile botTile = gameState.getBoard()[ppa.getxCor()][ppa.getyCor() + 1];
            Tile leftTile = gameState.getBoard()[ppa.getxCor() - 1][ppa.getyCor()];
            Tile rightTile = gameState.getBoard()[ppa.getxCor() + 1][ppa.getyCor()];

            //check to make sure tile borders at least tile0 tile
            if(topTile == null && botTile == null && leftTile == null && rightTile == null)
            {
                return false;
            }

            if(leftTile != null) //if left tile exists
            {
                //make sure zones line up
                if(leftTile.getZones()[9] != gameState.getCurrTile().getZones()[1])
                {
                    return false;
                }
                if(leftTile.getZones()[8] != gameState.getCurrTile().getZones()[2])
                {
                    return false;
                }

                if(leftTile.getZones()[7] != gameState.getCurrTile().getZones()[3])
                {
                    return false;
                }

            }
            if(rightTile != null) //if right tile exists
            {
                //make sure zones line up
                if(rightTile.getZones()[1] != gameState.getCurrTile().getZones()[9])
                {
                    return false;
                }
                if(rightTile.getZones()[2] != gameState.getCurrTile().getZones()[8])
                {
                    return false;
                }

                if(rightTile.getZones()[3] != gameState.getCurrTile().getZones()[7])
                {
                    return false;
                }

            }
            if(topTile != null) //if top tile exists
            {
                //make sure zones line up
                if(topTile.getZones()[4] != gameState.getCurrTile().getZones()[0])
                {
                    return false;
                }
                if(topTile.getZones()[5] != gameState.getCurrTile().getZones()[11])
                {
                    return false;
                }

                if(topTile.getZones()[6] != gameState.getCurrTile().getZones()[10])
                {
                    return false;
                }

            }
            if(botTile != null) //if bot tile exists
            {
                //make sure zones line up
                if(botTile.getZones()[0] != gameState.getCurrTile().getZones()[4])
                {
                    return false;
                }
                if(botTile.getZones()[11] != gameState.getCurrTile().getZones()[5])
                {
                    return false;
                }

                if(botTile.getZones()[10] != gameState.getCurrTile().getZones()[6])
                {
                    return false;
                }

            }

            //place da tile
            gameState.getBoard()[ppa.getxCor()][ppa.getyCor()] = new Tile(gameState.getCurrTile());
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

            //if the tile zone is not used then return false
            if( gameState.getCurrTile().getZones()[pfa.getZone()] == Tile.EMPTY) return false;

            //if we are in ther correct phase
            if(gameState.getTurnPhase() != CarcassonneState.FOLLOWER_PHASE) return false;

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
}
