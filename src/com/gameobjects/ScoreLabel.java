package com.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class ScoreLabel extends GameObject{
	
	public int score;

	public ScoreLabel(float x, float y){
		
		this.x = x;
		this.y = y;
		
		this.score = 0;
	}
	
	public void setScore(int score){
		
		this.score = score;
	}
	
	public void draw(Canvas canvas){
		
		String text = String.format("Score: %d", score);
		
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setTextSize(60);
		
		canvas.drawText(text, x, y, paint);
		
	}
}
