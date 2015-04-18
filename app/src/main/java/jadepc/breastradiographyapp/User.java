package jadepc.breastradiographyapp;

/**
 * Created by JadePC on 05/04/2015.
 */
public class User {

    //private variables
    int id;
    String user_name;
    int score, highscore, lives, tenRight, twentyRight, fiftyRight, oneHundredRight, newHighScore;


    //empty constructor
    public User() {
    }

    //Constructor
    public User(int id, String name, int lives, int score, int highscore, int tenRight, int twentyRight,
                int fiftyRight, int oneHundredRight, int newHighScore){
        this.id = id;
        this.user_name = name;
        this.score = score;
        this.highscore = highscore;
        this.lives=lives;
        this.tenRight=tenRight;
        this.twentyRight=twentyRight;
        this.fiftyRight=fiftyRight;
        this.oneHundredRight=oneHundredRight;
        this.newHighScore=newHighScore;

    }

    public User(String name, int lives, int score, int highscore, int tenRight, int twentyRight,
                int fiftyRight, int oneHundredRight, int newHighScore) {
        this.user_name = name;
        this.score = score;
        this.highscore = highscore;
        this.lives=lives;
        this.tenRight=tenRight;
        this.twentyRight=twentyRight;
        this.fiftyRight=fiftyRight;
        this.oneHundredRight=oneHundredRight;
        this.newHighScore=newHighScore;
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

    public int getTenRight(){
        return this.tenRight;
    }

    public void setTenRight(int tenRight){
        this.tenRight = tenRight;
    }

    public void setTwentyRight(int twentyRight){
        this.twentyRight = twentyRight;
    }
    public int getTwentyRight(){
        return this.twentyRight;
    }

    public void setFiftyRight(int fiftyRight){
        this.fiftyRight=fiftyRight;
    }

    public int getFiftyRight(){
        return this.fiftyRight;
    }

    public void set100Right(int oneHundredRight){
        this.oneHundredRight=oneHundredRight;
    }

    public int get100Right(){
        return this.oneHundredRight;
    }

    public void setNewHighScore(int newHighScore){
        this.newHighScore=newHighScore;
    }

    public int getNewHighScore(){
        return this.newHighScore;
    }
}
