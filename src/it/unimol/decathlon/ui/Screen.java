package it.unimol.decathlon.ui;

import it.unimol.decathlon.app.*;

import java.io.Serializable;
import java.util.*;

public abstract class Screen implements Serializable {
    protected PlayerManager playerManager;
    protected Discipline discipline;

    protected final String FILENAME = "mainMenu.bin";

    public abstract void start();

    public void delayScreen(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public int inputInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public String inputString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void waitUserInput() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }


    public boolean isFinished() {
        return this.discipline.getFinished();
    }


    public Discipline getDisciplina() {
        return this.discipline;
    }


    public void suspance(String message , int ms) {
        for(int i=0; i<2; i++){
            this.clearScreen();
            System.out.print(message);
            this.delayScreen(ms);
            System.out.print(". ");
            this.delayScreen(ms);
            System.out.print(". ");
            this.delayScreen(ms);
            System.out.print(". ");
            this.delayScreen(ms);
        }
    }

    public void getEnding() {
        this.suspance("Calcolo vincitore in corso, attendi" , 700);
        this.clearScreen();
        System.out.println(this.discipline.getPodium() +"\n");
        System.out.println("Il vincitore della disciplina  \"" + this.discipline.getName() + "\" e' " + this.discipline.getWinner() + " punti!");
        System.out.print("Premi invio per continuare");
        this.waitUserInput();
    }


}
