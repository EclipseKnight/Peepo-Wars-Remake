package game.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Game;
import game.audio.AudioPlayer;
import game.levels.LevelMultiplayer;
import game.levels.LevelUsername;
import game.sprites.mobs.players.Player;

/**
 * Handles all of the input for the game.
 * @author Eclipse
 *
 */
public class InputHandler implements KeyListener {

	private int[] exceptionKeys = {KeyEvent.VK_ENTER};
	private Game game;
	
    public InputHandler(Game game) {
        game.addKeyListener(this);
        this.game = game;
    }

    /**
     * Used to create Key objects to map controls.
     * @author Eclipse
     *
     */
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
        
        //Level that takes user input
        if (Game.level instanceof LevelUsername || Game.level instanceof LevelMultiplayer) {
        	var p = Game.level.getPlayers().get(0);
        	
        	//Toggle between typing and moving
        	if (e.getKeyCode() == KeyEvent.VK_ENTER && !p.canInput()) {
        		p.setCanInput(true);
        	} else if (e.getKeyCode() == KeyEvent.VK_ENTER && p.canInput()) {
        		p.setCanInput(false);
        	}
        	
        	//If typing...
        	if (!p.canInput() && Game.level instanceof LevelUsername) {
        		StringBuilder sb = new StringBuilder(p.getName());
            	
            	if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            		if (sb.length() > 0)
            			sb.deleteCharAt(sb.length()-1);
            		
            		p.setName(sb.toString());
            		
            	} else if (isExceptionKey(e.getKeyCode())){
            		return;
            		
            	} else {
            		p.setName(sb.append(e.getKeyChar()).toString().replaceAll("[^a-zA-Z0-9]", ""));
            	}
        	}
        	
        	if (!p.canInput() && Game.level instanceof LevelMultiplayer) {
        		StringBuilder sb = new StringBuilder(LevelMultiplayer.credentials);
            	
            	if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            		if (sb.length() > 0)
            			sb.deleteCharAt(sb.length()-1);
            		
            		LevelMultiplayer.credentials = sb.toString();
            		
            	} else if (isExceptionKey(e.getKeyCode())){
            		return;
            		
            	} else {
            		LevelMultiplayer.credentials = sb.append(e.getKeyChar()).toString().replaceAll("[^a-zA-Z0-9:.]", "");
            	}
        	}
        	
        }
        
        
        
       
//        if (e.getKeyCode() == KeyEvent.VK_R) {
//        	if (game.gameState == 3) {
//        		game.leveler.firstLevel();
//        	}
//        	if (game.gameState == 2) {
//        		game.leveler.firstLevel();
//        	} 
//        	if (game.gameState == 4) {
//        		game.leveler.nextLevel();
//        	}
//        }
        
        //If moving...
        if (Game.level.getPlayers().get(0).canInput()) {
        	
        	//Pause the music
            if (e.getKeyCode() == KeyEvent.VK_M) {
            	if (AudioPlayer.status.equals("play")) {
            		game.audioPlayer.pause();
            	} else if (AudioPlayer.status.equals("paused")) {
            		game.audioPlayer.resume();
            	}
            }
            
        	if (e.getKeyCode() == KeyEvent.VK_S) {
             	Player p = Game.level.getPlayers().get(0);
             	if (p.isShieldMode()) {
             		p.setMode("normal");
             	} else if (p.getShield() > 0) {
             		p.setMode("shield");
             	}
            }
             
            if (e.getKeyCode() == KeyEvent.VK_D) {
             	Player p = Game.level.getPlayers().get(0);
             	if (p.isDemonMode()) {
             		p.setMode("normal");
             	} else {
             		p.setMode("demon");
             	}
             	
            }
             
            if (e.getKeyCode() == KeyEvent.VK_H) {
             	if (Game.showhitbox) {
             		Game.showhitbox = false;
             	}
             		
             	else {
             		Game.showhitbox = true;
             	}
            }
        }
       
        
        /*
        //Pauses the Game
        if (e.getKeyCode() == KeyEvent.VK_P) {
        	
        	if (Game.gameState != 2 && Game.gameState != 1) {
        		Game.lastGameState = Game.gameState;
        		Game.gameState = 2;
        		Board.pause();
        	} else if (Game.gameState == 2) {
        		Game.gameState = Game.lastGameState;
        		Board.resume();
        	}
        }
        */
        
        
    }

    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent e) {
    	
    }
    
    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_UP) {
            up.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            down.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            left.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            right.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_SPACE) {
        	space.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_1) {
        	one.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_2) {
        	two.toggle(isPressed);
        }
        
        
    }
    
    private boolean isExceptionKey(int key) {
    	for (Integer k : exceptionKeys) {
    		if (key == k) {
    			return true;
    		}
    	}
    	return false;
    }
}
