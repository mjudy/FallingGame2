package com.gameobjects;

import com.collision.PhysVector;
import com.example.fallinggametest.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Defines the homing missile class, a special type of missile that tracks the paratrooper in Falling.
 */
public class HomingMissile extends Missile{

	private GameObject target;
	private int screenWidth;
	private boolean targetAcquired;
	
	/**
	 * Constructor for HomingMissile when it HAS a target
	 * @param x the starting x position
	 * @param y the starting y position
	 * @param speed the missile velocity
	 * @param screenWidth the width of the game screen
	 * @param target the target for the missile to track
	 * @param context information about the application environment
	 */
	public HomingMissile(float x, float y,float speed, int screenWidth, GameObject target, Context context){
		
		this(x, y, speed, screenWidth, context);
		
		this.targetAcquired = true;
		this.target = target;
	}
	
	
	/**
	 * Constructor for HomingMissile when it DOESN'T have a target
	 * @param x the starting x position
	 * @param y the starting y position
	 * @param speed the missile velocity
	 * @param screenWidth the width of the game screen
	 * @param context information about the application environment
	 */
	public HomingMissile(float x, float y, float speed, int screenWidth, Context context){
		
		this.alive = true;
		
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.targetAcquired = false;
		
		this.screenWidth = screenWidth;
		
		// default waypoint, center X of the screen, with y coordinate just out of bounds 
		this.waypoint = new PhysVector(screenWidth/2,-10);
		
		dx = dy = dx2 = dy2 = 0;
		
		this.sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.missile_sprite);
		
		// add the hitbox to this game object
		createHitboxForSprite();
		
		this.targetAcquired = false;
		this.waypoint = new PhysVector(0,0);
		
	}


    /**
     * Updates the location and orientation of the homing missile
     * @param deltaTime the time increment.
     */
	@Override
	public void updatePhysics(float deltaTime){
		
		// if missile altitude rises above its target, stop tracking it.
		if(this.y <= target.y){
			targetAcquired = false;
		}
		
		// change missile heading if it is tracking its target
		if(targetAcquired == true){
			
			/*
		 	* Update the waypoint of the missile, have a max turning speed 
		 	* so the missile isn't "too good". 
		 	*/
			
			// the turning speed is dependent on the missiles x distance to target
			float turningSpeed = 30.0f * (float)Math.abs(waypoint.x - target.x);
			
			if(waypoint.x < target.x && waypoint.x < screenWidth){
			
				waypoint.x += turningSpeed * deltaTime;
			} else if(waypoint.x > target.x && waypoint.x > 0){
			
				waypoint.x += turningSpeed * -1 * deltaTime;
			} 
		
			// calculate the new velocity vector
			PhysVector currentPos = new PhysVector(x, y);
			PhysVector velocity = PhysVector.subtract(waypoint, currentPos);
			velocity.becomeUnitVector();
			velocity.scale(speed);
		
			// update velocity of the missile
			dx = velocity.x;
			dy = velocity.y;
		}
		
		// update position of the missile
		x += dx * deltaTime;
		y += dy * deltaTime;
		
		// update position of missile's hitbox
		if(hitbox != null){
			hitbox.setPosition((int) this.getCenterX(), (int) this.getCenterY());
		}
		
	}

    /**
     * Sets a new target for this homing missile.
     * @param obj the target to lock onto
     */
	public void setTarget(GameObject obj){
		
		this.target = obj;
		this.targetAcquired = true;
	}

    /**
     * Draws the homing missile on the given canvas
     * @param canvas the canvas on which to draw
     */
	@Override
	public void draw(Canvas canvas){
		
		// use the draw method of parent Missile class
		super.draw(canvas);
	}
}
