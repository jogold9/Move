package com.joshbgold.move.content;

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
        "“Do not pray for an easy life, pray for the strength to endure a difficult one.” - Bruce Lee",
        "“To give anything less than your best, is to sacrifice the gift.” - Steve Prefontaine",
        "“I was taught that the way of progress was neither swift nor easy.” - Marie Curie",
        "“I am determined to be cheerful and happy in whatever situation I may find myself.”  - Martha Washington",
        "“Dwell on the beauty of life. Watch the stars, and see yourself running with them.” ― Marcus Aurelius",
        "“An onion can make people cry, but there has never been a vegetable invented to make them laugh.” - Will Rogers",
        "“There is no such joy in the tavern as upon the road thereto.” - Cormac McCarthy",
        "“If you want one thing too much it's likely to be a disappointment. The healthy way is to learn to like the everyday things...” - Larry " +
                "McMurtry",
        "“There is no bad weather, only inappropriate clothing.” - Sir Ranalph Fiennes",
        "“You only lose what you cling to.” ― Gautama Buddha",
        "“No one saves us but ourselves. No one can and no one may. We ourselves must walk the path.” ― Gautama Buddha ",
        "“If you want to take care of tomorrow, take better care of today. We always live now.” ― Dainin Katagiri",
        "“Let the breath lead the way.” ― Sharon Salzberg",
        "“Suffering usually relates to wanting things to be different from the way they are.” - Alan Lokos",
        "“Everything is connected.” - Alan Moore",
        "“Whenever ego suffers from fear of death & your practice turns to seeing impermanence, ego settles down.” - Tsoknyi Rinpoche"
        
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
