package com.joshbgold.move;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ReminderActivity extends Activity {

    private TextView DoTheThing;
    private String doThis = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        DoTheThing = (TextView) findViewById(R.id.doThisThing);
        //alarmTextView = (TextView) findViewById(R.id.alarmText);
        final Button exitButton = (Button) findViewById(R.id.exitButton);

        //MediaPlayer is used to play an mp3 file
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.drawable.om_mani_short);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaplayer) {
                mediaplayer.stop();
                mediaplayer.release();
            }
        });

        mediaPlayer.start();

        //Puts random move instruction into text view (i.e. breathe, stretch, go outside, etc).
        ThingsToDo thing = new ThingsToDo();
        doThis = thing.getThingToDo();
        DoTheThing.setText(doThis);

        View.OnClickListener quitApp = new View.OnClickListener() {  //this block stops music when exiting
            @Override
            public void onClick(View view) {

                if (mediaPlayer != null) try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                } catch (Exception e) {
                    Log.d("Alarm Activity", e.toString());
                }

                finish();
            }
        };

        exitButton.setOnClickListener(quitApp);


        //this code block does 5 second delay before executing
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //fade in quote to the text edit to replace the previous instructions after 10 seconds
                Quotations quotations = new Quotations();
                doThis = quotations.getQuote();
                DoTheThing.setText(doThis);
            }
        }, 10000);  //equivalent to ten seconds
    }

}
