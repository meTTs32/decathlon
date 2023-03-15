package it.unimol.decathlon.gui;

import it.unimol.decathlon.app.PlayerManager;
import it.unimol.decathlon.gui.Start.StartFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoadManager {

    private static final String FILENAME = "save.bin";

    public static void start(){
        File file = new File(FILENAME);
        if (file.exists()) {
            int option = JOptionPane.showConfirmDialog(null, "E' presente una partita salvata, vuoi caricarla?", "CARICA PARTITA", JOptionPane.YES_NO_OPTION);
            if (option==JOptionPane.YES_OPTION){
                try (
                        FileInputStream fis = new FileInputStream(FILENAME);
                        ObjectInputStream ois = new ObjectInputStream(fis)
                ) {
                    Object[] obj = (Object[]) ois.readObject();

                    PlayerManager.getInstance((PlayerManager) obj[0]);
                    MainPanel.getInstance().setCurrent(((int) obj[1]) - 1);

                    MainFrame.getInstance().setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Errore nel caricamento del file di salvataggio", "Errore", JOptionPane.ERROR_MESSAGE);
                    StartFrame.getInstance(false).setVisible(true);
                }
            } else if(option==JOptionPane.NO_OPTION)
                StartFrame.getInstance(true).setVisible(true);
            else
                System.exit(0);
        } else {
            StartFrame.getInstance(false).setVisible(true);
        }
    }


}
