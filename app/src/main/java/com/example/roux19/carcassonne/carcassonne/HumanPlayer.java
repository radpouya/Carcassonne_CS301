package com.example.roux19.carcassonne.carcassonne;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfRenderer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.roux19.carcassonne.R;
import com.example.roux19.carcassonne.game.GameHumanPlayer;
import com.example.roux19.carcassonne.game.GameMainActivity;
import com.example.roux19.carcassonne.game.actionMsg.GameAction;
import com.example.roux19.carcassonne.game.infoMsg.GameInfo;
import com.example.roux19.carcassonne.game.infoMsg.GameState;
import com.example.roux19.carcassonne.game.infoMsg.IllegalMoveInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by roux19 on 2/27/2017.
 */
public class HumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener
{
    //our drawing canvases
    private CurrTile currTileView;
    private GameBoard gameBoardView;
    private Activity theActivity;

    //our buttons
    private ImageButton rotateLeftAndCancel;
    private ImageButton rotateRightAndEndTurn;
    private Button helpButton;
    private Button quitButton;
    private Button backButton;

    private TextView followerText;
    private TextView helpText;
    private TextView remainingTiles;
    private TextView turnIn;
    private TextView nameHead;
    private TextView scoreHead;
    private ArrayList<TextView> names = new ArrayList<TextView>();
    private ArrayList<TextView> scores = new ArrayList<TextView>();

    private Typeface carcassonneFont;

    private Context myContext;

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

        // Extra feature, vibrates to let user know that they were illegally
        // trying to place a tile.
        if( info instanceof IllegalMoveInfo)
        {
            Vibrator v = (Vibrator) myContext.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(250);
            /** External Citation
             * Date: 25 April 2017
             * Problem: Wanted to figure out how to invoke haptic feedback from
                        device.
             * Resource: http://stackoverflow.com/questions/13950338/how-to-
                         make-an-android-device-vibrate
             * Solution: Used the code provided by the first commenter and
                         modified it for our own usage.
             */

        }

        if (!(info instanceof CarcassonneState)) return; // if we do not have a CarcassonneState, ignore
        this.state = (CarcassonneState)info; //set our state
        gameBoardView.setState((CarcassonneState)info); //set the boards state
        currTileView.setState((CarcassonneState)info); //set the current tile's state

        //draw our stuff
        gameBoardView.invalidate();
        currTileView.invalidate();

        //set relevant text in buttons corresponding to state
        if (state.getTurnPhase() == CarcassonneState.PIECE_PHASE)
        {
            rotateLeftAndCancel.setImageResource(R.drawable.leftrot);
            rotateRightAndEndTurn.setImageResource(R.drawable.rightrot);
        }
        else if (state.getTurnPhase() == CarcassonneState.FOLLOWER_PHASE)
        {
            rotateLeftAndCancel.setImageResource(R.drawable.undo1);
            rotateRightAndEndTurn.setImageResource(R.drawable.end_turn);
        }
        else if (state.getTurnPhase() == CarcassonneState.END_TURN_PHASE)
        {
            rotateLeftAndCancel.setImageResource(R.drawable.undo1);
            rotateRightAndEndTurn.setImageResource(R.drawable.end_turn);
        }

        //set relevant follower text
        int playerFollowers;
        playerFollowers = state.getRemainingFollowers().get(this.playerNum);
        followerText.setText("Followers: " + playerFollowers);



        for (int i = 0; i < state.getScores().size(); i++) {
            scores.get(i).setText("" + state.getScores().get(i));
        }



        remainingTiles.setText("Tiles Remaining: " + state.getRemainingTilesNum());
        turnIn.setText("" + allPlayerNames[state.getPlyrTurn()] + "'s Turn");

    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        this.theActivity = activity;
        myContext = activity;
        drawGUIHelper();
    }


    @Override //button presses
    public void onClick(View view) {

        GameAction action = null;

        //depending on state and which button is pressed
        //send the correct action
        if( view.getId() == R.id.rotateRight) {
            if(state.getPlyrTurn() == playerNum) {
                if (state.getTurnPhase() == CarcassonneState.PIECE_PHASE) {
                    action = new rotateAction(true, this);
                } else if (state.getTurnPhase() == CarcassonneState.FOLLOWER_PHASE ||
                        state.getTurnPhase() == CarcassonneState.END_TURN_PHASE) {
                    action = new EndTurnAction(this);
                }
            }
        }
        else if( view.getId() == R.id.rotateLeft ) {
            // Make sure it's the user's turn before allowing them to access
            // the buttons.
            if(state.getPlyrTurn() == playerNum) {
                if (state.getTurnPhase() == CarcassonneState.PIECE_PHASE) {
                    action = new rotateAction(false, this);
                } else if (state.getTurnPhase() == CarcassonneState.FOLLOWER_PHASE) {
                    action = new returnTileAction(this);
                } else if (state.getTurnPhase() == CarcassonneState.END_TURN_PHASE) {
                    action = new ReturnFollowerAction(this);
                }
            }
        } else if(view.getId() == R.id.helpButton) {
            carcassonneFont = Typeface.createFromAsset(theActivity.getAssets(),
                              "fonts/ufonts.com_lindsay-becker.ttf");
            theActivity.setContentView(R.layout.help_activity);
            backButton = (Button)theActivity.findViewById(R.id.backButton);
            backButton.setTypeface(carcassonneFont);
            backButton.setOnClickListener(this);
            helpText = (TextView)theActivity.findViewById(R.id.helpTV);
            helpText.setTypeface(carcassonneFont);
            /** External Citation
             * Date: 12 April 2017
             * Problem: Difficulty getting new layout to display when button
                        was pressed
             * Resource: Professor Nuxoll's office hours.
             * Solution: Nux recommended that we make sure that everything
                         in the new layout was being initialized only after
                         we switched to that layout.
             */
            ruleWriter();
        } else if(view.getId() == R.id.quitButton) {
            System.exit(0);
        } else if(view.getId() == R.id.backButton) {
            drawGUIHelper();
            initAfterReady();

        }

        //we haven't done that button yet
        if(action != null) {
            game.sendAction(action);
        }

    }


    private void drawGUIHelper() {
        // Set up the GUI to the main gameboard layout and assign all
        // listeners.
        theActivity.setContentView(R.layout.activity_main);
        currTileView = (CurrTile)theActivity.findViewById(R.id.curTile);
        gameBoardView = (GameBoard)theActivity.findViewById(R.id.gameBoard);

        carcassonneFont = Typeface.createFromAsset(theActivity.getAssets(),
                          "fonts/ufonts.com_lindsay-becker.ttf");
        /** External Citation
         * Date: 24 April 2017
         * Problem: Wanted to get a custom font to be similar to that of the
                    Carcassonne game.
         * Resource: http://stackoverflow.com/questions/27588965/how-to-use-
                     custom-font-in-android-studio
         * Solution: Used code provided on site as an example for our own
                     usage.
         */

        rotateLeftAndCancel = (ImageButton)theActivity.findViewById(R.id.rotateLeft);
        rotateRightAndEndTurn = (ImageButton)theActivity.findViewById(R.id.rotateRight);
        helpButton = (Button)theActivity.findViewById(R.id.helpButton);
        quitButton = (Button)theActivity.findViewById(R.id.quitButton);
        helpButton.setTypeface(carcassonneFont);
        quitButton.setTypeface(carcassonneFont);

        followerText = (TextView)theActivity.findViewById(R.id.followersText);
        remainingTiles = (TextView)theActivity.findViewById(R.id.tileRemainingTV);
        turnIn = (TextView)theActivity.findViewById(R.id.turnIndicatorTV);
        nameHead = (TextView)theActivity.findViewById(R.id.nameHead);
        scoreHead = (TextView)theActivity.findViewById(R.id.scoreHead);


        followerText.setTypeface(carcassonneFont);
        remainingTiles.setTypeface(carcassonneFont);
        turnIn.setTypeface(carcassonneFont);
        nameHead.setTypeface(carcassonneFont);
        scoreHead.setTypeface(carcassonneFont);


        //send references to activity (used fo retreiving recourses)
        gameBoardView.setMyActivity((GameMainActivity) theActivity);
        currTileView.setMyActivity((GameMainActivity)theActivity);

        //set listeners, guess what they're me
        rotateRightAndEndTurn.setOnClickListener(this);
        rotateLeftAndCancel.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);

        currTileView.setOnTouchListener(this);
        gameBoardView.setOnTouchListener(this);

        gameBoardView.scrollBy(200*60, 200*60);

        if( names.isEmpty() ) {
            names.add((TextView) theActivity.findViewById(R.id.p1Name));
            names.add((TextView) theActivity.findViewById(R.id.p2Name));
            names.add((TextView) theActivity.findViewById(R.id.p3Name));
            names.add((TextView) theActivity.findViewById(R.id.p4Name));
            names.add((TextView) theActivity.findViewById(R.id.p5Name));
            scores.add((TextView)theActivity.findViewById(R.id.p1Score));
            scores.add((TextView)theActivity.findViewById(R.id.p2Score));
            scores.add((TextView)theActivity.findViewById(R.id.p3Score));
            scores.add((TextView)theActivity.findViewById(R.id.p4Score));
            scores.add((TextView)theActivity.findViewById(R.id.p5Score));
        }
        else {
            names.set(0,(TextView) theActivity.findViewById(R.id.p1Name));
            names.set(1,(TextView) theActivity.findViewById(R.id.p2Name));
            names.set(2,(TextView) theActivity.findViewById(R.id.p3Name));
            names.set(3,(TextView) theActivity.findViewById(R.id.p4Name));
            names.set(4,(TextView) theActivity.findViewById(R.id.p5Name));
            scores.set(0,(TextView)theActivity.findViewById(R.id.p1Score));
            scores.set(1,(TextView)theActivity.findViewById(R.id.p2Score));
            scores.set(2,(TextView)theActivity.findViewById(R.id.p3Score));
            scores.set(3,(TextView)theActivity.findViewById(R.id.p4Score));
            scores.set(4,(TextView)theActivity.findViewById(R.id.p5Score));
        }

        names.get(0).setTextColor(Color.BLACK);
        names.get(1).setTextColor(Color.RED);
        names.get(2).setTextColor(Color.GREEN);
        names.get(3).setTextColor(Color.BLUE);
        names.get(4).setTextColor(Color.YELLOW);







        this.receiveInfo(state);
    }

    private void ruleWriter() {
        // Multiline string for the rules
        String rules = "This page describes the rules for this implementation " +
                "of Carcassonne.\nCarcassonne is an exciting tile-laying game " +
                "for 2-5 players.\nThe game consists of 66 Land tiles, which " +
                "indicate different road segments, crossings, castles, and " +
                "farms. Each player has 7 followers in a specific color, and " +
                "can use them as a knight, thief, or farmer.\nThese are " +
                "displayed as different colored squares, depending on the " +
                "player.\nTo play, you tap the screen to indicate where you " +
                "want to place the current drawn tile displayed in the top " +
                "right hand corner of the screen.\nTiles must be placed " +
                "legally such that all sections (roads, castles, etc) match " +
                "with the tile it is being placed adjacent to (e.g. two " +
                "roads must match up to form one road).\nAfter placing a " +
                "tile, the player has the option of placing a follower on " +
                "a section of the tile.\nThis is done by tapping the desired " +
                "area on the current tile in the top right hand of the screen." +
                "\nIf you place a follower in an area that becomes completed " +
                "upon the end of your turn, \nthen that area is scored " +
                "immediately and your follower is returned to you.\nIt is " +
                "important to note that a section that already has a follower " +
                "on it already is not a valid area to place a new follower. " +
                "For example, a road that is incomplete and has a follower on " +
                "is not a legal area for a new follower to be placed.\n " +
                "Score is added as follows when followers are present on the area:\n" +
                "\t\t\tA road is completed when both ends connect two cities\\castles or " +
                "the road forms a loop.\t\t\tEach tile in a completed road is worth " +
                "1 point.\n" +
                "\t\t\tA castle is completed when it is surrounded by walls and there " +
                "are no gaps in the city.\t\t\tEach tile in a completed city is worth " +
                "2 points.\n" +
                "\t\t\tAt the end of the game, any incomplete road is worth 1 point per" +
                "tile, each incomplete city is worth 1 point per tile, and each " +
                "connected field is worth 1 point per tile.\nRemember, the game " +
                "ends when all the tiles are used!";

        helpText.setText(""+rules);
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
                        view.scrollBy((int)(mx-curX),(int)(my-curY) );

                        isMove = false; //resent is move to false
                        return false;
                    }
                    else { //we are placing a tile
                        Random rand = new Random(); //who cares

                        //add touch event coor to scroll coor
                        int touchX = (int)(e.getX()+view.getScrollX());
                        int touchY = (int)(e.getY()+view.getScrollY());

                        //make sure it's in the game board
                        if( touchX>0 && touchX<(200*128) && touchY>0 && touchY<(200*128) ) {
                            //send place peice action
                            GameAction action = new PlacePieceAction(touchX / 200, touchY / 200, this);
                            game.sendAction(action);
                        }
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

    @Override
    protected void initAfterReady() {
        for(int i = 0; i < allPlayerNames.length; i++) {

            names.get(i).setTypeface(carcassonneFont);
            names.get(i).setText(allPlayerNames[i]);

            scores.get(i).setTypeface(carcassonneFont);
            if( state == null ) {
                scores.get(i).setText("" + 0);
            }
            else {
                scores.get(i).setText(""+state.getScores().get(i));
            }

        }
    }

}
