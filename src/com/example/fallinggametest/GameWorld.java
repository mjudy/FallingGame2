package com.example.fallinggametest;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gameobjects.GameObject;


public class GameWorld extends SurfaceView {
	
	private SurfaceHolder holder;
	
	private ArrayList<GameObject> gameObjects;
	
	public GameWorld(Context context, ArrayList<GameObject> gameObjects){
		
		super(context);
		
		holder = this.getHolder();
		
		this.gameObjects = gameObjects;
		
		this.setBackgroundColor(Color.WHITE);
		this.setFocusableInTouchMode(true);
		
	}

	public void onDraw(Canvas canvas){
		
		for(int i = 0; i < gameObjects.size(); i++){
			
			gameObjects.get(i).draw(canvas);
		}
	}	
}
