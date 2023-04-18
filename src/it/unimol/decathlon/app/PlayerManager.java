package it.unimol.decathlon.app;

import it.unimol.decathlon.app.exceptions.DuplicatePlayerException;

import java.io.*;
import java.util.*;

public class PlayerManager implements Serializable {

    private final List<Player> leaderboard;
    private static PlayerManager instance;

    private PlayerManager() {
        this.leaderboard = new ArrayList<>();
    }
    private PlayerManager(PlayerManager g) { this.leaderboard = new ArrayList<>(g.getBoard()); }

    public static PlayerManager getInstance() {
        if (instance == null)
            instance = new PlayerManager();
        return instance;
    }

    public static PlayerManager getInstance(PlayerManager g){
        instance = new PlayerManager(g);
        return instance;
    }

    public void addPlayer(Player g) throws DuplicatePlayerException {
        if(this.contains(g.getName()))
            throw new DuplicatePlayerException();
        else
            this.leaderboard.add(g);
    }

    public Player getPlayer(int i) {
        return this.leaderboard.get(i);
    }

    public void resetTemp() {
        for (Player g : this.leaderboard){
            g.setTempScore(0);
        }
    }
    public List<Player> getBoard() {
        return this.leaderboard;
    }

    private void sort() {
        Collections.sort(this.leaderboard);
    }

    public void tempSort() {
        this.leaderboard.sort((p1, p2) -> Integer.compare(p2.getTempScore(), p1.getTempScore()));
    }

    public boolean contains(String g) {
        for (Player p : this.leaderboard) {
            if(p.getName().equals(g))
                return true;
        }

        return false;
    }

    public void reset() {
        this.leaderboard.clear();
    }

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