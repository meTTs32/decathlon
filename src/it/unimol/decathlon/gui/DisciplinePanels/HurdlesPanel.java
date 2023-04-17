package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Player;

import javax.swing.*;
import java.util.Arrays;

import static javax.swing.JOptionPane.*;

public class HurdlesPanel extends DisciplinePanel{

    public HurdlesPanel(){
        super("110 METRI OSTACOLI", "Il giocatore lancia 5 dadi. Se non è soddisfatto del risultato, "
                + "il giocatore può rilanciare tutti i dadi. Ogni volta che rilancia, però, viene applicato un malus di 1 punto " +
                "sul totale. Il giocatore può rilanciare fino a un massimo di 6 volte (oltre al primo tentativo). Il punteggio " +
                "totale è dato dalla somma dei dadi dell'ultimo tentativo meno i malus accumulati a causa dei rilanci.");
    }

    protected void turn(Player p) {

        this.time = 180;
        this.rerolls = 0;

        Thread timer = this.timer();

        timer.start();
        this.turnMechanic(p);
        timer.interrupt();
        this.appendText(p.getName() + " ha totalizzato " + p.getTempScore() + " punti\n");
        p.addScore(this.temp);
    }

    private void turnMechanic(Player p) {

        this.reroll = true;

        do {
            this.temp = 0;

            this.rolls = Dice.roll(5);
            for (int i : this.rolls) {
                this.temp += i;
            }

            this.temp -= this.rerolls;

            if(this.rerolls == 6)
                this.reroll = false;
            else {
                JLabel label = new JLabel("<html>DADI: " + Arrays.toString(this.rolls) + "<br/>MALUS: " + this.rerolls + "<br/>TOTALE: " + this.temp + " punti.<br/>TEMPO RIMASTO : " + this.time + "<br/>Vuoi rilanciare? (" + (6 - this.rerolls) + " rilanci rimasti)</html>");
                JOptionPane panel = new JOptionPane(label, QUESTION_MESSAGE, YES_NO_OPTION);

                Thread action = new Thread(() -> {
                    while (true) {
                        try {
                            Thread.sleep(500);
                            label.setText("<html>DADI: " + Arrays.toString(this.rolls) + "<br/>MALUS: " + this.rerolls + "<br/>TOTALE: " + this.temp + " punti.<br/>TEMPO RIMASTO : " + this.time + "<br/>Vuoi rilanciare? (" + (6 - this.rerolls) + " rilanci rimasti)</html>");

                            if (this.time == 0) {
                                this.reroll = false;
                                this.temp = 0;
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
                panel.createDialog("RILANCIO").setVisible(true);

                int option;

                try {
                    option = (int) panel.getValue();
                } catch (NullPointerException e) {
                    option = NO_OPTION;
                }

                if (option == YES_OPTION) {
                    this.rerolls++;
                } else
                    this.reroll = false;

                if (!action.isInterrupted())
                    action.interrupt();
            }

        } while (this.reroll && this.rerolls <= 6);

        p.addTempScore(this.temp);
    }
}
