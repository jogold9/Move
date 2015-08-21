package com.joshbgold.move.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joshbgold.move.R;

public class AlarmRepeating extends Activity {

    private String repeatIntervalAsString = "";
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
                repeatIntervalAsString = repeatIntervalEditText.getText() + "";

                //insert code here to check if input is actually an integer between 0 and 720!!!!!

                try {

                    if (repeatIntervalAsString == ""){
                        repeatIntervalInMinutes = 0;
                        savePrefs("repeatInterval", repeatIntervalInMinutes);
                        finish();
                    }
                    else {
                        Integer repeatIntervalAsInt = Integer.parseInt(repeatIntervalAsString);
                            if (repeatIntervalAsInt != 0 && repeatIntervalAsInt < 2 || repeatIntervalAsInt > 1440){
                                Toast.makeText(AlarmRepeating.this, "Please enter a number between 2 and 1440, or leave blank for a one-time alarm.",
                                        Toast.LENGTH_LONG).show();
                            }
                            else {
                                 repeatIntervalInMinutes = Integer.valueOf(repeatIntervalAsString);
                                 savePrefs("repeatInterval", repeatIntervalInMinutes);
                                 finish();
                            }
                    }

                } catch (NumberFormatException exception) {
                    Toast.makeText(AlarmRepeating.this, "Please enter a number between 2 and 1440, or leave blank for a one-time alarm.", Toast
                            .LENGTH_LONG).show();
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


