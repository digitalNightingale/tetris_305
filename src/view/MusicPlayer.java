/*
 * TCSS 305 - Autumn 2016
 * Assignment 6a - Tetris
 */

package view;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class for creating the music player
 * for Tetris.
 * Music down loaded from
 * https://www.youtube.com/watch?v=NmCCQxVBfyM // Tetris song
 * https://www.youtube.com/watch?v=jq-nRPiZbtI // game over
 * https://www.youtube.com/watch?v=3bCT3YxZfAY // startup
 * 
 * @author Leah Ruisenor
 * @version December 2016.
 */
public class MusicPlayer {

    /** The file path where tetris music is located. */
    private static final String MUSIC_FILE = "./music/OriginalTetris.wav";
    
    /** The file path where game over sound is located. */
    private static final String GAME_OVER_FILE = "./music/TetrisGameOver.wav";
    
    /** The file path where start up sound is located. */
    private static final String PAUSE_FILE = "./music/StartupSound.wav";
    
    /** The file of the current music clip. */
    private File myCurrentSoundFile;
    
    /** The file of the game over sound. */
    private final File myGameOverSound;
    
    /** The file of the pause sound. */
    private final File myPauseSound;
    
    /** The current music clip for play back. */
    private Clip myMusicClip;
    
    /** If it is paused. */
    private boolean myIsPaused;
    
    /**
     * Constructor for the music player.
     */
    public MusicPlayer() {
        myCurrentSoundFile = new File(MUSIC_FILE);
        myGameOverSound = new File(GAME_OVER_FILE);
        myPauseSound = new File(PAUSE_FILE);
    }
    
    /**
     * Starting the music.
     */
    public void start() {
        myIsPaused = false;
        playMusic(myCurrentSoundFile);
    }
    
    /**
     * Play game over sound.
     */
    public void gameOver() {
        myMusicClip.stop();
        myIsPaused = true;
        playMusic(myGameOverSound);
    }
    
    /**
     * Reset player to initial state.
     */
    public void reset() {
        myMusicClip.stop();
        myCurrentSoundFile = new File(MUSIC_FILE);
    }
    
    /**
     * Pause the music.
     */
    public void pause() {
        myIsPaused = true;
        myMusicClip.stop();
        playMusic(myPauseSound);
    }
    
    /**
     * Un pause the music.
     */
    public void unPause() {
        myIsPaused = false;
        myMusicClip.stop();
        playMusic(myCurrentSoundFile);
    }
    
    /**
     * Plays the music that is in the file.
     * 
     * @param theMusicFile is the music file to be played.
     */
    private void playMusic(final File theMusicFile) {
        try {
            final AudioInputStream audioInputStream 
                = AudioSystem.getAudioInputStream(theMusicFile);
            myMusicClip = AudioSystem.getClip();
            myMusicClip.open(audioInputStream);
            if (!myIsPaused) {
                myMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            myMusicClip.start();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (final LineUnavailableException e) {
            e.printStackTrace();
        }
    } 
}