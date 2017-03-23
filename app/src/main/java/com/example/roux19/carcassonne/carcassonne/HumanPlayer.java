package com.example.roux19.carcassonne.carcassonne;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.roux19.carcassonne.R;
import com.example.roux19.carcassonne.game.GameHumanPlayer;
import com.example.roux19.carcassonne.game.GameMainActivity;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;
import com.example.roux19.carcassonne.game.infoMsg.GameInfo;
import com.example.roux19.carcassonne.game.infoMsg.GameState;

import java.util.Random;

/**
 * Created by roux19 on 2/27/2017.
 */
public class HumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener
{
    //our drawing canvases
    private CurrTile currTileView;
    private GameBoard gameBoardView;

    //our buttons
    private Button rotateLeftAndCancel;
    private Button rotateRightAndEndTurn;
    private TextView followerText;

    //the state we have
    private CarcassonneState state;

    //used in our on touch stuff to diferentiate tap from drag
    private float mx, my, origX, origY;
    boolean isMove = false;

    public HumanPlayer( String name )
    {
        super(name);
    }

    @Override //unused
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (info == null) return;

        if (!(info instanceof CarcassonneState)) return; // if we do not have a CarcassonneState, ignore
        else {
            this.state = (CarcassonneState)info; //set our state
            gameBoardView.setState((CarcassonneState)info); //set the boards state
            currTileView.setState((CarcassonneState)info); //set the current tile's state

            //draw our stuff
            gameBoardView.invalidate();
            currTileView.invalidate();

            //set relevant text in buttons corresponding to state
            if (state.getTurnPhase() == CarcassonneState.PIECE_PHASE)
            {
                rotateLeftAndCancel.setText("<---");
                rotateRightAndEndTurn.setText("--->");
            }
            else if (state.getTurnPhase() == CarcassonneState.FOLLOWER_PHASE)
            {
                rotateLeftAndCancel.setText("UNDO");
                rotateRightAndEndTurn.setText("END TURN");
            }
            else if (state.getTurnPhase() == CarcassonneState.END_TURN_PHASE)
            {
                rotateLeftAndCancel.setText("UNDO");
                rotateRightAndEndTurn.setText("END TURN");
            }

            //set relevant follower text
            int playerFollowers;
            playerFollowers = state.getRemainingFollowers().get(this.playerNum);
            followerText.setText("Followers: "+playerFollowers);
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {

        //uhhhhhh
        //activity.onCreate(savedInstanceState);
        activity.setContentView(R.layout.activity_main);

        //attach widgets
        currTileView = (CurrTile)activity.findViewById(R.id.curTile);
        gameBoardView = (GameBoard)activity.findViewById(R.id.gameBoard);
        rotateLeftAndCancel = (Button)activity.findViewById(R.id.rotateLeft);
        rotateRightAndEndTurn = (Button)activity.findViewById(R.id.rotateRight);
        followerText = (TextView)activity.findViewById(R.id.followersText);

        //send references to activity (used fo retreiving recourses)
        gameBoardView.setMyActivity(activity);
        currTileView.setMyActivity(activity);

        //set listeners, guess what they're me
        rotateRightAndEndTurn.setOnClickListener(this);
        rotateLeftAndCancel.setOnClickListener(this);
        currTileView.setOnTouchListener(this);
        gameBoardView.setOnTouchListener(this);

    }


    @Override //button presses
    public void onClick(View view) {

        GameAction action = null;

        //depending on state and which button is pressed
        //send the correct action
        if( view.getId() == R.id.rotateRight) {
            if ( state.getTurnPhase() == CarcassonneState.PIECE_PHASE) {
                action = new rotateAction(true, this);
            }
            else if ( state.getTurnPhase() == CarcassonneState.FOLLOWER_PHASE ||
                    state.getTurnPhase() == CarcassonneState.END_TURN_PHASE) {
                action = new EndTurnAction( this );
            }
        }
        else if( view.getId() == R.id.rotateLeft )
        {
            if ( state.getTurnPhase() == CarcassonneState.PIECE_PHASE ) {
                action = new rotateAction(false, this);
            }
            else if ( state.getTurnPhase() == CarcassonneState.FOLLOWER_PHASE ) {
                action = new returnTileAction(this);
            }
            else if ( state.getTurnPhase() == CarcassonneState.END_TURN_PHASE ) {
                action = new ReturnFollowerAction( this );
            }
        }

        //we haven't done that button yet
        if(action != null) {
            game.sendAction(action);
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        if( view.getId() == R.id.gameBoard ) //we are scrolling or placing a tile
        {
            /**
             External Citation
             Date: 16 February 2017
             Problem: Handle taps and scrolls on a surface view
             Resource: http://stackoverflow.com/questions/26231906/how-do-i-program-a-scrollable-
             surfaceview-in-android-java
             Solution: Used concept from code example
             */

            float curX, curY; //temporary variables

            //mx,my are previous values
            //origX, origY are the original values
            switch (e.getAction())
            {
                case MotionEvent.ACTION_DOWN: //if we are first touching the screen
                    //record varibles into instance variables
                    mx = e.getX();
                    my = e.getY();
                    origX = e.getX();
                    origY = e.getY();
                    break;
                case MotionEvent.ACTION_MOVE: //we have movement!!
                    //if there is more than 50 movement from the original location, set isMove to true
                    //this signifies that it is a scroll instead of a tap
                    if ( Math.abs(e.getX()-origX) > 50 || Math.abs(e.getY()-origY) > 50 ) { isMove = true; }
                    //record the current values
                    curX = e.getX();
                    curY = e.getY();
                    //scroll by difference of current values to last values
                    view.scrollBy((int)(mx-curX), (int)(my-curY));
                    //record these values as last values
                    mx = curX;
                    my = curY;
                    break;
                case MotionEvent.ACTION_UP: //if relaese of screen
                    if (isMove) { //if we are a move
                        //set values
                        curX = e.getX();
                        curY = e.getY();
                        //scroll by final movement
                        view.scrollBy((int) (mx - curX), (int) (my - curY));
                        isMove = false; //resent is move to false
                        return false;
                    }
                    else { //we are placing a tile
                        Random rand = new Random(); //who cares

                        //add touch event coor to scroll coor
                        int touchX = (int)(e.getX()+view.getScrollX());
                        int touchY = (int)(e.getY()+view.getScrollY());

                        //some varibles to scale movement
                        //these are here because of how rounding works in java
                        //-100/200 = 0 insead of -200, the negatives round up while positives round down
                        int placeX = 0;
                        int placeY = 0;

                        // set these varibles to -1 if we are placing in the negatives
                        if (touchX<0) { placeX = -1;}
                        if (touchY<0) { placeY = -1;}

                        //send place peice action
                        GameAction action = new PlacePieceAction( touchX/200 + placeX, touchY/200 + placeY, this);
                        game.sendAction(action);
                        return true;
                    }
            }

            return true;
        }
        else if ( view.getId() == R.id.curTile ) //place a follower
        {
            //figure out which area was touched

            //saving keystrokes one poorly named varible at a time
            float x = e.getX();
            float y = e.getY();

            //the area we are touching
            int areaTouched;

            if ( x > 400*(7.0/16.0) && x < 400*(9.0/16.0) &&
                    y > 400*(7.0/16.0) && y < 400*(9.0/16.0)) {
                areaTouched = 12;
            }
            else if ( x > y ) {
                //in the top right
                if ( x > - y + 400) {
                    //in the right
                    if ( y < 400*(7.0/16.0)) {
                        //upper right
                        areaTouched = 9;
                    }
                    else if ( y < 400*(9.0/16.0)) {
                        //middle right
                        areaTouched = 8;
                    }
                    else {
                        //lower right
                        areaTouched = 7;
                    }
                }
                else {
                    //in the top
                    if ( x < 400*(7.0/16.0)) {
                        //leftside top
                        areaTouched = 0;
                    }
                    else if ( x < 400*(9.0/16.0)) {
                        //middle top
                        areaTouched = 11;
                    }
                    else {
                        //rightside top
                        areaTouched = 10;
                    }
                }
            }
            else {
                //in the bottom left
                if ( x > - y + 400) {
                    //in the bottom
                    if ( x < 400*(7.0/16.0)) {
                        //leftside bottom
                        areaTouched = 4;
                    }
                    else if ( x < 400*(9.0/16.0)) {
                        //middle bottom
                        areaTouched = 5;
                    }
                    else {
                        //rightside bottom
                        areaTouched = 6;
                    }
                }
                else {
                    //in the left
                    if ( y < 400*(7.0/16.0)) {
                        areaTouched = 1;
                        //upper left
                    }
                    else if ( y < 400*(9.0/16.0)) {
                        areaTouched = 2;
                        //middle left
                    }
                    else {
                        areaTouched = 3;
                        //lower left
                    }
                }
            }

            game.sendAction(new PlaceFollowerAction(areaTouched, this));

        }

        return false;
    }


}
