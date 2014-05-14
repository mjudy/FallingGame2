package com.gameobjects;


import com.example.fallinggametest.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Defines the bird game object for the falling game.
 */
public class Bird extends GameObject{

	private Bitmap spriteL, spriteR;

    /**
     * Constructs a new bird game object.
     * @param x the starting x coordinate
     * @param y the starting y coordinate
     * @param dx the velocity on the x axis
     * @param dy the velocity on the y axis
     * @param context the application environment of this object
     */
	public Bird(float x, float y, float dx, float dy, Context context){
		
		this.alive = true;
		
		this.x = x;
		this.y = y;
		
		this.dx = dx;
		this.dy = dy;

		this.spriteL = BitmapFactory.decodeResource(context.getResources(), R.drawable.birdl);
		this.spriteR = BitmapFactory.decodeResource(context.getResources(), R.drawable.birdr);
		
		if(dx > 0){
			sprite = spriteL;
		} else {
			sprite = spriteR;
		}
		
		// add a hitbox for this gameobject
		createHitboxForSprite();
		
	}
	
	
}
