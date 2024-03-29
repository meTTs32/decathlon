package it.unimol.decathlon.gui.DisciplinePanels;

import it.unimol.decathlon.app.Discipline;
import it.unimol.decathlon.app.Player;
import it.unimol.decathlon.gui.MainPanel;
import it.unimol.decathlon.gui.Panel;


import static javax.swing.SwingUtilities.getWindowAncestor;

public abstract class DisciplinePanel extends Panel {

    protected Discipline discipline;

    protected int time;

    protected int temp;

    protected int[] rolls;

    protected int rerolls;

    protected int roll;

    protected boolean reroll;

    protected int attempts;



    public DisciplinePanel(String disciplineName, String instructions){

        this.discipline = new Discipline(disciplineName);
        this.discipline.setInstructions(instructions);

        this.build();
    }

    protected void build(){
        super.build();
        this.setText("Disciplina corrente: " + this.discipline.getName() + "\n\nISTRUZIONI:\n" + this.discipline.getInstructions());

        button1.setText("INIZIA DISCIPLINA");
        button2.setText("TERMINA DISCIPLINA");
        this.button2.setEnabled(false);

        button1.addActionListener(e->this.start());
    }

    private void start() {

        if(!this.discipline.isFinished()) {

//            setup iniziale del giocatore corrente

            Player p = this.discipline.getCurrentPlayer();
            this.setText("Disciplina corrente: " + this.discipline.getName() +"\nÈ il turno di " + p.getName() + "\n");
            this.button1.setText("INIZIA TURNO");
            this.button2.setText("TERMINA TURNO");
            getWindowAncestor(this).revalidate();

            //scambio listener dei bottoni
            this.button1.removeActionListener(this.button1.getActionListeners()[0]);
            if(this.button2.getActionListeners().length > 0)
                this.button2.removeActionListener(this.button2.getActionListeners()[0]);

            //premendo il primo bottone, si inizia il turno del giocatore corrente
            this.button1.addActionListener(e -> {
                this.button1.setEnabled(false);
                this.button2.setEnabled(true);
                this.turn(p);
            });

//            premendo il secondo bottone, si termina il turno del giocatore corrente e si passa al successivo
//            si devono invertire le abilitazioni ai bottoni per permettere al giocatore di iniziare il turno successivo

            this.button2.addActionListener(e-> {
                this.button1.setEnabled(true);
                this.button2.setEnabled(false);
                this.start();
            });
        } else {

//            se la disciplina è terminata, si ripristinano i bottoni come erano prima dell'inizio
//            con la sola differenza che adesso il bottone di termine disciplina è abilitato, in quanto la disciplina è terminata

            button1.setText("INIZIA DISCIPLINA");
            button2.setText("TERMINA DISCIPLINA");
            this.button1.setEnabled(false);
            this.button2.setEnabled(true);
            this.setText(this.discipline.getPodium());
//            scambio listener dei bottoni
            this.button2.removeActionListener(this.button2.getActionListeners()[0]);
            this.button2.addActionListener(e-> this.replace(MainPanel.getInstance()));
        }
    }

    protected abstract void turn(Player p);

    public String getName(){
        return this.discipline.getName();
    }

    protected Thread timer(){

        return new Thread(() -> {
            while (this.time > 0) {
                try {
                    System.out.println(this.time);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Timer interrotto");
                    break;
                }
                this.time--;
            }
        });
    }

}
