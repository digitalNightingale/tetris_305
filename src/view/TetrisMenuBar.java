/*
 * TCSS 305 - Autumn 2016
 * Assignment 6a - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import model.Board;

/**
 * The menu bar class for Tetris.
 * 
 * @author Leah Ruisenor
 * @version December 2016
 */
public class TetrisMenuBar extends JMenuBar implements Observer {
    
    /** Serialization. */
    private static final long serialVersionUID = 5439753581584358089L;
    
    /** The default small font size. */
    private static final int SM_FONT_SIZE = 8;
    
    /** The default medium font size. */
    private static final int MED_FONT_SIZE = 10;
    
    /** The default small board size. */
    private static final Dimension SM_BOARD = new Dimension(200, 400);
    
    /** The medium board size. */
    private static final Dimension MED_BOARD = new Dimension(300, 600);
    
    /** The default large board size. */
    private static final Dimension LRG_BOARD = new Dimension(400, 800);
    
    /** The JFrame window. */
    private final JFrame myFrame;
    
    /** The new font for the text. */
    private final ChangeMyFont myNewFont;
    
    /** The Timer for game. */
    private final Timer myTimer;
    
    /** The Tetris game board. */
    private final Board myGameBoard;
    
    /** the next piece panel. */
    private final TetrisUpNextPanel myNextPiecePanel;
    
    /** The Tetris panel to display the Tetris pieces. */
    private final TetrisGamePanel myTetrisGamePanel;
    
    /** The Tetris panel to display the information about scoring. */
    private final TetrisInfoPanel myInfoPanel;
    
    /** The control keys for moving Tetris pieces. */
    private TetrisControlKeys myControlKeys;
    
    /** The game over text label. */
    private JLabel myGameOverText;

    /** The new game button. */
    private JMenuItem myNewGameButton;
    
    /** The pause/unpause game button. */
    private JMenuItem myPauseGameButton;

    /** The quit current game button. */
    private JMenuItem myQuitCurrentGame;
    
    /** The exit button. */
    private JMenuItem myExitButton;
    
    /** The music player. */
    private MusicPlayer myMusicPlayer;
    
    /**
     * Constructor for setting up the menu bar for 
     * the Tetris game.
     * 
     * @param theFrame is the Tetris game frame.
     * @param theGameBoard is the game board info.
     * @param theTimer is the game timer.
     * @param theGamePanel is the actual game panel where the pieces are drawn.
     * @param theUpNextPanel is the panel where the next piece is shown.
     * @param theInfoPanel is the scoring panel.
     */
    public TetrisMenuBar(final JFrame theFrame, final Board theGameBoard,
                         final Timer theTimer,
                         final TetrisGamePanel theGamePanel, 
                         final TetrisUpNextPanel theUpNextPanel,
                         final TetrisInfoPanel theInfoPanel) {
        super();
        myTimer = theTimer;
        myGameBoard = theGameBoard;
        myTetrisGamePanel = theGamePanel;
        myNewFont = new ChangeMyFont();
        myFrame = theFrame;
        myNextPiecePanel = theUpNextPanel;
        myInfoPanel = theInfoPanel;
        buildMenuBar();
        setUpGame();
    }

    /**
     * Setting up the keys and observers.
     */
    private void setUpGame() {
        setGameBoardSize(SM_BOARD);
        gameOverLabel();
        myControlKeys = new TetrisControlKeys(myGameBoard);
        myControlKeys.setPieceCanMove(false);
        myFrame.addKeyListener(myControlKeys);
        myGameBoard.addObserver(myTetrisGamePanel);
        myGameBoard.addObserver(myNextPiecePanel);
        myGameBoard.addObserver(this);
        myMusicPlayer = new MusicPlayer();
    }
    
    /**
     * Pauses the game timer and board if pause button
     * is clicked or restarts the timer and board if
     * clicked again.
     */
    private void pauseGame() {
        if (myTimer.isRunning()) {
            myTimer.stop();
            myMusicPlayer.pause();
            myTetrisGamePanel.setVisible(false);
            myQuitCurrentGame.setEnabled(false);
            myTetrisGamePanel.repaint();
            myControlKeys.setPieceCanMove(false);
        } else {
            myTimer.start();
            myMusicPlayer.unPause();
            myTetrisGamePanel.setVisible(true);
            myQuitCurrentGame.setEnabled(true);
            myControlKeys.setPieceCanMove(true);     
        }
    }
    
    /**
     * Building the game over label.
     */
    private void gameOverLabel() {
        myGameOverText = new JLabel("GAME OVER!");
        myNewFont.changeFont(myGameOverText, MED_FONT_SIZE * 2 - 2);
        myGameOverText.setForeground(Color.LIGHT_GRAY);   
    }
    
    /**
     * Sets the size of the Tetris Game board.
     * 
     * @param theGameSize the default Dimension of the board to be set.
     */
    private void setGameBoardSize(final Dimension theGameSize) {
        myTetrisGamePanel.setPreferredSize(theGameSize);
        myFrame.pack();
    }
    
    /**
     * Building the Menu Bar.
     */
    private void buildMenuBar() {
        
        final JMenu fileMenu = buildFileMenu();
        final JMenu optionMenu = bulidSizeMenu();
        final JMenu helpMenu = buildHelpMenu();
        
        myNewFont.changeFont(fileMenu, SM_FONT_SIZE);
        myNewFont.changeFont(optionMenu, SM_FONT_SIZE);
        myNewFont.changeFont(helpMenu, SM_FONT_SIZE);
        
        add(fileMenu);
        add(optionMenu);
        add(helpMenu);
    }

    
     /**
      * Building the file menu.
      * 
      * @return fileMenu with the File button and Start/Exit sub buttons.
      */
    private JMenu buildFileMenu() {
       
        final JMenu fileMenu = new JMenu("Game");
        fileMenu.setMnemonic(KeyEvent.VK_G);
        
        myNewGameButton = new JMenuItem("Start", KeyEvent.VK_S);
        myPauseGameButton = new JMenuItem("Pause/Un-Pause", KeyEvent.VK_P);
        myPauseGameButton.setAccelerator(KeyStroke.getKeyStroke(
                                         KeyEvent.VK_P, ActionEvent.ALT_MASK));
        myQuitCurrentGame = new JMenuItem("Quit", KeyEvent.VK_Q);
        myExitButton = new JMenuItem("Exit", KeyEvent.VK_E);
        
        final MenuBarActionListener menuBarAction = new MenuBarActionListener();
        myNewGameButton.addActionListener(menuBarAction);
        myPauseGameButton.addActionListener(menuBarAction);
        myQuitCurrentGame.addActionListener(menuBarAction);
        myExitButton.addActionListener(menuBarAction);

        myNewFont.changeFont(myNewGameButton, SM_FONT_SIZE);
        myNewFont.changeFont(myPauseGameButton, SM_FONT_SIZE);
        myNewFont.changeFont(myQuitCurrentGame, SM_FONT_SIZE);
        myNewFont.changeFont(myExitButton, SM_FONT_SIZE);
        
        myPauseGameButton.setEnabled(false);
        myQuitCurrentGame.setEnabled(false);
        
        fileMenu.add(myNewGameButton);
        fileMenu.addSeparator();
        fileMenu.add(myPauseGameButton);
        fileMenu.addSeparator();
        fileMenu.add(myQuitCurrentGame);
        fileMenu.addSeparator();
        fileMenu.add(myExitButton);
        return fileMenu;
    }
    
    /**
     * Building the size menu.
     * 
     * @return sizeMenu with the three preset size buttons.
     */
    private JMenu bulidSizeMenu() {
        
        final JMenu sizeMenu = new JMenu("Size");
        sizeMenu.setMnemonic(KeyEvent.VK_S);
        
        final ButtonGroup group = new ButtonGroup();   
        final JRadioButtonMenuItem sm = new JRadioButtonMenuItem("Small");
        final JRadioButtonMenuItem med = new JRadioButtonMenuItem("Medium");
        final JRadioButtonMenuItem lrg = new JRadioButtonMenuItem("Large");
        
        myNewFont.changeFont(sm, SM_FONT_SIZE);
        myNewFont.changeFont(med, SM_FONT_SIZE);
        myNewFont.changeFont(lrg, SM_FONT_SIZE);
        
        group.add(sm);
        group.add(med);
        group.add(lrg);
        
        sm.addActionListener((theEvent) -> { 
            setGameBoardSize(SM_BOARD);
        });
        
        med.addActionListener((theEvent) -> { 
            setGameBoardSize(MED_BOARD);
        });
        
        lrg.addActionListener((theEvent) -> { 
            setGameBoardSize(LRG_BOARD);
        });
       
        sm.setSelected(true);
        sizeMenu.add(sm);
        sizeMenu.add(med);
        sizeMenu.add(lrg);
        return sizeMenu;
    }
    
    /**
     * Building the help subMenu.
     * 
     * @return helpMenu with the Help and About menus.
     */
    private JMenu buildHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        final String newLine = System.getProperty("line.separator");
        final Icon lmrImage = new ImageIcon("./images/lmr.gif");
        final JMenuItem aboutPopUp = new JMenuItem("About...", KeyEvent.VK_A);
        aboutPopUp.addActionListener((theEvent) -> { 
            JOptionPane.showMessageDialog(myFrame, "TCSS 305 Tetris" + newLine
                                          + "Autumn 2016" + newLine + "Leah Ruisenor"
                                          + newLine + "Downloaded PressStart2P font from:"
                                          + newLine
                                          + "http://www.dafont.com/press-start-2p.font"
                                          + newLine + "Downloaded Music Clips from:"
                                          + newLine
                                          + "https://www.youtube.com/watch?v=NmCCQxVBfyM"
                                          + newLine
                                          + "https://www.youtube.com/watch?v=jq-nRPiZbtI"
                                          + newLine
                                          + "https://www.youtube.com/watch?v=3bCT3YxZfAY",
                                          "About", 0, lmrImage);
        });
        final JMenuItem scoringPopUp = new JMenuItem("Scoring...", KeyEvent.VK_S);
        scoringPopUp.addActionListener((theEvent) -> { 
            JOptionPane.showMessageDialog(myFrame, "Point Scoring System" + newLine
                                          + "Piece Placed =  4 pt" + newLine 
                                          + "1 Line  Cleared =  40 * Level" + newLine
                                          + "2 Lines Cleared =  100 * Level" + newLine
                                          + "3 Lines Cleared =  300 * Level" + newLine
                                          + "4 Lines Cleared =  120 * Level", 
                                          "Game Scoring", 0, lmrImage);
        });
        
        myNewFont.changeFont(aboutPopUp, SM_FONT_SIZE);
        myNewFont.changeFont(scoringPopUp, SM_FONT_SIZE);
        
        helpMenu.add(aboutPopUp);
        helpMenu.add(scoringPopUp);
        return helpMenu;
    }
    
    /** 
     * Checks if the game is over and displays
     * the game over text, stops the timer and
     * sets the enable new game button to true.
     * 
     * {@inheritDoc} 
     */
    @Override
    public void update(final Observable theObservable, final Object theData) {
        if (theObservable instanceof Board && theData instanceof Boolean) {
            myTimer.stop();
            myMusicPlayer.reset();
            myMusicPlayer.gameOver();
            myTetrisGamePanel.add(myGameOverText);
            myNewGameButton.setEnabled(true);
            myControlKeys.setPieceCanMove(false);
            myPauseGameButton.setEnabled(false);
            myQuitCurrentGame.setEnabled(false);
            myFrame.validate();
        }
    }
    
    /**
     * Creating the action listeners for the menu bar
     * for starting the game or exiting the game.
     */
    private class MenuBarActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            
            if (theEvent.getSource() == myNewGameButton) {
                myTimer.start();
                myMusicPlayer.start();
                myGameBoard.newGame();
                myTetrisGamePanel.remove(myGameOverText);
                myNewGameButton.setEnabled(false);
                myControlKeys.setPieceCanMove(true);
                myPauseGameButton.setEnabled(true);
                myQuitCurrentGame.setEnabled(true);
                myInfoPanel.clearScores();
            }
            if (theEvent.getSource() == myPauseGameButton) {
                pauseGame();
            }
            if (theEvent.getSource() == myQuitCurrentGame) {
                gameOverLabel();
                myTimer.stop();
                myMusicPlayer.reset();
                myMusicPlayer.gameOver();
                myTetrisGamePanel.add(myGameOverText);
                myNewGameButton.setEnabled(true);
                myControlKeys.setPieceCanMove(false);
                myPauseGameButton.setEnabled(false);
                myQuitCurrentGame.setEnabled(false);
            }
            if (theEvent.getSource() == myExitButton) {
                myFrame.dispatchEvent(new WindowEvent(myFrame,
                                                      WindowEvent.WINDOW_CLOSING));
            }
        }
    }
}