/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeclone.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import snakeclone.EntityManagement;
import snakeclone.Sprite;
import snakeclone.SpriteStore;
/**
 *
 * @author Manning
 */
public abstract class Entity {
	//positions
	protected long x;
	protected long y;
	//speeds
	protected long speedX;
	protected long speedY;
	//image used for this entitiy
	protected Sprite sprite;
	//sets the entity management for this entity
	protected EntityManagement entities = new EntityManagement().get();
	//sets speed of a snake
	protected int speedOfSnake = -200;
	//sets rectangles for collition detection
	private Rectangle me = new Rectangle();
	private Rectangle him = new Rectangle();
	
	
	public Entity(String ref, int x, int y) {
		this.sprite = SpriteStore.get().getSprite(ref);
		this.x = x;
		this.y = y;
	}
	
	//moves the entity based on its speed
	public void move(long delta) {
		//Moves the entity
		this.x += (delta * speedX)/1000;
		this.y += (delta * speedY)/1000;
	}
	
	public void setHorizontalMovement(int x){
		this.speedX = x;
	}
	
	public void setVerticleMovement(int y) {
		this.speedY = y;
	}
	
	public long getHorizontalMovement() {
		return speedX;
	}
	
	public long getVerticleMovement() {
		return speedY;
	}
	
	//draws the entitiy
	public void draw(Graphics g) {
		sprite.draw(g, (int) x, (int) y);
	}
	
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
	//turns the entity
	public void turnLeft() {
		if(speedX > 0) {
			this.speedX = 0;
			this.speedY = speedOfSnake;
		} else if(speedX < 0){
			this.speedX = 0;
			this.speedY = -speedOfSnake;
		} else if(speedY > 0) {
			this.speedY = 0;
			this.speedX = -speedOfSnake;
		} else if(speedY < 0) {
			this.speedY = 0;
			this.speedX = speedOfSnake;
		}
	}
	public void turnRight() {
		if(speedX > 0) {
			speedX = 0;
			speedY = -speedOfSnake;
		} else if(speedX < 0){
			speedX = 0;
			speedY = speedOfSnake;
		} else if(speedY > 0) {
			speedY = 0;
			speedX = speedOfSnake;
		} else if(speedY < 0) {
			speedY = 0;
			speedX = -speedOfSnake;
		}
	}
	
	//checks if out of screen bounds	
	public boolean checkPostion() {
		if (x < 0 || x > 800 || y < 0 || y > 600)
			return true;
		return false;
	}
	
	//checks for collitions
	public boolean collidesWith(Entity other) {
		me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
		return me.intersects(him);
	}
	
	//determines what to do if collition occurs
	public abstract void collidedWith(Entity other);
}
