package com.example.scarnesdice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public int userTotalScore;
    public int userCurrentScore;
    public int computerTotalScore;
    public int computerCurrentScore;

    public ImageView imageView;
    public int diceFaceList[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }

    public void roll(View view) {
        int randomNumber = r.nextInt(6);
        imageView.setImageResource(diceFaceList[randomNumber]);

    }

}