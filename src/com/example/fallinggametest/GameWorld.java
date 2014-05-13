package com.example.fallinggametest;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.transition.Visibility;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;

import com.collision.PhysVector;
import com.gameobjects.BouncyBall;
import com.gameobjects.GameObject;
import com.gameobjects.Trooper;


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
