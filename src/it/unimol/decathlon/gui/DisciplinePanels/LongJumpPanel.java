package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Player;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.*;

public class LongJumpPanel extends DisciplinePanel {

    private final JLabel errorLabel = new JLabel();
    private final JOptionPane error = new JOptionPane(errorLabel, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
    private int selected;
    public LongJumpPanel(){

        super("SALTO IN LUNGO",
                "Questo gioco si compone di due fasi:\n- Rincorsa: Il giocatore lancia i 5 dadi a " +
                "disposizione. A questo punto, sceglie quali dadi congelare (almeno uno). Il giocatore può lanciare " +
                "diverse volte i dadi non ancora congelati, ma a ogni rilancio deve per forza congelare un dado. " +
                "Quando il giocatore è soddisfatto dei punti accumulati in questa prima fase, può decidere di fermarla. " +
                "Se, però, il giocatore supera 9 punti con i dadi congelati, il tentativo risulta essere nullo, non si " +
                "procede alla fase 2 e il giocatore totalizza 0 punti.\n- Salto: Il giocatore prende il numero di dadi " +
                "congelati e procede come nella fase precedente: ad ogni lancio sceglie quali dadi congelare (almeno uno) " +
                "e procede. Questa volta non ha un limite massimo che invalida il tentativo. Per calcolare il punteggio " +
                "totale si sommano i valori ottenuti in entrambe le fasi (rincorsa e salto).");

    }

    protected void turn (Player p){

        this.time = 180;
        this.reroll = true;

        Thread timer = this.timer();

        timer.start();
        this.prep(p, timer);
        this.jump(p, timer);
        timer.interrupt();

        this.appendText(p.getName() + " ha totalizzato " + p.getTempScore() + " punti\n");
    }

    private void prep(Player p, Thread timer){

        this.temp = 0;
        this.rerolls = 5;
        JLabel label = new JLabel();
        Thread action = null;

        do {

            if (this.rerolls == 1) {
                this.autoFreeze(action);
                label.setText("<html>TOTALE: " + this.temp + "/9<br/>Hai ottenuto " + roll + "<br/>Essendo l'ultimo dado, è stato congelato automaticamente</html>");
                JOptionPane.showMessageDialog(this, label, "SALTO", INFORMATION_MESSAGE);
            } else {
                label.setText("<html>TOTALE: " + this.temp + "/9<br/>TEMPO RIMASTO: " + this.time +
                        "<br/>Rilanci? (scegli quali dadi congelare)</html>");

                this.rolls = Dice.roll(this.rerolls);
                JPanel panel = this.buildDialog(this.rolls, label);

                JOptionPane option = new JOptionPane(panel, PLAIN_MESSAGE, YES_NO_OPTION);


                action = new Thread(() -> {
                    while (true) {
                        try {

                            Thread.sleep(500);

                            label.setText("<html>TOTALE: " + this.temp + "/9<br/>TEMPO RIMASTO: " + this.time +
                                    "<br/>Rilanci? (scegli quali dadi congelare)</html>");

                            errorLabel.setText("<html>Devi selezionare almeno un dado<br/>TEMPO RIMASTO: "
                                    + this.time + "</html>");

                            if (this.time == 0) {
                                this.rerolls = -1;
                                if (error.isVisible()) {
                                    error.setValue(CLOSED_OPTION);
                                    option.setVisible(false);
                                }
                                option.setValue(CLOSED_OPTION);
                                option.setVisible(false);
                                this.appendText(p.getName() + " ha saltato il turno\n");

                                this.temp = 0;
                                break;
                            }

                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                });

                action.start();

                do {

                    label.setText("<html>TOTALE: " + this.temp + "/9<br/>TEMPO RIMASTO: " + this.time +
                            "<br/>Rilanci? (scegli quali dadi congelare)</html>");


                    option.createDialog("RINCORSA").setVisible(true);
                    this.turnMechanic(option, panel);

                } while (this.selected == 0 && this.reroll);

                if (!action.isInterrupted())
                    action.interrupt();
            }

            if(this.temp > 9) {
                timer.interrupt();
                JOptionPane.showMessageDialog(this, "Hai superato 9 punti, il tentativo è nullo", "ERRORE", ERROR_MESSAGE);
            }
        } while (this.reroll && this.temp < 9 && this.rerolls > 0);

        this.rerolls = 5 - this.rerolls;

        if (this.temp <= 9) {
            timer.interrupt();
            JOptionPane.showMessageDialog(this, "<html>Rincorsa terminata" +
                    "<br/>PARZIALE: " + this.temp + "<br/> DADI PER IL SALTO: " + this.rerolls +"</html>", "RINCORSA", INFORMATION_MESSAGE);
        }
    }




    private void jump(Player p, Thread timer){
        if(this.temp > 9) {
            this.temp = 0;
            this.appendText("Salto nullo per " + p.getName() + "\n");
        } else if(this.rerolls != -1) {


            this.reroll = true;
            JLabel label = new JLabel();
            Thread action = null;

            do {

                if (this.rerolls == 1) {
                    this.autoFreeze(action);

                    label.setText("<html>TOTALE: " + this.temp + "<br/>Hai ottenuto " + this.roll + "<br/>Essendo l'ultimo dado, è stato congelato automaticamente</html>");
                    JOptionPane.showMessageDialog(this, label, "SALTO", INFORMATION_MESSAGE);

                } else {

                    timer = this.timer();
                    timer.start();

                    label.setText("<html>TOTALE: " + this.temp + "<br/>TEMPO RIMASTO: " + this.time +
                            "<br/>Rilanci? (scegli quali dadi congelare)</html>");

                    this.rolls = Dice.roll(this.rerolls);
                    JPanel panel = this.buildDialog(this.rolls, label);

                    JOptionPane option = new JOptionPane(panel, QUESTION_MESSAGE, YES_NO_OPTION);

                    action = new Thread(() -> {
                        while (true) {
                            try {

                                Thread.sleep(500);

                                label.setText("<html>TOTALE: " + this.temp + "<br/>TEMPO RIMASTO: " + this.time +
                                        "<br/>Rilanci? (scegli quali dadi congelare)</html>");

                                errorLabel.setText("<html>Devi selezionare almeno un dado<br/>TEMPO RIMASTO: "
                                        + this.time + "</html>");

                                if (this.time == 0) {
                                    this.appendText(p.getName() + " ha saltato il turno\n");
                                    this.rerolls = -1;
                                    this.temp = 0;
                                    if (error.isVisible()) {
                                        error.setValue(CLOSED_OPTION);
                                        error.setVisible(false);

                                    }

                                    option.setValue(CLOSED_OPTION);
                                    option.setVisible(false);

                                    break;
                                }

                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    });

                    action.start();

                    do {

                        option.createDialog("SALTO").setVisible(true);
                        this.turnMechanic(option, panel);

                    } while (this.selected == 0 && this.reroll);

                    if (!action.isInterrupted())
                        action.interrupt();
                }

            } while (this.reroll && this.rerolls>0);
        }

        p.addTempScore(this.temp);
        p.addScore(this.temp);
        timer.interrupt();
    }

    private void autoFreeze(Thread action) {
        this.rerolls--;

        if(action!=null && !action.isInterrupted())
            action.interrupt();

        this.roll = Dice.roll();
        this.temp += this.roll;

    }

    private JPanel buildDialog(int[] rolls, JLabel label){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        panel.add(label, new GridBagConstraints(0, 0, 5, 1, 0.01, 0, GridBagConstraints.CENTER, GridBagConstraints.NORTH, new Insets(10, 5, 5, 5), 0, 0));

        for (int i=0; i < rolls.length; i++){
            JCheckBox box = new JCheckBox();
            box.setText(String.valueOf(rolls[i]));
            panel.add(box, new GridBagConstraints(i, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NORTH, new Insets(10, 5, 5, 5), 0, 0));
        }

        panel.validate();
        return panel;
    }

    private void turnMechanic(JOptionPane option, JPanel panel){

        this.selected = 0;
        int result;

        try{
            result = (int) option.getValue();
        } catch (NullPointerException e){
            result = JOptionPane.NO_OPTION;
        }

        if (result == JOptionPane.YES_OPTION || result == JOptionPane.NO_OPTION || result == DEFAULT_OPTION) {

            for (Component c : panel.getComponents()) {
                if (c instanceof JCheckBox && ((JCheckBox) c).isSelected()) {
                    this.temp += Integer.parseInt(((JCheckBox) c).getText());
                    this.selected++;
                    this.rerolls--;
                }
            }

            if (this.selected == 0) {
                errorLabel.setText("<html>Devi selezionare almeno un dado<br/>TEMPO RIMASTO: " + this.time + "</html>");
                error.createDialog("ERRORE").setVisible(true);
            } else if (result == DEFAULT_OPTION || result == JOptionPane.NO_OPTION)
                this.reroll = false;
        }
    }
}
