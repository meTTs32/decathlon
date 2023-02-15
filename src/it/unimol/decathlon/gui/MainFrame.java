package it.unimol.decathlon.gui;

import it.unimol.decathlon.app.Database;

import javax.swing.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


public class MainFrame extends JFrame {

    private static MainFrame instance;
    private String FILENAME = "save.bin";

    private MainFrame(){
        super("DECATHLON");
        this.setSize(500,400);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                MainFrame.getInstance().save();
                MainFrame.getInstance().dispose();

            }
        });

        this.add(MainPanel.getInstance());
    }

    public static MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame();
        }
        return instance;
    }

    private void save() {
        int option = JOptionPane.showConfirmDialog(this, "Vuoi salvare la partita?", "Salvataggio", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            File file = new File(FILENAME);
            if (file.exists())
                Database.getInstance().addSavings(file);
            file.delete();
        }
    }

}
