package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Player;

import javax.swing.*;

import static javax.swing.JOptionPane.*;

public class FifteenHundredPanel extends DisciplinePanel{


    public FifteenHundredPanel(){
        super("1500 METRI",
                "Il gioco chiede al giocatore attivo di lanciare un dado alla volta. Se il giocatore non è soddisfatto " +
                        "del risultato, può riprovare diverse volte, finché non decide di congelare il dado. Successivamente, " +
                        "il giocatore lancia il secondo dado e procede alla stessa maniera. In totale, il giocatore può " +
                        "decidere di rilanciare i dadi (senza congelarli) per 5 volte. Se il giocatore non può ulteriormente " +
                        "rilanciare dadi perché ha esaurito i 5 rilanci a disposizione, i lanci successivi devono essere presi " +
                        "così come sono. Ad esempio, se il giocatore esaurisce i rilanci al terzo dado, i valori dei dadi dal " +
                        "quarto all'ottavo sono determinati automaticamente (senza la possibilità di rilanciare). " +
                        "Per calcolare il punteggio, si sommano i punteggi ottenuti sui vari dadi; i 6, tuttavia, valgono -6.");
    }

    protected void turn(Player p){

        this.time = 180;
        this.rerolls = 5;

        Thread timer = this.timer();

        timer.start();
        for(this.attempts = 1; this.attempts <= 8; this.attempts++)
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
                this.temp = Dice.roll();
                if (this.temp == 6)
                    this.temp *= -1;
                }

            if (this.rerolls > 0){

                JLabel label = new JLabel("<html>PUNTEGGIO: " + this.temp + " punti.<br/>TEMPO RIMASTO : " + this.time + "<br/>Vuoi rilanciare? (" + this.rerolls + " rilanci rimasti)</html>");
                JOptionPane panel = new JOptionPane(label, QUESTION_MESSAGE, YES_NO_OPTION);

                Thread action = new Thread (() -> {
                    while (true) {
                        try {

                            Thread.sleep(500);

                            label.setText("<html>PUNTEGGIO: " + this.temp + " punti.<br/>TEMPO RIMASTO : " + this.time + "<br/>Vuoi rilanciare? (" + this.rerolls + " rilanci rimasti)</html>");

                            if (this.time == 0) {
                                this.rerolls = -1;
                                this.temp = 0;
                                this.reroll = false;

                                p.setTempScore(0);
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
            this.appendText("Lancio " + this.attempts + ": " + this.temp + " (CONGELATO)\n");
        }

        p.addTempScore(this.temp);
    }

}
