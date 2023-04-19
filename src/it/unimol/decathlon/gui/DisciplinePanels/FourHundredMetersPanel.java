package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Player;

import javax.swing.*;
import java.util.Arrays;

import static javax.swing.JOptionPane.*;

public class FourHundredMetersPanel extends DisciplinePanel{

    public FourHundredMetersPanel(){

        super("400 METRI",
                "Il gioco chiede al giocatore attivo di lanciare due dadi alla volta. Se il giocatore non è " +
                        "soddisfatto del risultato, può riprovare diverse volte, finché non decide di congelare i primi due " +
                        "dadi. Successivamente, il giocatore lancia i secondi due dadi e procede alla stessa maniera. " +
                        "In totale, il giocatore può decidere di rilanciare i dadi (senza congelarli) per 7 volte. Se il " +
                        "giocatore non può ulteriormente rilanciare dadi perché ha esaurito i 7 rilanci a disposizione, " +
                        "i lanci successivi devono essere presi così come sono. Ad esempio, se il giocatore esaurisce i " +
                        "rilanci al secondo slot, gli slot 3 e 4 sono determinati automaticamente (senza la possibilità di rilanciare). " +
                        "Per calcolare il punteggio, si sommano i punteggi ottenuti sui vari dadi; i 6, tuttavia, valgono -6.");

    }



    protected void turn(Player p){

        this.time = 180;
        this.rerolls = 7;

        Thread timer = this.timer();

        timer.start();
        for(this.attempts = 1; this.attempts <= 4; this.attempts++)
            this.turnMechanic(p);
        timer.interrupt();

        this.appendText(p.getName() + " ha totalizzato " + p.getTempScore() + " punti\n");
        p.addScore(p.getTempScore());
    }

    private void turnMechanic(Player p) {


        do {
            this.temp = 0;
            this.reroll = true;

            if (this.rerolls == -1)
                this.reroll = false;
            else {
                this.rolls = Dice.roll(2);
                for (int i = 0; i < this.rolls.length; i++) {

                    if (this.rolls[i] == 6)
                        this.rolls[i] *= -1;

                    this.temp += this.rolls[i];
                }
            }

            if (this.rerolls > 0){

                JLabel label = new JLabel("<html>DADI: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + " punti.<br/>TEMPO RIMASTO : " + this.time + "<br/>Vuoi rilanciare? (" + this.rerolls + " rilanci rimasti)</html>");
                JOptionPane panel = new JOptionPane(label, QUESTION_MESSAGE, YES_NO_OPTION);

                Thread action = new Thread (() -> {
                    while (true) {
                        try {

                            Thread.sleep(500);

                            label.setText("<html>DADI: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + " punti.<br/>TEMPO RIMASTO : " + this.time + "<br/>Vuoi rilanciare? (" + this.rerolls + " rilanci rimasti)</html>");

                            if (this.time == 0) {
                                this.rerolls = -1;
                                this.temp = 0;
                                p.setTempScore(0);
                                this.reroll = false;
                                this.appendText(p.getName() + " ha saltato il turno\n");
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

                panel.createDialog("LANCIO " + this.attempts).setVisible(true);

                int option;

                try {
                    option = (int) panel.getValue();
                } catch (NullPointerException e){
                    option = NO_OPTION;
                }


                if (option == YES_OPTION)
                    this.rerolls--;
                else
                    this.reroll = false;

                if(!action.isInterrupted())
                    action.interrupt();

            }
        } while (this.rerolls > 0 && this.reroll);

        if (this.rerolls > -1){
            this.appendText("Lancio " + this.attempts + ": " + Arrays.toString(this.rolls) + " (CONGELATO)\n");
        }

        p.addTempScore(this.temp);
    }

}
