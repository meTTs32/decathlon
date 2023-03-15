package it.unimol.decathlon.gui.Start;

import it.unimol.decathlon.app.PlayerManager;
import it.unimol.decathlon.gui.MainFrame;
import it.unimol.decathlon.gui.MainPanel;
import it.unimol.decathlon.gui.Select.SelectionFrame;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import static javax.swing.SwingUtilities.getWindowAncestor;

public class StartPanel extends JPanel {

    private static final String FILENAME = "save.bin";
    private static StartPanel instance;

    private StartPanel(boolean load) {

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton startButton = new JButton("AVVIA PARTITA");
        JButton exitButton = new JButton("ESCI DAL GIOCO");

        JButton loadButton = new JButton("CARICA PARTITA");
        loadButton.setEnabled(false);

        if(load) {
            loadButton.setEnabled(true);
            loadButton.addActionListener(e -> this.load());
        }

        startButton.addActionListener(e -> this.start());
        exitButton.addActionListener(e -> this.exit());

        this.add(startButton, BorderLayout.CENTER);
        this.add(loadButton, BorderLayout.CENTER);
        this.add(exitButton, BorderLayout.SOUTH);

        }


    public static StartPanel getInstance(boolean load) {
        if (instance == null) {
            instance = new StartPanel(load);
        }
        return instance;
    }

    private void start() {
        getWindowAncestor(this).dispose();
        SelectionFrame.getInstance().setVisible(true);
    }

    private void exit() {
        int selected = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler uscire?", "Attenzione", JOptionPane.YES_NO_OPTION);
        if (selected == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void load(){
        if(JOptionPane.showConfirmDialog(this, "E' presente una partita salvata, vuoi caricarla?", "CARICA PARTITA", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            getWindowAncestor(this).dispose();
            try (
                    FileInputStream fis = new FileInputStream(FILENAME);
                    ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                Object[] obj = (Object[]) ois.readObject();

                PlayerManager.getInstance((PlayerManager) obj[0]);
                MainPanel.getInstance().setCurrent(((int) obj[1]) - 1);

                MainFrame.getInstance().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Errore nel caricamento del file di salvataggio.\nVerrà avviata una nuova partita", "Errore", JOptionPane.ERROR_MESSAGE);
                SelectionFrame.getInstance().setVisible(true);
            }
        }
    }

}
