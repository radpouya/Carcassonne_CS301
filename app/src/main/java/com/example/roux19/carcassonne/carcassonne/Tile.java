// Jake Galves, Pouya Rad, Malcolm Roux, Sean Tan
// CS 301 A - Spring 2017
// Dr. Andrew Nuxoll
// Team Project - Carcassonne
// HW Assignment 4 Final Release
// 1 May 2017

package com.example.roux19.carcassonne.carcassonne;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import com.example.roux19.carcassonne.game.GameMainActivity;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by roux19 on 2/20/2017.
 */

public class Tile implements Serializable {


    public static final long serialVersionUID = 69420696942069L;

    private int bitmapRes; // keeps the address of the needed bitmap
    private int rotation; // rotation of tile
    private char[] zones = new char[13]; // stores the terrain on tile
    private ArrayList<Area> tileAreas = new ArrayList<>(); //all the areas in the tile

    /**
     * Tile
     * creates a tile initially
     * used to make tiles into tile deck
     * replaces area arguemnt with area propigation
     * propigation indexes correspond to zone indexes
     * propigatoin values correspond to areas indexes
     * values must go from lowest to highest
     * @param initBitmapRes
     * @param initZones
     * @param initArrayPropigation
     * @param initRotation
     */
    public Tile(int initBitmapRes, char[] initZones,
                int[] initArrayPropigation, int initRotation)
    {
        bitmapRes = initBitmapRes;
        rotation = initRotation;
        for( int i = 0; i < 13; i++)
        {
            this.zones[i] = initZones[i];
        }

        for( int i = 0; i < 13; i++) //loop through all the zones
        {
            //negative 1 means skip this zone (it is an empty zone)
            if( initArrayPropigation[i] != -1 ) {
                //if we have not created this area yet
                if (tileAreas.size() == initArrayPropigation[i])
                {
                    int score = 2; //the score is 2 per tile
                    if (initZones[i] == 'f') score = 1; //unless farm then 1
                    // make a new area and add it, it has this indexes
                    // type no owner no tile and no zones yet
                    tileAreas.add(new Area(initZones[i], score, null, null));
                }

                //add this zone to this areas occupied zones
                tileAreas.get(initArrayPropigation[i]).getOccZones().add(i);
            }
        }
    }

    /**
     * Tile
     * deep copy of Tile class above
     * @param tile
     */
    public Tile(Tile tile)
    {
        if( tile == null ){ return; }

        this.bitmapRes = tile.bitmapRes;
        this.rotation = tile.rotation;

        for(int i = 0; i < 13; i++)
        {
            this.zones[i] = tile.zones[i];
        }

        for( int i = 0; i < tile.tileAreas.size(); i++)
        {
            tileAreas.add(new Area(tile.tileAreas.get(i)));
        }
    }

    /**
     * isPlacable
     * tells whether a follower can be placed on a specific area
     * basically searching for a connected follower
     * uses the index of the area the follower is being placed on
     * @param indexOfArea
     * @param xCor
     * @param yCor
     * @param gameState
     * @param touchedAreas
     * @return
     */
    public boolean isPlaceable(int indexOfArea, int xCor, int yCor,
                               CarcassonneState gameState, ArrayList<Area>
                               touchedAreas){

        //this is trying to place on an empty area
        if( indexOfArea == -1 ) return false;

        // this is the area we are working with
        Area targetArea = tileAreas.get(indexOfArea);

        //if this area has already been touched return true (aka, base case to not double back)
        for( int i = 0; i < touchedAreas.size(); i++ )
        {
            if ( targetArea ==  touchedAreas.get(i) ) return true;
        }
        //add this area to the touched areas list
        touchedAreas.add(targetArea);
        //infinite recursion: fixed

        //if this area has a follower then return false (base case to false)
        if ( this.getTileAreas().get(indexOfArea).getFollower() != null ) return false;

        Tile roamTile; //the tile we will roam to (up, down, left, right)
        int roamTileAreaIndex; //the index of the are we will roam to within the roam tile

        //yes we know this is a lot of IF statements but that was the only
        //way that we could think of the match the appropriate zones together

        //for all of our zones
        for( int i = 0; i < targetArea.getOccZones().size(); i++ ){
            //if we are in the 0 zone
            if ( targetArea.getOccZones().get(i) == 0 )
            {
                //have our roam be to the top
                roamTile = gameState.getBoard()[xCor][yCor-1];
                //if the roam is not false
                if( roamTile != null ) {
                    //0 goes to 4 so find the index of area that we are roaming to
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(4);
                    //recursive call on that area, if it evaluates as false
                    // then return false
                    //if it is true we still gotta search everywhere else
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor,
                            yCor - 1, gameState, touchedAreas)) return false;
                }
            }
            //find which zone we are in
            else if ( targetArea.getOccZones().get(i) == 1 )
            {
                //roam to the tile this zone borders
                roamTile = gameState.getBoard()[xCor-1][yCor];
                //if the roam tile is not null
                if( roamTile != null ) {
                    //roam to the area this zone borders
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(9);
                    //call this meathod on the area this zone borders
                    //if this is false then we are false
                    //if this is true we have to finish everything else
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor - 1,
                            yCor, gameState, touchedAreas)) return false;
                }
            }
            else if ( targetArea.getOccZones().get(i) == 2 )
            {
                roamTile = gameState.getBoard()[xCor-1][yCor];
                if( roamTile != null ) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(8);
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor - 1,
                            yCor, gameState, touchedAreas)) return false;
                }
            }
            else if ( targetArea.getOccZones().get(i) == 3 )
            {
                roamTile = gameState.getBoard()[xCor-1][yCor];
                if( roamTile != null ) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(7);
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor - 1,
                            yCor, gameState, touchedAreas)) return false;
                }
            }
            else if ( targetArea.getOccZones().get(i) == 4 )
            {
                roamTile = gameState.getBoard()[xCor][yCor+1];
                if( roamTile != null ) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(0);
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor,
                            yCor + 1, gameState, touchedAreas)) return false;
                }
            }
            else if ( targetArea.getOccZones().get(i) == 5 )
            {
                roamTile = gameState.getBoard()[xCor][yCor+1];
                if( roamTile != null ) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(11);
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor,
                            yCor + 1, gameState, touchedAreas)) return false;
                }
            }
            else if ( targetArea.getOccZones().get(i) == 6 )
            {
                roamTile = gameState.getBoard()[xCor][yCor+1];
                if( roamTile != null ) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(10);
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor,
                            yCor + 1, gameState, touchedAreas)) return false;
                }
            }
            else if ( targetArea.getOccZones().get(i) == 7 )
            {
                roamTile = gameState.getBoard()[xCor+1][yCor];
                if( roamTile != null ) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(3);
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor + 1,
                            yCor, gameState, touchedAreas)) return false;
                }
            }
            else if ( targetArea.getOccZones().get(i) == 8 )
            {
                roamTile = gameState.getBoard()[xCor+1][yCor];
                if( roamTile != null ) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(2);
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor + 1,
                            yCor, gameState, touchedAreas)) return false;
                }
            }
            else if ( targetArea.getOccZones().get(i) == 9 )
            {
                roamTile = gameState.getBoard()[xCor+1][yCor];
                if( roamTile != null ) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(1);
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor+1,
                            yCor, gameState, touchedAreas)) return false;
                }
            }
            else if ( targetArea.getOccZones().get(i) == 10 )
            {
                roamTile = gameState.getBoard()[xCor][yCor-1];
                if( roamTile != null ) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(6);
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor,
                            yCor - 1, gameState, touchedAreas)) return false;
                }
            }
            else if ( targetArea.getOccZones().get(i) == 11 )
            {
                roamTile = gameState.getBoard()[xCor][yCor-1];
                if( roamTile != null ) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(5);
                    if (!roamTile.isPlaceable(roamTileAreaIndex, xCor,
                            yCor - 1, gameState, touchedAreas)) return false;
                }
            }
        }

        //if all of our calls have retrurned true
        return true;

    }

    /**
     * getAreaIndexFromZone
     * get the index of the area which ocupies a given zone
     * @param zoneIndex
     * @return
     */
    public int getAreaIndexFromZone( int zoneIndex ) {
        //through all of the areas in this tile
        for( int i = 0; i < this.getTileAreas().size(); i++ ) {
            //through all of the zones in that area
            for( int j = 0; j < this.getTileAreas().get(i).getOccZones().size(); j++) {
                //if the area has the zone we are looking at
                if( zoneIndex == this.getTileAreas().get(i).getOccZones().get(j) ) {
                    return i; //return the index of that area

                }
            }
        }
        return -1; //this happens if this is a empty zone
    }

    /**
     * rotateTile
     * Allows you to rotate a piece plus or minus 90 degrees and keeps the allowed partnered
     * to the zones they had before
     * @param isClockwise
     */
    public void rotateTile( boolean isClockwise ) {
        //transfering boolean to int...
        int rotateDirection = -1;
        if (isClockwise) rotateDirection = 1;

        //this is a rotation that will be used on a bitmap
        rotation = rotation+90*rotateDirection;

        //create array to house all of the adjusted zones
        char[] newZones = new char[13];

        //this will be how much the index of the zones switch
        //if we are conter clockwise everything goes forward 9 spaces
        // (or backwards 3 spaces but negative numbers are bad)
        int rotateScalar = 9;

        //if we are clockwise everything goes forward 3 spaces
        if(isClockwise) rotateScalar = 3;

        for( int i = 0; i<12; i++) {
            //for all the zones 0-11 adjust the index, mod 12 to keep it in the array
            newZones[i] = zones[(i + rotateScalar) % 12];
        }

        //zone 12 will always be the same, the joys of being the middle zone
        newZones[12] = zones[12];
        //transfer zones over
        zones = newZones;

        rotateScalar = 12 - rotateScalar; //we rotate the zones within the areas in the opposite
        // direction of the tile zones, hard to explain in text

        //same concept but we are adjusting the zones within the areas
        for( int i = 0; i<tileAreas.size(); i++) //all the areas
        {
            for( int j = 0; j<tileAreas.get(i).getOccZones().size(); j++) //all the occupied zones
            {
                if( tileAreas.get(i).getOccZones().get(j) != 12 ) //if we aren't zone 12
                {
                    //get adjusted by the scalar and get back to array bounds
                    tileAreas.get(i).getOccZones().set(j, (tileAreas.get(i)
                                .getOccZones().get(j) + rotateScalar) % 12);
                }
            }
        }
    }

    /**
     * endTurnScore
     * meathod to be called at the end of every turn
     * checks to see if any areas are ready to be scored
     * appropriately scores areas and returns followers
     * @param gameState
     * @param xCor
     * @param yCor
     */
    public void endTurnScore( CarcassonneState gameState, int xCor, int yCor)
    {
        //loop through all the areas in this tile
        for( int i = 0; i < this.tileAreas.size(); i++ )
        {
            //make a blank arraylist of areas
            ArrayList<Area> touchedArea = new ArrayList<Area>();
            //check if this area is completed aka doesnt have any loose ends
            //this also fills the array with all the connected areas
            if ( this.tileAreas.get(i).isCompleted( gameState , xCor, yCor, touchedArea ))
            {
                //give the score of all the connected areas to the appropriate player(s)
                //also return the follower to the player
                this.tileAreas.get(i).score( touchedArea, gameState.getScores().size(), gameState );
            }
        }
    }

    /**
     * drawTile
     * meathod to be called in surface views
     * draws the tile and potential follower
     * @return
     */
    public void drawTile( float xCor, float yCor, float size, Canvas c,
                          GameMainActivity myActivity, Paint[] playerPaints )
    {
        /**
         External Citation
         Date: 16 February 2017
         Problem: Rotate a bitmap
         Resource: http://stackoverflow.com/questions/8722359/scale-rotate-bitmap-using-
         matrix-in-android
         Solution: Used ammended example code
         */
        Bitmap toBeDrawn = BitmapFactory.decodeResource(myActivity.getResources(), bitmapRes);
        Matrix mat = new Matrix();
        mat.postRotate(rotation);
        toBeDrawn = Bitmap.createBitmap(toBeDrawn, 0, 0, toBeDrawn.getWidth(),
                                        toBeDrawn.getHeight(), mat, true);

        //draw it
        c.drawBitmap(toBeDrawn, null, new RectF(size * xCor, size * yCor,
                     size * xCor + size, size * yCor + size), null);

        //draw the possible follower
        for(int k = 0; k < tileAreas.size(); k++) {
            if ( tileAreas.get(k).getFollower() != null )
            {
                //keep track of top left and will be adjusted through zones
                float topLeftX = 200*xCor;
                float topLeftY = 200*yCor;
                //the zone in which the follower is
                int fz = tileAreas.get(k).getFollower().getPos();
                //adjusting the x y corrdinate based on zone
                //(note) we should 1. use a meathod in follower 2. use case notation
                if ( fz == 0 ) {
                    topLeftX += size*.2;
                    topLeftY += size*.05;
                }
                else if ( fz == 1 ) {
                    topLeftX += size*.05;
                    topLeftY += size*.2;
                }
                else if ( fz == 2 ) {
                    topLeftX += size*.05;
                    topLeftY += size*.45;
                }
                else if ( fz == 3 ) {
                    topLeftX += size*.05;
                    topLeftY += size*.7;
                }
                else if ( fz == 4 ) {
                    topLeftX += size*.2;
                    topLeftY += size*.85;
                }
                else if ( fz == 5 ) {
                    topLeftX += size*.45;
                    topLeftY += size*.85;
                }
                else if ( fz == 6 ) {
                    topLeftX += size*.7;
                    topLeftY += size*.85;
                }
                else if ( fz == 7 ) {
                    topLeftX += size*.85;
                    topLeftY += size*.7;
                }
                else if ( fz == 8 ) {
                    topLeftX += size*.85;
                    topLeftY += size*.45;
                }
                else if ( fz == 9 ) {
                    topLeftX += size*.85;
                    topLeftY += size*.2;
                }
                else if ( fz == 10 ) {
                    topLeftX += size*.7;
                    topLeftY += size*.05;
                }
                else if ( fz == 11 ) {
                    topLeftX += size*.45;
                    topLeftY += size*.05;
                }
                else if ( fz == 12 ) {
                    topLeftX += size*.45;
                    topLeftY += size*.45;
                }
                c.drawRect(topLeftX, topLeftY, (float)(topLeftX+size*.1),
                           (float)(topLeftY+size*.1), playerPaints[tileAreas.get(k)
                            .getFollower().getOwner()]);
            }
        }
    }

    public char[] getZones() { return zones; }

    public ArrayList<Area> getTileAreas() {
        return tileAreas;
    }
}
