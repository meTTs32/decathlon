package it.unimol.decathlon.gui.Start;

import it.unimol.decathlon.app.Database;
import it.unimol.decathlon.app.PlayerManager;
import it.unimol.decathlon.gui.MainFrame;
import it.unimol.decathlon.gui.MainPanel;
import it.unimol.decathlon.gui.Select.SelectionFrame;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.getWindowAncestor;

public class StartPanel extends JPanel {

    private static String FILENAME = "save.bin";
    private static StartPanel instance;

    private JButton StartButton;
    private JButton ExitButton;
    private JButton LoadButton;
    private Database db;
    private List<Document> savings;


    private StartPanel() {

        this.LoadButton = new JButton("CARICA PARTITA");
        this.LoadButton.addActionListener(e -> this.loadGame());

        this.db = Database.getInstance();
        this.savings = new ArrayList<>(this.db.getSavings());
        if(savings.isEmpty())
            this.LoadButton.setEnabled(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.StartButton = new JButton("AVVIA PARTITA");
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

    }

}
