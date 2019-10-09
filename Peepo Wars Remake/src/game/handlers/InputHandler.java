package game.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Game;
import game.audio.AudioPlayer;

public class InputHandler implements KeyListener {

	private Game game;
    public InputHandler(Game game) {
        game.addKeyListener(this);
        this.game = game;
    }

    public class Key {
        private int numTimesPressed = 0;
        private boolean pressed = false;

        public int getNumTimesPressed() {
            return numTimesPressed;
        }

        public boolean isPressed() {
            return pressed;
        }

        public void toggle(boolean isPressed) {
            pressed = isPressed;
            if (isPressed) numTimesPressed++;
        }
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key space = new Key();
    public Key one = new Key();
    public Key two = new Key();
    public Key h = new Key();
    public Key r = new Key();
    public Key m = new Key();
    public Key p = new Key();

    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
        
        //Pause the music
        if(e.getKeyCode() == KeyEvent.VK_M) {
        	if(AudioPlayer.status.equals("play")) {
        		game.audioPlayer.pause();
        	} else if(AudioPlayer.status.equals("paused")) {
        		game.audioPlayer.resume();
        	}
        }
        
        //TODO Fix so it retarts at the beginning. Maybe remake the list.
        if(e.getKeyCode() == KeyEvent.VK_R) {
        	if(game.gameState == 3) {
        		game.leveler.firstLevel();
        	}
        	if(game.gameState == 2) {
        		game.leveler.nextLevel();
        	} 
        	if(game.gameState == 4) {
        		game.leveler.nextLevel();
        	}
        }
        /*
        //Pauses the Game
        if(e.getKeyCode() == KeyEvent.VK_P) {
        	
        	if(Game.gameState != 2 && Game.gameState != 1) {
        		Game.lastGameState = Game.gameState;
        		Game.gameState = 2;
        		Board.pause();
        	} else if(Game.gameState == 2) {
        		Game.gameState = Game.lastGameState;
        		Board.resume();
        	}
        }
        */
        if(e.getKeyCode() == KeyEvent.VK_H) {
        	if(Game.showhitbox) {
        		Game.showhitbox = false;
        	}
        		
        	else {
        		Game.showhitbox = true;
        	}
        }
        
    }

    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent e) {
    	
    }
    
    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            up.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            down.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            left.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            right.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_SPACE) {
        	space.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_1) {
        	one.toggle(isPressed);
        }
        if(keyCode == KeyEvent.VK_2) {
        	two.toggle(isPressed);
        }
        
        
    }
}
