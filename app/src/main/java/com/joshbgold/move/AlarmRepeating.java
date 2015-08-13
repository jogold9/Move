package com.joshbgold.move;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                String text = repeatIntervalString;
                try {
                    Integer x = Integer.parseInt(text);
                    if (x != 0 && x < 30 || x > 300){
                        Toast.makeText(AlarmRepeating.this, "Please enter a number between 30 and 300, or enter zero for a one-time alarm.", Toast
                                .LENGTH_LONG).show();
                    }
                    else {
                        repeatIntervalInMinutes = Integer.valueOf(repeatIntervalString);
                        savePrefs("repeatInterval", repeatIntervalInMinutes);
                        finish();

                    }
                } catch (NumberFormatException exception) {
                    Toast.makeText(AlarmRepeating.this, "Please enter a number between 30 and 300, or enter zero for a one-time alarm.", Toast.LENGTH_LONG).show();
                }
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


