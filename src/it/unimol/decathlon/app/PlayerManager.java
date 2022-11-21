package it.unimol.decathlon.app;

import it.unimol.decathlon.app.exceptions.DuplicatePlayerException;

import java.io.*;
import java.util.*;

public class PlayerManager implements Serializable {

    private List<Player> classifica;
    private static PlayerManager instance;

    private PlayerManager() {
        this.classifica = new ArrayList<>();
    }
    private PlayerManager(PlayerManager g) { this.classifica = new ArrayList<>(g.getList()); }

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
            this.classifica.add(g);
    }

    public Player getPlayer(int i) {
        return this.classifica.get(i);
    }

    public void resetTemp() {
        for (Player g : this.classifica){
            g.setTempPoints(0);
        }
    }

    public List<Player> getList() {
        return this.classifica;
    }

    private void sort() {
        Collections.sort(this.classifica);
    }

    public void tempSort() {
        Collections.sort(this.classifica, (p1, p2) -> Integer.compare(p2.getTempPoints(), p1.getTempPoints()));
    }

    public String toString() {
        this.sort();
        String output = "";
        if(this.classifica.size() == 0){
            return "Nessun giocatore";
        }
        for(int i=0; i<this.classifica.size(); i++)
            output += i+1 + ") " + this.getPlayer(i).toString() + "\n";
        return output;
    }


    public boolean contains(String g) {
        for (Player p : this.classifica) {
            if(p.getName().equals(g))
                return true;
        }

        return false;
    }

    public void reset() {
        this.classifica.clear();
    }
}