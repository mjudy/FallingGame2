package com.example.fallinggametest;

import java.util.concurrent.TimeUnit;

import android.os.Handler;

public class GameLoop implements Runnable {

	/**
	 * references to Game 
	 */
	private Game game;
	
	private Handler handler;
	
	private boolean running;
	
	public static final int FPS = 60;
	public static final int DELAY = 1000 / FPS;
	
	public GameLoop(Game game, GameWorld gameWorld) {
		this.game = game;
	}
	
	
	@Override
	public void run() {
		running = true;
				
		
		while (running) {
			try {								
				// do everything that needs to be done in the game
				game.updatePhysics(1f / FPS);
				game.doCollisionTesting();
				game.checkForStopCondition();
				game.spawnHandling();
				game.removeDeadGameObjects();
				game.incrementScore(1);
				game.incrementTime(1000 / FPS);
				
				// force a redraw on the screen
				game.redrawCanvas();
				
				TimeUnit.MILLISECONDS.sleep(DELAY);
			} catch (InterruptedException ie) {
				running = false;
				notifyAll();
			}
		}
	}
	
	public void stop() {
		running = false;
	}
	
	public boolean isRunning(){
		
		return running;
	}
	
}
