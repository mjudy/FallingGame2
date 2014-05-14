package com.gameobjects;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Surface;
import android.view.WindowManager;

import com.collision.PhysVector;
import com.example.fallinggametest.Game;
import com.example.fallinggametest.R;

/**
 * Defines methods and data relating to the paratrooper in Falling. He's the hero of our game!
 */
public class Trooper extends GameObject{
	
	private PhysVector destination;
	
	public final float MAX_SPEED;
	
	private int orientation;
	
	private Game game;

    /**
     * Creates a new Trooper at a given starting position, for a given game, and with knowledge of the game's width for
     * scaling the trooper's movement speed.
     * @param x the top left x coordinate
     * @param y the top left y coordinate
     * @param game the game in which the trooper will spawn
     * @param gameWidth the width of the game screen for scaling the trooper's movement speed.
     */
	public Trooper(float x, float y, Game game, int gameWidth) {
		
		this.x = x;
		this.y = y;
		this.game = game;
		this.MAX_SPEED = gameWidth;
		
		dx = dy = dx2 = dy2 = 0;
		
		this.alive = true;
		
		this.sprite = BitmapFactory.decodeResource(game.getResources(), R.drawable.trooper);
		createHitboxForSprite();
		
		SensorManager manager = (SensorManager) game.getSystemService(Context.SENSOR_SERVICE);
		manager.registerListener(listener, manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		
		WindowManager wm = (WindowManager) game.getSystemService(Context.WINDOW_SERVICE);
		orientation = wm.getDefaultDisplay().getRotation();
	}

    /**
     * Checks for collisions with other game objects.
     * @param gameObjects list of other game objects.
     */
    @Override
	public void checkForCollisions(ArrayList<GameObject> gameObjects){
		
		for(int i = 0; i < gameObjects.size(); i++){
			
			GameObject obj = gameObjects.get(i);
			
			if(isColliding(obj)){
				
				if(obj instanceof Bird 
						|| obj instanceof UFO 
						|| obj instanceof Balloon
						|| obj instanceof NormalMissile
						|| obj instanceof HomingMissile ){
					
					
					this.alive = false;
				}
			}
			else if(this.x + sprite.getWidth() <= 0 || this.x >= game.screenWidth)
				this.alive = false;
		}
	}

    /**
     * Updates the trooper's location on the game screen.
     * @param deltaTime the time increment.
     */
	@Override
	public void updatePhysics(float deltaTime){
		
		super.updatePhysics(deltaTime);
		
		if(destination != null){
			
			PhysVector pos = new PhysVector(x, y);
			float distance = PhysVector.distance(pos, destination);
			
			// test if trooper should stop, give it a variance of 3 units
			if(distance <20){
				
				dx = dy = dx2 = dy2 = 0;
			} else {
				
				
				float speed = MAX_SPEED;
				PhysVector newVelocity = PhysVector.subtract(destination, pos);
				newVelocity.becomeUnitVector();
				
				newVelocity.scale(speed);
				
				dx = newVelocity.x;
				dy = newVelocity.y;
			}

            if(x+dx < 0)
                x = 0;
            if(x+dx > screenWidth - 48)
                x = screenWidth - 48;
		}
	}

    /**
     * Returns whether or not the trooper is still alive during his fall.
     * @return true if the trooper is still alive.
     */
	public boolean isAlive(){
		
		return this.alive;
	}

    /**
     * Listener class for the android accelerometer input
     */
private SensorEventListener listener = new SensorEventListener() {

        /**
         * Listens for changes in the accelerometer's position and converts the output of the accelerometer to degrees.
         * @param event the indicated change in the accelerometer's state
         */
		@Override
		public void onSensorChanged(SensorEvent event) {
			if(game.useAccelerometer == false)
				return;
			
			double x = Math.abs(event.values[0]);
			double y = Math.abs(event.values[1]);
			double xraw = event.values[0];
			
			if(orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270) {
				double temp = x;
				x = y;
				y = temp;
				
				xraw = event.values[1];
			}
			
			double ang = (90 * x) / 9.8;			
			double percentage = ang / 90.0;
	
			xraw *= -1.0;
			
			double direction = xraw / x;
			
			dx = (float) (percentage * MAX_SPEED * direction * 2);
			
			if(x == 0)
				dx = 0;
		}

        /**
         * Listens for a forced change in accuracy of the accelerometer.
         * @param sensor The sensor that has changed
         * @param accuracy The new accuracy weight
         */
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// Do nothing...
		}
	};
	
}
