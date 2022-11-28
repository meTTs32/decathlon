package it.unimol.decathlon.gui;

import javax.swing.*;

public class MainFrame extends JFrame {

    private static MainFrame instance;

    private MainFrame(){
        super("DECATHLON");
        this.setSize(500,400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(MainPanel.getInstance());
    }

    public static MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame();
        }
        return instance;
    }

}
