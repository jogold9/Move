package com.joshbgold.move;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


public class ChooseDays extends Activity {

    CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    View.OnClickListener checkBoxListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_days);

        monday = (CheckBox)findViewById(R.id.mondayCheckBox);
        tuesday = (CheckBox)findViewById(R.id.tuesdayCheckBox);
        wednesday = (CheckBox)findViewById(R.id.wednesdayCheckBox);
        thursday = (CheckBox)findViewById(R.id.thursdayCheckbox);
        friday = (CheckBox)findViewById(R.id.fridayCheckbox);
        saturday = (CheckBox)findViewById(R.id.saturdayCheckbox);
        sunday = (CheckBox)findViewById(R.id.sundayCheckBox);
        checkBoxListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monday.isChecked()){
                    savePrefs("Monday", true);
                }
                if (!monday.isChecked()){
                    savePrefs("Monday", false);
                }
                if (tuesday.isChecked()) {
                    savePrefs("Tuesday", true);
                }
                if (!tuesday.isChecked()) {
                    savePrefs("Tuesday", false);
                }
                if (wednesday.isChecked()){
                    savePrefs("Wednesday", true);
                }
                if (!wednesday.isChecked()){
                    savePrefs("Wednesday", false);
                }
                if (thursday.isChecked()){
                    savePrefs("Thursday", true);
                }
                if (!thursday.isChecked()){
                    savePrefs("Thursday", false);
                }
                if (friday.isChecked()){
                    savePrefs("Friday", true);
                }
                if (!friday.isChecked()){
                    savePrefs("Friday", false);
                }
                if (saturday.isChecked()){
                    savePrefs("Saturday", true);
                }
                if (!saturday.isChecked()){
                    savePrefs("Saturday", false);
                }
                if (sunday.isChecked()){
                    savePrefs("Sunday", true);
                }
                if (!sunday.isChecked()){
                    savePrefs("Sunday", false);
                }

            }
        };
        final Button backButton = (Button) findViewById(R.id.backButton);

        View.OnClickListener goBack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };

        backButton.setOnClickListener(goBack);
    }

    //save prefs
    public void savePrefs(String key, boolean value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

}
