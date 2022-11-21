package it.unimol.decathlon.gui;

import it.unimol.decathlon.app.PlayerManager;
import it.unimol.decathlon.gui.DisciplinePanels.Panel100m;

import javax.swing.*;

public class MainPanel extends Panel {

    private static MainPanel instance;

    private Integer currentDiscipline = null;
    private final JPanel[] disciplinePanels;



    public static MainPanel getInstance(){
        if(instance == null){
            instance = new MainPanel();
        }
        return instance;
    }

    private MainPanel(){
        //TODO: implementare il salvataggio dei dati

        //inizializazzione componenti backend;
        this.disciplinePanels = new JPanel[10];
        this.disciplinePanels[0] = new Panel100m();
        //TODO: inserire le discipline nell'array

        //inizializzazione componenti grafici
        this.build();
        this.incrementDiscipline();
        button1.setText("AVVIA DISCIPLINA");
        button2.setText("TERMINA PARTITA");
        String text = "Benvenuto nel Decathlon!\n\n" + "CLASSIFICA:\n\n" + PlayerManager.getInstance().toString();
        this.setText(text);
        System.out.println(this.currentDiscipline);


        this.button1.addActionListener(e -> {
            this.replace(this.disciplinePanels[0]);
            System.out.println(this.currentDiscipline);
        });


    }

    public void incrementDiscipline(){
        if(this.currentDiscipline == null){
            this.currentDiscipline = 0;
            this.button2.setEnabled(false);
        }else if(++this.currentDiscipline==this.disciplinePanels.length) {
                this.button1.setEnabled(false);
                this.button2.setEnabled(true);
            }



    }
}
