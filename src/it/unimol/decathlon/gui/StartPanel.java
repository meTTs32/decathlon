package it.unimol.decathlon.gui;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingUtilities.getWindowAncestor;

public class StartPanel extends JPanel {

    private static StartPanel mainPanel;
    private JButton StartButton;

    private StartPanel() {
        this.setLayout(new BorderLayout());
        this.StartButton = new JButton("START");
        this.StartButton.addActionListener(e -> this.start());
        this.add(this.StartButton, BorderLayout.CENTER);
    }

    public static StartPanel getInstance() {
        if (mainPanel == null) {
            mainPanel = new StartPanel();
        }
        return mainPanel;
    }

    private void start() {
        getWindowAncestor(this).dispose();
    }
}
