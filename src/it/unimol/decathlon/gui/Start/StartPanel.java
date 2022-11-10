package it.unimol.decathlon.gui.Start;

import it.unimol.decathlon.gui.Select.SelectionFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.SwingUtilities.getWindowAncestor;

public class StartPanel extends JPanel {

    private static String FILENAME = "prova.txt";
    private static StartPanel instance;
    private JButton StartButton;
    private JButton ExitButton;

    private JButton LoadButton;

    private StartPanel() {

        this.LoadButton = new JButton("CARICA PARTITA");
        this.LoadButton.setEnabled(false);
        File file = new File(FILENAME);
        if (file.exists()) {
            this.LoadButton.setEnabled(true);
            this.loadGame();
        }

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.StartButton = new JButton("NUOVA PARTITA");
        this.ExitButton = new JButton("ESCI DAL GIOCO");

        this.StartButton.addActionListener(e -> this.start());
        this.ExitButton.addActionListener(e -> this.exit());

        this.add(this.StartButton, BorderLayout.CENTER);
        this.add(this.LoadButton, BorderLayout.CENTER);
        this.add(this.ExitButton, BorderLayout.SOUTH);

    }


    public static StartPanel getInstance() {
        if (instance == null) {
            instance = new StartPanel();
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

    private void loadGame(){
        int selected = JOptionPane.showConfirmDialog(this, "Esiste un file di salvataggio valido, vuoi caricare i dati?", "Attenzione", JOptionPane.YES_NO_OPTION);
        if (selected == JOptionPane.YES_OPTION) {
            //LOAD DATA
        }
    }
}
