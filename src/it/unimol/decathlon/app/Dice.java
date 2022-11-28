package it.unimol.decathlon.app;

import java.util.Random;

public class Dice {
    public static int roll(){
        Random r = new Random();
        return r.nextInt(6)+1;
    }

    public static int[] roll(int n){
        int[] output = new int[n];
        for(int i=0; i<n; i++){
            output[i] = Dice.roll();
        }
        return output;
    }
}
