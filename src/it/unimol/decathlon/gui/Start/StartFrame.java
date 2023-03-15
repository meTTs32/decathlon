package it.unimol.decathlon.gui.Start;

import javax.swing.*;

public class StartFrame extends JFrame {

    private static StartFrame instance;

    private StartFrame(boolean load) {
        super("BENVENUTI AL DECATHLON!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 110);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(StartPanel.getInstance(load));
    }

    public static StartFrame getInstance(boolean load) {
        if (instance == null) {
            instance = new StartFrame(load);
        }
        return instance;
    }
}
