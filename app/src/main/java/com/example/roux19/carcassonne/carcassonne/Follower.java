package com.example.roux19.carcassonne.carcassonne;

/**
 * Created by roux19 on 3/5/2017.
 */
public class Follower {

    int pos;
    int owner;

    public Follower( int initPos, int initOwner )
    {
        pos = initPos;
        owner = initOwner;
    }

    public Follower( Follower f )
    {
        pos = f.pos;
        owner = f.owner;
    }

    public int getOwner() {
        return owner;
    }

    public int getPos() {
        return pos;
    }
}
