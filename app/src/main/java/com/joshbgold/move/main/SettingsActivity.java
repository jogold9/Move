package com.joshbgold.move.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.joshbgold.move.R;


public class SettingsActivity extends Activity {

    private SeekBar volumeControl = null;
    private float volume = (float) 0.50;
    private String repeatIntervalAsString = "";
    private String customReminderString = "";
    private int repeatIntervalInHours = 0;  //Number of hours that user wants alarm to repeat at (optional)
    private boolean blockWeekendAlarms = false;
    private boolean blockNonWorkHoursAlarms = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText CustomReminderEditText = (EditText) findViewById(R.id.AddYourOwn);
        final EditText repeatIntervalEditText = (EditText) findViewById(R.id.repeatIntervalInMinutes);
        final Button backButton = (Button) findViewById(R.id.backButton);
        final CheckBox blockWeekendsCheckBox = (CheckBox)findViewById(R.id.blockWeekends);
        final CheckBox blockNonWorkHoursCheckBox = (CheckBox)findViewById(R.id.blockNonWorkDayHours);

        customReminderString = loadPrefs("customReminder", customReminderString);
        if (customReminderString != null && !customReminderString.isEmpty()) {
            CustomReminderEditText.setText(customReminderString);
        }

        volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);

            volume = loadPrefs("volumeKey", volume);
            volumeControl.setProgress((int)(volume*100));

            repeatIntervalInHours = loadPrefs("repeatIntervalKey", repeatIntervalInHours);
            repeatIntervalEditText.setText(repeatIntervalInHours + "");

            blockWeekendAlarms = loadPrefs("noWeekendsKey", blockWeekendAlarms);
            if (blockWeekendAlarms) {
                blockWeekendsCheckBox.setChecked(true);
            } else {
                blockWeekendsCheckBox.setChecked(false);
            }

            blockNonWorkHoursAlarms = loadPrefs("workHoursOnlyKey", blockNonWorkHoursAlarms);
            if (blockNonWorkHoursAlarms) {
                blockNonWorkHoursCheckBox.setChecked(true);
            }
            else {
                blockNonWorkHoursCheckBox.setChecked(false);
            }

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

                volume = (float) (((double) (progressChanged)) / 100);  //allows division w/ decimal results instead of integer results
                savePrefs("volumeKey", volume);
                Toast.makeText(SettingsActivity.this, "Audio volume is set to: " + progressChanged + " %", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener goBack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //saves user preference for whether to have alarm on weekends
                if (blockWeekendsCheckBox.isChecked()){
                    savePrefs("noWeekendsKey", true);
                    blockWeekendAlarms = loadPrefs("noWeekendsKey", blockWeekendAlarms);
                }
                else{
                    savePrefs("noWeekendsKey", false);
                    blockWeekendAlarms = loadPrefs("noWeekendsKey", blockWeekendAlarms);
                }

                //saves user preference for alarm during work hours / non-work hours
                if (blockNonWorkHoursCheckBox.isChecked()){
                    savePrefs("workHoursOnlyKey", true);
                    blockNonWorkHoursAlarms = loadPrefs("workHoursOnlyKey", blockNonWorkHoursAlarms);
                }
                else {
                    savePrefs("workHoursOnlyKey", false);
                    blockNonWorkHoursAlarms = loadPrefs("workHoursOnlyKey", blockNonWorkHoursAlarms);
                }

                savePrefs("volumeKey", volume);

                repeatIntervalAsString = repeatIntervalEditText.getText() + "";

                customReminderString = CustomReminderEditText.getText() + "";
                repeatIntervalAsString = repeatIntervalEditText.getText() + "";

                savePrefs("customReminder", customReminderString);  //having this after .getText is critical, fails otherwise


                try {

                    if (repeatIntervalAsString.equals("")){
                        repeatIntervalInHours = 0;
                        savePrefs("repeatIntervalKey", repeatIntervalInHours);
                        finish();
                    }
                    else {
                        Integer repeatIntervalAsInt = Integer.parseInt(repeatIntervalAsString);
                        if (repeatIntervalAsInt != 0 && repeatIntervalAsInt < 1 || repeatIntervalAsInt > 24){
                            Toast.makeText(SettingsActivity.this, "Please enter a number between 1 and 24, or leave blank for a one-time alarm.",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            repeatIntervalInHours = Integer.valueOf(repeatIntervalAsString);
                            savePrefs("repeatIntervalKey", repeatIntervalInHours);
                            finish();
                        }
                    }

                } catch (NumberFormatException exception) {
                    Toast.makeText(SettingsActivity.this, "Please enter a number between 2 and 1440, or leave blank for a one-time alarm.", Toast
                            .LENGTH_LONG).show();
                }
            }
        };
        backButton.setOnClickListener(goBack);

    }

    //save prefs
    public void savePrefs(String key, float value){
        SharedPreferences sharedPreferences = getSharedPreferences("MoveAppPrefs", Context.MODE_PRIVATE);
        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void savePrefs(String key, int value){
        SharedPreferences sharedPreferences = getSharedPreferences("MoveAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    //save prefs
    public void savePrefs(String key, boolean value){
        SharedPreferences sharedPreferences = getSharedPreferences("MoveAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //save prefs
    public void savePrefs(String key, String value){
        SharedPreferences sharedPreferences = getSharedPreferences("MoveAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //get prefs
    private float loadPrefs(String key,float value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MoveAppPrefs", Context.MODE_PRIVATE);
         return sharedPreferences.getFloat(key, value);
    }

    //get prefs
    private int loadPrefs(String key,int value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MoveAppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, value);
    }

    //get prefs
    private boolean loadPrefs(String key,boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MoveAppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, value);
    }

    //get prefs
    private String loadPrefs(String key,String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("MoveAppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, value);
    }
}
