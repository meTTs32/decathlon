package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class JavelinPanel extends DisciplinePanel{

    private final JLabel errorLabel = new JLabel();
    private final JOptionPane error = new JOptionPane(errorLabel, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
    private int selected;

    public JavelinPanel(){
        super("LANCIO DEL GIAVELLOTTO",
                "Il giocatore lancia 6 dadi. A ogni turno, il giocatore deve congelare almeno un dado e, se vuole, " +
                        "può rilanciare i dadi restanti. Possono essere congelati soltanto i dadi con valore dispari (1, 3, 5). " +
                        "Se tutti i dadi lanciati o rilanciati sono pari, il tentativo è nullo e il giocatore ottiene 0 punti in totale. " +
                        "Il punteggio totale è dato dalla somma del valore dei dadi congelati.");
    }

    protected void turn(Player p){

        this.time = 180;
        this.reroll = true;

        Thread timer = this.timer();

        timer.start();
        this.throwJavelin(p, timer);
        if(!timer.isInterrupted())
            timer.interrupt();

        this.appendText(p.getName() + " ha totalizzato " + p.getTempScore() + " punti");
    }

    private void throwJavelin(Player p, Thread timer) {

        this.temp = 0;
        this.rerolls = 5;
        JLabel label = new JLabel();
        Thread action = null;
        boolean valid;

        do{

            valid = false;
            this.rolls = Dice.roll(this.rerolls);
            for (int roll: this.rolls)
                if(roll % 2 != 0) {
                    valid = true;
                    break;
                }

            if(!valid){
                timer.interrupt();
                this.temp = 0;
                label.setText("Non è possibile congelare dadi, il tentativo è nullo");
                JOptionPane.showMessageDialog(this, label, "TENTATIVO NULLO", JOptionPane.ERROR_MESSAGE);
            } else {
                if(this.rerolls == 1){
                    this.autoFreeze(action);

                    label.setText("<html>TOTALE: " + this.temp + "<br/>Hai ottenuto " + this.roll + "<br/>Essendo l'ultimo dado, è stato congelato automaticamente</html>");
                    JOptionPane.showMessageDialog(this, label, "LANCIO", INFORMATION_MESSAGE);

                } else {
                    label.setText("<html>LANCIO: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + "<br/> DADI CONGELATI : " + (5 - this.rerolls) +
                            "<br/>TEMPO RIMASTO: " + this.time + "<br/>Rilanci? (scegli quali dadi congelare)</html>");

                    JPanel panel = this.buildDialog(this.rolls, label);

                    JOptionPane option = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);

                    action = new Thread(() -> {
                        while (true) {
                            try {
                                Thread.sleep(500);

                                label.setText("<html>LANCIO: " + Arrays.toString(this.rolls) + "<br/>TOTALE: " + this.temp + "<br/> DADI CONGELATI : " + (5 - this.rerolls) +
                                        "<br/>TEMPO RIMASTO: " + this.time + "<br/>Rilanci? (scegli quali dadi congelare)</html>");

                                errorLabel.setText("<html>Devi selezionare almeno un dado<br/>TEMPO RIMASTO: "
                                        + this.time + "</html>");

                                if (this.time == 0) {
                                    this.appendText(p.getName() + " ha saltato il turno\n");
                                    this.rerolls = -1;
                                    this.temp = 0;
                                    if (error.isVisible()) {
                                        error.setValue(JOptionPane.CLOSED_OPTION);
                                        error.setVisible(false);
                                    }

                                    option.setValue(JOptionPane.CLOSED_OPTION);
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
                        option.createDialog("LANCIO").setVisible(true);
                        this.turnMechanic(option, panel);
                    } while (this.selected == 0 && this.reroll);

                    if (!action.isInterrupted())
                        action.interrupt();
                }
            }
        }while(this.reroll && this.rerolls > 0 && valid);

        p.addTempScore(this.temp);
        p.addScore(this.temp);
    }

    private JPanel buildDialog(int[] rolls, JLabel label){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        panel.add(label, new GridBagConstraints(0, 0, 5, 1, 0.01, 0, GridBagConstraints.CENTER, GridBagConstraints.NORTH, new Insets(10, 5, 5, 5), 0, 0));

        int i = 0;
        for (int roll : rolls) {
            if (roll % 2 != 0) {
                JCheckBox box = new JCheckBox();
                box.setText(String.valueOf(roll));
                panel.add(box, new GridBagConstraints(i++, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NORTH, new Insets(10, 5, 5, 5), 0, 0));
            }
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

        if (result == JOptionPane.YES_OPTION) {
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
            }
        } else if (result == DEFAULT_OPTION || result == JOptionPane.NO_OPTION)
            this.reroll = false;
    }

    private void autoFreeze(Thread action) {
        this.rerolls--;

        if(action!=null && !action.isInterrupted())
            action.interrupt();

        this.roll = Dice.roll();
        this.temp += this.roll;

    }

}
