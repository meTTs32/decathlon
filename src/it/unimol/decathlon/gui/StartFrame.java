package it.unimol.decathlon.gui;

import javax.swing.*;

public class StartFrame extends JFrame {

    private static StartFrame mainFrame;
    private StartFrame() {
        super("BENVENUTI AL DECATHLON!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.add(StartPanel.getInstance());
    }

    public static StartFrame getInstance() {
        if (mainFrame == null) {
            mainFrame = new StartFrame();
        }
        return mainFrame;
    }
}
