package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Discipline;
import it.unimol.decathlon.app.Player;

import javax.swing.*;
import java.util.Arrays;

import static javax.swing.JOptionPane.*;

public class Panel100m extends DisciplinePanel {

    private int temp;

    private int[] rolls;

    private int rerolls;


    public Panel100m(){

        this.discipline = new Discipline("100 metri");

        this.discipline.setInstructions("Ogni giocatore lancia quattro dadi alla volta. Se non è soddisfatto del risultato, può riprovare diverse volte, " +
                "finché non decide di congelare i primi quattro dadi. Successivamente, il giocatore lancia i quattro dadi " +
                "restanti e procede alla stessa maniera. In totale, il giocatore può decidere di rilanciare i dadi " +
                "(senza congelarli) per 5 volte. Per calcolare il punteggio, si sommano i punteggi ottenuti sui vari dadi. " +
                "I 6, tuttavia, valgono -6.");

        this.build();

    }



    protected void turn(Player p){

        this.time = 180;

        Thread timer = new Thread(() -> {
            while (this.time > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                this.time--;
            }
        });
        timer.start();

        this.rerolls = 5;
        this.rerolls = this.turnMechanic(p);
        this.turnMechanic(p);
        timer.interrupt();
        this.appendText(p.getName() + " ha totalizzato " + p.getTempScore() + " punti\n");

    }

    private int turnMechanic(Player p) {

        boolean reroll;

        do {
            reroll = true;
            this.temp = 0;
            if (this.rerolls == -1){
                this.rolls = new int[]{0,0,0,0};
            } else {
                this.rolls = Dice.roll(4);
                for (int i = 0; i < this.rolls.length; i++) {
                    if (this.rolls[i] == 6) {
                        this.rolls[i] *= -1;
                    }
                    this.temp += this.rolls[i];
                }
            }
            if (this.rerolls > 0){

                final JLabel label = new JLabel("<html>" + Arrays.toString(this.rolls) + "  Totale: " + this.temp + " punti.<br/>Rilanciare? (" + this.rerolls + " rilanci rimasti)<br/>TEMPO RIMASTO : " + this.time + "</html>");
                JOptionPane panel = new JOptionPane(label, QUESTION_MESSAGE, YES_NO_OPTION, null, null, NO_OPTION);


                Thread action = new Thread (() -> {
                    while (true) {
                        try {
                            Thread.sleep(500);
                            label.setText("<html>" + Arrays.toString(this.rolls) + "  Totale: " + this.temp + " punti.<br/>Rilanciare? (" + this.rerolls + " rilanci rimasti)<br/>TEMPO RIMASTO : " + this.time + "</html>");
                            if (this.time == 0) {
                                panel.setValue(CLOSED_OPTION);
                                panel.setVisible(false);
                                break;
                            }
                        } catch (InterruptedException e) {
                            break;
                        }
                    }

                });

                action.start();

                panel.createDialog("RILANCIO").setVisible(true);

                int option;

                try {
                    option = (int) panel.getValue();
                } catch (NullPointerException e){
                    option = NO_OPTION;
                }


                if (option == YES_OPTION) {
                    this.rerolls--;
                } else if (option == NO_OPTION){
                    reroll = false;
                } else {
                    this.rerolls = -1;
                    this.temp = 0;
                    this.rolls = new int[]{0, 0, 0, 0};
                    this.appendText(p.getName() + " ha saltato il turno\n");
                }

            }
        } while (this.rerolls > 0 && reroll);

        if (this.rerolls > -1){
            this.appendText("Lancio: " + Arrays.toString(this.rolls) + " (CONGELATO)\n");
        }

        p.addTempScore(this.temp);
        p.addScore(this.temp);

        return this.rerolls;
    }
}
