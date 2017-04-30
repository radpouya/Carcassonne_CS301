// Jake Galves, Pouya Rad, Malcolm Roux, Sean Tan
// CS 301 A - Spring 2017
// Dr. Andrew Nuxoll
// Team Project - Carcassonne
// HW Assignment 4 Final Release
// 1 May 2017

package com.example.roux19.carcassonne.carcassonne;

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
