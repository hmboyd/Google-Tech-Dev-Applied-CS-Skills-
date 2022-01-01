package com.example.scarnesdice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public int userTotalScore;
    public int userCurrentScore;
    public int computerTotalScore;
    public int computerCurrentScore;

    public ImageView imageView;
    public TextView scoreDisplayText;
    public Button rollBtn;
    public Button holdBtn;
//    public Button resetBtn;

    public int diceFaceList[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        scoreDisplayText = findViewById(R.id.score);

        rollBtn = findViewById(R.id.rollBtn);
        holdBtn = findViewById(R.id.holdBtn);
    }

    public void roll(View view) {
        int randomNumber = r.nextInt(6);
        imageView.setImageResource(diceFaceList[randomNumber]);

        if (randomNumber == 0) {
            userTotalScore += userCurrentScore;
            String scoreText = "Your score: " + userTotalScore + " computer score: " + computerTotalScore;
            userCurrentScore = 0;
            scoreDisplayText.setText(scoreText);

            rollBtn.setEnabled(false);
            holdBtn.setEnabled(false);
            computerTurn();
        } else {
            userCurrentScore += randomNumber + 1;
            String scoreText = "Your score: " + userTotalScore + " computer score: " + computerTotalScore + " your turn score: " + userCurrentScore;
            scoreDisplayText.setText(scoreText);
        }

    }

    public void holdBtn(View view) {
        userTotalScore += userCurrentScore;
        String scoreText = "Your score: " + userTotalScore + " computer score: " + computerTotalScore;
        userCurrentScore = 0;
        scoreDisplayText.setText(scoreText);

        rollBtn.setEnabled(false);
        holdBtn.setEnabled(false);
        computerTurn();
    }

    public void resetBtn(View view) {
        userTotalScore = 0;
        userCurrentScore = 0;
        computerTotalScore = 0;
        computerCurrentScore = 0;

        String scoreText = "Your score: " + userTotalScore + " computer score: " + computerTotalScore;
        scoreDisplayText.setText(scoreText);
    }

    public void computerTurn() {

        while (true) {
            int randomNumber = r.nextInt(6);
            imageView.setImageResource(diceFaceList[randomNumber]);

            if (randomNumber == 0) {
                computerTotalScore += computerCurrentScore;
                String scoreText = "Your score: " + userTotalScore + " computer score: " + computerTotalScore;
                computerCurrentScore = 0;
                scoreDisplayText.setText(scoreText);

                rollBtn.setEnabled(true);
                holdBtn.setEnabled(true);
                break;
            } else {
                computerCurrentScore += randomNumber + 1;

                if (computerCurrentScore >= 20) {
                    computerTotalScore += computerCurrentScore;
                    String scoreText = "Your score: " + userTotalScore + " computer score: " + computerTotalScore;
                    computerCurrentScore = 0;
                    scoreDisplayText.setText(scoreText);

                    rollBtn.setEnabled(true);
                    holdBtn.setEnabled(true);
                    break;
                } else {
                    String scoreText = "Your score: " + userTotalScore + " computer score: " + computerTotalScore + " computer turn score: " + computerCurrentScore;
                    scoreDisplayText.setText(scoreText);
                }
            }
        }
    }

}