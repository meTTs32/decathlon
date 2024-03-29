package it.unimol.decathlon.gui;


import it.unimol.decathlon.app.PlayerManager;
import it.unimol.decathlon.gui.DisciplinePanels.*;


import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class MainPanel extends Panel {

    private static MainPanel instance;

    private Integer currentDiscipline = null;
    private final DisciplinePanel[] disciplinePanels;

    private PlayerManager playerManager;


    public static MainPanel getInstance(){
        if(instance == null){
            instance = new MainPanel();
        }
        instance.firePropertyChange("instance", null, instance);
        return instance;
    }

    private MainPanel(){

        //inizializazzione componenti applicativi;

        this.disciplinePanels = new DisciplinePanel[]{
                new HundredMetersPanel(),
                new LongJumpPanel(),
                new WeightThrowPanel(),
                new HighJumpPanel(),
                new FourHundredMetersPanel(),
                new HurdlesPanel(),
                new DiscusPanel(),
                new PoleVaultPanel(),
                new JavelinPanel(),
                new FifteenHundredPanel()
        };

        //inizializzazione componenti grafici
        this.build();
        this.addPropertyChangeListener("instance", e->this.setup());

        this.button1.setText("AVVIA DISCIPLINA");
        this.button2.setText("TERMINA PARTITA");


        this.button1.addActionListener(e -> this.replace(this.disciplinePanels[this.currentDiscipline]));
        this.button2.addActionListener(e -> {

            JOptionPane.showMessageDialog(this, "Il vincitore è " + PlayerManager.getInstance().getPlayer(0).toString() + " punti!");
            File f = new File(FILENAME);
            boolean del = false;
            if(f.exists())
                del = f.delete();

            System.exit(del ? 0 : 1);
        });


    }

    private void setup(){
        if(this.currentDiscipline == null){
            this.currentDiscipline = 0;
            this.button2.setEnabled(false);
            this.playerManager = PlayerManager.getInstance();
        }else if(++this.currentDiscipline==this.disciplinePanels.length) {
            this.button1.setEnabled(false);
            this.button2.setEnabled(true);
        }

        this.printDisciplines();
        this.save();


    }


    private void printDisciplines(){

        this.setText("CLASSIFICA:\n\n" + PlayerManager.getInstance().toString() + "\nDISCIPLINE:\n\n");
        for (int i =0; i<this.disciplinePanels.length; i++){
            if (i<this.currentDiscipline)
                this.appendText("#      ");
            else if (i==this.currentDiscipline)
                this.appendText("*      ");
            else
                this.appendText("       ");

            this.appendText(this.disciplinePanels[i].getName() + "\n");
        }
    }

    protected void save(){
        this.playerManager.resetTemp();
        Object[] obj = new Object[2];

        obj[0] = this.playerManager;
        obj[1] = this.currentDiscipline;

        try (
                FileOutputStream fos = new FileOutputStream(FILENAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(obj);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore durante il salvataggio dei dati", "Errore", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void setCurrent(int i){
        this.currentDiscipline = i;
    }
}
