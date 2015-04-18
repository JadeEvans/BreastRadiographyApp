package jadepc.breastradiographyapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActualGameScreen extends ActionBarActivity {

    private GridView gv;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    public String biRads;

    private String[][] stringArrayImg;
    //Context ctx = this;

    // this should work out a random set of images to be shown!
    private String breastname = null;
    private int breastnum;



    //work out how to get random images to show!
    public String randImg() {
        /** part of this function and the LoadFile function are from
         * http://www.41post.com/3985/programming/android-loading-files-from-the-assets-and-raw-folders
         */
        String output= null;
        try
        {
            //Load the file from the raw folder - don't forget to OMIT the extension
            output = LoadFile("lookup",true);


        }
        catch (IOException e)
        {
            Log.i("exception1", output);
        }

        String[] rows = output.split("\n");
        Log.d("rows", rows[4]);
        //String[][] tmpImg;
        List<String> tmpImg = new ArrayList<String>();

        for (int i = 0; i < rows.length; i++) {
            tmpImg.add(rows[i]);
           // tmpImg[i] = rows[i].split("\t");
            //Log.d("tmpImg", tmpImg[i]);
        }
            Log.d("tmpImg", tmpImg.get(4));

        int max =rows.length;
        int min =1;
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + min) +1;

        Log.d("randomNumber", Integer.toString(randomNum));
            //int randomNum=1;

       String tmpNum = tmpImg.get(randomNum);
       Log.d("tmpNum", tmpNum);

        String[] tmpRtns = tmpNum.split("\t");

        Log.d("rtn0", tmpRtns[0]);
        Log.d("rtn1", tmpRtns[1]);

        //return new String[]{tmpRtns[0], tmpRtns[1]};
        //return tmpRtns[0];
        return tmpNum;
    }

    //load file from apps res/raw folder or Assets folder
    public String LoadFile(String fileName, boolean loadFromRawFolder) throws IOException
    {
        //Create a InputStream to read the file into
        InputStream iS;
        Log.d("inputstream", "yay");
        Resources res = getResources();

        if (loadFromRawFolder)
        {
            Log.d("ifStatement", "if statement");
            //get the resource id from the file name
            int rID = res.getIdentifier("jadepc.breastradiographyapp:raw/"+fileName, null, null);
            //get the file as a stream
            iS = res.openRawResource(rID);
        }
        else
        {
            //get the file as a stream
            iS = res.getAssets().open(fileName);
            Log.d("else", "cannot find");
        }

        //create a buffer that has the same size as the InputStream
        byte[] buffer = new byte[iS.available()];
        //read the text file as a stream, into the buffer
        iS.read(buffer);
        //create a output stream to write the buffer into
        ByteArrayOutputStream oS = new ByteArrayOutputStream();
        //write this buffer to the output stream
        oS.write(buffer);
        //Close the Input and Output streams
        oS.close();
        iS.close();

        //return the output stream as a String
        Log.d("BufferString", oS.toString());

        return oS.toString();
    }


    // Create Array thumbs resource id's:
    private int thumb[]  = { R.drawable.breast_cl0, R.drawable.breast_cr0,
            R.drawable.breast_ml0, R.drawable.breast_mr0  };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_game_screen);

        breastname = randImg();
        String[] randBreast = breastname.split("\t");
        biRads = randBreast[1];
        biRads = biRads.replaceAll("\\n|\\r", "");
        Log.d("breastname", breastname);
        Log.d("randBreast1", randBreast[0]);
        Log.d("randBreast2", randBreast[1]);


        //thumbnail array
        if (randBreast[0] != null){

            //end of each image
            String cl = "p"+randBreast[0]+"cl";
            String cr = "p"+randBreast[0]+"cr";
            String ml = "p"+randBreast[0]+"ml";
            String mr = "p"+randBreast[0]+"mr";

            //change each into an int
            int resID_cl = getResources().getIdentifier(cl , "drawable", getPackageName());
            int resID_cr = getResources().getIdentifier(cr , "drawable", getPackageName());
            int resID_ml = getResources().getIdentifier(ml , "drawable", getPackageName());
            int resID_mr = getResources().getIdentifier(mr , "drawable", getPackageName());


           // put into array
            thumb[0] = resID_cr;
            thumb[1] = resID_cl;
            thumb[2] = resID_mr;
            thumb[3] = resID_ml;
        }
       else{
            thumb[0] =  R.drawable.breast_cl0;
            thumb[1] = R.drawable.breast_cr0;
            thumb[2] = R.drawable.breast_ml0;
            thumb[3] = R.drawable.breast_mr0;
        }
        // Initialize the variables:
        gv = (GridView) findViewById(R.id.gridView);

        // Set an Adapter to the ListView
        gv.setAdapter(new ImageAdapter(this));

        // Set on item click listener to the ListView
        gv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos,
                                    long id) {

                // Display the zoomed in image in full screen
                zoomImageFromThumb(v, thumb[pos]);

            }
        });

        // Set the Animation time form the android defaults
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

    }

    // Create an Adapter Class extending the BaseAdapter

    /** taken from the Android Grid View Tutorial on
     * http://techiedreams.com/android-custom-grid-view-with-zoom-in-animation-effect/
     */
    class ImageAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public ImageAdapter(ActualGameScreen activity) {
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // Set the count value to the total number of items in the Array
            return thumb.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Inflate the item layout and set the views
            View listItem = convertView;
            int pos = position;
            if (listItem == null) {
                listItem = layoutInflater.inflate(R.layout.grid_item, null);
            }

            // Initialize the views in the layout
            ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);

            // Set the views in the layout
            iv.setBackgroundResource(thumb[pos]);

            return listItem;
        }

    }
    @TargetApi(14)
    private void zoomImageFromThumb(final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it immediately and
        // proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the
        // final bounds are the global visible rectangle of the container view.
        // Also
        // set the container view's offset as the origin for the bounds, since
        // that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds,
                globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the
        // "center crop" technique. This prevents undesirable stretching during
        // the animation.
        // Also calculate the start scaling factor (the end scaling factor is
        // always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
                .width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the
        // top-left corner of
        // the zoomed-in view (the default is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set.play(
                ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y,
                        startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the
        // original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their
                // original values.
                AnimatorSet set = new AnimatorSet();
                set.play(
                        ObjectAnimator.ofFloat(expandedImageView, View.X,
                                startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                                startBounds.top))
                        .with(ObjectAnimator.ofFloat(expandedImageView,
                                View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator.ofFloat(expandedImageView,
                                View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    /** Called when the user clicks the correct button */
    public void answer_Click(View view) {

        int intID = view.getId();
        Button button = (Button) findViewById(intID);
        String message = button.getText().toString();

        Log.d("messageString", message);

        Log.d("biRads", biRads);
        Intent intent;

        int btnNum = Integer.parseInt(message);
        int biradNum = Integer.parseInt(biRads);

        Log.d("testBtnNum", Integer.toString(btnNum));
        Log.d("testbiradNum", Integer.toString(biradNum));

        String tmpNum = "4";
        if (message.contentEquals(biRads)){
            intent = new Intent(this,Answer.class);
            startActivity(intent);
        }
        else{
            intent = new Intent(this, Incorrect.class);
            startActivity(intent);
        }
        /**
        intent = new Intent(this,Answer.class);
        startActivity(intent);
        **/
    }


}