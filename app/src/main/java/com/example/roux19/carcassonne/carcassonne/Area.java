package com.example.roux19.carcassonne.carcassonne;

import java.util.ArrayList;

/**
 * Created by roux19 on 2/22/2017.
 */
public class Area {

    private char type; //see Tile for chars

    private int points; //total amount of points to be awarded for this area (can change)

    private int ownership; //int corresponds to player number in game framework (-1 if no owner)

    private ArrayList<Integer> occupiedZones =

    public Area( char initType, int initPoints, int initOwnership)
    {

        type = initType;
        points = initPoints;
        ownership = initOwnership;

    }

    public Area( Area area, boolean origCall)
    {
        type = area.type;
        points = area.points;
        ownership = area.ownership;

        if ( origCall )
        {
            ArrayList<Area> tempConnected = new ArrayList<Area>();

            tempConnected.add(this);

            for( int i = 0; i < area.connectedAreas.size(); i++)
            {
                tempConnected.add(new Area(area.connectedAreas.get(i), false));
            }

            for( int i = 0; i < tempConnected.size(); i++)
            {
                for (int j = 0; j < tempConnected.size(); j++)
                {
                    if( i != j )
                    {
                        tempConnected.get(i).connectedAreas.add(tempConnected.get(j));
                    }
                }
            }
        }
    }

    public void mergeAreas( Area areaToMerge )
    {
        //merge the areas
    }

}
