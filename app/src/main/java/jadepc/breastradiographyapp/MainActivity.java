package jadepc.breastradiographyapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    //static variables for scoring
    private static final String TAG = MainActivity.class.getName();
    private static final String FILENAME = "scores.txt";
    private int score=0;
    private int lives =3;
    private String name= "Jade";
    //SQLiteHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseHandler db = new DatabaseHandler(this);

        //get score and amount of lives
        String score = db.getUserScore("Jade");


        //new user object to update the database
        User jade = new User(1, "Jade", 3, Integer.parseInt(score), 10);
        db.updateUser(jade);

        //insert the user
        Log.d("Insert: ", "Inserting...");
       // db.addUser(new User("Jade",3, 0, 10));
        //read db for user
        Log.d("Read:" , "Reading ALL Users...");
        List<User> users = db.getAllUsers();

        for (User us : users){
            String logString = "ID: "+us.getID()+" ,User Name: "+us.getUser_name() +
                    " ,Score: "+us.getScore() + " ,HighScore: "+ us.getHighscore();

            Log.d("User: ", logString);
        }




    }

    private void writeToFile(String name, int lives, int score) {
        String saveToFile = name+"\t"+Integer.toString(lives)+"\t"+Integer.toString(score);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(saveToFile);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /** Called when the user clicks the  Play button */
    public void play_Click(View view) {

        Intent intent = new Intent(this, PlayGame.class);
        startActivity(intent);
    }

    /** Called when the user clicks the How To Play button */
    public void howToPlay_Click(View view) {
        Intent intent = new Intent(this, howToPlay.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Achievements button */
    public void achievementClick(View view) {
        Intent intent = new Intent(this, Achievement.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Leaderboard button */
    public void leaderboardClick(View view) {
        Intent intent = new Intent(this, Leaderboard.class);
        startActivity(intent);
    }

}
