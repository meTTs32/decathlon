package it.unimol.decathlon.gui;



import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingUtilities.getWindowAncestor;

public abstract class Panel extends JPanel {

    protected JTextArea textArea;
    protected JButton button1;
    protected JButton button2;

    protected void build(){

        this.setLayout(new GridBagLayout());
        this.textArea = new JTextArea();
        this.button1 = new JButton();
        this.button2 = new JButton();
        this.textArea.setEditable(false);
        Font font = this.textArea.getFont();
        float size = font.getSize() + 4.0f;
        this.textArea.setFont(font.deriveFont(size));


        //this.textArea.append();

        this.add(this.textArea, new GridBagConstraints(0,0,1,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(2,0,0,0),0,0));
        this.add(this.button1, new GridBagConstraints(1,0,1,1,0,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(2,0,0,0),0,0));
        this.add(this.button2, new GridBagConstraints(1,1,1,1,0,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,2,0),0,0));

    }

    protected void replace(JPanel p){
        Window w = getWindowAncestor(this);
        w.remove(this);
        if(p instanceof MainPanel)
            MainPanel.getInstance().incrementDiscipline();
        w.add(p);
        w.setVisible(true);
    }

    protected void setText(String text){
        this.textArea.setText(text);
    }

    protected void appendText(String text){
        this.textArea.append(text);
    }

    /*protected void retitle(String text){
        MainFrame.getInstance().retitle(text);
    }*/

}
