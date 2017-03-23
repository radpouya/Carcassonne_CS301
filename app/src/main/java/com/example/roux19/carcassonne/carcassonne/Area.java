package com.example.roux19.carcassonne.carcassonne;

import java.util.ArrayList;

/**
 * Created by roux19 on 2/22/2017.
 *
 * Areas will be created inside of tiles in order to represent a scoring area within a tile
 * the area class will hold the follower and its occupied zones
 * areas handle how they are connected and how they are scored
 */
public class Area {

    private char type; //see Tile for chars

    private int points; //total amount of points to be awarded for this area (can change)

    private int ownership; //int corresponds to player number in game framework (-1 if no owner)

    private Follower follower; //null by default, holds a portential follower within the area

    private ArrayList<Integer> occZones = new ArrayList<Integer>(); //occ means occupied, the zones
    // within a tile that are occupies by this area

    /**
     * Area
     * normal constructor for an area, taking in all params
     * @param initType
     * @param initPoints
     * @param initOwnership
     * @param initOccZones
     * @param initFollower
     */
    public Area( char initType, int initPoints, int initOwnership, ArrayList<Integer> initOccZones, Follower initFollower)
    {

        type = initType;
        points = initPoints;
        ownership = initOwnership;
        if (initFollower != null) {
            follower = new Follower(initFollower);
        }

        if( initOccZones != null) {
            for (int i = 0; i < initOccZones.size(); i++) {
                occZones.add(new Integer(initOccZones.get(i)));
            }
        }

    }

    /**
     * Area
     * copy constructor
     * @param area
     */
    public Area( Area area )
    {

        type = area.type;
        points = area.points;
        ownership = area.ownership;
        if ( area.follower != null) {
            follower = new Follower(area.follower);
        }
        if( area.occZones != null ) {
            for (int i = 0; i < area.occZones.size(); i++) {
                occZones.add(new Integer(area.occZones.get(i)));
            }
        }
    }

    //getters
    public ArrayList<Integer> getOccZones() {
        return occZones;
    }

    public Follower getFollower() {
        return follower;
    }

    //setters
    public void setFollower(Follower follower) {
        this.follower = follower;
    }
}
