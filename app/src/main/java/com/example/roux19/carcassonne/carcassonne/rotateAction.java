package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;

/**
 * Created by roux19 on 2/27/2017.
 *
 * rotate action
 * we know the naming convention is off
 * refactoring makes committing impossible
 */
public class rotateAction extends GameAction
{

    public static final long serialVersionUID = 1337133713371337L;

    private boolean isClockwise; //keeps track of direction

    /**
     * rotateAction
     *
     * @param initIsClockWise
     * @param player
     */
    public rotateAction(boolean initIsClockWise, GamePlayer player)
    {
        super(player);

        isClockwise = initIsClockWise;
    }

    public boolean isClockwise() {
        return isClockwise;
    }
}
