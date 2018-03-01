/*
 * TCSS 305 - Autumn 2016
 * Assignment 6a - Tetris
 */

package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Board;

/**
 * 
 * @author Leah Ruisenor
 * @version December 2016.
 */
public class TetrisControlKeys extends KeyAdapter {
    
    /** The Tetris game board.  */
    private final Board myGameBoard;
    
    /** The move status if the Tetris piece. */
    private Boolean myPieceCanMove;
    
    /**
     * Constructor for the Tetris game controls.
     * 
     * @param theGameBoard is the tetris game board.
     */
    public TetrisControlKeys(final Board theGameBoard) {
        super(); 
        myGameBoard = theGameBoard;
        myPieceCanMove = true;
    }
 
    /**
     * Setting the if the Tetris piece can move.
     * 
     * @param theCanItMove is the state of a Tetris piece.
     */
    public void setPieceCanMove(final Boolean theCanItMove) {
        myPieceCanMove = theCanItMove;
    }
    
    /**
     * Creating the event to control the Tetris piece.
     * 
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(final KeyEvent theEvent) {
        super.keyPressed(theEvent); 
        final String keyName = KeyEvent.getKeyText(theEvent.getKeyCode());
        if (myPieceCanMove) {
            switch (keyName) {
                case "A":
                    myGameBoard.left();
                    break;
                case "K":
                    myGameBoard.right();
                    break;
                case "Z":
                    myGameBoard.rotateCCW();
                    break;
                case "M":
                    myGameBoard.rotateCW();
                    break;
                case "X":
                    myGameBoard.down();
                    break;
                case "N":
                    myGameBoard.drop();
                    break;
                default:
                    break;
            }
        }
    }
}