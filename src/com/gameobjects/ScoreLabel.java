package com.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

/**
 * Defines a game object that shows the current score to the player.
 */
public class ScoreLabel extends GameObject{
	
	public int score;
	public int gameHeight;

    /**
     * Creates the score label at the given x and y coordinates, with the game height for scaling the text size.
     * @param x the top left x coordinate
     * @param y the top left y coordinate
     * @param gameHeight The height of the game area for scaling the text size.
     */
	public ScoreLabel(float x, float y, int gameHeight){
		
		this.x = x;
		this.y = y;
		this.score = 0;
		this.gameHeight = gameHeight;
	}

    /**
     * Sets the label to show a new score.
     * @param score the new score to show
     */
	public void setScore(int score){
		
		this.score = score;
	}

    /**
     * Draws the score label on the given canvas
     * @param canvas the canvas on which to draw the sprite
     */
    @Override
	public void draw(Canvas canvas){
		
		String text = String.format("Score: %d", score);
		
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.FILL);
		paint.setTextSize(gameHeight / 30);
		
		canvas.drawText(text, x, y, paint);
		
	}
}
