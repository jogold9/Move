package com.joshbgold.move;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;


public class ReminderActivity extends Activity {

    private TextView movesAndQuotesTextView;
    private String movesString = "";
    private String quoteString = "";
    private String lastMovesInstruction ="";
    private static PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        movesAndQuotesTextView = (TextView) findViewById(R.id.doThisThing);
        //alarmTextView = (TextView) findViewById(R.id.alarmText);
        final Button backButton = (Button) findViewById(R.id.backButton);
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

        mediaPlayer.start();

        //Puts random move instruction into text view (i.e. breathe, stretch, go outside, etc).
        ThingsToDo thing = new ThingsToDo();
        movesString = thing.getThingToDo();
        lastMovesInstruction = movesString; //store for later use for back button
        movesAndQuotesTextView.setText(movesString);

/*        //hide quote and show moves instructions again
        View.OnClickListener goBack = new View.OnClickListener(){
            @Override
            public void onClick (View view){
                movesAndQuotesTextView.setText(lastMovesInstruction);
            }
        };*/


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
}
