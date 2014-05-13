package com.gameobjects;

import com.collision.PhysVector;
import com.example.fallinggametest.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class HomingMissile extends Missile{

	private GameObject target;
	private float speed;
	
	public HomingMissile(float x, float y, float speed, GameObject target, Context context){
		
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.target = target;
		this.waypoint = new PhysVector(target.getCenterX(), target.getCenterY());
		dx =dy = dx2 = dy2 = 0;
		
		this.sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.missile);
	
	}
	
	@Override
	public void updatePhysics(float deltaTime){
		
		// calculate the new waypoint
		this.waypoint = new PhysVector(target.getCenterX(), target.getCenterY());
		
		// calculate the new velocity vector
		PhysVector currentPos = new PhysVector(x, y);
		PhysVector velocity = PhysVector.subtract(waypoint, currentPos);
		velocity.becomeUnitVector();
		velocity.scale(speed);
		
		dx = velocity.x;
		dy = velocity.y;
		
		x += dx * deltaTime;
		y += dy * deltaTime;
		
	}
	
	@Override
	public void draw(Canvas canvas){
		
		super.draw(canvas);
	}
}
