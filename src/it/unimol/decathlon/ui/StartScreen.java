package it.unimol.decathlon.ui;

import it.unimol.decathlon.app.*;

public class StartScreen extends Screen {

    public StartScreen() {
        this.playerManager = GestoreGiocatori.getInstance();
        this.disciplina = null;
    }

    public void start(){
        this.clearScreen();
        this.delayScreen(1500);
        System.out.println("BENVENUTO AL DECATHLON!");
        this.delayScreen(1500);
        System.out.println("TA DAAAAAAAAAAAA");
        this.delayScreen(1500);
        System.out.print("Premi invio per iniziare");
        this.waitUserInput();
        this.delayScreen(800);
        this.clearScreen();

        int giocatori = this.setPlayerNumber();

        this.delayScreen(800);
        this.clearScreen();
        System.out.println("Non e' possibile inserire giocatori con lo stesso nome!");
        this.delayScreen(800);

        this.setPlayers(giocatori);

    }

    private int setPlayerNumber(){
        int p;

        do {
            System.out.print("Quanti giocatori siete? ");
            p = this.inputInt();
            if(p < 2)
                System.out.println("Devi inserire almeno 2 giocatori");
        } while(p < 2);

        return p;
    }

    private void setPlayers(int giocatori){

        boolean contained;
        String s;

        for(int i=0; i<giocatori; i++) {

            do{
                contained = false;
                System.out.print("Inserisci nome del giocaotore " + (i+1) + ": ");
                s = this.inputString();
                try {
                    this.playerManager.addGiocatore(new Giocatore(s));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    contained = true;
                }
            } while(contained);


        }
    }

}