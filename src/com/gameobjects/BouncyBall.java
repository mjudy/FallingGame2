package com.gameobjects;

import java.util.ArrayList;

import com.example.fallinggametest.Game;
import com.collision.Hitbox;

public class BouncyBall extends Ball{

	public BouncyBall(float x, float y, int radius) {
		super(x, y, radius);
		
	}
	
	public void updatePhysics(float deltaTime){
		
		dx += dx2 * deltaTime;
		dy += dy2 * deltaTime;

		x += dx * deltaTime;
		y += dy * deltaTime;
		
		if(hitbox != null){
			
			hitbox.setPosition((int)x, (int)y);
		}
		
		// now, check for bounce conditions
		
		if( (x - radius) <= 0 || (x + radius) >= Game.screenWidth){
			dx = -dx;
		}
		
		if( (y - radius) <= 0 || (y + radius) >= Game.screenHeight){
			dy = -dy;
		}
		
	}
	
	public void createHitbox(){
		
		this.hitbox = new Hitbox((int)x, (int)y, 2*(int)radius, 2*(int)radius);
	}

	public void checkForCollisions(ArrayList<GameObject> gameObjects){
		
		for(int i = 0; i < gameObjects.size(); i++){
			
			if(isColliding(gameObjects.get(i))){
				
				dx = -dx;
				dy = -dy;
			}
		}
	}
}
