package com.example.roux19.carcassonne.carcassonne;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.roux19.carcassonne.game.infoMsg.GameState;

import java.util.ArrayList;

/**
 * Created by roux19 on 2/20/2017.
 */
public class Tile {

    //different types of terrain
    public static final char FARM = 'f';
    public static final char ROAD = 'r';
    public static final char CASTLE = 'c';
    public static final char EMPTY = 'n'; //rare but sometimes the middle zone is not used

    private Bitmap tilePic; //appropriately rotated pic of tile
    private char[] zones = new char[13]; //stores the terrain on tile

    private ArrayList<Area> tileAreas = new ArrayList<Area>();



    public Tile( Bitmap initTilePic, char[] initZones, int initFollower, ArrayList<Area> initTileAreas )
    {
        this.tilePic = Bitmap.createBitmap(initTilePic,0,0,initTilePic.getHeight(),initTilePic.getWidth());
        for( int i = 0; i<13; i++)
        {
            this.zones[i] = initZones[i];
        }
        for( int i = 0; i<initTileAreas.size(); i++)
        {
            tileAreas.add( new Area(initTileAreas.get(i)));
        }
    }

    public Tile( Tile tile)
    {
        if( tile == null ){ return; }
        this.tilePic = Bitmap.createBitmap(tile.tilePic,0,0,tile.tilePic.getHeight(),tile.tilePic.getWidth());
        for( int i = 0; i<13; i++)
        {
            this.zones[i] = tile.zones[i];
        }

        for( int i = 0; i<tile.tileAreas.size(); i++)
        {
            tileAreas.add( new Area(tile.tileAreas.get(i)));
        }
    }

    public boolean isPlaceable(int indexOfArea, int xCor, int yCor, CarcassonneState gameState, ArrayList<Area> touchedAreas){
        Area targetArea = tileAreas.get(indexOfArea);

        for( int i = 0; i < touchedAreas.size(); i++ )
        {
            if ( targetArea ==  touchedAreas.get(i) ) return true;
        }

        if ( this.getTileAreas().get(indexOfArea).getFollower() != null ) return false;

        Tile roamTile;
        int roamTileAreaIndex;

        for( int i = 0; i < targetArea.getOccZones().size(); i++ ){
            if ( targetArea.getOccZones().get(i) == 0 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()-1];
                roamTileAreaIndex = getAreaIndexFromZone( 4 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile(),gameState.getyCurrTile()-1,gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 1 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()-1][gameState.getyCurrTile()];
                roamTileAreaIndex = getAreaIndexFromZone( 9 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile()-1,gameState.getyCurrTile(),gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 2 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()-1][gameState.getyCurrTile()];
                roamTileAreaIndex = getAreaIndexFromZone( 8 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile()-1,gameState.getyCurrTile(),gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 3 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()-1][gameState.getyCurrTile()];
                roamTileAreaIndex = getAreaIndexFromZone( 7 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile()-1,gameState.getyCurrTile(),gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 4 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()+1];
                roamTileAreaIndex = getAreaIndexFromZone( 0 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile(),gameState.getyCurrTile()+1,gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 5 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()+1];
                roamTileAreaIndex = getAreaIndexFromZone( 11 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile(),gameState.getyCurrTile()+1,gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 6 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()+1];
                roamTileAreaIndex = getAreaIndexFromZone( 10 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile(),gameState.getyCurrTile()+1,gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 7 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()+1][gameState.getyCurrTile()];
                roamTileAreaIndex = getAreaIndexFromZone( 3 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile()+1,gameState.getyCurrTile(),gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 8 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()+1][gameState.getyCurrTile()];
                roamTileAreaIndex = getAreaIndexFromZone( 2 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile()+1,gameState.getyCurrTile(),gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 9 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()+1][gameState.getyCurrTile()];
                roamTileAreaIndex = getAreaIndexFromZone( 1 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile(),gameState.getyCurrTile()-1,gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 10 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()-1];
                roamTileAreaIndex = getAreaIndexFromZone( 6 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile(),gameState.getyCurrTile()-1,gameState,touchedAreas) ) return false;
            }
            else if ( targetArea.getOccZones().get(i) == 11 )
            {
                roamTile = gameState.getBoard()[gameState.getxCurrTile()][gameState.getyCurrTile()-1];
                roamTileAreaIndex = getAreaIndexFromZone( 5 );
                if( !roamTile.isPlaceable(roamTileAreaIndex,gameState.getxCurrTile(),gameState.getyCurrTile()-1,gameState,touchedAreas) ) return false;
            }
        }

        return true;

    }

    public int getAreaIndexFromZone( int zoneIndex ) {

        for( int i = 0; i < this.getTileAreas().size(); i++ ) {

            for( int j = 0; j < this.getTileAreas().get(i).getOccZones().size(); i++) {

                if( zoneIndex == this.getTileAreas().get(i).getOccZones().get(j) ) {

                    return i;

                }

            }

        }

        return -1;
    }

    public void rotateTile( boolean isClockwise ) {

        Matrix mat = new Matrix();

        int rotateDirection = 1;
        if (isClockwise) rotateDirection = -1;

        mat.postRotate(90*rotateDirection);

        Bitmap draw = Bitmap.createBitmap(this.tilePic, 0,0, this.tilePic.getWidth(), this.tilePic.getHeight(), mat, true);

        int[] newZones = new int[13];

        int rotateScalar = 3;
        if(isClockwise) rotateScalar = 9;

        for( int i = 0; i<12; i++) {
            newZones[i] = zones[(i+rotateScalar)%12];
        }

        newZones[12] = zones[12];

        for( int i = 0; i<tileAreas.size(); i++)
        {
            for( int j = 0; j<tileAreas.get(i).getOccZones().size(); j++)
            {
                if( tileAreas.get(i).getOccZones().get(j) != 12 )
                {
                    tileAreas.get(i).getOccZones().set(j, (tileAreas.get(i).getOccZones().get(j) + rotateScalar) % 12);
                }
            }
        }
    }

    public char[] getZones() { return zones; }


    public ArrayList<Area> getTileAreas() {
        return tileAreas;
    }
}
