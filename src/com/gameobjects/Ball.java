package com.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Ball extends GameObject{

	private final float elasticity = 0.6f;
	float radius;
	
	public Ball(float x, float y, int radius){
		
		this.x = x;
		this.y = y;
		this.dx = 0;
		this.dy = 0;
		this.dx2 = 0;
		this.dy2 = 0;
		this.radius = radius;
	}
	
	public float getRadius(){
		
		return this.radius;
	}

	@Override
	public void draw(Canvas canvas) {
		
		Paint p = new Paint();
		p.setColor(Color.RED);
		p.setStyle(Style.FILL);
		canvas.drawCircle((int)x, (int)y, radius, p);
		
	}
	
}
