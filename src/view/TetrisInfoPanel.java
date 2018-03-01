/*
 * TCSS 305 - Autumn 2016
 * Assignment 6a - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.Board;
import model.TetrisPiece;

/**
 * Class for creating the panel for the 
 * info of the current game.
 * 
 * @author Leah Ruisenor
 * @version December 2016.
 */
public class TetrisInfoPanel extends JPanel implements Observer {
    
    /** Serialization. */
    private static final long serialVersionUID = 7744188070152444584L;
    
    /** For spacing and string concatenating. */
    private static final String SPACE = "     ";
    
    /** Default font for the panel. */
    private static final int MED_SIZE_FONT = 10;
    
    /** Default info panel dimension. */
    private static final Dimension PANEL_DIMENSION = new Dimension(200, 100);
    
    /** 4 points to the score when a piece freezes in place. */
    private static final int ADD_4 = 4;
    
    /** Default number for leveling up. */
    private static final int LINES_TO_LEVEL_UP = 5;

    /** Default number for calculating score. */
    private static final int LINE1_SCORE = 40;
    
    /** Default number for calculating score. */
    private static final int LINE2_SCORE = 100;
    
    /** Default number for calculating score. */
    private static final int LINE3_SCORE = 300;
    
    /** Default number for calculating score. */
    private static final int LINE4_SCORE = 1200;
    
    /** Default number for the timer delay. */
    private static final int TIMER_DELAY = 500;
    
    /** Default number for the timer decrement. */
    private static final int TIMER_DECREMENT = 50;

    /** The game timer. */
    private final javax.swing.Timer myTimer;
    
    /** How many lines have cleared. */
    private int myClearedLines;
    
    /** What level game is on. */
    private int myLevel;
    
    /** The score of the current game. */
    private int myScore;
    
    /** How many lines to go before leveling up. */
    private int myNextLevelUp; 
    
    /** How many lines to go. */
    private int myLinesToGo;
    
    /** The label for how many lines cleared. */
    private JLabel myLinesLabel;

    /** The label for the current score. */
    private JLabel myScoreLabel;

    /** The label for the current level. */
    private JLabel myLevelLabel;

    /** The Label for how many lines to go. */
    private JLabel myLinesToGoLabel;
    
    /** The timer delay that is updated every completed level. */
    private int myTimeDelay = TIMER_DELAY;
    
    /**
     * Constructs the Tetris info panel.
     * 
     * @param theBoard is the game board info.
     * @param theTimer is the gameTimer.
     */
    public TetrisInfoPanel(final Board theBoard, final javax.swing.Timer theTimer) {
        super(); 
        final Board board = theBoard;
        board.addObserver(this);
        myTimer = theTimer;
        myClearedLines = 0;
        myLevel = 1;
        myScore = 0;
        myNextLevelUp = LINES_TO_LEVEL_UP;
        myLinesToGo = LINES_TO_LEVEL_UP;
        setUpPanel();
    }
    
    /**
     * Setting up the info panels color,
     * size and labels.
     */
    private void setUpPanel() {
        buildLabelInfo();
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(PANEL_DIMENSION);
    }
    
    /**
     * Builds the side box for the info
     * panel and adds the JLabel text to
     * the box and the box to the panel.
     */
    private void buildLabelInfo() {
        
        final Box infoBox = Box.createVerticalBox();
        final Box controls = new Box(BoxLayout.Y_AXIS);
        final Box score = new Box(BoxLayout.Y_AXIS);
        final Box level = new Box(BoxLayout.Y_AXIS);
        final Box lines = new Box(BoxLayout.Y_AXIS);
        final Box lineToGo = new Box(BoxLayout.Y_AXIS);
        
        final TitledBorder controlTitle = new TitledBorder("Game Controls");
        final TitledBorder scoreTitle = new TitledBorder("SCORE");
        final TitledBorder levelTitle = new TitledBorder("LEVEL");
        final TitledBorder linesTitle = new TitledBorder("LINES");
        final TitledBorder linesToGoTitle = new TitledBorder("LINES LEFT");
        
        final ChangeMyFont newFont = new ChangeMyFont();
        newFont.changeFont(controlTitle, MED_SIZE_FONT);
        newFont.changeFont(scoreTitle, MED_SIZE_FONT);
        newFont.changeFont(levelTitle, MED_SIZE_FONT);
        newFont.changeFont(linesTitle, MED_SIZE_FONT);
        newFont.changeFont(linesToGoTitle, MED_SIZE_FONT);
        
        controlTitle.setTitleJustification(TitledBorder.CENTER);
        scoreTitle.setTitleJustification(TitledBorder.CENTER);
        levelTitle.setTitleJustification(TitledBorder.CENTER);
        linesTitle.setTitleJustification(TitledBorder.CENTER);
        linesToGoTitle.setTitleJustification(TitledBorder.CENTER);
        
        controls.setBorder(controlTitle);
        score.setBorder(scoreTitle);
        level.setBorder(levelTitle);
        lines.setBorder(linesTitle);
        lineToGo.setBorder(linesToGoTitle);
        
        controls.add(new JLabel("Move Left:    A"));
        controls.add(new JLabel("Rotate Left:   Z"));
        controls.add(new JLabel("Slow Drop:      X"));
        controls.add(new JLabel(SPACE));
        controls.add(new JLabel("Move Right:     K"));
        controls.add(new JLabel("Rotate Right:  M"));
        controls.add(new JLabel("Fast Drop:    N"));
        infoBox.add(controls);
        
        myScoreLabel = new JLabel(SPACE + myScore + SPACE);
        score.add(myScoreLabel);
        infoBox.add(score);
        
        myLevelLabel = new JLabel(SPACE + myLevel + SPACE);
        level.add(myLevelLabel);
        infoBox.add(level);

        myLinesLabel = new JLabel(SPACE + myClearedLines + SPACE);
        lines.add(myLinesLabel);
        infoBox.add(lines);
        
        myLinesToGoLabel = new JLabel(SPACE + myLinesToGo + SPACE);
        lineToGo.add(myLinesToGoLabel);
        infoBox.add(lineToGo);
        
        UIManager.put("TitledBorder.border", new LineBorder(Color.RED.darker(), 1));
        add(infoBox);
    }
    
    /**
     * For calculating if the last Tetris piece has been 
     * placed add 4.
     * 
     * @param theNextPiece is the up next tetris piece.
     */
    private void calculateThePieceScore(final TetrisPiece theNextPiece) {
        if (theNextPiece != null) {
            myScore += ADD_4;
        }
        myScoreLabel.setText(SPACE + myScore + SPACE);
    }

    /**
     * For calculating the current score,
     * adding the line number multiplying the points.
     * 
     * @param theLinesHaveCleared is the number of how many lines have been cleared.
     */
    private void calculateTheScore(final int theLinesHaveCleared) {
        myLinesLabel.setText(SPACE + myClearedLines + SPACE);       
        
        if (theLinesHaveCleared != 0) {
            switch (theLinesHaveCleared) {
                case 1:
                    myScore += LINE1_SCORE * myLevel;
                    break;
                case 2:
                    myScore += LINE2_SCORE * myLevel;
                    break;
                case ADD_4 - 1: // 3 is a magic number
                    myScore += LINE3_SCORE * myLevel;
                    break;
                case ADD_4: // 4 is a magic number
                    myScore += LINE4_SCORE * myLevel;
                    break;
                default:
                    break;
            }
        }
        myScoreLabel.setText(SPACE + myScore + SPACE);
    }
    
    /**
     * For calculating how man lines to go before leveling up
     * and increments the timer when leveling up.
     * 
     * @param theLinesHaveCleared is the number of how many lines have been cleared.
     */
    private void calculateLinesToGo(final int theLinesHaveCleared) {
        final int linesHaveCleared = theLinesHaveCleared;
        myClearedLines += linesHaveCleared;
        if (myClearedLines >= myNextLevelUp) {
            myLevel++;
            //System.out.println(myTimer.getDelay());
            if (myTimer.getDelay() <= (TIMER_DELAY * 2) 
                            && myTimer.getDelay() > TIMER_DECREMENT) {
                myTimeDelay -= TIMER_DECREMENT;
                myTimer.setDelay(myTimeDelay);
                //System.out.println(myTimer.getDelay());
            }
            myLevelLabel.setText(SPACE + myLevel + SPACE);
            myNextLevelUp += LINES_TO_LEVEL_UP; 
        } 
        myLinesToGo = myNextLevelUp - myClearedLines;
        myLinesToGoLabel.setText(SPACE + myLinesToGo + SPACE);
    }
    
    /**
     * If a new game has been started this will clear scores.
     */
    public void clearScores() {
        myScore = 0;
        myLevel = 1;
        myClearedLines = 0;
        myTimeDelay = TIMER_DELAY;
        myTimer.setDelay(TIMER_DELAY * 2); // 1000 is a magic number
        myNextLevelUp = LINES_TO_LEVEL_UP;
        myLinesToGo = LINES_TO_LEVEL_UP;
        myScoreLabel.setText(SPACE + myScore + SPACE);
        myLevelLabel.setText(SPACE + myLevel + SPACE);
        myLinesLabel.setText(SPACE + myClearedLines + SPACE);
        myLinesToGoLabel.setText(SPACE + myLinesToGo + SPACE);
    }
    
    @Override
    public void update(final Observable theObservable, final Object theData) {
        if (theObservable instanceof Board && theData instanceof Integer[]) {          
            final int linesHaveCleared = ((Integer[]) theData).length;
            calculateLinesToGo(linesHaveCleared);
            calculateTheScore(linesHaveCleared);
        }
        if (theObservable instanceof Board && theData instanceof TetrisPiece) {
            final TetrisPiece nextPiece = (TetrisPiece) theData;
            calculateThePieceScore(nextPiece);
        }
    }
}