package com.gameobjects;

import com.collision.PhysVector;

import android.graphics.Canvas;

/**
 * 
 * @author Evan Hanger, Andrew Huber, Mark Judy
 *
 */
public abstract class Missile extends GameObject{

	protected PhysVector waypoint;
	
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
		
		// rotate the canvas the opposite number of degrees after drawing
		canvas.rotate(-degrees, rearPoint.x, rearPoint.y);
		
	}
	
}
