package it.unimol.decathlon.app;

import java.util.Random;

public class Dado {
    public static int lancia(){
        Random r = new Random();
        return r.nextInt(6)+1;
    }
}
