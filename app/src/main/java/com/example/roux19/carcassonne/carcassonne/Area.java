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

    private Follower follower; //null by default, holds a portential follower within the area

    private ArrayList<Integer> occZones = new ArrayList<Integer>(); //occ means occupied, the zones
    // within a tile that are occupies by this area

    /**
     * Area
     * normal constructor for an area, taking in all params
     * @param initType
     * @param initPoints
     * @param initOccZones
     * @param initFollower
     */
    public Area( char initType, int initPoints, int initOwnership, ArrayList<Integer> initOccZones, Follower initFollower)
    {

        type = initType;
        points = initPoints;
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
        if ( area.follower != null) {
            follower = new Follower(area.follower);
        }
        if( area.occZones != null ) {
            for (int i = 0; i < area.occZones.size(); i++) {
                occZones.add(new Integer(area.occZones.get(i)));
            }
        }
    }

    /**
     * isCompleted
     * checks to see if this area string is ready to be scored
     * also creates a arraylist of areas that contain all of the areas in this area string
     * @param gameState
     * @param xCor
     * @param yCor
     * @param touchedAreas
     * @return
     */
    public boolean isCompleted( CarcassonneState gameState , int xCor, int yCor, ArrayList<Area> touchedAreas)
    {
        //farms are never done
        if ( this.type == Tile.FARM ) return false;

        //if this area has already been touched return true (aka, base case to not double back)
        for( int i = 0; i < touchedAreas.size(); i++ )
        {
            if ( this ==  touchedAreas.get(i) ) return true;
        }
        //add this area to the touched areas list
        //this list will contain all of the connected areas if the area string is completed
        touchedAreas.add(this);
        //infinite recursion: fixed

        Tile roamTile; //the tile we will roam to (up, down, left, right)
        int roamTileAreaIndex; //the index of the are we will roam to within the roam tile

        //yes we know this is a lot of IF statements but that was the only way that we could think
        //of the match the appropriate zones together

        //for all of our zones
        for( int i = 0; i < this.occZones.size(); i++ ){
            //if we are in the 0 zone
            if ( this.occZones.get(i) == 0 )
            {
                //have our roam be to the top
                roamTile = gameState.getBoard()[xCor][yCor-1];
                //if the roam is null return false (this is loose end of this string of areas)
                if( roamTile == null) return false;
                else {
                    //0 goes to 4 so find the index of area that we are roaming to
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(4);
                    //recursive call on that area, if it evaluates as false then return false
                    //if it is true we still gotta search everywhere else
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor,
                            yCor - 1, touchedAreas)) return false;
                }
            }
            //find which zone we are in
            else if ( this.occZones.get(i) == 1 )
            {
                //roam to the tile this zone borders
                roamTile = gameState.getBoard()[xCor-1][yCor];
                //if the roam tile is null return false, this area string is not completed
                if( roamTile == null) return false;
                else  {
                    //roam to the area this zone borders
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(9);
                    //call this meathod on the area this zone borders
                    //if this is false then we are false
                    //if this is true we have to finish everything else
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor - 1,
                            yCor, touchedAreas)) return false;
                }
            }
            else if ( this.occZones.get(i) == 2 )
            {
                roamTile = gameState.getBoard()[xCor-1][yCor];
                if( roamTile == null) return false;
                else {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(8);
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor - 1,
                            yCor, touchedAreas)) return false;
                }
            }
            else if ( this.occZones.get(i) == 3 )
            {
                roamTile = gameState.getBoard()[xCor-1][yCor];
                if( roamTile == null) return false;
                else {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(7);
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor - 1,
                            yCor, touchedAreas)) return false;
                }
            }
            else if ( this.occZones.get(i) == 4 )
            {
                roamTile = gameState.getBoard()[xCor][yCor+1];
                if( roamTile == null) return false;
                else {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(0);
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor,
                            yCor+1, touchedAreas)) return false;
                }
            }
            else if ( this.occZones.get(i) == 5 )
            {
                roamTile = gameState.getBoard()[xCor][yCor+1];
                if( roamTile == null) return false;
                else {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(11);
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor,
                            yCor+1, touchedAreas)) return false;
                }
            }
            else if ( this.occZones.get(i) == 6 )
            {
                roamTile = gameState.getBoard()[xCor][yCor+1];
                if( roamTile == null) return false;
                else {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(10);
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor,
                            yCor+1, touchedAreas)) return false;
                }
            }
            else if ( this.occZones.get(i) == 7 )
            {
                roamTile = gameState.getBoard()[xCor+1][yCor];
                if( roamTile == null) return false;
                else {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(3);
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor + 1,
                            yCor, touchedAreas)) return false;
                }
            }
            else if ( this.occZones.get(i) == 8 )
            {
                roamTile = gameState.getBoard()[xCor+1][yCor];
                if( roamTile == null) return false;
                else {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(2);
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor + 1,
                            yCor, touchedAreas)) return false;
                }
            }
            else if ( this.occZones.get(i) == 9 )
            {
                roamTile = gameState.getBoard()[xCor+1][yCor];
                if( roamTile == null) return false;
                else {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(1);
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor + 1,
                            yCor, touchedAreas)) return false;
                }
            }
            else if ( this.occZones.get(i) == 10 )
            {
                roamTile = gameState.getBoard()[xCor][yCor-1];
                if( roamTile == null) return false;
                else {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(6);
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor,
                            yCor-1, touchedAreas)) return false;
                }
            }
            else if ( this.occZones.get(i) == 11 )
            {
                roamTile = gameState.getBoard()[xCor][yCor-1];
                if( roamTile == null) return false;
                else {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(5);
                    if (!roamTile.getTileAreas().get(roamTileAreaIndex).isCompleted(gameState, xCor,
                            yCor-1, touchedAreas)) return false;
                }
            }
        }

        //if all of our calls have retrurned true
        return true;
    }

    /**
     * createPropigation
     * creates a arraylist of areas that contain all of the areas in this area string
     * @param gameState
     * @param xCor
     * @param yCor
     * @param touchedAreas
     * @return
     */
    public void createPropigation( CarcassonneState gameState , int xCor, int yCor, ArrayList<Area> touchedAreas)
    {
        //if this area has already been touched return true (aka, base case to not double back)
        for( int i = 0; i < touchedAreas.size(); i++ )
        {
            if ( this ==  touchedAreas.get(i) ) return;
        }
        //add this area to the touched areas list
        //this list will contain all of the connected areas if the area string is completed
        touchedAreas.add(this);
        //infinite recursion: fixed

        Tile roamTile; //the tile we will roam to (up, down, left, right)
        int roamTileAreaIndex; //the index of the are we will roam to within the roam tile

        //yes we know this is a lot of IF statements but that was the only way that we could think
        //of the match the appropriate zones together

        //for all of our zones
        for( int i = 0; i < this.occZones.size(); i++ ){
            //if we are in the 0 zone
            if ( this.occZones.get(i) == 0 ) {
                //have our roam be to the top
                roamTile = gameState.getBoard()[xCor][yCor - 1];
                //0 goes to 4 so find the index of area that we are roaming to
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(4);
                    //recursive call on that area
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor,
                            yCor - 1, touchedAreas);
                }
            }
            //find which zone we are in
            else if ( this.occZones.get(i) == 1 ) {
                //roam to the tile this zone borders
                roamTile = gameState.getBoard()[xCor - 1][yCor];
                //roam to the area this zone borders
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(9);
                    //call this meathod on the area this zone borders
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor - 1,
                            yCor, touchedAreas);
                }
            }
            else if ( this.occZones.get(i) == 2 ) {
                roamTile = gameState.getBoard()[xCor - 1][yCor];
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(8);
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor - 1,
                            yCor, touchedAreas);
                }
            }
            else if ( this.occZones.get(i) == 3 )
            {
                roamTile = gameState.getBoard()[xCor - 1][yCor];
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(7);
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor - 1,
                            yCor, touchedAreas);
                }
            }
            else if ( this.occZones.get(i) == 4 )
            {
                roamTile = gameState.getBoard()[xCor][yCor+1];
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(0);
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor,
                            yCor + 1, touchedAreas);
                }
            }
            else if ( this.occZones.get(i) == 5 )
            {
                roamTile = gameState.getBoard()[xCor][yCor+1];
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(11);
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor,
                            yCor + 1, touchedAreas);
                }
            }
            else if ( this.occZones.get(i) == 6 )
            {
                roamTile = gameState.getBoard()[xCor][yCor+1];
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(10);
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor,
                            yCor + 1, touchedAreas);
                }
            }
            else if ( this.occZones.get(i) == 7 )
            {
                roamTile = gameState.getBoard()[xCor + 1][yCor];
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(3);
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor + 1,
                            yCor, touchedAreas);
                }
            }
            else if ( this.occZones.get(i) == 8 )
            {
                roamTile = gameState.getBoard()[xCor + 1][yCor];
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(2);
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor + 1,
                            yCor, touchedAreas);
                }
            }
            else if ( this.occZones.get(i) == 9 )
            {
                roamTile = gameState.getBoard()[xCor + 1][yCor];
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(1);
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor + 1,
                            yCor, touchedAreas);
                }
            }
            else if ( this.occZones.get(i) == 10 )
            {
                roamTile = gameState.getBoard()[xCor][yCor - 1];
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(6);
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor,
                            yCor - 1, touchedAreas);
                }
            }
            else if ( this.occZones.get(i) == 11 )
            {
                roamTile = gameState.getBoard()[xCor][yCor - 1];
                if (roamTile!=null) {
                    roamTileAreaIndex = roamTile.getAreaIndexFromZone(5);
                    roamTile.getTileAreas().get(roamTileAreaIndex).createPropigation(gameState, xCor,
                            yCor - 1, touchedAreas);
                }
            }
        }

        //if all of our calls have retrurned true
        return;
    }

    /**
     * score
     * scores a area string
     * returns followers to players
     * @param areasToScore
     * @param numPlayers
     * @param gameState
     */
    public void score( ArrayList<Area> areasToScore, int numPlayers, CarcassonneState gameState )
    {
        //variable for total score
        int score = 0;

        //add up all the scores in the arraylist of area
        for( int i = 0; i < areasToScore.size(); i++)
        {
            score += areasToScore.get(i).points;
        }

        //keeps track of how many followers each player has on this area string
        int[] numFollowerPerOwner = new int[numPlayers];

        //add up and remove all of the followers on this area string
        for( int i = 0; i < areasToScore.size(); i++ )
        {
            //if there is a follower
            if( areasToScore.get(i).getFollower() != null )
            {
                //increment this owner's follower count
                numFollowerPerOwner[areasToScore.get(i).getFollower().getOwner()]++;
                //increment this owner's follower remaining count
                gameState.getRemainingFollowers().set(areasToScore.get(i).getFollower().getOwner(),
                        gameState.getRemainingFollowers().get(areasToScore.get(i).getFollower().getOwner())+1);
                //remove the follower from the area
                areasToScore.get(i).setFollower(null);
            }
        }

        //set default highest follower to 1 to ensure someone needs atleast one follower to score
        int highestNumFollowers = 1;
        //set the highest follower to the highest count of followers an owner has
        for( int i = 0; i < numPlayers; i++ )
        {
            if ( numFollowerPerOwner[i] > highestNumFollowers )
            {
                highestNumFollowers = numFollowerPerOwner[i];
            }
        }

        //add the score to each player who ties the highest num of followers
        for( int i = 0; i < numPlayers; i++ )
        {
            if ( numFollowerPerOwner[i] == highestNumFollowers )
            {
                gameState.getScores().set( i, gameState.getScores().get(i) + score );
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
