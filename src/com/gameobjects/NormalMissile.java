package com.gameobjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.fallinggametest.R;

public class NormalMissile extends Missile{
    private Bitmap sprite;

    public NormalMissile(float x, float y, float dx, float dy, Context context){

        this.x = x;
        this.y = y;

        this.dx = dx;
        this.dy = dy;

        this.sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.missile);
    }
}
