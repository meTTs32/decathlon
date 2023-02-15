package it.unimol.decathlon.gui.LoadSavings;

import it.unimol.decathlon.app.Player;
import it.unimol.decathlon.app.PlayerManager;
import it.unimol.decathlon.gui.MainFrame;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingUtilities.getWindowAncestor;


public class LoadPanel extends JPanel {

    private static LoadPanel instance;
    private JButton OKButton;



    private LoadPanel() {

        this.setLayout(new GridBagLayout());



    }

    public static LoadPanel getInstance() {
        if (instance == null) {
            instance = new LoadPanel();
        }
        return instance;
    }

    private void ok(){
        PlayerManager playerManager = PlayerManager.getInstance();
        boolean error = false;
        //vengono scannerizzati tutti i componenti del pannello
        //se uno di essi è una text field, viene aggiunto un giocatore con il nome inserito
        //(a meno che non si presenti un'eccezione: in tal caso viene resettato il player manager e annullata l'operazione)
        for (Component c : this.getComponents()){
            if (c instanceof JTextField){
                try {
                    playerManager.addPlayer(new Player(((JTextField) c).getText()));
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
