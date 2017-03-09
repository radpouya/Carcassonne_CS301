package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.LocalGame;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by roux19 on 2/27/2017.
 */
public class CarcassonneLocalGame extends LocalGame
{

    private CarcassonneState gameState;

    private ArrayList<Tile> tileDeck = new ArrayList<Tile>();

    public CarcassonneLocalGame( ArrayList<Tile> initTileDeck )
    {
        super();

        for( int i = 0; i < initTileDeck.size(); i++)
        {
            tileDeck.add(new Tile(initTileDeck.get(i)));
        }

        this.gameState = new CarcassonneState(new Tile[128][128], 64, 0, this.randTile(), null, null, -1, -1, CarcassonneState.PIECE_PHASE);
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

    }

    private Tile randTile()
    {
        /**
        Random r = new Random();
        int i = r.nextInt(tileDeck.size());

        return tileDeck.get(i);
         */
        return null;
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
            Tile topTile = gameState.getBoard()[ppa.getxCor()][ppa.getyCor() + 1];
            Tile botTile = gameState.getBoard()[ppa.getxCor()][ppa.getyCor() - 1];
            Tile leftTile = gameState.getBoard()[ppa.getxCor() - 1][ppa.getyCor()];
            Tile rightTile = gameState.getBoard()[ppa.getxCor() + 1][ppa.getyCor()];

            //check to make sure tile borders at least one tile
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
            gameState.setxCurrTile(ppa.getxCor());
            //enter next phase
            gameState.setTurnPhase(CarcassonneState.FOLLOWER_PHASE);

            //action completed
            return true;

        }
        //allows you to enter follower phase and place a follower
        else if ( action instanceof PlaceFollowerAction ) {

            PlaceFollowerAction pfa = (PlaceFollowerAction)action;

            if(gameState.getTurnPhase() != CarcassonneState.FOLLOWER_PHASE) return false;
            //place a follower
            int indexOfTargetArea = gameState.getCurrTile().getAreaIndexFromZone( pfa.getZone() );
            //tells you where a placeable follower is allowed
            ArrayList<Area> areas = new ArrayList<Area>();
            if( !gameState.getCurrTile().isPlaceable(indexOfTargetArea, gameState.getxCurrTile(),
                    gameState.getyCurrTile(), gameState, areas) )
            {
                return false;
            }
            //allows you to enter the end turn phase
            gameState.getCurrTile().getTileAreas().get(indexOfTargetArea).setFollower
                    ( new Follower(pfa.getZone(), gameState.getPlyrTurn()));
            gameState.setTurnPhase(CarcassonneState.END_TURN_PHASE);

            return true;

        } //allows you to undo the tile follower phase and go back into the piece phase to replace
        // your piece
            else if ( action instanceof returnTileAction ) {

            returnTileAction rta = (returnTileAction) action;

            if(gameState.getTurnPhase() != CarcassonneState.FOLLOWER_PHASE) return false;

            gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()] = null;

            gameState.setTurnPhase(CarcassonneState.PIECE_PHASE);

            return true;

        } //allows you to undo the end turn phase and go back into the follower phase to replace
        // your follower
        else if ( action instanceof ReturnFollowerAction ) {

            ReturnFollowerAction rfa = (ReturnFollowerAction) action;

            if(gameState.getTurnPhase() != CarcassonneState.END_TURN_PHASE) return false;

            for( int i = 0; i < gameState.getCurrTile().getTileAreas().size(); i++) {
                gameState.getCurrTile().getTileAreas().get(i).setFollower(null);
            }

            gameState.setTurnPhase(CarcassonneState.FOLLOWER_PHASE);

            return true;
        }//tells state if a player has entered the end turn phase and ended their turn and  player
        // turn index +1
        else if ( action instanceof EndTurnAction ) {

            EndTurnAction eta = (EndTurnAction) action;

            if(gameState.getTurnPhase() != CarcassonneState.END_TURN_PHASE) return false;

            gameState.setPlyrTurn((gameState.getPlyrTurn()+1)%this.players.length);
            gameState.setTurnPhase(CarcassonneState.PIECE_PHASE);

            return true;
        }//allows player whose turn it is to rotate a tile
        else if ( action instanceof rotateAction ) {

            rotateAction ra = (rotateAction) action;

            if( gameState.getTurnPhase() != CarcassonneState.PIECE_PHASE) return false;

            gameState.getCurrTile().rotateTile( ra.isClockwise() );

            return true;

        }

        return false;
    }
}
