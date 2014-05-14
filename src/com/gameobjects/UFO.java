package com.gameobjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.fallinggametest.R;

/**
 * Defines the UFO game object for Falling.
 */
public class UFO extends GameObject {

    /**
     * Constructs a new UFO game object.
     * @param x the starting x coordinate
     * @param y the starting y coordinate
     * @param dx the velocity on the x axis
     * @param dy the velocity on the y axis
     * @param context the application environment of this object
     */
    public UFO(float x, float y, float dx, float dy, Context context)
    {

    	this.alive = true;
    	
        this.x = x;
        this.y = y;

        this.dx = dx;
        this.dy = dy;

        this.sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.ufo);
   
        createHitboxForSprite();
    }

}
