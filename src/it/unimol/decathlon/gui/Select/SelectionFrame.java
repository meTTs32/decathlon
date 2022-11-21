package it.unimol.decathlon.gui.Select;

import javax.swing.*;

public class SelectionFrame extends JFrame {

    private static SelectionFrame instance;

    private SelectionFrame() {
        super("NOMI GIOCATORI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(350, 350);
        this.setLocationRelativeTo(null);
        this.add(SelectionPanel.getInstance());
        this.pack();
    }

    public static SelectionFrame getInstance() {
        if (instance == null) {
            instance = new SelectionFrame();
        }
        return instance;
    }

}
