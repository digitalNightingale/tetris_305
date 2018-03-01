/*
 * TCSS 305 - Autumn 2016
 * Assignment 6a - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Board;
import model.Point;
import model.TetrisPiece;

/**
 * The class for creating the Tetris
 * piece that is up next in the que for
 * the display on the panel.
 * 
 * @author Leah Ruisenor
 * @version December 2016.
 */
public class TetrisUpNextPanel extends JPanel implements Observer {
    
    /** Serialization. */
    private static final long serialVersionUID = -4186141619837165386L;
    
    /** The default size of the panel. */
    private static final Dimension PREF_SIZE = new Dimension(200, 100);
    
    /** The default size of the medium Tetris piece block. */
    private static final int MED_BLOCK_SCALE = 30;
    
    /** The default size of the large Tetris piece block. */
    private static final int LRG_BLOCK_SCALE = 40;
    
    /** The default size of the first shift of 4. */
    private static final int SHIFT4_SCALE = 4;
    
    /** The default size of the second shift of 5. */
    private static final int SHIFT5_SCALE = 5;
    
    /** Tetris piece that is up next to play. */
    private TetrisPiece myNextPiece;
    
    /** Points of the block. */
    private Point[] myPoints;
    
    /**
     * Constructs the next piece panel.
     */
    public TetrisUpNextPanel() {
        super();
//        final ChangeMyFont newFont = new ChangeMyFont();
//        final TitledBorder name = new TitledBorder("Next Piece");
//        name.setTitleJustification(TitledBorder.CENTER);
//        UIManager.put("TitledBorder.border", new LineBorder(Color.RED.darker(), 1));
//        setBorder(name);
//        newFont.changeFont(name, SHIFT5_SCALE * 2);
        final JLabel name = new JLabel("Next Piece");
        add(name);
        setBackground(Color.GRAY);
        setPreferredSize(PREF_SIZE); 
    }
    
    /** 
     * Drawing the next Tetris piece in the que on the panel.
     * 
     * {@inheritDoc} 
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        // blockScale will be 20, 30, or 40
        final int blockScale = getHeight() / 10;
        int tinyBlockScale = SHIFT5_SCALE; // 5
        int tinyBlockShift = SHIFT5_SCALE * 2; // 10

//        System.out.println(getHeight()); //200 //300 //400
//        System.out.println(getWidth());  //200  //200 //200
        
        if (myNextPiece != null) {
            myPoints = myNextPiece.getPoints();
            for (final Point p : myPoints) {
                
                int shiftUpToCenter = MED_BLOCK_SCALE * 2; // 60
                int shiftUp = SHIFT4_SCALE * 2; // 4
                int shiftLeft = SHIFT5_SCALE - 2; // 3
                int centerMore = 0;
                
                if (blockScale == MED_BLOCK_SCALE) {
                    
                    shiftUpToCenter += MED_BLOCK_SCALE; // 90
                    shiftUp += SHIFT4_SCALE; // 12
                    shiftLeft = SHIFT4_SCALE;
                    centerMore = SHIFT4_SCALE;
                    tinyBlockScale = SHIFT5_SCALE + 2; // 7
                    tinyBlockShift =  SHIFT5_SCALE * 2 + SHIFT4_SCALE; // 14
                }
                if (blockScale == LRG_BLOCK_SCALE) {
                    
                    shiftUpToCenter *= 2; // 120
                    shiftUp = SHIFT4_SCALE * SHIFT4_SCALE; // 16
                    shiftLeft *= 2; // 6
                    centerMore = SHIFT5_SCALE * 2; // 10
                    tinyBlockScale = SHIFT5_SCALE * 2 - 1; // 9
                    tinyBlockShift = SHIFT5_SCALE * 2 + SHIFT4_SCALE * 2; // 18
                }
                
                int widthCalc =   (p.x() * blockScale + getWidth() / shiftLeft) + SHIFT5_SCALE;
                int heightCalc = (getHeight() - p.y() * blockScale - blockScale)
                                  - shiftUpToCenter;
                
                g2d.setPaint(Color.YELLOW.darker().darker().darker());
                
                // for centering the odd sized Tetris pieces.
                if ("I".equals(myNextPiece.name())) {
                    heightCalc = (getHeight() - p.y() * blockScale - blockScale)
                                  - shiftUpToCenter + shiftUp;
                    widthCalc = (p.x() * blockScale) + (getWidth() / shiftLeft) 
                                    - shiftLeft - centerMore;
                } else if ("O".equals(myNextPiece.name())) {
                    widthCalc = (p.x() * blockScale) + (getWidth() / shiftLeft) 
                                    - shiftLeft - centerMore;
                }
                g2d.setPaint(Color.YELLOW.darker().darker().darker());
                g2d.fillRect(widthCalc, heightCalc, blockScale, blockScale);
                g2d.setPaint(Color.BLACK); 
                g2d.drawRect(widthCalc, heightCalc, blockScale, blockScale);
//                if (myNextPiece.name().contains("O")) {
//                    g2d.fill(new Rectangle2D.Double(widthCalc + tinyBlockScale, 
//                                                    heightCalc - tinyBlockScale + 10, 
//                                                    blockScale / 2, blockScale / 2));
//                } else if (myNextPiece.name().contains("S")) {
//                    g2d.setPaint(Color.YELLOW.darker().darker());
//                    g2d.fill(new Rectangle2D.Double(widthCalc + tinyBlockScale, 
//                                                    heightCalc - tinyBlockScale + 10, 
//                                                    blockScale / 2, blockScale / 2));
//                }
                g2d.setPaint(Color.YELLOW.darker().darker());
                g2d.fillRect(widthCalc + tinyBlockScale, heightCalc 
                             - tinyBlockScale + tinyBlockShift, 
                                                blockScale / 2, blockScale / 2);
                g2d.setPaint(Color.BLACK); 
                g2d.drawRect(widthCalc + tinyBlockScale, heightCalc
                             - tinyBlockScale + tinyBlockShift, 
                                                blockScale / 2, blockScale / 2);
            }
        }
    }
    
    /** 
     * Gets the next Tetris piece from the game board
     * so the next piece can be updated and displayed.
     * 
     * {@inheritDoc} 
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {
        if (theObservable instanceof Board && theData instanceof TetrisPiece) {
            myNextPiece = (TetrisPiece) theData;
            repaint();
        }
    }
}