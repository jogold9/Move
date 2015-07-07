package com.joshbgold.move;

import android.app.Activity;
import android.media.MediaPlayer;

/**
 * Created by JoshG on 7/6/2015.
 */

public class PlayAudio extends Activity {

    public MediaPlayer mediaPlayer;

    //constructor
    public PlayAudio(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public PlayAudio() {
    }

    public void play(){
        //This code block handles the audio for this activity
        mediaPlayer = MediaPlayer.create(this, R.drawable.om_mani_short);
        mediaPlayer.start();
    }

    public void stop(){
        mediaPlayer.stop();
    }
}
