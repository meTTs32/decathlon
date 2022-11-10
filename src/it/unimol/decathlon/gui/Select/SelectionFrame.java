package it.unimol.decathlon.gui.Select;

import javax.swing.*;
import java.awt.*;

public class SelectionFrame extends JFrame {

    private static SelectionFrame instance;

    private SelectionFrame() {
        this.setTitle("Decathlon");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(350, 350);
        this.setLocationRelativeTo(null);
        this.add(SelectionPanel.getInstance());
        //TODO: ridimensionare il pannello in base al numero di textAreas
    }

    public static SelectionFrame getInstance() {
        if (instance == null) {
            instance = new SelectionFrame();
        }
        return instance;
    }

}
