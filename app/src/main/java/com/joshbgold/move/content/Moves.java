package com.joshbgold.move.content;

import android.content.Context;
import android.content.SharedPreferences;

import com.joshbgold.move.main.AlarmActivity;

import java.util.ArrayList;
import java.util.Random;

public class Moves {

    private static Context mAppContext = AlarmActivity.getAppContext();
    public ArrayList<String> movementsArrayList = new ArrayList<>();
    public String customReminder = "";

    public Moves() {
        customReminder = loadPrefs("customReminder", customReminder);
    }

    //actions to do
    public String[] movementsArray = {
            "Reach towards the sky, then reach & touch your toes (or as close as you can).  Repeat four times.", "Go " +
            "outside", "Walk around the block.", "Take nine slow deep breaths", "Take seven slow deep breaths", "Walk around the block twice.",
            "Your choise: Go outside, or seven slow deep breaths.", "Your choice: Go outside, or go up and down some stairs.",
            "Reach towards the sky, then reach & touch your toes (or as close as you can).  Repeat six times.",
            "Roll both shoulders back.  Roll both shoulders forward.  Repeat five times.",
            "Walk for approximately four minutes", "Stand up. Take a slow deep breath.  Sit down.  Repeat four times.",
            "Your choice: Walk up and down stairs for four minutes, or go outside."
    };

    public String getMoves() {
        //see FunFacts app ColorWheel.java for how to implement if needed
        String movements = "";
        StringBuilder customMovements = new StringBuilder();

        if (customReminder != null && !customReminder.isEmpty()) {
            customMovements.append(customReminder);
            return customMovements.toString();

        } else {
            //Randomly select a movement from the array of movements
            Random randomGenerator = new Random();  //Construct a new random number generator
            int randomNumber = randomGenerator.nextInt(movementsArray.length);

            movements = movementsArray[randomNumber];
            return movements;
        }
    }

    private String loadPrefs(String key, String value) {
        SharedPreferences sharedPreferences = mAppContext.getSharedPreferences("MoveAppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, value);
    }
}
