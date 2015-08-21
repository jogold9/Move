package com.joshbgold.move;

import java.util.Random;

/**
 * Created by JoshG on 7/8/2015.
 */
public class Moves {

    //actions to do
    public String [] movementsArray = {
            "Reach towards the sky, then reach & touch your toes (or as close as you can).  Repeat four times.", "Go " +
            "outside", "Walk around the block.", "Take nine slow deep breaths", "Take seven slow deep breaths", "Walk around the block twice.",
            "Your choise: Go outside, or seven slow deep breaths.", "Your choice: Go outside, or go up and down some stairs.",
            "Reach towards the sky, then reach & touch your toes (or as close as you can).  Repeat six times.",
            "Roll both shoulders back.  Roll both shoulders forward.  Repeat five times.",
            "Walk for approximately four minutes", "Stand up. Take a slow deep breath.  Sit down.  Repeat four times.",
            "Your choice: Walk up and down stairs for four minutes, or go outside."
    };

    public String getMoves(){
        //see FunFacts app ColorWheel.java for how to implement if needed
        String movements = "";

        //Randomly select a movement from the array of movements
        Random randomGenerator = new Random();  //Construct a new random number generator
        int randomNumber = randomGenerator.nextInt(movementsArray.length);

        movements = movementsArray[randomNumber];

        return movements;
    }
}
