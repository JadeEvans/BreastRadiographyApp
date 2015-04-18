package jadepc.breastradiographyapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class Achievement extends ActionBarActivity {

    ImageView tenRight, twentyRight, fiftyRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        DatabaseHandler db = new DatabaseHandler(this);

        User user = db.getUser(1);

        //check user exists.
        Log.d("User Achievement: ", user.getUser_name());
        Log.d("User Achievement 10: ", Integer.toString(user.getTenRight()));

        //only greyscale the image if the user doesn't have the achievement
        if (user.getTenRight() != 1) {
            tenRight = (ImageView) findViewById(R.id.tenRight);
            tenRight.setImageBitmap(grayScaleImage(BitmapFactory.decodeResource(getResources(), R.drawable.achievement_10)));

        }

        if (user.getFiftyRight() != 1){
            fiftyRight = (ImageView) findViewById(R.id.fiftyRight);
            fiftyRight.setImageBitmap(grayScaleImage(BitmapFactory.decodeResource(getResources(), R.drawable.achievement_10)));
        }

        if (user.getTwentyRight() != 1){
            twentyRight = (ImageView) findViewById(R.id.twentyRight);
            twentyRight.setImageBitmap(grayScaleImage(BitmapFactory.decodeResource(getResources(), R.drawable.achievement_10)));
        }

        if (user.get100Right() != 1){
            twentyRight = (ImageView) findViewById(R.id.oneHundredRight);
            twentyRight.setImageBitmap(grayScaleImage(BitmapFactory.decodeResource(getResources(), R.drawable.achievement_10)));
        }

        if (user.getNewHighScore() != 1){
            twentyRight = (ImageView) findViewById(R.id.newHighScore);
            twentyRight.setImageBitmap(grayScaleImage(BitmapFactory.decodeResource(getResources(), R.drawable.achievement_10)));
        }

        db.close();


    }

    /**method used to grey-scale the images when the user has not gained an achievement
    code taken from http://shaikhhamadali.blogspot.co.uk/2013/06/gray-scale-image-in-imageview-android_29.html
     **/
    public static Bitmap grayScaleImage(Bitmap src) {
        // constant factors
        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;

        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = src.getWidth();
        int height = src.getHeight();

        // scan through every single pixel
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = src.getPixel(x, y);
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        return bmOut;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_achievement, menu);
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
}
