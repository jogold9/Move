package com.joshbgold.move;

import java.util.Random;

/**
 * Created by JoshG on 7/8/2015.
 */
public class ThingsToDo {

    //actions to do
    public String [] ThingsToDoArray = {
            "Reach towards the sky, then reach & touch your toes (or as close as you can).  Repeat four times.", "Go " +
            "outside", "Walk around the block.", "Take nine slow deep breaths", "Take seven slow deep breaths", "Walk around the block twice.",
            "Your choise: Go outside, or seven slow deep breaths.", "Your choice: Go outside, or go up and down some stairs.",
            "Reach towards the sky, then reach & touch your toes (or as close as you can).  Repeat six times.",
            "Roll both shoulders back.  Roll both shoulders forward.  Repeat five times.",
            "Walk a lap or so around the office", "Stand up. Take a slow deep breath.  Sit down.  Repeat four times.",
            "Your choice: Walk a lap or so around the office, or go outside."
    };

    public String getThingToDo(){
        //see FunFacts app ColorWheel.java for how to implement if needed
        String ToDoItem = "";

        //Randomly select a quotation from the array of quotes
        Random randomGenerator = new Random();  //Construct a new random number generator
        int randomNumber = randomGenerator.nextInt(ThingsToDoArray.length);

        ToDoItem = ThingsToDoArray[randomNumber];

        return ToDoItem;
    }
}
