package com.joshbgold.move;

import java.util.Random;

/**
 * Created by JoshG on 7/8/2015.
 */
public class Quotations {

//Rumi quotes and others
    public String [] quotesArray = {
        "“Only from the heart Can you touch the sky.” - Rumi",
        "“If you want others to be happy, practice compassion. If you want to be happy, practice compassion.” - The 14th Dalai Lama",
        "“Destroying rainforest for economic gain is like burning a Renaissance painting to cook a meal.” - E. O Wilson",
        "“I think the best evenings are when we have messages, things that make us think, but we can also laugh and enjoy each other's company.” - " +
                "Jane Goodall",
        "“Our prime purpose in this life is to help others. And if you can't help them, at least don't hurt them.” - The 14th Dalai Lama",
        "“Do not pray for an easy life, pray for the strength to endure a difficult one.” - Bruce Lee"

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
