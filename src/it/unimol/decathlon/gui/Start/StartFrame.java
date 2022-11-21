package it.unimol.decathlon.gui.Start;

import javax.swing.*;

public class StartFrame extends JFrame {

    private static StartFrame instance;
    private StartFrame() {
        super("BENVENUT* AL DECATHLON!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 110);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(StartPanel.getInstance());
    }

    public static StartFrame getInstance() {
        if (instance == null) {
            instance = new StartFrame();
        }
        return instance;
    }
}
