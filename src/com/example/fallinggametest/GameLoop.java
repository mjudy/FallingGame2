package com.example.fallinggametest;

import java.util.concurrent.TimeUnit;

public class GameLoop implements Runnable {

	/**
	 * references to Game 
	 */
	private Game game;
	
	private volatile boolean running;
	
	public GameLoop(Game game, GameWorld gameWorld){
		
		this.game = game;
	}
	
	
	@Override
	public void run() {
		running = true;
		
		while (running) {
			try {
				
				// 1000 / 33 = 30fps
				TimeUnit.MILLISECONDS.sleep(33);
				
				// do everything that needs to be done in the game
				game.updatePhysics(.033f);
				game.doCollisionTesting();
				game.checkForStopCondition();
				game.spawnHandling();
				game.removeDeadGameObjects();
				game.incrementScore(1);
				game.incrementTime(33);
				
				// force a redraw on the screen
				game.redrawCanvas();

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
