package com.joshbgold.move;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

      //Retrieve the current day of the week and display it
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayOfTheWeek = simpleDateFormat.format(date);
        Toast.makeText(SettingsActivity.this, "Today is " + dayOfTheWeek, Toast.LENGTH_LONG).show();
    }

}
