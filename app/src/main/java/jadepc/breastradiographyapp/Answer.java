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

        //get score and amount of lives
        String score = db.getUserScore("Jade");
        int lives = db.getUserLives("Jade");

        int tmpScore = Integer.parseInt(score);
        tmpScore ++;

        //new user object to update the database
        User jade = new User(1, "Jade", lives, tmpScore, 10);
        db.updateUser(jade);

        Log.d("Play Click Score : ", Integer.toString(tmpScore));
        Log.d("Play Click Lives : ", Integer.toString(lives));

        if (tmpScore > 0){
            new AlertDialog.Builder(this)
                    .setTitle("Correct!")
                    .setMessage("You have gained a score point")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Answer.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

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
