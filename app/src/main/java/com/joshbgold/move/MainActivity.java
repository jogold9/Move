package com.joshbgold.move;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;


public class MainActivity extends Activity {

    public int alarmInterval = 3600000; //equal to 1 hour in milliseconds
    private Switch ReminderSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MediaPlayer object is used to play a mp3 file
        final MediaPlayer player;
        final Button exitButton = (Button) findViewById(R.id.exitButton);

        ReminderSwitch = (Switch) findViewById(R.id.On_Off_Switch);

        //This code block handles turning on and off the reminders
        ReminderSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    //Reminders will be on
                }else{
                    //Reminders will be off
                }
            }
        });

        //This code block handles the audio for this activity (but not for reminders)
        player = MediaPlayer.create(this, R.drawable.om_mani_padme_om);
        player.start();
        View.OnClickListener quitApp = new View.OnClickListener() {  //this block stops music when exiting
            @Override
            public void onClick(View view) {
                player.stop();
                finish();
            }
        };

        exitButton.setOnClickListener(quitApp);
    }
}
