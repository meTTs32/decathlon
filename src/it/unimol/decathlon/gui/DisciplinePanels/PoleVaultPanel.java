package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PoleVaultPanel extends DisciplinePanel{

    private boolean isValid;

    public PoleVaultPanel(){

        super("SALTO CON L'ASTA",
                "Il giocatore inserisce l'altezza a cui vuole gareggiare (tra 8 e 48). Il giocatore può scegliere " +
                        "il numero di dadi da lanciare (tra 2 e 8) e ha tre tentativi per lanciare il numero di dadi scelto e " +
                        "totalizzare un punteggio maggiore o uguale a quello scelto. Se almeno un dado ha valore 1, il tentativo " +
                        "risulta nullo. Il punteggio totale è dato dall'altezza inserita (anche se il punteggio ottenuto " +
                        "con i dadi supera l'altezza), oppure è 0 se il giocatore non riesce a superare il punteggio prefissato.");
    }

    protected void turn(Player p){

        this.time = 180;
        this.temp = 0;

        Thread timer = this.timer();
        int height;
        int dice;

        JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(8, 8, 48, 1));
        heightSpinner.setEditor(new JSpinner.DefaultEditor(heightSpinner));

        JSpinner diceSpinner = new JSpinner (new SpinnerNumberModel(2, 2, 8, 1));
        diceSpinner.setEditor(new JSpinner.DefaultEditor(diceSpinner));

        JOptionPane panel = this.getSpinnerPanels(heightSpinner, diceSpinner);
        panel.createDialog("ALTEZZA E DADI").setVisible(true);

        if(panel.getValue() == null){
            height = 0;
            dice = 0;
        } else{
            height = (int) heightSpinner.getValue();
            dice = (int) diceSpinner.getValue();
        }

        if(height != 0){
            timer.start();
            this.turnMechanic(p, height, dice, timer);
            if(!timer.isInterrupted())
                timer.interrupt();
        } else
            this.appendText(p.getName() + " ha annullato il turno\n");

        this.appendText(p.getName() + " ha totalizzato " + p.getTempScore() + " punti\n");
    }

    private void turnMechanic(Player p, int height, int dice, Thread time) {

        this.rerolls = 0;
        this.temp = 0;
        this.reroll = true;

        JLabel label = new JLabel();

        do{
            this.isValid = true;
            this.temp = 0;

            for (int i : this.rolls = Dice.roll(dice)) {
                if (i == 1){
                    this.isValid = false;
                    break;
                } else
                    this.temp += i;
            }

            JOptionPane panel = new JOptionPane(label, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);

            Thread action = new Thread(() -> {
                while (true){
                    try {
                        Thread.sleep(500);
                        if(this.isValid)
                            label.setText("<html>ALTEZZA FISSATA: " + height + " (" + dice + " dadi selezionati)<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + " PUNTI<br/>TEMPO RIMASTO: " + this.time + " secondi<br/>Premi OK per rilanciare</html>");
                        else
                            label.setText("<html>ALTEZZA FISSATA: " + height + " (" + dice + " dadi selezionati)<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>Hai ottenuto un 1, il lancio non è valido<br/>TEMPO RIMASTO: " + this.time + " secondi<br/>Premi OK per rilanciare</html>");

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

            if (this.temp >= height || this.rerolls == 2) {

                action.interrupt();
                time.interrupt();

                if (this.temp >= height) {
                    label.setText("<html>ALTEZZA FISSATA: " + height + " (" + dice + " dadi selezionati)<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + " punti<br/>HAI SUPERATO IL LANCIO!</html>");
                    this.reroll = false;
                } else {
                    if(this.isValid)
                        label.setText("<html>ALTEZZA FISSATA: " + height + " (" + dice + " dadi selezionati)<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + " punti<br/>Hai esaurito i tentativi</html>");
                    else
                        label.setText("<html>ALTEZZA FISSATA: " + height + " (" + dice + " dadi selezionati)<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>È uscito un 1, il tentativo non è valido.<br/>Hai esaurito i tentativi.</html>");
                    this.temp = 0;
                }
            } else {
                if(this.isValid)
                    label.setText("<html>ALTEZZA FISSATA: " + height + " (" + dice + " dadi selezionati)<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + " PUNTI<br/>TEMPO RIMASTO: " + this.time + " secondi<br/>Premi OK per rilanciare</html>");
                else {
                    label.setText("<html>ALTEZZA FISSATA: " + height + " (" + dice + " dadi selezionati)<br/>LANCIO: " + Arrays.toString(this.rolls) + "<br/>È uscito un 1, il lancio non è valido.<br/>TEMPO RIMASTO: " + this.time + " secondi<br/>Premi OK per rilanciare</html>");
                }

            }

            this.rerolls++;
            panel.createDialog("SALTO - TENTATIVO " + this.rerolls).setVisible(true);


            if (!action.isInterrupted())
                action.interrupt();

        } while (this.rerolls < 3 && this.reroll);

        if(rerolls == 3 && this.temp < height)
            this.appendText(p.getName() + " ha esaurito i tentativi\n");

        p.addTempScore(this.temp);
        p.addScore(this.temp);

    }

    private JOptionPane getSpinnerPanels(JSpinner heightSpinner, JSpinner diceSpinner){

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        panel.add(new JLabel("Seleziona l'altezza obiettivo: "), new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(heightSpinner, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        panel.add(new JLabel("Seleziona il numero di dadi: "), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        panel.add(diceSpinner, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

        return new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
    }
}
