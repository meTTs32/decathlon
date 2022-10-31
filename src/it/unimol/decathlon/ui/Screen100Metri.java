package it.unimol.decathlon.ui;

import it.unimol.decathlon.app.*;

import java.util.*;


public class Screen100Metri extends Screen{


    public Screen100Metri(Disciplina disciplina) {
        this.disciplina = disciplina;
        this.playerManager = GestoreGiocatori.getInstance();
    }


    public void start(){
        Giocatore currentPlayer;
        String start;
        this.playerManager.resetTemp();

        do {
            currentPlayer = this.disciplina.getCurrentPlayer();
            start = "Gioco corrente: " + this.disciplina.getNome() + "\nE' il turno di " + currentPlayer.getNome();
            this.clearScreen();
            System.out.println(start);
            this.getPunti(currentPlayer);
        } while(!(this.disciplina.isFinished()));

        this.clearScreen();
        this.getEnding();
        this.disciplina.setFinished(true);
    }



    private void getPunti(Giocatore currentPlayer){

        String start = "Gioco corrente: 100 metri\nE' il turno di " + currentPlayer.getNome();
        int rilanci = 5;
        String Slot;
        String Slot2;
        String input;
        boolean continua;
        int[] temp;
        int somma = 0;
        int parziale = 0;

        System.out.println("Premi invio per lanciare");
        this.waitUserInput();

        do{

            this.suspance(start + "\nLancio in corso" , 400);
            Slot = "Slot 1 : ";
            //somma = 0;
            continua = false;
            temp = new int[4];
            for (int i=0; i<temp.length; i++){
                temp[i] = Dado.lancia();
                if (temp[i] == 6)
                    temp[i] = -6;
                somma += temp[i];
            }

            Slot += Arrays.toString(temp);
            this.clearScreen();
            System.out.println(start);
            System.out.println(Slot);
            System.out.println("Totale :" + somma);

            if (rilanci>0){
                System.out.println("Rilanci (" + rilanci + " rilanci rimasti)?");
                System.out.print("(Premi s per rilanciare, altrimenti premi qualsiasi altro tasto): ");
                input = inputString();
                if(!(input.isEmpty()) && input.equals("s")) {
                    rilanci--;
                    continua = true;
                }
            }
        }while (rilanci>=0 && continua);


        do{
            String wait = Slot + " (congelato)\n\nLancio in corso";
            temp = new int[4];
            continua = false;
            Slot2 = "Slot 2: ";
            //somma = parziale;
            parziale = 0;
            this.suspance(start + "\n" + wait , 400);
            temp = new int[4];
            for (int i=0; i<temp.length; i++){
                temp[i] = Dado.lancia();
                if (temp[i] == 6)
                    temp[i] = -6;
                //somma += temp[i];
                parziale += temp[i];
            }
            this.clearScreen();
            Slot2 += Arrays.toString(temp);
            System.out.println(start);
            System.out.println(Slot + " (congelato)");
            System.out.println(Slot2);
            System.out.println("Totale :" + parziale);

            if (rilanci>0){
                System.out.println("Rilanci (" + rilanci + " rilanci rimasti)?");
                System.out.print("(Premi s per rilanciare, altrimenti premi qualsiasi altro tasto): ");
                input = inputString();
                if(!(input.isEmpty()) && input.equalsIgnoreCase("s")) {
                    rilanci--;
                    continua = true;
                }
            } else {
                System.out.print("Rilanci terminati. premi invio per concludere");
                this.waitUserInput();
            }

        } while(rilanci>=0 && continua);

        somma += parziale;
        this.suspance("Calcolo punteggio" , 500);
        this.clearScreen();
        System.out.println(currentPlayer.getNome() + " ha totalizzato " + somma + " punti in questa disciplina");
        currentPlayer.addTempPunteggio(somma);
        currentPlayer.addPunteggio(somma);
        System.out.println("Premi invio per continuare");
        this.waitUserInput();

    }



}
