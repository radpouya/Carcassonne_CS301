package com.example.roux19.carcassonne.carcassonne;

import com.example.roux19.carcassonne.game.GamePlayer;

import java.io.Serializable;

/**
 * Created by roux19 on 3/5/2017.
 *
 * used to represent a follower on an area
 */
public class Follower implements Serializable {


    public static final long serialVersionUID = 4206942042069420L;

    int pos; //zone possition
    int owner; //reference to owner of the follower

    /**
     * Follower
     * make a follower from params
     *
     * @param initPos
     * @param initOwner
     */
    public Follower( int initPos, int initOwner )
    {
        pos = initPos;
        owner = initOwner;
    }

    /**
     * Follower
     * deep copy of a follower
     *
     * @param f
     */
    public Follower( Follower f )
    {
        pos = f.pos;
        owner = f.owner;
    }

    //getters
    public int getOwner() {
        return owner;
    }

    public int getPos() {
        return pos;
    }
}
