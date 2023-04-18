package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Player;

import javax.swing.*;

import java.util.Arrays;

import static it.unimol.decathlon.gui.Select.SelectionPanel.getjSpinnerPanel;

public class HighJumpPanel extends DisciplinePanel {

    public HighJumpPanel(){

        super("SALTO IN ALTO",
               "Il giocatore inserisce l'altezza a cui vuole gareggiare (tra 5 e 30). Ha, quindi, tre tentativi " +
                       "per lanciare 5 dadi e totalizzare un punteggio maggiore o uguale a quello scelto. Il punteggio totale " +
                       "è dato dall'altezza inserita (anche se il punteggio ottenuto con i dadi supera l'altezza), oppure " +
                       "è 0 se il giocatore non riesce a superare il punteggio prefissato." );
    }

    protected void turn(Player p){

        this.time = 180;
        this.temp = 0;

        Thread timer = this.timer();

        int height = this.selectHeight();
        if (height != 0){
            timer.start();
            this.turnMechanic(p, height, timer);
            if(!timer.isInterrupted())
                timer.interrupt();
        } else
            this.appendText(p.getName() + " ha annullato il turno\n");

        this.appendText(p.getName() + " ha totalizzato " + p.getTempScore() + " punti\n");
    }

    private void turnMechanic(Player p, int height, Thread time){

        this.rerolls = 0;
        this.temp = 0;
        this.reroll = true;

        JLabel label = new JLabel();

        do {
            this.temp = 0;

            for (int i : this.rolls = Dice.roll(5)) {
                this.temp += i;
            }

            JOptionPane panel = new JOptionPane(label, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);


            Thread action = new Thread(() -> {
                while (true){
                    try {
                        Thread.sleep(500);
                        label.setText("<html>ALTEZZA FISSATA: " + height + "<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + " PUNTI<br/>TEMPO RIMASTO: " + this.time + " secondi<br/>Premi OK per rilanciare</html>");

                        if(this.time == 0){
                            this.reroll = false;
                            this.temp = 0;
                            this.appendText(p.getName() + " ha saltato il turno\n");
                            panel.setValue(JOptionPane.CLOSED_OPTION);
                            panel.setVisible(false);
                        }
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });

            action.start();

            if(this.temp >= height || this.rerolls == 2){

                action.interrupt();
                time.interrupt();

                if (this.temp >= height) {
                    label.setText("<html>ALTEZZA FISSATA: " + height + "<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + " punti<br/>HAI SUPERATO IL LANCIO!</html>");
                    this.reroll = false;
                } else {
                    label.setText("<html>ALTEZZA FISSATA: " + height + "<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + " punti<br/>Hai esaurito i tentativi</html>");
                    this.temp = 0;
                }
            }else
                label.setText("<html>ALTEZZA FISSATA: " + height + "<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + " PUNTI<br/>TEMPO RIMASTO: " + this.time + " secondi<br/>Premi OK per rilanciare</html>");

            this.rerolls++;

            panel.createDialog(this, "SALTO IN ALTO - TENTATIVO " + this.rerolls).setVisible(true);


            if(!action.isInterrupted())
                action.interrupt();

        } while (this.rerolls < 3 && this.reroll);

        if(rerolls == 3 && this.temp < height)
            this.appendText(p.getName() + " ha esaurito i tentativi\n");

        p.addTempScore(this.temp);
        p.addScore(this.temp);
    }

    private int selectHeight() {
        JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(5, 5, 30, 1));
        heightSpinner.setEditor(new JSpinner.DefaultEditor(heightSpinner));
        JPanel panel = getjSpinnerPanel(heightSpinner, new JLabel("Seleziona l'altezza obiettivo: "));
        JOptionPane pane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
        pane.createDialog(this, "ALTEZZA").setVisible(true);

        if (pane.getValue() == null)
            return 0;

        return (int) heightSpinner.getValue();

    }

}
