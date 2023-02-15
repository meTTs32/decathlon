package it.unimol.decathlon.gui.LoadSavings;

import javax.swing.*;

public class LoadFrame extends JFrame {

    private static LoadFrame instance;

    private LoadFrame() {
        super("NOMI GIOCATORI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(350, 350);
        this.setLocationRelativeTo(null);
        this.add(LoadPanel.getInstance());
        this.pack();
    }

    public static LoadFrame getInstance() {
        if (instance == null) {
            instance = new LoadFrame();
        }
        return instance;
    }

}
