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


public class Answer extends ActionBarActivity {

    //score/name/lives.
    private int score,lives=0;
    private String name =null;
    private static final String TAG = MainActivity.class.getName();
    private static final String FILENAME = "scores.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        DatabaseHandler db = new DatabaseHandler(this);

        User jade = db.getUser(1);

        //get score and amount of lives
        String score = db.getUserScore("Jade");

        //int highScore = db.getAchievement(1,"highScore");
        //int lives = db.getUserLives("Jade");

        Log.d("Answer: HighScore: ", Integer.toString(jade.getHighscore()));
        if (Integer.parseInt(score) > jade.getHighscore()) {
            Log.d("HighScore: ", "Score is higher");
            jade.setHighscore(Integer.parseInt(score));
            jade.setNewHighScore(1);
            db.updateUser(jade);

        }else{
            Log.d("HighScore: ", "Score is less");
        }
        int tmpScore = Integer.parseInt(score);
        tmpScore ++;

        Log.d("Achievement 20-3: ", Integer.toString(jade.getTwentyRight()));

        jade.setScore(tmpScore);

        Log.d("Achievement 20-1: ", Integer.toString(jade.getTwentyRight()));
        getAchievements(jade);

        db.updateUser(jade);

        Log.d("Play Click Score : ", Integer.toString(tmpScore));
        Log.d("Play Click Lives : ", Integer.toString(lives));

        TextView textView = (TextView) findViewById(R.id.score_update);
        textView.setText(" " + Integer.toString(tmpScore));

        db.close();
    }

    private void getAchievements(User user) {

        DatabaseHandler db = new DatabaseHandler(this);
        Log.d("Achievement 20-1: ", Integer.toString(user.getTwentyRight()));
        if (user.getScore() == 10){
            user.setTenRight(1);
        }
        else if (user.getScore() == 20) {
            user.setTwentyRight(1);
        }
        else if (user.getScore() == 50){
            user.setFiftyRight(1);
        }
        else if (user.getScore() == 100){
            user.set100Right(1);
        }

        db.updateUser(user);

        db.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer, menu);
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
