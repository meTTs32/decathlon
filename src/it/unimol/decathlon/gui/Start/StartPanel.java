package it.unimol.decathlon.gui.Start;

import it.unimol.decathlon.app.PlayerManager;
import it.unimol.decathlon.gui.MainFrame;
import it.unimol.decathlon.gui.MainPanel;
import it.unimol.decathlon.gui.Select.SelectionFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import static javax.swing.SwingUtilities.getWindowAncestor;

public class StartPanel extends JPanel {

    private static String FILENAME = "save.bin";
    private static StartPanel instance;

    private JButton StartButton;
    private JButton ExitButton;

    private JButton LoadButton;

    private boolean load;

    private StartPanel() {
        File file = new File(FILENAME);

        this.LoadButton = new JButton("CARICA PARTITA");
        this.LoadButton.setEnabled(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.StartButton = new JButton("AVVIA PARTITA");
        this.ExitButton = new JButton("ESCI DAL GIOCO");

        this.StartButton.addActionListener(e -> this.start());
        this.ExitButton.addActionListener(e -> this.exit());
        this.LoadButton.addActionListener(e -> this.load=this.loadGame(file));

        this.add(this.StartButton, BorderLayout.CENTER);
        this.add(this.LoadButton, BorderLayout.CENTER);
        this.add(this.ExitButton, BorderLayout.SOUTH);

        if (file.exists()) {
            this.LoadButton.setEnabled(true);
            this.load=this.loadGame(file);
        }

    }


    public static StartPanel getInstance() {
        if (instance == null) {
            instance = new StartPanel();
        }
        return instance;
    }

    private void start() {
        getWindowAncestor(this).dispose();
        if(this.load)
            MainFrame.getInstance().setVisible(true);
        else
            SelectionFrame.getInstance().setVisible(true);
    }

    private void exit() {
        int selected = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler uscire?", "Attenzione", JOptionPane.YES_NO_OPTION);
        if (selected == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private boolean loadGame(File file){

        boolean loaded = false;
        int selected = JOptionPane.showConfirmDialog(this, "Esiste un file di salvataggio valido, vuoi caricare i dati?", "Attenzione", JOptionPane.YES_NO_OPTION);
        if (selected == JOptionPane.YES_OPTION) {
            if(file.exists()) {
                try (
                    FileInputStream fis = new FileInputStream(FILENAME);
                    ObjectInputStream ois = new ObjectInputStream(fis)
                ) {
                    Object[] obj = (Object[]) ois.readObject();

                    PlayerManager.getInstance((PlayerManager) obj[0]);
                    MainPanel.getInstance().setCurrent(((int) obj[1])-1);
                    loaded = true;
                    JOptionPane.showMessageDialog(this, "Partita caricata con successo, clicca su AVVIA PARTITA per iniziare", "Caricamento", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Errore nel caricamento del file di salvataggio", "Errore", JOptionPane.ERROR_MESSAGE);
                } finally {
                    this.LoadButton.setEnabled(false);
                }

            }
        }

        return loaded;
    }

}
