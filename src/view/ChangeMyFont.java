/*
 * TCSS 305 - Autumn 2016
 * Assignment 6a - Tetris
 */

package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.border.TitledBorder;

/**
 * The panel for the status of the game.
 * 
 * @author Leah Ruisenor
 * @version December 2016.
 */
public class ChangeMyFont {
    
    /** The ttf file where the font is stored. */
    private final File myFontFile;
    
    /**
     * Constructor for the ChangeMyFont class.
     */
    public ChangeMyFont() {
        super();
        myFontFile = new File("./images/PressStart2P.ttf");
    }
    
    /**
     * This method will change the font of the 
     * TitledBoarder text.
     * 
     * @param theTitle is the Title text of the border to be changed.
     * @param theFontSize is the size that the font will be.
     */
    public void changeFont(final TitledBorder theTitle, final int theFontSize) {
        Font f = null;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, 
                                new FileInputStream(myFontFile)).deriveFont(
                                Font.PLAIN, theFontSize);
        } catch (final FontFormatException | IOException e) {
            e.printStackTrace();
        }
        theTitle.setTitleFont(f);
    }
    
    /**
     * This method will change the font of the 
     * Component text.
     * 
     * @param theComponent is the Title text of the border to be changed.
     * @param theFontSize is the size that the font will be.
     */
    public void changeFont(final Component theComponent, final int theFontSize) {
        Font f = null;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, 
                                 new FileInputStream(myFontFile)).deriveFont(
                                 Font.PLAIN, theFontSize);
        } catch (final FontFormatException | IOException e) {
            e.printStackTrace();
        }
        theComponent.setFont(f);
        if (theComponent instanceof Container) {
            for (final Component c : ((Container) theComponent).getComponents()) {
                changeFont(c, theFontSize);
            }
        }
    }
}