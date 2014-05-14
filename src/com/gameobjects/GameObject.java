package com.gameobjects;

import java.util.ArrayList;

import com.collision.Hitbox;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Base class that defines game objects for Falling with methods to create their hitboxes, draw their sprites
 * and update their location on the game screen.
 */
public abstract class GameObject {

	/** variables for position, velocity, acceleration */
	public float x, y, dx, dy, dx2, dy2;
	
	protected int screenHeight, screenWidth;
	
	protected Hitbox hitbox;
	
	public boolean alive;
	
	protected Bitmap sprite;

    /**
     * Draws the game objects sprite on the given canvas.
     * @param canvas the canvas on which to draw the sprite
     */
	public void draw(Canvas canvas){
		
		if(sprite != null){
			
			canvas.drawBitmap(sprite, x, y, null);
		}
	}

    /**
     * Creates a hitbox for the game object based on the object's sprite.
     */
	public void createHitboxForSprite(){
		
		if(sprite != null){
			this.hitbox = new Hitbox((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
		}
	}

    /**
     * Returns the center x coordinate of the game object.
     * @return the center x coordinate of the game object.
     */
	public float getCenterX() {
		return x + sprite.getWidth() / 2;
	}

    /**
     * Returns the center y coordinate of the game object.
     * @return the center y coordinate of the game object.
     */
	public float getCenterY() {
		return y + sprite.getHeight() / 2;
	}

    /**
     * Updates the game objects position using its velocity and the time increment.
     * @param deltaTime the time increment.
     */
	public void updatePhysics(float deltaTime){
		
		// update velocity
		dx += dx2 * deltaTime;
		dy += dy2 * deltaTime;

		// update position
		x += dx * deltaTime;
		y += dy * deltaTime;
		
		//update the position of the hitbox
		if(hitbox != null){
			
			hitbox.setPosition((int) x, (int) y);
		}
	}

    /**
     * Checks for collisions with other game objects.
     * @param gameObjects list of other game objects.
     */
	public void checkForCollisions(ArrayList<GameObject> gameObjects){
		// do nothing unless overwritten
	}

    /**
     * Checks if this game object is colliding with another game object.
     * @param other the other game object to check for collision with.
     * @return true if the objects are colliding.
     */
	public boolean isColliding(GameObject other){
	
		if(this.hitbox != null && other.hitbox != null){
			return this.hitbox.isColliding(other.hitbox);
		} else {
			return false;
		}
	}
		
	
	
}
