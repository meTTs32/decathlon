package it.unimol.decathlon.app;

import java.util.Random;

public class Dice {

    /**
     * Classe che rappresenta un dado.
     * <br/>
     * Lancia un singolo dado
     * @return il numero uscito dal dado
     */
    public static int roll(){
        Random r = new Random();
        return r.nextInt(6)+1;
    }


    /**
     * Lancia n dadi
     * @param n numero di dadi da lanciare
     * @return un array contenente i numeri usciti dai dadi
     */
    public static int[] roll(int n){
        int[] output = new int[n];
        for(int i=0; i<n; i++){
            output[i] = Dice.roll();
        }
        return output;
    }
}
