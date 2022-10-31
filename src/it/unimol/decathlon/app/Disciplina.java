package it.unimol.decathlon.app;

import java.io.Serializable;
import java.util.*;


public class Disciplina implements Serializable {
    private GestoreGiocatori playerManager;
    private boolean finished;
    private Set<Giocatore> pastPlayers;
    private String nomeDisciplina;


    public Disciplina (String nome) {
        this.playerManager = GestoreGiocatori.getInstance();
        this.nomeDisciplina = nome;
        this.finished = false;
        this.pastPlayers = new HashSet<>();
    }

    public String getNome() {
        return this.nomeDisciplina;
    }

    public boolean getFinished() {
        return this.finished;
    }

    public void setFinished(boolean f) {
        this.finished = f;
    }

    public boolean isFinished() {
        Set<Giocatore> currentPlayers = new HashSet<>(this.playerManager.getList());
        return this.pastPlayers.equals(currentPlayers);
    }


    public boolean addPast(Giocatore g){
        return this.pastPlayers.add(g);
    }

    public Giocatore getCurrentPlayer() {
        Random r = new Random();
        int index = this.playerManager.getList().size();
        Giocatore currentPlayer;
        do {
            currentPlayer = this.playerManager.getGiocatore(r.nextInt(index));
        } while (!(this.addPast(currentPlayer)));

        return currentPlayer;

    }

    public String getPodio() {
        this.playerManager.tempSort();
        String output = "";
        int index;
        if(this.playerManager.getList().size()<3) {
            for(Giocatore g : this.playerManager.getList()){
                index = this.playerManager.getList().indexOf(g) + 1;
                output += index + ") " + g.tempString() + "\n";
            }
            return output;
        }

        for(int i=0; i<3; i++)
            output += i+1 + ") " + this.playerManager.getGiocatore(i).tempString() + "\n";
        return output;
    }

    public String getWinner() {
        return this.playerManager.getGiocatore(0).tempString();
    }


}
