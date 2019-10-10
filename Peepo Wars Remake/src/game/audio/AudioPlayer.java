package game.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.handlers.CrashHandler;
import resources.ResourceLoader;

public class AudioPlayer {
	
	//Stores current position.
	Long currentFrame;
	Clip clip;
	
	//Current status of clip.
	public static String status; //play, paused, 
	
	InputStream audioSrc;
	InputStream bufferedIn;
	AudioInputStream audioInputStream;
	FloatControl gainControl;
	BooleanControl muteControl;
	static String filePath = "music/menutheme.wav";
	
	//Constructor to initialize streams and clip.
	public AudioPlayer() {
		audioSrc = ResourceLoader.loadStream(filePath);
		bufferedIn = new BufferedInputStream(audioSrc);
		//Create AudioInputStream object
			try {
				audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
			} catch (UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
				CrashHandler.throwError(e.toString());
			}
			
			//Create clip reference
			try {
				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				CrashHandler.throwError(e.toString());
			}
			
			//Open audioInputStream to the clip
			try {
				clip.open(audioInputStream);
			} catch (LineUnavailableException | IOException e) {
				e.printStackTrace();
				CrashHandler.throwError(e.toString());
			}
			
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-30.0f);
			
			muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
			muteControl.setValue(false);
			//Loops
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		
	}
	
	public String getStatus() {
		return status;
	}
	
	//Play the audio
	public void play() {
		clip.start();
		status = "play";
	}
	
	//Mute the audio
	public void mute(boolean isMuted) {
		muteControl.setValue(isMuted);
		if(isMuted) {
			status = "muted";
		} else {
			status = "unmuted";
		}
	}
	
	//Pause the audio
	public void pause() {
		if(status.equals("paused")) {
			System.out.println("audio is already paused");
			return;
		}
		
		currentFrame = clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}
	
	public void restart() {
		clip.stop();
		clip.close();
		
		resetAudioStream();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		play();
	}
	
	public void stop() {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}
	
	public void resume() {
		if(status.equals("play")) {
			return;
		}
		
		try {
			clip.open();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
//		resetAudioStream();
		clip.setMicrosecondPosition(currentFrame);
		play();
	}
	
	public void resetAudioStream() {
		audioSrc = ResourceLoader.loadStream(filePath);
		bufferedIn = new BufferedInputStream(audioSrc);
		try {
			audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
			CrashHandler.throwError(e.toString());
		}
		try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
			CrashHandler.throwError(e.toString());
		}
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
}
