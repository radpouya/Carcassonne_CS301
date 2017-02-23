package com.example.roux19.carcassonne.carcassonne;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by roux19 on 2/20/2017.
 */
public class Tile {

    //different types of terrain
    public static char farm = 'f';
    public static char road = 'r';
    public static char castle = 'c';
    public static char empty = 'n'; //rare but sometimes the middle zone is not used

    private Bitmap tilePic; //appropriately rotated pic of tile
    private char[] zones = new char[13]; //stores the terrain on tile

    ArrayList<Area> tileAreas = new ArrayList<Area>();

    //-1 if no followers on tile, 0-12 corresponding to zone of follower
    private int follower;

    public Tile( Bitmap initTilePic, char[] initZones, int initFollower, ArrayList<Area> initTileAreas )
    {
        this.tilePic = Bitmap.createBitmap(initTilePic,0,0,initTilePic.getHeight(),initTilePic.getWidth());
        for( int i = 0; i<13; i++)
        {
            this.zones[i] = initZones[i];
        }
        this.follower = initFollower;

        for( int i = 0; i<initTileAreas.size(); i++)
        {
            tileAreas.add( new Area(initTileAreas.get(i)));
        }
    }

    public Tile( Tile tile)
    {
        this.tilePic = Bitmap.createBitmap(tile.tilePic,0,0,tile.tilePic.getHeight(),tile.tilePic.getWidth());
        for( int i = 0; i<13; i++)
        {
            this.zones[i] = tile.zones[i];
        }
        this.follower = tile.follower;

        for( int i = 0; i<tile.tileAreas.size(); i++)
        {
            tileAreas.add( new Area(tile.tileAreas.get(i)));
        }
    }
}
