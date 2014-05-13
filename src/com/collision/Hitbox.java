package com.collision;

/**
 * 
 * @author Evan Hanger, Andrew Huber, Mark Judy
 * 5/6/2014 3:40pm
 *
 *	This class acts as a Hitbox for each GameObject. It allows for the use
 *	of collision detection of objects within the FallingGame. 
 *
 */

public class Hitbox {

	
	/**
	 * The coordinates for center, and the width/height of the hitbox
	 */
	private int centerX, centerY, width, height;
	
	
	
	/**
	 * 
	 * The Corners of the Hitbox
	 * Lower Left, Lower Right, Upper Left, Upper Right 
	 */
	private Corner ll, lr, ul, ur;
	
	
	
	/**
	 * Constructor for Hitbox
	 * 
	 * @param centerX
	 * @param centerY
	 * @param width
	 * @param height
	 */
	public Hitbox(int centerX, int centerY, int width, int height){

		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
		
		// initialize corners which make up the hitbox
		ll = new Corner(centerX - width/2, centerY + height/2);
		lr = new Corner(centerX + width/2, centerY + height/2);
		ul = new Corner(centerX - width/2, centerY - height/2);
		ur = new Corner(centerX + width/2, centerY - height/2);
	}
	
	/**
	 * accessor for x coordinate
	 * @return
	 */
	public int getXPos(){
		
		return this.centerX;
	}
	
	/**
	 * accessor for y coordinate
	 * @return
	 */
	public int getYPos(){
		
		return this.centerY;
	}
	
	
	/**
	 * accessor for width
	 * @return
	 */
	public int getWidth(){
		
		return this.width;
	}
	
	/**
	 * accessor for height
	 * @return
	 */
	public int getHeight(){
		
		return this.height;
	}
	
	/**
	 * accessor for lower left corner
	 * @return
	 */
	public Corner getLowerLeft(){
		
		return this.ll;
	}
	
	/**
	 * accessor for lower right corner
	 * @return
	 */
	public Corner getLowerRight(){
		
		return this.lr;
	}
	
	/**
	 * accessor for upper left corner
	 * @return
	 */
	public Corner getUpperLeft(){
		
		return this.ul;
	}

	/**
	 * accessor for upper right corner
	 * @return
	 */
	public Corner getUpperRight(){
	
		return this.ur;
	}
	
	/**
	 * Allows you to change the location of the hitbox, 
	 * the locations of the corners are updated as well. 
	 * 
	 * @param centerX
	 * @param centerY
	 */
	public void setPosition(int centerX, int centerY){
		
		this.centerX = centerX;
		this.centerY = centerY;
		
		// updates position of all the corners
		ll.setPosition(centerX - width/2, centerY + height/2);
		lr.setPosition(centerX + width/2, centerY + height/2);
		ul.setPosition(centerX - width/2, centerY - height/2);
		ur.setPosition(centerX + width/2, centerY - height/2);
	}
	
	/**
	 * 
	 * Used for detecting collision with another Hitbox
	 * 
	 * Tests for mutual collision
	 */
	public boolean isColliding(Hitbox other){
		
		return (this.containsCorner(other.getLowerLeft()) 
				|| this.containsCorner(other.getLowerRight())
				|| this.containsCorner(other.getUpperLeft())
				|| this.containsCorner(other.getUpperRight())
				|| other.containsCorner(this.ll)
				|| other.containsCorner(this.lr)
				|| other.containsCorner(this.ul)
				|| other.containsCorner(this.ur));
	}
	
	/**
	 * Auxiliary method for isColliding()
	 * 
	 * tests whether or not a Corner is inside this Hitbox
	 * 
	 */
	private boolean containsCorner(Corner c){
		
		 return (c.x > ll.x && c.x < lr.x && c.y > ul.y && c.y < ll.y);
	}
	
}
