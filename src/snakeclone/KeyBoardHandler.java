/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeclone;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * Handles keyboard events
 * @author Manning
 */
public class KeyBoardHandler extends KeyAdapter {
	//variables for relaying info to game loop
	private boolean leftPressed;
	private boolean rightPressed;
	private boolean nPressed;
	private boolean pPressed;
	private static KeyBoardHandler keyHandler = new KeyBoardHandler();
	
	//getters and setters for the keys
	public KeyBoardHandler() {
		super();
	}
	public KeyBoardHandler get() {
		return keyHandler;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}

	public boolean isnPressed() {
		return nPressed;
	}

	public void setnPressed(boolean nPressed) {
	    this.nPressed = nPressed;
	}

	public boolean ispPressed() {
	    return pPressed;
	}

	public void setpPressed(boolean pPressed) {
	    this.pPressed = pPressed;
	}
	
	
	
	
	//sets the variables upon a key being pressed
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
		
                if (e.getKeyCode() == KeyEvent.VK_N) {
			nPressed = true;
                }	
		
		if (e.getKeyCode() == KeyEvent.VK_P) {
		    pPressed = true;
		}
	}
}
