package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Dice;
import it.unimol.decathlon.app.Discipline;
import it.unimol.decathlon.app.Player;

import javax.swing.*;
import java.util.Arrays;

public class Panel100m extends DisciplinePanel {

    private final String ISTRUZIONI =
            "Ogni giocatore lancia quattro dadi alla volta. Se non è soddisfatto del risultato, può riprovare diverse volte, " +
            "finché non decide di congelare i primi quattro dadi. Successivamente, il giocatore lancia i quattro dadi " +
            "restanti e procede alla stessa maniera. In totale, il giocatore può decidere di rilanciare i dadi " +
            "(senza congelarli) per 5 volte. Per calcolare il punteggio, si sommano i punteggi ottenuti sui vari dadi. " +
            "I 6, tuttavia, valgono -6.";

    public Panel100m(){
        this.discipline = new Discipline("100 metri");
        this.discipline.setInstructions(ISTRUZIONI);
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
