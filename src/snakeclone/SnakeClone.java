/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeclone;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snakeclone.entity.Entity;
import snakeclone.entity.FoodEntity;
import snakeclone.entity.SnakeEntity;

/**
 *
 * @author Manning
 */
public class SnakeClone extends Canvas {
	//Rendering variables
	private BufferStrategy strategy;
	//Stores wether game is running
	private boolean gameIsRunning = true;
	//Stores wether game is paused
	private boolean paused = false;
	//Stores the players snake
	private SnakeEntity snake;
	//Stores the games entity managment
	private EntityManagement entities;
	//Stores and init. the games keyboard input handler
	private KeyBoardHandler keyBoard = new KeyBoardHandler().get();
	/* 
	 * Stores how many food there are left
	 * this allow for the traking of more than one food
	 */
	public int foodLeft = 0;
	//Stores what should be displayed during a paused game
	private String message = "";
	//Object for entities to reside in
	
	public SnakeClone() {
	    
		//Sets up the window for the game and adds keyboard listener
		JFrame container = new JFrame("Snake Wars");

		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(800,600));
		panel.setLayout(null);

		setBounds(0,0,800,600);
		panel.add(this);

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		container.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//Focuses the window to get keyboard inputs
		setFocusable(true);
		requestFocus();
		addKeyListener(keyBoard);

		//Creates the buffer
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		//Sets up the entities
		entities = new EntityManagement();
		
		//Starts the game
		gameStart();
	}
	
	
	public final void gameStart() {
		//kill any entities from previous games
		for(int i=0; i < entities.getEntitySize(); i++){
			Entity entity = entities.getEntity(i);
			entities.addDeadEntity(entity);
		}
		entities.destroyDeadEntities();
		
		//Adds new snake
		System.out.println("Starting Entity Init");
		snake = new SnakeEntity(this, "snakeclone/Res/BlueSnake.gif", 400, 300);
		entities.addLivingEntity(snake);
		
		//resets food count
		foodLeft = 0;
	}
	
	public final void loose() {
	    //pauses and displays you loose message
		paused = true;
		message = "You loose. Try Again? Press N";
		
	}
	
	public final void pause() {
	    //pauses the game untip p is pressed
		message = "Paused. Press P";
		if(paused)
		    paused = false;
		else
		    paused = true;
		keyBoard.setpPressed(false);
	}
	
	public void gameLoop() {
	    //starts the timer
		long lastLoopTime = System.currentTimeMillis();
		System.out.println("Starting game looop");
		//gameloop
		while(gameIsRunning) {
			//work out how long its been since the last update
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			//clear and draw
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,800,600);
			
			//Entity Collision BruteForce
			for (int p=0;p<entities.getEntitySize();p++) {
				for (int s=p+1;s<entities.getEntitySize();s++) {
					Entity me = (Entity) entities.getEntity(p);
					Entity him = (Entity) entities.getEntity(s);
					
					if (me.collidesWith(him)) {
						me.collidedWith(him);
						him.collidedWith(me);
					}
				}
			}
			
			//Removes Entities destroyed in collisions
			entities.destroyDeadEntities();
			
			//Makes sure snake is in bounds and destroys it if it isn't and
			//displays the loose message
			if (snake.checkPostion()){
				this.loose();
			}
			
			//Generates a new food if there is none left
			if(foodLeft <= 0) {
				FoodEntity food = new FoodEntity(this, "snakeclone/Res/Food.gif");
				foodLeft++;
				entities.addLivingEntity(food);
			}
			
			//poll the controls
			if(keyBoard.isLeftPressed()) {
				snake.turnLeft();
				keyBoard.setLeftPressed(false);
			}
			if(keyBoard.isRightPressed()) {
				snake.turnRight();
				keyBoard.setRightPressed(false);
			}
			
			//starts new game
			if(keyBoard.isnPressed()) {
				loose();
				gameStart();
				paused = false;
				keyBoard.setnPressed(false);
			}
			//pauses the game
			if(keyBoard.ispPressed())
				pause();
			
			//Move and draw the entities
			if (!paused) {
				for (int i=0;i<entities.getEntitySize();i++) {
					Entity entity = entities.getEntity(i);
					entity.move(delta);
				}
			}
			
			for (int i=0;i<entities.getEntitySize();i++) {
				Entity entity = entities.getEntity(i);
				entity.draw(g);
			}
			//If paused draws the message
			if (paused) {
				g.setColor(Color.WHITE);
				g.drawString(message, 300, 300);
			}
			
			//destroy object and flip buffer
			g.dispose();
			strategy.show();
			
			
			//allows each framebuffer and logic loop to occur no more than
			//100 times a second
			try { Thread.sleep(lastLoopTime + 100 - System.currentTimeMillis()); } catch (Exception e) {}
			
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String argv[]) {
		SnakeClone g = new SnakeClone();
		g.gameLoop();
	}
}
