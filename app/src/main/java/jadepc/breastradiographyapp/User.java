package jadepc.breastradiographyapp;

/**
 * Created by JadePC on 05/04/2015.
 */
public class User {

    //private variables
    int id;
    String user_name;
    int score;
    int highscore;
    int lives;

    //empty constructor
    public User() {
    }

    //Constructor
    public User(int id, String name, int lives, int score, int highscore) {
        this.id = id;
        this.user_name = name;
        this.score = score;
        this.highscore = highscore;
        this.lives=lives;

    }

    public User(String name, int lives, int score, int highscore) {
        this.user_name = name;
        this.score = score;
        this.highscore = highscore;
        this.lives=lives;
    }

    public int getID() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String name) {
        this.user_name = name;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score){
        this.score=score;
    }

    public int getHighscore(){
        return this.highscore;
    }

    public void setHighscore(int highscore){
        this.highscore=highscore;
    }

    public int getLives(){
        return this.lives;
    }

    public void setLives(int lives){
        this.lives = lives;
    }
}
