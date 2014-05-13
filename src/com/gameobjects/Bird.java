package com.gameobjects;


import com.example.fallinggametest.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bird extends GameObject{

	private Bitmap spriteL, spriteR;
	
	public Bird(float x, float y, float dx, float dy, Context context){
		
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
		
	}
}
