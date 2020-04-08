package game.handlers;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import game.Game;

/**
 * Handles any window related events.
 * @author Eclipse
 *
 */
public class WindowHandler implements WindowListener{

	private Game game;
	public WindowHandler(Game game) {
		this.game = game;
		this.game.frame.addWindowListener(this);
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("Closing Game...");
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
