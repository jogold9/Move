package com.joshbgold.move;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class ReminderActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

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
    }

}
