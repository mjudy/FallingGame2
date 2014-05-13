package com.collision;

/**
 * @author Evan Hanger, Andrew Huber, Mark Judy
 * 5/6/2014, 3:40pm
 * 
 * 	This class is used within the Hitbox class. Each Hitbox object is composed
 * 	of four (4) Corners. 
 */

public class Corner {
	
	/**
	 * 
	 * The coordinates for the Corner. These are made public to allow for quick, 
	 * easy access. 
	 */
	public int x, y;
	
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public Corner(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Allows changing of the position of the Corner
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		
		this.x = x;
		this.y = y;
	}
}
