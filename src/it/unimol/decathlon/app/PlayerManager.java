package it.unimol.decathlon.app;

import it.unimol.decathlon.app.exceptions.DuplicatePlayerException;

import java.io.*;
import java.util.*;

public class PlayerManager implements Serializable {

    /**
     * Singleton che serve a gestire la classifica del gioco.
     */

    private final List<Player> leaderboard;
    private static PlayerManager instance;

    private PlayerManager() {
        this.leaderboard = new ArrayList<>();
    }
    private PlayerManager(PlayerManager g) { this.leaderboard = new ArrayList<>(g.getBoard()); }


    /**
     * Restituisce l'istanza del singleton. Nel caso in cui questo metodo viene chiamato per la prima volta, prima di
     * restituire l'istanza, quest'ultima viene inizializzata.
     *
     * @return l'istanza del singleton
     */
    public static PlayerManager getInstance() {
        if (instance == null)
            instance = new PlayerManager();
        return instance;
    }


    /**
     * Restituisce l'istanza del singleton. In questo caso viene chiesto un parametro di input in quanto
     * questo metodo viene utilizzato durante la serializzazione per poter caricare un'istanza preesistente del singleton.
     *
     * @param g l'istanza da caricare
     * @return l'istanza del singleton
     */
    public static PlayerManager getInstance(PlayerManager g){
        instance = new PlayerManager(g);
        return instance;
    }


    /**
     * Aggiunge un giocatore alla classifica.
     *
     * @param g il giocatore da aggiungere
     * @throws DuplicatePlayerException se il giocatore è già presente nella classifica
     */
    public void addPlayer(Player g) throws DuplicatePlayerException {
        if(this.contains(g.getName()))
            throw new DuplicatePlayerException();
        else
            this.leaderboard.add(g);
    }


    /**
     * Restituisce il giocatore nella classifica che si trova nella i+1 esima posizione.
     *
     * @param i l'indice del giocatore da ottenere
     * @return il giocatore
     */
    public Player getPlayer(int i) {
        return this.leaderboard.get(i);
    }


    /**
     * Azzera i punti della classifica della singola disciplina di tutti i giocatori.
     */
    public void resetTemp() {
        for (Player g : this.leaderboard){
            g.setTempScore(0);
        }
    }


    /**
     * Restituisce la classifica dei giocatori.
     *
     * @return la classifica
     */
    public List<Player> getBoard() {
        return this.leaderboard;
    }


    /**
     * Ordina la classifica in base ai punti totali dei giocatori.
     */
    private void sort() {
        Collections.sort(this.leaderboard);
    }


    /**
     * Ordina la classifica in base ai punti della singola disciplina dei giocatori.
     */
    public void tempSort() {
        this.leaderboard.sort((p1, p2) -> Integer.compare(p2.getTempScore(), p1.getTempScore()));
    }


    /**
     * Restituisce un booleano, che rappresenta se il giocatore passato come parametro è presente o meno in classifica
     * @param g il giocatore da cercare
     * @return true se il giocatore è presente, false altrimenti
     */
    public boolean contains(String g) {
        for (Player p : this.leaderboard) {
            if(p.getName().equals(g))
                return true;
        }

        return false;
    }


    /**
     * Elimina tutti i giocatori dalla classifica.
     */
    public void reset() {
        this.leaderboard.clear();
    }


    /**
     * Restituisce una stringa che rappresenta la classifica.
     *
     * @return la stringa che rappresenta la classifica
     */
    public String toString() {
        this.sort();
        StringBuilder output = new StringBuilder();
        if(this.leaderboard.size() == 0){
            return "Nessun giocatore";
        }
        for(int i = 0; i<this.leaderboard.size(); i++)
            output.append(i + 1).append(") ").append(this.getPlayer(i).toString()).append("\n");
        return output.toString();
    }
}