package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Discipline;
import it.unimol.decathlon.app.Player;

import javax.swing.*;
import java.util.Arrays;

public class Panel100m extends DisciplinePanel {

    public Panel100m(){
        this.discipline = new Discipline("100 metri");
        this.build();

    }



    protected void turn(Player p){
        int rerolls = 5;
        rerolls = this.turnMechanic(p,rerolls);
        this.turnMechanic(p,rerolls);
        this.appendText(p.getName() + " ha totalizzato " + p.getTempScore() + " punti\n");

    }

    private int turnMechanic(Player p, int rerolls){
        int temp;
        boolean reroll;
        int[] rolls;

        do {
            temp = 0;
            reroll = true;
            rolls = Dice.roll(4);
            for (int i = 0; i < rolls.length; i++) {
                if (rolls[i] == 6) {
                    rolls[i] *= -1;
                }
                temp += rolls[i];
            }
            if (rerolls > 0){
                int option = JOptionPane.showConfirmDialog(this, "Hai tirato: " + Arrays.toString(rolls) + " per un totale di " + temp + " punti.\nVuoi rilanciare? (RILANCI RIMASTI: " + rerolls + ")", "Lancio", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION)
                    rerolls--;
                else
                    reroll = false;
            }
        } while (rerolls > 0 && reroll);

        this.appendText("Lancio: " + Arrays.toString(rolls) + " (CONGELATO)\n" );

        p.addTempScore(temp);
        p.addScore(temp);

        return rerolls;
    }
}
