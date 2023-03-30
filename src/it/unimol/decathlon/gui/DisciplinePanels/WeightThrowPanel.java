package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Player;

import javax.swing.*;
import java.util.*;

import static javax.swing.JOptionPane.CLOSED_OPTION;

public class WeightThrowPanel extends DisciplinePanel {

    public WeightThrowPanel() {

        super("LANCIO DEL PESO",
            "Il giocatore lancia 8 dadi, uno alla volta. In ogni momento può decidere di fermarsi. " +
            "Se esce 1, tuttavia, il tentativo è nullo e bisogna iniziare da capo. Ogni giocatore ha tre tentativi in totale. " +
            "Il punteggio totale è dato dalla somma dei dadi lanciati. Se l'ultimo tentativo è nullo, " +
            "il punteggio totalizzato dal giocatore è 0.");


    }

    protected void turn(Player p){

        this.time = 180;
        this.reroll = true;

        Thread timer = this.timer();

        timer.start();
        this.throwWeight(p);
        timer.interrupt();

        this.appendText(p.getName() + " ha totalizzato " + p.getTempScore() + " punti\n");
    }

    private void throwWeight(Player p) {


        this.rerolls = 1;
        this.temp = 0;
        int attempts = 8;
        this.reroll = true;

        JLabel label = new JLabel();
        JLabel newlabel = new JLabel();
        List<Integer> dice = new ArrayList<>();

        do{
            String title = "LANCIO - TENTATIVO " + this.rerolls;
            JOptionPane panel = new JOptionPane(label, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION);
            this.roll = Dice.roll();


            if(this.roll != 1){

                dice.add(this.roll);
                this.temp += this.roll;
                attempts--;
                label.setText("<html>Hai ottenuto " + this.roll + "<br/>TOTALE: " + this.temp + "<br/>DADI: "
                        + Arrays.toString(dice.toArray()) + "<br/>TEMPO RIMASTO: " + this.time + "<br/> Vuoi rilanciare?" + "</html>");
            } else{

                this.temp = 0;
                attempts = 8;
                this.rerolls++;
                if(!dice.isEmpty())
                    dice.clear();
                label.setText("<html>Hai ottenuto 1, il tentativo è nullo." + "<br/>TEMPO RIMASTO: " + this.time +
                        "<br/> Vuoi rilanciare?" + "</html>");
            }

            Thread action = new Thread(() -> {
                while (true){
                    try {
                        Thread.sleep(500);

                        if(this.time == 0) {
                            panel.setValue(CLOSED_OPTION);
                            panel.setVisible(false);
                            this.reroll = false;
                            this.temp = 0;
                            this.appendText(p.getName() + " ha saltato il turno\n");
                            break;

                        }else if(this.roll!= 1) {
                            label.setText("<html>Hai ottenuto " + this.roll + "<br/>TOTALE: " + this.temp + "<br/>DADI: "
                                    + Arrays.toString(dice.toArray()) + "<br/>TEMPO RIMASTO: " + this.time + "<br/> Vuoi rilanciare?" + "</html>");
                        }
                        else {
                            label.setText("<html>Hai ottenuto 1, il tentativo è nullo." + "<br/>TEMPO RIMASTO: " + this.time +
                                    "<br/> Vuoi rilanciare?" + "</html>");
                        }

                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });

            action.start();

            if (this.roll == 1 && this.rerolls > 3) {
                action.interrupt();
                newlabel.setText("<html>Hai ottenuto 1, il tentativo è nullo.<br/>Il tuo turno è finito e hai terminato i tentativi, premi OK per terminare.</html>");
                JOptionPane.showMessageDialog(this, newlabel, "LANCIO - TENTATIVO 3", JOptionPane.PLAIN_MESSAGE);
            } else if(attempts > 0 || this.roll == 1) {

                panel.createDialog(this, title).setVisible(true);

                int result;
                try {
                    result = (int) panel.getValue();
                } catch (NullPointerException e) {
                    result = JOptionPane.NO_OPTION;
                }

                if (result != JOptionPane.YES_OPTION)
                    this.reroll = false;

            }else {

                action.interrupt();
                newlabel.setText("<html>Hai ottenuto " + this.roll + "<br/>TOTALE: " + this.temp + "<br/>DADI: "
                        + Arrays.toString(dice.toArray()) + "<br/>Il tuo turno è finito, premi OK per terminare.</html>");

                JOptionPane.showMessageDialog(this, newlabel, "LANCIO - TENTATIVO " + this.rerolls, JOptionPane.PLAIN_MESSAGE);
            }

            if(!action.isInterrupted())
                action.interrupt();

        }while(this.reroll && attempts > 0  && this.rerolls <= 3);

        if(this.roll == 1 && this.rerolls > 3)
            this.appendText(p.getName() + " ha superato il limite di tentativi\n");


        p.addTempScore(this.temp);
        p.addScore(this.temp);

    }
}
