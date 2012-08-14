/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snakeclone.entity;

import java.util.ArrayList;
import snakeclone.SnakeClone;

/**
 *
 * @author Manning
 */
public class SnakeEntity extends Entity {
	//this snakes tail
	protected ArrayList snakeTail;
	//the game for this entity
	private SnakeClone game;
	//the previous cordinates
	private long lastX;
	private long lastY;

	public SnakeEntity(SnakeClone game, String ref, int x, int y) {
		super(ref, x, y);
		snakeTail = new ArrayList();
		this.game = game;
		this.setHorizontalMovement(0);
		this.setVerticleMovement(speedOfSnake);
	}

	@Override
	public void move(long delta){
		lastX = x;
		lastY = y;
		super.move(delta);
	}
	
	
	@Override
	public void collidedWith(Entity other) {
		//if collides with food add new tail segments
	        if (other instanceof FoodEntity) {
			for(int i=0; i < 6; i++){
			SnakeTailEntity tailSeg = new SnakeTailEntity("snakeclone/Res/BlueSnakeBody.gif", this);
			entities.addLivingEntity(tailSeg);
			snakeTail.add(tailSeg);
			}
		}
		//if collides with dail end the game
		if (other instanceof SnakeTailEntity) {
			game.loose();
		}
	}

	public long getLastX() {
		return lastX;
	}

	public void setLastX(long lastX) {
		this.lastX = lastX;
	}

	public long getLastY() {
		return lastY;
	}

	public void setLastY(long lastY) {
		this.lastY = lastY;
	}

	//returns the snake tail
	public ArrayList getSnakeTail() {
		return snakeTail;
	}
	
}
