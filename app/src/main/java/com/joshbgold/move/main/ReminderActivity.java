package com.joshbgold.move.main;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.joshbgold.move.R;
import com.joshbgold.move.backend.AlarmReceiver;
import com.joshbgold.move.content.Moves;
import com.joshbgold.move.content.Quotations;


public class ReminderActivity extends Activity {

    AlarmManager alarmManager;
    private TextView movesAndQuotesTextView;
    private String movesString = "";
    private String quoteString = "";
    private String lastMovesInstruction ="";
    private static PendingIntent pendingIntent;
    private float volume;
    private float volumePercent;
    private final static int MAX_VOLUME = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        movesAndQuotesTextView = (TextView) findViewById(R.id.doThisThing);
        final Button backButton = (Button) findViewById(R.id.backButton);
        final Button cancelButton = (Button) findViewById(R.id.cancelAllButton);
        final Button exitButton = (Button) findViewById(R.id.exitButton);

        //MediaPlayer is used to play an mp3 file
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.drawable.om_mani_short);

        //vibrate the device for 1/2 second if the device is capable
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(vibrator.hasVibrator()) {
            vibrator.vibrate(500);
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaplayer) {
                mediaplayer.stop();
                mediaplayer.release();
            }
        });

        volume = loadPreferences("volume", volume); //gets the current volume
        volumePercent = volume * 100;
        //Toast.makeText(ReminderActivity.this, "Audio volume is set to: " + volumePercent + " %", Toast.LENGTH_LONG).show();

        mediaPlayer.setVolume(volume, volume); //sets right speaker volume and left speaker volume for mediaPlayer
        mediaPlayer.start();

        //Puts random move instruction into text view (i.e. breathe, stretch, go outside, etc).
        Moves moveObject = new Moves();
        movesString = moveObject.getMoves();
        lastMovesInstruction = movesString; //store for later use for back button
        movesAndQuotesTextView.setText(movesString);

        //cancel all alarms
        View.OnClickListener cancelAll = new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AlarmActivity.getAppContext();
                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

                if (mediaPlayer != null) try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                } catch (Exception e) {
                    Log.d("Alarm Activity", e.toString());
                }


                Toast.makeText(ReminderActivity.this, "Alarms Canceled", Toast.LENGTH_LONG).show();
            }
        };

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



//        backButton.setOnClickListener(goBack);
        cancelButton.setOnClickListener(cancelAll);
        exitButton.setOnClickListener(quitApp);


        //this code block allows for a delay before executing
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //fade in quote to the text edit to replace the previous instructions after 20 seconds
                Quotations quotations = new Quotations();
                quoteString = quotations.getQuote();
                movesAndQuotesTextView.setText(quoteString);
                backButton.setVisibility(View.VISIBLE);
            }
        }, 20000);  //equivalent to twenty seconds



    }

    public void onToggleClicked(View view){
        if (((ToggleButton) view).isChecked()) {
            movesAndQuotesTextView.setText(lastMovesInstruction);

        } else {
            movesAndQuotesTextView.setText(quoteString);
        }
    }

    //get prefs
    private float loadPreferences(String key, float value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        float data = sharedPreferences.getFloat(key, value);
        return data;
    }
}
