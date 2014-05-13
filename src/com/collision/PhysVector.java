package com.collision;

public class PhysVector {

	public float x, y;
	
	public PhysVector(float x, float y){
		
		this.x = x;
		this.y = y;
	}
	
	public float magnitude(){
		
		return (float)Math.sqrt(Math.pow((double)x, 2) + Math.pow((double)y,2));
	}
	
	public void becomeUnitVector(){
		
		float oldMag = this.magnitude();
		
		x /= oldMag;
		y /= oldMag;
	}
	
	public void scale(float n){
		
		x *= n;
		y *= n;
	}
	
	public static PhysVector add(PhysVector v1, PhysVector v2){
		
		return new PhysVector(v1.x + v2.x, v1.y + v2.y);
	}
	
	/**
	 * 
	 * Subtracts second vector from first
	 * 
	 * @param v1 the vector that is subtracted from
	 * @param v2 the vector that is subtracted
	 * @return difference between the vectors
	 */
	public static PhysVector subtract(PhysVector v1, PhysVector v2){
		
		return new PhysVector(v1.x - v2.x, v1.y - v2.y);
	}
	
	public static PhysVector unitVector(PhysVector v){
		
		return new PhysVector(v.x / v.magnitude(), v.y / v.magnitude());
	}
	
	public static float distance(PhysVector v1, PhysVector v2){
		
		return PhysVector.subtract(v1, v2).magnitude();
	}
	
	public static float dotProduct(PhysVector a, PhysVector b){
		
		return ((a.x * b.x) + (a.y * b.y));
	}
}
