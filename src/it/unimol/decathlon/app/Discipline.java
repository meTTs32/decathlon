package it.unimol.decathlon.app;

import java.io.Serializable;
import java.util.*;


public class Discipline implements Serializable {
    private PlayerManager playerManager;
    private boolean finished;
    private Set<Player> pastPlayers;
    private String disciplineName;


    public Discipline(String nome) {
        this.playerManager = PlayerManager.getInstance();
        this.disciplineName = nome;
        this.finished = false;
        this.pastPlayers = new HashSet<>();
    }

    public String getName() {
        return this.disciplineName;
    }

    public boolean getFinished() {
        return this.finished;
    }

    public void setFinished(boolean f) {
        this.finished = f;
    }

    public boolean isFinished() {
        Set<Player> currentPlayers = new HashSet<>(this.playerManager.getList());
        return this.pastPlayers.equals(currentPlayers);
    }


    public boolean addPast(Player g){
        return this.pastPlayers.add(g);
    }

    public Player getCurrentPlayer() {
        Random r = new Random();
        int index = this.playerManager.getList().size();
        Player currentPlayer;
        do {
            currentPlayer = this.playerManager.getPlayer(r.nextInt(index));
        } while (!(this.addPast(currentPlayer)));

        return currentPlayer;

    }

    public String getPodium() {
        this.playerManager.tempSort();
        String output = "";
        int index;
        if(this.playerManager.getList().size()<3) {
            for(Player g : this.playerManager.getList()){
                index = this.playerManager.getList().indexOf(g) + 1;
                output += index + ") " + g.tempString() + "\n";
            }
            return output;
        }

        for(int i=0; i<3; i++)
            output += i+1 + ") " + this.playerManager.getPlayer(i).tempString() + "\n";
        return output;
    }

    public String getWinner() {
        return this.playerManager.getPlayer(0).tempString();
    }


}
