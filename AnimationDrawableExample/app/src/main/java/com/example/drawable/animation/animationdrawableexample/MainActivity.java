package com.example.drawable.animation.animationdrawableexample;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imgview);
        imageView.setBackgroundResource(R.drawable.myanimation);

        animationDrawable = (AnimationDrawable) imageView.getBackground();
    }

    public void startAnimation(View view){
        animationDrawable.start();
    }

    public void stopAnimation(View view){
        animationDrawable.stop();
    }
}
