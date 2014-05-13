package com.example.fallinggametest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainMenu extends Activity implements OnTouchListener{

	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
        findViewById(R.id.menu).setOnTouchListener(this);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(v == findViewById(R.id.menu))
        {
            Intent intent = new Intent (this, Game.class);
            intent.putExtra("useAccel", true);
            startActivity(intent);
            return true;
        }
        return false;
	}
}
