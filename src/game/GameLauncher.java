package game;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * The launcher for the game.
 * @author Eclipse
 *
 */
public class GameLauncher {
	private static Game game = new Game();
	
	public static void main(String[] args) {
		initGameFrame();
		game.start();
	}
	
	private static void initGameFrame() {
		
		game.setMinimumSize(Game.DIMENSIONS);
		game.setMaximumSize(Game.DIMENSIONS);
		game.setPreferredSize(Game.DIMENSIONS);
		
		game.frame = new JFrame(Game.NAME);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLayout(new BorderLayout());
		game.frame.add(game, BorderLayout.CENTER);
		game.frame.pack();
		
		game.frame.setResizable(true);
		game.frame.setLocationRelativeTo(null);
		game.frame.setFocusable(true);
		game.frame.setVisible(true);
		
	}
}
