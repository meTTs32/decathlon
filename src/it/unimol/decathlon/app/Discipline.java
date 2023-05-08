package it.unimol.decathlon.app;

import java.io.Serializable;
import java.util.*;


public class Discipline implements Serializable {

    /**
     * Classe che serve a gestire una singola disciplina.
     */

    private final PlayerManager playerManager;

    private final Set<Player> pastPlayers;
    private final String disciplineName;

    private String instructions;


    /**
     * Costruttore della classe disciplina.
     *
     * @param nome nome della disciplina.
     */
    public Discipline(String nome) {
        this.playerManager = PlayerManager.getInstance();
        this.disciplineName = nome;
        this.pastPlayers = new HashSet<>();
        this.instructions = "";
    }


    /**
     * Restituisce il nome della disciplina.
     *
     * @return il nome della disciplina.
     */
    public String getName() {
        return this.disciplineName;
    }


    /**
     * Imposta le istruzioni della disciplina.
     * @param instructions istruzioni della disciplina.
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


    /**
     * Restituisce le istruzioni della disciplina.
     *
     * @return le istruzioni della disciplina.
     */
    public String getInstructions() {
        return this.instructions;
    }


    /**
     * Restituisce un booleano, che indica se la disciplina è terminata oppure no.
     * @return true se la disciplina è terminata, false altrimenti.
     */
    public boolean isFinished() {
        Set<Player> currentPlayers = new HashSet<>(this.playerManager.getBoard());
        return this.pastPlayers.equals(currentPlayers);
    }


    /**
     * Restituisce il giocatore corrente.
     * @return il giocatore corrente.
     */
    public Player getCurrentPlayer() {
        Random r = new Random();
        int index = this.playerManager.getBoard().size();
        Player currentPlayer;
        do {
            currentPlayer = this.playerManager.getPlayer(r.nextInt(index));
        } while (!(this.pastPlayers.add(currentPlayer)));

        return currentPlayer;

    }


    /**
     * Restituisce il podio della disciplina
     * (se alla partita partecipano meno di 3 giocatori, restituisce l'intera classifica).
     * @return la classifica della disciplina.
     */
    public String getPodium() {
        this.playerManager.tempSort();
        StringBuilder output = new StringBuilder();
        int index;

        if(this.playerManager.getBoard().size()<3) {
            for(Player g : this.playerManager.getBoard()){
                index = this.playerManager.getBoard().indexOf(g) + 1;
                output.append(index).append(") ").append(g.tempString()).append("\n");
            }
        }else for(int i=0; i<3; i++)
            output.append(i + 1).append(") ").append(this.playerManager.getPlayer(i).tempString()).append("\n");

        output.append("\nIl vincitore della disciplina è ").append(this.getWinner()).append(" punti!\n");

        return output.toString();
    }


    private String getWinner() {
        return this.playerManager.getPlayer(0).tempString();
    }


}
