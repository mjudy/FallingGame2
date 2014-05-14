package com.gameobjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.fallinggametest.R;

/**
 * Defines the hot air balloon game object.
 */
public class Balloon extends GameObject
{
    /**
     * Constructs a new hot air balloon game object.
     * @param x the starting x coordinate
     * @param y the starting y coordinate
     * @param dx the velocity on the x axis
     * @param dy the velocity on the y axis
     * @param context the application environment of this object
     */
    public Balloon(float x, float y, float dx, float dy, Context context)
    {

        this.x = x;
        this.y = y;

        this.dx = dx;
        this.dy = dy;

        this.sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.balloon);
        createHitboxForSprite();
    }
}
