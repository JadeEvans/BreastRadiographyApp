package jadepc.breastradiographyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JadePC on 05/04/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //ALL static variables.
    //DB version
    private static final int DATABASE_VERSION = 1;
    //DB name
    private static final String DATABASE_NAME = "UsersDB";
    //table name
    private static final String TABLE_USERS ="users";
    //collum names
    private static final String USERS_ID = "id";
    private static final String USERS_NAME = "userName";
    private static final String USERS_SCORE = "score";
    private static final String USERS_LIVES = "lives";
    private static final String USERS_HIGHSCORE = "highScore";

    public DatabaseHandler(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create the tables
    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + "(" + USERS_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + USERS_NAME + " VARCHAR(50)," + USERS_LIVES + " INTEGER,"
                + USERS_SCORE + " INTEGER," + USERS_HIGHSCORE
                + " INTEGER" + ")";

        db.execSQL(CREATE_USERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //drop old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        onCreate(db);
    }

    //Adding a new User
    public void addUser(User user){


        SQLiteDatabase db = this.getWritableDatabase();

        onCreate(db);

        ContentValues values = new ContentValues();
        values.put(USERS_NAME, user.getUser_name());
        values.put(USERS_LIVES, user.getLives());
        values.put(USERS_SCORE, user.getScore());
        values.put(USERS_HIGHSCORE, user.getHighscore());

        Log.d("username: ", user.getUser_name());
        Log.d("ID:" , Integer.toString(user.getID()));

        //insert row
        db.insert(TABLE_USERS, null, values);
        db.close();

    }

    //get ONE user
    public User getUser(int id){
        Log.d("Get User", "getting user...");
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { USERS_ID, USERS_NAME, USERS_LIVES, USERS_SCORE, USERS_HIGHSCORE },
                USERS_ID + "=?",
                new String [] {String.valueOf(id)}, null,null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

             User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),
                     Integer.parseInt(cursor.getString(4)));

        return user;

    }

    //get ALL users.
    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<User>();

        Log.d("READING", "Reading all contacts!");
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        Log.d("Read String: " , selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loop through and add to list
        if (cursor.moveToFirst()){
            do{
                User user = new User();
                if (cursor.getString(0) == null){
                    user.setId(0);
                }
                else {
                    user.setId(Integer.parseInt(cursor.getString(0)));
                }

                user.setUser_name(cursor.getString(1));
                user.setLives(Integer.parseInt(cursor.getString(2)));
                user.setScore(Integer.parseInt(cursor.getString(3)));
                user.setHighscore(Integer.parseInt(cursor.getString(4)));

                //add to list
                userList.add(user);
            }while (cursor.moveToNext());
        }
        return userList;
    }

    //update a user
    public int updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERS_NAME, user.getUser_name());
        values.put(USERS_LIVES, user.getLives());
        values.put(USERS_SCORE, user.getScore());
        values.put(USERS_HIGHSCORE, user.getHighscore());

        //update row
        return db.update(TABLE_USERS, values, USERS_ID + " =?",
                new String[] {String.valueOf(user.getID())});
    }

    //delete a user
    public void deleteUser (User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, USERS_ID + " =?",
                new String[] { String.valueOf(user.getID())});
        db.close();
    }

    /*
    //number of users in DB
    public int getUsersCount(){
        String query = "SELECT * FROM " + TABLE_USERS;

        //log
        Log.d("COUNT QUERY: ", query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        //return count
        return cursor.getCount();
    } */

    //get score
    public String getUserScore(String name){

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USERS_NAME + " = '" +name.trim()+ "'";

        Log.d("ScoreQUERY: ", query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        String score ="noScore";

        if(cursor.moveToFirst()){
                score = cursor.getString(3);
        }

        cursor.close();
        db.close();

        return score;

    }

    //get lives
    public int getUserLives(String name){

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USERS_NAME + " = '" +name.trim()+ "'";

        Log.d("ScoreQUERY: ", query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int lives = 3;

        if(cursor.moveToFirst()){
            lives = Integer.parseInt(cursor.getString(2));
        }

        cursor.close();
        db.close();

        return lives;

    }

    //get UserID
    public int getUserID(String name){

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USERS_NAME + " = '" +name.trim()+ "'";

        Log.d("ScoreQUERY: ", query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int id = 0;

        if(cursor.moveToFirst()){
            id = Integer.parseInt(cursor.getString(0));
        }

        cursor.close();
        db.close();

        return id;

    }

    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

    }
}
