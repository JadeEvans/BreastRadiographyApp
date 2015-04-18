package jadepc.breastradiographyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Incorrect extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect);

        DatabaseHandler db = new DatabaseHandler(this);

        User jade = db.getUser(1);

        int lives = jade.getLives();
        lives--;
        jade.setLives(lives);

        db.updateUser(jade);

        Log.d("Play Click Score : ", Integer.toString(jade.getScore()));
        Log.d("Play Click Lives : ", Integer.toString(lives));

        Log.d("HighScore Before: ", Integer.toString(jade.getHighscore()));
        Log.d("Score Before:" , Integer.toString(jade.getScore()));
        if (jade.getScore() > jade.getHighscore()) {
            Log.d("HighScore: ", "Score is higher");
            jade.setHighscore(jade.getScore());
            jade.setNewHighScore(1);
            db.updateUser(jade);
        }else{
            Log.d("HighScore: ", "Score is less");
        }

        Log.d("HighScore After: ", Integer.toString(jade.getHighscore()));
        Log.d("Score After:" , Integer.toString(jade.getScore()));

        TextView textView = (TextView) findViewById(R.id.lives_update);


        if (jade.getLives() == 0){
            jade.setScore(0);
            db.updateUser(jade);
            textView.setText(lives);
        }
        else{

            textView.setText(" " + lives + " lives remaining");

        }

        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_incorrect, menu);
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

    /** Called when the user clicks the patient button */
    public  void patient_Click(View view) {
        Intent intent = new Intent(this, ActualGameScreen.class);
        startActivity(intent);
    }

}
