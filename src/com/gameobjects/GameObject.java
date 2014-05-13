package com.gameobjects;

import java.util.ArrayList;

import com.collision.Hitbox;
import com.collision.PhysVector;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public abstract class GameObject {

	/** variables for position, velocity, acceleration */
	protected float x, y, dx, dy, dx2, dy2;
	
	protected int screenHeight, screenWidth;
	
	protected Hitbox hitbox;
	
	protected boolean alive;
	
	protected Bitmap sprite;
	
	public void draw(Canvas canvas){
		
		if(sprite != null){
			
			canvas.drawBitmap(sprite, x, y, null);
		}
	}
	
	public void createHitboxForSprite(){
		
		if(sprite != null){
			this.hitbox = new Hitbox((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
		}
	}
	
	public void setPosition(float x, float y){
		
		this.x = x;
		this.y = y;
	}
	
	public void setVelocityX(float dx){
		this.dx = dx;
	}
	
	public void setVelocityY(float dy){
		this.dy = dy;
	}
	
	public void setAccelX(float dx2){
		this.dx2 = dx2;
	}
	
	public void setAccelY(float dy2){
		this.dy2 = dy2;
	}
	
	public float getXPos(){
		
		return this.x;
	}
	
	public float getYPos(){
		
		return this.y;
	}
	
	public float getVelocityX(){
		
		return this.dx;
	}
	
	public float getVelocityY(){
		
		return this.dy;
	}
	
	public float getAccelX(){
		
		return this.dx2;
	}
	
	public float getAccelY(){
		
		return this.dy2;
	}
	
	public float getCenterX(){
		
		return x + sprite.getWidth()/2;
	}
	
	public float getCenterY(){
		
		return y + sprite.getHeight()/2;
	}
	
	public void updatePhysics(float deltaTime){
		
		dx += dx2 * deltaTime;
		dy += dy2 * deltaTime;

		x += dx * deltaTime;
		y += dy * deltaTime;
		
		if(hitbox != null){
			
			hitbox.setPosition((int)x, (int)y);
		}
		
		
	}
	
	public void setSprite(Bitmap bmp){
		
		this.sprite = bmp;
	}
	
	public void checkForCollisions(ArrayList<GameObject> gameObjects){
		// do nothing unless overwritten
	}
		
	// TODO
	public boolean isColliding(GameObject other){
	
		if(this.hitbox != null && other.hitbox != null){
			return this.hitbox.isColliding(other.hitbox);
		} else {
			return false;
		}
	}
		
	
	
}
