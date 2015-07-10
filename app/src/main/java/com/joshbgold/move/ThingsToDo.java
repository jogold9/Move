package com.joshbgold.move;

import java.util.Random;

/**
 * Created by JoshG on 7/8/2015.
 */
public class ThingsToDo {

    //actions to do
    public String [] ThingsToDoArray = {
            "Reach towards the sky, then reach & touch your toes (or as close as you can).  Repeat four times.", "Go " +
            "outside", "Walk around the block.", "Take nine slow deep breaths", "Take 7 seven slow deep breaths", "Walk around the block twice."
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
