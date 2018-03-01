/*
 * TCSS 305 - Autumn 2016
 * Assignment 6a - Tetris
 */

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;

/**
 * Tetris GUI.
 * 
 * @author Leah Ruisenor
 * @version December 2016.
 */
public class TetrisGUI {
      
    /** The default timer delay of 1 second. */
    private static final int TIME_DELAY = 1000;
    
    /** The default Tetris window frame size. */
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(400, 449);
    
    /** The default small font size. */
    private static final int SM_FONT_SIZE = 8;
    
    /** The default medium font size. */
    private static final int MED_FONT_SIZE = 10;
    
    /** The JFrame window. */
    private final JFrame myFrame;
    
    /** The Tetris game board. */
    private final Board myGameBoard;
    
    /** The new font for the text. */
    private final ChangeMyFont myNewFont;
    
    /** Image for Icon. */
    private final java.awt.Image myImage;
    
    /**
     * Constructs Tetris GUI and initializes all fields.
     */
    public TetrisGUI() {
        myFrame = new JFrame("Tetris");
        myImage = Toolkit.getDefaultToolkit().getImage("./images/lmr.gif");
        myGameBoard = new Board();
        myNewFont = new ChangeMyFont();
    }
    
    /**
     * Sets up graphical components of Tetris.
     */
    public final void start() {
        
        myFrame.setIconImage(myImage);
        myFrame.setMinimumSize(new Dimension(DEFAULT_FRAME_SIZE));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        final Timer timer = new Timer(TIME_DELAY, new TimerListener());
        
        final JPanel rightPanel = new JPanel();
        final BoxLayout layout = new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS);
        rightPanel.setLayout(layout);
        
        final TetrisGamePanel tetrisGamePanel = new TetrisGamePanel();
        final TetrisUpNextPanel nextPiecePanel = new TetrisUpNextPanel();
        final TetrisInfoPanel infoPanel = new TetrisInfoPanel(myGameBoard, timer);
        myNewFont.changeFont(infoPanel, SM_FONT_SIZE + 2);
        myNewFont.changeFont(nextPiecePanel, MED_FONT_SIZE);
        
        final Box box = new Box(BoxLayout.PAGE_AXIS);
        box.add(nextPiecePanel);
        box.add(infoPanel);
        rightPanel.add(box);
        
        tetrisGamePanel.setLayout(new GridBagLayout()); 
        myFrame.add(tetrisGamePanel);
        myFrame.add(rightPanel, BorderLayout.EAST);
        
        final TetrisMenuBar tetrisMenuBar = new TetrisMenuBar(myFrame, myGameBoard,
                                                              timer, tetrisGamePanel, 
                                                              nextPiecePanel, infoPanel);
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setJMenuBar(tetrisMenuBar);
        myFrame.setVisible(true);
    }
    /**
     * The timer listener for letting the game take
     * one step. 
     */
    private class TimerListener implements ActionListener {
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myGameBoard.step();
        }
    } 
}