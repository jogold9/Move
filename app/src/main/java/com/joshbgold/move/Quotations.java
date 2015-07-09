package com.joshbgold.move;

import java.util.Random;

/**
 * Created by JoshG on 7/8/2015.
 */
public class Quotations {

//Rumi quotes and others
    public String [] quotesArray = {
        "1st rumi quote",
        "2nd rumi quote",
        "1st dali lama quote",
        "famous ant biologist quote",
        "chimp biologist quote",
        "hunter gatherer quote",
        "bruce lee difficult life quote",
        "other quote",
        "other quote"
    };

    public String getQuote(){
        //see FunFacts app ColorWheel.java for how to implement if needed
        String quote = "";

        //Randomly select a quotation from the array of quotes
        Random randomGenerator = new Random();  //Construct a new random number generator
        int randomNumber = randomGenerator.nextInt(quotesArray.length);

        quote = quotesArray[randomNumber];

        return quote;
    }

}
