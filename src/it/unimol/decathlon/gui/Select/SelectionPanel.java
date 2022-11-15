package it.unimol.decathlon.gui.Select;

import it.unimol.decathlon.app.GestoreGiocatori;
import it.unimol.decathlon.app.Giocatore;
import it.unimol.decathlon.gui.MainFrame;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingUtilities.getWindowAncestor;


public class SelectionPanel extends JPanel {

    private static SelectionPanel instance;
    private JButton OKButton;



    private SelectionPanel() {

        this.setLayout(new GridBagLayout());

        //setup bottone
        this.OKButton = new JButton("OK");

        //creazione dello spinner
        JSpinner playersSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 8, 1));
        playersSpinner.setEditor(new JSpinner.DefaultEditor(playersSpinner));
        JOptionPane.showMessageDialog(this, playersSpinner, "Seleziona il numero di giocatori", JOptionPane.PLAIN_MESSAGE);

        //viene letto il valore dello spinner e vengono aggiunte le corrispettive Text Area
        int players = Integer.parseInt(playersSpinner.getValue().toString());
        this.add(new JLabel("Inserisci i nomi dei giocatori"));

        for(int i = 0; i < players; i++){
            JLabel label = new JLabel("Giocatore " + (i + 1));
            JTextField textField = new JTextField(15);
            this.add(label, new GridBagConstraints(0, i+1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NORTH, new Insets(5, 0, 5, 0), 0, 0));
            this.add(textField, new GridBagConstraints(1, i+1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NORTH, new Insets(5, 5, 5, 0), 0, 0));
        }

        //setup bottone
        this.OKButton.addActionListener(e -> this.ok());
        this.add(this.OKButton, new GridBagConstraints(0, players + 1, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NORTH, new Insets(5, 0, 0, 0), 0, 0));
    }

    public static SelectionPanel getInstance() {
        if (instance == null) {
            instance = new SelectionPanel();
        }
        return instance;
    }

    private void ok(){
        GestoreGiocatori playerManager = GestoreGiocatori.getInstance();
        boolean error = false;
        //vengono scannerizzati tutti i componenti del pannello
        //se uno di essi è una text field, viene aggiunto un giocatore con il nome inserito
        //(a meno che non si presenti un'eccezione: in tal caso viene resettato il player manager e annullata l'operazione)
        for (Component c : this.getComponents()){
            if (c instanceof JTextField){
                try {
                    playerManager.addGiocatore(new Giocatore(((JTextField) c).getText()));
                } catch (Exception e) {
                    error = true;
                    playerManager.reset();
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        }

        if (!error){
            getWindowAncestor(this).dispose();
            MainFrame.getInstance().setVisible(true);
        }

    }
}
