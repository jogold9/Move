package com.joshbgold.move;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlarmRepeating extends Activity {

    private String repeatIntervalString = "";
    private int repeatIntervalInMinutes = 0;  //Number of minutes between 0 and 720 that user wants alarm to repeat at.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_repeating);

        final EditText repeatIntervalEditText = (EditText) findViewById(R.id.repeatIntervalInMinutes);
        final Button backButton = (Button) findViewById(R.id.backButton);

        View.OnClickListener goBack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatIntervalString = repeatIntervalEditText.getText() + "";

                //insert code here to check if input is actually an integer between 0 and 720!!!!!




                    repeatIntervalInMinutes = Integer.valueOf(repeatIntervalString);

                    savePrefs("repeatInterval", repeatIntervalInMinutes);

         /*    Toast.makeText(AlarmRepeating.this, "Repeat interval for the reminders is to : " + repeatIntervalInMinutes + " minutes.", Toast
                        .LENGTH_SHORT).show();*/

             /*   try {
                    Thread.sleep(3000);                 //Delay 3 seconds
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }*/

                finish();
            }
        };

        backButton.setOnClickListener(goBack);
    }

    //get prefs
    private int LoadPreferences(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int data = sharedPreferences.getInt(key, value);
        return data;
    }

    //save prefs
    public void savePrefs(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}


