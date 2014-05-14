package com.gameobjects;

import com.collision.PhysVector;

import android.graphics.Canvas;

/**
 * An abstract class that defines information regarding the missile game objects.
 * This allows the missiles to change orientation in flight and move along vectors different from the x and y axes.
 * @author Evan Hanger, Andrew Huber, Mark Judy
 */
public abstract class Missile extends GameObject{

	// this is a position vector that the Missile will fly towards
	protected PhysVector waypoint;
	protected float speed;
	
	/**
	 * This method gets overwritten because we need to be able to do 
	 * a rotation of the missile before it gets drawn. 
	 */
	@Override
	public void draw(Canvas canvas){
		
		// calculate the amount of degrees to rotate the canvas by
		
		PhysVector frontPoint = new PhysVector(x, y + sprite.getHeight()/2);
		PhysVector rearPoint = new PhysVector(x + sprite.getWidth(), y + sprite.getHeight()/2);
		PhysVector rearToFront = PhysVector.subtract(frontPoint, rearPoint);
		PhysVector rearToWaypoint = PhysVector.subtract(waypoint, rearPoint);
		
		float theta = (float)Math.acos(PhysVector.dotProduct(rearToFront, rearToWaypoint)/(rearToFront.magnitude() * rearToWaypoint.magnitude()));
		float degrees = (float)Math.toDegrees(theta);
		
		// perform the rotation of the canvas before drawing
		canvas.rotate(degrees, rearPoint.x, rearPoint.y);
		
		canvas.drawBitmap(sprite, x, y, null);
		
		// rotate the canvas in the opposite direction after drawing
		canvas.rotate(-degrees, rearPoint.x, rearPoint.y);
	}
	
}
