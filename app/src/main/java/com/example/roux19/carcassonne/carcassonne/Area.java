package com.example.roux19.carcassonne.carcassonne;

import java.util.ArrayList;

/**
 * Created by roux19 on 2/22/2017.
 */
public class Area {

    private char type; //see Tile for chars

    private int points; //total amount of points to be awarded for this area (can change)

    private int ownership; //int corresponds to player number in game framework (-1 if no owner)

    private ArrayList<Integer> occZones = new ArrayList<Integer>(); //occ means occupied

    public Area( char initType, int initPoints, int initOwnership, ArrayList<Integer> initOccZones)
    {

        type = initType;
        points = initPoints;
        ownership = initOwnership;

        for( int i = 0; i < initOccZones.size(); i++)
        {
            occZones.add(new Integer(initOccZones.get(i)));
        }

    }

    public Area( Area area )
    {

        type = area.type;
        points = area.points;
        ownership = area.ownership;

        for( int i = 0; i < area.occZones.size(); i++)
        {
            occZones.add(new Integer(area.occZones.get(i)));
        }
    }


}
