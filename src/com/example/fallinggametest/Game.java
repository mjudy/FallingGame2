package com.example.fallinggametest;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.gameobjects.GameObject;
import com.gameobjects.HomingMissile;
import com.gameobjects.ScoreLabel;
import com.gameobjects.SkyBackground;
import com.gameobjects.Trooper;

/**
 * 
 * This is the class which handles all the main functions of the game. 
 * 
 * @author Evan Hanger, Andrew Huber, Mark Judy
 *
 */
public class Game extends Activity implements OnTouchListener {

	/** The view in which everything is visible */
	private GameWorld gameWorld;
	
	
	/** The class which contains the necessary looping for calling 
	 * the Game's methods. 
	 */
	private GameLoop gameLoop;
	
	private SpawnHandler spawnHandler;
	
	/**
	 * Determine whether to control Trooper using touch screen or accelerometer
	 */
	public boolean useAccelerometer;
	
	public int screenWidth, screenHeight;
	
	private ArrayList<GameObject> gameObjects;
	
	private Trooper trooper;
	
	private ScoreLabel scoreLabel;
	
	private int currentScore, timeInMillis, timeSinceLastSpawn;
	
	public final static String USE_ACCELEROMETER_KEY = "USE_ACCELEROMETER";
	public final static String SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY";
	public final static String HIGH_SCORE_KEY = "HIGH_SCORE_KEY";
	
	private AlertDialog dialog;
	private Thread thread;
	private boolean dialogOpen = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		// this will get the screen dimensions 
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenHeight = displaymetrics.heightPixels;
		screenWidth = displaymetrics.widthPixels;
		
		// both default to false but will be changed in the code below
		useAccelerometer = true;
		
		// determine what controls to use for Trooper, 
		// this info is passed from MainMenu
		
		
		SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_KEY, 
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		
		if(!preferences.contains(USE_ACCELEROMETER_KEY)) {
			editor.putBoolean(USE_ACCELEROMETER_KEY, true);
			editor.commit();
		}
		
		useAccelerometer = preferences.getBoolean(USE_ACCELEROMETER_KEY, true);
		
		
		// start these variables at 0
		this.currentScore = 0;
		this.timeInMillis = 0;
		this.timeSinceLastSpawn = 0;
		
		// create ArrayList of GameObjects
		this.gameObjects = new ArrayList<GameObject>();
		
		// create the GameWorld and make it the main view
		gameWorld = new GameWorld(this, gameObjects);
		setContentView(gameWorld);
		
		/* Add the initial game objects which are always going to be present:
		 * -Trooper
		 * -SkyBackground
		 * -ScoreLabel
		 */
		addInitialGameObjects();
		
		spawnHandler = new SpawnHandler(this);
		
		// give gameWorld a touch listener
		gameWorld.setOnTouchListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.falling_menu, menu);
		return true;
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		menu.getItem(0).setVisible(false);		
		closeContextMenu();
		closeOptionsMenu();
		gameLoop.stop();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Paused");
		
		OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface d, int which) {
				
				if(which == 0) {
					useAccelerometer = !useAccelerometer;
					
					SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.remove(USE_ACCELEROMETER_KEY);
					editor.putBoolean(USE_ACCELEROMETER_KEY, useAccelerometer);
					editor.commit();
				}
				
				
				thread = new Thread(gameLoop);
				thread.start();
				dialog.cancel();
				dialog.dismiss();
			}
		};
		
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				thread = new Thread(gameLoop);
				thread.start();
				dialog.cancel();
				dialog.dismiss();				
			}
		});
		
		if(useAccelerometer)
			builder.setItems(R.array.dialog_options_accel_enabled, listener);
		else
			builder.setItems(R.array.dialog_options_accel_disabled, listener);
		
		dialog = builder.create();
		dialog.show();
		return true;
	}
	
	@Override
	public void onBackPressed() {
		// Do nothing...
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		// create and start the GameLoop Runnable class
		startGameLoop();
	}
	
	
	/**
	 * Handles TouchEvents that occur on screen
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(gameLoop.isRunning() == false) {
			thread = new Thread(gameLoop);
			thread.start();
		}
		
		if(useAccelerometer == true)
			return true;
		
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			float xPos = event.getX();
			float mid = screenWidth / 2;
			
			if(xPos < mid)
				trooper.dx = trooper.MAX_SPEED * -1;
			else
				trooper.dx = trooper.MAX_SPEED;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
			trooper.dx = 0;
		else
			return true; 
		
		return true;
	}
	
	
	/**
	 * Called when the game goes into background
	 */
	@Override
	public void onPause(){
		super.onPause();
		gameLoop.stop();
	}
	
	public void redrawCanvas(){
		
		this.gameWorld.postInvalidate();
	}
	
	public int getCurrentScore(){
		
		return this.currentScore;
	}
	
	public int getTimeInMillis(){
		
		return timeInMillis;
	}
	
	/**
	 * Increases the score, and changes the ScoreLabel to display this new score
	 * @param amount
	 */
	public void incrementScore(int amount){
		
		currentScore += amount;
		
		if(scoreLabel != null){
			
			scoreLabel.setScore(currentScore);
		}
		
	}
	
	/**
	 * Increments both variables keeping track of a time
	 * @param amount
	 */
	public void incrementTime(int amount){
		
		timeInMillis += amount;
		timeSinceLastSpawn += amount;
		
	}
	
	/**
	 * Instantiates the GameLoop object and starts its thread
	 */
	public void startGameLoop(){
		
		this.gameLoop = new GameLoop(this, this.gameWorld);
		thread = new Thread(gameLoop);
		thread.start();		
	}
	
	/**
	 * Adds a GameObject to the ArrayList containing GameObjects
	 * @param object to be added
	 */
	public void addGameObject(GameObject obj){
		
		this.gameObjects.add(obj);
	}
	
	/**
	 * This is where you add the GameObjects that are initially present in the game
	 * 
	 * DON'T spawn enemies here, that should happen in spawnHandling()
	 */
	public void addInitialGameObjects(){
		
		SkyBackground background = new SkyBackground(BitmapFactory.decodeResource(getResources(), 
				R.drawable.background), screenWidth, screenHeight);
		addGameObject(background);
		
		Trooper trooper = new Trooper(350, 300, this, screenWidth);
		this.trooper = trooper; // store a reference to trooper
		addGameObject(trooper);
		
		ScoreLabel label = new ScoreLabel(15, screenHeight / 20, screenHeight);
		this.scoreLabel = label; // store a reference to scoreLabel
		addGameObject(label);
	}

	
	
	/**
	 * Tests collision on every single GameObject present in the game
	 */
	public void doCollisionTesting(){
		
		for(int i = 0; i < gameObjects.size(); i++){
			
			gameObjects.get(i).checkForCollisions(gameObjects);
		}
	}
	
	/**
	 * Updates the physics of the game by calling every 
	 * single GameObject's updatePhysics() method
	 * @param deltaTime
	 */
	public void updatePhysics(float deltaTime){
		
		for(int i = 0; i < gameObjects.size(); i++){
			
			gameObjects.get(i).updatePhysics(deltaTime);
		}
		
	}
	
	public void lockMissileOnTrooper(HomingMissile missile){
		
		missile.setTarget(this.trooper);
	}

	/** 
	 * TODO
	 * (NOT YET COMPLETE)
	 * This is where enemies in the game are spawned
	 *
	 */
	public void spawnHandling(){
		
		timeSinceLastSpawn = spawnHandler.spawnGameObject(timeSinceLastSpawn);

	}
	
	
	/**
	 * Removes GameObjects from that game that have gone off the screen
	 */
	public void removeDeadGameObjects(){
		
		// start iterating at i=3, to avoid trooper, skyBackground, scoreLabel
		for(int i = 3; i < gameObjects.size(); i++){
			
			GameObject temp = gameObjects.get(i);
			
			// if an object goes off the screen, remove it from ArrayList
			if(temp.x < 0 
					|| temp.x > screenWidth
					|| temp.y < 0 
					|| temp.y > screenHeight){
				
				gameObjects.remove(temp);
			}
		}
	}
	
	
	public void checkForStopCondition(){
		
		if(trooper.isAlive() == false) {
			
			this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					gameLoop.stop();
					
					int highScore = getSharedPreferences(SHARED_PREFERENCES_KEY, 
							Context.MODE_PRIVATE).getInt(HIGH_SCORE_KEY, 0);
					
					AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
					View view;
					
					if(currentScore > highScore) {
						view = getLayoutInflater().inflate(R.layout.high_score, null);
						builder.setTitle("Congratulations!");
						
						DecimalFormat formatter = new DecimalFormat("#,###");
						TextView textView = (TextView) view.findViewById(R.id.scoreView);
						textView.setText("Your score was " + formatter.format(currentScore));
						
						SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_KEY, 
								Context.MODE_PRIVATE);
						Editor editor = preferences.edit();
						
						if(preferences.contains(HIGH_SCORE_KEY))
							editor.remove(HIGH_SCORE_KEY);
						
						editor.putInt(HIGH_SCORE_KEY, currentScore);
						editor.commit();				
					}
					else {
						view = getLayoutInflater().inflate(R.layout.game_over, null);
						builder.setTitle("You crashed!");
					}
					
					
					builder.setView(view);
					builder.setPositiveButton("Play Again?", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialogOpen = false;
							Game.this.finish();
							Intent intent = new Intent(Game.this, Game.class);
							startActivity(intent);
						}
					});
					
					builder.setNegativeButton("Return to Main Menu", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialogOpen = false;
							Game.this.finish();
							Intent intent = new Intent(Game.this, MainMenu.class);
							startActivity(intent);
						}
					});
					
					builder.setCancelable(false); // Cannot tap outside the dialog to cancel it
					
					if(dialogOpen == false) {
						dialogOpen = true;
						builder.show();
					}
				}
			});
			
		}
	}
}