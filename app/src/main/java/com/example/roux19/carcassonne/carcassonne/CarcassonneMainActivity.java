package com.example.roux19.carcassonne.carcassonne;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.roux19.carcassonne.R;
import com.example.roux19.carcassonne.game.GameMainActivity;
import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.LocalGame;
import com.example.roux19.carcassonne.game.config.GameConfig;
import com.example.roux19.carcassonne.game.config.GamePlayerType;

import java.util.ArrayList;

/**
 * Created by roux19 on 2/27/2017.
 * External Citation
 *  Date: 2/27/17
 *  Problem: We didn't want to write our MainActivity
 *  Resource: srvegdahl/CounterGame.git
 *  Solution: We copy pasted our MainActivity then edited it
 */
public class CarcassonneMainActivity extends GameMainActivity
{

    private static final int PORT_NUMBER = 3420;

    private ArrayList<Tile> tileDeck = new ArrayList<Tile>();

    @Override
    public GameConfig createDefaultConfig()
    {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // a human player player type (player type 0)
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new HumanPlayer(name);
            }});

        // a computer player type (player type 1)
        playerTypes.add(new GamePlayerType("Computer Player Dumb") {
            public GamePlayer createPlayer(String name) {
                return new ComputerPlayerDumb(name);
            }});

        // a computer player type (player type 2)
        playerTypes.add(new GamePlayerType("Computer Player Smart") {
            public GamePlayer createPlayer(String name) {
                return new ComputerPlayerSmart(name);
            }});

        // Create a game configuration class for Counter:
        // - player types as given above
        // - from 1 to 2 players
        // - name of game is "Counter Game"
        // - port number as defined above
        GameConfig defaultConfig = new GameConfig(playerTypes, 1, 5, "Carcassonne",
                PORT_NUMBER);

        // Add the default players to the configuration
        defaultConfig.addPlayer("Nux", 0); // player 1: a human player
        defaultConfig.addPlayer("Schmidt", 1); // player 2: a computer player
        defaultConfig.addPlayer("Vegdahl", 1); // player 3: a computer player
        defaultConfig.addPlayer("Epp", 1); // player 4: a computer player

        // Set the default remote-player setup:
        // - player name: "Remote Player"
        // - IP code: (empty string)
        // - default player type: human player
        defaultConfig.setRemoteData("Remote Player", "", 0);

        // return the configuration
        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame() {
        return new CarcassonneLocalGame();
    }
}
