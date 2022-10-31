package it.unimol.decathlon.app;

import java.io.*;
import java.util.*;

public class GestoreGiocatori implements Serializable {

    private List<Giocatore> classifica;
    private static GestoreGiocatori instance;

    private GestoreGiocatori() {
        this.classifica = new ArrayList<Giocatore>();
    }
    private GestoreGiocatori(GestoreGiocatori g) { this.classifica = new ArrayList<>(g.getList()); }

    public static GestoreGiocatori getInstance() {
        if (instance == null)
            instance = new GestoreGiocatori();
        return instance;
    }

    public static GestoreGiocatori getInstance(GestoreGiocatori g){
        instance = new GestoreGiocatori(g);
        return instance;
    }

    /*public void loadList(GestoreGiocatori g) {
        this.classifica = new ArrayList<>(g.getList());
    }*/
    public void addGiocatore(Giocatore g) {
        this.classifica.add(g);
    }

    public Giocatore getGiocatore (int i) {
        return this.classifica.get(i);
    }

    public void resetTemp() {
        for (Giocatore g : this.classifica){
            g.setTempPunteggio(0);
        }
    }

    public List<Giocatore> getList() {
        return this.classifica;
    }

    private void sort() {
        Collections.sort(this.classifica);
    }

    public void tempSort() {
        Collections.sort(this.classifica, (p1, p2) -> Integer.compare(p2.getTempPunteggio(), p1.getTempPunteggio()));
    }

    public String toString() {
        this.sort();
        String output = "";
        if(this.classifica.size() == 0){
            return "Nessun giocatore";
        }
        for(int i=0; i<this.classifica.size(); i++)
            output += i+1 + ") " + this.getGiocatore(i).toString() + "\n";
        return output;
    }


    public boolean contains(String g) {
        for (Giocatore p : this.classifica) {
            if(p.getNome().equals(g))
                return true;
        }

        return false;
    }
}