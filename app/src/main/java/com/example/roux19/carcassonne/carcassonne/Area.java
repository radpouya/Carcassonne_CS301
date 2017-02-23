package com.example.roux19.carcassonne.carcassonne;

/**
 * Created by roux19 on 2/22/2017.
 */
public class Area {

    private char type; //see Tile for chars

    private int points; //total amount of points to be awarded for this area (can change)

    private int ownership; //int corresponds to player number in game framework (-1 if no owner)

    public Area( char initType, int initPoints, int initOwnership)
    {

        type = initType;
        points = initPoints;
        ownership = initOwnership;

    }

    public Area( Area area)
    {
        type = area.type;
        points = area.points;
        ownership = area.ownership;
    }

}
