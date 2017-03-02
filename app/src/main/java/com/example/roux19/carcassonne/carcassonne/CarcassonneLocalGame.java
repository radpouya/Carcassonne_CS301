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
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }
}
