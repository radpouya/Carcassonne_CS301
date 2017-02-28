package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.LocalGame;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

import java.util.ArrayList;

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
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

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
