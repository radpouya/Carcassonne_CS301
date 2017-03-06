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
            PlacePieceAction ppa = (PlacePieceAction)action;

            if(gameState.getTurnPhase() != CarcassonneState.PIECE_PHASE) return false;

            if(gameState.getBoard()[ppa.getxCor()][ppa.getyCor()]!= null) return false;

            Tile topTile = gameState.getBoard()[ppa.getxCor()][ppa.getyCor() + 1];
            Tile botTile = gameState.getBoard()[ppa.getxCor()][ppa.getyCor() - 1];
            Tile leftTile = gameState.getBoard()[ppa.getxCor() - 1][ppa.getyCor()];
            Tile rightTile = gameState.getBoard()[ppa.getxCor() + 1][ppa.getyCor()];

            if(topTile == null && botTile == null && leftTile == null && rightTile == null)
            {
                return false;
            }
            if(leftTile != null)
            {
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
            if(rightTile != null)
            {
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
            if(topTile != null)
            {
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
            if(botTile != null)
            {
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

            gameState.getBoard()[ppa.getxCor()][ppa.getyCor()] = new Tile(gameState.getCurrTile());
            gameState.setxCurrTile(ppa.getxCor());
            gameState.setxCurrTile(ppa.getxCor());
            gameState.setTurnPhase(CarcassonneState.FOLLOWER_PHASE);


            return true;

        }
        else if ( action instanceof PlaceFollowerAction ) {

            PlaceFollowerAction pfa = (PlaceFollowerAction)action;

            if(gameState.getTurnPhase() != CarcassonneState.FOLLOWER_PHASE) return false;

            int indexOfTargetArea = gameState.getCurrTile().getAreaIndexFromZone( pfa.getZone() );

            ArrayList<Area> areas = new ArrayList<Area>();
            if( !gameState.getCurrTile().isPlaceable(indexOfTargetArea, gameState.getxCurrTile(), gameState.getyCurrTile(), gameState, areas) )
            {
                return false;
            }

            //gameState.getCurrTile().getTileAreas().get(indexOfTargetArea).setFollower( new Follower(pfa.getZone(), pfa.getPlayer()));
            //problems
            gameState.setTurnPhase(CarcassonneState.END_TURN_PHASE);

        }
        else if ( action instanceof ReturnTileAction ) {

            ReturnTileAction pfa = (ReturnTileAction) action;

            if(gameState.getTurnPhase() != CarcassonneState.FOLLOWER_PHASE) return false;

            gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()] = null;

            //unfinneshed

        }
        else if ( action instanceof ReturnFollowerAction ) {

        }
        else if ( action instanceof EndTurnAction ) {

        }
        else if ( action instanceof RotateAction ) {

        }

        return false;
    }
}
