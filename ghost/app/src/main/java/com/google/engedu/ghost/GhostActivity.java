/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    private TextView fragment;
    private TextView status;
    SimpleDictionary sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        fragment = (TextView) findViewById(R.id.ghostText);
        status = (TextView) findViewById(R.id.gameStatus);
        try {
            InputStream inputStream = getAssets().open("words.txt");
            sd = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
//        userTurn = true;
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    public void onChallenge(View view){

    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        String fragmentWord = String.valueOf(fragment.getText());
        if (fragmentWord.length() >= 4 && sd.isWord(fragmentWord)) {
            label.setText("Computer Wins!");
            return;
        } else {
            String otherWord = sd.getAnyWordStartingWith(fragmentWord);
            if (otherWord == null) {
                // challenge
                label.setText("You can't bluff this computer.");
                return;
            } else {
                fragment.append(String.valueOf(otherWord.charAt(fragmentWord.length())));
            }
        }

        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        if (keyCode >= 29 && keyCode <= 54){
            fragment.append(String.valueOf(event.getDisplayLabel()).toLowerCase());
            String fragmantWord = String.valueOf(fragment.getText());
            System.out.println(fragmantWord);
            if (sd.isWord(fragmantWord)) {
                status.setText("Correct Word");
                return true;
            }
            status.setText(COMPUTER_TURN);
            userTurn = false;
            computerTurn();
        }
        System.out.println("inside onKeyUp");
        System.out.println(keyCode);
        System.out.println(event.getDisplayLabel());
        System.out.println(event);
        return super.onKeyUp(keyCode, event);
    }
}
