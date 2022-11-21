package it.unimol.decathlon.ui;

import it.unimol.decathlon.app.PlayerManager;

import java.io.File;




public class EndingScreen extends Screen{

    public EndingScreen() {
        this.playerManager = PlayerManager.getInstance();
        this.discipline = null;
    }

    public void start() {
        this.suspance("Partita terminata, calcolo vincitore " , 800);
        this.clearScreen();
        System.out.println("Classifica finale: ");


        System.out.println(this.playerManager.toString());

        //System.out.println(this.playerManager.getGiocatore(0).getNome() + " ha vinto la partita con " + this.playerManager.getGiocatore(0).getPunteggio() +" punti!");
        System.out.print("Premi invio per terminare");
        this.waitUserInput();
        File f = new File(FILENAME);
        if(f.exists())
            f.delete();
    }
}

