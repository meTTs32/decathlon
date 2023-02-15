package it.unimol.decathlon.gui;


import it.unimol.decathlon.app.Database;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingUtilities.getWindowAncestor;

public abstract class Panel extends JPanel {

    protected final String FILENAME = "save.bin";
    protected JTextArea textArea;
    protected JScrollPane scrollPane;
    protected JButton button1;
    protected JButton button2;

    protected Database db;



    protected void build(){

        this.db = Database.getInstance();
        this.setLayout(new GridBagLayout());
        this.textArea = new JTextArea();
        this.button1 = new JButton();
        this.button2 = new JButton();

        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setEditable(false);
        Font font = this.textArea.getFont();
        float size = font.getSize() + 4.0f;
        this.textArea.setFont(font.deriveFont(size));

        this.scrollPane = new JScrollPane(this.textArea);


        this.add(this.scrollPane, new GridBagConstraints(0,0,1,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(2,0,0,0),0,0));
        this.add(this.button1, new GridBagConstraints(1,0,1,1,0,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(2,0,0,0),0,0));
        this.add(this.button2, new GridBagConstraints(1,1,1,1,0,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,2,0),0,0));

    }

    protected void replace(JPanel p){
        Window w = getWindowAncestor(this);
        w.remove(this);
        w.add(p);
        w.repaint();
        w.setVisible(true);
    }


    protected void setText(String text){
        this.textArea.setText(text);
    }

    protected void appendText(String text){
        this.textArea.append(text);
    }


}
