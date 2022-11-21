package it.unimol.decathlon.app;

import java.util.Random;

public class Dice {
    public static int roll(){
        Random r = new Random();
        return r.nextInt(6)+1;
    }
}
