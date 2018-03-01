/*
 * TCSS 305 - Autumn 2016
 * Assignment 6a - Tetris
 */

package view;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * Runs Tetris by instantiating and starting the TetrisGUI.
 * 
 * @author Leah Ruisenor
 * @version November 2016
 */
public final class TetrisMain {
    
    /**
     * Private constructor that prevents instantiation of this class.
     */
    private TetrisMain() { 
        throw new IllegalStateException();
    }
    
    /**
     * The main method to invoke the PowerPaint GUI.
     * 
     * https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     * @param theArgs is the command line argument.
     */
    public static void main(final String[] theArgs) {
        
        try {
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        //UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI().start();
            }
        });
    }
}