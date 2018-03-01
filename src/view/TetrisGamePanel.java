/*
 * TCSS 305 - Autumn 2016
 * Assignment 6a - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Block;
import model.Board;
import model.Point;

/**
 * The game panel were the blocks will be shown on.
 * 
 * @author Leah Ruisenor
 * @version December 2016.
 */
public class TetrisGamePanel extends JPanel implements Observer {
    
    /** Serialization. */
    private static final long serialVersionUID = 1159971900757430935L;
    
    /** Constant for calculating shifts. */
    private static final int FIVE = 5;
    
    /** Constant for calculating shifts. */
    private static final int THIRTY = 30;
    
    /** Constant for calculating shifts. */
    private static final int FOURTY = 40;
    
    /** A list of blocks for drawing the tetris pieces. */
    private final List<Block[]> myBlockList;
    
    /**
     * Constructs the tetris board.
     */
    public TetrisGamePanel() {
        super();
        myBlockList = new ArrayList<Block[]>();
        setBackground(Color.YELLOW.darker().darker());
    }

    /** 
     * Draws tetris pieces on the game panel.
     * 
     * {@inheritDoc} 
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setPaint(Color.YELLOW.darker().darker().darker());
        //System.out.print(getPreferredSize());
        
        // blockScale will be 20, 30, or 40
        final int blockScale = getWidth() / 10;
        int tinyBlockScale = FIVE; // 5
        if (blockScale == THIRTY) {
            tinyBlockScale = FIVE + 2; // 7
        } else if (blockScale == FOURTY) {
            tinyBlockScale = FIVE * 2 - 1; // 9
        }
        
        for (int i = 0; i < myBlockList.size(); i++) {
            
            final Block[] blocks = myBlockList.get(i);
            
            for (int j = 0; j < blocks.length; j++) {
                final Point pt = new Point(j * blockScale, i * blockScale);
                
                g2d.setPaint(Color.YELLOW.darker().darker().darker());
                if (blocks[j] != null) {
                    g2d.fill(new Rectangle2D.Double(pt.x(), 
                                                    getHeight() - pt.y() - blockScale, 
                                                    blockScale, blockScale));
                    g2d.setPaint(Color.BLACK);
                    g2d.drawRect(pt.x(), getHeight() - pt.y() - blockScale, 
                                 blockScale, blockScale);
                    g2d.setPaint(Color.YELLOW.darker().darker());
                    g2d.fillRect(pt.x() + tinyBlockScale, getHeight() - pt.y() - blockScale 
                                 / 2 - tinyBlockScale, blockScale / 2, blockScale / 2);
                    g2d.setPaint(Color.BLACK);
                    g2d.drawRect(pt.x() + tinyBlockScale, getHeight() - pt.y() - blockScale 
                                 / 2 - tinyBlockScale, blockScale / 2, blockScale / 2);
                }
            }
        }
    }
    
    /** 
     * Getting the data for the tetris game board.
     * 
     * {@inheritDoc} 
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {
        
        if (theObservable instanceof Board && theData instanceof List) {
            myBlockList.clear();
            for (int i = 0; i < ((List<?>) theData).size(); i++) {
                final Block[] blockArray = (Block[]) ((List<?>) theData).get(i);
                myBlockList.add(blockArray);
            }
            repaint();
        }
    }
}